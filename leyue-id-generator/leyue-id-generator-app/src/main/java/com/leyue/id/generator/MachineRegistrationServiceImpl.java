package com.leyue.id.generator;

import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.api.generator.MachineRegistrationService;
import com.leyue.id.domain.generator.enums.MachineStatusEnum;
import com.leyue.id.domain.generator.gateway.DataCenterGateway;
import com.leyue.id.domain.generator.gateway.MachineNodeGateway;
import com.leyue.id.domain.generator.model.DataCenter;
import com.leyue.id.domain.generator.model.MachineNode;
import com.leyue.id.dto.generator.MachineNodeDTO;
import com.leyue.id.utils.NetworkUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 机器注册服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MachineRegistrationServiceImpl implements MachineRegistrationService {

    private final DataCenterGateway dataCenterGateway;

    private final MachineNodeGateway machineNodeGateway;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<MachineNodeDTO> registerMachine(String dataCenterName) {
        log.info("开始注册机器到数据中心: {}", dataCenterName);
        
        // 1. 查找数据中心，如果不存在则创建一个新的
        DataCenter dataCenter = dataCenterGateway.findByName(dataCenterName);
        if (dataCenter == null) {
            log.info("数据中心[{}]不存在，创建新的数据中心", dataCenterName);
            dataCenter = new DataCenter();
            dataCenter.setName(dataCenterName);
            dataCenter.setCenterId(1L); // 默认为1，实际应用中应该有分配逻辑
            dataCenter.setRegion("默认区域");
            dataCenter = dataCenterGateway.save(dataCenter);
        }
        
        // 2. 获取本机IP和MAC
        String localIp = NetworkUtils.getLocalIp();
        String localMac = NetworkUtils.getLocalMac();
        log.info("本机信息 - IP: {}, MAC: {}", localIp, localMac);
        
        // 3. 检查是否已注册
        MachineNode existNode = machineNodeGateway.findByMac(localMac);
        if (existNode != null) {
            log.info("机器已注册，更新状态为在线 - machineId: {}", existNode.getMachineId());
            // 如果已注册，则更新状态为在线
            existNode.setIp(localIp); // 更新IP，可能会变
            existNode.setStatus(MachineStatusEnum.ONLINE);
            existNode.setLastOnlineTime(new Date());
            existNode = machineNodeGateway.update(existNode);
            
            // 转换为DTO并返回
            MachineNodeDTO dto = convertToDTO(existNode);
            return SingleResponse.of(dto);
        }
        
        // 4. 未注册，则分配新的机器ID并注册
        Long machineId = machineNodeGateway.allocateMachineId(dataCenter.getCenterId());
        log.info("分配新的机器ID: {}", machineId);
        
        MachineNode newNode = new MachineNode();
        newNode.setCenterId(dataCenter.getCenterId());
        newNode.setMachineId(machineId);
        newNode.setIp(localIp);
        newNode.setMac(localMac);
        newNode.setStatus(MachineStatusEnum.ONLINE);
        newNode.setLastOnlineTime(new Date());
        
        // 5. 保存新节点
        MachineNode savedNode = machineNodeGateway.save(newNode);
        log.info("机器注册成功 - machineId: {}", savedNode.getMachineId());
        
        // 6. 转换为DTO并返回
        MachineNodeDTO dto = convertToDTO(savedNode);
        return SingleResponse.of(dto);
    }

    @Override
    public SingleResponse<MachineNodeDTO> getLocalMachineInfo() {
        String localMac = NetworkUtils.getLocalMac();
        MachineNode node = machineNodeGateway.findByMac(localMac);
        
        if (node == null) {
            return SingleResponse.buildFailure("NODE_NOT_FOUND", "本机未注册");
        }
        
        return SingleResponse.of(convertToDTO(node));
    }
    
    /**
     * 将领域模型转换为DTO
     */
    private MachineNodeDTO convertToDTO(MachineNode node) {
        if (node == null) {
            return null;
        }
        
        MachineNodeDTO dto = new MachineNodeDTO();
        BeanUtils.copyProperties(node, dto);
        if (node.getStatus() != null) {
            dto.setStatus(node.getStatus().name());
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<Boolean> markMachineOffline() {
        log.info("开始将机器标记为离线状态");
        
        try {
            // 获取本机MAC地址
            String localMac = NetworkUtils.getLocalMac();
            
            // 查找本机节点
            MachineNode node = machineNodeGateway.findByMac(localMac);
            if (node == null) {
                log.warn("未找到本机注册信息，MAC: {}", localMac);
                return SingleResponse.buildFailure("NODE_NOT_FOUND", "本机未注册");
            }
            
            // 更新状态为离线
            node.setStatus(MachineStatusEnum.OFFLINE);
            node.setLastOfflineTime(new Date());
            node = machineNodeGateway.update(node);

            
            log.info("机器已成功标记为离线状态 - machineId: {}", node.getMachineId());
            return SingleResponse.of(true);
        } catch (Exception e) {
            log.error("将机器标记为离线状态时发生错误", e);
            return SingleResponse.buildFailure("MARK_OFFLINE_ERROR", "将机器标记为离线状态时发生错误: " + e.getMessage());
        }
    }
} 
package com.leyue.id.generator;


import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.api.generator.IdGeneratorService;
import com.leyue.id.domain.generator.enums.MachineStatusEnum;
import com.leyue.id.domain.generator.gateway.MachineNodeGateway;
import com.leyue.id.domain.generator.model.MachineNode;
import com.leyue.id.dto.generator.IdGenerateRequest;
import com.leyue.id.dto.generator.IdGenerateResponse;
import com.leyue.id.generator.executor.cmd.GenerateIdCmdExe;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * ID生成器服务实现类
 */
@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {
    
    @Resource
    private GenerateIdCmdExe generateIdCmdExe;
    
    @Resource
    private MachineNodeGateway machineNodeGateway;
    
    /**
     * 当前机器的节点信息
     */
    private MachineNode currentMachineNode;
    
    @Override
    public SingleResponse<IdGenerateResponse> generateId(IdGenerateRequest request) {
        try {
            // 如果请求中没有指定数据中心ID和机器ID，使用当前机器的配置
            if (request.getDataCenterId() == null || request.getMachineId() == null) {
                setupCurrentMachineIfNeeded();
                request.setDataCenterId(currentMachineNode.getCenterId());
                request.setMachineId(currentMachineNode.getMachineId());
            }
            
            // 执行ID生成
            IdGenerateResponse response = generateIdCmdExe.execute(request);
            return SingleResponse.of(response);
        } catch (Exception e) {
            return SingleResponse.buildFailure("GENERATE_ID_ERROR", e.getMessage());
        }
    }
    
    @Override
    public MultiResponse<IdGenerateResponse> generateBatchIds(IdGenerateRequest request) {
        try {
            // 如果请求中没有指定数据中心ID和机器ID，使用当前机器的配置
            if (request.getDataCenterId() == null || request.getMachineId() == null) {
                setupCurrentMachineIfNeeded();
                request.setDataCenterId(currentMachineNode.getCenterId());
                request.setMachineId(currentMachineNode.getMachineId());
            }
            
            // 确保批量大小有效
            if (request.getBatchSize() == null || request.getBatchSize() <= 0) {
                request.setBatchSize(10); // 默认生成10个
            }
            
            // 执行批量ID生成
            IdGenerateResponse response = generateIdCmdExe.execute(request);
            
            // 将结果转换为列表响应
            List<IdGenerateResponse> responseList = new ArrayList<>();
            if (response.getIdList() != null && !response.getIdList().isEmpty()) {
                for (Long id : response.getIdList()) {
                    IdGenerateResponse singleResponse = new IdGenerateResponse();
                    singleResponse.setId(id);
                    singleResponse.setDataCenterId(response.getDataCenterId());
                    singleResponse.setMachineId(response.getMachineId());
                    singleResponse.setTimestamp(response.getTimestamp());
                    responseList.add(singleResponse);
                }
            } else {
                responseList.add(response);
            }
            
            return MultiResponse.of(responseList);
        } catch (Exception e) {
            return MultiResponse.buildFailure("GENERATE_BATCH_IDS_ERROR", e.getMessage());
        }
    }
    
    @Override
    public SingleResponse<IdGenerateResponse> parseId(Long id) {
        try {
            // 解析ID
            IdGenerateResponse response = generateIdCmdExe.parseId(id);
            return SingleResponse.of(response);
        } catch (Exception e) {
            return SingleResponse.buildFailure("PARSE_ID_ERROR", e.getMessage());
        }
    }
    
    @Override
    public SingleResponse<Boolean> registerMachine(Long dataCenterId) {
        try {
            // 获取本机IP和MAC地址
            String ip = getLocalIp();
            String mac = getLocalMac();
            
            // 查询是否已存在该机器
            MachineNode existingNode = machineNodeGateway.findByMac(mac);
            if (existingNode != null) {
                // 如果已存在，更新状态为在线
                existingNode.online();
                machineNodeGateway.update(existingNode);
                currentMachineNode = existingNode;
                return SingleResponse.of(true);
            }
            
            // 分配新的机器ID
            Long machineId = machineNodeGateway.allocateMachineId(dataCenterId);
            
            // 创建新的机器节点
            MachineNode machineNode = new MachineNode();
            machineNode.setCenterId(dataCenterId);
            machineNode.setMachineId(machineId);
            machineNode.setIp(ip);
            machineNode.setMac(mac);
            machineNode.online(); // 设置为在线状态
            
            // 保存机器节点
            MachineNode savedNode = machineNodeGateway.save(machineNode);
            currentMachineNode = savedNode;
            
            return SingleResponse.of(true);
        } catch (Exception e) {
            return SingleResponse.buildFailure("REGISTER_MACHINE_ERROR", e.getMessage());
        }
    }
    
    @Override
    public SingleResponse<Boolean> updateMachineOnline() {
        try {
            setupCurrentMachineIfNeeded();
            
            // 更新状态为在线
            machineNodeGateway.updateStatus(currentMachineNode.getMachineId(), MachineStatusEnum.ONLINE);
            return SingleResponse.of(true);
        } catch (Exception e) {
            return SingleResponse.buildFailure("UPDATE_MACHINE_ONLINE_ERROR", e.getMessage());
        }
    }
    
    @Override
    public SingleResponse<Boolean> updateMachineOffline() {
        try {
            setupCurrentMachineIfNeeded();
            
            // 更新状态为离线
            machineNodeGateway.updateStatus(currentMachineNode.getMachineId(), MachineStatusEnum.OFFLINE);
            return SingleResponse.of(true);
        } catch (Exception e) {
            return SingleResponse.buildFailure("UPDATE_MACHINE_OFFLINE_ERROR", e.getMessage());
        }
    }
    
    /**
     * 确保当前机器节点已经初始化
     */
    private void setupCurrentMachineIfNeeded() throws Exception {
        if (currentMachineNode == null) {
            String mac = getLocalMac();
            MachineNode machineNode = machineNodeGateway.findByMac(mac);
            if (machineNode == null) {
                throw new IllegalStateException("当前机器未注册，请先调用registerMachine方法");
            }
            currentMachineNode = machineNode;
        }
    }
    
    /**
     * 获取本机IP地址
     */
    private String getLocalIp() throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        return localHost.getHostAddress();
    }
    
    /**
     * 获取本机MAC地址
     */
    private String getLocalMac() throws Exception {
        StringBuilder sb = new StringBuilder();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface != null && !networkInterface.isLoopback() && !networkInterface.isVirtual()) {
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    break;
                }
            }
        }
        
        return sb.toString();
    }
} 
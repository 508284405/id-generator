package com.leyue.id.generator.gateway;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leyue.id.domain.generator.enums.MachineStatusEnum;
import com.leyue.id.domain.generator.gateway.MachineNodeGateway;
import com.leyue.id.domain.generator.model.MachineNode;
import com.leyue.id.generator.convertor.MachineNodeConvertor;
import com.leyue.id.generator.dataobject.MachineInfoDO;
import com.leyue.id.generator.dataobject.MachineStatusLogDO;
import com.leyue.id.generator.mapper.MachineInfoMapper;
import com.leyue.id.generator.mapper.MachineStatusLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * 机器节点网关实现类
 */
@Component
@RequiredArgsConstructor
public class MachineNodeGatewayImpl implements MachineNodeGateway {
    
    private final MachineInfoMapper machineInfoMapper;
    
    private final MachineStatusLogMapper machineStatusLogMapper;
    
    private final MachineNodeConvertor machineNodeConvertor;
    
    @Override
    public MachineNode save(MachineNode machineNode) {
        MachineInfoDO machineInfoDO = machineNodeConvertor.toDO(machineNode);
        machineInfoMapper.insert(machineInfoDO);
        machineNode.setId(machineInfoDO.getId());
        // 记录状态变更日志
        logStatusChange(machineNode, machineNode.getStatus());
        return machineNode;
    }
    
    @Override
    public MachineNode findById(Long id) {
        MachineInfoDO machineInfoDO = machineInfoMapper.selectById(id);
        if (machineInfoDO == null) {
            return null;
        }
        return machineNodeConvertor.toEntity(machineInfoDO);
    }
    
    @Override
    public MachineNode findByMachineId(Long machineId) {
        MachineInfoDO machineInfoDO = machineInfoMapper.selectByMachineId(machineId);
        if (machineInfoDO == null) {
            return null;
        }
        return machineNodeConvertor.toEntity(machineInfoDO);
    }
    
    @Override
    public MachineNode findByMac(String mac) {
        MachineInfoDO machineInfoDO = machineInfoMapper.selectByMac(mac);
        if (machineInfoDO == null) {
            return null;
        }
        return machineNodeConvertor.toEntity(machineInfoDO);
    }
    
    @Override
    public MachineNode findByIp(String ip) {
        MachineInfoDO machineInfoDO = machineInfoMapper.selectByIp(ip);
        if (machineInfoDO == null) {
            return null;
        }
        return machineNodeConvertor.toEntity(machineInfoDO);
    }
    
    @Override
    public List<MachineNode> findByCenterId(Long centerId) {
        List<MachineInfoDO> machineInfoDOList = machineInfoMapper.selectByCenterId(centerId);
        return machineNodeConvertor.toEntityList(machineInfoDOList);
    }
    
    @Override
    public List<MachineNode> findByStatus(MachineStatusEnum status) {
        List<MachineInfoDO> machineInfoDOList = machineInfoMapper.selectByStatus(status.name());
        return machineNodeConvertor.toEntityList(machineInfoDOList);
    }
    
    @Override
    public List<MachineNode> findAll() {
        List<MachineInfoDO> machineInfoDOList = machineInfoMapper.selectList(new LambdaQueryWrapper<>());
        return machineNodeConvertor.toEntityList(machineInfoDOList);
    }
    
    @Override
    public MachineNode update(MachineNode machineNode) {
        MachineInfoDO machineInfoDO = machineNodeConvertor.toDO(machineNode);
        machineInfoMapper.updateById(machineInfoDO);
        // 记录状态变更日志
        logStatusChange(machineNode, machineNode.getStatus());
        return machineNode;
    }
    
    @Override
    public MachineNode updateStatus(Long machineId, MachineStatusEnum status) {
        machineInfoMapper.updateStatus(machineId, status.name());
        MachineNode machineNode = findByMachineId(machineId);
        // 记录状态变更日志
        logStatusChange(machineNode, status);
        return machineNode;
    }
    
    @Override
    public void logStatusChange(MachineNode machineNode, MachineStatusEnum status) {
        MachineStatusLogDO logDO = machineNodeConvertor.toStatusLogDO(machineNode, status);
        machineStatusLogMapper.insert(logDO);
    }
    
    @Override
    public Long allocateMachineId(Long centerId) {
        // 获取当前数据中心最大的机器ID
        Long maxMachineId = machineInfoMapper.selectMaxMachineId(centerId);
        if (maxMachineId == null) {
            return 1L; // 如果没有分配过机器，则从1开始
        }
        return maxMachineId + 1; // 否则返回最大值+1
    }
    
    @Override
    public void delete(Long id) {
        machineInfoMapper.deleteById(id);
    }
} 
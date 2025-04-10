package com.leyue.id.generator.convertor;

import com.leyue.id.domain.generator.enums.MachineStatusEnum;
import com.leyue.id.domain.generator.model.MachineNode;
import com.leyue.id.generator.dataobject.MachineInfoDO;
import com.leyue.id.generator.dataobject.MachineStatusLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 机器节点转换器
 */
@Mapper
public interface MachineNodeConvertor {
    
    MachineNodeConvertor INSTANCE = Mappers.getMapper(MachineNodeConvertor.class);
    
    /**
     * DO转换为领域模型
     *
     * @param machineInfoDO 机器信息DO
     * @return 机器节点领域模型
     */
    @Mapping(source = "status", target = "status", qualifiedByName = "statusToEnum")
    MachineNode toEntity(MachineInfoDO machineInfoDO);
    
    /**
     * 领域模型转换为DO
     *
     * @param machineNode 机器节点领域模型
     * @return 机器信息DO
     */
    @Mapping(source = "status", target = "status", qualifiedByName = "enumToStatus")
    MachineInfoDO toDO(MachineNode machineNode);
    
    /**
     * DO列表转换为领域模型列表
     *
     * @param machineInfoDOList 机器信息DO列表
     * @return 机器节点领域模型列表
     */
    List<MachineNode> toEntityList(List<MachineInfoDO> machineInfoDOList);
    
    /**
     * 领域模型转换为状态日志DO
     *
     * @param machineNode 机器节点领域模型
     * @param status 状态
     * @return 机器状态日志DO
     */
    @Mapping(source = "machineNode.machineId", target = "machineId")
    @Mapping(source = "machineNode.centerId", target = "centerId")
    @Mapping(source = "machineNode.ip", target = "ip")
    @Mapping(source = "machineNode.mac", target = "mac")
    @Mapping(source = "status", target = "status", qualifiedByName = "enumToStatus")
    @Mapping(target = "timestamp", expression = "java(new java.util.Date())")
    @Mapping(target = "id", ignore = true)
    MachineStatusLogDO toStatusLogDO(MachineNode machineNode, MachineStatusEnum status);
    
    /**
     * 状态字符串转枚举
     *
     * @param status 状态字符串
     * @return 状态枚举
     */
    @Named("statusToEnum")
    default MachineStatusEnum statusToEnum(String status) {
        if (status == null) {
            return MachineStatusEnum.OFFLINE;
        }
        return MachineStatusEnum.valueOf(status);
    }
    
    /**
     * 状态枚举转字符串
     *
     * @param status 状态枚举
     * @return 状态字符串
     */
    @Named("enumToStatus")
    default String enumToStatus(MachineStatusEnum status) {
        if (status == null) {
            return MachineStatusEnum.OFFLINE.name();
        }
        return status.name();
    }
} 
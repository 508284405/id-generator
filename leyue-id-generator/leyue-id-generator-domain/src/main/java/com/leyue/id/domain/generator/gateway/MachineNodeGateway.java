package com.leyue.id.domain.generator.gateway;

import com.leyue.id.domain.generator.enums.MachineStatusEnum;
import com.leyue.id.domain.generator.model.MachineNode;

import java.util.List;

/**
 * 机器节点仓储网关接口
 */
public interface MachineNodeGateway {
    
    /**
     * 保存机器节点
     *
     * @param machineNode 机器节点
     * @return 保存后的机器节点
     */
    MachineNode save(MachineNode machineNode);
    
    /**
     * 根据ID查询机器节点
     *
     * @param id 主键ID
     * @return 机器节点
     */
    MachineNode findById(Long id);
    
    /**
     * 根据机器ID查询机器节点
     *
     * @param machineId 机器ID
     * @return 机器节点
     */
    MachineNode findByMachineId(Long machineId);
    
    /**
     * 根据MAC地址查询机器节点
     *
     * @param mac MAC地址
     * @return 机器节点
     */
    MachineNode findByMac(String mac);
    
    /**
     * 根据IP地址查询机器节点
     *
     * @param ip IP地址
     * @return 机器节点
     */
    MachineNode findByIp(String ip);
    
    /**
     * 根据数据中心ID查询机器节点列表
     *
     * @param centerId 数据中心ID
     * @return 机器节点列表
     */
    List<MachineNode> findByCenterId(Long centerId);
    
    /**
     * 根据状态查询机器节点列表
     *
     * @param status 机器状态
     * @return 机器节点列表
     */
    List<MachineNode> findByStatus(MachineStatusEnum status);
    
    /**
     * 查询所有机器节点
     *
     * @return 机器节点列表
     */
    List<MachineNode> findAll();
    
    /**
     * 更新机器节点
     *
     * @param machineNode 机器节点
     * @return 更新后的机器节点
     */
    MachineNode update(MachineNode machineNode);
    
    /**
     * 更新机器状态
     *
     * @param machineId 机器ID
     * @param status 机器状态
     * @return 更新后的机器节点
     */
    MachineNode updateStatus(Long machineId, MachineStatusEnum status);
    
    /**
     * 记录机器状态变更日志
     *
     * @param machineNode 机器节点
     * @param status 变更后的状态
     */
    void logStatusChange(MachineNode machineNode, MachineStatusEnum status);
    
    /**
     * 分配新的机器ID
     *
     * @param centerId 数据中心ID
     * @return 分配的机器ID
     */
    Long allocateMachineId(Long centerId);
    
    /**
     * 删除机器节点
     *
     * @param id 主键ID
     */
    void delete(Long id);
} 
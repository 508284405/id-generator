package com.leyue.id.api.generator;


import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageQuery;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.dto.generator.MachineNodeDTO;
import com.leyue.id.dto.generator.MachineStatusLogDTO;

/**
 * 机器节点服务接口
 */
public interface MachineNodeService {
    
    /**
     * 创建机器节点
     *
     * @param machineNodeDTO 机器节点DTO
     * @return 创建结果
     */
    SingleResponse<MachineNodeDTO> createMachineNode(MachineNodeDTO machineNodeDTO);
    
    /**
     * 更新机器节点
     *
     * @param machineNodeDTO 机器节点DTO
     * @return 更新结果
     */
    SingleResponse<MachineNodeDTO> updateMachineNode(MachineNodeDTO machineNodeDTO);
    
    /**
     * 删除机器节点
     *
     * @param id 机器节点ID
     * @return 删除结果
     */
    SingleResponse<Boolean> deleteMachineNode(Long id);
    
    /**
     * 根据ID查询机器节点
     *
     * @param id 主键ID
     * @return 机器节点
     */
    SingleResponse<MachineNodeDTO> getMachineNodeById(Long id);
    
    /**
     * 根据机器ID查询机器节点
     *
     * @param machineId 机器ID
     * @return 机器节点
     */
    SingleResponse<MachineNodeDTO> getMachineNodeByMachineId(Long machineId);
    
    /**
     * 根据MAC地址查询机器节点
     *
     * @param mac MAC地址
     * @return 机器节点
     */
    SingleResponse<MachineNodeDTO> getMachineNodeByMac(String mac);
    
    /**
     * 根据IP地址查询机器节点
     *
     * @param ip IP地址
     * @return 机器节点
     */
    SingleResponse<MachineNodeDTO> getMachineNodeByIp(String ip);
    
    /**
     * 根据数据中心ID查询机器节点列表
     *
     * @param centerId 数据中心ID
     * @return 机器节点列表
     */
    MultiResponse<MachineNodeDTO> getMachineNodesByCenterId(Long centerId);
    
    /**
     * 根据状态查询机器节点列表
     *
     * @param status 机器状态
     * @return 机器节点列表
     */
    MultiResponse<MachineNodeDTO> getMachineNodesByStatus(String status);
    
    /**
     * 查询所有机器节点
     *
     * @return 机器节点列表
     */
    MultiResponse<MachineNodeDTO> getAllMachineNodes();
    
    /**
     * 分页查询机器节点
     *
     * @param pageQuery 分页查询参数
     * @return 分页机器节点列表
     */
    PageResponse<MachineNodeDTO> pageMachineNodes(PageQuery pageQuery);
    
    /**
     * 更新机器状态
     *
     * @param machineId 机器ID
     * @param status 机器状态
     * @return 更新结果
     */
    SingleResponse<Boolean> updateMachineStatus(Long machineId, String status);
    
    /**
     * 查询机器状态日志
     *
     * @param machineId 机器ID
     * @return 状态日志列表
     */
    MultiResponse<MachineStatusLogDTO> getMachineStatusLogs(Long machineId);
} 
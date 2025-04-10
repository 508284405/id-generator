package com.leyue.id.api.generator;

import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.dto.generator.MachineNodeDTO;

/**
 * 机器注册服务接口
 */
public interface MachineRegistrationService {
    
    /**
     * 注册当前机器到指定数据中心
     * 
     * @param dataCenterName 数据中心名称
     * @return 注册后的机器节点信息
     */
    SingleResponse<MachineNodeDTO> registerMachine(String dataCenterName);
    
    /**
     * 获取本机信息
     * 
     * @return 本机信息
     */
    SingleResponse<MachineNodeDTO> getLocalMachineInfo();

    /**
     * 将当前机器标记为离线状态
     * 
     * @return 操作结果
     */
    SingleResponse<Boolean> markMachineOffline();
}
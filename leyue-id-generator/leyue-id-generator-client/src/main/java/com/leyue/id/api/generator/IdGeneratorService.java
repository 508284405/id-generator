package com.leyue.id.api.generator;


import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.dto.generator.IdGenerateRequest;
import com.leyue.id.dto.generator.IdGenerateResponse;

/**
 * ID生成器服务接口
 */
public interface IdGeneratorService {
    
    /**
     * 生成单个ID
     *
     * @param request ID生成请求
     * @return ID生成响应
     */
    SingleResponse<IdGenerateResponse> generateId(IdGenerateRequest request);
    
    /**
     * 批量生成ID
     *
     * @param request ID生成请求
     * @return ID生成响应列表
     */
    MultiResponse<IdGenerateResponse> generateBatchIds(IdGenerateRequest request);
    
    /**
     * 解析ID
     *
     * @param id 要解析的ID
     * @return ID解析结果
     */
    SingleResponse<IdGenerateResponse> parseId(Long id);
    
    /**
     * 注册当前机器
     *
     * @param dataCenterId 数据中心ID
     * @return 注册结果
     */
    SingleResponse<Boolean> registerMachine(Long dataCenterId);
    
    /**
     * 更新当前机器状态为在线
     *
     * @return 更新结果
     */
    SingleResponse<Boolean> updateMachineOnline();
    
    /**
     * 更新当前机器状态为离线
     *
     * @return 更新结果
     */
    SingleResponse<Boolean> updateMachineOffline();
} 
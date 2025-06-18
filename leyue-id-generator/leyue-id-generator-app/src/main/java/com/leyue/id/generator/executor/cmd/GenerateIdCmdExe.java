package com.leyue.id.generator.executor.cmd;

import com.leyue.id.domain.generator.model.IdGenerator;
import com.leyue.id.dto.generator.IdGenerateRequest;
import com.leyue.id.dto.generator.IdGenerateResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ID生成命令执行器
 */
@Component
public class GenerateIdCmdExe {
    
    /**
     * ID生成器实例（单例）
     */
    private IdGenerator idGenerator;
    
    /**
     * 执行ID生成
     *
     * @param request ID生成请求
     * @return ID生成响应
     */
    public IdGenerateResponse execute(IdGenerateRequest request) {
        // 初始化ID生成器，确保只初始化一次
        if (idGenerator == null) {
            synchronized (this) {
                if (idGenerator == null) {
                    Long dataCenterId = request.getDataCenterId();
                    Long machineId = request.getMachineId();
                    
                    // 参数校验
                    if (dataCenterId == null || machineId == null) {
                        throw new IllegalArgumentException("数据中心ID和机器ID不能为空");
                    }
                    
                    // 创建ID生成器
                    idGenerator = new IdGenerator(dataCenterId, machineId);
                }
            }
        }
        
        // 生成ID
        long id = idGenerator.nextId();
        
        // 构建响应
        IdGenerateResponse response = new IdGenerateResponse();
        response.setId(id);
        response.setDataCenterId(request.getDataCenterId());
        response.setMachineId(request.getMachineId());
        response.setTimestamp(System.currentTimeMillis());
        
        // 批量生成ID
        if (request.getBatchSize() != null && request.getBatchSize() > 1) {
            List<Long> idList = new ArrayList<>();
            idList.add(id); // 添加第一个ID
            
            // 生成剩余的ID
            for (int i = 1; i < request.getBatchSize(); i++) {
                idList.add(idGenerator.nextId());
            }
            
            response.setIdList(idList);
        }
        
        return response;
    }
    
    /**
     * 解析ID
     *
     * @param id 要解析的ID
     * @return 解析结果
     */
    public IdGenerateResponse parseId(Long id) {
        // 解析参数
        long timestamp = id >> 22;
        long dataCenterId = (id >> 12) & 0x3FF; // 取10位
        long machineId = (id >> 2) & 0x3FF; // 取10位
        long sequence = id & 0x3; // 取2位
        
        // 构建响应
        IdGenerateResponse response = new IdGenerateResponse();
        response.setId(id);
        response.setDataCenterId(dataCenterId);
        response.setMachineId(machineId);
        response.setTimestamp(timestamp);
        
        return response;
    }
} 
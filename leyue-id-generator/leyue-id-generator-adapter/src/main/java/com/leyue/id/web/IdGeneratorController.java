package com.leyue.id.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.api.generator.IdGeneratorService;
import com.leyue.id.dto.generator.IdGenerateRequest;
import com.leyue.id.dto.generator.IdGenerateResponse;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * ID生成器控制器
 */
@RestController
@RequestMapping("/api/generator")
public class IdGeneratorController {
    
    @Resource
    private IdGeneratorService idGeneratorService;
    
    /**
     * 生成单个ID
     *
     * @param request ID生成请求
     * @return ID生成响应
     */
    @PostMapping("/id")
    public SingleResponse<IdGenerateResponse> generateId(@RequestBody IdGenerateRequest request) {
        return idGeneratorService.generateId(request);
    }
    
    /**
     * 批量生成ID
     *
     * @param request ID生成请求
     * @return ID生成响应列表
     */
    @PostMapping("/ids/batch")
    public MultiResponse<IdGenerateResponse> generateBatchIds(@RequestBody IdGenerateRequest request) {
        return idGeneratorService.generateBatchIds(request);
    }
    
    /**
     * 解析ID
     *
     * @param id 要解析的ID
     * @return ID解析结果
     */
    @GetMapping("/id/parse/{id}")
    public SingleResponse<IdGenerateResponse> parseId(@PathVariable("id") Long id) {
        return idGeneratorService.parseId(id);
    }
    
    /**
     * 注册当前机器
     *
     * @param dataCenterId 数据中心ID
     * @return 注册结果
     */
    @PostMapping("/machine/register/{dataCenterId}")
    public SingleResponse<Boolean> registerMachine(@PathVariable("dataCenterId") Long dataCenterId) {
        return idGeneratorService.registerMachine(dataCenterId);
    }
    
    /**
     * 更新当前机器状态为在线
     *
     * @return 更新结果
     */
    @PostMapping("/machine/online")
    public SingleResponse<Boolean> updateMachineOnline() {
        return idGeneratorService.updateMachineOnline();
    }
    
    /**
     * 更新当前机器状态为离线
     *
     * @return 更新结果
     */
    @PostMapping("/machine/offline")
    public SingleResponse<Boolean> updateMachineOffline() {
        return idGeneratorService.updateMachineOffline();
    }
} 
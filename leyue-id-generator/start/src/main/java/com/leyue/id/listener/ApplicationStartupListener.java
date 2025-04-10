package com.leyue.id.listener;

import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.api.generator.MachineRegistrationService;
import com.leyue.id.config.DataCenterConfig;
import com.leyue.id.dto.generator.MachineNodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 应用启动监听器，自动注册机器ID
 */
@Slf4j
@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationStartedEvent> {

    @Resource
    private MachineRegistrationService machineRegistrationService;

    @Resource
    private DataCenterConfig dataCenterConfig;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("应用启动完成，开始注册机器ID");
        
        try {
            // 获取数据中心名称
            String dataCenterName = dataCenterConfig.getName();
            log.info("使用数据中心: {}", dataCenterName);
            
            // 注册机器
            SingleResponse<MachineNodeDTO> response = machineRegistrationService.registerMachine(dataCenterName);
            
            if (response.isSuccess()) {
                MachineNodeDTO machineNode = response.getData();
                log.info("机器注册成功 - 数据中心: {}, 机器ID: {}, IP: {}, MAC: {}", 
                        dataCenterName, machineNode.getMachineId(), machineNode.getIp(), machineNode.getMac());
            } else {
                String errorMsg = "机器注册失败: " + response.getErrMessage();
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
        } catch (Exception e) {
            log.error("机器注册过程出现异常", e);
            // 抛出异常导致应用启动失败
            throw new RuntimeException("机器ID注册失败，应用启动中止: " + e.getMessage(), e);
        }
    }
} 
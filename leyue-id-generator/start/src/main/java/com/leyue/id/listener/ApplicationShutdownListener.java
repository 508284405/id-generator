package com.leyue.id.listener;

import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.api.generator.MachineRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;


/**
 * 应用关闭监听器，自动将机器标记为离线状态
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationShutdownListener implements ApplicationListener<ContextClosedEvent> {
    private final MachineRegistrationService machineRegistrationService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("应用准备关闭，开始将机器标记为离线状态");

        try {
            SingleResponse<Boolean> response = machineRegistrationService.markMachineOffline();

            if (response.isSuccess()) {
                log.info("机器已成功标记为离线状态");
            } else {
                String errorMsg = "将机器标记为离线状态失败: " + response.getErrMessage();
                log.error(errorMsg);
            }
        } catch (Exception e) {
            log.error("将机器标记为离线状态时出现异常", e);
        }
    }
}
package com.leyue.id.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * 数据中心配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "id-generator.datacenter")
public class DataCenterConfig {

    /**
     * 数据中心名称，默认为default
     */
    private String name = "default";
} 
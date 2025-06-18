package com.leyue.id.dto.generator;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据中心DTO
 */
@Data
public class DataCenterDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 数据中心ID（全局唯一）
     */
    private Long centerId;
    
    /**
     * 数据中心名称
     */
    private String name;
    
    /**
     * 数据中心所在区域
     */
    private String region;
} 
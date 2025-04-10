package com.leyue.id.domain.generator.model;

/**
 * 数据中心领域模型
 */
public class DataCenter {
    
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
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCenterId() {
        return centerId;
    }
    
    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
} 
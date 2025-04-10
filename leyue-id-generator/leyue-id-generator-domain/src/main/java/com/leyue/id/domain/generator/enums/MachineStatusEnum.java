package com.leyue.id.domain.generator.enums;

/**
 * 机器状态枚举
 */
public enum MachineStatusEnum {
    
    /**
     * 在线状态
     */
    ONLINE("ONLINE", "在线"),
    
    /**
     * 离线状态
     */
    OFFLINE("OFFLINE", "离线");
    
    private final String code;
    private final String desc;
    
    MachineStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
} 
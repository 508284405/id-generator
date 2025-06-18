package com.leyue.id.dto.generator;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 机器状态日志DTO
 */
@Data
public class MachineStatusLogDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 机器节点ID
     */
    private Long machineId;
    
    /**
     * 数据中心ID
     */
    private Long centerId;
    
    /**
     * 机器状态
     */
    private String status;
    
    /**
     * 状态变更时间
     */
    private Date timestamp;
    
    /**
     * 机器IP地址
     */
    private String ip;
    
    /**
     * 机器MAC地址
     */
    private String mac;
} 
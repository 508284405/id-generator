package com.leyue.id.dto.generator;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 机器节点DTO
 */
@Data
public class MachineNodeDTO implements Serializable {
    
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
     * 机器IP地址
     */
    private String ip;
    
    /**
     * 机器MAC地址
     */
    private String mac;
    
    /**
     * 机器状态（ONLINE/OFFLINE）
     */
    private String status;
    
    /**
     * 最后上线时间
     */
    private Date lastOnlineTime;
    
    /**
     * 最后下线时间
     */
    private Date lastOfflineTime;
} 
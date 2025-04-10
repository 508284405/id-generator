package com.leyue.id.domain.generator.model;

import com.leyue.id.domain.generator.enums.MachineStatusEnum;
import java.util.Date;

/**
 * 机器节点领域模型
 */
public class MachineNode {
    
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
     * 机器状态
     */
    private MachineStatusEnum status;
    
    /**
     * 最后上线时间
     */
    private Date lastOnlineTime;
    
    /**
     * 最后下线时间
     */
    private Date lastOfflineTime;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMachineId() {
        return machineId;
    }
    
    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
    
    public Long getCenterId() {
        return centerId;
    }
    
    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getMac() {
        return mac;
    }
    
    public void setMac(String mac) {
        this.mac = mac;
    }
    
    public MachineStatusEnum getStatus() {
        return status;
    }
    
    public void setStatus(MachineStatusEnum status) {
        this.status = status;
    }
    
    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }
    
    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }
    
    public Date getLastOfflineTime() {
        return lastOfflineTime;
    }
    
    public void setLastOfflineTime(Date lastOfflineTime) {
        this.lastOfflineTime = lastOfflineTime;
    }
    
    /**
     * 更新机器状态为在线
     */
    public void online() {
        this.status = MachineStatusEnum.ONLINE;
        this.lastOnlineTime = new Date();
    }
    
    /**
     * 更新机器状态为离线
     */
    public void offline() {
        this.status = MachineStatusEnum.OFFLINE;
        this.lastOfflineTime = new Date();
    }
} 
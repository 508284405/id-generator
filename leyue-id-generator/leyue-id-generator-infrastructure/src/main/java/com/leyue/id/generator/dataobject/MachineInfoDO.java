package com.leyue.id.generator.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.leyue.id.dao.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * 机器信息数据对象
 */
@Data
@TableName("machine_info")
@EqualsAndHashCode(callSuper = true)
public class MachineInfoDO extends BaseDO {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 机器节点ID
     */
    @TableField("machine_id")
    private Long machineId;
    
    /**
     * 数据中心ID
     */
    @TableField("center_id")
    private Long centerId;
    
    /**
     * 机器IP地址
     */
    @TableField("ip")
    private String ip;
    
    /**
     * 机器MAC地址
     */
    @TableField("mac")
    private String mac;
    
    /**
     * 机器状态（ONLINE/OFFLINE）
     */
    @TableField("status")
    private String status;
    
    /**
     * 最后上线时间
     */
    @TableField("last_online_time")
    private Date lastOnlineTime;
    
    /**
     * 最后下线时间
     */
    @TableField("last_offline_time")
    private Date lastOfflineTime;
} 
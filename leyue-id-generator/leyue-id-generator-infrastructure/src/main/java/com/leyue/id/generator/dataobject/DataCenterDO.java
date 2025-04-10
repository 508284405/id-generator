package com.leyue.id.generator.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.leyue.id.dao.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据中心数据对象
 */
@Data
@TableName("data_center")
@EqualsAndHashCode(callSuper = true)
public class DataCenterDO extends BaseDO {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 数据中心ID（全局唯一）
     */
    @TableField("center_id")
    private Long centerId;
    
    /**
     * 数据中心名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 数据中心所在区域
     */
    @TableField("region")
    private String region;
} 
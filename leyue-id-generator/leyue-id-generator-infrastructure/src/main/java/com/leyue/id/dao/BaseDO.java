package com.leyue.id.dao;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableLogic
    private Integer isDeleted = 0;
    
    private String createdBy;
    
    private String updatedBy;
    
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}
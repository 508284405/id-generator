package com.leyue.id.dto.generator;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * ID生成响应DTO
 */
@Data
public class IdGenerateResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 生成的单个ID
     */
    private Long id;
    
    /**
     * 批量生成的ID列表
     */
    private List<Long> idList;
    
    /**
     * 使用的数据中心ID
     */
    private Long dataCenterId;
    
    /**
     * 使用的机器ID
     */
    private Long machineId;
    
    /**
     * 生成的时间戳
     */
    private Long timestamp;
} 
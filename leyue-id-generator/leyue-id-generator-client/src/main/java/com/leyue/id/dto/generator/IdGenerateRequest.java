package com.leyue.id.dto.generator;

import lombok.Data;
import java.io.Serializable;

/**
 * ID生成请求DTO
 */
@Data
public class IdGenerateRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 批量生成ID的数量，默认为1
     */
    private Integer batchSize = 1;
    
    /**
     * 数据中心ID，如果不传则使用当前机器注册的数据中心ID
     */
    private Long dataCenterId;
    
    /**
     * 机器ID，如果不传则使用当前机器的机器ID
     */
    private Long machineId;
} 
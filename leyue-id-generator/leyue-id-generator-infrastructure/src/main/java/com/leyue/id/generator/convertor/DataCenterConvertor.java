package com.leyue.id.generator.convertor;

import com.leyue.id.domain.generator.model.DataCenter;
import com.leyue.id.generator.dataobject.DataCenterDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 数据中心转换器
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DataCenterConvertor {
    /**
     * DO转换为领域模型
     *
     * @param dataCenterDO 数据中心DO
     * @return 数据中心领域模型
     */
    DataCenter toEntity(DataCenterDO dataCenterDO);
    
    /**
     * 领域模型转换为DO
     *
     * @param dataCenter 数据中心领域模型
     * @return 数据中心DO
     */
    DataCenterDO toDO(DataCenter dataCenter);
    
    /**
     * DO列表转换为领域模型列表
     *
     * @param dataCenterDOList 数据中心DO列表
     * @return 数据中心领域模型列表
     */
    List<DataCenter> toEntityList(List<DataCenterDO> dataCenterDOList);
} 
package com.leyue.id.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyue.id.generator.dataobject.DataCenterDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 数据中心Mapper接口
 */
@Mapper
public interface DataCenterMapper extends BaseMapper<DataCenterDO> {
    
    /**
     * 根据数据中心ID查询
     *
     * @param centerId 数据中心ID
     * @return 数据中心DO
     */
    @Select("SELECT * FROM data_center WHERE center_id = #{centerId}")
    DataCenterDO selectByCenterId(@Param("centerId") Long centerId);
    
    /**
     * 根据数据中心名称查询
     *
     * @param name 数据中心名称
     * @return 数据中心DO
     */
    @Select("SELECT * FROM data_center WHERE name = #{name}")
    DataCenterDO selectByName(@Param("name") String name);
} 
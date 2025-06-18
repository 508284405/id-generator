package com.leyue.id.domain.generator.gateway;

import com.leyue.id.domain.generator.model.DataCenter;

import java.util.List;

/**
 * 数据中心仓储网关接口
 */
public interface DataCenterGateway {
    
    /**
     * 保存数据中心
     *
     * @param dataCenter 数据中心
     * @return 保存后的数据中心
     */
    DataCenter save(DataCenter dataCenter);
    
    /**
     * 根据ID查询数据中心
     *
     * @param id 数据中心ID
     * @return 数据中心
     */
    DataCenter findById(Long id);
    
    /**
     * 根据中心ID查询数据中心
     *
     * @param centerId 中心ID
     * @return 数据中心
     */
    DataCenter findByCenterId(Long centerId);
    
    /**
     * 根据中心名称查询数据中心
     *
     * @param name 中心名称
     * @return 数据中心
     */
    DataCenter findByName(String name);
    
    /**
     * 查询所有数据中心
     *
     * @return 数据中心列表
     */
    List<DataCenter> findAll();
    
    /**
     * 更新数据中心
     *
     * @param dataCenter 数据中心
     * @return 更新后的数据中心
     */
    DataCenter update(DataCenter dataCenter);
    
    /**
     * 删除数据中心
     *
     * @param id 数据中心ID
     */
    void delete(Long id);
} 
package com.leyue.id.api.generator;


import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageQuery;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.leyue.id.dto.generator.DataCenterDTO;

/**
 * 数据中心服务接口
 */
public interface DataCenterService {
    
    /**
     * 创建数据中心
     *
     * @param dataCenterDTO 数据中心DTO
     * @return 创建结果
     */
    SingleResponse<DataCenterDTO> createDataCenter(DataCenterDTO dataCenterDTO);
    
    /**
     * 更新数据中心
     *
     * @param dataCenterDTO 数据中心DTO
     * @return 更新结果
     */
    SingleResponse<DataCenterDTO> updateDataCenter(DataCenterDTO dataCenterDTO);
    
    /**
     * 删除数据中心
     *
     * @param id 数据中心ID
     * @return 删除结果
     */
    SingleResponse<Boolean> deleteDataCenter(Long id);
    
    /**
     * 根据ID查询数据中心
     *
     * @param id 数据中心ID
     * @return 数据中心
     */
    SingleResponse<DataCenterDTO> getDataCenterById(Long id);
    
    /**
     * 根据中心ID查询数据中心
     *
     * @param centerId 中心ID
     * @return 数据中心
     */
    SingleResponse<DataCenterDTO> getDataCenterByCenterId(Long centerId);
    
    /**
     * 根据名称查询数据中心
     *
     * @param name 数据中心名称
     * @return 数据中心
     */
    SingleResponse<DataCenterDTO> getDataCenterByName(String name);
    
    /**
     * 查询所有数据中心
     *
     * @return 数据中心列表
     */
    MultiResponse<DataCenterDTO> getAllDataCenters();
    
    /**
     * 分页查询数据中心
     *
     * @param pageQuery 分页查询参数
     * @return 分页数据中心列表
     */
    PageResponse<DataCenterDTO> pageDataCenters(PageQuery pageQuery);
} 
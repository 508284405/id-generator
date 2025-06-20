package com.leyue.id.generator.gateway;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leyue.id.domain.generator.gateway.DataCenterGateway;
import com.leyue.id.domain.generator.model.DataCenter;
import com.leyue.id.generator.convertor.DataCenterConvertor;
import com.leyue.id.generator.dataobject.DataCenterDO;
import com.leyue.id.generator.mapper.DataCenterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据中心网关实现类
 */
@Component
@RequiredArgsConstructor
public class DataCenterGatewayImpl implements DataCenterGateway {
    
    private final DataCenterMapper dataCenterMapper;
    private final DataCenterConvertor dataCenterConvertor;
    
    @Override
    public DataCenter save(DataCenter dataCenter) {
        DataCenterDO dataCenterDO = dataCenterConvertor.toDO(dataCenter);
        dataCenterMapper.insert(dataCenterDO);
        return dataCenterConvertor.toEntity(dataCenterDO);
    }
    
    @Override
    public DataCenter findById(Long id) {
        DataCenterDO dataCenterDO = dataCenterMapper.selectById(id);
        if (dataCenterDO == null) {
            return null;
        }
        return dataCenterConvertor.toEntity(dataCenterDO);
    }
    
    @Override
    public DataCenter findByCenterId(Long centerId) {
        DataCenterDO dataCenterDO = dataCenterMapper.selectByCenterId(centerId);
        if (dataCenterDO == null) {
            return null;
        }
        return dataCenterConvertor.toEntity(dataCenterDO);
    }
    
    @Override
    public DataCenter findByName(String name) {
        DataCenterDO dataCenterDO = dataCenterMapper.selectByName(name);
        if (dataCenterDO == null) {
            return null;
        }
        return dataCenterConvertor.toEntity(dataCenterDO);
    }
    
    @Override
    public List<DataCenter> findAll() {
        List<DataCenterDO> dataCenterDOList = dataCenterMapper.selectList(new LambdaQueryWrapper<>());
        return dataCenterConvertor.toEntityList(dataCenterDOList);
    }
    
    @Override
    public DataCenter update(DataCenter dataCenter) {
        DataCenterDO dataCenterDO = dataCenterConvertor.toDO(dataCenter);
        dataCenterMapper.updateById(dataCenterDO);
        return dataCenter;
    }
    
    @Override
    public void delete(Long id) {
        dataCenterMapper.deleteById(id);
    }
} 
package com.leyue.id.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyue.id.generator.dataobject.MachineStatusLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 机器状态日志Mapper接口
 */
@Mapper
public interface MachineStatusLogMapper extends BaseMapper<MachineStatusLogDO> {
    
    /**
     * 根据机器ID查询状态日志
     *
     * @param machineId 机器ID
     * @return 状态日志列表
     */
    @Select("SELECT * FROM machine_status_log WHERE machine_id = #{machineId} ORDER BY timestamp DESC")
    List<MachineStatusLogDO> selectByMachineId(@Param("machineId") Long machineId);
    
    /**
     * 根据数据中心ID查询状态日志
     *
     * @param centerId 数据中心ID
     * @return 状态日志列表
     */
    @Select("SELECT * FROM machine_status_log WHERE center_id = #{centerId} ORDER BY timestamp DESC")
    List<MachineStatusLogDO> selectByCenterId(@Param("centerId") Long centerId);
    
    /**
     * 根据时间范围查询状态日志
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 状态日志列表
     */
    @Select("SELECT * FROM machine_status_log WHERE timestamp BETWEEN #{startTime} AND #{endTime} ORDER BY timestamp DESC")
    List<MachineStatusLogDO> selectByTimeRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
} 
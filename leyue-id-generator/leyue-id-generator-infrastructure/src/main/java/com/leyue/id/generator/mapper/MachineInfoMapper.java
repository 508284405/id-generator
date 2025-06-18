package com.leyue.id.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyue.id.generator.dataobject.MachineInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 机器信息Mapper接口
 */
@Mapper
public interface MachineInfoMapper extends BaseMapper<MachineInfoDO> {
    
    /**
     * 根据机器ID查询
     *
     * @param machineId 机器ID
     * @return 机器信息DO
     */
    @Select("SELECT * FROM machine_info WHERE machine_id = #{machineId}")
    MachineInfoDO selectByMachineId(@Param("machineId") Long machineId);
    
    /**
     * 根据MAC地址查询
     *
     * @param mac MAC地址
     * @return 机器信息DO
     */
    @Select("SELECT * FROM machine_info WHERE mac = #{mac}")
    MachineInfoDO selectByMac(@Param("mac") String mac);
    
    /**
     * 根据IP地址查询
     *
     * @param ip IP地址
     * @return 机器信息DO
     */
    @Select("SELECT * FROM machine_info WHERE ip = #{ip}")
    MachineInfoDO selectByIp(@Param("ip") String ip);
    
    /**
     * 根据数据中心ID查询
     *
     * @param centerId 数据中心ID
     * @return 机器信息DO列表
     */
    @Select("SELECT * FROM machine_info WHERE center_id = #{centerId}")
    List<MachineInfoDO> selectByCenterId(@Param("centerId") Long centerId);
    
    /**
     * 根据状态查询
     *
     * @param status 状态
     * @return 机器信息DO列表
     */
    @Select("SELECT * FROM machine_info WHERE status = #{status}")
    List<MachineInfoDO> selectByStatus(@Param("status") String status);
    
    /**
     * 更新机器状态
     *
     * @param machineId 机器ID
     * @param status 状态
     * @return 影响的行数
     */
    @Update("UPDATE machine_info SET status = #{status} WHERE machine_id = #{machineId}")
    int updateStatus(@Param("machineId") Long machineId, @Param("status") String status);
    
    /**
     * 获取数据中心内已分配的最大机器ID
     *
     * @param centerId 数据中心ID
     * @return 最大机器ID
     */
    @Select("SELECT MAX(machine_id) FROM machine_info WHERE center_id = #{centerId}")
    Long selectMaxMachineId(@Param("centerId") Long centerId);
} 
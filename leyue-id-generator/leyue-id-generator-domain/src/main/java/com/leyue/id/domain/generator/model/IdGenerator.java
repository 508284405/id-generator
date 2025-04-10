package com.leyue.id.domain.generator.model;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 雪花算法ID生成器领域模型
 */
public class IdGenerator {
    
    /**
     * 起始时间戳，从2025-01-01 00:00:00开始
     */
    private final long epoch = 1735689600000L;
    
    /**
     * 数据中心ID占用的位数
     */
    private final long dataCenterIdBits = 5L;

    /**
     * 机器ID占用的位数
     */
    private final long machineIdBits = 5L;

    /**
     * 序列号占用的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 最大数据中心ID
     */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * 最大机器ID
     */
    private final long maxMachineId = -1L ^ (-1L << machineIdBits);

    /**
     * 序列号掩码
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 数据中心ID向左移位数
     */
    private final long dataCenterIdShift = machineIdBits + sequenceBits;

    /**
     * 机器ID向左移位数
     */
    private final long machineIdShift = sequenceBits;

    /**
     * 时间戳向左移位数
     */
    private final long timestampLeftShift = dataCenterIdBits + dataCenterIdShift;

    /**
     * 数据中心ID
     */
    private long dataCenterId;

    /**
     * 机器ID
     */
    private long machineId;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间戳
     */
    private long lastTimestamp = -1L;

    // 新增：网络时间偏差，单位毫秒
    private volatile long timeOffset = 0L;

    // 定时任务更新器
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * 构造方法
     *
     * @param dataCenterId 数据中心ID
     * @param machineId 机器ID
     */
    public IdGenerator(long dataCenterId, long machineId) {
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException("Data center ID is invalid: " + dataCenterId);
        }
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException("Machine ID is invalid: " + machineId);
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;

        // 启动定时任务，定时同步网络时间偏差（例如每1分钟）
        scheduler.scheduleAtFixedRate(this::syncTimeOffset, 0, 1, TimeUnit.MINUTES);
    }

    /**
     * 生成下一个ID
     *
     * @return 生成的ID
     */
    public synchronized long nextId() {
        long timestamp = currentTimeMillis();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for "
                    + (lastTimestamp - timestamp) + " milliseconds");
        }

        // 如果是同一时间生成的，则进行毫秒内序列号递增
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 同一毫秒内序列号用尽，等待下一毫秒
            if (sequence == 0) {
                timestamp = waitForNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列号重置为0
            sequence = 0L;
        }

        // 记录上次生成ID的时间戳
        lastTimestamp = timestamp;

        // 生成并返回ID
        return ((timestamp - epoch) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (machineId << machineIdShift)
                | sequence;
    }

    /**
     * 等待下一个毫秒的到来
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 下一个毫秒的时间戳
     */
    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 通过网络时间协议 (NTP) 同步本地时间与网络时间的偏差
     */
    private void syncTimeOffset() {
        try {
            NTPUDPClient client = new NTPUDPClient();
            client.setDefaultTimeout(3000);
            InetAddress hostAddr = InetAddress.getByName("pool.ntp.org");
            TimeInfo info = client.getTime(hostAddr);
            info.computeDetails();
            Long offset = info.getOffset();
            if (offset != null) {
                timeOffset = offset;
                // 可以添加日志记录同步成功
            }
        } catch (Exception e) {
            // 处理同步失败情况，保留现有偏差，或者记录日志
            e.printStackTrace();
        }
    }

    /**
     * 获取校正后的当前时间
     *
     * @return 网络校准后的当前毫秒时间戳
     */
    private long currentTimeMillis() {
        return System.currentTimeMillis() + timeOffset;
    }
}
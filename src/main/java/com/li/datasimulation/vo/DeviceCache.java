package com.li.datasimulation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 设备缓存
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class DeviceCache {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 上次压力
     */
    private double lastPressure;

    /**
     * 上次温度
     */
    private int lastTemperature;


}

package com.li.datasimulation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author lizhouquan
 * @description 功能描述
 * @create 2022/6/25 16:44
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class DeviceData {
    private String deviceId;
    private double pressure;
    private int temperature;
}

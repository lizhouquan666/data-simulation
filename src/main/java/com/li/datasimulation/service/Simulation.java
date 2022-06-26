package com.li.datasimulation.service;

import com.li.datasimulation.vo.DeviceCache;

/**
 * @author lizhouquan
 * @description 功能描述
 * @create 2022/6/25 19:27
 */
public interface Simulation {


    void prepareDeviceCache();

    void findDeviceCache();

    void updateDeviceCache();

    void maker(String id);

    //    @Value("${simulation.pressureRandom.pressure}")
//    private double pressure;
//    @Value("${simulation.pressureRandom.morePressure}")
//    private double morePressure;
//    @Value("${simulation.pressureRandom.changePressure}")
//    private double changePressure;
    double pressureRandom(DeviceCache deviceCache);

    int temperatureRandom();
}

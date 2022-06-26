package com.li.datasimulation.service.impl;

import com.li.datasimulation.service.Simulation;
import com.li.datasimulation.vo.DeviceCache;
import com.li.datasimulation.vo.DeviceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

/**
 * @Author: lizhouquan
 * @Description: 模拟10个设备每秒上传数据，每个设备的数据包含1设备号 2温度 3压力
 * @Date:  2022/6/26 12:25
 * @Version V1.0
 */
@Slf4j
@Service
public class SimulationImpl implements Simulation {

    //设备缓存数据
    private HashMap<String, DeviceCache> deviceCacheMap = new HashMap<>();
    //设备处理数据
    private HashMap<String, DeviceData> deviceDataMap = new HashMap<>();


    /**
     * @description 模拟10个设备初始数据
     *
     * TODO 1.设备初始化
     * 1.设备初始化
     */
    @Override
    public void prepareDeviceCache() {

        for (int i = 0; i < 10; i++) {
            deviceCacheMap.put(String.valueOf(i), DeviceCache.builder()
                    .deviceId(String.valueOf(i))
                    .lastPressure(0)
                    .lastTemperature(0)
                    .build());
        }
    }

    /**
     * TODO 2.取缓存
     * 2取缓存，找上次的压力 温度
     **/
    @Override
    public void findDeviceCache() {
        //判断是否有上次数据，如果没有则初始化数据
        if (deviceCacheMap.isEmpty()) {
            prepareDeviceCache();
        }else {
            //取出DeviceCache 数据放入DeviceData
            deviceCacheMap.forEach((key, value) -> {

                deviceDataMap.put(key, DeviceData.builder()
                        .deviceId(key)
                        .pressure(value.getLastPressure())
                        .temperature(value.getLastTemperature())
                        .build());
            });
        }
    }


    /**
     *
     * TODO 3.发送数据
     * 3发送数据
     * **/


    /**
     * TODO 4.更新缓存
     * 4 更新缓存
     * **/
    @Override
    public void updateDeviceCache() {
        //判断是否有数据，如果没有则初始化数据
        if (deviceDataMap.isEmpty()) {
            prepareDeviceCache();
        }else {
            //取出DeviceCache 数据放入DeviceData
            deviceDataMap.forEach((key, value) -> {

                deviceCacheMap.put(key, DeviceCache.builder()
                        .deviceId(key)
                        .lastPressure(value.getPressure())
                        .lastTemperature(value.getTemperature())
                        .build());
            });
        }
    }

    /**
     * 模拟递增压力
     *
     * @param lastPressure 上次压力读数  0
     * @param base         基准线  10
     * @param upperLimit   上限 20
     * @param lowerLimit   下限 -5
     * @return
     */

    private double simulatePressure(Double lastPressure, double base, double upperLimit, double lowerLimit) {

        if (null == lastPressure) {
            // todo


        } else {
            // todo
        }
        return 0.0;
    }

    private double simulateTemperature() {
        return 0.0;
    }

    /**
     * todo 根据以前数据制造数据
     * 数据制造类
     *
     * @param id
     */
    @Override
    public void maker(String id) {
        DeviceData machineData = new DeviceData();
        SimulationImpl simulation = new SimulationImpl();
        DeviceCache deviceCache = new DeviceCache();


        machineData.setDeviceId(id);
        machineData.setPressure(simulation.pressureRandom(deviceCache));
        machineData.setTemperature(simulation.temperatureRandom());
        log.info(String.valueOf(machineData));

    }

    /**
     * 随机压力生成
     *
     * @return
     */


//    @Value("${simulation.pressureRandom.pressure}")
//    private double pressure;
//    @Value("${simulation.pressureRandom.morePressure}")
//    private double morePressure;
//    @Value("${simulation.pressureRandom.changePressure}")
//    private double changePressure;


    /**
     *压力1-10 递增，超过10之后随机下降7-15个点，然后继续递增
     *压力1-10递增，每次递增区间为0.1-3，90%的概率在0.1-0.5之间，剩下在0.5-3之间
     *
     * @param deviceCache
     * @return
     */
    @Override
    public double pressureRandom(DeviceCache deviceCache) {

        /**
         * todo 问题压力固定
         */
        double pressure = deviceCache.getLastPressure();
        //压力大于20
        if (pressure > 22) {
            double changeMinValue = new Random().nextDouble() * 10;
            pressure -= changeMinValue;
            //压力小于10
        } else {
            Random random = new Random();
            int num = random.nextInt(100);
            if (num > 70) {
                double changeAValue = new Random().nextDouble() * 5;
                pressure += changeAValue;
            } else {
                double changeAValue = new Random().nextDouble() * 10;
                pressure += changeAValue;

            }
        }
        return Double.parseDouble(String.format("%.2f", pressure));
    }

    /**
     * 随机温度生成
     *温度80%的概率落在0-20区间，剩下随便
     * @return
     */
    @Override
    public int temperatureRandom() {

        Random random = new Random();
        int n = random.nextInt(100);
        int temperature;
        if (n > 20) {
            int min = 0;
            int max = 20;

            temperature = new Random().nextInt(max - min + 1) + min;
            log.info("当前温度为：    " + String.valueOf(temperature) + "℃   " + "0℃-20℃");
        } else {
            int min = 20;
            int max = 100;
            temperature = new Random().nextInt(max - min + 1) + min;
            log.info("当前温度为：    " + String.valueOf(temperature) + "℃   " + "20℃-100℃");
        }

        return temperature;
    }

}

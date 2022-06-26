package com.li.datasimulation;

import com.li.datasimulation.service.Simulation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author lizhouquan
 * @description 功能描述
 * @create 2022/6/26 10:46
 */

@Slf4j
public class PrepareDeviceCacheTest {

@Resource
Simulation simulation;
    @Test

    public void PrepareDeviceTest(){
        simulation.prepareDeviceCache();


    }
}

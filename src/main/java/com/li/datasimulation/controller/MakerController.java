package com.li.datasimulation.controller;

import com.li.datasimulation.common.CommonResult;
import com.li.datasimulation.service.Simulation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author lizhouquan
 * @description 功能描述
 * @create 2022/6/25 16:56
 */


@Controller
@RequestMapping("/DataMaker")
public class MakerController {

    @Resource
    Simulation simulation;


    @GetMapping("/maker")
    public CommonResult dataMaker(String id){


        simulation.maker(id);

        return CommonResult.success(null);
    }


}

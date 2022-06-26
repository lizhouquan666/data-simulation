package com.li.datasimulation.controller;

import com.li.datasimulation.common.CommonResult;
import com.li.datasimulation.service.MyProducer;
import com.li.datasimulation.service.impl.MyProducerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by macro on 2020/5/19.
 */
@Slf4j
@Controller
@RequestMapping("/rabbit")
public class RabbitController {


    @Resource
    MyProducer myProducer;

    //InnerMQProducer
    @RequestMapping(value = "/InnerMQProducer", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult InnerMQProducerTest() {
        myProducer.innerSend();
//        for(int i=0;i<10;i++){
//
//        }
        return CommonResult.success(null);
    }
    //InnerMQProducer
    @RequestMapping(value = "/OuterMQProducer", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult OuterMQProducerTest() {


        for(int i=0;i<10;i++){
//            outerMQProducer.send();
            myProducer.sendDelayed();
        }
        return CommonResult.success(null);
    }
    }


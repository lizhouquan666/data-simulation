package com.li.datasimulation.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author lizhouquan
 * @description 功能描述
 * @create 2022/6/25 19:48
 */
public interface MyConsumer {
    @RabbitListener(queues = "simple.hello",containerFactory ="outerListenerContainerFactoryOne")
    void receiveOne(String in);
}

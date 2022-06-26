package com.li.datasimulation.service;

/**
 * @author lizhouquan
 * @description 功能描述
 * @create 2022/6/25 19:29
 */
public interface MyProducer {
    void innerSend();

    void outerSend();
    void sendDelayed();

}

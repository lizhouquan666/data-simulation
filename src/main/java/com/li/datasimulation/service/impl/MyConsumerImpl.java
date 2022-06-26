package com.li.datasimulation.service.impl;

import com.li.datasimulation.service.MyConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lizhouquan
 * @description 功能描述
 * @create 2022/6/25 19:49
 */

@Slf4j
@Service
public class MyConsumerImpl implements MyConsumer {

    /**
     * 覆盖容器工厂的acknowledgeMode属性  容器自动确认消息 默认模式
     * */
//    @RabbitListener(queues = "simple.hello",containerFactory ="innerListenerContainerFactory")
//    public void receive(String in) {
//        log.info(" [x] Received '{}'", in);
//    }
    /**
     * 覆盖容器工厂的acknowledgeMode属性 NONE 不发送任何确认
     * */
//    @RabbitListener(queues = "simple.hello",containerFactory ="innerListenerContainerFactory",ackMode = "NONE")
//    public void receiveAckNone(String in) {
//        log.info(" [x] Received '{}'", in);
//    }
    /**
     * 覆盖容器工厂的acknowledgeMode属性  MANUAL 侦听器必须通过调用来确认所有消息 Channel.basicAck()
     * */
//    @RabbitListener(queues = "simple.hello",containerFactory ="innerListenerContainerFactory",ackMode = "MANUAL")
//    public void receiveAckManual(String in,Channel channel, Message message) throws IOException {
//        log.info(" [x] Received '{}'", in);
//
//        try{
//            throw new RuntimeException();
////            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        }
//        catch (Exception e){
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
//
//        }
//
//    }

    /**
     * OuterListener
     * */
//    @RabbitListener(queues = "simple.hello",containerFactory ="outerListenerContainerFactory")
//    public void receive(List<String> in) {
//        log.info(" [x] Received '{}'", in);
//    }
    /**
     * OuterListenerOne
     * */
    @Override
    @RabbitListener(queues = "simple.hello",containerFactory ="outerListenerContainerFactoryOne")
    public void receiveOne(String in) {
        Date date = new Date();
        log.info(" [x] Received '{}''{}'", date,in);
    }



}

package com.li.datasimulation.service.impl;

import com.li.datasimulation.service.MyProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lizhouquan
 * @description 功能描述
 * @create 2022/6/25 19:32
 */


@Service
@Slf4j
public class MyProducerImpl implements MyProducer {
    @Resource(name = "innerRabbitTemplate")
    private RabbitTemplate innerRabbitTemplate;
    @Override
    public void innerSend() {
        String message = "Hello World!";
        String exchange = "amq.direct";
        String routingKey = "Hello";
        this.innerRabbitTemplate.convertAndSend(exchange,routingKey, message);
        log.info(" [x] Sent '{}'", message);
    }





    @Resource(name = "outerRabbitTemplate")
    private RabbitTemplate outerRabbitTemplate;
    @Override
    public void outerSend() {
        String message = "Hello World!";
        String exchange = "amq.direct";
        String routingKey = "Hello";
        this.outerRabbitTemplate.convertAndSend(exchange,routingKey, message);
//        log.info(" [x] Sent '{}'", message);

    }
    @Override
    public void sendDelayed() {
        Date date = new Date();
        String exchange = "delayed.exchanges";
        String routingKey = "Hello";
        MessageProperties properties = new MessageProperties();
        properties.setDelay(20000);
//        outerRabbitTemplate.send(exchange, routingKey, MessageBuilder.withBody("foo".getBytes()).andProperties(properties).build());
//        log.info(" [x] Sent '{}'", date);
        outerRabbitTemplate.convertAndSend(exchange, routingKey, "foOOOo", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(3000);
                log.info(" [x] Sent '{}'", date);
                return message;
            }
        });


    }


}

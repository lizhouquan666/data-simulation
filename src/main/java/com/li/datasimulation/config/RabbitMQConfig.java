package com.li.datasimulation.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * rabbit mq 配置
 *
 * @author xieyu
 * @date 2021-07-02
 */
@Configuration
public class RabbitMQConfig {
    /**
     * outer
     */
    @Value("${spring.rabbitmq.outer.listener.simple.acknowledge-mode}")
    private String outerAcknowledgeMode;
    @Value("${spring.rabbitmq.outer.listener.simple.prefetch}")
    private Integer outerPrefetch;
    @Value("${spring.rabbitmq.outer.listener.simple.batch-size}")
    private Integer outerBatchSize;

    /**
     * 创建ConnectionFactory方法
     * @return
     */
    
    public ConnectionFactory rabbitConfiguration(String host, int port, String username, String password, boolean publisherReturns, String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(publisherReturns);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    /**
     * 内部RabbitMq   connectionFactory
     * @return
     */
    @Bean(name="innerConnectionFactory")
    @Primary
    public ConnectionFactory innerRabbitConfiguration(@Value("${spring.rabbitmq.inner.host}") String host,
                                                      @Value("${spring.rabbitmq.inner.port}") int port,
                                                      @Value("${spring.rabbitmq.inner.username}") String username,
                                                      @Value("${spring.rabbitmq.inner.password}") String password,
                                                      @Value("${spring.rabbitmq.inner.publisher-returns}") boolean publisherReturns,
                                                      @Value("${spring.rabbitmq.inner.virtual-host}") String virtualHost) {
        return this.rabbitConfiguration(host, port, username, password, publisherReturns, virtualHost);
    }

    /**
     * 内部RabbitMq   AmqpAdmin
     * @return
     */
    @Bean(name = "innerRabbitAdmin")
    @Primary
    public AmqpAdmin innerRabbitAdmin(@Qualifier("innerConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    /**
     * 内部RabbitMq   RabbitTemplate
     * @return
     */
    @Bean(name="innerRabbitTemplate")
    @Primary
    public RabbitTemplate innerGroundRabbitTemplate(@Qualifier("innerConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    /**
     * 内部RabbitMq   ListenerContainerFactory
     * @return
     */
    @Bean(name = "innerListenerContainerFactory")
    @Primary
    public SimpleRabbitListenerContainerFactory innerListenerContainerFactory(@Qualifier("innerConnectionFactory") ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 外部RabbitMq   connectionFactory
     * @return
     */
    @Bean(name="outerConnectionFactory")
    public ConnectionFactory outerRabbitConfiguration(@Value("${spring.rabbitmq.outer.host}") String host,
                                                      @Value("${spring.rabbitmq.outer.port}") int port,
                                                      @Value("${spring.rabbitmq.outer.username}") String username,
                                                      @Value("${spring.rabbitmq.outer.password}") String password,
                                                      @Value("${spring.rabbitmq.outer.publisher-returns}") boolean publisherReturns,
                                                      @Value("${spring.rabbitmq.outer.virtual-host}") String virtualHost) {
        return this.rabbitConfiguration(host, port, username, password, publisherReturns, virtualHost);
    }

    /**
     * 外部RabbitMq   AmqpAdmin
     * @return
     */
    @Bean(name = "outerRabbitAdmin")
    public AmqpAdmin outerRabbitAdmin(@Qualifier("outerConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    /**
     * 外部RabbitMq   RabbitTemplate
     * @return
     */
    @Bean(name="outerRabbitTemplate")
    public RabbitTemplate outerRabbitTemplate(@Qualifier("outerConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    /**
     * 外部RabbitMq   ListenerContainerFactory
     * @return
     */
    @Bean(name = "outerListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory outerListenerContainerFactory(
            @Qualifier("outerConnectionFactory") ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //容器自动(auto)确认消息
        factory.setAcknowledgeMode(AcknowledgeMode.valueOf(outerAcknowledgeMode.toUpperCase()));
        //确定是否应重新排队因侦听器引发异常而被拒绝的消息 默认为true
        factory.setDefaultRequeueRejected(false);
        //每个消费者可以未确认的未确认消息的数量
        factory.setPrefetchCount(outerPrefetch);
        //批处理
        factory.setBatchListener(true);
        //在消费者中启用离散消息的批处理
        factory.setConsumerBatchEnabled(true);
        //批量发送前的消息数。
        factory.setBatchSize(outerBatchSize);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
    /**
     * 外部RabbitMq   ListenerContainerFactoryOne
     * @return
     */
    @Bean(name = "outerListenerContainerFactoryOne")
    public SimpleRabbitListenerContainerFactory outerListenerContainerFactoryOne(
            @Qualifier("outerConnectionFactory") ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
    /**
     * 声明队列
     *
     * */
    @Bean
    public Queue hello(@Qualifier("innerRabbitAdmin") AmqpAdmin amqpAdmin) {
        Queue queue = new Queue("simple.hello", true);
        amqpAdmin.declareQueue(queue);
        return queue;
    }
    @Bean
    public Queue outerHello(@Qualifier("innerRabbitAdmin") AmqpAdmin outamqpAdmin) {
        Queue queue = new Queue("simple.hello", true);
        outamqpAdmin.declareQueue(queue);
        return queue;
    }
}

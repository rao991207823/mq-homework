package com.raos.example.delayqueue.ttl;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列 DLX DLQ
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/11 21:36
 */
@Configuration
public class DlxConfig {

    @Value("${rabbitmq.uri:amqp://guest:guest@127.0.0.1:5672}")
    private String rabbitmqUri;

    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(rabbitmqUri);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean("oriUseExchange")
    public DirectExchange exchange() {

        return new DirectExchange("GP_ORI_USE_EXCHANGE", true, false, new HashMap<>());
    }

    @Bean("oriUseQueue")
    public Queue queue() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-message-ttl", 10000); // 10秒钟后成为死信
        map.put("x-dead-letter-exchange", "GP_DEAD_LETTER_EXCHANGE"); // 队列中的消息变成死信后，进入死信交换机
        return new Queue("GP_ORI_USE_QUEUE", true, false, false, map);
    }

    @Bean
    public Binding binding(@Qualifier("oriUseQueue") Queue queue,
                           @Qualifier("oriUseExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("yufeng.ori.use");
    }

    /**
     * 队列的死信交换机
     * @return
     */
    @Bean("deadLetterExchange")
    public TopicExchange deadLetterExchange() {

        return new TopicExchange("GP_DEAD_LETTER_EXCHANGE", true, false, new HashMap<>());
    }

    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {

        return new Queue("GP_DEAD_LETTER_QUEUE", true, false, false, new HashMap<>());
    }

    @Bean
    public Binding bindingDead(@Qualifier("deadLetterQueue") Queue queue,
                               @Qualifier("deadLetterExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#"); // 无条件路由
    }

}
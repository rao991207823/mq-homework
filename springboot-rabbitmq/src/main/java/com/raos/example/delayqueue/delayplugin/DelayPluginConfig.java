package com.raos.example.delayqueue.delayplugin;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
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
 * 基于插件实现的延迟队列的 rabbitmq 配置
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/11 21:55
 */
@Configuration
public class DelayPluginConfig {

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

    @Bean("delayExchange") // CustomExchange 允许自定义的交换机
    public CustomExchange exchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        // 设置交换机类型--延时插件类型
        return new CustomExchange("YF_DELAY_EXCHANGE", "x-delayed-message",true, false, args);
    }

    @Bean("delayQueue")
    public Queue deadLetterQueue() {

        return new Queue("YF_DELAY_QUEUE", true, false, false, new HashMap<>());
    }

    @Bean
    public Binding bindingDead(@Qualifier("delayQueue") Queue queue,
                               @Qualifier("delayExchange") CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange)
                .with("#").noargs(); // 无条件路由
    }

}

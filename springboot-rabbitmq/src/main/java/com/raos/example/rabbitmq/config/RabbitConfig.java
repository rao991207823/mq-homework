package com.raos.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springboot 集成 rabbitmq 简单入门 - RabbitMq 配置类
 * 配置三个交换机，分别是直连、广播和主题；然后是四个队列绑定，绑定关系为直连1、广播2、主题1。
 * 注意：测试前清除一下之前同名的交换机和队列，不然无法自动创建交换机和队列
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 22:51
 */
@Configuration
public class RabbitConfig {

    // 1.定义三个交换机：直连交换机、广播交换机和主题交换机
    @Bean("directExchange")
    public DirectExchange getDirectExchange() {
        return new DirectExchange("DIRECT_EXCHANGE");
    }

    @Bean("fanoutExchange")
    public FanoutExchange getFanoutExchange() {
        return new FanoutExchange("FANOUT_EXCHANGE");
    }

    @Bean("topicExchange")
    public TopicExchange getTopicExchange() {
        return new TopicExchange("TOPIC_EXCHANGE");
    }

    // 2.定义四个队列
    @Bean("firstQueue")
    public Queue getFirstQueue() {
        return new Queue("FIRST_QUEUE");
    }

    @Bean("secondQueue")
    public Queue getSecondQueue() {
        return new Queue("SECOND_QUEUE");
    }

    @Bean("thirdQueue")
    public Queue getThirdQueue() {
        return new Queue("THIRD_QUEUE");
    }

    @Bean("fourthQueue")
    public Queue getFourthQueue() {
        return new Queue("FOURTH_QUEUE");
    }

    // 3.定义四个绑定
    @Bean
    public Binding bindFirst(@Qualifier("firstQueue") Queue queue,
                             @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("yufeng.best");
    }

    @Bean
    public Binding bindSecond(@Qualifier("secondQueue") Queue queue,
                              @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.best.#");
    }

    @Bean
    public Binding bindThird(@Qualifier("thirdQueue") Queue queue,
                             @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFourth(@Qualifier("fourthQueue") Queue queue,
                              @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}

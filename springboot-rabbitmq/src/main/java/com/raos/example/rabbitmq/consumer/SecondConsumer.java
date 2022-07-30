package com.raos.example.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * springboot 集成 rabbitmq 简单入门 -- RabbitMq 消息消费者2
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 23:06
 */
//@Component
@RabbitListener(queues = "SECOND_QUEUE")
public class SecondConsumer {

    @RabbitHandler
    public void process(String msg) {
        // 消费主题式消息时，只有一条？？
        // 原因：生产者生产消息完成后，由于同属一个工程，因此测试时它也作为消费者消费消息
        // 排除影响方案时，分成两个工程或 生产者测试时 注销掉主题式消费者类上的注解即可
        System.out.println("Second queue received msg: " + msg);
    }

}

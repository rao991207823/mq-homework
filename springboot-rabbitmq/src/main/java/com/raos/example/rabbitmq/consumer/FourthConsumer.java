package com.raos.example.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * springboot 集成 rabbitmq 简单入门 -- RabbitMq 消息消费者4
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 23:06
 */
//@Component
@RabbitListener(queues = "FOURTH_QUEUE")
public class FourthConsumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("fourth queue received msg: " + msg);
    }

}

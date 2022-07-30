package com.raos.example.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * springboot 集成 rabbitmq 简单入门 -- RabbitMq 消息消费者1
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 23:06
 */
//@Component
@RabbitListener(queues = "FIRST_QUEUE") // 设置绑定队列
public class FirstConsumer {

    @RabbitHandler // 接收消息的处理逻辑
    public void process(String msg) {
        System.out.println("First queue received msg: " + msg);
    }

}

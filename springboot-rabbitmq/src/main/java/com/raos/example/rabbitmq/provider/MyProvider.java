package com.raos.example.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * springboot 集成 rabbitmq 简单入门 -- RabbitMq 消息生产者
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 23:23
 */
@Component
public class MyProvider {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        // 发送4条消息
        // 1.直连消息
        amqpTemplate.convertAndSend("", "FIRST_QUEUE", "This is a direct msg");

        // 2.两条主题消息
        amqpTemplate.convertAndSend("TOPIC_EXCHANGE", "gupao.best.teacher",
                "-------- a topic msg: gupao.best.teacher");
        amqpTemplate.convertAndSend("TOPIC_EXCHANGE", "yufeng.best.interest",
                "-------- a topic msg: yufeng.best.interest");

        // 3.广播消息
        amqpTemplate.convertAndSend("FANOUT_EXCHANGE", "", "This is a fanout msg");
    }

}

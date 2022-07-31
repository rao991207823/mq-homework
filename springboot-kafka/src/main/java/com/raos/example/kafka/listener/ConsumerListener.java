package com.raos.example.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka入门之 springboot-kafka 客户端监听器
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/12 21:21
 */
@Component
public class ConsumerListener {

    @KafkaListener(topics = "springboot-topic", groupId = "springboot-topic-group")
    public void onMessage(String msg) {

        System.out.println("----收到消息：" + msg + "----");
    }

}

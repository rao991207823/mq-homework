package com.raos.example.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * kafka入门之 springboot-kafka 消息生产者
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/12 21:19
 */
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public String send(@RequestParam String msg) {
        kafkaTemplate.send("springboot-topic", msg);
        return "ok";
    }

}

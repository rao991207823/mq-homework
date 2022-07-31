package com.raos.example.kafka;

import com.raos.example.kafka.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * kafka入门之 springboot-kafka 测试
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/12 21:23
 */
@SpringBootTest
class KafkaAppTests {

    @Autowired
    private KafkaProducer producer;

    // 消费者：先启动 kafkaApp
    @Test
    void testSendMsg() {
        long time = System.currentTimeMillis();
        System.out.println("----当前毫秒= " + time + "，已经发出----");
        producer.send("yufeng good, " + time);
    }

}

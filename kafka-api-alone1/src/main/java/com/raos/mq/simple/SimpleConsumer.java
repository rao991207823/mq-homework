package com.raos.mq.simple;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * kafka入门之简单的消费者
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/12 20:57
 */
public class SimpleConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        // 单机连接
        props.put("bootstrap.servers","192.168.235.102:9092");
        props.put("group.id", "gp-test-group");
        // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
        props.put("enable.auto.commit", "true");
        // 消费者自动提交的间隔
        props.put("auto.commit.interval.ms", "1000");
        // 从最早的数据开始消费 earliest（最早的） | latest（最新的） | none
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // 订阅topic
        consumer.subscribe(Arrays.asList("mytopic"));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset= %d ,key= %s, value= %s, partition= %s%n", record.offset(), record.key(),
                            record.value(), record.partition());
                }
            }
        } finally {
            consumer.close();
        }
    }
}

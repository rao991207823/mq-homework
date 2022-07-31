package com.raos.mq.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * kafka入门之简单的生产者
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/12 20:52
 */
public class SimpleProducer {

    public static void main(String[] args) {
        Properties pros = new Properties();
        // 单机连接
        pros.put("bootstrap.servers","192.168.235.102:9092");
        pros.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pros.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 0 发出去就确认 | 1 leader 落盘就确认| all(-1) 所有Follower同步完才确认
        pros.put("acks", "1");
        // 异常自动重试次数
        pros.put("retries", 3);
        // 多少条数据发送一次，默认16K 1024 * 16 = 16384
        pros.put("batch.size", 16384);
        // 批量发送的等待时间
        pros.put("linger.ms", 5);
        // 客户端缓冲区大小，默认32M，满了也会触发消息发送 1024 * 1024 * 32 = 33554432
        pros.put("buffer.memory", 33554432);
        // 获取元数据时生产者的阻塞时间，超时后抛出异常
        pros.put("max.block.ms", 3000);

        // 创建Sender线程
        Producer<String, String> producer = new KafkaProducer<>(pros);

        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("mytopic", Integer.toString(i), Integer.toString(i)));
            // System.out.println("发送: " + i);
        }

        producer.close();
    }
}

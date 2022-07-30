package com.raos.mq.delayqueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基于死信队列实现消息延迟投递 -- 消息生产者
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/11 21:05
 */
public class DlxProducer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@127.0.0.1:5672");

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String msg = "Hello world, RabbitMQ DLX MSG, Send Time：" + sf.format(new Date());

        // 设置属性，消息20秒钟过期
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 持久化消息
                .contentEncoding("UTF-8")
                .expiration("20000") // TTL
                .build();

        // 发送消息(默认交换机)
        channel.basicPublish("", "yufeng.ori.use", properties, msg.getBytes());

        channel.close();
        conn.close();
    }
}


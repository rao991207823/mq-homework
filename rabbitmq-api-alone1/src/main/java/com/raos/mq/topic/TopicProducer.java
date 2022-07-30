package com.raos.mq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ使用案例之 Topic通信--生产者 --主题交换机
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 22:35
 */
public class TopicProducer {

    /** 交换机名称 */
    private static final String EXCHANGE_NAME = "TOPIC_EXCHANGE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接IP
        factory.setHost("127.0.0.1");
        // 连接端口
        factory.setPort(5672);
        // virtualHost
        factory.setVirtualHost("/");
        // 用户
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 发送消息
        for (int i = 0; i < 5; i++) {
            String msg = "Hello world, this is topic msg to RabbitMQ..." + i;

            // String exchange, String routingKey, BasicProperties props, byte[] body
            channel.basicPublish(EXCHANGE_NAME, "com.raos.mq", null, msg.getBytes());
        }
        channel.close();
        conn.close();
    }

}

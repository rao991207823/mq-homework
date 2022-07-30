package com.raos.mq.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * RabbitMQ使用案例之 Topic通信--消费者 --主题交换机
 *   主题类型的交换机与一个队列绑定时，可以指定按模式匹配的routing key。
 *     通配符有两个，*代表匹配一个单词。#代表匹配零个或者多个单词。单词与单词之间用 . 隔开。
 *     路由规则：发送消息到主题类型的交换机时，routing key符合binding key的模式时，绑定的队列才能收到消息。
 *     注意：一个队列下只有一个匹配路由的消费者收到消息。
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 22:35
 */
public class TopicConsumer {

    /** 交换机名称 */
    private static final String EXCHANGE_NAME = "TOPIC_EXCHANGE";
    /** 队列名称 */
    private static final String QUEUE_NAME = "TOPIC_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接IP
        factory.setHost("127.0.0.1");
        // 默认监听端口
        factory.setPort(5672);
        // virtualHost
        factory.setVirtualHost("/");

        // 设置访问的用户（默认用户为：guest---只能本机访问）
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 声明交换机（包括定义名称以及类型等）
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel.exchangeDeclare(EXCHANGE_NAME,"topic",false, false, null);

        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Waiting for message from topic ....");

        // 绑定队列和交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "com.raos.mq.#"); //模糊匹配

        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received message: '" + msg + "'");
                System.out.println("consumerTag: " + consumerTag);
                System.out.println("deliveryTag: " + envelope.getDeliveryTag());
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

}

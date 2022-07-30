package com.raos.mq.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * RabbitMQ使用案例之 Fanout通信--消费者 --广播交换机
 *
 *  广播类型的交换机与一个队列绑定时，不需要指定binding key。
 *      路由规则：当消息发送到广播类型的交换机时，不需要指定routing key，所有与之绑定的队列都能收到消息。
 *      因此测试定义多个队列绑定到同一个 Fanout，可以看到不同队列的消费者接收到相同消息，
 *      否则绑定相同队列的多个消费者，只有一个消费者收到消息。
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 22:23
 */
public class FanoutConsumer {

    /** 交换机名称 */
    private static final String EXCHANGE_NAME = "FANOUT_EXCHANGE";
    /** 队列名称 */
    private static final String QUEUE_NAME = "FANOUT_QUEUE"; // 测试时，这里每一个消费者绑定队列名不同

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接IP
        factory.setHost("127.0.0.1");
        // 默认监听端口
        factory.setPort(5672);
        // 虚拟机
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
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout",false, false, null);

        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Waiting for message from fanout ....");

        // 绑定队列和交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

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

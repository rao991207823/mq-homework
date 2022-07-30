package com.raos.example.delayqueue.ttl;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 死信队列测试 - 消息生产者
 *   注意：为了避免错误，保证程序顺利执行，请清理掉其他工厂创建的同名死信交换机和死信队列
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/11 21:47
 */
@ComponentScan(basePackages = "com.raos.example.delayqueue.ttl")
public class DlxSender {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DlxSender.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class); //
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        // 随队列的过期属性过期，单位ms
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        rabbitTemplate.convertAndSend("GP_ORI_USE_EXCHANGE", "yufeng.ori.use",
                "测试死信消息，时间=" + sf.format(new Date()));

        // 10秒后容器关闭
        TimeUnit.SECONDS.sleep(10);
        context.close();
    }

}

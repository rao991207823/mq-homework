package com.raos.example.rabbitmq;

import com.raos.example.rabbitmq.provider.MyProvider;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 生产者发消息测试
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/10 23:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RabbitmqAppTests {

    @Autowired
    private MyProvider provider;

    @Test
    public void contextLoads() {
        provider.send();
        System.out.println("发送消息完成");
    }

}

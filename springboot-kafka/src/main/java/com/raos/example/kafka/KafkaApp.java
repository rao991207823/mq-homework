package com.raos.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot集成kafka入门 -- 启动类
 *
 * @author raos
 * @emil 991207823@qq.com
 * @date 2022/7/12 21:13
 */
@SpringBootApplication
public class KafkaApp {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApp.class, args);
    }

}

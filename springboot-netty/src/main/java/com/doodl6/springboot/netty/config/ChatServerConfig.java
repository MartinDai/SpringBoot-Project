package com.doodl6.springboot.netty.config;

import com.doodl6.springboot.netty.server.ChatServer;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * 聊天服务配置类
 */
@Configuration
public class ChatServerConfig {

    @PostConstruct
    public void init() {
        //启动聊天socket服务
        new Thread(() -> {
            try {
                ChatServer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

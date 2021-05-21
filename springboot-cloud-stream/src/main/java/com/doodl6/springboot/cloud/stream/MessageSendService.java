package com.doodl6.springboot.cloud.stream;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息发送服务
 */
@Service
public class MessageSendService {

    @Resource
    private MySource mySource;

    public boolean sendMessage(String content) {
        return mySource.messageChannel().send(MessageBuilder.withPayload(content).build());
    }
}

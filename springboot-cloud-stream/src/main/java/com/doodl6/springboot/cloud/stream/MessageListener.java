package com.doodl6.springboot.cloud.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * 消息处理监听
 */
@Slf4j
@Component
public class MessageListener {

    @StreamListener(MySource.STREAM_MESSAGE_TOPIC)
    @SendTo(MySource.STREAM_RESULT_TOPIC)
    public int processMessage(Message<String> message) {
        String payload = message.getPayload();
        log.info("收到Stream消息，content:{}", payload);
        //计算消息长度发送到下游事件
        return payload.length();
    }

}

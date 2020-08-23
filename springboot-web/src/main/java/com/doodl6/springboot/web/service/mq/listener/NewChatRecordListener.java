package com.doodl6.springboot.web.service.mq.listener;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.web.service.chat.ChatService;
import com.doodl6.springboot.web.service.mq.domain.NewChatRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 聊天记录消息监听
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.chatRecord.group}", topic = "${rocketmq.consumer.chatRecord.topic}", selectorExpression = "newChatRecord")
public class NewChatRecordListener implements RocketMQListener<NewChatRecord> {

    @Resource
    private ChatService chatService;

    @Override
    public void onMessage(NewChatRecord newChatRecord) {
        log.info("收到聊天记录MQ消息 | {}", JSON.toJSONString(newChatRecord));
        chatService.saveChatRecord(newChatRecord);
    }
}

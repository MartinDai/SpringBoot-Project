package com.doodl6.springboot.web.service.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doodl6.springboot.web.service.chat.ChatService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消费消息监听
 */
@Component
public class ConsumeMessageListener implements MessageListenerConcurrently {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ChatService chatService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context) {
        for (MessageExt messageExt : messageList) {
            String message = new String(messageExt.getBody());
            logger.info("收到消息 | {}", message);
            JSONObject messageJSON = JSON.parseObject(message);
            chatService.saveChatRecord(messageJSON.getString("userName"), messageJSON.getString("content"), messageJSON.getLongValue("timestamp"));
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}

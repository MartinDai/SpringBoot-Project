package com.doodl6.springboot.web.service.mq;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.web.service.mq.domain.NewChatRecord;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RocketMQService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.txGroup}")
    private String txGroup;

    @Value("${rocketmq.producer.clearUser.destination}")
    private String clearUserDestination;

    @Value("${rocketmq.producer.chatRecord.destination}")
    private String chatRecordDestination;

    public void sendNewChatRecord(NewChatRecord newChatRecord) {
        rocketMQTemplate.asyncSend(chatRecordDestination, newChatRecord, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("发送新聊天记录MQ消息成功 | {}", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable e) {
                logger.error("发送新聊天记录消息异常", e);
            }
        });
    }

    /**
     * 发送清除用户消息
     */
    public void sendClearUserMsg(long userId) {
        Message<Long> message = new GenericMessage<>(userId);
        try {
            SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(txGroup, clearUserDestination, message, userId);
            logger.info("发送清除用户消息完成 | {}", JSON.toJSONString(sendResult));
        } catch (Exception e) {
            logger.error("发送清除用户消息异常", e);
        }
    }

}

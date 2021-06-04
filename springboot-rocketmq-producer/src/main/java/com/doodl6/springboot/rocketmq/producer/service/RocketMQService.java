package com.doodl6.springboot.rocketmq.producer.service;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.rocketmq.producer.ProducerConstants;
import com.doodl6.springboot.rocketmq.producer.domain.NewChatRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class RocketMQService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.clearUser.destination}")
    private String clearUserDestination;

    @Value("${rocketmq.producer.chatRecord.destination}")
    private String chatRecordDestination;

    public void sendNewChatRecord(NewChatRecord newChatRecord) {
        rocketMQTemplate.asyncSend(chatRecordDestination, JSON.toJSONString(newChatRecord), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送新聊天记录MQ消息成功 | {}", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable e) {
                log.error("发送新聊天记录消息异常", e);
            }
        });
    }

    /**
     * 发送清除用户事务消息
     * 1.先发送一个半消息（不可消费）
     * 2.执行ClearUserTransactionListener这个类的executeLocalTransaction本地方法
     * 3.本地方法执行完成后返回RocketMQLocalTransactionState.COMMIT回执，触发半消息可被消费
     */
    public void sendClearUserMsg(long userId) {
        Message<Long> message = new GenericMessage<>(userId);
        try {
            SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(ProducerConstants.TRANSACTION_CLEAR_USER_GROUP, clearUserDestination, message, userId);
            log.info("发送清除用户消息完成 | {}", JSON.toJSONString(sendResult));
        } catch (Exception e) {
            log.error("发送清除用户消息异常", e);
        }
    }

}

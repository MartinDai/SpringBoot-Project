package com.doodl6.springboot.web.service.mq;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.web.service.mq.domain.NewChatRecord;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RocketMQService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.destination}")
    private String destination;

    public void sendNewChatRecord(NewChatRecord newChatRecord) {
        rocketMQTemplate.asyncSend(destination, newChatRecord, new SendCallback() {
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

}

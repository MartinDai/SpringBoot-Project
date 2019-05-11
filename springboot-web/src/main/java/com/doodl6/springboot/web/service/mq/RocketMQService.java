package com.doodl6.springboot.web.service.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RocketMQService {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Resource
    private DefaultMQProducer producer;

//    @Resource(name = "topic")
    private String topic;

//    @Resource(name = "tags")
    private String tags;

    public void sendNewChatRecord(String userName, String content, long timestamp) {
        JSONObject messageJSON = new JSONObject();
        messageJSON.put("userName", userName);
        messageJSON.put("content", content);
        messageJSON.put("timestamp", timestamp);
        try {
            Message msg = new Message(topic, tags, messageJSON.toJSONString().getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            logger.info("发送新聊天记录消息完成 | {}", JSON.toJSONString(sendResult));
        } catch (Exception e) {
            logger.error("发送新聊天记录消息异常", e);
        }
    }

}

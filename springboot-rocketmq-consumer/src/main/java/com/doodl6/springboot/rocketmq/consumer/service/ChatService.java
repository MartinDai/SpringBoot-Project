package com.doodl6.springboot.rocketmq.consumer.service;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.rocketmq.consumer.domain.NewChatRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by daixiaoming on 2019/1/3.
 */
@Slf4j
@Service
public class ChatService {

    /**
     * 保存聊天记录
     */
    public void saveChatRecord(NewChatRecord newChatRecord) {
        log.info("保存用户聊天记录 | {}", JSON.toJSONString(newChatRecord));

        //真实项目可以在这里把聊天内容入库
    }
}

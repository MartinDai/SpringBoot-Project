package com.doodl6.springboot.web.service.mq.listener;

import com.doodl6.springboot.dao.api.UserLoginLogMapper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 清除用户消息监听
 */
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.clearUser.group}", topic = "${rocketmq.consumer.clearUser.topic}")
public class ClearUserMessageListener implements RocketMQListener<Long> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserLoginLogMapper userLoginLogMapper;

    @Override
    public void onMessage(Long userId) {
        logger.info("收到清除用户消息 | {}", userId);
        userLoginLogMapper.deleteAllByUserId(userId);
        logger.info("删除用户登录记录完成 | {}", userId);
    }

}

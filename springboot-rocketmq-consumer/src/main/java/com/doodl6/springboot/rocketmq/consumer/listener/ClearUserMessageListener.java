package com.doodl6.springboot.rocketmq.consumer.listener;

import com.doodl6.springboot.dao.manager.UserLoginLogManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 清除用户消息监听
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.clearUser.group}", topic = "${rocketmq.consumer.clearUser.topic}")
public class ClearUserMessageListener implements RocketMQListener<Long> {

    @Resource
    private UserLoginLogManager userLoginLogManager;

    @Override
    public void onMessage(Long userId) {
        log.info("收到清除用户消息 | {}", userId);
        int count = userLoginLogManager.deleteByUserId(userId);
        log.info("删除用户登录记录完成 | userId:{} | count:{}", userId, count);
    }

}

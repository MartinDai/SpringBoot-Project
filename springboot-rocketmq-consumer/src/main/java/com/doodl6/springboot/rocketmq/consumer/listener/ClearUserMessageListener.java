package com.doodl6.springboot.rocketmq.consumer.listener;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.doodl6.springboot.dao.mapper.UserLoginLogMapper;
import com.doodl6.springboot.dao.entity.UserLoginLog;
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
    private UserLoginLogMapper userLoginLogMapper;

    @Override
    public void onMessage(Long userId) {
        log.info("收到清除用户消息 | {}", userId);
        UpdateWrapper<UserLoginLog> wrapper = Wrappers.update();
        wrapper.eq("user_id", userId);
        int count = userLoginLogMapper.delete(wrapper);
        log.info("删除用户登录记录完成 | userId:{} | count:{}", userId, count);
    }

}

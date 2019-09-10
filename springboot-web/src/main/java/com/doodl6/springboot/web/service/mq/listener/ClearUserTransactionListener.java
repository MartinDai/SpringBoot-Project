package com.doodl6.springboot.web.service.mq.listener;

import com.doodl6.springboot.dao.api.UserMapper;
import com.doodl6.springboot.dao.entity.User;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * 清除用户事务监听
 */
@Component
@RocketMQTransactionListener(txProducerGroup = "clearUserGroup")
public class ClearUserTransactionListener implements RocketMQLocalTransactionListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String charset = "UTF-8";

    @Resource
    private UserMapper userMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String msgStr = new String((byte[]) msg.getPayload(), Charset.forName(charset));
        logger.info("收到清除用户执行本地事务消息 | {} | {}", msgStr, arg);
        Long userId = (Long) arg;
        userMapper.deleteById(userId);
        logger.info("删除用户信息完成 | {}", userId);

        //如果返回RocketMQLocalTransactionState.UNKNOWN，则会定时轮训下面的检查本地事务状态方法
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String msgStr = new String((byte[]) msg.getPayload(), Charset.forName(charset));
        Long userId = Long.valueOf(msgStr);
        logger.info("收到检查清除用户本地事务状态消息 | {}", userId);

        User user = userMapper.getById(userId);
        return user == null ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
    }
}

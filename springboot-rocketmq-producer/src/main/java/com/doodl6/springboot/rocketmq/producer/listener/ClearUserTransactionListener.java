package com.doodl6.springboot.rocketmq.producer.listener;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.dao.api.UserMapper;
import com.doodl6.springboot.dao.entity.User;
import com.doodl6.springboot.rocketmq.producer.ProducerConstants;
import com.doodl6.springboot.rocketmq.producer.domain.TransactionMessageObj;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * 清除用户事务监听
 */
@Slf4j
@Component
@RocketMQTransactionListener
public class ClearUserTransactionListener implements RocketMQLocalTransactionListener {

    private static final String charset = "UTF-8";

    @Resource
    private UserMapper userMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        if (arg instanceof TransactionMessageObj) {
            TransactionMessageObj obj = (TransactionMessageObj) arg;
            if (ProducerConstants.TRANSACTION_DELETE_USER.equals(obj.getTransactionType())) {
                String msgStr = new String((byte[]) msg.getPayload(), Charset.forName(charset));
                log.info("收到清除用户执行本地事务消息 | msg:{} ", msgStr);
                Long userId = (Long) obj.getArg();
                int count = userMapper.deleteById(userId);
                log.info("删除用户信息完成 | userId:{} | count:{}", userId, count);
                //如果返回RocketMQLocalTransactionState.UNKNOWN，则会定时轮训下面的checkLocalTransaction方法检查本地事务状态
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                log.warn("收到不支持的本地事务消息 | obj:{}", JSON.toJSONString(obj));
                return RocketMQLocalTransactionState.UNKNOWN;
            }
        } else {
            log.warn("收到未知的本地事务消息 | arg:{}", JSON.toJSONString(arg));
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String msgStr = new String((byte[]) msg.getPayload(), Charset.forName(charset));
        long userId = Long.parseLong(msgStr);
        log.info("收到检查清除用户本地事务状态消息 | {}", userId);

        User user = userMapper.getById(userId);
        return user == null ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
    }
}

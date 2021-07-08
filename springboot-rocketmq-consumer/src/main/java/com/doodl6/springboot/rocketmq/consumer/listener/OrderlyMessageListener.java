package com.doodl6.springboot.rocketmq.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 顺序消息监听，需要配置consumeMode = ConsumeMode.ORDERLY才能保证顺序消费，默认是ConsumeMode.CONCURRENTLY会并发消费
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.orderlyMessage.group}", topic = "${rocketmq.consumer.orderlyMessage.topic}", consumeMode = ConsumeMode.ORDERLY)
public class OrderlyMessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String content) {
        log.info("开始处理顺序消息 | {}", content);
        try {
            //模拟消息消费慢，如果不是顺序消费，则输出的日志会乱序
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("顺序消息处理完成 | {}", content);
    }
}

package com.doodl6.springboot.web.service.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//@Configuration
//@PropertySource("classpath:mq.properties")
public class RocketMQConfig implements InitializingBean, DisposableBean, ApplicationContextAware {

    @Value("${producerGroup}")
    private String producerGroup;

    @Value("${nameServerAddress}")
    private String nameServerAddress;

    @Value("${topic}")
    private String topic;

    @Value("${tags}")
    private String tags;

    private ApplicationContext applicationContext;

    private DefaultMQProducer producer;

    private DefaultMQPushConsumer consumer;

    @Bean
    public DefaultMQProducer getProducer() {
        return producer;
    }

    @Bean
    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    @Bean(name = "topic")
    public String getTopic() {
        return topic;
    }

    @Bean(name = "tags")
    public String getTags() {
        return tags;
    }

    @Override
    public void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws MQClientException {
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameServerAddress);
        producer.start();

        consumer = new DefaultMQPushConsumer(producerGroup);
        consumer.setNamesrvAddr(nameServerAddress);
        consumer.subscribe(topic, tags);
        consumer.registerMessageListener(applicationContext.getAutowireCapableBeanFactory().getBean(ConsumeMessageListener.class));
        consumer.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

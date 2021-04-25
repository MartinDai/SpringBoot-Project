package com.doodl6.springboot.rocketmq.producer.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewChatRecord {

    private String userName;

    private String content;

    private long timestamp;

}

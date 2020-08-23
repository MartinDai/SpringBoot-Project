package com.doodl6.springboot.web.service.mq.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NewChatRecord implements Serializable {

    private String userName;

    private String content;

    private long timestamp;

}

package com.doodl6.springboot.web.service.mq.domain;

import java.io.Serializable;

public class NewChatRecord implements Serializable {

    private String userName;

    private String content;

    private long timestamp;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

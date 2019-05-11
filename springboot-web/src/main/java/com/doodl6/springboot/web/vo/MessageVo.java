package com.doodl6.springboot.web.vo;

import java.util.Date;

/**
 * Created by daixiaoming on 2018-12-10.
 */
public class MessageVo {

    private int userId;

    private String userName;

    private String content;

    private Date sendTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}

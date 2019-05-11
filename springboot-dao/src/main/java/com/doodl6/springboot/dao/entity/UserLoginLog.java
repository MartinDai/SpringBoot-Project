package com.doodl6.springboot.dao.entity;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户登录日志实体类
 */
@Alias("UserLoginLog")
public class UserLoginLog {

    private Long id;

    private Long userId;

    private Date loginTime;

    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

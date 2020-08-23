package com.doodl6.springboot.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户登录日志实体类
 */
@Getter
@Setter
@Alias("UserLoginLog")
public class UserLoginLog {

    private Long id;

    private Long userId;

    private Date loginTime;

    private Date created;

}

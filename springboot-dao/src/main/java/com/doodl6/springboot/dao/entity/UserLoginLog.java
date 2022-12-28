package com.doodl6.springboot.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户登录日志实体类
 */
@Getter
@Setter
@TableName("user_login_log")
public class UserLoginLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Date loginTime;

    @TableField(fill = FieldFill.INSERT)
    private Date created;

}

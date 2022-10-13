package com.doodl6.springboot.dao.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doodl6.springboot.dao.entity.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("user_login_log")
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {
}

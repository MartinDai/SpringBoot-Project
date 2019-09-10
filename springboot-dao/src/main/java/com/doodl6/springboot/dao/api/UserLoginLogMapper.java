package com.doodl6.springboot.dao.api;

import com.doodl6.springboot.dao.entity.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {

    List<UserLoginLog> queryLastLoginLog(long userId);

    void deleteAllByUserId(long userId);

}

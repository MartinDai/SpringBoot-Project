package com.doodl6.springboot.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doodl6.springboot.dao.entity.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {
}

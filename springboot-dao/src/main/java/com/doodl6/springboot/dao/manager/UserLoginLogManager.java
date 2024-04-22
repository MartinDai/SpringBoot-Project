package com.doodl6.springboot.dao.manager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doodl6.springboot.dao.entity.UserLoginLog;
import com.doodl6.springboot.dao.mapper.UserLoginLogMapper;
import org.springframework.stereotype.Component;

@Component
public class UserLoginLogManager extends ServiceImpl<UserLoginLogMapper, UserLoginLog> {

    public int deleteByUserId(long userId) {
        LambdaUpdateWrapper<UserLoginLog> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(UserLoginLog::getUserId, userId);
        return getBaseMapper().delete(wrapper);
    }

}

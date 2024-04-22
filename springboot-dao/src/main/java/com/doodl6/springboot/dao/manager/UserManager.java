package com.doodl6.springboot.dao.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doodl6.springboot.dao.entity.User;
import com.doodl6.springboot.dao.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserManager extends ServiceImpl<UserMapper, User> {

    public User queryByName(String name) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getName, name);
        return getOne(wrapper);
    }

}

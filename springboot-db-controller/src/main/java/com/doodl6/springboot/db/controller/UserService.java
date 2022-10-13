package com.doodl6.springboot.db.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.doodl6.springboot.dao.mapper.UserLoginLogMapper;
import com.doodl6.springboot.dao.mapper.UserMapper;
import com.doodl6.springboot.dao.entity.User;
import com.doodl6.springboot.dao.entity.UserLoginLog;
import com.google.common.collect.Lists;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserLoginLogMapper userLoginLogMapper;

    public User insertUser(String name) {
        User user = new User();
        user.setName(name);
        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("该用户已存在");
        }
        return user;
    }

    /**
     * 批量新增用户
     */
    @Transactional(rollbackFor = Exception.class)
    public List<User> batchAddUser(List<String> nameList) {
        List<User> userList = Lists.newArrayListWithCapacity(nameList.size());
        for (String name : nameList) {
            User user = new User();
            user.setName(name);
            try {
                userMapper.insert(user);
            } catch (DuplicateKeyException ignore) {
                //忽略重复的用户
                QueryWrapper<User> wrapper = Wrappers.query();
                wrapper.eq("name", name);
                user = userMapper.selectOne(wrapper);
            }
            userList.add(user);
        }

        return userList;
    }

    public User userLogin(long userId) {
        User user = userMapper.selectById(userId);
        Assert.notNull(user, "用户不存在");

        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginTime(new Date());
        userLoginLogMapper.insert(userLoginLog);

        return user;
    }

}

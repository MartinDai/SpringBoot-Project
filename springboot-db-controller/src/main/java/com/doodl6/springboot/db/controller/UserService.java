package com.doodl6.springboot.db.controller;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.dao.entity.User;
import com.doodl6.springboot.dao.entity.UserLoginLog;
import com.doodl6.springboot.dao.manager.UserLoginLogManager;
import com.doodl6.springboot.dao.manager.UserManager;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserManager userManager;

    @Resource
    private UserLoginLogManager userLoginLogManager;

    public User insertUser(String name) {
        User user = new User();
        user.setName(name);
        try {
            userManager.save(user);
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
                userManager.save(user);
            } catch (DuplicateKeyException ignore) {
                //忽略重复的用户
                user = userManager.queryByName(name);
            }
            userList.add(user);
        }

        return userList;
    }

    public User userLogin(long userId) {
        User user = userManager.getById(userId);
        Assert.notNull(user, "用户不存在");

        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginTime(new Date());
        userLoginLogManager.save(userLoginLog);

        return user;
    }

}

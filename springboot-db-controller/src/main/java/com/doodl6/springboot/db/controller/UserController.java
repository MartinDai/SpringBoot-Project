package com.doodl6.springboot.db.controller;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.common.web.response.MapResponse;
import com.doodl6.springboot.dao.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 新增用户
     */
    @RequestMapping("/addUser")
    public MapResponse addUser(String name) {
        Assert.isTrue(StringUtils.isNotBlank(name), "用户名称不能为空");

        User user = userService.insertUser(name);
        MapResponse response = new MapResponse();
        response.appendData("user", user);

        return response;
    }

    /**
     * 批量新增用户
     */
    @RequestMapping("/batchAddUser")
    public MapResponse batchAddUser(String names) {
        Assert.isTrue(StringUtils.isNotBlank(names), "用户名称不能为空");

        List<String> nameList = Arrays.asList(names.split(","));
        List<User> users = userService.batchAddUser(nameList);
        MapResponse response = new MapResponse();
        response.appendData("users", users);

        return response;
    }

    /**
     * 用户登录
     */
    @RequestMapping("/login/{userId}")
    public MapResponse addUser(@PathVariable Long userId) {
        Assert.notNull(userId, "用户ID不能为空");

        User user = userService.userLogin(userId);

        MapResponse response = new MapResponse();
        response.appendData("user", user);

        return response;
    }
}

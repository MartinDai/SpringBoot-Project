package com.doodl6.springboot.rocketmq.producer;

import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.dao.api.UserMapper;
import com.doodl6.springboot.dao.entity.User;
import com.doodl6.springboot.rocketmq.producer.service.RocketMQService;
import com.google.common.base.Preconditions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("mqUserController")
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RocketMQService rocketMQService;

    /**
     * 删除用户
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public BaseResponse<Void> deleteUser(Long userId) {
        Preconditions.checkArgument(userId != null, "用户ID不能为空");

        User user = userMapper.getById(userId);
        Preconditions.checkArgument(user != null, "用户不存在");

        rocketMQService.sendClearUserMsg(userId);

        return BaseResponse.success(null);
    }

}

package com.doodl6.springboot.rocketmq.producer;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.rocketmq.producer.service.RocketMQService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("mqUserController")
@RequestMapping("/user")
public class UserController {

    @Resource
    private RocketMQService rocketMQService;

    /**
     * 删除用户
     */
    @PostMapping(value = "/deleteUser")
    public BaseResponse<Void> deleteUser(Long userId) {
        Assert.notNull(userId, "用户ID不能为空");

        rocketMQService.sendClearUserMsg(userId);

        return BaseResponse.success();
    }

}

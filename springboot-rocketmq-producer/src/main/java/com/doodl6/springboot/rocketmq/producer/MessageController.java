package com.doodl6.springboot.rocketmq.producer;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.rocketmq.producer.service.RocketMQService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private RocketMQService rocketMQService;

    /**
     * 发送顺序消息
     */
    @PostMapping(value = "/sendOrderlyMessage")
    public BaseResponse<Void> sendOrderlyMessage(String key, String content) {
        Assert.isTrue(StringUtils.isNotBlank(key), "消息key不能为空");
        Assert.isTrue(StringUtils.isNotBlank(content), "消息内容不能为空");

        rocketMQService.sendOrderlyMessage(key, content);

        return BaseResponse.success();
    }

}

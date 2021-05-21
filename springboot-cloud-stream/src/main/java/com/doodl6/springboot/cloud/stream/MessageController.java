package com.doodl6.springboot.cloud.stream;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.common.web.response.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageSendService messageSendService;

    /**
     * 发送消息
     */
    @PostMapping(value = "/sendMessage")
    public BaseResponse<Boolean> sendMessage(String content) {
        Assert.isTrue(StringUtils.isNotBlank(content), "消息内容不能为空");

        boolean result = messageSendService.sendMessage(content);

        return BaseResponse.success(result);
    }

}

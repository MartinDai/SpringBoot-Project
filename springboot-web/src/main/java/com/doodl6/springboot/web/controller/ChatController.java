package com.doodl6.springboot.web.controller;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.web.vo.MessageVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 聊天控制类，基于异步请求实现
 * Created by daixiaoming on 2018-12-10.
 */
@Api(tags = "聊天控制")
@RestController
@RequestMapping("/chat")
public class ChatController {

    /**
     * 用户ID生成器
     */
    private static final AtomicInteger USER_ID_GENERATOR = new AtomicInteger();

    private static final Map<Integer, String> USER_MAP = Maps.newHashMap();

    /**
     * 为每个用户维护一份聊天记录队列
     */
    private static final Map<Integer, LinkedBlockingDeque<MessageVo>> MESSAGE_QUEUE_MAP = Maps.newHashMap();

    /**
     * 拉取数据，如果没有数据，会hold一段时间
     */
    @ApiOperation("拉取数据")
    @GetMapping("/pullData")
    public DeferredResult<BaseResponse<List<MessageVo>>> pullData(Integer userId) {

        Assert.notNull(userId, "用户ID不能为空");

        Assert.isTrue(USER_MAP.containsKey(userId), "用户不在聊天室内");

        //超时5秒钟
        DeferredResult<BaseResponse<List<MessageVo>>> deferredResult = new DeferredResult<>(5000L, new BaseResponse<>());

        PullDataThread pullDataThread = new PullDataThread(userId, deferredResult, 4000);
        ForkJoinPool.commonPool().submit(pullDataThread);

        return deferredResult;
    }

    /**
     * 进入聊天室
     */
    @ApiOperation("进入聊天室")
    @PostMapping("/intoChatRoom")
    public BaseResponse<Integer> intoChatRoom(String userName) {

        Assert.isTrue(StringUtils.isNotEmpty(userName), "用户名不能为空");

        int userId = USER_ID_GENERATOR.incrementAndGet();

        MESSAGE_QUEUE_MAP.put(userId, Queues.newLinkedBlockingDeque());

        USER_MAP.put(userId, userName + "[" + userId + "]");

        BaseResponse<Integer> response = new BaseResponse<>();
        response.setData(userId);
        return response;
    }

    /**
     * 发送聊天信息
     */
    @ApiOperation("发送聊天信息")
    @PostMapping("/sendMessage")
    public BaseResponse<Void> sendMessage(Integer userId, String content) {
        Assert.notNull(userId, "用户ID不能为空");
        Assert.isTrue(StringUtils.isNotBlank(content), "消息内容不能为空");
        Assert.isTrue(USER_MAP.containsKey(userId), "用户不存在");

        String userName = USER_MAP.get(userId);

        MessageVo messageVo = new MessageVo();
        messageVo.setUserId(userId);
        messageVo.setUserName(userName);
        messageVo.setSendTime(new Date());
        messageVo.setContent(content);

        for (Integer id : USER_MAP.keySet()) {
            MESSAGE_QUEUE_MAP.get(id).add(messageVo);
        }

        return BaseResponse.success();
    }

    static class PullDataThread implements Runnable {

        /**
         * 用户ID
         */
        private final int userId;

        private final DeferredResult<BaseResponse<List<MessageVo>>> deferredResult;

        /**
         * 超时时间(单位：毫秒)
         */
        private final int timeout;

        PullDataThread(int userId, DeferredResult<BaseResponse<List<MessageVo>>> deferredResult, int timeout) {
            this.userId = userId;
            this.deferredResult = deferredResult;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            LinkedBlockingDeque<MessageVo> messageQueue = MESSAGE_QUEUE_MAP.get(userId);
            BaseResponse<List<MessageVo>> response = new BaseResponse<>();
            List<MessageVo> list = Lists.newArrayList();

            MessageVo vo;
            try {
                if ((vo = messageQueue.poll(timeout, TimeUnit.MILLISECONDS)) != null) {
                    list.add(vo);
                    //一次最多取10条信息
                    for (int i = 0; i < 9; i++) {
                        vo = messageQueue.poll();
                        if (vo == null) {
                            break;
                        }
                        list.add(vo);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            response.setData(list);
            deferredResult.setResult(response);
        }
    }

}


package com.doodl6.springboot.dubbo.provider.service;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.dubbo.api.FirstDubboService;
import com.doodl6.springboot.dubbo.api.domain.DubboDomain;
import com.doodl6.springboot.dubbo.api.request.GetDubboInfoRequest;
import com.doodl6.springboot.dubbo.api.response.GetDubboInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;


@Slf4j
@DubboService(version = "${dubbo.service.firstDubbo.version}", timeout = 10000)
public class FirstDubboServiceImpl implements FirstDubboService {

    @Override
    public GetDubboInfoResponse getDubboInfo(GetDubboInfoRequest request) {
        log.info("收到Dubbo请求 | {}", JSON.toJSONString(request));
        GetDubboInfoResponse response = new GetDubboInfoResponse();
        try {
            //模拟服务响应慢
            Thread.sleep(100);
            Assert.notNull(request, "参数为空");
            Long id = request.getId();
            Assert.notNull(id, "id为空");

            DubboDomain dubboDomain = new DubboDomain();
            dubboDomain.setId(id);
            dubboDomain.setName("dubbo" + id);
            response.setDubboDomain(dubboDomain);
            response.setSuccess(true);
        } catch (IllegalArgumentException e) {
            response.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            log.error("获取dubbo信息出现异常", e);
            response.setErrorMsg("未知异常");
        }

        return response;
    }

}

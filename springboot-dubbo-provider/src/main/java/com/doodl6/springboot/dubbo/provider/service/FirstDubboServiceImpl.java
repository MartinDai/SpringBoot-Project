package com.doodl6.springboot.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.dubbo.api.FirstDubboService;
import com.doodl6.springboot.dubbo.api.domain.DubboDomain;
import com.doodl6.springboot.dubbo.api.request.GetDubboInfoRequest;
import com.doodl6.springboot.dubbo.api.response.GetDubboInfoResponse;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service(version = "${dubbo.service.firstDubbo.version}", timeout = 10000)
public class FirstDubboServiceImpl implements FirstDubboService {

    @Override
    public GetDubboInfoResponse getDubboInfo(GetDubboInfoRequest request) {
        log.info("收到Dubbo请求 | {}", JSON.toJSONString(request));
        GetDubboInfoResponse response = new GetDubboInfoResponse();
        try {
            //模拟服务响应慢
            Thread.sleep(100);
            Preconditions.checkArgument(request != null, "参数为空");
            Long id = request.getId();
            Preconditions.checkArgument(id != null, "id为空");

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

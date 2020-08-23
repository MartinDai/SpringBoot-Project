package com.doodl6.springboot.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.client.api.FirstDubboService;
import com.doodl6.springboot.client.domain.DubboDomain;
import com.doodl6.springboot.client.request.GetDubboInfoRequest;
import com.doodl6.springboot.client.response.GetDubboInfoResponse;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service(version = "${dubbo.service.firstDubbo.version}", timeout = 10000)
public class FirstDubboServiceImpl implements FirstDubboService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstDubboServiceImpl.class);

    @Override
    public GetDubboInfoResponse getDubboInfo(GetDubboInfoRequest request) {
        LOGGER.info("收到Dubbo请求 | {}", JSON.toJSONString(request));
        GetDubboInfoResponse response = new GetDubboInfoResponse();
        try {
            //模拟服务响应慢
//            Thread.sleep(100);
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
            LOGGER.error("获取dubbo信息出现异常", e);
            response.setErrorMsg("未知异常");
        }

        return response;
    }

}

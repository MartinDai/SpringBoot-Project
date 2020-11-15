package com.doodl6.springboot.web.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.config.annotation.Reference;
import com.doodl6.springboot.client.api.FirstDubboService;
import com.doodl6.springboot.client.domain.DubboDomain;
import com.doodl6.springboot.client.request.GetDubboInfoRequest;
import com.doodl6.springboot.client.response.GetDubboInfoResponse;
import com.doodl6.springboot.web.aspect.TraceIdHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IndexService {

    @Reference(version = "${dubbo.reference.firstDubbo.version}", check = false)
    private FirstDubboService firstDubboService;

    @SentinelResource(value = "getDubboInfo", blockHandler = "processDubboInfoBlock", fallback = "getDubboInfoFallback")
    public DubboDomain getDubboInfoWithSentinel(long id) {
        GetDubboInfoResponse response = getDubboInfoResponse(id);
        if (response.isSuccess()) {
            return response.getDubboDomain();
        }

        throw new IllegalStateException("dubbo调用失败");
    }

    @HystrixCommand(
            fallbackMethod = "getDubboInfoFallback",
            threadPoolKey = "index",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            },
            commandProperties = {
                    //超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            }
    )
    public DubboDomain getDubboInfoWithHystrix(long id) {
        GetDubboInfoResponse response = getDubboInfoResponse(id);
        if (response.isSuccess()) {
            return response.getDubboDomain();
        }

        throw new IllegalStateException("dubbo调用失败");
    }

    private GetDubboInfoResponse getDubboInfoResponse(long id) {
        GetDubboInfoRequest getDubboInfoRequest = new GetDubboInfoRequest();
        getDubboInfoRequest.setTraceId(TraceIdHolder.getTraceId());
        getDubboInfoRequest.setId(id);
        return firstDubboService.getDubboInfo(getDubboInfoRequest);
    }

    public DubboDomain getDubboInfoFallback(long id, Throwable e) {
        log.error("getDubboInfo方法出现异常", e);

        DubboDomain dubboDomain = new DubboDomain();
        dubboDomain.setId(id);
        dubboDomain.setName("服务不可用");
        return dubboDomain;
    }

    public DubboDomain processDubboInfoBlock(long id, BlockException be) {
        log.warn("getDubboInfo方法被Block了", be);

        DubboDomain dubboDomain = new DubboDomain();
        dubboDomain.setId(id);
        dubboDomain.setName("服务被限流");
        return dubboDomain;
    }
}

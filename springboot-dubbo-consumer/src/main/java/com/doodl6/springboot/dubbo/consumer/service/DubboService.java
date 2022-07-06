package com.doodl6.springboot.dubbo.consumer.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.doodl6.springboot.common.web.context.TraceIdHolder;
import com.doodl6.springboot.dubbo.api.FirstDubboService;
import com.doodl6.springboot.dubbo.api.domain.DubboDomain;
import com.doodl6.springboot.dubbo.api.request.GetDubboInfoRequest;
import com.doodl6.springboot.dubbo.api.response.GetDubboInfoResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DubboService {

    @DubboReference(version = "${dubbo.reference.firstDubbo.version}", check = false)
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
        if (e == null) {
            log.warn("getDubboInfoFallback被调用了");
        } else {
            if (e instanceof HystrixTimeoutException) {
                log.warn("getDubboInfo方法超时触发熔断");
            } else {
                log.error("getDubboInfo方法出现异常", e);
            }
        }

        DubboDomain dubboDomain = new DubboDomain();
        dubboDomain.setId(id);
        dubboDomain.setName("服务不可用");
        return dubboDomain;
    }

    public DubboDomain processDubboInfoBlock(long id, BlockException be) {
        log.warn("getDubboInfo方法被Block了");

        DubboDomain dubboDomain = new DubboDomain();
        dubboDomain.setId(id);
        dubboDomain.setName("服务被限流");
        return dubboDomain;
    }
}

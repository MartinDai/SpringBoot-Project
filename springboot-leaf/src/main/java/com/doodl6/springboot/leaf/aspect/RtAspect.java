package com.doodl6.springboot.leaf.aspect;

import cn.hutool.core.date.TimeInterval;
import com.doodl6.springboot.common.web.context.RequestParamContext;
import com.doodl6.springboot.common.web.context.TraceIdHolder;
import com.doodl6.springboot.leaf.common.Result;
import com.doodl6.springboot.leaf.common.Status;
import com.doodl6.springboot.leaf.segment.SegmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 接口RT切面类
 */
@Slf4j(topic = "rtLogger")
@Aspect
@Component
public class RtAspect {

    private static final String TRACE_ID_KEY = "traceId";

    @Resource
    private SegmentService segmentService;

    /**
     * 对添加了RequestMapping，GetMapping和PostMapping注解的方法添加切入点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) ||@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void api() {
    }

    /**
     * 记录接口耗时日志
     */
    @Around("api()")
    public Object recordRt(ProceedingJoinPoint pjp) throws Throwable {
        TimeInterval ti = new TimeInterval();
        Object obj;
        String traceId = null;
        try {
            traceId = getTraceId();
            TraceIdHolder.setTraceId(traceId);
            obj = pjp.proceed();
        } finally {
            log.info("traceId:{} | {} | {} | {} | {}ms", traceId, pjp.getSignature().getDeclaringType().getSimpleName(), pjp.getSignature().getName(), RequestParamContext.get(), ti.interval());
            TraceIdHolder.removeTraceId();
        }

        return obj;
    }

    private String getTraceId() {
        Result result = segmentService.getId(TRACE_ID_KEY);
        if (result.getStatus() == Status.SUCCESS) {
            return String.valueOf(result.getId());
        }

        return StringUtils.EMPTY;
    }
}

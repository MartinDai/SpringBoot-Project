package com.doodl6.springboot.web.aspect;

import com.doodl6.springboot.web.service.leaf.common.Result;
import com.doodl6.springboot.web.service.leaf.common.Status;
import com.doodl6.springboot.web.service.leaf.segment.SegmentService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 接口RT切面类
 */
@Aspect
@Component
public class RtAspect {

    private Logger logger = LoggerFactory.getLogger("rtLogger");

    private static final String TRACE_ID_KEY = "traceId";

    @Resource
    private SegmentService segmentService;

    /**
     * 对controller包下所有public方法添加切入点
     */
    @Pointcut("execution(public * com.doodl6.springboot.web.controller.*.*(..))")
    public void api() {
    }

    /**
     * 记录接口耗时日志
     */
    @Around("api()")
    public Object recordRt(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj;
        try {
            TraceIdHolder.setTraceId(getTraceId());
            obj = pjp.proceed();
        } finally {
            logger.info("{} | {} | {}ms", pjp.getSignature().getDeclaringType().getSimpleName(), pjp.getSignature().getName(), System.currentTimeMillis() - start);
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

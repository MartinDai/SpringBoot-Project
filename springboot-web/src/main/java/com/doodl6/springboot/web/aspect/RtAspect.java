package com.doodl6.springboot.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 接口RT切面类
 */
@Aspect
@Component
public class RtAspect {

    private Logger logger = LoggerFactory.getLogger("rtLogger");

    /**
     * 对controller包下所有public方法添加切入点
     */
    @Pointcut("execution(public * com.doodl6.springmvc.web.controller.*.*(..))")
    public void api() {
    }

    /**
     * 记录接口耗时日志
     */
    @Around("api()")
    public Object recordRt(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = pjp.proceed();
        logger.info("{} | {} | {}ms", pjp.getSignature().getDeclaringType().getSimpleName(), pjp.getSignature().getName(), System.currentTimeMillis() - start);
        return obj;
    }
}

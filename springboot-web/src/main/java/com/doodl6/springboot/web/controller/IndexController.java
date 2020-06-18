package com.doodl6.springboot.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.doodl6.springboot.client.api.FirstDubboService;
import com.doodl6.springboot.client.request.GetDubboInfoRequest;
import com.doodl6.springboot.client.response.GetDubboInfoResponse;
import com.doodl6.springboot.common.check.CheckUtil;
import com.doodl6.springboot.web.aspect.TraceIdHolder;
import com.doodl6.springboot.web.request.CheckParameterRequest;
import com.doodl6.springboot.web.response.CheckParameterResult;
import com.doodl6.springboot.web.response.base.BaseResponse;
import com.doodl6.springboot.web.response.base.MapResponse;
import com.doodl6.springboot.web.response.base.ResponseCode;
import com.google.common.collect.Maps;
import io.netty.util.internal.PlatformDependent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.JavaNioAccess;
import sun.misc.SharedSecrets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 首页控制类
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Reference(version = "${dubbo.reference.firstDubbo.version}", check = false)
    private FirstDubboService firstDubboService;

    /**
     * 普通接口
     */
    @RequestMapping("/hello")
    public MapResponse hello(String name) {
        MapResponse mapResponse = new MapResponse();

        mapResponse.appendData("name", name);

        return mapResponse;
    }

    /**
     * 获取dubbo信息
     */
    @RequestMapping("/getDubboInfo")
    public MapResponse getDubboInfo(Long id) {
        MapResponse mapResponse = new MapResponse();

        GetDubboInfoRequest getDubboInfoRequest = new GetDubboInfoRequest();
        getDubboInfoRequest.setTraceId(TraceIdHolder.getTraceId());
        getDubboInfoRequest.setId(id);
        GetDubboInfoResponse getDubboInfoResponse = firstDubboService.getDubboInfo(getDubboInfoRequest);
        if (getDubboInfoResponse.isSuccess()) {
            mapResponse.appendData("dubboInfo", getDubboInfoResponse.getDubboDomain());
        } else {
            mapResponse.setResult(ResponseCode.BIZ_ERROR);
            mapResponse.setMessage(getDubboInfoResponse.getErrorMsg());
        }


        return mapResponse;
    }

    /**
     * 参数校验
     */
    @RequestMapping("/parameterCheck")
    public BaseResponse<CheckParameterResult> parameterCheck(CheckParameterRequest request) {

        CheckUtil.check(request);

        CheckParameterResult result = new CheckParameterResult();
        result.setName(request.getName());
        result.setAge(request.getAge());
        result.setFavorites(request.getFavorites());
        BaseResponse<CheckParameterResult> response = new BaseResponse<>();
        response.setData(result);

        return response;
    }

    /**
     * 获取堆外内存使用情况
     */
    @RequestMapping(value = "/getDirectMemoryUsage")
    public BaseResponse<Map<String, Long>> getDirectMemoryUsage() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, Long> directMemoryUsageMap = Maps.newHashMap();

        Method getJavaNioAccessMethod = SharedSecrets.class.getDeclaredMethod("getJavaNioAccess");
        JavaNioAccess javaNioAccess = (JavaNioAccess) getJavaNioAccessMethod.invoke(SharedSecrets.class);
        directMemoryUsageMap.put("directMemoryUsage", javaNioAccess.getDirectBufferPool().getMemoryUsed());

        Field[] fields = PlatformDependent.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("DIRECT_MEMORY_LIMIT")) {
                field.setAccessible(true);
                directMemoryUsageMap.put("maxDirectMemorySize", field.getLong(PlatformDependent.class));
            } else if (field.getName().equals("DIRECT_MEMORY_COUNTER")) {
                field.setAccessible(true);
                directMemoryUsageMap.put("nettyDirectMemoryUsage", ((AtomicLong) field.get(PlatformDependent.class)).longValue());
            }
        }

        return BaseResponse.success(directMemoryUsageMap);
    }

    /**
     * 测试打印日志
     */
    @RequestMapping("/testLog")
    public BaseResponse<Void> testLog() {
        BaseResponse<Void> response = new BaseResponse<>();

        LOGGER.trace("test trace log");
        LOGGER.debug("test debug log");
        LOGGER.info("test info log");
        LOGGER.warn("test warn log");
        LOGGER.error("test error log");

        return response;
    }

}

package com.doodl6.springboot.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.google.common.collect.Maps;
import io.netty.util.internal.PlatformDependent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.JavaNioAccess;
import sun.misc.SharedSecrets;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 监控控制类
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    /**
     * 获取堆内存使用情况
     */
    @GetMapping(value = "/getHeapMemoryUsage")
    public BaseResponse<JSONArray> getHeapMemoryUsage() {
        List<MemoryPoolMXBean> memoryMXBeanList = ManagementFactory.getMemoryPoolMXBeans();
        JSONArray memoryUsageArray = new JSONArray();
        for (MemoryPoolMXBean memoryPoolMXBean : memoryMXBeanList) {
            JSONObject memoryUsageJSON = new JSONObject();

            //内存类型
            memoryUsageJSON.put("type", memoryPoolMXBean.getType().name());
            memoryUsageJSON.put("name", memoryPoolMXBean.getName());
            MemoryUsage memoryUsage = memoryPoolMXBean.getUsage();
            //最大可用内存
            memoryUsageJSON.put("maxMemorySize", memoryUsage.getMax() / 1024 / 1024 + "MB");
            //已提交的内存
            memoryUsageJSON.put("committedMemorySize", memoryUsage.getCommitted() / 1024 / 1024 + "MB");
            //已使用的内存
            memoryUsageJSON.put("usedMemorySize", memoryUsage.getUsed() / 1024 / 1024 + "MB");
            memoryUsageArray.add(memoryUsageJSON);
        }

        return BaseResponse.success(memoryUsageArray);
    }

    /**
     * 获取直接内存使用情况
     */
    @GetMapping(value = "/getDirectMemoryUsage")
    public BaseResponse<Map<String, String>> getDirectMemoryUsage() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, String> directMemoryUsageMap = Maps.newHashMap();

        //最大可使用的直接内存大小
        directMemoryUsageMap.put("maxDirectMemorySize", PlatformDependent.maxDirectMemory() / 1024 / 1024 + "MB");

        //netty自行管理已使用的直接内存使用量
        long usedDirectMemory = PlatformDependent.usedDirectMemory();
        directMemoryUsageMap.put("nettyDirectMemoryUsage", usedDirectMemory == -1 ? "-1" : usedDirectMemory / 1024 / 1024 + "MB");

        //通过jdk申请分配的直接内存使用量
        Method getJavaNioAccessMethod = SharedSecrets.class.getDeclaredMethod("getJavaNioAccess");
        JavaNioAccess javaNioAccess = (JavaNioAccess) getJavaNioAccessMethod.invoke(SharedSecrets.class);
        directMemoryUsageMap.put("directMemoryUsage", javaNioAccess.getDirectBufferPool().getMemoryUsed() / 1024 / 1024 + "MB");

        return BaseResponse.success(directMemoryUsageMap);
    }
}

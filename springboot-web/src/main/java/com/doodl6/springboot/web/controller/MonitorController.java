package com.doodl6.springboot.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doodl6.springboot.web.response.base.BaseResponse;
import com.google.common.collect.Maps;
import io.netty.util.internal.PlatformDependent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.JavaNioAccess;
import sun.misc.SharedSecrets;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 监控控制类
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController extends BaseController {

    /**
     * 获取堆内存使用情况
     */
    @RequestMapping(value = "/getHeapMemoryUsage")
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
            memoryUsageJSON.put("maxMemorySize", memoryUsage.getMax());
            //已提交的内存
            memoryUsageJSON.put("committedMemorySize", memoryUsage.getCommitted());
            //已使用的内存
            memoryUsageJSON.put("usedMemorySize", memoryUsage.getUsed());
            memoryUsageArray.add(memoryUsageJSON);
        }

        return BaseResponse.success(memoryUsageArray);
    }

    /**
     * 获取堆外内存使用情况
     */
    @RequestMapping(value = "/getDirectMemoryUsage")
    public BaseResponse<Map<String, Long>> getDirectMemoryUsage() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, Long> directMemoryUsageMap = Maps.newHashMap();

        Method getJavaNioAccessMethod = SharedSecrets.class.getDeclaredMethod("getJavaNioAccess");
        JavaNioAccess javaNioAccess = (JavaNioAccess) getJavaNioAccessMethod.invoke(SharedSecrets.class);
        //通过jdk申请分配的直接内存使用量
        directMemoryUsageMap.put("directMemoryUsage", javaNioAccess.getDirectBufferPool().getMemoryUsed());

        Field[] fields = PlatformDependent.class.getDeclaredFields();
        for (Field field : fields) {
            if ("DIRECT_MEMORY_LIMIT".equals(field.getName())) {
                field.setAccessible(true);
                //最大可使用的直接内存大小
                directMemoryUsageMap.put("maxDirectMemorySize", field.getLong(PlatformDependent.class));
            } else if ("DIRECT_MEMORY_COUNTER".equals(field.getName())) {
                field.setAccessible(true);
                //netty自行管理已使用的直接内存使用量
                directMemoryUsageMap.put("nettyDirectMemoryUsage", ((AtomicLong) field.get(PlatformDependent.class)).longValue());
            }
        }

        return BaseResponse.success(directMemoryUsageMap);
    }
}

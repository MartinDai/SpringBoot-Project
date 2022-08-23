package com.doodl6.springboot.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doodl6.springboot.common.web.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

/**
 * 监控控制类
 */
@Api(tags = "监控相关")
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    /**
     * 获取堆内存使用情况
     */
    @ApiOperation("获取堆内存使用情况")
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

}

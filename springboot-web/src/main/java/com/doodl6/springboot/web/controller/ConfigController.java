package com.doodl6.springboot.web.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置控制类
 */
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @NacosValue(value = "${maxPageSize:20}", autoRefreshed = true)
    private int maxPageSize;

    @GetMapping(value = "/getMaxPageSize")
    public int getMaxPageSize() {
        return maxPageSize;
    }
}

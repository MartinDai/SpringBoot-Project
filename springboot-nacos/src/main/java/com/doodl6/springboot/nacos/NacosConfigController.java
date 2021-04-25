package com.doodl6.springboot.nacos;

import com.doodl6.springboot.nacos.service.NacosConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Nacos配置控制类
 */
@RestController
@RequestMapping("/nacosConfig")
public class NacosConfigController {

    @Resource
    private NacosConfigService nacosConfigService;

    @GetMapping(value = "/getMaxPageSize")
    public int getMaxPageSize() {
        return nacosConfigService.getMaxPageSize();
    }
}

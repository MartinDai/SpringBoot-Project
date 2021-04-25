package com.doodl6.springboot.nacos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * nacos配置服务
 */
@Service
@RefreshScope
public class NacosConfigService {

    @Value("${maxPageSize:20}")
    private int maxPageSize;

    public int getMaxPageSize() {
        return maxPageSize;
    }
}

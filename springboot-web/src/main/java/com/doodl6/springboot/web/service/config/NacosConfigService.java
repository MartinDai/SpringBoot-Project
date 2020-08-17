package com.doodl6.springboot.web.service.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Service;

/**
 * nacos配置服务
 */
@Service
public class NacosConfigService {

    @NacosValue(value = "${maxPageSize:20}", autoRefreshed = true)
    private int maxPageSize;

    public int getMaxPageSize() {
        return maxPageSize;
    }
}

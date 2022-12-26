package com.doodl6.springboot.dubbo.provider.config;

import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDubbo(scanBasePackages = "com.doodl6.springboot.dubbo.provider.service")
public class DubboConfig {

    @Value("${dubbo.protocol.rest.port}")
    private int restPort;

    @Value("${dubbo.protocol.dubbo.port}")
    private int dubboPort;

    @Bean
    public ProtocolConfig restProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("rest");
        protocolConfig.setPort(restPort);
        protocolConfig.setServer("netty");
        return protocolConfig;
    }

    @Bean
    public ProtocolConfig dubboProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(dubboPort);
        protocolConfig.setServer("netty");
        return protocolConfig;
    }
}

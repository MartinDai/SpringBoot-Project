package com.doodl6.springboot.feign.consumer;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.doodl6.springboot.feign.consumer.service")
public class FeignAutoConfig {
}

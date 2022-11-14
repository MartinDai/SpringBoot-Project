package com.doodl6.springboot.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
@EnableDubbo(scanBasePackages = "com.doodl6.springboot.dubbo")
public class DubboAutoConfig {
}

package com.doodl6.springboot.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = "com.doodl6.springboot")
@MapperScan("com.doodl6.springboot.dao.api")
@EnableElasticsearchRepositories(basePackages = "com.doodl6.springboot.elasticsearch.repository")
@ServletComponentScan(basePackages = "com.doodl6.springboot.common.web.filter")
@EnableCaching
@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.doodl6.springboot.feign.consumer.service")
public class WebApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(WebApplication.class, args);
    }

}

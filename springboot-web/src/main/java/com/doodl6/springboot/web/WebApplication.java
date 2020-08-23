package com.doodl6.springboot.web;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication(scanBasePackages = "com.doodl6")
@NacosPropertySource(dataId = "doodl6", groupId = "SpringBoot", autoRefreshed = true)
@MapperScan("com.doodl6.springboot.dao.api")
@ServletComponentScan
@EnableCaching
@EnableHystrix
public class WebApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(WebApplication.class, args);
    }

}

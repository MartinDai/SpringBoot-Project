package com.doodl6.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.doodl6.springboot")
@ServletComponentScan(basePackages = "com.doodl6.springboot.web.listener")
public class WebApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(WebApplication.class, args);
    }
}

package com.doodl6.springboot.common.web;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan(basePackages = "com.doodl6.springboot.common.web.filter")
public class WebAutoConfig {
}

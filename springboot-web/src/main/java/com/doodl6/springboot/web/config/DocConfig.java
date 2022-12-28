package com.doodl6.springboot.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("SpringBoot-Project项目的接口文档"));
    }
}

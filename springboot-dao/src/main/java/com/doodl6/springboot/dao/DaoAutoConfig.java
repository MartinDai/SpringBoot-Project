package com.doodl6.springboot.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.doodl6.springboot.dao.mapper")
public class DaoAutoConfig {
}

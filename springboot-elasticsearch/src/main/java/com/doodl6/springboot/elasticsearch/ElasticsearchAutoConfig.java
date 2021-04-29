package com.doodl6.springboot.elasticsearch;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.doodl6.springboot.elasticsearch.repository")
public class ElasticsearchAutoConfig {
}

package com.doodl6.springboot.web.service.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * ES配置
 */
//@Configuration
//@PropertySource("classpath:elasticSearch.properties")
public class ElasticSearchConfig {

    @Value("${httpHost.host}")
    private String host;

    @Value("${httpHost.port}")
    private int port;

    @Value("${httpHost.schema}")
    private String schema;

    @Bean
    public HttpHost httpHost() {
        return new HttpHost(host, port, schema);
    }

    @Bean
    public RestHighLevelClient getRHLClient(HttpHost httpHost) {
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }

}

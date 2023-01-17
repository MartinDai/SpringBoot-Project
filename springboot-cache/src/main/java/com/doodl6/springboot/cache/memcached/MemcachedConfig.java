package com.doodl6.springboot.cache.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MemcachedConfig {

    @Value("${memcached.address}")
    private String address;

    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(address), new int[]{1});
        //存储的数据使用JSON格式
        Transcoder<Object> jsonTranscoder = new JSONTranscoder();
        builder.setTranscoder(jsonTranscoder);
        return builder.build();
    }

}

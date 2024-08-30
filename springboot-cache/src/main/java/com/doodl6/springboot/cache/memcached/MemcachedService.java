package com.doodl6.springboot.cache.memcached;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;

/**
 * 基于xmemcached实现
 */
@Component
public class MemcachedService {

    @Resource
    private MemcachedClient memcachedClient;

    public void set(String key, Object value) {
        try {
            memcachedClient.set(key, 1000, value);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
        }
    }

    public <T> T get(String key, Class<T> type) {
        try {
            GetsResponse<String> getsResponse = memcachedClient.gets(key, memcachedClient.getTranscoder());
            String value = getsResponse.getValue();
            if (value != null) {
                return JSON.parseObject(value, type);
            }
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
        }

        return null;
    }

}

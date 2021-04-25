package com.doodl6.springboot.cache.memcached;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.cache.memcached.base.MemCachedService;
import com.whalin.MemCached.MemCachedClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Memcached-Java-Client实现
 * Created by daixiaoming on 2018/5/6.
 */
@Component
public class MemCachedImpl implements MemCachedService {

    @Resource
    private MemCachedClient memCachedClient;

    @Override
    public void set(String key, Object value) {
        memCachedClient.set(key, value);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        String value = (String) memCachedClient.get(key);
        if (value != null) {
            return JSON.parseObject(value, type);
        }
        return null;
    }
}

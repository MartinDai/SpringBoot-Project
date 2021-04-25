package com.doodl6.springboot.cache.memcached.base;

/**
 * memcached服务接口
 * Created by daixiaoming on 2018/5/12.
 */
public interface MemCachedService {

    void set(String key, Object value);

    <T> T get(String key, Class<T> type);
}

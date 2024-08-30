package com.doodl6.springboot.cache.redis;

import jakarta.annotation.Resource;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * redis服务接口
 * Created by daixiaoming on 2018-12-06.
 */
@Service
public class RedisService {

    /**
     * 使用redisson操作
     */
    @Resource
    private RedissonClient redissonClient;

    /**
     * 设置值，不设置过期时间
     */
    public void set(String key, Object value) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    /**
     * 设置值，设置过期时间
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value, timeout, unit);
    }

    /**
     * 获取锁
     */
    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    /**
     * 自增值，不设置过期时间
     */
    public long addAndGet(String key, long delta) {
        RAtomicLong value = redissonClient.getAtomicLong(key);
        return value.addAndGet(delta);
    }

    /**
     * 自增值，设置过期时间
     */
    public long addAndGet(String key, long delta, long timeout, TimeUnit unit) {
        RAtomicLong value = redissonClient.getAtomicLong(key);
        long newValue = value.addAndGet(delta);
        value.expire(timeout, unit);
        return newValue;
    }

    /**
     * 获取指定key的值
     */
    public <T> T get(String key) {
        return (T) redissonClient.getBucket(key).get();
    }

    /**
     * 往列表添加数据
     */
    public <T> void pushToList(String key, T value) {
        RList<T> list = redissonClient.getList(key);
        list.add(value);
    }

    /**
     * 往列表批量添加数据
     */
    public <T> void pushToList(String key, Collection<T> values) {
        RList<T> list = redissonClient.getList(key);
        list.addAll(values);
    }

    /**
     * 获取列表长度
     */
    public long size(String key) {
        RList<Object> list = redissonClient.getList(key);
        return list.size();
    }

    /**
     * 从列表弹出数据
     */
    public <T> T popFromList(String key) {
        RList<T> list = redissonClient.getList(key);
        return list.remove(0);
    }

    /**
     * 给布隆过滤器添加值
     */
    public <T> void addByBloomFilter(String key, T value) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(key);
        bloomFilter.tryInit(1000,0.03);
        bloomFilter.add(value);
    }

    /**
     * 判断布隆过滤器是否存在指定的值
     */
    public <T> boolean includeByBloomFilter(String key, T value) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(key);
        return bloomFilter.contains(value);
    }
}

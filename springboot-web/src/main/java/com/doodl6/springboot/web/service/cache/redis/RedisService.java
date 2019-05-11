package com.doodl6.springboot.web.service.cache.redis;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis服务
 * Created by daixiaoming on 2018-12-06.
 */
@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final Long SUCCESS = 1L;

    /**
     * 设置值，不设置过期时间
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置值，设置过期时间
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 如果不存在则设置值
     */
    public boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);

        //成功
        if (result) {
            redisTemplate.expire(key, timeout, unit);
        }

        return result;
    }

    /**
     * 获取锁
     */
    public boolean getLock(String key, String value, int expireTime) {
        String script = "if redis.call('setNX',KEYS[1],ARGV[1]) == 1 then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Lists.newArrayList(key), value, expireTime);

        return SUCCESS.equals(result);
    }

    /**
     * 解锁
     */
    public boolean releaseLock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Lists.newArrayList(key), value);

        return SUCCESS.equals(result);
    }

    /**
     * 自增值，不设置过期时间
     */
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自增值，设置过期时间
     */
    public long increment(String key, long delta, long timeout, TimeUnit unit) {
        long result = redisTemplate.opsForValue().increment(key, delta);

        //首次自增
        if (result == delta) {
            redisTemplate.expire(key, timeout, unit);
        }

        return result;
    }

    /**
     * 同时设置多个值，不设置过期时间
     */
    public void multiSet(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 同时设置多个值，设置过期时间
     */
    public void multiSet(Map<String, Object> map, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().multiSet(map);
        for (String key : map.keySet()) {
            redisTemplate.expire(key, timeout, unit);
        }
    }

    /**
     * 获取指定key的值
     */
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 列表左边push
     */
    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 列表左边批量push
     */
    public void leftPushAll(String key, Collection<Object> values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 获取列表长度
     */
    public long size(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 列表右边pop
     */
    public <T> T rightPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            if (!redisTemplate.opsForValue().getBit(key, i)) {
                return false;
            }
        }

        return true;
    }
}

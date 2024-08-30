package com.doodl6.springboot.cache;

import com.doodl6.springboot.cache.memcached.MemcachedService;
import com.doodl6.springboot.cache.redis.RedisService;
import com.doodl6.springboot.cache.vo.Model;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.common.web.response.MapResponse;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 缓存控制类
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Resource
    private MemcachedService memcachedService;

    @Resource
    private RedisService redisService;

    /**
     * 设置缓存数据到memcached
     */
    @PostMapping("/setToMemcached")
    public BaseResponse<Map<String, Object>> setToMemcached(String key, String value) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        Assert.isTrue(StringUtils.isNotBlank(value), "value不能为空");
        MapResponse mapResponse = new MapResponse();

        Model model = new Model(key, value);
        memcachedService.set(key, model);
        model = memcachedService.get(key, Model.class);
        mapResponse.appendData("key", model);

        return mapResponse;
    }

    /**
     * 从memcached获取缓存数据
     */
    @GetMapping("/getFromMemcached")
    public MapResponse getFromMemcached(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        MapResponse mapResponse = new MapResponse();

        Model value = memcachedService.get(key, Model.class);
        mapResponse.appendData("key", value);

        return mapResponse;
    }

    /**
     * 设置缓存数据到redis
     */
    @PostMapping("/setToRedis")
    public BaseResponse<Map<String, Object>> setToRedis(String key, String value) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        Assert.isTrue(StringUtils.isNotBlank(value), "value不能为空");
        MapResponse mapResponse = new MapResponse();

        Model model = new Model(key, value);
        redisService.set(key, model);
        model = redisService.get(key);
        mapResponse.appendData(key, model);

        return mapResponse;
    }

    /**
     * 从redis获取缓存数据
     */
    @GetMapping("/getFromRedis")
    public MapResponse getFromRedis(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        MapResponse mapResponse = new MapResponse();

        Model value = redisService.get(key);
        mapResponse.appendData(key, value);

        return mapResponse;
    }

    /**
     * 操作redis自增
     */
    @PostMapping("/incrementToRedis")
    public BaseResponse<Map<String, Object>> incrementToRedis(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        MapResponse mapResponse = new MapResponse();

        long result = redisService.addAndGet(key, 1);
        mapResponse.appendData("result", result);

        return mapResponse;
    }

    /**
     * 操作redis左push
     */
    @PostMapping("/leftPushToRedis")
    public BaseResponse<Map<String, Object>> leftPushToRedis(String key, String value) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        Assert.isTrue(StringUtils.isNotBlank(value), "value不能为空");
        MapResponse mapResponse = new MapResponse();

        redisService.pushToList(key, value);
        long size = redisService.size(key);
        mapResponse.appendData("size", size);

        return mapResponse;
    }

    /**
     * 操作redis右pop
     */
    @GetMapping("/rightPopToRedis")
    public BaseResponse<Map<String, Object>> rightPopToRedis(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        MapResponse mapResponse = new MapResponse();

        String result = redisService.popFromList(key);
        mapResponse.appendData(key, result);

        return mapResponse;
    }

    /**
     * 把数据放入bloomFilter
     */
    @PostMapping("/putIntoBloomFilter")
    public MapResponse putIntoBloomFilter(String key, String value) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        Assert.isTrue(StringUtils.isNotBlank(value), "value不能为空");
        MapResponse mapResponse = new MapResponse();

        Model model = new Model(key, value);
        redisService.addByBloomFilter("modelFilter", model);

        return mapResponse;
    }

    /**
     * 检查数据是否在bloomFilter中
     */
    @GetMapping("/checkIncludeBloomFilter")
    public MapResponse checkIncludeBloomFilter(String key, String value) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        Assert.isTrue(StringUtils.isNotBlank(value), "value不能为空");
        MapResponse mapResponse = new MapResponse();

        Model model = new Model(key, value);
        boolean include = redisService.includeByBloomFilter("modelFilter", model);
        mapResponse.appendData("include", include);

        return mapResponse;
    }

    /**
     * 测试redis锁
     *
     * @param expireTime 过期时间，单位：秒
     */
    @PostMapping("/testRedisLock")
    public MapResponse testRedisLock(String key, Integer expireTime) throws InterruptedException {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        Assert.notNull(expireTime, "过期时间不能为空");
        MapResponse mapResponse = new MapResponse();

        RLock lock = redisService.getLock(key);
        long start = System.currentTimeMillis();
        lock.lock();
        long lockCost = System.currentTimeMillis() - start;
        try {
            Thread.sleep(expireTime * 1000);
        } finally {
            lock.unlock();
        }
        mapResponse.appendData("lockCost", lockCost);
        mapResponse.appendData("totalCost", System.currentTimeMillis() - start);

        return mapResponse;
    }

}

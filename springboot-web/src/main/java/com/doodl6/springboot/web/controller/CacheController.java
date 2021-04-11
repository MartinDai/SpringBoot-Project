package com.doodl6.springboot.web.controller;

import com.doodl6.springboot.web.response.base.BaseResponse;
import com.doodl6.springboot.web.response.base.MapResponse;
import com.doodl6.springboot.web.service.cache.memcached.base.MemCachedService;
import com.doodl6.springboot.web.service.cache.redis.RedisService;
import com.doodl6.springboot.web.service.cache.vo.Model;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 缓存控制类
 */
@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {

    @Resource
    private MemCachedService memCachedImpl;

    @Resource
    private MemCachedService xmemcachedImpl;

    @Resource
    private RedisService redisService;

    /**
     * 设置缓存数据到memcached
     */
    @RequestMapping("/setToMemcached")
    public BaseResponse<Map<String, Object>> setToMemcached(String key, String value) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        Assert.isTrue(StringUtils.isNotBlank(value), "value不能为空");
        MapResponse mapResponse = new MapResponse();

        Model model = new Model(key, value);
        xmemcachedImpl.set(key, model);
        model = xmemcachedImpl.get(key, Model.class);
        mapResponse.appendData("key", model);

        return mapResponse;
    }

    /**
     * 从memcached获取缓存数据
     */
    @RequestMapping("/getFromMemcached")
    public MapResponse getFromMemcached(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        MapResponse mapResponse = new MapResponse();

        Model value = memCachedImpl.get(key, Model.class);
        mapResponse.appendData("key", value);

        return mapResponse;
    }

    /**
     * 设置缓存数据到redis
     */
    @RequestMapping("/setToRedis")
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
     * 操作redis自增
     */
    @RequestMapping("/incrementToRedis")
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
    @RequestMapping("/leftPushToRedis")
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
    @RequestMapping("/rightPopToRedis")
    public BaseResponse<Map<String, Object>> rightPopToRedis(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        MapResponse mapResponse = new MapResponse();

        String result = redisService.popFromList(key);
        mapResponse.appendData(key, result);

        return mapResponse;
    }

    /**
     * 从redis获取缓存数据
     */
    @RequestMapping("/getFromRedis")
    public MapResponse getFromRedis(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "key不能为空");
        MapResponse mapResponse = new MapResponse();

        Model value = redisService.get(key);
        mapResponse.appendData(key, value);

        return mapResponse;
    }

    /**
     * 把数据放入bloomFilter
     */
    @RequestMapping("/putIntoBloomFilter")
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
    @RequestMapping("/checkIncludeBloomFilter")
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
    @RequestMapping("/testRedisLock")
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

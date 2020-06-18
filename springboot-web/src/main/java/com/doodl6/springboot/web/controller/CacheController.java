package com.doodl6.springboot.web.controller;

import com.doodl6.springboot.web.response.base.BaseResponse;
import com.doodl6.springboot.web.response.base.MapResponse;
import com.doodl6.springboot.web.service.cache.memcached.base.MemCachedService;
import com.doodl6.springboot.web.service.cache.redis.BloomFilterHelper;
import com.doodl6.springboot.web.service.cache.redis.RedisService;
import com.doodl6.springboot.web.service.cache.vo.Model;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.hash.Funnel;
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

    private final BloomFilterHelper<Model> modelBloomFilterHelper = new BloomFilterHelper<>((Funnel<Model>) (from, into) -> into.putString(from.getKey(), Charsets.UTF_8).putString(from.getValue(), Charsets.UTF_8), 100, 0.01);

    /**
     * 设置缓存数据到memcached
     */
    @RequestMapping("/setToMemcached")
    public BaseResponse<Map<String, Object>> setToMemcached(String key, String value) {
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
        MapResponse mapResponse = new MapResponse();

        Model model = new Model(key, value);
        redisService.set(key, model);
        model = redisService.get(key);
        mapResponse.appendData(key, model);

        return mapResponse;
    }

    /**
     * 操作redis同时设置多个值
     */
    @RequestMapping("/multiSetToRedis")
    public BaseResponse<Map<String, Object>> multiSetToRedis(String key1, String value1, String key2, String value2) {
        MapResponse mapResponse = new MapResponse();

        Map<String, Object> map = Maps.newHashMap();
        map.put(key1, value1);
        map.put(key2, value2);
        redisService.multiSet(map);

        String result1 = redisService.get(key1);
        String result2 = redisService.get(key2);
        mapResponse.appendData(key1, result1);
        mapResponse.appendData(key2, result2);

        return mapResponse;
    }

    /**
     * 操作redis自增
     */
    @RequestMapping("/incrementToRedis")
    public BaseResponse<Map<String, Object>> incrementToRedis(String key) {
        MapResponse mapResponse = new MapResponse();

        long result = redisService.increment(key, 1);
        mapResponse.appendData("result", result);

        return mapResponse;
    }

    /**
     * 操作redis左push
     */
    @RequestMapping("/leftPushToRedis")
    public BaseResponse<Map<String, Object>> leftPushToRedis(String key, String value) {
        MapResponse mapResponse = new MapResponse();

        redisService.leftPush(key, value);
        long size = redisService.size(key);
        mapResponse.appendData("size", size);

        return mapResponse;
    }

    /**
     * 操作redis右pop
     */
    @RequestMapping("/rightPopToRedis")
    public BaseResponse<Map<String, Object>> rightPopToRedis(String key) {
        MapResponse mapResponse = new MapResponse();

        String result = redisService.rightPop(key);
        mapResponse.appendData(key, result);

        return mapResponse;
    }

    /**
     * 从redis获取缓存数据
     */
    @RequestMapping("/getFromRedis")
    public MapResponse getFromRedis(String key) {
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
        MapResponse mapResponse = new MapResponse();

        Model model = new Model(key, value);
        redisService.addByBloomFilter(modelBloomFilterHelper, "modelFilter", model);

        return mapResponse;
    }

    /**
     * 检查数据是否在bloomFilter中
     */
    @RequestMapping("/checkIncludeBloomFilter")
    public MapResponse checkIncludeBloomFilter(String key, String value) {
        MapResponse mapResponse = new MapResponse();

        Model model = new Model(key, value);
        boolean include = redisService.includeByBloomFilter(modelBloomFilterHelper, "modelFilter", model);
        mapResponse.appendData("include", include);

        return mapResponse;
    }

    /**
     * 获取redis锁
     */
    @RequestMapping("/getRedisLock")
    public MapResponse getRedisLock(String key, String value, Integer expireTime) {
        MapResponse mapResponse = new MapResponse();

        boolean success = redisService.getLock(key, value, expireTime);
        mapResponse.appendData("success", success);

        return mapResponse;
    }

    /**
     * 释放redis锁
     */
    @RequestMapping("/releaseRedisLock")
    public MapResponse releaseRedisLock(String key, String value) {
        MapResponse mapResponse = new MapResponse();

        boolean success = redisService.releaseLock(key, value);
        mapResponse.appendData("success", success);

        return mapResponse;
    }

}

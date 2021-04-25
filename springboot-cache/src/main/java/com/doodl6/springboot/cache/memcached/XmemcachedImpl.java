package com.doodl6.springboot.cache.memcached;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.cache.memcached.base.MemCachedService;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeoutException;

/**
 * xmemcached实现
 * Created by daixiaoming on 2018/5/12.
 */
@Component
public class XmemcachedImpl implements MemCachedService {

    @Resource
    private MemcachedClient memcachedClient;

    @Override
    public void set(String key, Object value) {
        try {
            memcachedClient.set(key, 1000, value);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    public static void main(String[] args) {
        XmemcachedImpl xmemcached = new XmemcachedImpl();
        xmemcached.set("a", "111");
        System.out.println(xmemcached.get("a", String.class));
    }
}

package com.doodl6.springboot.cache.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
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
    public MemCachedClient memCachedClient() {

        Integer[] weights = {1};

        // grab an instance of our connection pool
        SockIOPool pool = SockIOPool.getInstance();

        // set the servers and the weights
        pool.setServers(new String[]{address});
        pool.setWeights(weights);

        // set some basic pool settings
        // 5 initial, 5 min, and 250 max conns
        // and set the max idle time for a conn
        // to 6 hours
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 * 6);

        // set the sleep for the maint thread
        // it will wake up every x seconds and
        // maintain the pool size
        pool.setMaintSleep(30);

        // set some TCP settings
        // disable nagle
        // set the read timeout to 3 secs
        // and don't set a connect timeout
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);

        // initialize the connection pool
        pool.initialize();

        //存储的数据使用JSON格式，兼容不同客户端,如果是单一客户端可以不需要
        MemCachedClient memCachedClient = new MemCachedClient();
        memCachedClient.setTransCoder(new JSONObjectTransCoder());

        return memCachedClient;
    }

    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        MemcachedClientBuilder builder =
                new XMemcachedClientBuilder(AddrUtil.
                        getAddresses(address), new int[]{1});
        //存储的数据使用JSON格式，兼容不同客户端,如果是单一客户端可以不需要
        Transcoder JSON_TRANSCODER = new JSONTranscoder();
        builder.setTranscoder(JSON_TRANSCODER);

        return builder.build();
    }

}

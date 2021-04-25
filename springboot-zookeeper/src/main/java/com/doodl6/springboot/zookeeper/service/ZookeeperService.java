package com.doodl6.springboot.zookeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by daixiaoming on 2019/5/22.
 */
@Slf4j
@Service
public class ZookeeperService implements InitializingBean {

    private static final String ZK_LOCK_PATH = "/zkLock";

    private static final int LOCK_WAIT = 1;

    @Value("${zookeeper.address}")
    private String zkAddress;

    private InterProcessMutex xLock;

    private InterProcessReadWriteLock readWriteLock;

    public boolean tryGetXLock() throws Exception {
        return xLock.acquire(LOCK_WAIT, TimeUnit.SECONDS);
    }

    public boolean tryGetWriteLock() throws Exception {
        return readWriteLock.writeLock().acquire(LOCK_WAIT, TimeUnit.SECONDS);
    }

    public boolean tryGetReadLock() throws Exception {
        return readWriteLock.readLock().acquire(LOCK_WAIT, TimeUnit.SECONDS);
    }

    public void releaseXLock() {
        try {
            xLock.release();
        } catch (Exception e) {
            log.error("释放独占锁异常", e);
        }
    }

    public void releaseWriteLock() {
        try {
            readWriteLock.writeLock().release();
        } catch (Exception e) {
            log.error("释放写锁异常", e);
        }
    }

    public void releaseReadLock() {
        try {
            readWriteLock.readLock().release();
        } catch (Exception e) {
            log.error("释放读锁异常", e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                zkAddress,
                new RetryNTimes(10, 5000)
        );
        client.start();

        xLock = new InterProcessMutex(client, ZK_LOCK_PATH);
        readWriteLock = new InterProcessReadWriteLock(client, ZK_LOCK_PATH);
    }
}

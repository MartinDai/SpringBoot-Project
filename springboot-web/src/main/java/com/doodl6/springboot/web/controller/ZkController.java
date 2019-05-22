package com.doodl6.springboot.web.controller;

import com.doodl6.springboot.web.response.base.BaseResponse;
import com.doodl6.springboot.web.service.zk.ZookeeperService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 操作zookeeper的相关操作
 */
@RestController
@RequestMapping("/zk")
public class ZkController extends BaseController {

    @Resource
    private ZookeeperService zookeeperService;

    /**
     * 获取独占锁，然后执行业务
     */
    @RequestMapping("/doXLockBiz")
    public BaseResponse<String> doLockBiz() {
        String result;
        try {
            if (zookeeperService.tryGetXLock()) {
                for (int i = 0; i < 60; i++) {
                    Thread.sleep(1000L);
                    System.out.println("独占锁做业务中:" + i);
                }
                result = "拿锁成功，执行完成";
            } else {
                result = "拿锁失败，请稍后重试";
            }
        } catch (Exception e) {
            LOGGER.error("执行zk独占锁业务异常", e);
            result = "执行异常";
        } finally {
            zookeeperService.releaseXLock();
        }

        return BaseResponse.success(result);
    }

    /**
     * 获取写锁，然后执行业务
     */
    @RequestMapping("/doWriteLockBiz")
    public BaseResponse<String> doWriteLockBiz() {
        String result;
        try {
            if (zookeeperService.tryGetWriteLock()) {
                for (int i = 0; i < 60; i++) {
                    Thread.sleep(1000L);
                    System.out.println("写锁做业务中:" + i);
                }
                result = "拿锁成功，执行完成";
            } else {
                result = "拿锁失败，请稍后重试";
            }
        } catch (Exception e) {
            LOGGER.error("执行zk写锁业务异常", e);
            result = "执行异常";
        } finally {
            zookeeperService.releaseWriteLock();
        }

        return BaseResponse.success(result);
    }

    /**
     * 获取读锁，然后执行业务
     */
    @RequestMapping("/doReadLockBiz")
    public BaseResponse<String> doReadLockBiz() {
        String result;
        try {
            if (zookeeperService.tryGetReadLock()) {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(1000L);
                    System.out.println("读锁做业务中:" + i);
                }
                result = "拿锁成功，执行完成";
            } else {
                result = "拿锁失败，请稍后重试";
            }
        } catch (Exception e) {
            LOGGER.error("执行zk读锁业务异常", e);
            result = "执行异常";
        } finally {
            zookeeperService.releaseReadLock();
        }

        return BaseResponse.success(result);
    }

}

package com.doodl6.springboot.zookeeper;

import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.zookeeper.service.ZookeeperService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作zookeeper的相关操作
 */
@Slf4j
@RestController
@RequestMapping("/zookeeper")
public class ZookeeperController {

    @Resource
    private ZookeeperService zookeeperService;

    /**
     * 获取独占锁，然后执行业务
     */
    @PostMapping("/doXLockBiz")
    public BaseResponse<String> doLockBiz() {
        String result;
        boolean lockSuccess = false;
        try {
            lockSuccess = zookeeperService.tryGetXLock();
            if (lockSuccess) {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000L);
                    System.out.println("独占锁做业务中:" + i);
                }
                result = "拿锁成功，执行完成";
            } else {
                result = "拿锁失败，请稍后重试";
            }
        } catch (Exception e) {
            log.error("执行zk独占锁业务异常", e);
            result = "执行异常";
        } finally {
            if (lockSuccess) {
                zookeeperService.releaseXLock();
            }
        }

        return BaseResponse.success(result);
    }

    /**
     * 获取写锁，然后执行业务
     */
    @PostMapping("/doWriteLockBiz")
    public BaseResponse<String> doWriteLockBiz() {
        String result;
        boolean lockSuccess = false;
        try {
            lockSuccess = zookeeperService.tryGetWriteLock();
            if (lockSuccess) {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000L);
                    System.out.println("写锁做业务中:" + i);
                }
                result = "拿锁成功，执行完成";
            } else {
                result = "拿锁失败，请稍后重试";
            }
        } catch (Exception e) {
            log.error("执行zk写锁业务异常", e);
            result = "执行异常";
        } finally {
            if (lockSuccess) {
                zookeeperService.releaseWriteLock();
            }
        }

        return BaseResponse.success(result);
    }

    /**
     * 获取读锁，然后执行业务
     */
    @PostMapping("/doReadLockBiz")
    public BaseResponse<String> doReadLockBiz() {
        String result;
        boolean lockSuccess = false;
        try {
            lockSuccess = zookeeperService.tryGetReadLock();
            if (lockSuccess) {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(1000L);
                    System.out.println("读锁做业务中:" + i);
                }
                result = "拿锁成功，执行完成";
            } else {
                result = "拿锁失败，请稍后重试";
            }
        } catch (Exception e) {
            log.error("执行zk读锁业务异常", e);
            result = "执行异常";
        } finally {
            if (lockSuccess) {
                zookeeperService.releaseReadLock();
            }
        }

        return BaseResponse.success(result);
    }

}

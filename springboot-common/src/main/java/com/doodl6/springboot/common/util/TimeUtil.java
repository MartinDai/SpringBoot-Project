package com.doodl6.springboot.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 时间工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeUtil {

    /**
     * 根据给定的开始时间，返回截止到现在的时间间隔（单位：毫秒）
     */
    public static long getIntervalTimeMillis(long startTime) {
        return (System.currentTimeMillis() - startTime);
    }

    /**
     * 根据给定的开始时间，返回截止到现在的时间间隔（单位：毫秒）
     */
    public static String getIntervalTimeMillisStr(long startTime) {
        return "耗时:" + getIntervalTimeMillis(startTime) + "毫秒";
    }

    /**
     * 根据给定的开始时间，返回截止到现在的时间间隔（单位：秒,保留两位小数）
     */
    public static double getIntervalTime(long startTime) {
        return Math.round(getIntervalTimeMillis(startTime) / 10.0) / 100.0;
    }

    /**
     * 根据给定的开始时间，返回截止到现在的时间间隔（单位：秒,保留两位小数）
     */
    public static String getIntervalTimeStr(long startTime) {
        return "耗时:" + getIntervalTime(startTime) + "秒";
    }

}

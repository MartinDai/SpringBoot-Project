package com.doodl6.springboot.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 数字工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberUtil {

    /**
     * 限制数值不超过范围
     */
    public static long limitNumber(long num, long min, long max) {
        if (num < min) {
            num = min;
        } else if (num > max) {
            num = max;
        }

        return num;
    }

    /**
     * 限制数值不超过范围
     */
    public static int limitNumber(int num, int min, int max) {
        num = limitMinNumber(num, min);
        num = limitMaxNumber(num, max);
        return num;
    }

    /**
     * 限制数值最最小
     */
    public static int limitMinNumber(int num, int min) {
        if (num < min) {
            num = min;
        }

        return num;
    }

    /**
     * 限制数值最大值
     */
    public static int limitMaxNumber(int num, int max) {
        if (num > max) {
            num = max;
        }

        return num;
    }

}

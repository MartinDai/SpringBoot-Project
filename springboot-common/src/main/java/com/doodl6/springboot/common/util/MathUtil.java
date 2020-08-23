package com.doodl6.springboot.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by daixiaoming on 2018/9/25.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtil {

    /**
     * 获取数组元素的最小公倍数
     */
    public static int lcm(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int length = array.length;
        int max = 0;
        //先找出这n个数的那个最大的数
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        while (true) {
            //设置标记
            boolean flag = true;
            for (int num : array) {
                if (max % num != 0) {
                    //只要有一个数不能整除max则令标记为false
                    flag = false;
                    break;
                }
            }
            //如果标记为true说明该max可以整除这n个数
            if (flag) {
                return max;
            }
            //最大数+1
            max++;
        }
    }
}

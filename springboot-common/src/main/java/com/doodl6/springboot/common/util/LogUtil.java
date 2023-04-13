package com.doodl6.springboot.common.util;

import com.alibaba.fastjson2.JSON;
import com.doodl6.springboot.common.model.Page;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 日志工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtil {

    /**
     * 构建日志字符串
     */
    public static String buildLog(Object... objArray) {
        StringBuilder logBuilder = new StringBuilder();

        for (Object obj : objArray) {
            if (logBuilder.length() > 0) {
                logBuilder.append(" | ");
            }

            if (obj instanceof String) {
                logBuilder.append(obj);
            } else {
                logBuilder.append(JSON.toJSONString(obj));
            }
        }

        return logBuilder.toString();
    }

    public static void main(String[] args) {
        Page<Object> page = new Page<>();
        System.out.println(JSON.toJSONString(page));
    }
}

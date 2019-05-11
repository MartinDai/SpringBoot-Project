package com.doodl6.springboot.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public final class StringUtil {

    private static Pattern BLANK_PATTERN = Pattern.compile("[\\s\\p{Zs}]");

    private StringUtil() {
    }

    /**
     * 去除空白字符串
     */
    public static String trimBlank(String title) {
        Matcher re = BLANK_PATTERN.matcher(title);
        title = re.replaceAll("");
        return title;
    }

    /**
     * 转换字符串列表为字符串
     */
    public static String convertListToString(List<?> stringList) {
        return convertListToString(stringList, ",");
    }

    public static String convertListToString(List<?> stringList, String splitStr) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(stringList)) {
            for (Object str : stringList) {
                if (sb.length() > 0) {
                    sb.append(splitStr);
                }
                sb.append(str);
            }
        }

        return sb.toString();
    }

    /**
     * 统计子字符串在指定字符串中出现的次数
     */
    public static int countSubString(String text, String sub) {
        int count = 0, start = 0;
        while ((start = text.indexOf(sub, start)) >= 0) {
            start += sub.length();
            count++;
        }
        return count;
    }

    /**
     * 使用逗号拆分Long类型的字符串为列表
     */
    public static List<Long> splitToLongList(String text) {
        return splitToLongList(text, ",");
    }

    /**
     * 使用指定的分隔符拆分Long类型的字符串为列表
     */
    public static List<Long> splitToLongList(String text, String separatorStr) {
        List<Long> longList = Lists.newArrayList();
        if (StringUtils.isNotBlank(text)) {
            String[] strArray = text.split(separatorStr);
            for (String str : strArray) {
                try {
                    longList.add(Long.valueOf(str));
                } catch (Exception ignored) {
                }
            }
        }
        return longList;
    }

    /**
     * 使用逗号拆分字符串类型的字符串为列表
     */
    public static List<String> splitToStringList(String text) {
        return splitToStringList(text, ",");
    }

    /**
     * 使用指定的分隔符拆分字符串类型的字符串为列表
     */
    public static List<String> splitToStringList(String text, String separatorStr) {
        List<String> stringList = Lists.newArrayList();
        if (StringUtils.isNotBlank(text)) {
            String[] strArray = text.split(separatorStr);
            Collections.addAll(stringList, strArray);
        }
        return stringList;
    }

    /**
     * 添加内容至Builder，并自动添加逗号作为分隔符
     */
    public static StringBuilder appendWidthSeparator(StringBuilder stringBuilder, Object content) {
        return appendWidthSeparator(stringBuilder, content, ",");
    }

    /**
     * 添加内容至Builder，并自动添加指定分隔字符
     */
    public static StringBuilder appendWidthSeparator(StringBuilder stringBuilder, Object content, String separator) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(separator);
        }

        stringBuilder.append(content);

        return stringBuilder;
    }

    public static void main(String[] args) {
        List<String> testList = Lists.newArrayList();
        testList.add("ss");
        testList.add("bb");
        System.out.println(convertListToString(testList));
    }

}


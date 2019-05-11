package com.doodl6.springboot.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 列表工具类
 */
public final class ListUtil {

    private ListUtil() {
    }

    /**
     * 分割List
     *
     * @param list     待分割的list
     * @param pageSize 每段list的大小
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        List<List<T>> splitList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(list)) {
            int listSize = list.size();
            if (listSize <= pageSize) {
                splitList.add(list.subList(0, listSize));
            } else {
                int fromIndex = 0 - pageSize;
                int toIndex = 0;
                do {
                    fromIndex += pageSize;
                    toIndex += pageSize;
                    if (toIndex > listSize) {
                        toIndex = listSize;
                    }
                    splitList.add(list.subList(fromIndex, toIndex));
                } while (toIndex != listSize);
            }
        }

        return splitList;
    }

    public static void main(String[] s) {

        List<String> stringList = Lists.newArrayList();
        for (int i = 0; i < 120; i++) {
            stringList.add("aa" + (i + 1));
        }
        List<List<String>> splitList = splitList(stringList, 17);

        int index = 1;
        for (List<String> subStringList : splitList) {
            System.out.println(index++);
            System.out.println("----------------------------------");
            for (String str : subStringList) {
                System.out.print(str + "\t");
            }
            System.out.println("END");
        }
    }

}
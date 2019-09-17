package com.doodl6.springboot.common.excel;

import com.doodl6.springboot.common.util.DateTimeUtil;
import com.doodl6.springboot.common.util.MathUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by daixiaoming on 2018/9/25.
 */
public class ExcelHelper {

    /**
     * 转换表头
     */
    public static List<Header> createHeaders(String[] heads) {
        List<Header> headerLst = Lists.newArrayList();
        for (String head : heads) {
            // 根据冒号分隔
            String[] headVector = head.split(":");
            if (headVector.length == 2) {
                Header header = new Header();
                header.setField(headVector[1]);
                header.setName(headVector[0]);
                headerLst.add(header);
            }
        }
        return headerLst;
    }

    /**
     * 转换数据
     */
    public static <T> List<ExcelData> parseExcelDataList(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return Lists.newArrayList();
        }

        List<ExcelData> list = Lists.newArrayList();
        for (T data : dataList) {
            ExcelData excelData = parseExcelData(data);
            list.add(excelData);
        }

        return list;
    }

    public static <T> ExcelData parseExcelData(T data) {
        Field[] declaredFields = data.getClass().getDeclaredFields();
        ExcelData excelData = new ExcelData(declaredFields.length);
        Map<String, List<String>> map = excelData.getDataMap();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            Object o;
            try {
                o = declaredField.get(data);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (o == null) {
                map.put(name, Lists.newArrayList());
            } else {
                if (o instanceof List) {
                    List<String> ll = (List<String>) o;
                    if (!ll.isEmpty()) {
                        excelData.setMergeCells(true);
                        int mergeRowCount = excelData.getMergeRowCount();
                        if (ll.size() > mergeRowCount) {
                            excelData.setMergeRowCount(ll.size());
                        }
                    }
                    map.put(name, ll);
                } else if (o instanceof LocalDateTime) {
                    List<String> values = new ArrayList<>();
                    values.add(DateTimeUtil.getDateTime((LocalDateTime) o));
                    map.put(name, values);
                } else {
                    map.put(name, Lists.newArrayList(o.toString()));
                }
            }
        }

        //计算最小公倍数,用于合并单元格
        if (excelData.isMergeCells()) {
            List<Integer> ls = Lists.newArrayList();
            Map<String, List<String>> data1 = excelData.getDataMap();
            Collection<List<String>> values = data1.values();
            for (List<String> value : values) {
                int size = value.size();
                if (size > 1) {
                    ls.add(size);
                }
            }
            if (CollectionUtils.isNotEmpty(ls)) {
                int[] ints = new int[ls.size()];
                int size = ls.size();
                for (int i = 0; i < size; i++) {
                    ints[i] = ls.get(i);
                }
                int lcm = MathUtil.lcm(ints);
                excelData.setMergeRowCount(lcm);
            }
        }

        return excelData;
    }
}

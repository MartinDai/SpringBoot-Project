package com.doodl6.springboot.common.excel;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class ExcelData {

    /**
     * 数据类 对应一行
     */
    private Map<String, List<String>> dataMap;

    /**
     * 是否合并单元格
     */
    private boolean mergeCells;

    /**
     * 合并行数
     */
    private int mergeRowCount;

    public ExcelData() {
    }

    public ExcelData(int size) {
        this.dataMap = Maps.newHashMapWithExpectedSize(size);
    }

    public Map<String, List<String>> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, List<String>> dataMap) {
        this.dataMap = dataMap;
    }

    public List<String> getValue(String key) {
        return dataMap.get(key);
    }

    public boolean isMergeCells() {
        return mergeCells;
    }

    public void setMergeCells(boolean mergeCells) {
        this.mergeCells = mergeCells;
    }

    public int getMergeRowCount() {
        return mergeRowCount;
    }

    public void setMergeRowCount(int mergeRowCount) {
        this.mergeRowCount = mergeRowCount;
    }
}
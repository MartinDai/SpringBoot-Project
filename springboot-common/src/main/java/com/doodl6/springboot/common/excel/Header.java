package com.doodl6.springboot.common.excel;


/**
 * Excel表格头部类
 */
public class Header {

    protected String field;

    protected String name;

    protected Integer columnIndex = -1;

    public Header() {
    }

    public Header(String field, String name) {
        this.field = field;
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }
}
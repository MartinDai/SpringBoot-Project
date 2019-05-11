package com.doodl6.springboot.common.excel;

import java.util.List;

/**
 * Created by daixiaoming on 2018/9/19.
 */
public class SheetModel {

    private String name;

    private String title;

    private List<Header> headers;

    private List<ExcelData> dataList;

    public SheetModel() {
    }

    public SheetModel(String name, String title, List<Header> headers, List<ExcelData> dataList) {
        this.name = name;
        this.title = title;
        this.headers = headers;
        this.dataList = dataList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<ExcelData> getDataList() {
        return dataList;
    }

    public void setDataList(List<ExcelData> dataList) {
        this.dataList = dataList;
    }
}

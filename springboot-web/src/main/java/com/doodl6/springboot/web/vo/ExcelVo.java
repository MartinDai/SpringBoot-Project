package com.doodl6.springboot.web.vo;

import java.util.List;

/**
 * Created by daixiaoming on 2018-10-31.
 */
public class ExcelVo {

    private List<String> column1;

    private List<String> column2;

    private String mergeColumn;

    public List<String> getColumn1() {
        return column1;
    }

    public void setColumn1(List<String> column1) {
        this.column1 = column1;
    }

    public List<String> getColumn2() {
        return column2;
    }

    public void setColumn2(List<String> column2) {
        this.column2 = column2;
    }

    public String getMergeColumn() {
        return mergeColumn;
    }

    public void setMergeColumn(String mergeColumn) {
        this.mergeColumn = mergeColumn;
    }
}

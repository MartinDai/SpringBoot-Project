package com.doodl6.springboot.web.dto;


import com.alibaba.excel.annotation.ExcelProperty;

public class ExcelData {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

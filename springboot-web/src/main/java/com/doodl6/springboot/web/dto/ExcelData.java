package com.doodl6.springboot.web.dto;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelData {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private String age;

}

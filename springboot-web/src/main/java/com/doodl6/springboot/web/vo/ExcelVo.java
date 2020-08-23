package com.doodl6.springboot.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by daixiaoming on 2018-10-31.
 */
@Getter
@Setter
public class ExcelVo {

    private List<String> column1;

    private List<String> column2;

    private String mergeColumn;

}

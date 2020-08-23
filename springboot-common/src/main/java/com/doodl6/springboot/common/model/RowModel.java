package com.doodl6.springboot.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * excel行模型
 */
@Getter
@Setter
public class RowModel {

    /**
     * 单行
     */
    private List<String> cellList;
    /**
     * 多行
     */
    private List<List<String>> multiCellList;

}

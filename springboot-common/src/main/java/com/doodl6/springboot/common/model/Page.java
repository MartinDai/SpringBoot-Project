package com.doodl6.springboot.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据模型类
 */
@Getter
@Setter
public class Page<T> implements Serializable {

    private int pageNo = 1;

    private int pageSize = 10;

    private int total;

    private int pageCount;

    private List<T> list;

}

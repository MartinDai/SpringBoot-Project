package com.doodl6.springboot.seata.common.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Storage {

    private Integer id;

    private String goodsCode;

    private Integer stockNum;

}
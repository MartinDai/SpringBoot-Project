package com.doodl6.springboot.seata.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Order {

    private Integer id;

    private Integer userId;

    private String goodsCode;

    private Integer stockNum;

    private Integer money;

}
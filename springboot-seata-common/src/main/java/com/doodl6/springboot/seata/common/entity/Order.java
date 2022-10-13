package com.doodl6.springboot.seata.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@TableName("`order`")
public class Order {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String goodsCode;

    private Integer stockNum;

    private Integer money;

}
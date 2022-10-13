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
@TableName("storage")
public class Storage {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String goodsCode;

    private Integer stockNum;

}
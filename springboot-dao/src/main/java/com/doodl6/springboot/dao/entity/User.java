package com.doodl6.springboot.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户实体类
 */
@Getter
@Setter
@Alias("User")
public class User {

    private Long id;

    private String name;

    private Date created;

    private Date modified;

}

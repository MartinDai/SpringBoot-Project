package com.doodl6.springboot.dao.entity;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户实体类
 */
@Alias("User")
public class User {

    private Long id;

    private String name;

    private Date created;

    private Date modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}

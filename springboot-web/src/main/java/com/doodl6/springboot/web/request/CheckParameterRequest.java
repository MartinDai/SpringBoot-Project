package com.doodl6.springboot.web.request;

import com.doodl6.springboot.common.check.annotation.FieldNotEmpty;
import com.doodl6.springboot.common.check.annotation.FieldNotNull;

import java.util.List;

public class CheckParameterRequest {

    @FieldNotEmpty(name = "姓名")
    private String name;

    @FieldNotNull
    private Integer age;

    @FieldNotEmpty(name = "爱好")
    private List<String> favorites;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }
}

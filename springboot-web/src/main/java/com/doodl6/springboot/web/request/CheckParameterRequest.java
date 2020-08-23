package com.doodl6.springboot.web.request;

import com.doodl6.springboot.common.check.annotation.FieldNotEmpty;
import com.doodl6.springboot.common.check.annotation.FieldNotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckParameterRequest {

    @FieldNotEmpty(name = "姓名")
    private String name;

    @FieldNotNull
    private Integer age;

    @FieldNotEmpty(name = "爱好")
    private List<String> favorites;

}

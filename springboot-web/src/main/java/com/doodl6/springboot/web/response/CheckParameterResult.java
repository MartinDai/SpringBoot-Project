package com.doodl6.springboot.web.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckParameterResult {

    private String name;

    private int age;

    private List<String> favorites;

}

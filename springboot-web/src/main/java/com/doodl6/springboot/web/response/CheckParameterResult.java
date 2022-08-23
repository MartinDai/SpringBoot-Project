package com.doodl6.springboot.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("校验参数响应类")
public class CheckParameterResult {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年龄")
    private int age;

    @ApiModelProperty("爱好")
    private List<String> favorites;

}

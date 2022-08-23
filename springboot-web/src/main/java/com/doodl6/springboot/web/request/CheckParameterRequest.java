package com.doodl6.springboot.web.request;

import com.doodl6.springboot.common.check.annotation.FieldNotEmpty;
import com.doodl6.springboot.common.check.annotation.FieldNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("校验参数请求类")
public class CheckParameterRequest {

    @FieldNotEmpty(name = "姓名")
    @ApiModelProperty("姓名")
    private String name;

    @FieldNotNull(name = "年龄")
    @ApiModelProperty("年龄")
    private Integer age;

    @FieldNotEmpty(name = "爱好")
    @ApiModelProperty("爱好")
    private List<String> favorites;

}

package com.doodl6.springboot.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "校验参数响应类")
public class CheckParameterResult {

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "年龄")
    private int age;

    @Schema(description = "爱好")
    private List<String> favorites;

}

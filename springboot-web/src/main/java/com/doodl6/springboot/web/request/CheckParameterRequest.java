package com.doodl6.springboot.web.request;

import com.doodl6.springboot.common.check.annotation.FieldNotEmpty;
import com.doodl6.springboot.common.check.annotation.FieldNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "校验参数请求类")
public class CheckParameterRequest {

    @FieldNotEmpty(name = "姓名")
    @Schema(description = "姓名")
    private String name;

    @FieldNotNull(name = "年龄")
    @Schema(description = "年龄")
    private Integer age;

    @FieldNotEmpty(name = "爱好")
    @Schema(description = "爱好")
    private List<String> favorites;

}

package com.doodl6.springboot.web.controller;

import com.doodl6.springboot.common.check.CheckUtil;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.common.web.response.MapResponse;
import com.doodl6.springboot.web.request.CheckParameterRequest;
import com.doodl6.springboot.web.response.CheckParameterResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 常规控制类
 */
@Tag(name = "常规接口")
@Slf4j
@RestController
public class StandardController {

    /**
     * 普通接口
     */
    @Operation(summary = "普通接口")
    @GetMapping("/hello")
    public MapResponse hello(String name) {
        MapResponse mapResponse = new MapResponse();
        Assert.notNull(name, "name不能为空");

        mapResponse.appendData("name", name);

        return mapResponse;
    }

    /**
     * 参数校验
     */
    @Operation(summary = "参数校验")
    @PostMapping("/parameterCheck")
    public BaseResponse<CheckParameterResult> parameterCheck(@RequestBody CheckParameterRequest request) {

        CheckUtil.check(request);

        CheckParameterResult result = new CheckParameterResult();
        result.setName(request.getName());
        result.setAge(request.getAge());
        result.setFavorites(request.getFavorites());
        BaseResponse<CheckParameterResult> response = new BaseResponse<>();
        response.setData(result);

        return response;
    }

    /**
     * 测试打印日志
     */
    @Operation(summary = "测试打印日志")
    @GetMapping("/testLog")
    public BaseResponse<Void> testLog() {
        BaseResponse<Void> response = new BaseResponse<>();

        log.trace("test trace log");
        log.debug("test debug log");
        log.info("test info log");
        log.warn("test warn log");
        log.error("test error log");

        return response;
    }

}

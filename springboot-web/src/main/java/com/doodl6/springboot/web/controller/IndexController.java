package com.doodl6.springboot.web.controller;

import com.doodl6.springboot.client.domain.DubboDomain;
import com.doodl6.springboot.common.check.CheckUtil;
import com.doodl6.springboot.web.request.CheckParameterRequest;
import com.doodl6.springboot.web.response.CheckParameterResult;
import com.doodl6.springboot.web.response.base.BaseResponse;
import com.doodl6.springboot.web.response.base.MapResponse;
import com.doodl6.springboot.web.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 首页控制类
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Resource
    private IndexService indexService;

    /**
     * 普通接口
     */
    @GetMapping("/hello")
    public MapResponse hello(String name) {
        MapResponse mapResponse = new MapResponse();

        mapResponse.appendData("name", name);

        return mapResponse;
    }

    /**
     * 获取dubbo信息
     */
    @GetMapping("/getDubboInfoWithSentinel")
    public MapResponse getDubboInfoWithSentinel(Long id) {
        Assert.notNull(id, "id不能为空");
        MapResponse mapResponse = new MapResponse();

        DubboDomain dubboDomain = indexService.getDubboInfoWithSentinel(id);
        mapResponse.appendData("dubboInfo", dubboDomain);

        return mapResponse;
    }

    /**
     * 获取dubbo信息
     */
    @GetMapping("/getDubboInfoWithHystrix")
    public MapResponse getDubboInfoWithHystrix(Long id) {
        Assert.notNull(id, "id不能为空");
        MapResponse mapResponse = new MapResponse();

        DubboDomain dubboDomain = indexService.getDubboInfoWithHystrix(id);
        mapResponse.appendData("dubboInfo", dubboDomain);

        return mapResponse;
    }

    /**
     * 参数校验
     */
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

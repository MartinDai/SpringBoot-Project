package com.doodl6.springboot.dubbo.consumer;

import com.doodl6.springboot.common.web.response.MapResponse;
import com.doodl6.springboot.dubbo.api.domain.DubboDomain;
import com.doodl6.springboot.dubbo.consumer.service.DubboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * dubbo控制类
 */
@Slf4j
@RestController
@RequestMapping("/dubbo")
public class DubboController {

    @Resource
    private DubboService dubboService;

    /**
     * 获取dubbo信息
     */
    @GetMapping("/getDubboInfoWithSentinel")
    public MapResponse getDubboInfoWithSentinel(Long id) {
        Assert.notNull(id, "id不能为空");
        MapResponse mapResponse = new MapResponse();

        DubboDomain dubboDomain = dubboService.getDubboInfoWithSentinel(id);
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

        DubboDomain dubboDomain = dubboService.getDubboInfoWithHystrix(id);
        mapResponse.appendData("dubboInfo", dubboDomain);

        return mapResponse;
    }

}

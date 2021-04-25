package com.doodl6.springboot.feign.consumer;

import com.doodl6.springboot.feign.consumer.service.FeignService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Resource
    private FeignService feignService;

    @RequestMapping(value = "/testEcho/{string}", method = RequestMethod.GET)
    public String testEcho(@PathVariable String string) {
        return feignService.echo(string);
    }

}

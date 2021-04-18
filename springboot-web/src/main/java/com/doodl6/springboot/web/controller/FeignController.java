package com.doodl6.springboot.web.controller;

import com.doodl6.springboot.web.service.feign.FeignService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FeignController {

    @Resource
    private FeignService feignService;

    @RequestMapping(value = "/testEcho/{string}", method = RequestMethod.GET)
    public String testEcho(@PathVariable String string) {
        return feignService.echo(string);
    }

}

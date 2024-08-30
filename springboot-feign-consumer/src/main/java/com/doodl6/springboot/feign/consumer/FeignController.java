package com.doodl6.springboot.feign.consumer;

import com.doodl6.springboot.feign.consumer.service.FeignService;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private FeignService feignService;

    @RequestMapping(value = "/testEcho/{string}", method = RequestMethod.GET)
    public String testEcho(@PathVariable String string) {
        return feignService.echo(string);
    }

    /**
     * 获取所有服务提供者节点信息
     */
    @GetMapping(value = "/getServices")
    public Object getServices() {
        return discoveryClient.getInstances("springboot-feign-provider");
    }

    /**
     * 轮训获取服务提供者节点
     */
    @GetMapping(value = "/chooseService")
    public Object chooseService() {
        return loadBalancerClient.choose("springboot-feign-provider").getUri().toString();
    }
}

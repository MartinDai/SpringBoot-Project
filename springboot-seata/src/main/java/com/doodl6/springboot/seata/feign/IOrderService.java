package com.doodl6.springboot.seata.feign;

import com.doodl6.springboot.seata.common.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "order-service")
public interface IOrderService {

    @PostMapping(value = "/order/createOrder")
    String createOrder(@RequestBody Order order);

    @GetMapping(value = "/order/selectOrderByCode")
    List<Order> selectOrderByCode(@RequestParam("goodsCode") String goodsCode);

    @PostMapping(value = "/order/clearOrderByCode")
    String clearOrderByCode(@RequestParam("goodsCode") String goodsCode);

}
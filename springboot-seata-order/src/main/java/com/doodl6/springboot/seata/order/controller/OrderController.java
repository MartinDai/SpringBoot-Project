package com.doodl6.springboot.seata.order.controller;

import com.doodl6.springboot.seata.common.Constants;
import com.doodl6.springboot.seata.common.entity.Order;
import com.doodl6.springboot.seata.order.service.OrderService;
import io.seata.core.context.RootContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping(value = "/createOrder")
    public String createOrder(@RequestBody Order order) {
        System.out.println("order-service执行createOrder 当前正在执行的事务xid:" + RootContext.getXID());
        try {
            orderService.createOrder(order);
        } catch (Exception exx) {
            exx.printStackTrace();
            return Constants.FAIL_RESPONSE;
        }
        return Constants.SUCCESS_RESPONSE;
    }

    @GetMapping(value = "/selectOrderByCode")
    public List<Order> selectOrderByCode(String goodsCode) {
        return orderService.selectOrderByCode(goodsCode);
    }

    @PostMapping(value = "/clearOrderByCode")
    public String clearOrderByCode(String goodsCode) {
        System.out.println("order-service执行clearOrderByCode 当前正在执行的事务xid:" + RootContext.getXID());
        try {
            orderService.clearOrderByCode(goodsCode);
        } catch (Exception exx) {
            exx.printStackTrace();
            return Constants.FAIL_RESPONSE;
        }
        return Constants.SUCCESS_RESPONSE;
    }
}

package com.doodl6.springboot.seata.order.service;

import com.doodl6.springboot.seata.common.entity.Order;
import com.doodl6.springboot.seata.order.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    public void createOrder(Order order) {
        orderMapper.insert(order);
    }

    public List<Order> selectOrderByCode(String goodsCode) {
        return orderMapper.selectOrderByCode(goodsCode);
    }

    public void clearOrderByCode(String goodsCode) {
        orderMapper.clearOrderByCode(goodsCode);
    }
}
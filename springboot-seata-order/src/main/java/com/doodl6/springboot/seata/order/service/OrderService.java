package com.doodl6.springboot.seata.order.service;

import com.doodl6.springboot.seata.common.entity.Order;
import com.doodl6.springboot.seata.order.manager.OrderManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderManager orderManager;

    public void createOrder(Order order) {
        orderManager.save(order);
    }

    public List<Order> selectOrderByCode(String goodsCode) {
        return orderManager.queryByGoodsCode(goodsCode);
    }

    public void clearOrderByCode(String goodsCode) {
        orderManager.deleteByGoodsCode(goodsCode);
    }
}
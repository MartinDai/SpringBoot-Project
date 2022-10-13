package com.doodl6.springboot.seata.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
        QueryWrapper<Order> wrapper = Wrappers.query();
        wrapper.eq("goods_code", goodsCode);
        return orderMapper.selectList(wrapper);
    }

    public void clearOrderByCode(String goodsCode) {
        UpdateWrapper<Order> wrapper = Wrappers.update();
        wrapper.eq("goods_code", goodsCode);
        orderMapper.delete(wrapper);
    }
}
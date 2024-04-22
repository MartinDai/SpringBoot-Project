package com.doodl6.springboot.seata.order.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doodl6.springboot.seata.common.entity.Order;
import com.doodl6.springboot.seata.order.mapper.OrderMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderManager extends ServiceImpl<OrderMapper, Order> {

    public List<Order> queryByGoodsCode(String goodsCode) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getGoodsCode, goodsCode);

        return list(wrapper);
    }

    public void deleteByGoodsCode(String goodsCode) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Order::getGoodsCode, goodsCode);
        getBaseMapper().delete(wrapper);
    }

}

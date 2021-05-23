package com.doodl6.springboot.seata.order.mapper;

import com.doodl6.springboot.seata.common.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    int insert(Order order);

    List<Order> selectOrderByCode(String goodsCode);

    void clearOrderByCode(String goodsCode);

}
package com.doodl6.springboot.seata.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doodl6.springboot.seata.common.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
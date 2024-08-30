package com.doodl6.springboot.seata.service;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.seata.common.entity.Order;
import com.doodl6.springboot.seata.common.entity.Storage;
import com.doodl6.springboot.seata.feign.IOrderService;
import com.doodl6.springboot.seata.feign.IStorageService;
import com.doodl6.springboot.seata.response.StorageWithOrderData;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.doodl6.springboot.seata.common.Constants.SUCCESS_RESPONSE;

@Service
public class TradeService {

    @Resource
    private IStorageService storageService;

    @Resource
    private IOrderService orderService;

    /**
     * 初始化商品数据，清空占用记录
     */
    @GlobalTransactional(timeoutMills = 30000)
    public void initGoodsData(String goodsCode) {
        String xid = RootContext.getXID();
        String result = orderService.clearOrderByCode(goodsCode);
        if (!SUCCESS_RESPONSE.equals(result)) {
            throw new IllegalStateException("初始化商品数据失败，清理订单失败! xid:" + xid);
        }

        boolean success = storageService.initStock(goodsCode, 100);
        Assert.state(success, "初始化商品数据失败，更新库存失败! xid:" + xid);
    }

    /**
     * 创建订单
     */
    @GlobalTransactional(timeoutMills = 30000)
    public void createOrder(String goodsCode, int stockNum, boolean mockException) {
        String xid = RootContext.getXID();
        System.out.println("createOrder 当前正在执行的事务xid:" + xid);
        String result = storageService.reduceStock(goodsCode, stockNum);

        if (!SUCCESS_RESPONSE.equals(result)) {
            throw new IllegalStateException("库存扣减失败! xid:" + xid);
        }

        Order order = new Order().setGoodsCode(goodsCode).setStockNum(stockNum).setMoney(stockNum * 100).setUserId(1);

        result = orderService.createOrder(order);

        if (!SUCCESS_RESPONSE.equals(result)) {
            throw new IllegalStateException("创建订单失败! xid:" + xid);
        }

        if (mockException) {
            throw new IllegalStateException("模拟用户业务异常! xid:" + xid);
        }
    }

    public StorageWithOrderData checkData(String goodsCode) {
        StorageWithOrderData storageWithOrderData = new StorageWithOrderData();
        Storage storage = storageService.selectByCode(goodsCode);
        List<Order> orders = orderService.selectOrderByCode(goodsCode);
        storageWithOrderData.setStorage(storage);
        storageWithOrderData.setOrders(orders);
        return storageWithOrderData;
    }

}

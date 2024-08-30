package com.doodl6.springboot.seata.controller;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.seata.response.StorageWithOrderData;
import com.doodl6.springboot.seata.service.TradeService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Resource
    private TradeService tradeService;

    /**
     * 初始化商品库存和订单数据
     */
    @PostMapping(value = "/initGoodsData")
    public BaseResponse<Void> initGoodsData(String goodsCode) {
        tradeService.initGoodsData(goodsCode);
        return BaseResponse.success();
    }

    /**
     * 创建订单
     */
    @PostMapping(value = "/createOrder")
    public BaseResponse<Void> createOrder(String goodsCode, int stockNum, boolean mockException) {
        Assert.isTrue(StringUtils.isNotBlank(goodsCode), "商品编码不能为空");
        Assert.isTrue(stockNum > 0, "订单库存数必须大于0");
        tradeService.createOrder(goodsCode, stockNum, mockException);
        return BaseResponse.success();
    }

    /**
     * 检查商品库存和订单数据
     */
    @GetMapping(value = "/checkData")
    public BaseResponse<StorageWithOrderData> checkData(String goodsCode) {
        Assert.isTrue(StringUtils.isNotBlank(goodsCode), "商品编码不能为空");
        StorageWithOrderData storageWithOrderData = tradeService.checkData(goodsCode);
        return BaseResponse.success(storageWithOrderData);
    }
}

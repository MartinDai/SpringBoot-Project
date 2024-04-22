package com.doodl6.springboot.seata.storage.service;

import com.doodl6.springboot.seata.common.entity.Storage;
import com.doodl6.springboot.seata.storage.manager.StorageManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class StorageService {

    @Resource
    private StorageManager storageManager;

    public boolean reduceStock(String goodsCode, int stockNum) {
        return storageManager.updateReduceStockNumByGoodsCode(goodsCode, stockNum) == 1;
    }

    @Transactional
    public boolean initStock(String goodsCode, int stockNum) {
        storageManager.deleteByGoodsCode(goodsCode);
        return storageManager.save(new Storage().setGoodsCode(goodsCode).setStockNum(stockNum));
    }

    public Storage selectByCode(String goodsCode) {
        return storageManager.queryByGoodsCode(goodsCode);
    }
}
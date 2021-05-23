package com.doodl6.springboot.seata.storage.service;

import com.doodl6.springboot.seata.common.entity.Storage;
import com.doodl6.springboot.seata.storage.mapper.StorageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StorageService {

    @Resource
    private StorageMapper storageMapper;

    public boolean reduceStock(String goodsCode, int stockNum) {
        return storageMapper.updateStockByCode(goodsCode, stockNum) == 1;
    }

    public int initStock(String goodsCode, int stockNum) {
        return storageMapper.insert(new Storage().setGoodsCode(goodsCode).setStockNum(stockNum));
    }

    public Storage selectByCode(String goodsCode) {
        return storageMapper.selectByCode(goodsCode);
    }
}
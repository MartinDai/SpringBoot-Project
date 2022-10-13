package com.doodl6.springboot.seata.storage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.doodl6.springboot.seata.common.entity.Storage;
import com.doodl6.springboot.seata.storage.mapper.StorageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class StorageService {

    @Resource
    private StorageMapper storageMapper;

    public boolean reduceStock(String goodsCode, int stockNum) {
        Storage storage = new Storage();
        storage.setGoodsCode(goodsCode);
        storage.setStockNum(stockNum);
        return storageMapper.updateStockByCode(storage) == 1;
    }

    @Transactional
    public int initStock(String goodsCode, int stockNum) {
        UpdateWrapper<Storage> wrapper = Wrappers.update();
        wrapper.eq("goods_code", goodsCode);
        storageMapper.delete(wrapper);
        return storageMapper.insert(new Storage().setGoodsCode(goodsCode).setStockNum(stockNum));
    }

    public Storage selectByCode(String goodsCode) {
        QueryWrapper<Storage> wrapper = Wrappers.query();
        wrapper.eq("goods_code", goodsCode);
        return storageMapper.selectOne(wrapper);
    }
}
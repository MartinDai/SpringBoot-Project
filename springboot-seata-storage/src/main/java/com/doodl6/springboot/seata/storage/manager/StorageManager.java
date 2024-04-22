package com.doodl6.springboot.seata.storage.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doodl6.springboot.seata.common.entity.Storage;
import com.doodl6.springboot.seata.storage.mapper.StorageMapper;
import org.springframework.stereotype.Component;

@Component
public class StorageManager extends ServiceImpl<StorageMapper, Storage> {

    public Storage queryByGoodsCode(String goodsCode) {
        LambdaQueryWrapper<Storage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Storage::getGoodsCode, goodsCode);
        return getOne(wrapper);
    }

    public int updateReduceStockNumByGoodsCode(String goodsCode, int reduceStockNum) {
        LambdaUpdateWrapper<Storage> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Storage::getGoodsCode, goodsCode)
                .ge(Storage::getStockNum, reduceStockNum)
                .setSql("stock_num = stock_num - " + reduceStockNum);
        return getBaseMapper().update(null, wrapper);
    }

    public void deleteByGoodsCode(String goodsCode) {
        LambdaUpdateWrapper<Storage> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Storage::getGoodsCode, goodsCode);
        getBaseMapper().delete(wrapper);
    }

}

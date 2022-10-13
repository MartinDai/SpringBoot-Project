package com.doodl6.springboot.seata.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.doodl6.springboot.seata.common.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {

    @Update("UPDATE storage SET stock_num = stock_num - #{et.stockNum} WHERE goods_code = #{et.goodsCode} AND stock_num >= #{et.stockNum}")
    int updateStockByCode(@Param(Constants.ENTITY) Storage storage);
}
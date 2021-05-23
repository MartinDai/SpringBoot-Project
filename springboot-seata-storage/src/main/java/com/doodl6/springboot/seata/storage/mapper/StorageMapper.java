package com.doodl6.springboot.seata.storage.mapper;

import com.doodl6.springboot.seata.common.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageMapper {

    Storage selectByCode(@Param("goodsCode") String goodsCode);

    int updateStockByCode(@Param("goodsCode") String goodsCode, @Param("stockNum") int stockNum);

    int insert(Storage storage);
}
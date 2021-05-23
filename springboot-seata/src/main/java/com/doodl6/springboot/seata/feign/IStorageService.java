package com.doodl6.springboot.seata.feign;

import com.doodl6.springboot.seata.common.entity.Storage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "storage-service")
public interface IStorageService {

    @PostMapping("/storage/reduceStock")
    String reduceStock(@RequestParam(name = "goodsCode") String goodsCode,
                       @RequestParam(name = "stockNum") Integer stockNum);

    @PostMapping("/storage/initStock")
    int initStock(@RequestParam(name = "goodsCode") String goodsCode,
                  @RequestParam(name = "stockNum") Integer stockNum);

    @GetMapping(value = "/storage/selectByCode")
    Storage selectByCode(@RequestParam(name = "goodsCode") String goodsCode);
}
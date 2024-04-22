package com.doodl6.springboot.seata.storage.controller;

import com.doodl6.springboot.seata.common.Constants;
import com.doodl6.springboot.seata.common.entity.Storage;
import com.doodl6.springboot.seata.storage.service.StorageService;
import io.seata.core.context.RootContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Resource
    private StorageService storageService;

    @PostMapping("/reduceStock")
    public String reduceStock(@RequestParam(name = "goodsCode") String goodsCode,
                              @RequestParam(name = "stockNum") Integer stockNum) {
        System.out.println("storage-service执行reduceStock 当前事务xid:" + RootContext.getXID());
        return storageService.reduceStock(goodsCode, stockNum) ? Constants.SUCCESS_RESPONSE : Constants.FAIL_RESPONSE;
    }

    @PostMapping("/initStock")
    public boolean initStock(@RequestParam(name = "goodsCode") String goodsCode,
                         @RequestParam(name = "stockNum") Integer stockNum) {
        return storageService.initStock(goodsCode, stockNum);
    }

    @GetMapping("/selectByCode")
    public Storage selectByCode(@RequestParam(name = "goodsCode") String goodsCode) {
        return storageService.selectByCode(goodsCode);
    }
}
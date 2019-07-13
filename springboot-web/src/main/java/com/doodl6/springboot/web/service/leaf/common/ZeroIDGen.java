package com.doodl6.springboot.web.service.leaf.common;


import com.doodl6.springboot.web.service.leaf.IDGen;

public class ZeroIDGen implements IDGen {

    @Override
    public Result get(String key) {
        return new Result(0, Status.SUCCESS);
    }

    @Override
    public boolean init() {
        return true;
    }
}
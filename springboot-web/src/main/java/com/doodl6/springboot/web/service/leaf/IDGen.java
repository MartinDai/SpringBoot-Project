package com.doodl6.springboot.web.service.leaf;


import com.doodl6.springboot.web.service.leaf.common.Result;

public interface IDGen {

    Result get(String key);

    boolean init();
}
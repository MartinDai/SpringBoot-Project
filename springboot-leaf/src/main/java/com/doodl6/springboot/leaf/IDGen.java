package com.doodl6.springboot.leaf;


import com.doodl6.springboot.leaf.common.Result;

public interface IDGen {

    Result get(String key);

    boolean init();
}
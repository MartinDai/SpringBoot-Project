package com.doodl6.springboot.web.service.leaf.segment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeafAlloc {

    private String key;

    private long maxId;

    private int step;

    private String updateTime;

}
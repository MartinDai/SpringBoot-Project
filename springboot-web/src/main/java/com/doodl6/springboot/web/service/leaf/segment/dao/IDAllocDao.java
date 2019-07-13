package com.doodl6.springboot.web.service.leaf.segment.dao;

import com.doodl6.springboot.web.service.leaf.segment.model.LeafAlloc;

import java.util.List;

public interface IDAllocDao {

    List<LeafAlloc> getAllLeafAllocs();

    LeafAlloc updateMaxIdAndGetLeafAlloc(String tag);

    LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc);

    List<String> getAllTags();
}
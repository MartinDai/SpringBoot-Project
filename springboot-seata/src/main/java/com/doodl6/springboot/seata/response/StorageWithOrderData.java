package com.doodl6.springboot.seata.response;

import com.doodl6.springboot.seata.common.entity.Order;
import com.doodl6.springboot.seata.common.entity.Storage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StorageWithOrderData {

    private Storage storage;

    private List<Order> orders;
}

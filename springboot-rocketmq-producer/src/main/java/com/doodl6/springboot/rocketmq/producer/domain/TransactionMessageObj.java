package com.doodl6.springboot.rocketmq.producer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionMessageObj {

    private String transactionType;

    private Object arg;

}

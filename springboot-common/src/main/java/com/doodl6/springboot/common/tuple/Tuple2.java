package com.doodl6.springboot.common.tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 元组
 */
@Getter
@Setter
@AllArgsConstructor
public class Tuple2<P1, P2> {

    private P1 p1;

    private P2 p2;

}

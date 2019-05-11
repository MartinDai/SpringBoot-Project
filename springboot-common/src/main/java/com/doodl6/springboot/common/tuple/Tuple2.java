package com.doodl6.springboot.common.tuple;

/**
 * 元组
 */
public class Tuple2<P1, P2> {

    private P1 p1;

    private P2 p2;

    public Tuple2(P1 p1, P2 p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public P1 getP1() {
        return p1;
    }

    public P2 getP2() {
        return p2;
    }
}

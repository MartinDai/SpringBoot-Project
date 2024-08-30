package com.doodl6.springboot.leaf.segment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Segment {

    private AtomicLong value = new AtomicLong(0);

    private volatile long max;

    private volatile int step;

    private final SegmentBuffer buffer;

    public Segment(SegmentBuffer buffer) {
        this.buffer = buffer;
    }

    public long getIdle() {
        return this.getMax() - getValue().get();
    }

    @Override
    public String toString() {
        return "Segment(" + "value:" +
                value +
                ",max:" +
                max +
                ",step:" +
                step +
                ")";
    }
}
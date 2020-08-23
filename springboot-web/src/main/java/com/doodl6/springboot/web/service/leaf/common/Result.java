package com.doodl6.springboot.web.service.leaf.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private long id;

    private Status status;

    @Override
    public String toString() {
        return "Result{" + "id=" + id +
                ", status=" + status +
                '}';
    }
}
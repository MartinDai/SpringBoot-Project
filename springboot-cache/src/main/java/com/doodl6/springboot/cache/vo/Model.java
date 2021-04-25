package com.doodl6.springboot.cache.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by daixiaoming on 2018/5/12.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Model implements Serializable {

    private String key;

    private String value;

}

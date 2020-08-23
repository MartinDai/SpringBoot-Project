package com.doodl6.springboot.client.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseResponse implements Serializable {

    private boolean success;

    private String errorMsg;

}

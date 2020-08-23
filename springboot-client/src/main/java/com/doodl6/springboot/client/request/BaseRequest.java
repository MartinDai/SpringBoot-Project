package com.doodl6.springboot.client.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseRequest implements Serializable {

    /**
     * 追踪ID
     */
    private String traceId;

}

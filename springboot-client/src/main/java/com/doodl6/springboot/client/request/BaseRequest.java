package com.doodl6.springboot.client.request;

import java.io.Serializable;

public class BaseRequest implements Serializable {

    /**
     * 追踪ID
     */
    private String traceId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

}

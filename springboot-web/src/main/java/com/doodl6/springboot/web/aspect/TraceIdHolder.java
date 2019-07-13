package com.doodl6.springboot.web.aspect;

public class TraceIdHolder {

    private static ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static String getTraceId() {
        return TRACE_ID.get();
    }

    public static void removeTraceId() {
        TRACE_ID.remove();
    }
}

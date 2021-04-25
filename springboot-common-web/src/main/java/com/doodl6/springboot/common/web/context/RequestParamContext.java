package com.doodl6.springboot.common.web.context;

public class RequestParamContext {

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    public static void set(String content) {
        HOLDER.set(content);
    }

    public static String get() {
        return HOLDER.get();
    }

    public static void remove() {
        HOLDER.remove();
    }
}

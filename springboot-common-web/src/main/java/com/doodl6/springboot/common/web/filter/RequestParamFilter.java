package com.doodl6.springboot.common.web.filter;

import com.alibaba.fastjson2.JSON;
import com.doodl6.springboot.common.web.context.RequestParamContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Order(1)
@WebFilter(asyncSupported = true)
public class RequestParamFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String contentType = request.getContentType();
        String params;
        ServletRequest servletRequest;
        if ("POST".equals(method) && contentType != null && contentType.startsWith("application/json")) {
            RequestBodyWrapper wrapper = new RequestBodyWrapper(request);
            byte[] body = wrapper.getBody();
            String encoding = getRequestEncoding(request);
            params = new String(body, encoding);
            if (JSON.isValid(params)) {
                params = JSON.parseObject(params).toJSONString();
            }
            servletRequest = wrapper;
        } else {
            params = getParams(request);
            servletRequest = request;
        }
        RequestParamContext.set(params);
        filterChain.doFilter(servletRequest, response);
        RequestParamContext.remove();
    }

    public static String getParams(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        if (map != null && !map.isEmpty()) {
            return JSON.toJSONString(map);
        } else {
            return StringUtils.EMPTY;
        }
    }

    private static String getRequestEncoding(HttpServletRequest request) {
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }

        return encoding;
    }

}
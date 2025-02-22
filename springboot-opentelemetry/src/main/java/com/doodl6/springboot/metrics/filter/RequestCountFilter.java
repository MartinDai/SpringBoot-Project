package com.doodl6.springboot.metrics.filter;

import com.doodl6.springboot.metrics.Counter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Order(10)
@WebFilter(asyncSupported = true)
public class RequestCountFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            Counter.getRequestCounter().add(1);
        } catch (Exception e) {
            log.error("RequestCountFilter exception", e);
        }
    }

}
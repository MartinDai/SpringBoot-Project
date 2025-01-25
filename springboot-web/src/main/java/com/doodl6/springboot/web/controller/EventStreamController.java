package com.doodl6.springboot.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@Slf4j
@RestController
public class EventStreamController {

    @GetMapping(value = "/eventStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> eventStream() {
        // 创建一个 Flux，用于每秒推送一条消息
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "服务器时间 " + LocalTime.now())
                .take(10)// 限制发送 10 条消息
                .concatWith(Flux.just("END")) // 添加一个结束信号
                .doOnCancel(() -> System.out.println("客户端连接已断开")); // 客户端断开时触发
    }
}
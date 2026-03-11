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
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> {
                    // 构造 OpenAI 风格的 JSON 数据
                    String jsonData = String.format(
                            "{\"id\": \"msg-%d\", \"object\": \"text_completion\", \"created\": %d, \"choices\": [{\"text\": \"服务器时间 %s\", \"index\": 0}]}",
                            sequence,
                            System.currentTimeMillis() / 1000,
                            LocalTime.now()
                    );
                    return "data: " + jsonData + "\n\n";
                })
                .take(10) // 限制发送 10 条消息
                .concatWith(Flux.just("data: {\"object\": \"stream.end\", \"message\": \"END\"}\n\n")) // OpenAI 风格的结束信号
                .doOnCancel(() -> System.out.println("客户端连接已断开")); // 客户端断开时触发
    }
}
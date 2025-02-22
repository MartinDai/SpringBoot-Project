package com.doodl6.springboot.metrics;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Counter {

    private static LongCounter REQUEST_COUNTER;

    public static LongCounter getRequestCounter() {
        if (REQUEST_COUNTER == null) {
            Meter meter = GlobalOpenTelemetry.getMeter("springboot-metrics");
            REQUEST_COUNTER = meter.counterBuilder("http_request_count").build();
        }
        return REQUEST_COUNTER;
    }
}

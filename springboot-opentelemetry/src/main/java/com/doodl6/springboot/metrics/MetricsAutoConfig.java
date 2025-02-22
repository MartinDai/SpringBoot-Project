package com.doodl6.springboot.metrics;

import cn.idev.excel.util.StringUtils;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.exporter.otlp.http.metrics.OtlpHttpMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.semconv.ServiceAttributes;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@ServletComponentScan(basePackages = "com.doodl6.springboot.metrics.filter")
public class MetricsAutoConfig {

    private static final String METRICS_PATH = "/opentelemetry/v1/metrics";

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${victoriaMetrics.url}")
    private String victoriaMetricsUrl;

    @Value("${victoriaMetrics.interval:10}")
    private int interval;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(victoriaMetricsUrl)) {
            throw new IllegalArgumentException("victoriaMetricsUrl is required");
        }

        GlobalOpenTelemetry.set(createOpenTelemetry());
    }

    public OpenTelemetry createOpenTelemetry() {
        Resource resource = Resource.builder()
                .put(ServiceAttributes.SERVICE_NAME, applicationName)
                .build();
        MetricExporter metricExporter = OtlpHttpMetricExporter.builder().setEndpoint(victoriaMetricsUrl + METRICS_PATH).build();
        PeriodicMetricReader metricReader = PeriodicMetricReader.builder(metricExporter).setInterval(interval, TimeUnit.SECONDS).build();
        SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
                .addResource(resource)
                .registerMetricReader(metricReader)
                .build();
        return OpenTelemetrySdk.builder()
                .setMeterProvider(sdkMeterProvider)
                .build();
    }
}

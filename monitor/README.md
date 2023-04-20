# 本目录用于存放项目启动植入监控需要的相关配置

## 基于JMX+prometheus收集监控信息

在启动的时候以下参数

```
-javaagent:<your-project-path>/monitor/jmx_prometheus_javaagent-0.16.1.jar=8088:<your-project-path>/monitor/prometheus-jmx-config.yml
```

需要替换`<your-project-path>`为本地的项目目录，`8088`也可以修改为自己想要的端口，启动以后，访问 `http://localhost:8088/metrics` 即可查看当前JVM各维度统计信息（prometheus格式）


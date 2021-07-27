# 本目录用于存放项目启动植入监控需要的相关配置

## 基于JMX+prometheus收集监控信息

在启动的时候添加参数，需要替换`/Users/martin/develop/projects/SpringBoot-Project`为实际的文件目录，
`8088`也可以修改为自己想要的端口，启动以后，访问 `http://localhost:8088/metrics` 即可查看当前JVM各维度统计信息（prometheus格式）

```
-javaagent:/Users/martin/develop/projects/SpringBoot-Project/monitor/jmx_prometheus_javaagent-0.16.1.jar=8088:/Users/martin/develop/projects/SpringBoot-Project/monitor/prometheus-jmx-config.yml
```
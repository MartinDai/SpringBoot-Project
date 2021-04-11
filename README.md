# SpringBoot-Project

一个整合了一些常用功能的SpringBoot项目。

## 涉及框架

- SpringBoot
- MyBatis
- Logback(日志框架)
- Guava
- Fastjson
- Dubbo(RPC微服务)
- Sharding-Sphere(分库分表)
- RocketMQ(消息队列)
- Lombok

## 支持的功能

- 文件上传下载
- Excel导入导出
- Memcached服务(Memcached-Java-Client和xmemcached两种client实现)
- Redis服务（Redisson客户端，含常规操作、分布式锁和布隆过滤器整合）
- 在线聊天室DEMO（包括基于Netty、WebSocket和长轮训三种实现方案）
- ElasticSearch调用实现
- AOP实现记录API耗时日志
- 基于Zookeeper实现的分布式锁
- 分布式ID（基于Leaf的Segment模式）
- 分布式事务（使用RocketMQ实现最终一致性）
- 基于Nacos的动态配置
- 支持服务熔断、降级和限流配置（包含基于Hystrix和sentinel）
- 获取内存监控数据
- 通过Dubbo Filter记录RPC请求耗时日志

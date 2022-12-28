# SpringBoot-Project

一个整合了一些常用功能开箱即用的SpringBoot项目，模块划分清晰易懂，可按需自由组合各个模块运行。

注意：本项目使用了lombok来简化Getter/Setter，所以需要安装lombok插件才能保证编译不报错

本项目整合了Swagger，启动web模块后可以通过[http://127.0.0.1:2019/doc.html](http://127.0.0.1:2019/doc.html)查看

# 涉及的重要框架和组件清单

- [SpringBoot](https://github.com/spring-projects/spring-boot)
- [Redis](https://github.com/redis/redis)
- [Redisson](https://github.com/redisson/redisson)
- [Memcached](https://github.com/memcached/memcached)
- [MySQL](https://github.com/mysql/mysql-server)
- [Leaf](https://github.com/Meituan-Dianping/Leaf)
- [Druid](https://github.com/alibaba/druid)
- [MyBatis](https://github.com/mybatis/mybatis-3)
- [MyBatis-Plus](https://github.com/baomidou/mybatis-plus)
- [Sharding-Jdbc](https://github.com/apache/shardingsphere)
- [Seata](https://github.com/seata/seata)
- [RocketMQ](https://github.com/apache/rocketmq)
- [RocketMQ-Spring](https://github.com/apache/rocketmq-spring)
- [Dubbo](https://github.com/apache/dubbo)
- [Hystrix](https://github.com/Netflix/Hystrix)
- [Sentinel](https://github.com/alibaba/Sentinel)
- [Feign](https://github.com/OpenFeign/feign)
- [Netty](https://github.com/netty/netty)
- [ZooKeeper](https://github.com/apache/zookeeper)
- [ElasticSearch](https://github.com/elastic/elasticsearch)
- [Consul](https://github.com/hashicorp/consul)
- [Prometheus](https://github.com/prometheus/prometheus)
- [Grafana](https://github.com/grafana/grafana)
- [Swagger-ui](https://github.com/swagger-api/swagger-ui)
- [Springdoc-openapi](https://github.com/springdoc/springdoc-openapi)
- [Knife4j](https://github.com/xiaoymin/swagger-bootstrap-ui)
- [Guava](https://github.com/google/guava)
- [Hutool](https://github.com/dromara/hutool)
- [EasyExcel](https://github.com/alibaba/easyexcel)

# 打包编译

```shell
./mvnw clean install
```

# 模块介绍

## docker
docker目录里面包含了本项目部分模块需要依赖的的组件环境，可通过脚本一键启动，README.md有详细使用介绍

## springboot-cache
包含缓存相关的操作案例

- Memcached，整合Memcached-Java-Client和xmemcached两种客户端实现基本操作
- Redis，使用Redisson客户端，包含常规操作、分布式锁和布隆过滤器使用示例

## springboot-common
包含一些通用的工具类等

## springboot-common-web
通用web模块，封装服务于HTTP接口通用的一些类，所有需要对外提供HTTP服务的模块都会依赖此模块

## springboot-dao
数据访问模块，基于MyBatis封装了包含User和UserLogin两个表的基本操作

SQL部分是基于sharding-jdbc分库分表写的，数据库表初始化文件为spring_project_1.sql和spring_project_2.sql

部分需要操作数据库的模块会依赖此模块

## springboot-db-controller
依赖于springboot-dao，对外提供访问数据库相关操作的接口，主要是为了方便把dao做成可选的灵活组合，一般整合在web模块的

部分需要操作数据库的模块会依赖此模块

## springboot-dubbo-api
dubbo服务的api模块，dubbo-provider和dubbo-consumer会依赖此模块

## springboot-dubbo-consumer
dubbo服务消费者，内容包含

- 简单的dubbo服务调用示例
- 整合Hystrix熔断
- 整合Sentinel限流
- 自定义Filter统计服务调用耗时日志

## springboot-dubbo-provider
dubbo服务提供者，包含基于dubbo-api模块实现的dubbo服务，提供了两种协议（dubbo和rest）

可单独启动，入口为DubboApplication

rest协议调用
```shell
curl --location --request POST 'http://127.0.0.1:8080/dubbo-rest/getDubboInfo' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1
}'
```

## springboot-elasticsearch
包含elasticsearch服务基本crud操作

## springboot-feign-consumer
feign服务消费者，包含简单的feign服务调用示例，使用consul做服务发现

## springboot-feign-provider
feign服务提供者，包含简单的基于feign实现的服务，使用consul做服务注册，可单独启动，入口为FeignApplication

## springboot-leaf
整合美团开源的分布式ID生成服务leaf，包含记录HTTP接口耗时日志切面，数据库表初始化文件为leaf.sql

## springboot-netty
包含基于netty+websocket实现的简易聊天室功能，入口页面为chat-netty.html

## springboot-rocketmq-consumer
包含RocketMQ的消费者使用示例，消息来自于rocketmq-producer模块

- 普通消息并发消费示例
- 事务消息使用示例（可实现最终一致性分布式事务）
- 顺序消息消费示例

## springboot-rocketmq-producer
RocketMQ的消息生产者模块，内容包含

- 使用tomcat提供的ServerEndpoint注解基于websocket实现的简单聊天室功能，聊天消息会发送RocketMQ普通消息，入口页面为chat-websocket.html
- 提供了简单的用户操作HTTP接口，其中删除用户会发送RocketMQ事务消息

## springboot-seata
seata示例的TM服务，可单独启动，docker目录下有seata-server的容器配置

## springboot-seata-common
seata功能的通用模块

## springboot-seata-order
seata功能的订单RM服务，可单独启动

## springboot-seata-storage
seata功能的商品库存RM服务，可单独启动

## springboot-web
Web模块，默认整合依赖了其他所有不支持单独启动的功能模块，可根据实际需要进行增删调整，启动入口为WebApplication，里面包含了所有其他模块需要的注解配置

本模块内容包括

- 基于长轮训实现的简单聊天室功能，入口页面为chat-long-polling.html
- 基于xhttp实现页面动态内容回传
- 简单的Excel上传下载功能
- 堆和直接内存使用情况监控接口

## springboot-zookeeper
包含基于zookeeper实现的分布式锁示例

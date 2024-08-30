## Docker环境部署使用说明

### _进入当前目录，根据实际情况选择需要的容器组合命令执行_

### Memcached和Redis服务

```bash
docker-compose -f docker-compose-cache.yml up -d
```

### consul服务

```bash
docker-compose -f docker-compose-consul.yml up -d
```
- 后台管理页面：http://localhost:8500

### Elasticsearch主从集群+Kibana服务+Elasticvue服务

```bash
docker-compose -f docker-compose-elasticsearch.yml up -d
```
- 注意需要修改当前目录下的`elasticsearch/master/config/elasticsearch.yml`和`elasticsearch/master/config/elasticsearch.yml`两个文件中的ip为本机内网IP
- 这个组合比较消耗内存，至少保证docker有3.5G的空闲可分配内存再启动
- kibana管理页面：http://localhost:5601
- Elasticvue管理页面：http://localhost:18080

### Prometheus+Grafana服务

```bash
docker-compose -f docker-compose-monitor.yml up -d
```
- prometheus管理页面：http://localhost:9090/graph
- grafana管理页面：http://localhost:3000，默认用户名/密码都为：admin  
- 如果要结合JMX配置监控项目，请参考`monitor/README.md`中的说明启动项目，然后需要修改当前目录下的`prometheus/config/prometheus.yml`最后一行配置中的ip为本地内网IP
- 启动以后在grafana添加prometheus数据源，然后可以通过链接`https://grafana.com/grafana/dashboards/8563` 导入dashboard，该dashboard包含了常用的JVM监控维度信息

### MySQL服务

```bash
docker-compose -f docker-compose-mysql.yml up -d
```

### RocketMQ服务+Dashboard服务

```bash
docker-compose -f docker-compose-rocketmq.yml up -d
```
- 注意需要修改基于当前目录下的rocketmq/broker/conf/broker.conf文件内的brokerIP1属性为本机内网IP
- rocketmq-dashboard页面：http://localhost:28080

### Zookeeper集群+zoonavigator服务

```bash
docker-compose -f docker-compose-zookeeper.yml up -d
```
- zoonavigator管理页面：http://localhost:9000

### Seata-Server服务

```bash
docker-compose -f docker-compose-seata.yml up -d
```
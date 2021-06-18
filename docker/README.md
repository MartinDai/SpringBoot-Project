## Docker环境部署使用说明

### _进入当前目录，根据实际情况选择需要的容器组合命令执行_

### memcached和redis服务

```
docker-compose -f docker-compose-cache.yml up -d
```

### elasticsearch主从和kibana服务

```
docker-compose -f docker-compose-es.yml up -d
```
- 注意需要修改当前目录下的`elasticsearch/master/config/elasticsearch.yml`和`elasticsearch/master/config/elasticsearch.yml`两个文件中的ip为本机内网IP
- 这个组合比较消耗内存，至少保证docker有3.5G的空闲可分配内存再启动
- kibana管理页面：http://localhost:5601
  
### mysql服务

```
docker-compose -f docker-compose-mysql.yml up -d
```

### nacos服务

```
docker-compose -f docker-compose-nacos.yml up -d
```
- 后台管理页面：http://localhost:8848，账号/密码：nacos

### rocketmq的namesrv和broker服务

```
docker-compose -f docker-compose-rocketmq.yml up -d
```
- 注意需要修改基于当前目录下的rocketmq/broker/conf/broker.conf文件内的brokerIP1属性为本机内网IP
- rocketmq-console页面：http://localhost:8081

### zk集群

```
docker-compose -f docker-compose-zk.yml up -d
```

### seata-server服务

```
docker-compose -f docker-compose-seata.yml up -d
```
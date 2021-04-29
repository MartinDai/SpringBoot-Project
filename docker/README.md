# Docker环境部署使用说明

- 进入当前目录，根据实际情况选择需要启动的容器组合
- docker-compose-cache.yml包含memcached和redis容器组合，后台运行命令`docker-compose -f docker-compose-cache.yml up -d`
- docker-compose-es.yml包含elasticsearch主从和kibana容器组合，后台运行命令`docker-compose -f docker-compose-es.yml up -d`，这个组合比较消耗内存，至少保证docker有3.5G的空闲可分配内存再启动，kibana管理页面：http://localhost:5601
- docker-compose-mysql.yml包含mysql服务容器，后台运行命令`docker-compose -f docker-compose-mysql.yml up -d`
- docker-compose-nacos.yml包含nacos服务容器，后台运行命令`docker-compose -f docker-compose-nacos.yml up -d`，后台管理页面：http://localhost:8848，账号/密码：nacos
- docker-compose-rocketmq.yml包含rocketmq的namesrv和broker服务容器组合，后台运行命令`docker-compose -f docker-compose-rocketmq.yml up -d`，注意需要修改基于当前目录下的rocketmq/broker/conf/broker.conf文件内的brokerIP1属性为宿主机内网IP，rocketmq-console页面：http://localhost:8081
- docker-compose-zk.yml包含两个节点的zk集群容器组合，后台运行命令`docker-compose -f docker-compose-zk.yml up -d`
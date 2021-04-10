# Docker环境部署使用说明

- 进入当前目录，根据实际情况选择需要启动的容器组合
- docker-compose-cache.yml包含memcached和redis容器组合，后台运行命令`docker-compose -f docker-compose-cache.yml up -d`
- docker-compose-es.yml包含elasticsearch主从和kibana容器组合，后台运行命令`docker-compose -f docker-compose-es.yml up -d`
- docker-compose-mysql.yml包含mysql服务容器，后台运行命令`docker-compose -f docker-compose-mysql.yml up -d`
- docker-compose-nacos.yml包含nacos+grafana+prometheus的容器组合，后台运行命令`docker-compose -f docker-compose-nacos.yml up -d`
- docker-compose-rocketmq.yml包含rocketmq的namesrv和broker服务容器组合，后台运行命令`docker-compose -f docker-compose-rocketmq.yml up -d`
- docker-compose-zk.yml包含两个节点的zk集群容器组合，后台运行命令`docker-compose -f docker-compose-zk.yml up -d`
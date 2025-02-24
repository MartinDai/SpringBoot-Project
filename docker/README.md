## Docker环境部署使用说明

### _进入当前目录，根据实际情况选择需要的容器组合命令执行_

### MySQL服务

```bash
docker-compose -f docker-compose-mysql.yml up -d
```

### Seata-Server服务

```bash
docker-compose -f docker-compose-seata.yml up -d
```
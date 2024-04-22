CREATE DATABASE seata_storage;
USE seata_storage;
CREATE TABLE `storage`
(
    `id`         bigint(20)  NOT NULL AUTO_INCREMENT,
    `goods_code` varchar(32) NOT NULL,
    `stock_num`  int(11)     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_GOODS_CODE` (`goods_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';
-- ----------------------------
--  create and use database `spring_project_2`
-- ----------------------------
create database spring_project_2;
use spring_project_2;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(48) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user_login_log_0`
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log_0`;
CREATE TABLE `user_login_log_0` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `login_time` datetime NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USER_ID_LOGIN_TIME` (`user_id`,`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user_login_log_1`
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log_1`;
CREATE TABLE `user_login_log_1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `login_time` datetime NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USER_ID_LOGIN_TIME` (`user_id`,`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
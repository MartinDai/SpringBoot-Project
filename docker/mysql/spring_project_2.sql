-- ----------------------------
--  create and use database `spring_project_2`
-- ----------------------------
create database spring_project_2;
use spring_project_2;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
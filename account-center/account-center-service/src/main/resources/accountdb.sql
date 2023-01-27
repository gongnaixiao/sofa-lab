DROP DATABASE IF EXISTS `account_db`;

CREATE DATABASE  `account_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE account_db;

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account_no` varchar(8) NOT NULL COMMENT '账户号,唯一标示,前两位为uid',
  `balance` decimal(10,0) DEFAULT '0' COMMENT '账户余额',
  `freeze_amount` decimal(10,0) DEFAULT '0' COMMENT '冻结资金',
  `unreach_amount` decimal(10,0) DEFAULT '0' COMMENT '未达资金',
  PRIMARY KEY (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账户表';

DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
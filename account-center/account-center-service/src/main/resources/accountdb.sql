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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户表';

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 记录每一笔分布式事务操作的账号、操作金额，TCC模式下才会使用到此表，sharding_key为拆分健，取前两位
DROP TABLE IF EXISTS `account_transaction`;
CREATE TABLE IF NOT EXISTS account_transaction(
  `tx_id` varchar(128) not null COMMENT '事务id+actionid',
  `account_no` varchar(8) not null COMMENT '账号',
  `amount` decimal(10, 0) not null COMMENT '金额',
  `operation` varchar(20) not null COMMENT '操作：入还是出',
  `status` varchar(10) not null COMMENT '状态',
  `sharding_key` varchar(8) not null COMMENT '分库分表字段',
  primary key (`tx_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '事务业务关联表';
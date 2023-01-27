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
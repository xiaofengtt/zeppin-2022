/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : aaaa

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2020-04-28 09:36:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `username` varchar(50) NOT NULL,
  `realname` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `role` (`role`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1d0db2b5-2a10-482c-a6c2-57adabdccbf6', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'admin', '管理员', '928bfd2577490322a6e19b793691467e', 'normal', '2020-03-24 16:53:55');

-- ----------------------------
-- Table structure for `bank`
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `short_name` varchar(200) NOT NULL,
  `logo` varchar(36) NOT NULL,
  `sort` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank
-- ----------------------------
INSERT INTO `bank` VALUES ('15e852e7-e077-43b4-a9c1-a91d3834ebd7', '南京银行', '南京银行', 'imgbase/bank/njbc.png', '18', 'normal');
INSERT INTO `bank` VALUES ('1fddfa88-8ec0-465b-b7bf-c3516018fdb1', '华夏银行', '华夏银行', 'imgbase/bank/hxbank.png', '12', 'normal');
INSERT INTO `bank` VALUES ('2903ea7a-0af4-4e80-93dc-0fae87648258', '浙商银行', '浙商银行', 'imgbase/bank/czbank.png', '19', 'normal');
INSERT INTO `bank` VALUES ('2e263348-0b7e-476d-8bf1-a5f8df52ce64', '光大银行', '光大银行', 'imgbase/bank/ceb.png', '7', 'normal');
INSERT INTO `bank` VALUES ('378e3ac9-80a6-4f5e-875f-682ffc1ed9da', '浦发银行', '浦发银行', 'imgbase/bank/spdb.png', '6', 'normal');
INSERT INTO `bank` VALUES ('46420425-f42e-4c99-8ac7-2efcf1f21cb9', '工商银行', '工商银行', 'imgbase/bank/icbc.png', '1', 'normal');
INSERT INTO `bank` VALUES ('51ef18c9-3347-42a9-a72e-487b7cfe6d4a', '兴业银行', '兴业银行', 'imgbase/bank/cib.png', '17', 'normal');
INSERT INTO `bank` VALUES ('5edd82f1-de7a-4bdb-8c70-207606dbd1f4', '邮储银行', '邮储银行', 'imgbase/bank/psbc.png', '4', 'normal');
INSERT INTO `bank` VALUES ('8d8899eb-b59d-4559-a15e-db05e8364a5b', '江苏银行', '江苏银行', 'imgbase/bank/jsbank.png', '16', 'normal');
INSERT INTO `bank` VALUES ('9310c0f0-58c2-4216-b315-311e60de6ab1', '招商银行', '招商银行', 'imgbase/bank/cmb.png', '3', 'normal');
INSERT INTO `bank` VALUES ('9bd7396c-f656-4b16-8fd7-5c8c22157896', '平安银行', '平安银行', 'imgbase/bank/spabank.png', '11', 'normal');
INSERT INTO `bank` VALUES ('bd762d75-05b9-4909-a1e9-2de5e08212f0', '中信银行', '中信银行', 'imgbase/bank/citic.png', '8', 'normal');
INSERT INTO `bank` VALUES ('bec52214-869f-45f6-b698-6d6b2ed6bcbe', '上海银行', '上海银行', 'imgbase/bank/shbank.png', '15', 'normal');
INSERT INTO `bank` VALUES ('c32d324b-7a1e-45f8-95c7-0e85b5f13f94', '广发银行', '广发银行', 'imgbase/bank/gdb.png', '14', 'normal');
INSERT INTO `bank` VALUES ('cc9372af-a719-4c32-9750-edd0b856e537', '恒丰银行', '恒丰银行', 'imgbase/bank/egbank.png', '10', 'normal');
INSERT INTO `bank` VALUES ('ce5e3a4a-30a6-40ff-ac9d-c7e6a97cc07e', '交通银行', '交通银行', 'imgbase/bank/comm.png', '9', 'normal');
INSERT INTO `bank` VALUES ('dbf2c9ac-8197-4b41-8f93-1c932bf72e2a', '中国银行', '中国银行', 'imgbase/bank/boc.png', '0', 'normal');
INSERT INTO `bank` VALUES ('dc062d29-dc00-4cf6-a967-f87f00aa789b', '建设银行', '建设银行', 'imgbase/bank/ccb.png', '5', 'normal');
INSERT INTO `bank` VALUES ('f64d9ab0-7013-4165-ac23-fcd53c2bf406', '民生银行', '民生银行', 'imgbase/bank/cmbc.png', '13', 'normal');
INSERT INTO `bank` VALUES ('fc7e7bbf-48e6-4484-b453-deee5e20edfc', '农业银行', '农业银行', 'imgbase/bank/abc.png', '2', 'normal');

-- ----------------------------
-- Table structure for `callback`
-- ----------------------------
DROP TABLE IF EXISTS `callback`;
CREATE TABLE `callback` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `order_info` varchar(36) NOT NULL,
  `url` varchar(200) NOT NULL,
  `body` text,
  `times` int(11) NOT NULL DEFAULT '0',
  `status` varchar(20) NOT NULL COMMENT 'normal/finish',
  `processtime` varchar(200) DEFAULT NULL,
  `lasttime` timestamp NULL DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  CONSTRAINT `callback_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of callback
-- ----------------------------

-- ----------------------------
-- Table structure for `channel`
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `code` varchar(2) NOT NULL,
  `data` text NOT NULL,
  `callback_url` varchar(100) NOT NULL COMMENT '回调地址',
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('1075f929-5f6d-4d54-a64a-f728e0c92d04', '企业支付宝充值', '01', '{\"appid\":\"AppID\",\"privateKey\":\"商户私钥\",\"publicKey\":\"蚂蚁公钥\"}', 'http://alipay.yyxunyue.com', 'recharge', 'normal');
INSERT INTO `channel` VALUES ('5075f929-5f6d-1d54-a64a-f728e0c92e12', '企业银行卡提现', '09', '{\"bank\":\"所属银行\",\"holder\":\"企业名称\"}', 'https://', 'withdraw', 'normal');

-- ----------------------------
-- Table structure for `channel_account`
-- ----------------------------
DROP TABLE IF EXISTS `channel_account`;
CREATE TABLE `channel_account` (
  `uuid` varchar(255) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `account_num` varchar(50) NOT NULL,
  `transfer_url` varchar(100) NOT NULL,
  `data` text NOT NULL,
  `balance` decimal(20,0) NOT NULL,
  `poundage` decimal(20,0) DEFAULT NULL COMMENT '手续费（定额）',
  `poundage_rate` decimal(10,4) DEFAULT NULL COMMENT '手续费（百分比）',
  `max` decimal(20,0) NOT NULL,
  `min` decimal(20,0) NOT NULL,
  `daily_max` decimal(20,0) NOT NULL,
  `total_max` decimal(20,0) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  CONSTRAINT `channel_account_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel_account
-- ----------------------------
INSERT INTO `channel_account` VALUES ('495cb7ae-f71c-43e1-a2d5-40bd89f243e5', '5075f929-5f6d-1d54-a64a-f728e0c92e12', '亚亿讯跃银行卡提现', '19011601040008981', 'http://', '{\"holder\":\"亚亿讯跃（杭州）科技有限公司\",\"bank\":\"中国农业银行\"}', '0', '0', null, '10000000', '100', '1000000000', '100000000000', 'withdraw', 'normal', '2020-04-11 10:21:26');
INSERT INTO `channel_account` VALUES ('ed45432b-4404-4308-b675-1a813d9566f7', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '亚亿讯跃支付宝账户', '2088731293737112', 'http://alipay.yyxunyue.com', '{\"privateKey\":\"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpDHIWzst4+rdAPdaGyLYZfK8g69LsIFQfnR//FfjoLu5U+StKOEf/YN5libCDt5YnnAHe33VYapR/82IvyT4oXB+Kmuk7fYXDXCe1mNC0XlU8AxNnr4ZUR78rrbLoMUlJ/0eMNd2eGB/QbRGUWcoEGez6Pa2ObOZObJQRcLUFGhqRiAlopcFrnvFVLVdh6dpJNIqGebeyvUm1OjSR+xuDPtkZSw5qgsq7tVbUF/lE9h/lEwEfj90OHcSbtKR5kgQsmBSOXAfSKxZDXHCzV7tdVb1NCBNl2UuH9MfR5CxRk01EaXndDHOb25HmG15Xj5jCFxNA2M2QYiScwME0WOBFAgMBAAECggEAY5WYVAVIaiFP+IE1Mjtkwsjnpf4mR8GXrPPz2GJKOgUDk30hWlyjVTbsbGZbXIy24OiPVYrP5uEKUAHBUqM7ga0bS5yMTuZX+Uj9f4XhVI/JqEurj8Yf3OBTJtjxbsUyBmxi6p8EydAuVasa0wi98AKRzqys5B0U4xgldcyuZq0L97TWfFEB3QpjDyAJ8kKCi0qIdSymaS+TQVm+eXBh/GHxg+cH8uCUYVr3yZsTd9smgjBtIf0f2cFXat7/EtlhDAbfUVDwAXBMy7JYfZQuihV6Am7WqQ0gv79HrnChyQs10I344vS7NnEAnAhlMQQ9WCTTWIHCgAJFuVrSgZcdwQKBgQD4x5BX0o4H1RwSZrHIlEJ9+VsKCY9WxeyMa9sYYiT8KAz2eE4pewt/10dDsMb6aM0O39yOSfFh6bcur65kJ+dvMzBABmX+YEEiNVWIEW3rvOPJtCJrWYXawr3q5dcbPqR4ZH8cgqfZZt9iqXHMkWwGukP0+3V0ybNpKXVgjusX0QKBgQCt9HrMi+5LM+GXx0RIMEyIEhhnjQ3zGtWGJPRaef2vtoQyEX24h1tlAVu+9hMTVIHugsNTtnjP05TvQ/5+UbP/n5BSrS91YyNecq1DmJDepN4q5lWBluuU6pYDjI6bXS3s+skPrdAAlla/0vBdxYT4OvzdACx+6X1hSs1q3kxSNQKBgQDq90FjUSCIUUVEM+4yLaVnab4Mit7w69rw/WVfcELSTdmAaDwafXZs9hz+19X26EjUhabAVSJAggdbdt5VBkNWCZjGv8RRLwQVlR4Fd8DD6BGUjif8D/+WkFvjodU6jD223+QWP2jcsobFnpg2yBqpd/97QIy9E0v6RQfxfMqaIQKBgG9QP3g+nrjOtTAbmg0bow17jtRMhb2n0TTq1tZCs9xJzeQAH1QCgbBroxraejBnZlGKqZUa8coDmY1WFDce7zDHGWJeYj0auFpKxaa4rRNbqrMG+3ZWbd+4EVCqE+NFSAsaG6kFz1Fg4pgTUi6fI0x46rA82TxJCytD45jcMTh1AoGAaCajlgKAY89COnzhZcCmc3JkPUbFPhyexD5kgdO0zveHQoigM3ADJDca0ra3yAe3zZH5q3UZLsEKOI+VQchTMdtvaEjnTLRIxZCrBy1IC9JPr1bXJ2J/qQgoeK+YY0RvpS5MWdOHPwUAM3nk6dRsNxyQGKszq8/7Nf1dpKF5D9M=\",\"publicKey\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhuMqOHaU67Qxsav8ksYPBDZRiXLRCmjygWn9kvV9OXWMMh6q7U+6pfE2nYjdxm4iK4B8b8f1aCGSOZLdNyvTVYRvqTongwYBbbaahreyPOlEC/mUMAqJIBh3CV8E2AiLgtDOb3afIDLpNM1EIXhpPQZw/9NyJnvZ7nUMI4n3fmHBbmgxJATT0RsVelUemmMrjF2hijhl2a0xu0wEQ4IXgZ2LVxbCwOtWuNohC1PNqkI54ZvD1IDQMMP0adYP2Q64l1IuW8XfZldpaQlU2fbInyOoRQfECAyK19ZFw0SKkanbIqb6mOT/KGVdV1FPdaAp4oDgwH8K3uUFg30ZLF+kVwIDAQAB\",\"appid\":\"2021001109685087\"}', '0', null, '0.0060', '10000000', '1', '100000000', '10000000000', 'recharge', 'normal', '2020-04-11 09:56:14');

-- ----------------------------
-- Table structure for `channel_account_daily`
-- ----------------------------
DROP TABLE IF EXISTS `channel_account_daily`;
CREATE TABLE `channel_account_daily` (
  `uuid` varchar(255) NOT NULL,
  `amount` decimal(20,0) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel_account_daily
-- ----------------------------

-- ----------------------------
-- Table structure for `channel_account_history`
-- ----------------------------
DROP TABLE IF EXISTS `channel_account_history`;
CREATE TABLE `channel_account_history` (
  `uuid` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `channel_account` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `order_info` varchar(36) NOT NULL COMMENT '订单uuid',
  `order_num` varchar(30) NOT NULL,
  `poundage` decimal(20,0) NOT NULL,
  `amount` decimal(20,0) NOT NULL COMMENT '变动金额',
  `balance` decimal(20,0) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  KEY `channel_account` (`channel_account`),
  KEY `company` (`company`),
  CONSTRAINT `channel_account_history_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `channel_account_history_ibfk_2` FOREIGN KEY (`channel_account`) REFERENCES `channel_account` (`uuid`),
  CONSTRAINT `channel_account_history_ibfk_3` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel_account_history
-- ----------------------------

-- ----------------------------
-- Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `code` varchar(50) NOT NULL,
  `company_private` text COMMENT '用户储存',
  `company_public` text COMMENT '用户上传',
  `system_private` text NOT NULL COMMENT '自己生成',
  `system_public` text NOT NULL COMMENT '给用户',
  `white_url` text NOT NULL,
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  `recharge_poundage` decimal(10,0) DEFAULT NULL,
  `recharge_poundage_rate` decimal(10,4) DEFAULT NULL,
  `withdraw_poundage` decimal(10,0) DEFAULT NULL,
  `withdraw_poundage_rate` decimal(10,4) DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `code` (`code`),
  KEY `creator` (`creator`),
  CONSTRAINT `company_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('88908df7-b8ee-4b4f-a180-516a19659617', '幸运夺宝', '202004100000', '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', '*', 'normal', null, null, '0', null, '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-04-10 14:33:17');

-- ----------------------------
-- Table structure for `company_account`
-- ----------------------------
DROP TABLE IF EXISTS `company_account`;
CREATE TABLE `company_account` (
  `uuid` varchar(36) NOT NULL,
  `balance` decimal(20,0) NOT NULL,
  `balance_lock` decimal(20,0) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_account
-- ----------------------------
INSERT INTO `company_account` VALUES ('88908df7-b8ee-4b4f-a180-516a19659617', '0', '0');

-- ----------------------------
-- Table structure for `company_account_history`
-- ----------------------------
DROP TABLE IF EXISTS `company_account_history`;
CREATE TABLE `company_account_history` (
  `uuid` varchar(36) NOT NULL,
  `channel` varchar(36) DEFAULT NULL,
  `channel_account` varchar(36) DEFAULT NULL,
  `company` varchar(36) NOT NULL,
  `company_channel` varchar(36) DEFAULT NULL,
  `company_bankcard` varchar(36) DEFAULT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `order_info` varchar(36) NOT NULL COMMENT '订单uuid',
  `order_num` varchar(30) NOT NULL,
  `poundage` decimal(20,0) NOT NULL,
  `amount` decimal(20,0) NOT NULL COMMENT '变动金额',
  `balance` decimal(20,0) NOT NULL,
  `company_order_num` varchar(50) DEFAULT NULL COMMENT '商户订单号',
  `company_data` text COMMENT '商户数据',
  `submittime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '提交申请时间',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '完成交易时间',
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  KEY `company` (`company`),
  KEY `company_channel` (`company_channel`),
  KEY `channel_account` (`channel_account`),
  KEY `company_bankcard` (`company_bankcard`),
  CONSTRAINT `company_account_history_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `company_account_history_ibfk_2` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `company_account_history_ibfk_3` FOREIGN KEY (`company_channel`) REFERENCES `company_channel` (`uuid`),
  CONSTRAINT `company_account_history_ibfk_4` FOREIGN KEY (`channel_account`) REFERENCES `channel_account` (`uuid`),
  CONSTRAINT `company_account_history_ibfk_5` FOREIGN KEY (`company_bankcard`) REFERENCES `company_bankcard` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_account_history
-- ----------------------------

-- ----------------------------
-- Table structure for `company_admin`
-- ----------------------------
DROP TABLE IF EXISTS `company_admin`;
CREATE TABLE `company_admin` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `company` (`company`),
  CONSTRAINT `company_admin_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_admin
-- ----------------------------
INSERT INTO `company_admin` VALUES ('8515c942-f43f-471a-babe-3b10ccdd39a2', '88908df7-b8ee-4b4f-a180-516a19659617', '王维', '4d38cdac0040aaaef538b2fab323c1d2', '18888888888', null, 'normal', '2020-04-21 11:58:00');

-- ----------------------------
-- Table structure for `company_bankcard`
-- ----------------------------
DROP TABLE IF EXISTS `company_bankcard`;
CREATE TABLE `company_bankcard` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `bank` varchar(36) NOT NULL,
  `account_num` varchar(50) NOT NULL,
  `area` varchar(200) NOT NULL,
  `branch_bank` varchar(200) NOT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `bank` (`bank`),
  KEY `company` (`company`),
  CONSTRAINT `company_bankcard_ibfk_2` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `company_bankcard_ibfk_1` FOREIGN KEY (`bank`) REFERENCES `bank` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_bankcard
-- ----------------------------

-- ----------------------------
-- Table structure for `company_channel`
-- ----------------------------
DROP TABLE IF EXISTS `company_channel`;
CREATE TABLE `company_channel` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `max` decimal(10,0) NOT NULL COMMENT '最大金额',
  `min` decimal(10,0) NOT NULL COMMENT '最小金额',
  `poundage` decimal(20,0) DEFAULT NULL COMMENT '手续费（定额）',
  `poundage_rate` decimal(10,4) DEFAULT NULL COMMENT '手续费（百分比）',
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `company` (`company`),
  KEY `channel` (`channel`),
  KEY `creator` (`creator`),
  CONSTRAINT `company_channel_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `company_channel_ibfk_2` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `company_channel_ibfk_3` FOREIGN KEY (`creator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_channel
-- ----------------------------
INSERT INTO `company_channel` VALUES ('1f1495de-a2b9-42ea-a031-6eef6ec1897d', '88908df7-b8ee-4b4f-a180-516a19659617', '1075f929-5f6d-4d54-a64a-f728e0c92d04', 'recharge', '1000000', '100', null, '0.0060', 'normal', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-04-10 14:36:52');
INSERT INTO `company_channel` VALUES ('7e083559-9bd1-4344-87ad-339d88d073d5', '88908df7-b8ee-4b4f-a180-516a19659617', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'withdraw', '5000000', '100', '0', null, 'normal', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-04-10 14:38:58');

-- ----------------------------
-- Table structure for `company_trade`
-- ----------------------------
DROP TABLE IF EXISTS `company_trade`;
CREATE TABLE `company_trade` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL,
  `company` varchar(36) NOT NULL,
  `company_bankcard` varchar(36) DEFAULT NULL,
  `order_num` varchar(50) NOT NULL,
  `amount` decimal(20,0) NOT NULL COMMENT '扣除手续费金额',
  `poundage` decimal(20,0) NOT NULL,
  `total_amount` decimal(20,0) NOT NULL COMMENT '总金额',
  `data` text NOT NULL,
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NULL DEFAULT NULL,
  `proof` varchar(100) DEFAULT NULL,
  `fail_reason` varchar(100) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `order_num` (`order_num`),
  KEY `company` (`company`),
  KEY `operator` (`operator`),
  KEY `proof` (`proof`),
  KEY `company_bankcard` (`company_bankcard`),
  CONSTRAINT `company_trade_ibfk_3` FOREIGN KEY (`company_bankcard`) REFERENCES `company_bankcard` (`uuid`),
  CONSTRAINT `company_trade_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `company_trade_ibfk_2` FOREIGN KEY (`operator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_trade
-- ----------------------------

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  `parent` varchar(36) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `sort` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('11c18105-6344-11ea-93bd-89a085bb1b54', 'system', '渠道管理', '2', '7d664978-6343-11ea-93bd-89a085bb1b54', 'channelList.html', '1');
INSERT INTO `menu` VALUES ('4b377a88-6344-11ea-93bd-89a085bb1b54', 'system', '账号管理', '2', 'ad4b66ae-6343-11ea-93bd-89a085bb1b54', 'channelAccountList.html', '0');
INSERT INTO `menu` VALUES ('5412733e-1f63-42e1-53bd-ae1085bf1b15', 'system', '角色权限管理', '2', '7d664978-6343-11ea-93bd-89a085bb1b54', 'roleMethodList.html', '3');
INSERT INTO `menu` VALUES ('6bab521b-6344-11ea-93bd-89a085bb1b54', 'system', '商户管理', '2', '875f8b2b-6343-11ea-93bd-89a085bb1b54', 'companyList.html', '0');
INSERT INTO `menu` VALUES ('7d664978-6343-11ea-93bd-89a085bb1b54', 'system', '系统管理', '1', null, null, '0');
INSERT INTO `menu` VALUES ('875f8b2b-6343-11ea-93bd-89a085bb1b54', 'system', '商户管理', '1', null, null, '2');
INSERT INTO `menu` VALUES ('9d01cf07-7e2b-11ea-a748-d35c538dc949', 'store', '渠道管理', '2', 'ace2735e-132d-41e1-a3b2-f12081ba1b42', 'channelList.html', '1');
INSERT INTO `menu` VALUES ('a2e2736e-24a3-42e1-a748-d35c538dc9a2', 'store', '充值订单管理', '2', 'fce2736e-24a3-42e1-a748-d35c538dc951', 'rechargeList.html', '0');
INSERT INTO `menu` VALUES ('a44390e5-82d3-11ea-927c-80f961f7b39d', 'store', '商户流水', '2', 'ace2735e-132d-41e1-a3b2-f12081ba1b42', 'historyList.html', '2');
INSERT INTO `menu` VALUES ('a9666784-82d2-11ea-927c-80f961f7b39d', 'store', '商户订单管理', '2', 'fce2736e-24a3-42e1-a748-d35c538dc951', 'tradeList.html', '2');
INSERT INTO `menu` VALUES ('ace2735e-132d-41e1-a3b2-f12081ba1b42', 'store', '商户管理', '1', null, null, '0');
INSERT INTO `menu` VALUES ('ad4b66ae-6343-11ea-93bd-89a085bb1b54', 'system', '账号管理', '1', null, null, '1');
INSERT INTO `menu` VALUES ('aec8343f-6344-41e1-adbd-aea08abb1b41', 'system', '资金总账流水', '2', 'b292a778-6343-11ea-93bd-89a085bb1b54', 'systemHistoryList.html', '3');
INSERT INTO `menu` VALUES ('afc8343f-6344-11ea-93bd-89a085bb1b54', 'system', '支付宝充值审核', '2', 'b292a778-6343-11ea-93bd-89a085bb1b54', 'userRechargeList.html', '1');
INSERT INTO `menu` VALUES ('b292a778-6343-11ea-93bd-89a085bb1b54', 'system', '财务管理', '1', null, null, '3');
INSERT INTO `menu` VALUES ('bce1726e-aa43-52d8-a5b1-84a085bb1c61', 'system', '商户注资提现审核', '2', 'b292a778-6343-11ea-93bd-89a085bb1b54', 'companyTradeList.html', '0');
INSERT INTO `menu` VALUES ('d412736e-24a3-12ea-63ba-21b085bf1b4a', 'system', '角色页面管理', '2', '7d664978-6343-11ea-93bd-89a085bb1b54', 'roleMenuList.html', '2');
INSERT INTO `menu` VALUES ('dfc8143a-7341-21e6-13ad-79a08ab11b55', 'system', '提现审核', '2', 'b292a778-6343-11ea-93bd-89a085bb1b54', 'userWithdrawList.html', '2');
INSERT INTO `menu` VALUES ('f2e2736e-24a3-4a51-b748-79ac538dc95a', 'store', '提现订单管理', '2', 'fce2736e-24a3-42e1-a748-d35c538dc951', 'withdrawList.html', '1');
INSERT INTO `menu` VALUES ('f521636e-24a3-42e1-a748-675c538dc9f2', 'store', '商户管理', '2', 'ace2735e-132d-41e1-a3b2-f12081ba1b42', 'index.html', '0');
INSERT INTO `menu` VALUES ('fce2736e-24a3-42e1-a748-d35c538dc951', 'store', '订单管理', '1', null, null, '1');
INSERT INTO `menu` VALUES ('fce2736e-6343-11ea-93bd-89a085bb1b54', 'system', '管理员管理', '2', '7d664978-6343-11ea-93bd-89a085bb1b54', 'adminList.html', '0');

-- ----------------------------
-- Table structure for `method`
-- ----------------------------
DROP TABLE IF EXISTS `method`;
CREATE TABLE `method` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(300) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `level` int(11) NOT NULL,
  `parent` varchar(36) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_5` (`parent`),
  CONSTRAINT `method_ibfk_1` FOREIGN KEY (`parent`) REFERENCES `method` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='API方法名';

-- ----------------------------
-- Records of method
-- ----------------------------
INSERT INTO `method` VALUES ('099d64ae-ab74-4adf-bf71-4af132215446', '管理系统', '/system/**', '1', null, '0');
INSERT INTO `method` VALUES ('0a4a5f74-797b-11ea-93de-86d390580617', '角色页面', '/system/roleMenu/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '9');
INSERT INTO `method` VALUES ('0d45ac1e-797a-11ea-93de-86d390580617', '银行信息', '/system/bank/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '1');
INSERT INTO `method` VALUES ('1716c344-797b-11ea-93de-86d390580617', '角色功能', '/system/roleMethod/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '10');
INSERT INTO `method` VALUES ('2568a52a-797b-11ea-93de-86d390580617', '用户充值', '/system/userRecharge/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '11');
INSERT INTO `method` VALUES ('33674bb2-797b-11ea-93de-86d390580617', '用户提现', '/system/userWithdraw/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '12');
INSERT INTO `method` VALUES ('55dd61d2-797a-11ea-93de-86d390580617', '渠道信息', '/system/channel/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '2');
INSERT INTO `method` VALUES ('6ba45dd7-797a-11ea-93de-86d390580617', '渠道账户', '/system/channelAccount/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '3');
INSERT INTO `method` VALUES ('72175d25-797a-11ea-93de-86d390580617', '渠道账户流水', '/system/channelAccountHistory/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '4');
INSERT INTO `method` VALUES ('9b9254a1-797a-11ea-93de-86d390580617', '商户信息', '/system/company/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '5');
INSERT INTO `method` VALUES ('b67a83c0-797a-11ea-93de-86d390580617', '商户渠道', '/system/companyChannel/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '6');
INSERT INTO `method` VALUES ('cd242c74-797a-11ea-93de-86d390580617', '商户流水', '/system/companyAccountHistory/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '7');
INSERT INTO `method` VALUES ('d435d8bd-7979-11ea-93de-86d390580617', '管理员', '/system/admin/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '0');
INSERT INTO `method` VALUES ('e1a1be3f-797a-11ea-93de-86d390580617', '商户交易', '/system/companyTrade/**', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '8');

-- ----------------------------
-- Table structure for `mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `mobile_code`;
CREATE TABLE `mobile_code` (
  `uuid` varchar(36) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `content` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mobile_code
-- ----------------------------

-- ----------------------------
-- Table structure for `notice_info`
-- ----------------------------
DROP TABLE IF EXISTS `notice_info`;
CREATE TABLE `notice_info` (
  `uuid` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `order_num` varchar(30) NOT NULL,
  `data` text NOT NULL,
  `source` text NOT NULL COMMENT '第三方返回数据',
  `status` varchar(20) NOT NULL COMMENT 'normal/success/fail',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  CONSTRAINT `notice_info_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice_info
-- ----------------------------

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `size` bigint(20) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('9f82edd6-98a1-4e0e-ad64-059346525d82', '财务');
INSERT INTO `role` VALUES ('e71fd95e-adcd-4092-b230-21a457703a1d', '管理员');

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `menu` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `role` (`role`),
  KEY `menu` (`menu`),
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu`) REFERENCES `menu` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1e1ee538-c549-4566-8542-f9b4fdb8d946', 'e71fd95e-adcd-4092-b230-21a457703a1d', '6bab521b-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('25fed018-4dab-4063-b7c8-b469ef0114e6', 'e71fd95e-adcd-4092-b230-21a457703a1d', '875f8b2b-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('2ca16850-3dc8-4a57-be15-83e1faabcee0', '9f82edd6-98a1-4e0e-ad64-059346525d82', 'b292a778-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('360abfc9-89f1-42e6-9fd4-db6170e765d2', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'd412736e-24a3-12ea-63ba-21b085bf1b4a');
INSERT INTO `role_menu` VALUES ('3809ecac-50f1-4999-b492-eae40be254e4', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'aec8343f-6344-41e1-adbd-aea08abb1b41');
INSERT INTO `role_menu` VALUES ('3c261202-1146-4990-b5a9-208650fb73f4', '9f82edd6-98a1-4e0e-ad64-059346525d82', 'afc8343f-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('3c921ca0-e334-4f07-9f46-2d1cba87fd18', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'ad4b66ae-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('688099ac-7a9a-4c39-ad59-514326aef7dc', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'dfc8143a-7341-21e6-13ad-79a08ab11b55');
INSERT INTO `role_menu` VALUES ('6edcdc38-d0c5-427b-a4ca-2fd7afef5ad4', '9f82edd6-98a1-4e0e-ad64-059346525d82', 'aec8343f-6344-41e1-adbd-aea08abb1b41');
INSERT INTO `role_menu` VALUES ('7e7098c9-8346-49cd-bb5a-e0edf9f8b8d9', 'e71fd95e-adcd-4092-b230-21a457703a1d', '5412733e-1f63-42e1-53bd-ae1085bf1b15');
INSERT INTO `role_menu` VALUES ('8052dbb9-6967-4541-b17e-9ed4a57b609d', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'afc8343f-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('a65fe9e0-a8a2-4081-9b0c-8f0afa2d2dfa', '9f82edd6-98a1-4e0e-ad64-059346525d82', 'bce1726e-aa43-52d8-a5b1-84a085bb1c61');
INSERT INTO `role_menu` VALUES ('aaa3db0b-2b6e-4df6-9a23-6d6a6ae4c269', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'bce1726e-aa43-52d8-a5b1-84a085bb1c61');
INSERT INTO `role_menu` VALUES ('acf023bd-cf7e-4bea-b468-a3785f511779', '9f82edd6-98a1-4e0e-ad64-059346525d82', 'dfc8143a-7341-21e6-13ad-79a08ab11b55');
INSERT INTO `role_menu` VALUES ('aefbbd2f-8e32-49d4-ad3f-83e544840125', 'e71fd95e-adcd-4092-b230-21a457703a1d', '4b377a88-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('c4d40d3d-e3a2-480f-a924-e4e172edba3d', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'b292a778-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('dba0d31f-e438-4774-aa4d-f4ee1dbf48ae', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'fce2736e-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('ed16d539-bd1f-457c-a9dd-d399907bcf2b', 'e71fd95e-adcd-4092-b230-21a457703a1d', '11c18105-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('fbdba85a-729b-41fe-a5ae-08124981f477', 'e71fd95e-adcd-4092-b230-21a457703a1d', '7d664978-6343-11ea-93bd-89a085bb1b54');

-- ----------------------------
-- Table structure for `role_method`
-- ----------------------------
DROP TABLE IF EXISTS `role_method`;
CREATE TABLE `role_method` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `method` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `role` (`role`),
  KEY `method` (`method`),
  CONSTRAINT `role_method_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`),
  CONSTRAINT `role_method_ibfk_2` FOREIGN KEY (`method`) REFERENCES `method` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_method
-- ----------------------------
INSERT INTO `role_method` VALUES ('2a4afde5-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '099d64ae-ab74-4adf-bf71-4af132215446');
INSERT INTO `role_method` VALUES ('2a4b0044-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '0a4a5f74-797b-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b00da-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '0d45ac1e-797a-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b033d-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '1716c344-797b-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b03d7-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '2568a52a-797b-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b046d-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '33674bb2-797b-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b04ed-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '55dd61d2-797a-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b0572-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '6ba45dd7-797a-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b060c-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '72175d25-797a-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b0695-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', '9b9254a1-797a-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b0711-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'b67a83c0-797a-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b078d-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'cd242c74-797a-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b0811-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'd435d8bd-7979-11ea-93de-86d390580617');
INSERT INTO `role_method` VALUES ('2a4b0891-7a25-11ea-8d02-0a9cf0de870a', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'e1a1be3f-797a-11ea-93de-86d390580617');

-- ----------------------------
-- Table structure for `user_recharge`
-- ----------------------------
DROP TABLE IF EXISTS `user_recharge`;
CREATE TABLE `user_recharge` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `company_channel` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `channel_account` varchar(36) NOT NULL,
  `order_num` varchar(30) NOT NULL,
  `total_amount` decimal(20,0) NOT NULL COMMENT '总金额',
  `amount` decimal(20,0) DEFAULT NULL COMMENT '扣减手续费金额',
  `poundage` decimal(10,0) DEFAULT NULL,
  `company_order_num` varchar(50) NOT NULL COMMENT '商户订单号',
  `company_data` text COMMENT '商户携带信息',
  `company_notify_url` varchar(200) NOT NULL COMMENT '商户回调地址',
  `timeout` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `trans_data` text COMMENT '平台交易信息',
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NULL DEFAULT NULL,
  `notice_info` varchar(36) DEFAULT NULL,
  `proof` varchar(100) DEFAULT NULL,
  `fail_reason` varchar(100) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'checking/checked/fail/close/success',
  `process_code` varchar(20) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `order_num` (`order_num`) USING BTREE,
  KEY `company` (`company`),
  KEY `channel` (`channel`),
  KEY `channel_account` (`channel_account`),
  KEY `company_recharge_ibfk_4` (`company_channel`),
  KEY `operator` (`operator`),
  CONSTRAINT `user_recharge_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `user_recharge_ibfk_2` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `user_recharge_ibfk_3` FOREIGN KEY (`channel_account`) REFERENCES `channel_account` (`uuid`),
  CONSTRAINT `user_recharge_ibfk_4` FOREIGN KEY (`company_channel`) REFERENCES `company_channel` (`uuid`),
  CONSTRAINT `user_recharge_ibfk_5` FOREIGN KEY (`operator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for `user_withdraw`
-- ----------------------------
DROP TABLE IF EXISTS `user_withdraw`;
CREATE TABLE `user_withdraw` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `company_channel` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `channel_account` varchar(36) DEFAULT NULL,
  `order_num` varchar(30) NOT NULL,
  `total_amount` decimal(20,0) NOT NULL COMMENT '总金额',
  `amount` decimal(20,0) DEFAULT NULL COMMENT '扣减手续费金额',
  `poundage` decimal(20,0) DEFAULT NULL,
  `company_order_num` varchar(50) NOT NULL COMMENT '商户订单号',
  `company_data` text COMMENT '商户携带信息',
  `company_notify_url` varchar(200) NOT NULL COMMENT '商户回调地址',
  `trans_data` text COMMENT '平台交易信息',
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NULL DEFAULT NULL,
  `proof` varchar(100) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'checking/checked/fail/close/success',
  `fail_reason` varchar(100) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `order_num` (`order_num`) USING BTREE,
  KEY `company` (`company`),
  KEY `company_channel` (`company_channel`),
  KEY `channel` (`channel`),
  KEY `channel_account` (`channel_account`),
  KEY `operator` (`operator`),
  KEY `proof` (`proof`),
  CONSTRAINT `user_withdraw_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_2` FOREIGN KEY (`company_channel`) REFERENCES `company_channel` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_3` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_4` FOREIGN KEY (`channel_account`) REFERENCES `channel_account` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_5` FOREIGN KEY (`operator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_withdraw
-- ----------------------------

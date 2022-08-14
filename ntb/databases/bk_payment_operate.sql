/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-12-01 18:06:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bk_payment_operate`
-- ----------------------------
DROP TABLE IF EXISTS `bk_payment_operate`;
CREATE TABLE `bk_payment_operate` (
  `uuid` varchar(36) NOT NULL,
  `bk_payment` varchar(36) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `value` text NOT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `checker` varchar(36) DEFAULT NULL,
  `checktime` timestamp NULL DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `submittime` timestamp NULL DEFAULT NULL COMMENT '提交审核时间',
  `old` text,
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `bk_payment_operate_payment` (`bk_payment`),
  CONSTRAINT `bk_payment_operate_ibfk_2` FOREIGN KEY (`checker`) REFERENCES `bk_operator` (`uuid`),
  CONSTRAINT `bk_payment_operate_payment` FOREIGN KEY (`bk_payment`) REFERENCES `bk_payment` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_payment_operate
-- ----------------------------

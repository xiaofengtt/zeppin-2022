/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-11-28 10:49:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `version`
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `uuid` varchar(36) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `version_name` varchar(20) NOT NULL DEFAULT '' COMMENT '版本名称',
  `version_des` varchar(200) DEFAULT '' COMMENT '版本描述',
  `device` varchar(10) NOT NULL DEFAULT '' COMMENT '设备',
  `flag_compel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否强制更新',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creator` varchar(36) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT '' COMMENT '版本状态',
  `resource` varchar(36) NOT NULL COMMENT '资源包',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `uk_version` (`version`,`device`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of version
-- ----------------------------

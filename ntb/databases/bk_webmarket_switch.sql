/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-11-28 17:19:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bk_webmarket_switch`
-- ----------------------------
DROP TABLE IF EXISTS `bk_webmarket_switch`;
CREATE TABLE `bk_webmarket_switch` (
  `uuid` varchar(36) NOT NULL,
  `web_market` varchar(20) NOT NULL DEFAULT '' COMMENT '应用市场标识码',
  `web_market_name` varchar(20) NOT NULL DEFAULT '' COMMENT '应用市场名称描述',
  `flag_switch` tinyint(1) NOT NULL DEFAULT '0' COMMENT '开关 默认关',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(36) NOT NULL DEFAULT '' COMMENT '创建人',
  `version` varchar(36) NOT NULL COMMENT '版本号',
  `status` varchar(10) NOT NULL DEFAULT '' COMMENT '状态 normal deleted',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `uk_webmarket` (`web_market`,`version`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_webmarket_switch
-- ----------------------------

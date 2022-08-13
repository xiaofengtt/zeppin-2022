/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.120_3306
Source Server Version : 50520
Source Host           : 192.168.1.120:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2016-11-22 13:04:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `service_apply_reply`
-- ----------------------------
DROP TABLE IF EXISTS `service_apply_reply`;
CREATE TABLE `service_apply_reply` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SERVICE_APPLY` int(11) NOT NULL COMMENT '下级申请外键',
  `COUNTENT` varchar(500) NOT NULL COMMENT '回复内容',
  `CREATOR` int(11) NOT NULL COMMENT '回复人',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `STATUS` tinyint(4) NOT NULL COMMENT '状态1-正常 0-删除',
  `CREATOR_ROLE` tinyint(4) NOT NULL COMMENT '回复人角色',
  PRIMARY KEY (`ID`),
  KEY `FK_SERVICE_APPLY_reply` (`SERVICE_APPLY`),
  KEY `INDEXT_CONTENT_reply` (`COUNTENT`(255)) USING BTREE,
  CONSTRAINT `FK_SERVICE_APPLY_reply` FOREIGN KEY (`SERVICE_APPLY`) REFERENCES `mail_information` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of service_apply_reply
-- ----------------------------

/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.120_3306
Source Server Version : 50520
Source Host           : 192.168.1.120:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2016-08-19 13:39:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `service_apply`
-- ----------------------------
DROP TABLE IF EXISTS `service_apply`;
CREATE TABLE `service_apply` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COUNTENT` varchar(500) NOT NULL COMMENT '申请正文',
  `REPLY_TEXT` varchar(500) DEFAULT NULL COMMENT '反馈',
  `CREATOR` int(11) NOT NULL COMMENT '申请人',
  `CREATOR_TYPE` tinyint(4) NOT NULL COMMENT '申请人类型1-管理员 2-承训单位',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '申请时间',
  `CHECKER` int(11) DEFAULT NULL COMMENT '审核人 或 回复人',
  `CHECKTIME` timestamp NULL DEFAULT NULL COMMENT '回复时间',
  `STATUS` tinyint(4) NOT NULL COMMENT '处理状态0-未回复 1-已回复',
  `TYPE` tinyint(4) NOT NULL COMMENT '申请类型 1-添加承训单位申请 2-添加培训学科申请 3-其他申请',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of service_apply
-- ----------------------------

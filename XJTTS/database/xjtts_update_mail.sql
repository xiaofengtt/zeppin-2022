/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-03-22 12:12:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mail_information`
-- ----------------------------
DROP TABLE IF EXISTS `mail_information`;
CREATE TABLE `mail_information` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(200) NOT NULL COMMENT '标题',
  `TEXT` varchar(5000) NOT NULL COMMENT '正文',
  `INSCRIPTION` varchar(200) NOT NULL COMMENT '落款',
  `TYPE` tinyint(4) NOT NULL COMMENT '1站内信 2短信',
  `CREATOR` int(11) NOT NULL,
  `CREATOR_ROLE` tinyint(4) NOT NULL COMMENT '1-管理员 2-承训单位 3-培训教师 4-培训专家 5-超级管理员',
  `CREATTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `SENDTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发送时间',
  `SENDSTATUS` tinyint(4) NOT NULL COMMENT '1-定时 2-非定时',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mail_information
-- ----------------------------

/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-03-22 12:12:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mail_connection`
-- ----------------------------
DROP TABLE IF EXISTS `mail_connection`;
CREATE TABLE `mail_connection` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MAIL_INFORMATION` int(11) NOT NULL,
  `ADDRESSEE` int(11) NOT NULL COMMENT '收件人',
  `ADDRESSEE_ROLE` tinyint(4) NOT NULL COMMENT '收件人角色',
  `STATUS` tinyint(4) NOT NULL COMMENT '0-未发送 1-未读 2-已读 3-删除',
  PRIMARY KEY (`ID`),
  KEY `FK_MAIL_INFORMATION` (`MAIL_INFORMATION`),
  CONSTRAINT `FK_MAIL_INFORMATION` FOREIGN KEY (`MAIL_INFORMATION`) REFERENCES `mail_information` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mail_connection
-- ----------------------------

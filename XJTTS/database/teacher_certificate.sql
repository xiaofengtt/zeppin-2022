/*
Navicat MySQL Data Transfer

Source Server         : xjjspxgl.zeppin.cn
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : xjtts_20161119

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-12-15 00:03:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `teacher_certificate`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_certificate`;
CREATE TABLE `teacher_certificate` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER` int(11) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `CERTIFICATE` varchar(20) NOT NULL,
  `CERTIFICATION_BODY` varchar(100) NOT NULL,
  `GETTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `TYPE` varchar(50) NOT NULL COMMENT '资格证类型',
  PRIMARY KEY (`ID`),
  KEY `FK_TEACHER_CERTIFICATE` (`TEACHER`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_certificate
-- ----------------------------

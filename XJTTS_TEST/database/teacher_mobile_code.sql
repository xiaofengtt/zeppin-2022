/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjtts

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2015-07-23 15:48:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `teacher_mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_mobile_code`;
CREATE TABLE `teacher_mobile_code` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER` int(11) DEFAULT NULL,
  `CODE` varchar(10) NOT NULL,
  `CREATTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UUID` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_KEY` (`UUID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_mobile_code
-- ----------------------------

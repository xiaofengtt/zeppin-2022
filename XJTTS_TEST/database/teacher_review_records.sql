/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2015-08-20 10:17:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `teacher_review_records`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_review_records`;
CREATE TABLE `teacher_review_records` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER` int(11) NOT NULL,
  `TYPE` tinyint(4) NOT NULL COMMENT '0未通过审核1通过审核',
  `CHECKER` int(11) NOT NULL,
  `CHECKTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `REASON` varchar(100) DEFAULT NULL COMMENT '未通过审核原因',
  PRIMARY KEY (`ID`),
  KEY `teacher_review_records_ibfk_1` (`TEACHER`) USING BTREE,
  CONSTRAINT `teacher_review_records_ibfk_1` FOREIGN KEY (`TEACHER`) REFERENCES `teacher` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of teacher_review_records
-- ----------------------------

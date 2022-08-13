/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjtts_mc

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-08-04 17:58:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `teacher_adjust`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_adjust`;
CREATE TABLE `teacher_adjust` (
  `ID` int(11) NOT NULL,
  `TEACHER` int(11) NOT NULL,
  `O_ORGANIZATION` int(11) NOT NULL COMMENT '教师调出学校',
  `N_ORGANIZATION` int(11) NOT NULL COMMENT '教师调入学校',
  `CREATOR` int(11) NOT NULL COMMENT '申请者',
  `CREATOR_TYPE` tinyint(4) NOT NULL COMMENT '申请人类型',
  `CHECKER` int(11) DEFAULT NULL COMMENT '审核人',
  `CHECKER_TYPE` tinyint(4) DEFAULT NULL COMMENT '审核人类型',
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` tinyint(4) NOT NULL COMMENT '0-未确认 1-已通过 2-未通过',
  PRIMARY KEY (`ID`),
  KEY `FK_ADJUST_ORGANIZATION_O` (`O_ORGANIZATION`),
  KEY `FK_ADJUST_ORGANIZATION_N` (`N_ORGANIZATION`),
  KEY `FK_ADJUST_TEACHER` (`TEACHER`),
  CONSTRAINT `FK_ADJUST_ORGANIZATION_O` FOREIGN KEY (`O_ORGANIZATION`) REFERENCES `organization` (`ID`),
  CONSTRAINT `FK_ADJUST_ORGANIZATION_N` FOREIGN KEY (`N_ORGANIZATION`) REFERENCES `organization` (`ID`),
  CONSTRAINT `FK_ADJUST_TEACHER` FOREIGN KEY (`TEACHER`) REFERENCES `teacher` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_adjust
-- ----------------------------

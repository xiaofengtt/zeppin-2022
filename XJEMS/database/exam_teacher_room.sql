/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjems

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-07-26 17:01:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `exam_teacher_room`
-- ----------------------------
DROP TABLE IF EXISTS `exam_teacher_room`;
CREATE TABLE `exam_teacher_room` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EXAM` int(11) NOT NULL COMMENT '考试',
  `EXAM_ROOM` int(11) DEFAULT NULL COMMENT '考场',
  `TEACHER` int(11) NOT NULL COMMENT '监考教师',
  `IS_CHIEF` tinyint(4) DEFAULT '0' COMMENT '是否为主考 0否 1是',
  `IS_MIXED` tinyint(4) DEFAULT '0' COMMENT '是否混考 0否 1是',
  `IS_CONFIRM` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否确认 0否 1是',
  `CONFIRM_TIME` timestamp NULL DEFAULT NULL COMMENT '确认时间',
  `OPERATER` int(11) DEFAULT NULL COMMENT '（审核）操作人',
  `IS_AUTO` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否自主申报 0否 1是',
  `CREATOR` int(11) NOT NULL COMMENT '（申报）操作人',
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '分配时间',
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1-正常 0-失效（删除）',
  `CREDIT` int(4) NOT NULL COMMENT '得分',
  `APPLY_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
  `REASON` varchar(20) DEFAULT NULL COMMENT '评价',
  `IS_FIRST_APPLY` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否初次申报',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FK_EXAM_T_EXAM` (`EXAM`),
  KEY `FK_EXAM_T_TEACHER` (`TEACHER`),
  KEY `FK_EXAM_T_ROOM` (`EXAM_ROOM`),
  CONSTRAINT `FK_EXAM_T_EXAM` FOREIGN KEY (`EXAM`) REFERENCES `exam_information` (`ID`),
  CONSTRAINT `FK_EXAM_T_ROOM` FOREIGN KEY (`EXAM_ROOM`) REFERENCES `exam_room` (`ID`),
  CONSTRAINT `FK_EXAM_T_TEACHER` FOREIGN KEY (`TEACHER`) REFERENCES `invigilation_teacher` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_teacher_room
-- ----------------------------

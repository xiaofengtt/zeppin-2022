/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjems

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-07-27 14:27:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `invigilation_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `invigilation_teacher`;
CREATE TABLE `invigilation_teacher` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL COMMENT '姓名',
  `PINYIN` varchar(100) NOT NULL COMMENT '姓名拼音',
  `IDCARD` varchar(20) NOT NULL COMMENT '身份证号',
  `MOBILE` varchar(12) NOT NULL COMMENT '手机号',
  `SEX` tinyint(4) NOT NULL COMMENT '性别1男 2女',
  `ETHNIC` smallint(6) NOT NULL COMMENT '民族',
  `PHOTO` int(11) DEFAULT '0' COMMENT '头像',
  `MAJOR` varchar(20) NOT NULL COMMENT '所学专业',
  `TYPE` smallint(6) NOT NULL DEFAULT '0' COMMENT '身份类别',
  `ORGANIZATION` varchar(30) NOT NULL COMMENT '所在学院或部门',
  `INSHCOOL_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入校时间',
  `IS_CHIEF_EXAMINER` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有主考经验',
  `IS_MIXED_EXAMINER` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有混考经验',
  `INTEGRAL` int(6) NOT NULL DEFAULT '0' COMMENT '监考累计积分（可为负数）',
  `SPECIALTY` varchar(50) DEFAULT '' COMMENT '教师特长',
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1正常 0停用 -1删除',
  `REASON` varchar(100) DEFAULT '' COMMENT '停用原因',
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `CHECK_STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核状态 1未审核 0未通过 2已通过',
  `CHECK_TIME` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '审核时间',
  `CHECKER` int(11) DEFAULT '0' COMMENT '审核人',
  `CHECK_REASON` varchar(100) DEFAULT '' COMMENT '审核不通过原因',
  `INVIGILATE_CAMPUS` varchar(10) DEFAULT '0' COMMENT '监考校区',
  `INVIGILATE_TYPE` tinyint(4) DEFAULT '0' COMMENT '监考类型',
  `INVIGILATE_COUNT` int(4) DEFAULT '0' COMMENT '监考次数默认0',
  `JOB_DUTY` varchar(20) DEFAULT '' COMMENT '职务',
  `STUDY_MAJOR` varchar(20) DEFAULT '' COMMENT '研究生所学专业',
  `STUDY_GRADE` varchar(20) DEFAULT '' COMMENT '研究生所在年级',
  `REMARK` varchar(100) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FK_TEACHER_ETHNIC` (`ETHNIC`),
  KEY `FK_TEACHER_PHOTO` (`PHOTO`),
  KEY `FK_TEACHER_TYPE` (`TYPE`),
  KEY `INDEX_NAME_PINYIN_IDCARD_MOBILE` (`NAME`,`PINYIN`,`IDCARD`,`MOBILE`) USING BTREE,
  KEY `INDEX_NAME` (`NAME`) USING BTREE,
  KEY `INDEX_IDCARD` (`IDCARD`) USING BTREE,
  KEY `INDEX_PINYIN` (`PINYIN`) USING BTREE,
  CONSTRAINT `FK_TEACHER_ETHNIC` FOREIGN KEY (`ETHNIC`) REFERENCES `ethnic` (`ID`),
  CONSTRAINT `FK_TEACHER_PHOTO` FOREIGN KEY (`PHOTO`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of invigilation_teacher
-- ----------------------------

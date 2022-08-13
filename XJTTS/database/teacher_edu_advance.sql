/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjtts_mc

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-09-22 14:42:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `teacher_edu_advance`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_edu_advance`;
CREATE TABLE `teacher_edu_advance` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER` int(11) NOT NULL DEFAULT '0' COMMENT '申请老师',
  `GRADUATION` varchar(50) NOT NULL COMMENT '毕业院校',
  `MAJOR` varchar(50) NOT NULL COMMENT '进修专业',
  `STARTTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '进修开始时间',
  `ENDTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '进修结束时间',
  `CERTIFICATE` varchar(20) NOT NULL COMMENT '毕业证编号',
  `FINAL_STATUS` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '终审状态1-通过  0-未通过 -1-未审核',
  `STATUS` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '审核状态 1-通过  0-未通过 -1-未审核',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '提交申请时间',
  `EDUCATION_BACKGROUND` tinyint(4) NOT NULL COMMENT '提升到学历',
  `OLD_EDUCATION_BACKGROUND` varchar(20) DEFAULT NULL COMMENT '提升前学历',
  PRIMARY KEY (`ID`),
  KEY `FK_TEACHER_EDUCATION_BACKGROUND` (`EDUCATION_BACKGROUND`),
  KEY `FK_EDU_TEACHER` (`TEACHER`),
  CONSTRAINT `FK_EDU_TEACHER` FOREIGN KEY (`TEACHER`) REFERENCES `teacher` (`ID`),
  CONSTRAINT `FK_TEACHER_EDUCATION_BACKGROUND` FOREIGN KEY (`EDUCATION_BACKGROUND`) REFERENCES `eduction_background` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_edu_advance
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher_edu_advance_adu`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_edu_advance_adu`;
CREATE TABLE `teacher_edu_advance_adu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER_EDU_ADVANCE` int(11) NOT NULL,
  `CHECKER` int(11) NOT NULL COMMENT '审核人',
  `TYPE` tinyint(4) NOT NULL COMMENT '审核类型 1-通过 2-终审通过 3-不通过',
  `REASON` varchar(100) DEFAULT NULL COMMENT '不通过原因',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  KEY `FK_EDU_ADVANCE` (`TEACHER_EDU_ADVANCE`),
  CONSTRAINT `FK_EDU_ADVANCE` FOREIGN KEY (`TEACHER_EDU_ADVANCE`) REFERENCES `teacher_edu_advance` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_edu_advance_adu
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher_edu_evidence`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_edu_evidence`;
CREATE TABLE `teacher_edu_evidence` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER_EDU_ADVANCE` int(11) NOT NULL COMMENT '学历提升认证提交记录',
  `DOCUMENT` int(11) NOT NULL COMMENT '学历提升认定证明材料',
  PRIMARY KEY (`ID`),
  KEY `FK_Evidence_teacheredu` (`TEACHER_EDU_ADVANCE`),
  KEY `FK_Evidence_document` (`DOCUMENT`),
  CONSTRAINT `FK_Evidence_document` FOREIGN KEY (`DOCUMENT`) REFERENCES `document` (`ID`),
  CONSTRAINT `FK_Evidence_teacheredu` FOREIGN KEY (`TEACHER_EDU_ADVANCE`) REFERENCES `teacher_edu_advance` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_edu_evidence
-- ----------------------------

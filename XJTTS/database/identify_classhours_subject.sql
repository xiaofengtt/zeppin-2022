/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjtts_mc

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-08-30 17:54:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `identify_classhours_subject`
-- ----------------------------
DROP TABLE IF EXISTS `identify_classhours_subject`;
CREATE TABLE `identify_classhours_subject` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_TYPE` int(11) NOT NULL COMMENT '外键：项目类型',
  `CENTRALIZE` int(4) NOT NULL COMMENT '集中培训学时',
  `INFORMATION` int(4) NOT NULL COMMENT '信息技术培训学时',
  `REGIONAL` int(4) NOT NULL COMMENT '区域特色培训学时',
  `SCHOOL` int(4) NOT NULL COMMENT '校本培训学时',
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` tinyint(4) NOT NULL COMMENT '状态1正常 0失效',
  `TRAINING_SUBJECT` smallint(6) NOT NULL COMMENT '培训学科',
  PRIMARY KEY (`ID`),
  KEY `FK_IDENTIFY_CLASSHOURS_PROJECTTYPE` (`PROJECT_TYPE`),
  KEY `FK_IDENTIFY_CLASS_SUBJECT` (`TRAINING_SUBJECT`),
  CONSTRAINT `FK_IDENTIFY_CLASS_SUBJECT` FOREIGN KEY (`TRAINING_SUBJECT`) REFERENCES `training_subject` (`ID`),
  CONSTRAINT `identify_classhours_subject_ibfk_1` FOREIGN KEY (`PROJECT_TYPE`) REFERENCES `project_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of identify_classhours_subject
-- ----------------------------

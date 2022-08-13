/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjtts_mc

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-09-13 11:41:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `project_cycle_classhours`
-- ----------------------------
DROP TABLE IF EXISTS `project_cycle_classhours`;
CREATE TABLE `project_cycle_classhours` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_CYCLE` int(11) NOT NULL COMMENT '项目周期',
  `CENTRALIZE` int(7) NOT NULL COMMENT '集中培训学时标准',
  `INFORMATION` int(7) NOT NULL COMMENT '信息技术培训学时',
  `REGIONAL` int(7) NOT NULL COMMENT '区域特色培训学时标准',
  `SCHOOL` int(7) NOT NULL COMMENT '校本培训学时标准',
  `STATUS` tinyint(4) NOT NULL COMMENT '状态1-正常  0-停用',
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FK_PROJECT_CYCLE_CLASSHOURS` (`PROJECT_CYCLE`),
  CONSTRAINT `FK_PROJECT_CYCLE_CLASSHOURS` FOREIGN KEY (`PROJECT_CYCLE`) REFERENCES `project_cycle` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_cycle_classhours
-- ----------------------------

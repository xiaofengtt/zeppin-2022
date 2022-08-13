/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjtts_mc

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-08-30 17:54:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `project_cycle`
-- ----------------------------
DROP TABLE IF EXISTS `project_cycle`;
CREATE TABLE `project_cycle` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL COMMENT '周期名称',
  `START_YEAR` char(4) NOT NULL DEFAULT '' COMMENT '周期开始年份',
  `END_YERA` char(4) NOT NULL DEFAULT '' COMMENT '周期结束年份',
  `CREATOR` int(11) NOT NULL COMMENT '创建人',
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `STATUS` tinyint(4) NOT NULL COMMENT '周期状态，1-正常 0-停用 -1-删除',
  PRIMARY KEY (`ID`),
  KEY `INDEX_PROJECT_CYCLE_NAME` (`NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_cycle
-- ----------------------------

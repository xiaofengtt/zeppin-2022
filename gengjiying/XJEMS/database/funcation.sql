/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjems_test

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-08-11 15:24:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `funcation`
-- ----------------------------
DROP TABLE IF EXISTS `funcation`;
CREATE TABLE `funcation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `PATH` varchar(200) DEFAULT NULL,
  `LEVEL` tinyint(4) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `PARENT` int(11) DEFAULT NULL,
  `SCODE` varchar(100) DEFAULT NULL,
  `ICON` varchar(50) DEFAULT NULL COMMENT '图表地址',
  PRIMARY KEY (`ID`),
  KEY `FK_REFERENCE_43` (`PARENT`),
  CONSTRAINT `FK_REFERENCE_43` FOREIGN KEY (`PARENT`) REFERENCES `funcation` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of funcation
-- ----------------------------
INSERT INTO `funcation` VALUES ('1', '考试管理', null, '1', '1', null, '0001', '../img/examManage');
INSERT INTO `funcation` VALUES ('2', '考试管理中心', '../admin/main.jsp', '2', '1', '1', '00010001', null);
INSERT INTO `funcation` VALUES ('3', '监考教师管理', null, '1', '1', null, '0002', '../img/teacherManage');
INSERT INTO `funcation` VALUES ('4', '监考教师信息管理', '../admin/teachersMessage.jsp', '2', '1', '3', '00020001', null);
INSERT INTO `funcation` VALUES ('5', '监考教师资格审核', '../admin/teachersAuditing.jsp', '2', '1', '3', '00020002', null);
INSERT INTO `funcation` VALUES ('6', '历史考试管理', null, '1', '1', null, '0003', '../img/examHistoryManage');
INSERT INTO `funcation` VALUES ('7', '历史考试信息查询', '../admin/historyInfo.jsp', '2', '1', '6', '00030001', null);

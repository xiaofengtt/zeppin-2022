/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjems

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-07-31 16:38:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` tinyint(4) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `SOURCE_PATH` varchar(200) NOT NULL,
  `PATH` varchar(200) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `TITLE` varchar(200) NOT NULL,
  `SUFFIX` varchar(10) NOT NULL,
  `FILESIZE` int(11) NOT NULL DEFAULT '0',
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1正常2删除',
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '1', 'userBig', 'img/userBig.png', 'img/userBig.png', '../img/userBig.png', 'managerPhoto', '.png', '11085', '1', '1', '2017-07-31 11:12:26');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(50) NOT NULL,
  `PHONE` varchar(20) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `ROLE` int(11) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `ORGANIZATION` int(11) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `CREATOR` int(11) DEFAULT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PHOTO` int(11) NOT NULL DEFAULT '1' COMMENT '管理员头像',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_KEY_2` (`EMAIL`),
  UNIQUE KEY `AK_KEY_3` (`PHONE`),
  KEY `FK_REFERENCE_34` (`CREATOR`),
  KEY `FK_REFERENCE_39` (`ROLE`),
  KEY `FK_REFERENCE_64` (`ORGANIZATION`),
  CONSTRAINT `FK_CREATOR` FOREIGN KEY (`CREATOR`) REFERENCES `sys_user` (`ID`),
  CONSTRAINT `FK_ROLE_SYS_USER` FOREIGN KEY (`ROLE`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin@zeppin.cn', '17777777777', '超级管理员', '1', 'ZePpIn@)!%', '1', '1', '1', '2015-01-04 15:21:52', '1');
INSERT INTO `sys_user` VALUES ('194', 'qinlong@zeppin.cn', '18701376560', '秦龙', '2', '61399256', '1', '1', '1', '2015-02-09 15:10:42', '1');
INSERT INTO `sys_user` VALUES ('195', 'zhangyahui@zeppin.cn', '18660351920', '张亚辉', '2', '351920', '1', '0', '1', '2015-02-09 15:13:13', '1');
INSERT INTO `sys_user` VALUES ('196', '740027729@qq.com', '15049105964', '刘莉莉', '2', '105964', '1', '1', '1', '2015-02-09 15:14:00', '1');
INSERT INTO `sys_user` VALUES ('197', 'zhangxuning@zeppin.cn', '18504410371', '张旭宁', '2', 's13110857', '1', '1', '1', '2015-06-17 09:49:46', '1');
INSERT INTO `sys_user` VALUES ('198', 'rongjingfeng@zeppin.cn', '18611920344', '荣景峰', '2', 'rongjingfeng!@#', '1', '1', '1', '2015-06-19 12:07:46', '1');
INSERT INTO `sys_user` VALUES ('200', 'liuyuzhen@zeppin.cn', '15836329585', '刘玉珍', '2', '329585', '1', '1', '1', '2015-07-03 22:24:04', '1');

/*
Navicat MySQL Data Transfer

Source Server         : xjjspxgl.zeppin.cn
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : xjttsss

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-11-22 16:10:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mail_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `mail_attachment`;
CREATE TABLE `mail_attachment` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `MAIL_INFORMATION` int(11) NOT NULL COMMENT '站内信外键',
  `DOCUMENT` int(11) NOT NULL COMMENT '附件（文件）外键',
  PRIMARY KEY (`ID`),
  KEY `FK_ATTACHMENT_DOCUMENT` (`DOCUMENT`),
  KEY `FK_MAIL_INFORMATION_ATTA` (`MAIL_INFORMATION`),
  CONSTRAINT `FK_ATTACHMENT_DOCUMENT` FOREIGN KEY (`DOCUMENT`) REFERENCES `document` (`ID`),
  CONSTRAINT `FK_MAIL_INFORMATION_ATTA` FOREIGN KEY (`MAIL_INFORMATION`) REFERENCES `mail_information` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mail_attachment
-- ----------------------------

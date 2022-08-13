/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-07-26 17:31:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `teacher_training_assigned`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_training_assigned`;
CREATE TABLE `teacher_training_assigned` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER` int(11) NOT NULL,
  `PROJECT` int(11) NOT NULL,
  `TRAINING_SUBJECT` smallint(6) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态0-失效  1-正常',
  PRIMARY KEY (`ID`),
  KEY `FK_REF_TTA_TEACHER` (`TEACHER`),
  KEY `FK_REF_TTA_PROJECT` (`PROJECT`),
  KEY `FK_REF_TTA_TRAININGCOLLEGE` (`TRAINING_SUBJECT`),
  CONSTRAINT `FK_REF_TTA_TRAININGCOLLEGE` FOREIGN KEY (`TRAINING_SUBJECT`) REFERENCES `training_subject` (`ID`),
  CONSTRAINT `FK_REF_TTA_PROJECT` FOREIGN KEY (`PROJECT`) REFERENCES `project` (`ID`),
  CONSTRAINT `FK_REF_TTA_TEACHER` FOREIGN KEY (`TEACHER`) REFERENCES `teacher` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_training_assigned
-- ----------------------------

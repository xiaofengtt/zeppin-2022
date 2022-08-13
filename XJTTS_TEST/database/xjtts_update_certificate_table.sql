/*
Navicat MySQL Data Transfer

Source Server         : xjjspxgl.zeppin.cn
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2015-12-21 16:55:02
*/


-- ----------------------------
-- Table structure for `teacher_training_certificate`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_training_certificate`;
CREATE TABLE `teacher_training_certificate` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER_TRAINING_RECORDS` int(11) NOT NULL,
  `IMAGE_URL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TTC_TTR` (`TEACHER_TRAINING_RECORDS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_training_certificate
-- ----------------------------

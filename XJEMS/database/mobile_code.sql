/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : self_cool

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-08-08 09:39:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `mobile_code`;
CREATE TABLE `mobile_code` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER` int(11) DEFAULT NULL,
  `MOBILE` varchar(20) NOT NULL,
  `UUID` varchar(50) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1正常 0停用',
  `CODE` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mobile_code
-- ----------------------------
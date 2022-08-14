/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.120_3306
Source Server Version : 50520
Source Host           : 192.168.1.120:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-11-28 10:50:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bk_payment`
-- ----------------------------
DROP TABLE IF EXISTS `bk_payment`;
CREATE TABLE `bk_payment` (
  `uuid` varchar(36) NOT NULL,
  `payment` varchar(20) NOT NULL DEFAULT '' COMMENT '支付方式',
  `flag_switch` tinyint(1) NOT NULL DEFAULT '1' COMMENT '支付开关 默认开',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(36) NOT NULL DEFAULT '' COMMENT '创建人',
  `status` varchar(10) NOT NULL DEFAULT '' COMMENT '状态 nomal  delete',
  `payment_des` varchar(50) NOT NULL DEFAULT '' COMMENT '支付方式描述',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UK_PAYMENT` (`payment`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_payment
-- ----------------------------
INSERT INTO `bk_payment` VALUES ('265cf93b-cf70-11e7-a254-ed8dfb4257ee', 'wechart', '0', '2017-11-22 18:28:35', '01f6829f-1602-4b12-8598-5f0b7ce9d23a', 'normal', '');
INSERT INTO `bk_payment` VALUES ('26658890-cf70-11e7-a254-ed8dfb4257ee', 'alipay', '1', '2017-11-22 18:28:35', '01f6829f-1602-4b12-8598-5f0b7ce9d23a', 'normal', '');
INSERT INTO `bk_payment` VALUES ('26690d35-cf70-11e7-a254-ed8dfb4257ee', 'chanpay', '0', '2017-11-22 18:28:35', '01f6829f-1602-4b12-8598-5f0b7ce9d23a', 'normal', '');
INSERT INTO `bk_payment` VALUES ('266cc347-cf70-11e7-a254-ed8dfb4257ee', 'fuqianla', '0', '2017-11-22 18:28:35', '01f6829f-1602-4b12-8598-5f0b7ce9d23a', 'normal', '');

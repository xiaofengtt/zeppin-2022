/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.120_3306
Source Server Version : 50520
Source Host           : 192.168.1.120:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-11-27 15:40:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `auto_ali_transfer_process`
-- ----------------------------
DROP TABLE IF EXISTS `auto_ali_transfer_process`;
CREATE TABLE `auto_ali_transfer_process` (
  `uuid` varchar(36) NOT NULL,
  `process_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '处理完成时间',
  `process_count` int(11) NOT NULL COMMENT '处理记录条目数',
  `status` varchar(10) NOT NULL COMMENT '处理状态 normal disable',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `process_index` int(11) NOT NULL DEFAULT '0' COMMENT '执行顺序',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auto_ali_transfer_process
-- ----------------------------
INSERT INTO `auto_ali_transfer_process` VALUES ('265cf93b-cf70-11e7-a254-ed8dfb42b7ee', '2017-11-23 00:00:57', '1', 'normal', '2017-11-23 12:15:07', '0');

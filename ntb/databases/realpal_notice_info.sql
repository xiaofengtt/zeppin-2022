/*
Navicat MySQL Data Transfer

Source Server         : xjjspxgl.zeppin.cn
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2018-07-18 18:29:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `realpal_notice_info`
-- ----------------------------
DROP TABLE IF EXISTS `realpal_notice_info`;
CREATE TABLE `realpal_notice_info` (
  `uuid` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` varchar(20) NOT NULL COMMENT '状态 unpro未处理 success处理成功 fail处理失败',
  `source` varchar(1000) NOT NULL COMMENT '结果集data数据',
  `order_num` varchar(32) DEFAULT '' COMMENT '订单号',
  `batch_no` varchar(50) DEFAULT '' COMMENT '批次号-目前与订单号相同',
  `pay_type` varchar(20) NOT NULL COMMENT '交易类型 recharge or withdraw',
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50646
Source Host           : localhost:3306
Source Database       : crebas6

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2020-05-25 18:43:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `activity_info`
-- ----------------------------
DROP TABLE IF EXISTS `activity_info`;
CREATE TABLE `activity_info` (
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '活动名称freebuy/checkin/scorelottery',
  `title` varchar(20) NOT NULL COMMENT '活动标题',
  `status` varchar(20) NOT NULL COMMENT '活动状态normal/disable',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `creator` varchar(36) NOT NULL,
  `configuration` text COMMENT '活动配置（JSON格式）',
  `starttime` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '起始时间',
  `endtime` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '截至时间',
  `banner_url` varchar(100) DEFAULT NULL COMMENT '宣传图链接',
  `link_url` varchar(100) DEFAULT NULL COMMENT '活动地址链接',
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity_info
-- ----------------------------
INSERT INTO `activity_info` VALUES ('buyfree', '0元购', 'disable', '2020-05-25 10:33:07', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '{\"timesline\":0}', '0000-00-00 00:00:00', '0000-00-00 00:00:00', null, null, '0');
INSERT INTO `activity_info` VALUES ('checkin', '签到活动', 'disable', '2020-05-25 10:34:34', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', null, '0000-00-00 00:00:00', '0000-00-00 00:00:00', null, null, '1');
INSERT INTO `activity_info` VALUES ('scorelottery', '积分转盘摇奖', 'disable', '2020-05-25 10:35:16', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '{\"timesline\":0,\"preBetScore\":0}', '0000-00-00 00:00:00', '0000-00-00 00:00:00', null, null, '2');

-- ----------------------------
-- Table structure for `activity_info_buyfree`
-- ----------------------------
DROP TABLE IF EXISTS `activity_info_buyfree`;
CREATE TABLE `activity_info_buyfree` (
  `uuid` varchar(36) NOT NULL,
  `activity_info` varchar(20) NOT NULL,
  `activity_info_buyfree_goods` varchar(36) NOT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `goods_id` varchar(36) NOT NULL,
  `goods_cover` varchar(36) NOT NULL,
  `goods_price` decimal(20,2) NOT NULL,
  `issue_num` int(11) NOT NULL,
  `goods_title` varchar(200) NOT NULL,
  `goods_short_title` varchar(100) NOT NULL,
  `shares` int(11) NOT NULL COMMENT '总人次',
  `bet_shares` int(11) NOT NULL COMMENT '已参与人次',
  `remain_shares` int(11) NOT NULL COMMENT '剩余人次',
  `sort` int(11) NOT NULL DEFAULT '0',
  `lotterytime` timestamp NULL DEFAULT NULL COMMENT '截至时间',
  `finishedtime` timestamp NULL DEFAULT NULL COMMENT '开奖时间',
  `lucky_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_activity_info_buyfree_01` (`activity_info`),
  KEY `FK_activity_info_buyfree_02` (`goods_id`),
  KEY `INDEX_activity_info_buyfree_01` (`goods_title`,`goods_short_title`) USING BTREE,
  KEY `FK_activity_info_buyfree_03` (`activity_info_buyfree_goods`),
  CONSTRAINT `FK_activity_info_buyfree_01` FOREIGN KEY (`activity_info`) REFERENCES `activity_info` (`name`),
  CONSTRAINT `FK_activity_info_buyfree_02` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`uuid`),
  CONSTRAINT `FK_activity_info_buyfree_03` FOREIGN KEY (`activity_info_buyfree_goods`) REFERENCES `activity_info_buyfree_goods` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity_info_buyfree
-- ----------------------------

-- ----------------------------
-- Table structure for `activity_info_buyfree_goods`
-- ----------------------------
DROP TABLE IF EXISTS `activity_info_buyfree_goods`;
CREATE TABLE `activity_info_buyfree_goods` (
  `uuid` varchar(36) NOT NULL,
  `activity_info` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `creator` varchar(36) NOT NULL,
  `goods_id` varchar(36) NOT NULL,
  `goods_cover` varchar(36) NOT NULL,
  `goods_price` decimal(20,2) NOT NULL,
  `goods_title` varchar(200) NOT NULL,
  `goods_short_title` varchar(100) NOT NULL,
  `shares` int(11) NOT NULL COMMENT '总人次',
  `current_issue_num` int(11) NOT NULL,
  `sort` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uuid`),
  KEY `FK_activity_info_buyfree_01` (`activity_info`),
  KEY `FK_activity_info_buyfree_02` (`goods_id`),
  KEY `INDEX_activity_info_buyfree_01` (`goods_title`,`goods_short_title`) USING BTREE,
  CONSTRAINT `activity_info_buyfree_goods_ibfk_1` FOREIGN KEY (`activity_info`) REFERENCES `activity_info` (`name`),
  CONSTRAINT `activity_info_buyfree_goods_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity_info_buyfree_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `activity_info_buyfree_sharesnum`
-- ----------------------------
DROP TABLE IF EXISTS `activity_info_buyfree_sharesnum`;
CREATE TABLE `activity_info_buyfree_sharesnum` (
  `activity_info_buyfree` varchar(36) NOT NULL,
  `sharenums` longtext NOT NULL,
  PRIMARY KEY (`activity_info_buyfree`),
  CONSTRAINT `FK_activity_info_buyfree_sharenum_01` FOREIGN KEY (`activity_info_buyfree`) REFERENCES `activity_info_buyfree` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品每份额的幸运号码';

-- ----------------------------
-- Records of activity_info_buyfree_sharesnum
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_buyfree_order`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_buyfree_order`;
CREATE TABLE `front_user_buyfree_order` (
  `uuid` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `front_user_show_id` int(11) NOT NULL,
  `activity_info_buyfree` varchar(36) NOT NULL,
  `goods_id` varchar(36) NOT NULL,
  `sharenum` int(11) NOT NULL COMMENT '抽奖号码',
  `is_lucky` tinyint(1) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT '状态normal/nowin/win/finished',
  `createtime` timestamp(3) NOT NULL DEFAULT '0000-00-00 00:00:00.000',
  `remark` varchar(100) DEFAULT NULL,
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NULL DEFAULT NULL,
  `winning_time` timestamp NULL DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL,
  `provide_info` varchar(500) DEFAULT NULL COMMENT '派奖信息JSON结构，包括时间、发货单号、快递公司等信息',
  PRIMARY KEY (`uuid`),
  KEY `FK_front_user_buyfree_order_01` (`front_user`),
  KEY `FK_front_user_buyfree_order_02` (`activity_info_buyfree`),
  KEY `FK_front_user_buyfree_03` (`goods_id`),
  CONSTRAINT `FK_front_user_buyfree_03` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`uuid`),
  CONSTRAINT `FK_front_user_buyfree_order_01` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`),
  CONSTRAINT `FK_front_user_buyfree_order_02` FOREIGN KEY (`activity_info_buyfree`) REFERENCES `activity_info_buyfree` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of front_user_buyfree_order
-- ----------------------------

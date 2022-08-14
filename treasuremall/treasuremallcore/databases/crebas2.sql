/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : crebas2

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2020-02-04 21:09:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `uuid` varchar(36) NOT NULL,
  `username` varchar(100) NOT NULL,
  `realname` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `role` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`username`),
  KEY `FK_Reference_4` (`role`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='backadmin';

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for `auction_game_goods`
-- ----------------------------
DROP TABLE IF EXISTS `auction_game_goods`;
CREATE TABLE `auction_game_goods` (
  `uuid` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auction_game_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `backadmin_offset_order`
-- ----------------------------
DROP TABLE IF EXISTS `backadmin_offset_order`;
CREATE TABLE `backadmin_offset_order` (
  `uuid` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `order_num` bigint(20) NOT NULL,
  `order_type` varchar(36) DEFAULT NULL COMMENT '为扩展不同游戏进入的充值订单',
  `type` int(11) DEFAULT NULL COMMENT '加币/减币',
  `d_amount` decimal(20,2) NOT NULL COMMENT '账户增加金额（代币）。根据充值活动，计算出充值订单增加的代币',
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(20) NOT NULL COMMENT '待处理/初审/已完成/取消/关闭',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `reason` varchar(100) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`order_num`),
  KEY `FK_Reference_19` (`operator`),
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`operator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手动补偿/扣减订单';

-- ----------------------------
-- Records of backadmin_offset_order
-- ----------------------------

-- ----------------------------
-- Table structure for `bank`
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `logo` varchar(36) NOT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `color` varchar(10) DEFAULT NULL,
  `icon` varchar(36) DEFAULT NULL,
  `short_name` varchar(200) NOT NULL,
  `icon_color` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank
-- ----------------------------

-- ----------------------------
-- Table structure for `banner`
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT '启动页广告/首页顶部广告/弹屏广告/登录页广告/支付页面广告等',
  `title` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `image` varchar(36) NOT NULL,
  `url` varchar(100) NOT NULL,
  `status` varchar(10) NOT NULL,
  `endtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sort` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `front_user_level` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banner
-- ----------------------------

-- ----------------------------
-- Table structure for `capital_account`
-- ----------------------------
DROP TABLE IF EXISTS `capital_account`;
CREATE TABLE `capital_account` (
  `uuid` varchar(36) NOT NULL,
  `capital_platform` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `account_num` varchar(50) NOT NULL,
  `poundage_rate` decimal(10,4) DEFAULT NULL,
  `data` text,
  `min` decimal(20,2) NOT NULL,
  `max` decimal(20,2) NOT NULL,
  `daily_max` decimal(20,2) NOT NULL,
  `total_max` decimal(20,2) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `trans_type` varchar(20) NOT NULL,
  `status` varchar(10) NOT NULL,
  `sort` int(11) DEFAULT NULL,
  `creator` varchar(36) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`account_num`),
  UNIQUE KEY `AK_Key_3` (`name`),
  KEY `FK_Reference_44` (`capital_platform`),
  CONSTRAINT `FK_Reference_44` FOREIGN KEY (`capital_platform`) REFERENCES `capital_platform` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of capital_account
-- ----------------------------

-- ----------------------------
-- Table structure for `capital_account_history`
-- ----------------------------
DROP TABLE IF EXISTS `capital_account_history`;
CREATE TABLE `capital_account_history` (
  `uuid` varchar(36) NOT NULL,
  `capital_platform` varchar(36) NOT NULL,
  `capital_account` varchar(36) NOT NULL,
  `serial_num` bigint(20) NOT NULL,
  `type` varchar(20) DEFAULT NULL COMMENT '加币/减币',
  `order_id` varchar(36) DEFAULT NULL,
  `order_type` varchar(20) NOT NULL,
  `order_num` varchar(36) DEFAULT NULL,
  `balance_before` decimal(20,2) NOT NULL,
  `amount` decimal(20,2) NOT NULL,
  `balance_after` decimal(20,2) NOT NULL,
  `reason` varchar(100) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_46` (`capital_account`),
  KEY `FK_Reference_47` (`capital_platform`),
  CONSTRAINT `FK_Reference_47` FOREIGN KEY (`capital_platform`) REFERENCES `capital_platform` (`uuid`),
  CONSTRAINT `FK_Reference_46` FOREIGN KEY (`capital_account`) REFERENCES `capital_account` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值账户流水表';

-- ----------------------------
-- Records of capital_account_history
-- ----------------------------

-- ----------------------------
-- Table structure for `capital_account_statistics`
-- ----------------------------
DROP TABLE IF EXISTS `capital_account_statistics`;
CREATE TABLE `capital_account_statistics` (
  `capital_account` varchar(36) NOT NULL,
  `balance` decimal(20,2) NOT NULL DEFAULT '0.00',
  `daily_sum` decimal(20,2) NOT NULL DEFAULT '0.00',
  `total_recharge` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总充值额（法币）',
  `total_withdraw` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总提现转账额',
  `total_payment` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总对外付款金额',
  `recharge_times` int(11) NOT NULL DEFAULT '0',
  `withdraw_times` int(11) NOT NULL DEFAULT '0',
  `payment_times` int(11) NOT NULL DEFAULT '0' COMMENT '对外付款次数',
  PRIMARY KEY (`capital_account`),
  CONSTRAINT `FK_Reference_45` FOREIGN KEY (`capital_account`) REFERENCES `capital_account` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of capital_account_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for `capital_platform`
-- ----------------------------
DROP TABLE IF EXISTS `capital_platform`;
CREATE TABLE `capital_platform` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `trans_type` varchar(30) NOT NULL COMMENT '支付宝/微信/银行卡/云闪付 根据此字段区分技术调用的接口方式',
  `type` varchar(30) NOT NULL COMMENT '充值分类,如个人银行卡收款,企业银行卡收款,支付宝扫码付,支付宝api等',
  `sort` int(11) NOT NULL COMMENT '前端显示的顺序',
  `is_recommend` tinyint(1) NOT NULL DEFAULT '1',
  `is_unique_amount` tinyint(1) NOT NULL DEFAULT '0',
  `is_random_amount` tinyint(1) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL COMMENT '开启/关闭/测试',
  `logo` varchar(36) NOT NULL,
  `explanation` varchar(1000) NOT NULL,
  `explan_img` varchar(36) NOT NULL,
  `remark` varchar(100) NOT NULL,
  `max` decimal(20,2) NOT NULL COMMENT '最大单笔充值金额',
  `min` decimal(20,2) NOT NULL COMMENT '最小单笔充值金额',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`name`),
  KEY `FK_Reference_39` (`logo`),
  KEY `FK_Reference_40` (`explan_img`),
  CONSTRAINT `FK_Reference_40` FOREIGN KEY (`explan_img`) REFERENCES `resource` (`uuid`),
  CONSTRAINT `FK_Reference_39` FOREIGN KEY (`logo`) REFERENCES `resource` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of capital_platform
-- ----------------------------

-- ----------------------------
-- Table structure for `common_payment_amount`
-- ----------------------------
DROP TABLE IF EXISTS `common_payment_amount`;
CREATE TABLE `common_payment_amount` (
  `uuid` varchar(36) NOT NULL,
  `sort` int(11) NOT NULL,
  `amount` decimal(20,2) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of common_payment_amount
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user`
-- ----------------------------
DROP TABLE IF EXISTS `front_user`;
CREATE TABLE `front_user` (
  `uuid` varchar(36) NOT NULL,
  `show_id` int(11) NOT NULL COMMENT '用户外显ID',
  `realname` varchar(100) DEFAULT NULL,
  `idcard` varchar(20) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(300) DEFAULT NULL,
  `realnameflag` tinyint(1) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `type` varchar(20) NOT NULL DEFAULT 'normal' COMMENT '用户类型normal/robot',
  `level` varchar(20) NOT NULL DEFAULT 'tourists' COMMENT '用户分级游客/注册/充值/高级 tourists/registered/recharged/VIP',
  `status` varchar(10) DEFAULT NULL,
  `register_channel` varchar(36) DEFAULT NULL,
  `openid` varchar(36) DEFAULT NULL,
  `wechaticon` varchar(200) DEFAULT NULL,
  `image` varchar(36) DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL,
  `area` varchar(30) DEFAULT NULL,
  `lastaccessip` varchar(30) DEFAULT NULL,
  `lastonline` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `agent` varchar(36) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_3` (`mobile`),
  UNIQUE KEY `AK_Key_5` (`show_id`),
  UNIQUE KEY `Index_2` (`mobile`),
  UNIQUE KEY `AK_Key_2` (`idcard`),
  UNIQUE KEY `AK_Key_4` (`email`),
  UNIQUE KEY `Index_3` (`email`),
  UNIQUE KEY `Index_1` (`idcard`),
  KEY `FK_Reference_42` (`image`),
  CONSTRAINT `FK_Reference_42` FOREIGN KEY (`image`) REFERENCES `resource` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of front_user
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_account`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_account`;
CREATE TABLE `front_user_account` (
  `front_user` varchar(36) NOT NULL,
  `balance` decimal(20,2) NOT NULL DEFAULT '0.00',
  `balance_lock` decimal(20,2) NOT NULL DEFAULT '0.00',
  `voucher_balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '抵扣券红包',
  `account_status` varchar(10) NOT NULL COMMENT '账户冻结等状态',
  `total_recharge` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总充值额（法币）',
  `total_withdraw` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总提现额（法币）',
  `total_payment` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总付款金额（代币）',
  `total_winning` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总中奖金币（代币）',
  `total_delivery` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总兑奖奖品总价值（法币）',
  `total_exchange` decimal(20,2) NOT NULL DEFAULT '0.00',
  `payment_times` int(11) NOT NULL DEFAULT '0' COMMENT '投注次数',
  `winning_times` int(11) NOT NULL DEFAULT '0',
  `recharge_times` int(11) NOT NULL DEFAULT '0',
  `withdraw_times` int(11) NOT NULL DEFAULT '0',
  `delivery_times` int(11) NOT NULL DEFAULT '0',
  `exchange_times` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`front_user`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户代币账户';

-- ----------------------------
-- Records of front_user_account
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_address`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_address`;
CREATE TABLE `front_user_address` (
  `uuid` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `receiver` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_default` tinyint(1) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_21` (`front_user`),
  CONSTRAINT `FK_Reference_21` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of front_user_address
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_bankcard`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_bankcard`;
CREATE TABLE `front_user_bankcard` (
  `uuid` varchar(36) NOT NULL,
  `bank` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL,
  `branch_bank` varchar(100) DEFAULT NULL,
  `account_holder` varchar(50) DEFAULT NULL,
  `account_number` varchar(64) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`account_number`),
  UNIQUE KEY `Index_1` (`bank`,`front_user`,`account_number`),
  KEY `FK_Reference_10` (`front_user`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`bank`) REFERENCES `bank` (`uuid`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of front_user_bankcard
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_history`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_history`;
CREATE TABLE `front_user_history` (
  `uuid` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `serial_num` bigint(20) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT '加币/减币',
  `order_type` varchar(20) NOT NULL COMMENT '用户充值/用户提现/手动补偿/手动扣减/系统赠送/用户支付',
  `order_num` varchar(36) DEFAULT NULL,
  `balance_before` decimal(20,2) NOT NULL,
  `d_amount` decimal(20,2) NOT NULL,
  `balance_after` decimal(20,2) NOT NULL,
  `reason` varchar(100) NOT NULL COMMENT '各用户显示的账变原因：用户充值/用户提现/手动处理加币/手动处理减币/系统自动加币/系统自动减币/活动赠送等',
  `order_id` varchar(36) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_16` (`front_user`),
  KEY `FK_Reference_33` (`order_id`),
  CONSTRAINT `FK_Reference_33` FOREIGN KEY (`order_id`) REFERENCES `front_user_payment_order` (`uuid`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`),
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`front_user`) REFERENCES `front_user_account` (`front_user`),
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`order_id`) REFERENCES `front_user_recharge_order` (`uuid`),
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`order_id`) REFERENCES `front_user_withdraw_order` (`uuid`),
  CONSTRAINT `FK_Reference_20` FOREIGN KEY (`order_id`) REFERENCES `backadmin_offset_order` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户账变,充值提现投注手动系统自动都会引起账变';

-- ----------------------------
-- Records of front_user_history
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_paid_number`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_paid_number`;
CREATE TABLE `front_user_paid_number` (
  `order_id` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `issue_goods` varchar(36) NOT NULL,
  `paid_sharenums` text NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `AK_Key_2` (`front_user`),
  UNIQUE KEY `AK_Key_3` (`order_id`),
  KEY `FK_Reference_36` (`issue_goods`),
  CONSTRAINT `FK_Reference_37` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`),
  CONSTRAINT `FK_Reference_34` FOREIGN KEY (`order_id`) REFERENCES `front_user_payment_order` (`uuid`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`issue_goods`) REFERENCES `luckygame_goods_issue` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of front_user_paid_number
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_payment_order`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_payment_order`;
CREATE TABLE `front_user_payment_order` (
  `uuid` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `goods_issue` varchar(36) NOT NULL,
  `game_type` varchar(20) NOT NULL,
  `order_num` int(11) DEFAULT NULL,
  `order_type` varchar(36) DEFAULT NULL,
  `d_amount` decimal(20,2) NOT NULL COMMENT '支付金额（代币）',
  `is_voucher_used` tinyint(1) NOT NULL DEFAULT '0',
  `voucher` varchar(36) DEFAULT NULL COMMENT '使用的代金券',
  `actual_d_amount` decimal(20,2) NOT NULL COMMENT '实际是支付的金额(代币),扣除代金券及活动优惠后',
  `buy_count` int(11) DEFAULT NULL COMMENT '购买了多少份额',
  `poundage` decimal(20,2) NOT NULL DEFAULT '0.00',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(20) NOT NULL COMMENT '正常/失败',
  `remark` varchar(500) DEFAULT NULL,
  `is_promotion` tinyint(1) DEFAULT NULL,
  `promotion_id` varchar(36) DEFAULT NULL,
  `is_lucky` tinyint(1) DEFAULT NULL,
  `lucky_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`order_num`),
  KEY `FK_Reference_31` (`front_user`),
  KEY `FK_Reference_32` (`goods_issue`),
  KEY `FK_Reference_51` (`voucher`),
  CONSTRAINT `FK_Reference_51` FOREIGN KEY (`voucher`) REFERENCES `front_user_voucher` (`uuid`),
  CONSTRAINT `FK_Reference_31` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`),
  CONSTRAINT `FK_Reference_32` FOREIGN KEY (`goods_issue`) REFERENCES `luckygame_goods_issue` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户投注表';

-- ----------------------------
-- Records of front_user_payment_order
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_recharge_order`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_recharge_order`;
CREATE TABLE `front_user_recharge_order` (
  `uuid` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `order_num` bigint(20) NOT NULL,
  `order_type` varchar(36) DEFAULT NULL COMMENT '为扩展不同游戏进入的充值订单',
  `order_channel` varchar(36) DEFAULT NULL,
  `amount` decimal(20,2) NOT NULL COMMENT '充值金额',
  `increase_d_amount` decimal(20,2) NOT NULL COMMENT '账户增加金额（代币）。根据充值活动，计算出充值订单增加的代币',
  `type` varchar(20) NOT NULL,
  `capital_account` varchar(36) NOT NULL,
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(20) NOT NULL COMMENT '待处理/初审/已完成/取消/关闭',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `trans_data` text,
  `remark` varchar(200) DEFAULT NULL,
  `is_firsttime` tinyint(1) NOT NULL COMMENT '是否首次充值',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`order_num`),
  KEY `FK_Reference_12` (`front_user`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值单amount是法币';

-- ----------------------------
-- Records of front_user_recharge_order
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_voucher`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_voucher`;
CREATE TABLE `front_user_voucher` (
  `uuid` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `voucher` varchar(36) NOT NULL,
  `title` varchar(50) NOT NULL,
  `discription` varchar(200) DEFAULT NULL,
  `d_amount` decimal(20,2) NOT NULL,
  `pay_min` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '购买商品额度使用限制须高于该值，0表示不限额',
  `goods_type` varchar(150) DEFAULT NULL COMMENT '代金券使用时商品类型限制，为空表示不限制商品类别，最多4类',
  `goods` varchar(400) DEFAULT NULL COMMENT '代金券使用时购买商品限制，为空表示不限制商品。最多10个商品',
  `starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间格式: yyyy-MM-dd HH:mm:ss  或 +B，B表示领取时间后多少天开始，不填代表立即生效',
  `endtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '时间格式: yyyy-MM-dd HH:mm:ss  或 +E，E表示领取时间后多少天结束，不填永不失效',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `creator` varchar(36) DEFAULT NULL,
  `status` varchar(10) NOT NULL COMMENT '未使用/已使用/已失效',
  `operattime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_49` (`voucher`),
  KEY `FK_Reference_50` (`front_user`),
  CONSTRAINT `FK_Reference_49` FOREIGN KEY (`voucher`) REFERENCES `voucher` (`uuid`),
  CONSTRAINT `FK_Reference_50` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of front_user_voucher
-- ----------------------------

-- ----------------------------
-- Table structure for `front_user_withdraw_order`
-- ----------------------------
DROP TABLE IF EXISTS `front_user_withdraw_order`;
CREATE TABLE `front_user_withdraw_order` (
  `uuid` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `order_num` bigint(20) NOT NULL,
  `order_type` varchar(36) DEFAULT NULL,
  `reduce_d_amount` decimal(20,2) NOT NULL,
  `amount` decimal(20,2) NOT NULL COMMENT '提现金额(法币)，扣除手续费前',
  `poundage` decimal(20,2) NOT NULL DEFAULT '0.00',
  `actual_amount` decimal(20,2) NOT NULL COMMENT '提现实际金额(法币),扣除手续费后',
  `type` varchar(20) NOT NULL COMMENT '用户提现/手动扣除',
  `capital_account` varchar(36) DEFAULT NULL,
  `front_user_bankcard` varchar(36) DEFAULT NULL,
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(20) NOT NULL COMMENT '待处理/初审/完成/取消',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `trans_data` text,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`order_num`),
  KEY `FK_Reference_13` (`front_user`),
  KEY `FK_Reference_14` (`front_user_bankcard`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`front_user_bankcard`) REFERENCES `front_user_bankcard` (`uuid`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提款单amount是法币';

-- ----------------------------
-- Records of front_user_withdraw_order
-- ----------------------------

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `shortname` varchar(100) NOT NULL,
  `type` varchar(20) NOT NULL,
  `code` varchar(30) NOT NULL,
  `source` varchar(50) DEFAULT NULL,
  `source_url` varchar(200) DEFAULT NULL,
  `price` decimal(20,2) NOT NULL,
  `costs` decimal(20,2) NOT NULL,
  `description` varchar(500) NOT NULL,
  `video` varchar(36) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `AK_Key_2` (`code`),
  KEY `FK_Reference_43` (`video`),
  CONSTRAINT `FK_Reference_43` FOREIGN KEY (`video`) REFERENCES `resource` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------

-- ----------------------------
-- Table structure for `goods_cover_image`
-- ----------------------------
DROP TABLE IF EXISTS `goods_cover_image`;
CREATE TABLE `goods_cover_image` (
  `uuid` varchar(36) NOT NULL,
  `belongs` varchar(36) NOT NULL COMMENT '属于哪个商品表的',
  `type` varchar(20) NOT NULL COMMENT '列表图/详情图/展示图',
  `sort` int(11) NOT NULL,
  `image` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_30` (`belongs`),
  KEY `FK_Reference_41` (`image`),
  CONSTRAINT `FK_Reference_41` FOREIGN KEY (`image`) REFERENCES `resource` (`uuid`),
  CONSTRAINT `FK_Reference_23` FOREIGN KEY (`belongs`) REFERENCES `goods` (`uuid`),
  CONSTRAINT `FK_Reference_24` FOREIGN KEY (`belongs`) REFERENCES `luckygame_goods` (`uuid`),
  CONSTRAINT `FK_Reference_30` FOREIGN KEY (`belongs`) REFERENCES `luckygame_goods_issue` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_cover_image
-- ----------------------------

-- ----------------------------
-- Table structure for `goods_issue_sharesnum`
-- ----------------------------
DROP TABLE IF EXISTS `goods_issue_sharesnum`;
CREATE TABLE `goods_issue_sharesnum` (
  `goods_issue` varchar(36) NOT NULL,
  `sharenums` text NOT NULL,
  PRIMARY KEY (`goods_issue`),
  CONSTRAINT `FK_Reference_28` FOREIGN KEY (`goods_issue`) REFERENCES `luckygame_goods_issue` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品每份额的幸运号码';

-- ----------------------------
-- Records of goods_issue_sharesnum
-- ----------------------------

-- ----------------------------
-- Table structure for `goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type` (
  `code` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `icon` varchar(36) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `parent` varchar(20) DEFAULT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `FK_Reference_22` (`parent`),
  CONSTRAINT `FK_Reference_22` FOREIGN KEY (`parent`) REFERENCES `goods_type` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_type
-- ----------------------------

-- ----------------------------
-- Table structure for `luckygame_goods`
-- ----------------------------
DROP TABLE IF EXISTS `luckygame_goods`;
CREATE TABLE `luckygame_goods` (
  `uuid` varchar(36) NOT NULL,
  `goods_id` varchar(36) NOT NULL,
  `game_type` varchar(20) NOT NULL COMMENT '传统玩法/1v1PK玩法/两群PK玩法/三群pk玩法',
  `title` varchar(200) NOT NULL,
  `short_title` varchar(100) NOT NULL,
  `d_price` decimal(20,2) NOT NULL COMMENT '商品代币价值，代币和法币比例换算的结果',
  `profit_rate` decimal(6,4) NOT NULL COMMENT '利润率',
  `bet_per_share` decimal(20,2) NOT NULL COMMENT '单份额投注金额',
  `shares` int(11) NOT NULL COMMENT '商品分成的份额',
  `total_issue_num` int(11) NOT NULL COMMENT '总共设置多少期',
  `current_issue_num` int(11) DEFAULT NULL COMMENT '当前进行到的期号',
  `status` varchar(20) NOT NULL COMMENT '上架/下架/删除',
  `sort` int(11) NOT NULL COMMENT '排序',
  `promotion_flag` tinyint(1) DEFAULT NULL,
  `creator` varchar(36) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_25` (`goods_id`),
  CONSTRAINT `FK_Reference_25` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of luckygame_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `luckygame_goods_issue`
-- ----------------------------
DROP TABLE IF EXISTS `luckygame_goods_issue`;
CREATE TABLE `luckygame_goods_issue` (
  `uuid` varchar(36) NOT NULL,
  `luckygame_goods` varchar(36) NOT NULL,
  `goods_id` varchar(36) NOT NULL,
  `game_type` varchar(20) NOT NULL COMMENT '传统玩法/1v1PK玩法/两群PK玩法/三群pk玩法',
  `title` varchar(200) NOT NULL,
  `short_title` varchar(100) NOT NULL,
  `d_price` decimal(20,2) NOT NULL,
  `profit_rate` decimal(6,4) NOT NULL,
  `bet_per_share` decimal(20,2) NOT NULL,
  `shares` int(11) NOT NULL,
  `issue_num` int(11) NOT NULL COMMENT '当前的期数',
  `sort` int(11) NOT NULL,
  `promotion_flag` tinyint(1) DEFAULT NULL COMMENT '促销标志',
  `bet_shares` int(11) NOT NULL COMMENT '已经投注的份额',
  `remain_shares` int(11) NOT NULL,
  `status` varchar(20) NOT NULL COMMENT '投注中/投注完成(开奖中)/已揭晓结束 betting/lottery/finished',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间也是创建时间',
  `lotterytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `finishedtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lucky_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `Index_1` (`issue_num`),
  KEY `FK_Reference_26` (`luckygame_goods`),
  KEY `FK_Reference_27` (`goods_id`),
  CONSTRAINT `FK_Reference_26` FOREIGN KEY (`luckygame_goods`) REFERENCES `luckygame_goods` (`uuid`),
  CONSTRAINT `FK_Reference_27` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每一期的商品执行信息';

-- ----------------------------
-- Records of luckygame_goods_issue
-- ----------------------------

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  `parent` varchar(36) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_1` (`parent`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`parent`) REFERENCES `menu` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='caidan';

-- ----------------------------
-- Records of menu
-- ----------------------------

-- ----------------------------
-- Table structure for `method`
-- ----------------------------
DROP TABLE IF EXISTS `method`;
CREATE TABLE `method` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(300) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `level` int(11) NOT NULL,
  `parent` varchar(36) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_5` (`parent`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`parent`) REFERENCES `method` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='API方法名';

-- ----------------------------
-- Records of method
-- ----------------------------

-- ----------------------------
-- Table structure for `mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `mobile_code`;
CREATE TABLE `mobile_code` (
  `uuid` varchar(36) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `content` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creatortype` varchar(10) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mobile_code
-- ----------------------------

-- ----------------------------
-- Table structure for `pay_notify_info`
-- ----------------------------
DROP TABLE IF EXISTS `pay_notify_info`;
CREATE TABLE `pay_notify_info` (
  `uuid` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(20) NOT NULL,
  `source` varchar(1000) NOT NULL,
  `order_num` varchar(36) NOT NULL,
  `pay_type` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pay_notify_info
-- ----------------------------

-- ----------------------------
-- Table structure for `platform_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `platform_orderinfo`;
CREATE TABLE `platform_orderinfo` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL,
  `order_num` varchar(36) NOT NULL,
  `pay_num` varchar(36) NOT NULL,
  `prepay_id` varchar(64) NOT NULL,
  `body` varchar(100) DEFAULT NULL,
  `fee` decimal(20,8) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(20) DEFAULT NULL,
  `message` varchar(128) DEFAULT NULL,
  `error_des` varchar(64) DEFAULT NULL,
  `error_code` varchar(64) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of platform_orderinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(10) NOT NULL,
  `filename` varchar(100) NOT NULL,
  `url` varchar(200) NOT NULL,
  `filetype` varchar(20) DEFAULT NULL,
  `size` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------

-- ----------------------------
-- Table structure for `robot_setting`
-- ----------------------------
DROP TABLE IF EXISTS `robot_setting`;
CREATE TABLE `robot_setting` (
  `robot_id` varchar(36) NOT NULL,
  `game_type` varchar(20) NOT NULL COMMENT '机器人的类型，不同玩法用不同游戏类型的机器人',
  `min_pay` decimal(20,2) NOT NULL,
  `max_pay` decimal(20,2) NOT NULL COMMENT '购买商品份额的最大值',
  `period_min` int(11) NOT NULL COMMENT '自动投注最小间隔周期单位秒',
  `period_random` int(11) NOT NULL COMMENT '投注周期的正向随机区间最大值',
  `worktime_begin` time NOT NULL,
  `worktime_end` time NOT NULL COMMENT '结束时间小于开始时间，结束时间为第二天',
  `goods_type` varchar(200) DEFAULT NULL,
  `goods_price_min` decimal(20,2) NOT NULL DEFAULT '0.00',
  `goods_price_max` decimal(20,2) NOT NULL DEFAULT '9999999.00',
  `status` varchar(20) NOT NULL COMMENT '启动/停止',
  PRIMARY KEY (`robot_id`),
  CONSTRAINT `FK_Reference_48` FOREIGN KEY (`robot_id`) REFERENCES `front_user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of robot_setting
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台角色';

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `menu` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `Index_1` (`role`,`menu`),
  KEY `FK_Reference_2` (`menu`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`menu`) REFERENCES `menu` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `role_method`
-- ----------------------------
DROP TABLE IF EXISTS `role_method`;
CREATE TABLE `role_method` (
  `uuid` varchar(36) NOT NULL,
  `method` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `Index_1` (`method`,`role`),
  KEY `FK_Reference_7` (`role`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`method`) REFERENCES `method` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_method
-- ----------------------------

-- ----------------------------
-- Table structure for `system_param`
-- ----------------------------
DROP TABLE IF EXISTS `system_param`;
CREATE TABLE `system_param` (
  `param_key` varchar(50) NOT NULL,
  `param_value` varchar(500) NOT NULL,
  `description` varchar(200) NOT NULL,
  `partitional` varchar(20) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `creator` varchar(36) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`param_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_param
-- ----------------------------

-- ----------------------------
-- Table structure for `voucher`
-- ----------------------------
DROP TABLE IF EXISTS `voucher`;
CREATE TABLE `voucher` (
  `uuid` varchar(36) NOT NULL,
  `title` varchar(50) NOT NULL,
  `discription` varchar(200) DEFAULT NULL,
  `d_amount` decimal(20,2) NOT NULL,
  `pay_min` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '购买商品额度使用限制须高于该值，0表示不限额',
  `goods_type` varchar(150) DEFAULT NULL COMMENT '代金券使用时商品类型限制，为空表示不限制商品类别，最多4类',
  `goods` varchar(400) DEFAULT NULL COMMENT '代金券使用时购买商品限制，为空表示不限制商品。最多10个商品',
  `starttime` varchar(30) DEFAULT NULL COMMENT '时间格式: yyyy-MM-dd HH:mm:ss  或 +B，B表示领取时间后多少天开始，不填代表立即生效',
  `endtime` varchar(30) DEFAULT NULL COMMENT '时间格式: yyyy-MM-dd HH:mm:ss  或 +E，E表示领取时间后多少天结束，不填永不失效',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voucher
-- ----------------------------

-- ----------------------------
-- Table structure for `winning_info`
-- ----------------------------
DROP TABLE IF EXISTS `winning_info`;
CREATE TABLE `winning_info` (
  `uuid` varchar(36) NOT NULL,
  `goods_issue` varchar(36) NOT NULL,
  `front_user` varchar(36) NOT NULL,
  `game_type` varchar(20) NOT NULL,
  `luckynum` int(11) DEFAULT NULL,
  `payment_d_amount` decimal(20,2) DEFAULT NULL,
  `buy_count` int(11) DEFAULT NULL,
  `deal_price` decimal(20,2) DEFAULT NULL,
  `winning_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_35` (`goods_issue`),
  KEY `FK_Reference_38` (`front_user`),
  CONSTRAINT `FK_Reference_38` FOREIGN KEY (`front_user`) REFERENCES `front_user` (`uuid`),
  CONSTRAINT `FK_Reference_35` FOREIGN KEY (`goods_issue`) REFERENCES `luckygame_goods_issue` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of winning_info
-- ----------------------------

ALTER TABLE `voucher`
ADD COLUMN `status`  varchar(10) NOT NULL AFTER `endtime`;


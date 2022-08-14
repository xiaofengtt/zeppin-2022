/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.120
Source Server Version : 50520
Source Host           : 192.168.1.120:3306
Source Database       : crebas2

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2020-05-28 18:54:08
*/

SET FOREIGN_KEY_CHECKS=0;

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
  `icon` varchar(100) DEFAULT NULL COMMENT '图表URL',
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0ae36f97-90b4-11e8-95fb-acaa14314cbd', '管理员菜单管理', '2', '0ae36f97-90b4-11e8-95fb-ad1f2d312ad2', 'roleMenuList.html', 'icon/icon0103.png', '2');
INSERT INTO `menu` VALUES ('0ae36f97-90b4-11e8-95fb-ad1f2d312aa1', '基础配置', '2', '0ae36f97-90b4-11e8-95fb-ad1f2d312ad2', 'systemParamList.html', 'icon/icon0102.png', '1');
INSERT INTO `menu` VALUES ('0ae36f97-90b4-11e8-95fb-ad1f2d312ad2', '系统设置', '1', null, null, 'icon/icon01.png', '0');
INSERT INTO `menu` VALUES ('0ae36f97-90b4-11e8-95fb-fcaa14314a51', '用户管理', '1', null, null, 'icon/icon04.png', '2');
INSERT INTO `menu` VALUES ('0ae36f97-90b4-11e8-95fb-fcaa14314cbe', '售后管理', '1', null, null, 'icon/icon09.png', '7');
INSERT INTO `menu` VALUES ('0ae36f97-90b4-11e8-95fb-fcaa163bc412', '运营管理', '1', null, null, 'icon/icon07.png', '5');
INSERT INTO `menu` VALUES ('0ae36f97-90b4-11e8-95fb-fccd1431ad2a', '广告位管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa163bc412', 'bannerList.html', 'icon/icon0708.png', '7');
INSERT INTO `menu` VALUES ('0ae36f97-a0be-21e8-95fb-faaa14314cbf', '管理员功能管理', '2', '0ae36f97-90b4-11e8-95fb-ad1f2d312ad2', 'roleControllerList.html', 'icon/icon0104.png', '2');
INSERT INTO `menu` VALUES ('10b52671-d03c-417f-9cd1-d88549b6c077', '抽奖玩法商品配置', '2', '47b230cb-19b4-4a68-a9f8-abc7b18893f4', 'luckyGoodsList.html', 'icon/icon0801.png', '0');
INSERT INTO `menu` VALUES ('16258f40-479f-4289-813d-63e2b5e0aec2', '金币审核', '2', '0ae36f97-90b4-11e8-95fb-fcaa163bc412', 'adminOffsetOrderCheckList.html', 'icon/icon0702.png', '1');
INSERT INTO `menu` VALUES ('2ae36f92-30ba-21e8-a5fb-d1aaf4314cb1', '财务管理', '1', null, null, 'icon/icon06.png', '4');
INSERT INTO `menu` VALUES ('47b230cb-19b4-4a68-a9f8-abc7b18893f4', '抽奖管理', '1', null, null, 'icon/icon08.png', '6');
INSERT INTO `menu` VALUES ('58ae01e4-6c07-4edb-9d5e-3756f5bc3860', '金币管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa163bc412', 'adminOffsetOrderList.html', 'icon/icon0701.png', '0');
INSERT INTO `menu` VALUES ('672230f6-3558-43bb-8aef-2605f096d2e5', '机器人管理', '1', null, null, 'icon/icon05.png', '3');
INSERT INTO `menu` VALUES ('68ae01e4-6c07-4edb-9d5e-3756f5bc3860', '用户金券管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa163bc412', 'userVoucherList.html', 'icon/icon0704.png', '3');
INSERT INTO `menu` VALUES ('6f2d74f5-4577-40ac-98b7-f0e2f4357dbc', '管理员管理', '2', '0ae36f97-90b4-11e8-95fb-ad1f2d312ad2', 'adminList.html', 'icon/icon0101.png', '0');
INSERT INTO `menu` VALUES ('6f89935a-7fc8-11ea-b313-fcaa14314cbe', '基础数据管理', '1', null, null, 'icon/icon02.png', '1');
INSERT INTO `menu` VALUES ('710f0db8-8157-45bb-9136-33528759a537', '金券管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa163bc412', 'voucherList.html', 'icon/icon0703.png', '2');
INSERT INTO `menu` VALUES ('836941ce-d554-4883-969a-2c6484a08493', '商品列表', '2', 'a1d8ef97-90b4-11e8-95fb-fcaa163bc412', 'goodsList.html', 'icon/icon0301.png', '0');
INSERT INTO `menu` VALUES ('87513889-cff7-4946-9fef-bd40a2fd0289', '用户列表', '2', '0ae36f97-90b4-11e8-95fb-fcaa14314a51', 'userList.html', 'icon/icon0401.png', '0');
INSERT INTO `menu` VALUES ('94ab6f97-90b4-11e8-95fb-fccd1431ad2a', '注册渠道管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa14314a51', 'channelList.html', 'icon/icon0402.png', '1');
INSERT INTO `menu` VALUES ('94ab6f97-90b4-11e8-95fb-fccd1431ad2b', '黑名单用户', '2', '0ae36f97-90b4-11e8-95fb-fcaa14314a51', 'blackList.html', 'icon/icon0403.png', '2');
INSERT INTO `menu` VALUES ('9544b1a1-8149-11ea-a34c-7cd30aeb2142', '充值金额管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa163bc412', 'rechargeAmountSetList.html', 'icon/icon0705.png', '4');
INSERT INTO `menu` VALUES ('9fbfcada-7fc8-11ea-b313-fcaa14314cbe', '银行管理', '2', '6f89935a-7fc8-11ea-b313-fcaa14314cbe', 'bankList.html', 'icon/icon0201.png', '0');
INSERT INTO `menu` VALUES ('a1d8ef97-90b4-11e8-95fb-fcaa163bc412', '商品管理', '1', null, null, 'icon/icon03.png', '1');
INSERT INTO `menu` VALUES ('a97a2567-1c55-43a7-a721-f5e084b9bb37', '派奖管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa14314cbe', 'awardOrderList.html', 'icon/icon0901.png', '0');
INSERT INTO `menu` VALUES ('b40eff7f-b4ff-4f89-a829-b04a0015d4fd', '提现审核', '2', '2ae36f92-30ba-21e8-a5fb-d1aaf4314cb1', 'userWithdrawCheckList.html', 'icon/icon0604.png', '3');
INSERT INTO `menu` VALUES ('b5e8cf97-90b4-11e8-95fb-fccd1431ad2a', '机器人列表', '2', '672230f6-3558-43bb-8aef-2605f096d2e5', 'robotList.html', 'icon/icon0501.png', '0');
INSERT INTO `menu` VALUES ('b6b0c281-14fd-4cae-8410-2af81f91c4ae', '投注订单', '2', '47b230cb-19b4-4a68-a9f8-abc7b18893f4', 'betOrderList.html', 'icon/icon0802.png', '1');
INSERT INTO `menu` VALUES ('ba4962c5-672c-11ea-b21f-fcaa14314cbe', '晒单管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa163bc412', 'userCommentList.html', 'icon/icon0706.png', '5');
INSERT INTO `menu` VALUES ('ba4fd924-672c-11ea-b21f-fcaa14314cbe', '公告管理', '2', '0ae36f97-90b4-11e8-95fb-fcaa163bc412', 'noticeList.html', 'icon/icon0707.png', '6');
INSERT INTO `menu` VALUES ('c93d1b60-7a15-4184-9e38-2b2b49970dc8', '支付类型管理', '2', '2ae36f92-30ba-21e8-a5fb-d1aaf4314cb1', 'capitalPlatformList.html', 'icon/icon0601.png', '0');
INSERT INTO `menu` VALUES ('df87e1d0-45b8-4be7-805d-6f676cb7d665', '充提渠道管理', '2', '2ae36f92-30ba-21e8-a5fb-d1aaf4314cb1', 'capitalAccountList.html', 'icon/icon0602.png', '1');
INSERT INTO `menu` VALUES ('e5e3578d-886a-446b-b847-ba850dbff467', '中奖订单', '2', '47b230cb-19b4-4a68-a9f8-abc7b18893f4', 'winOrderList.html', 'icon/icon0803.png', '2');
INSERT INTO `menu` VALUES ('fc7e7bbf-48e6-4484-b453-deee5e20edfc', '充值审核', '2', '2ae36f92-30ba-21e8-a5fb-d1aaf4314cb1', 'userRechargeCheckList.html', 'icon/icon0603.png', '2');

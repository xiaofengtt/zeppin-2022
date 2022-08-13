/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2017-07-07 14:54:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bank`
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `logo` varchar(36) NOT NULL,
  `status` varchar(20) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank
-- ----------------------------
INSERT INTO `bank` VALUES ('0639461f-f09f-4910-b101-c5c23e974470', '弱智银行', 'a80f828d-0a89-4d7a-8770-e37ed0b2e4d5', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-31 17:37:16');
INSERT INTO `bank` VALUES ('231037c9-c83c-4557-833d-b75eecba3652', '脑残银行', '4ab465d4-7878-457a-9705-fd77b3b2cbf9', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-31 17:36:39');
INSERT INTO `bank` VALUES ('630aef77-e154-11e6-bec8-7ce91bcbaaef', '缺德银行', '0239e0b3-d843-495b-a0aa-3a8e19ed14a0', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-06 17:49:24');
INSERT INTO `bank` VALUES ('6559ad28-e154-11e6-bec8-7ce91bcbaaef', '白痴银行', '0239e0b3-d843-495b-a0aa-3a8e19ed14a0', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-08 17:42:41');
INSERT INTO `bank` VALUES ('7a925574-e231-434b-a557-ddcd97e11d9b', '智障银行', '43652a2d-b7e1-47dc-9297-4fa84e310213', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-31 17:37:03');
INSERT INTO `bank` VALUES ('89a8fe4b-c8b8-4951-96d4-35bc8d4f2e2f', '辣鸡银行', '0239e0b3-d843-495b-a0aa-3a8e19ed14a0', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-08 11:53:30');
INSERT INTO `bank` VALUES ('af0c3380-d436-4783-a0e5-ce56c00c07b3', '人民银行', '0239e0b3-d843-495b-a0aa-3a8e19ed14a0', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-08 17:43:27');
INSERT INTO `bank` VALUES ('bf842616-00c6-487c-a6d6-6b4036864840', '中国银行', '5f2bb9ef-355d-4a6f-83ef-327d9382496b', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-31 17:37:31');
INSERT INTO `bank` VALUES ('dfc73240-adad-4014-b66f-4718965330b3', '呵呵银行', 'd16c5d87-d44d-46cf-afec-53f94af2849f', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-31 17:36:48');
INSERT INTO `bank` VALUES ('f8ada295-eac4-4ec1-936e-befdd32c8da7', '哈哈银行', '88744cf9-dbd6-4cc8-b7fc-aba9644c833b', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-31 17:37:10');
INSERT INTO `bank` VALUES ('fac4d62b-5e7c-4960-97a8-7a91eaacbf50', '嘿嘿银行', '0239e0b3-d843-495b-a0aa-3a8e19ed14a0', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-06 18:40:56');

-- ----------------------------
-- Table structure for `bank_financial_product`
-- ----------------------------
DROP TABLE IF EXISTS `bank_financial_product`;
CREATE TABLE `bank_financial_product` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '产品名称',
  `series` varchar(20) NOT NULL COMMENT '产品系列',
  `scode` varchar(100) NOT NULL COMMENT '产品编号',
  `shortname` varchar(50) NOT NULL COMMENT '产品简称',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `gp` varchar(50) DEFAULT NULL COMMENT '管理方',
  `custodian` varchar(50) DEFAULT NULL COMMENT '资金托管人',
  `currency_type` varchar(20) DEFAULT NULL COMMENT '理财币种',
  `target_annualized_return_rate` decimal(10,2) NOT NULL COMMENT '目标年化收益率',
  `min_annualized_return_rate` decimal(10,2) DEFAULT NULL COMMENT '最小年化收益率',
  `min_invest_amount` decimal(10,2) NOT NULL COMMENT '最小投资金额',
  `min_invest_amount_add` decimal(10,2) NOT NULL COMMENT '最小投资递增',
  `max_invest_amount` decimal(20,2) NOT NULL COMMENT '最大投资金额',
  `subscribe_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '认购费(每天或每年)',
  `purchase_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '申购费',
  `redeming_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '赎回费(每天或每年)',
  `management_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '管理费(每天或每年)',
  `custody_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '托管费',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '产品规模',
  `collect_starttime` datetime DEFAULT NULL COMMENT '认购起始日',
  `collect_endtime` datetime DEFAULT NULL COMMENT '认购截止日',
  `term` int(11) DEFAULT NULL COMMENT '产品期限（多少天，起息日（含）至到期日（不含）的计算）',
  `record_date` datetime DEFAULT NULL COMMENT '登记日',
  `value_date` datetime DEFAULT NULL COMMENT '起息日',
  `maturity_date` datetime DEFAULT NULL COMMENT '到期日',
  `flag_purchase` tinyint(1) DEFAULT NULL COMMENT 'open,close(申购状态)',
  `flag_redemption` tinyint(1) DEFAULT NULL COMMENT '赎回状态',
  `invest_scope` varchar(1000) DEFAULT NULL COMMENT '投资范围',
  `style` varchar(20) DEFAULT NULL COMMENT '谨慎/稳健/平衡/进取/激进(投资风格)',
  `risk_level` varchar(20) DEFAULT NULL COMMENT 'R1/R2/R3/R4/R5(风险等级)',
  `credit_level` varchar(20) DEFAULT NULL COMMENT '信用等级',
  `revenue_feature` varchar(2000) DEFAULT NULL COMMENT '产品收益说明',
  `flag_closeend` tinyint(1) DEFAULT NULL COMMENT '是否封闭产品',
  `guarantee_status` varchar(20) DEFAULT NULL COMMENT '不保本；保本；保本保息；（保本保息状态）',
  `area` varchar(100) DEFAULT NULL COMMENT '发行区域',
  `remark` varchar(5000) DEFAULT NULL COMMENT '产品更多描述（富文本）',
  `net_worth` decimal(20,8) DEFAULT NULL COMMENT '当前净值',
  `creator` varchar(36) NOT NULL COMMENT '信息录入人',
  `createtime` datetime NOT NULL COMMENT '信息录入时间',
  `document` varchar(36) DEFAULT NULL COMMENT '上传产品说明书（doc/pdf/docx）',
  `payment_type` varchar(10) DEFAULT NULL COMMENT '收益支付方式  day-按日 month-按月 last-到期一次',
  `stage` varchar(20) DEFAULT 'unstart',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_financial_product
-- ----------------------------
INSERT INTO `bank_financial_product` VALUES ('0a495faf-082d-4eb0-825e-fef7cdee2330', '赔钱宝', '213', '1221', '1232111', 'income', 'unchecked', '', 'all', 'rmb', '9.99', '0.00', '0.00', '0.00', '0.00', null, null, null, null, null, '0.00', null, null, null, '2017-06-29 00:00:00', null, null, '0', '0', null, 'profit', 'R1', '', null, '1', '3', 'all', null, null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-01 17:41:10', null, null, 'unstart');
INSERT INTO `bank_financial_product` VALUES ('22dd8979-d3cc-440f-b140-812597f95b7d', '一个产品', '3213', '123', '321213', 'income', 'unchecked', null, null, null, '3.40', null, '0.00', '0.00', '0.00', null, null, null, null, null, '0.00', null, null, null, null, null, null, null, null, '', null, null, null, '', null, null, null, '', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-05 17:55:16', null, null, 'unstart');
INSERT INTO `bank_financial_product` VALUES ('471e7c08-3119-4194-a89f-5b0100c0c1cc', '产品名称', '产品系列', '产品编号', '产品简称', 'income', 'unchecked', '', '0639461f-f09f-4910-b101-c5c23e974470', 'rmb', '0.00', '0.00', '0.00', '0.00', '0.00', '0.000000', '0.000000', '0.000000', '0.000000', '0.000000', '0.00', '2017-05-28 12:13:00', '2017-05-30 12:12:00', '6', '2017-06-06 00:00:00', '2017-06-16 00:00:00', '2017-06-21 00:00:00', '0', '0', '&lt;p&gt;是打发&lt;/p&gt;', 'profit', 'R1', '', '&lt;p&gt;大&lt;/p&gt;', '1', '3', 'all', '&lt;p&gt;撒&lt;/p&gt;', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-01 12:12:42', '0a673d71-185e-4d26-83b0-ab917c7b121f', null, 'finished');
INSERT INTO `bank_financial_product` VALUES ('90e54ce6-9510-4851-885b-98af96adf07f', '银行理财产品', '银行理财产品1', '12312asdsad', '银行理财产品', 'income', 'published', '1银行理财产品1,1000000', '6559ad28-e154-11e6-bec8-7ce91bcbaaef', 'rmb', '10.00', '5.00', '10.00', '100.00', '100000.00', '1.000000', '1.000000', '1.000000', '1.000000', '1.000000', '1000000.00', '2017-03-11 00:00:00', '2017-03-12 23:59:59', '100', '2017-03-11 00:00:00', '2017-03-12 00:00:00', '2017-03-31 00:00:00', '0', '0', '银行理财产品银行理财产品123123', 'profit', 'R1', 'one', '银行理财产品银行理财产品12312312312', '1', '1', 'all', '银行理财产品银行理财产品银行理财产品银行理财产品银行理财产品123123123', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-03-11 18:01:46', '7538d5e4-052f-4125-b69f-a0434ed53f0c', null, 'finished');
INSERT INTO `bank_financial_product` VALUES ('9617333d-847a-4fe3-84e8-097194595631', '易租宝', '3123', '12321', '123213', 'income', 'unchecked', '', 'all', 'rmb', '50.00', '0.00', '0.00', '0.00', '0.00', null, null, null, null, null, '0.00', null, null, null, '2017-06-21 00:00:00', null, null, '0', '0', null, 'profit', 'R1', '', null, '1', '3', 'all', null, null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-01 17:39:59', null, null, 'unstart');
INSERT INTO `bank_financial_product` VALUES ('d8f9d4e7-509e-4fc2-8d5a-f779d90c2146', '产品名称2', '11', '111', '11', 'income', 'unchecked', '', 'bf842616-00c6-487c-a6d6-6b4036864840', 'rmb', '0.00', '0.00', '0.00', '0.00', '0.00', '0.000000', '0.000000', '0.000000', '0.000000', '0.000000', '0.00', '2017-06-01 12:21:00', '2017-06-02 12:21:00', '17', '2017-06-15 00:00:00', '2017-06-13 00:00:00', '2017-06-29 00:00:00', '0', '0', '', 'profit', 'R1', '', '', '1', '3', 'all', '', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-01 12:21:41', '40076433-ba80-4c87-b60b-b37a8db5b263', null, 'finished');
INSERT INTO `bank_financial_product` VALUES ('ec1e37d6-7609-4b9b-9d5a-b4992a312a26', '【建设银行】建行财富2017年第3期人民币非保本', '建行财富', 'ZH020220170200301', '建行财富', 'income', 'unchecked', '111', 'bf842616-00c6-487c-a6d6-6b4036864840', 'rmb', '5.51', '5.11', '10000.00', '10000.00', '50000000.00', '4.110000', '4.000000', '4.000000', '4.000000', '4.000000', '50000000.00', '2017-06-01 15:36:00', '2017-06-06 15:36:00', '7', '2017-06-07 00:00:00', '2017-06-07 00:00:00', '2017-06-14 00:00:00', '1', '0', '&lt;p&gt;企鹅&lt;/p&gt;', 'balance', 'R4', 'one', '&lt;p&gt;11&lt;/p&gt;', '1', '1', '3fcbe315-0947-11e7-97f7-3a386a6ce01d', '124', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-12 15:36:58', '6f96ace0-62f9-4915-af6f-7e1742a14318', null, 'finished');
INSERT INTO `bank_financial_product` VALUES ('eff94291-e158-48d8-8c03-fe7b7eb210d3', '中国工商银行理财宝1号', '银行理财', 'ICBC-100010', '银行理财', 'income', 'unchecked', '中国工商银行', '6559ad28-e154-11e6-bec8-7ce91bcbaaef', 'rmb', '30.88', '10.00', '10000.00', '10000.00', '1000000.00', '1.000000', '1.000000', '1.000000', '1.000000', '1.000000', '10000000.00', '2017-03-15 00:00:00', '2017-03-16 23:59:59', '180', '2017-03-14 00:00:00', '2017-03-17 00:00:00', '2017-04-07 00:00:00', '0', '0', '待定', 'profit', 'R1', 'one', '待定', '1', '1', 'all', '待定', '123.00000000', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-03-14 15:47:15', 'bd5abc41-e149-4a1f-8e41-b928a4683b3a', null, 'finished');
INSERT INTO `bank_financial_product` VALUES ('f390f6e2-6522-47b2-927f-53725dece923', '滚蛋宝', '132', '132', '132', 'income', 'deleted', null, null, null, '0.00', null, '0.00', '0.00', '0.00', null, null, null, null, null, '0.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1.00000000', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-04-28 14:22:09', null, null, 'unstart');
INSERT INTO `bank_financial_product` VALUES ('f4800b5b-2282-4233-899a-ff22ac00cc7a', '中欧滚蛋宝', '21321', '3213', '3213213', 'income', 'unchecked', '', 'all', 'rmb', '23.21', '0.00', '0.00', '0.00', '0.00', null, null, null, null, null, '0.00', null, null, null, '2017-06-13 00:00:00', null, null, '0', '0', null, 'profit', 'R1', '', null, '1', '3', 'all', null, null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-01 17:36:16', null, null, 'unstart');
INSERT INTO `bank_financial_product` VALUES ('fc7b91e9-83df-4ba7-97bf-7fc4b80fbbde', '【建设银行】乾元-私享型2017-74理财产品', '乾元', 'ZH070417006067D11', '乾元-私享型', 'unfloatingIncome', 'unchecked', '中国建设银行', 'bf842616-00c6-487c-a6d6-6b4036864840', 'rmb', '4.35', '4.35', '50000.00', '10000.00', '50000000.00', '1.000000', '1.000000', '1.000000', '1.000000', '1.000000', '50000000.00', '2017-06-05 11:51:00', '2017-06-07 11:51:00', '7', '2017-06-14 00:00:00', '2017-06-08 00:00:00', '2017-06-15 00:00:00', '1', '0', '&lt;p&gt;12323&lt;/p&gt;', 'profit', 'R2', 'one', '&lt;p&gt;23123&lt;/p&gt;', '1', '1', '3fcbe315-0947-11e7-97f7-3a386a6ce01d', '&lt;p&gt;213213&lt;/p&gt;', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-09 11:51:19', '6861990c-5102-4fd7-80ac-8388e7c6a2e2', null, 'finished');

-- ----------------------------
-- Table structure for `bank_financial_product_daily`
-- ----------------------------
DROP TABLE IF EXISTS `bank_financial_product_daily`;
CREATE TABLE `bank_financial_product_daily` (
  `uuid` varchar(36) NOT NULL,
  `bank_financial_product` varchar(36) NOT NULL COMMENT '银行产品',
  `netvalue` decimal(16,8) NOT NULL COMMENT '净值',
  `createtime` datetime NOT NULL COMMENT '净值录入时间',
  `creator` varchar(36) NOT NULL COMMENT '录入人',
  `statistime` datetime NOT NULL COMMENT '登记日期',
  PRIMARY KEY (`uuid`),
  KEY `ak_key_2` (`bank_financial_product`) USING BTREE,
  CONSTRAINT `bank_financial_product_daily_fk1` FOREIGN KEY (`bank_financial_product`) REFERENCES `bank_financial_product` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_financial_product_daily
-- ----------------------------
INSERT INTO `bank_financial_product_daily` VALUES ('0d93f16e-0dff-4462-8894-ca32277d6a5b', 'f390f6e2-6522-47b2-927f-53725dece923', '12.00000000', '2017-05-31 15:43:31', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-01 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('195e483c-6251-4485-8101-2ea5be69510f', 'f390f6e2-6522-47b2-927f-53725dece923', '1.00000000', '2017-05-31 15:43:40', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-23 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('1b8d1e5e-243d-41eb-85a0-3781e2c99a00', 'eff94291-e158-48d8-8c03-fe7b7eb210d3', '123.00000000', '2017-03-28 18:33:38', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-03-02 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('52bd0027-fac4-4e0d-86dc-1b49be61d8c9', 'f390f6e2-6522-47b2-927f-53725dece923', '1.00000000', '2017-05-31 15:44:07', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-20 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('5acbaac8-0d7a-495c-9611-d7fe041df7d4', 'f390f6e2-6522-47b2-927f-53725dece923', '11.00000000', '2017-05-31 15:43:22', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-15 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('6c78f1a3-d884-4d5e-ac01-cdb2114ac94e', 'f390f6e2-6522-47b2-927f-53725dece923', '1.00000000', '2017-05-31 15:44:01', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-21 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('795c7e08-5c0c-4ef5-8770-ad67dbe45969', 'f390f6e2-6522-47b2-927f-53725dece923', '12.00000000', '2017-05-31 11:24:11', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-25 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('8260ab1a-55cf-42b8-9b4e-2bde30da1401', 'f390f6e2-6522-47b2-927f-53725dece923', '1.00000000', '2017-05-31 15:43:50', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-30 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('8bce3fc8-eda7-4031-acb9-918d2c0911b2', 'f390f6e2-6522-47b2-927f-53725dece923', '1.00000000', '2017-05-31 15:44:25', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-24 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('ba344f6e-5139-446a-83d6-64d60b3249b4', 'eff94291-e158-48d8-8c03-fe7b7eb210d3', '1.00000000', '2017-03-28 18:00:06', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-03-01 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('c293159f-8e6b-49c0-a26f-d64c678ffc2f', 'f390f6e2-6522-47b2-927f-53725dece923', '123.00000000', '2017-05-31 15:43:14', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-16 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('e40db08a-b345-4d27-88db-3dac0fe45b75', 'f390f6e2-6522-47b2-927f-53725dece923', '1.00000000', '2017-05-31 15:43:55', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-29 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('ea8172b4-f40f-42d0-9a6f-914ca28c5eeb', 'f390f6e2-6522-47b2-927f-53725dece923', '2.00000000', '2017-05-02 17:37:52', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-17 00:00:00');
INSERT INTO `bank_financial_product_daily` VALUES ('ed082089-b4b0-4635-8006-f2e864ccc618', 'f390f6e2-6522-47b2-927f-53725dece923', '1.00000000', '2017-04-28 14:52:24', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-04-19 00:00:00');

-- ----------------------------
-- Table structure for `bank_financial_product_invest`
-- ----------------------------
DROP TABLE IF EXISTS `bank_financial_product_invest`;
CREATE TABLE `bank_financial_product_invest` (
  `uuid` varchar(36) NOT NULL,
  `bank_financial_product` varchar(36) NOT NULL,
  `bank_financial_product_publish` varchar(36) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `stage` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_financial_product_invest
-- ----------------------------

-- ----------------------------
-- Table structure for `bank_financial_product_invest_operate`
-- ----------------------------
DROP TABLE IF EXISTS `bank_financial_product_invest_operate`;
CREATE TABLE `bank_financial_product_invest_operate` (
  `uuid` varchar(36) NOT NULL,
  `bank_financial_product_invest` varchar(36) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `value` varchar(100) NOT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `checker` varchar(36) DEFAULT NULL,
  `checktime` timestamp NULL DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `FK_BFPO_BFP` (`bank_financial_product_invest`),
  CONSTRAINT `FK_BFPIO_BFPI` FOREIGN KEY (`bank_financial_product_invest`) REFERENCES `bank_financial_product_invest` (`uuid`),
  CONSTRAINT `FK_BFPIO_C` FOREIGN KEY (`checker`) REFERENCES `bk_operator_role` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_financial_product_invest_operate
-- ----------------------------

-- ----------------------------
-- Table structure for `bank_financial_product_operate`
-- ----------------------------
DROP TABLE IF EXISTS `bank_financial_product_operate`;
CREATE TABLE `bank_financial_product_operate` (
  `uuid` varchar(36) NOT NULL,
  `bank_financial_product` varchar(36) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `value` text NOT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `checker` varchar(36) DEFAULT NULL,
  `checktime` timestamp NULL DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `FK_BFPO_BFP` (`bank_financial_product`),
  CONSTRAINT `FK_BFPO_BFP` FOREIGN KEY (`bank_financial_product`) REFERENCES `bank_financial_product` (`uuid`),
  CONSTRAINT `FK_BFPO_CHECKER` FOREIGN KEY (`checker`) REFERENCES `bk_operator_role` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_financial_product_operate
-- ----------------------------

-- ----------------------------
-- Table structure for `bank_financial_product_publish`
-- ----------------------------
DROP TABLE IF EXISTS `bank_financial_product_publish`;
CREATE TABLE `bank_financial_product_publish` (
  `uuid` varchar(36) NOT NULL,
  `bank_financial_product` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '产品名称',
  `series` varchar(20) NOT NULL COMMENT '产品系列',
  `scode` varchar(100) NOT NULL COMMENT '产品编号',
  `shortname` varchar(50) NOT NULL COMMENT '产品简称',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `stage` varchar(20) NOT NULL,
  `status` varchar(10) NOT NULL COMMENT '状态',
  `gp` varchar(50) DEFAULT NULL COMMENT '管理方',
  `custodian` varchar(50) DEFAULT NULL COMMENT '资金托管人',
  `currency_type` varchar(20) DEFAULT NULL COMMENT '理财币种',
  `target_annualized_return_rate` decimal(10,2) NOT NULL COMMENT '目标年化收益率',
  `min_annualized_return_rate` decimal(10,2) DEFAULT NULL COMMENT '最小年化收益率',
  `min_invest_amount` decimal(10,2) NOT NULL COMMENT '最小投资金额',
  `min_invest_amount_add` decimal(10,2) NOT NULL COMMENT '最小投资递增',
  `max_invest_amount` decimal(20,2) NOT NULL COMMENT '最大投资金额',
  `subscribe_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '认购费(每天或每年)',
  `purchase_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '申购费',
  `redeming_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '赎回费(每天或每年)',
  `management_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '管理费(每天或每年)',
  `custody_fee` decimal(10,6) DEFAULT '0.000000' COMMENT '托管费',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '产品规模',
  `collect_starttime` datetime DEFAULT NULL COMMENT '认购起始日',
  `collect_endtime` datetime DEFAULT NULL COMMENT '认购截止日',
  `term` int(11) DEFAULT NULL COMMENT '产品期限（多少天，起息日（含）至到期日（不含）的计算）',
  `record_date` datetime DEFAULT NULL COMMENT '登记日',
  `value_date` datetime DEFAULT NULL COMMENT '起息日',
  `maturity_date` datetime DEFAULT NULL COMMENT '到期日',
  `flag_purchase` tinyint(1) DEFAULT NULL COMMENT 'open,close(申购状态)',
  `flag_redemption` tinyint(1) DEFAULT NULL COMMENT '赎回状态',
  `invest_scope` varchar(1000) DEFAULT NULL COMMENT '投资范围',
  `style` varchar(20) DEFAULT NULL COMMENT '谨慎/稳健/平衡/进取/激进(投资风格)',
  `risk_level` varchar(20) DEFAULT NULL COMMENT 'R1/R2/R3/R4/R5(风险等级)',
  `credit_level` varchar(20) DEFAULT NULL COMMENT '信用等级',
  `revenue_feature` varchar(2000) DEFAULT NULL COMMENT '产品收益说明',
  `flag_closeend` tinyint(1) DEFAULT NULL COMMENT '是否封闭产品',
  `guarantee_status` varchar(20) DEFAULT NULL COMMENT '不保本；保本；保本保息；（保本保息状态）',
  `area` varchar(100) DEFAULT NULL COMMENT '发行区域',
  `remark` varchar(5000) DEFAULT NULL COMMENT '产品更多描述（富文本）',
  `net_worth` decimal(20,8) DEFAULT NULL COMMENT '当前净值',
  `document` varchar(36) DEFAULT NULL COMMENT '上传产品说明书（doc/pdf/docx）',
  `payment_type` varchar(10) DEFAULT NULL COMMENT '收益支付方式  day-按日 month-按月 last-到期一次',
  `creator` varchar(36) NOT NULL COMMENT '信息录入人',
  `createtime` datetime NOT NULL COMMENT '信息录入时间',
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPP_BFP` (`bank_financial_product`),
  CONSTRAINT `FK_BFPP_BFP` FOREIGN KEY (`bank_financial_product`) REFERENCES `bank_financial_product` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_financial_product_publish
-- ----------------------------

-- ----------------------------
-- Table structure for `bank_financial_product_publish_operate`
-- ----------------------------
DROP TABLE IF EXISTS `bank_financial_product_publish_operate`;
CREATE TABLE `bank_financial_product_publish_operate` (
  `uuid` varchar(36) NOT NULL,
  `bank_financial_product_publish` varchar(36) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `value` varchar(100) NOT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `checker` varchar(36) DEFAULT NULL,
  `checktime` timestamp NULL DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `FK_BFPO_BFP` (`bank_financial_product_publish`),
  CONSTRAINT `FK_BFPPO_BFPP` FOREIGN KEY (`bank_financial_product_publish`) REFERENCES `bank_financial_product_publish` (`uuid`),
  CONSTRAINT `FK_BFPPO_C` FOREIGN KEY (`checker`) REFERENCES `bk_operator_role` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_financial_product_publish_operate
-- ----------------------------

-- ----------------------------
-- Table structure for `bk_area`
-- ----------------------------
DROP TABLE IF EXISTS `bk_area`;
CREATE TABLE `bk_area` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  `pid` varchar(36) DEFAULT NULL,
  `scode` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `Unique_Key_01` (`scode`) USING BTREE,
  KEY `fk_reference_35` (`pid`),
  CONSTRAINT `fk_reference_35` FOREIGN KEY (`pid`) REFERENCES `bk_area` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_area
-- ----------------------------
INSERT INTO `bk_area` VALUES ('3fcbe315-0947-11e7-97f7-3a386a6ce01d', '全国', '1', null, '100000');
INSERT INTO `bk_area` VALUES ('3fcbe529-0947-11e7-97f7-3a386a6ce01d', '北京市', '1', null, '110000');
INSERT INTO `bk_area` VALUES ('3fcc0d8e-0947-11e7-97f7-3a386a6ce01d', '天津市', '1', null, '120000');
INSERT INTO `bk_area` VALUES ('3fcc0e93-0947-11e7-97f7-3a386a6ce01d', '河北省', '1', null, '130000');
INSERT INTO `bk_area` VALUES ('3fcc0f0f-0947-11e7-97f7-3a386a6ce01d', '山西省', '1', null, '140000');
INSERT INTO `bk_area` VALUES ('3fcc0f8b-0947-11e7-97f7-3a386a6ce01d', '内蒙古自治区', '1', null, '150000');
INSERT INTO `bk_area` VALUES ('3fcc100f-0947-11e7-97f7-3a386a6ce01d', '辽宁省', '1', null, '210000');
INSERT INTO `bk_area` VALUES ('3fcc1087-0947-11e7-97f7-3a386a6ce01d', '吉林省', '1', null, '220000');
INSERT INTO `bk_area` VALUES ('3fcc10ee-0947-11e7-97f7-3a386a6ce01d', '黑龙江省', '1', null, '230000');
INSERT INTO `bk_area` VALUES ('3fcc1172-0947-11e7-97f7-3a386a6ce01d', '上海市', '1', null, '310000');
INSERT INTO `bk_area` VALUES ('3fcc1262-0947-11e7-97f7-3a386a6ce01d', '江苏省', '1', null, '320000');
INSERT INTO `bk_area` VALUES ('3fcc12c8-0947-11e7-97f7-3a386a6ce01d', '浙江省', '1', null, '330000');
INSERT INTO `bk_area` VALUES ('3fcc1326-0947-11e7-97f7-3a386a6ce01d', '安徽省', '1', null, '340000');
INSERT INTO `bk_area` VALUES ('3fcc1396-0947-11e7-97f7-3a386a6ce01d', '福建省', '1', null, '350000');
INSERT INTO `bk_area` VALUES ('3fcc13de-0947-11e7-97f7-3a386a6ce01d', '江西省', '1', null, '360000');
INSERT INTO `bk_area` VALUES ('3fcc142f-0947-11e7-97f7-3a386a6ce01d', '山东省', '1', null, '370000');
INSERT INTO `bk_area` VALUES ('3fcc147c-0947-11e7-97f7-3a386a6ce01d', '河南省', '1', null, '410000');
INSERT INTO `bk_area` VALUES ('3fcc14d2-0947-11e7-97f7-3a386a6ce01d', '湖北省', '1', null, '420000');
INSERT INTO `bk_area` VALUES ('3fcc152c-0947-11e7-97f7-3a386a6ce01d', '湖南省', '1', null, '430000');
INSERT INTO `bk_area` VALUES ('3fcc1581-0947-11e7-97f7-3a386a6ce01d', '广东省', '1', null, '440000');
INSERT INTO `bk_area` VALUES ('3fcc1ad1-0947-11e7-97f7-3a386a6ce01d', '广西壮族自治区', '1', null, '450000');
INSERT INTO `bk_area` VALUES ('3fcc1b38-0947-11e7-97f7-3a386a6ce01d', '海南省', '1', null, '460000');
INSERT INTO `bk_area` VALUES ('3fcc1b89-0947-11e7-97f7-3a386a6ce01d', '重庆市', '1', null, '500000');
INSERT INTO `bk_area` VALUES ('3fcc1bda-0947-11e7-97f7-3a386a6ce01d', '四川省', '1', null, '510000');
INSERT INTO `bk_area` VALUES ('3fcc1c27-0947-11e7-97f7-3a386a6ce01d', '贵州省', '1', null, '520000');
INSERT INTO `bk_area` VALUES ('3fcc1c74-0947-11e7-97f7-3a386a6ce01d', '云南省', '1', null, '530000');
INSERT INTO `bk_area` VALUES ('3fcc1cc6-0947-11e7-97f7-3a386a6ce01d', '西藏自治区', '1', null, '540000');
INSERT INTO `bk_area` VALUES ('3fcc1d1b-0947-11e7-97f7-3a386a6ce01d', '陕西省', '1', null, '610000');
INSERT INTO `bk_area` VALUES ('3fcc1da4-0947-11e7-97f7-3a386a6ce01d', '甘肃省', '1', null, '620000');
INSERT INTO `bk_area` VALUES ('3fcc1e0f-0947-11e7-97f7-3a386a6ce01d', '青海省', '1', null, '630000');
INSERT INTO `bk_area` VALUES ('3fcc1e76-0947-11e7-97f7-3a386a6ce01d', '宁夏回族自治区', '1', null, '640000');
INSERT INTO `bk_area` VALUES ('3fcc1ecb-0947-11e7-97f7-3a386a6ce01d', '新疆维吾尔自治区', '1', null, '650000');
INSERT INTO `bk_area` VALUES ('3fcc1f18-0947-11e7-97f7-3a386a6ce01d', '台湾省', '1', null, '710000');
INSERT INTO `bk_area` VALUES ('3fcc1f76-0947-11e7-97f7-3a386a6ce01d', '香港特别行政区', '1', null, '810000');
INSERT INTO `bk_area` VALUES ('3fcc1fdd-0947-11e7-97f7-3a386a6ce01d', '澳门特别行政区', '1', null, '820000');

-- ----------------------------
-- Table structure for `bk_controller`
-- ----------------------------
DROP TABLE IF EXISTS `bk_controller`;
CREATE TABLE `bk_controller` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_controller
-- ----------------------------
INSERT INTO `bk_controller` VALUES ('097ffbf1-ea8f-11e6-aba8-30e5e9e3b3d6', 'resource', '资源操作');
INSERT INTO `bk_controller` VALUES ('10b51358-f7ea-11e6-ada9-d0295a4759e7', 'bankFinancialProduct', '银行理财产品操作');
INSERT INTO `bk_controller` VALUES ('26193227-f81a-11e6-ada9-d0295a4759e7', 'fund', '基金操作');
INSERT INTO `bk_controller` VALUES ('3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', 'roleMenu', '角色页面权限操作');
INSERT INTO `bk_controller` VALUES ('479f5023-f716-11e6-8a88-461ab128b514', 'superAdmin', '后台管理员操作');
INSERT INTO `bk_controller` VALUES ('47b271a9-97c2-47d2-b0a1-3da3e0c059e2', 'investor', '投资者用户功能');
INSERT INTO `bk_controller` VALUES ('68e07731-f359-11e6-8a3c-ab6e287cf557', 'controllerMethod', '功能方法操作');
INSERT INTO `bk_controller` VALUES ('796fc6f3-f71c-11e6-8a88-461ab128b514', 'operateOperator', '运营用户操作');
INSERT INTO `bk_controller` VALUES ('7cc0431a-f41e-11e6-8e06-510d4a4da552', 'operator', '管理员操作');
INSERT INTO `bk_controller` VALUES ('85ca07d0-f71c-11e6-8a88-461ab128b514', 'financeOperator', '财务用户操作');
INSERT INTO `bk_controller` VALUES ('8e1cbbb9-f35a-11e6-8a3c-ab6e287cf557', 'roleMethod', '角色功能权限操作');
INSERT INTO `bk_controller` VALUES ('8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'manager', '主理人操作');
INSERT INTO `bk_controller` VALUES ('c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'menu', '页面操作');
INSERT INTO `bk_controller` VALUES ('ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'bankFinancialProductInvest', '银行理财产品投资操作');
INSERT INTO `bk_controller` VALUES ('cf2e6a4b-0963-11e7-97f7-3a386a6ce01d', 'area', '地区管理操作');
INSERT INTO `bk_controller` VALUES ('e7f881af-0bed-7a2c-93c9-c10bee167af1', 'initialize', '后台管理员控制器');
INSERT INTO `bk_controller` VALUES ('f0c7bc11-edea-11e6-954c-704e382d3ba0', 'branchBank', '支行操作');
INSERT INTO `bk_controller` VALUES ('f789bacd-6225-11e7-89d2-39a48ccf689c', 'bankFinancialProductPublish', '银行理财产品发布操作');
INSERT INTO `bk_controller` VALUES ('fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', 'bank', '银行操作');
INSERT INTO `bk_controller` VALUES ('ffe60503-f359-11e6-8a3c-ab6e287cf557', 'role', '角色操作');

-- ----------------------------
-- Table structure for `bk_controller_method`
-- ----------------------------
DROP TABLE IF EXISTS `bk_controller_method`;
CREATE TABLE `bk_controller_method` (
  `uuid` varchar(36) NOT NULL,
  `controller` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_1` (`controller`,`name`),
  KEY `fk_reference_04` (`controller`),
  CONSTRAINT `fk_reference_04` FOREIGN KEY (`controller`) REFERENCES `bk_controller` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_controller_method
-- ----------------------------
INSERT INTO `bk_controller_method` VALUES ('0133e56d-45d2-11e7-af5a-d06899c61413', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'netvalueGet', '银行理财产品每日净值获取');
INSERT INTO `bk_controller_method` VALUES ('017f041b-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'stageList', '银行理财产品投资阶段列表');
INSERT INTO `bk_controller_method` VALUES ('04833cae-edeb-11e6-954c-704e382d3ba0', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', 'add', '支行添加');
INSERT INTO `bk_controller_method` VALUES ('0a33aea5-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'add', '银行理财产品投资添加');
INSERT INTO `bk_controller_method` VALUES ('0ed32dc1-f35a-11e6-8a3c-ab6e287cf557', 'ffe60503-f359-11e6-8a3c-ab6e287cf557', 'all', '角色全部');
INSERT INTO `bk_controller_method` VALUES ('1170a4e9-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'check', '银行理财产品投资审核');
INSERT INTO `bk_controller_method` VALUES ('1742f9b3-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'get', '银行理财产品发布获取');
INSERT INTO `bk_controller_method` VALUES ('1a462583-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'edit', '银行理财产品投资修改');
INSERT INTO `bk_controller_method` VALUES ('1b568f79-f35a-11e6-8a3c-ab6e287cf557', 'ffe60503-f359-11e6-8a3c-ab6e287cf557', 'get', '角色获取');
INSERT INTO `bk_controller_method` VALUES ('1b886c10-ee6e-11e6-b000-412b17f07060', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', 'edit', '银行修改');
INSERT INTO `bk_controller_method` VALUES ('1f6f13e7-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'list', '银行理财产品发布列表');
INSERT INTO `bk_controller_method` VALUES ('25691f5a-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'delete', '银行理财产品投资删除');
INSERT INTO `bk_controller_method` VALUES ('26628f5e-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'statusList', '银行理财产品发布状态列表');
INSERT INTO `bk_controller_method` VALUES ('2ed1a154-0964-11e7-97f7-3a386a6ce01d', 'cf2e6a4b-0963-11e7-97f7-3a386a6ce01d', 'all', '获取全部地区');
INSERT INTO `bk_controller_method` VALUES ('2fdddbdc-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'controllerlist', '获取功能列表');
INSERT INTO `bk_controller_method` VALUES ('2fe768bd-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'controllerget', '获取功能信息');
INSERT INTO `bk_controller_method` VALUES ('2feb6095-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'controlleradd', '添加功能信息');
INSERT INTO `bk_controller_method` VALUES ('2fee920f-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'controlleredit', '编辑功能信息');
INSERT INTO `bk_controller_method` VALUES ('2ff273b0-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'controllerdelete', '删除功能信息');
INSERT INTO `bk_controller_method` VALUES ('2ff869d3-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'methodlist', '获取方法列表');
INSERT INTO `bk_controller_method` VALUES ('3000de6f-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'methodget', '获取方法信息');
INSERT INTO `bk_controller_method` VALUES ('3007a7ec-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'methodadd', '添加方法信息');
INSERT INTO `bk_controller_method` VALUES ('300c9227-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'methodedit', '编辑方法信息');
INSERT INTO `bk_controller_method` VALUES ('30120889-1041-11e7-a0bb-519b17a1e492', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'methoddelete', '删除方法信息');
INSERT INTO `bk_controller_method` VALUES ('304ba42d-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'operateGet', '银行理财产品投资操作获取');
INSERT INTO `bk_controller_method` VALUES ('32295d11-d983-4d8f-9823-ae184e53b41b', '47b271a9-97c2-47d2-b0a1-3da3e0c059e2', 'get', '获取投资者用户详情');
INSERT INTO `bk_controller_method` VALUES ('397ef2bf-ee6e-11e6-b000-412b17f07060', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', 'delete', '银行删除');
INSERT INTO `bk_controller_method` VALUES ('39fd0144-62e0-11e7-8018-127d3cb7df39', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'exception', '银行理财产品发布异常下线');
INSERT INTO `bk_controller_method` VALUES ('3a9c15f7-6225-11e7-89d2-39a48ccf689c', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'stageList', '银行理财产品阶段列表');
INSERT INTO `bk_controller_method` VALUES ('3b219445-f7ea-11e6-ada9-d0295a4759e7', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'get', '银行理财产品获取');
INSERT INTO `bk_controller_method` VALUES ('3b5b53fa-f740-11e6-8a88-461ab128b514', '479f5023-f716-11e6-8a88-461ab128b514', 'password', '后台管理员重置密码');
INSERT INTO `bk_controller_method` VALUES ('3b9938b3-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'operateList', '银行理财产品投资操作列表');
INSERT INTO `bk_controller_method` VALUES ('3f8f508b-ea8f-11e6-aba8-30e5e9e3b3d6', '097ffbf1-ea8f-11e6-aba8-30e5e9e3b3d6', 'add', '资源添加');
INSERT INTO `bk_controller_method` VALUES ('417a80ff-f7ea-11e6-ada9-d0295a4759e7', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'list', '银行理财产品列表');
INSERT INTO `bk_controller_method` VALUES ('418b0374-f81a-11e6-ada9-d0295a4759e7', '26193227-f81a-11e6-ada9-d0295a4759e7', 'get', '基金获取');
INSERT INTO `bk_controller_method` VALUES ('445c79aa-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'operateCheck', '银行理财产品投资操作审核');
INSERT INTO `bk_controller_method` VALUES ('46709ddd-ea8e-11e6-aba8-30e5e9e3b3d6', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', 'list', '银行列表');
INSERT INTO `bk_controller_method` VALUES ('495fe49f-f81a-11e6-ada9-d0295a4759e7', '26193227-f81a-11e6-ada9-d0295a4759e7', 'list', '基金列表');
INSERT INTO `bk_controller_method` VALUES ('4966a6b2-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'stageList', '银行理财产品发布阶段列表');
INSERT INTO `bk_controller_method` VALUES ('4a0e1f69-f7ea-11e6-ada9-d0295a4759e7', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'add', '银行理财产品添加');
INSERT INTO `bk_controller_method` VALUES ('4b8953fe-f740-11e6-8a88-461ab128b514', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'password', '运营用户重置密码');
INSERT INTO `bk_controller_method` VALUES ('4c8341ee-45cf-11e7-af5a-d06899c61413', '26193227-f81a-11e6-ada9-d0295a4759e7', 'netvaluelist', '基金每日净值列表');
INSERT INTO `bk_controller_method` VALUES ('4dac7e6c-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'operateStatusList', '银行理财产品投资操作状态列表');
INSERT INTO `bk_controller_method` VALUES ('4e4b1c92-ee6e-11e6-b000-412b17f07060', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', 'get', '银行获取');
INSERT INTO `bk_controller_method` VALUES ('51a4dc67-f7ea-11e6-ada9-d0295a4759e7', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'edit', '银行理财产品修改');
INSERT INTO `bk_controller_method` VALUES ('52a4dfe2-f81a-11e6-ada9-d0295a4759e7', '26193227-f81a-11e6-ada9-d0295a4759e7', 'add', '基金添加');
INSERT INTO `bk_controller_method` VALUES ('52d40173-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'add', '银行理财产品发布添加');
INSERT INTO `bk_controller_method` VALUES ('534f95b7-45cf-11e7-af5a-d06899c61413', '26193227-f81a-11e6-ada9-d0295a4759e7', 'netvalueadd', '基金每日净值添加');
INSERT INTO `bk_controller_method` VALUES ('551e1dd4-6227-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'operateTypeList', '银行理财产品投资操作类型列表');
INSERT INTO `bk_controller_method` VALUES ('56da2617-f740-11e6-8a88-461ab128b514', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'password', '财务用户重置密码');
INSERT INTO `bk_controller_method` VALUES ('59194d3a-f7ea-11e6-ada9-d0295a4759e7', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'delete', '银行理财产品删除');
INSERT INTO `bk_controller_method` VALUES ('59761e39-f81a-11e6-ada9-d0295a4759e7', '26193227-f81a-11e6-ada9-d0295a4759e7', 'edit', '基金修改');
INSERT INTO `bk_controller_method` VALUES ('5b54c74c-f35a-11e6-8a3c-ab6e287cf557', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', 'edit', '角色页面权限修改');
INSERT INTO `bk_controller_method` VALUES ('5f79c327-f81a-11e6-ada9-d0295a4759e7', '26193227-f81a-11e6-ada9-d0295a4759e7', 'delete', '基金删除');
INSERT INTO `bk_controller_method` VALUES ('6323a38a-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'check', '银行理财产品发布审核');
INSERT INTO `bk_controller_method` VALUES ('652ab2ef-6225-11e7-89d2-39a48ccf689c', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'operateGet', '银行理财产品操作获取');
INSERT INTO `bk_controller_method` VALUES ('68356af3-f35a-11e6-8a3c-ab6e287cf557', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', 'list', '角色页面权限列表');
INSERT INTO `bk_controller_method` VALUES ('6f83a415-ee6e-11e6-b000-412b17f07060', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', 'get', '支行获取');
INSERT INTO `bk_controller_method` VALUES ('7147f331-f8d7-11e6-ada9-d0295a4759e7', '26193227-f81a-11e6-ada9-d0295a4759e7', 'check', '基金审核');
INSERT INTO `bk_controller_method` VALUES ('73224aba-f35a-11e6-8a3c-ab6e287cf557', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', 'sort', '角色页面权限排序');
INSERT INTO `bk_controller_method` VALUES ('7a916418-ee6e-11e6-b000-412b17f07060', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', 'list', '支行列表');
INSERT INTO `bk_controller_method` VALUES ('7b0d55db-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'edit', '银行理财产品发布修改');
INSERT INTO `bk_controller_method` VALUES ('816d0baa-ee6e-11e6-b000-412b17f07060', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', 'edit', '支行修改');
INSERT INTO `bk_controller_method` VALUES ('8288c144-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'delete', '银行理财产品发布删除');
INSERT INTO `bk_controller_method` VALUES ('82c6e003-f8d7-11e6-ada9-d0295a4759e7', '26193227-f81a-11e6-ada9-d0295a4759e7', 'statusList', '基金状态列表');
INSERT INTO `bk_controller_method` VALUES ('894359ec-ee6e-11e6-b000-412b17f07060', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', 'delete', '支行删除');
INSERT INTO `bk_controller_method` VALUES ('8a991da8-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'operateGet', '银行理财产品发布操作获取');
INSERT INTO `bk_controller_method` VALUES ('8bdb03d3-ea8e-11e6-aba8-30e5e9e3b3d6', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', 'add', '银行添加');
INSERT INTO `bk_controller_method` VALUES ('90b48cf1-f41e-11e6-8e06-510d4a4da552', '479f5023-f716-11e6-8a88-461ab128b514', 'get', '后台管理员获取');
INSERT INTO `bk_controller_method` VALUES ('93c9145a-b86d-4c4a-aa95-1fb997498b0e', '7cc0431a-f41e-11e6-8e06-510d4a4da552', 'password', '修改密码');
INSERT INTO `bk_controller_method` VALUES ('946fa323-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'operateList', '银行理财产品发布操作列表');
INSERT INTO `bk_controller_method` VALUES ('97ad9df0-f359-11e6-8a3c-ab6e287cf557', '68e07731-f359-11e6-8a3c-ab6e287cf557', 'all', '功能方法全部');
INSERT INTO `bk_controller_method` VALUES ('97dfbcc3-f41e-11e6-8e06-510d4a4da552', '479f5023-f716-11e6-8a88-461ab128b514', 'list', '后台管理员列表');
INSERT INTO `bk_controller_method` VALUES ('9e2aafc5-f41e-11e6-8e06-510d4a4da552', '479f5023-f716-11e6-8a88-461ab128b514', 'add', '后台管理员添加');
INSERT INTO `bk_controller_method` VALUES ('9e41f32b-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'operateCheck', '银行理财产品发布操作审核');
INSERT INTO `bk_controller_method` VALUES ('a4cca581-6225-11e7-89d2-39a48ccf689c', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'operateList', '银行理财产品操作列表');
INSERT INTO `bk_controller_method` VALUES ('a6d0db7c-f35a-11e6-8a3c-ab6e287cf557', '8e1cbbb9-f35a-11e6-8a3c-ab6e287cf557', 'list', '角色功能权限列表');
INSERT INTO `bk_controller_method` VALUES ('a7ac4d0a-f41e-11e6-8e06-510d4a4da552', '479f5023-f716-11e6-8a88-461ab128b514', 'edit', '后台管理员编辑');
INSERT INTO `bk_controller_method` VALUES ('a7ea6cb3-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'operateStatusList', '银行理财产品发布操作状态列表');
INSERT INTO `bk_controller_method` VALUES ('a804f9ec-ed1e-11e6-ae2e-d377d6cd7f14', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'add', '主理人添加');
INSERT INTO `bk_controller_method` VALUES ('aab7dc06-45ce-11e7-af5a-d06899c61413', '26193227-f81a-11e6-ada9-d0295a4759e7', 'netvalueGet', '基金每日净值获取');
INSERT INTO `bk_controller_method` VALUES ('abb3c663-0d30-11e7-a0bb-519b17a1e492', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'netvalueadd', '银行理财产品每日净值添加');
INSERT INTO `bk_controller_method` VALUES ('abbb87e3-0d30-11e7-a0bb-519b17a1e492', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'netvaluelist', '银行理财产品每日净值查询');
INSERT INTO `bk_controller_method` VALUES ('ac4c9214-ee6e-11e6-b000-412b17f07060', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'get', '主理人获取');
INSERT INTO `bk_controller_method` VALUES ('ade214d5-6225-11e7-89d2-39a48ccf689c', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'operateCheck', '银行理财产品操作审核');
INSERT INTO `bk_controller_method` VALUES ('b0aa4848-f41e-11e6-8e06-510d4a4da552', '479f5023-f716-11e6-8a88-461ab128b514', 'delete', '后台管理员删除');
INSERT INTO `bk_controller_method` VALUES ('b0e23549-f35a-11e6-8a3c-ab6e287cf557', '8e1cbbb9-f35a-11e6-8a3c-ab6e287cf557', 'edit', '角色功能权限修改');
INSERT INTO `bk_controller_method` VALUES ('b305328a-f8dc-11e6-ada9-d0295a4759e7', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'statusList', '银行理财产品状态列表');
INSERT INTO `bk_controller_method` VALUES ('b49844ef-ee6e-11e6-b000-412b17f07060', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'list', '主理人列表');
INSERT INTO `bk_controller_method` VALUES ('b6c59f5b-f71c-11e6-8a88-461ab128b514', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'get', '运营用户获取');
INSERT INTO `bk_controller_method` VALUES ('bd53d55f-f71c-11e6-8a88-461ab128b514', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'list', '运营用户列表');
INSERT INTO `bk_controller_method` VALUES ('be2693d4-f8dc-11e6-ada9-d0295a4759e7', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'check', '银行理财产品审核');
INSERT INTO `bk_controller_method` VALUES ('c13e8dd0-6226-11e7-89d2-39a48ccf689c', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'operateTypeList', '银行理财产品发布操作类型列表');
INSERT INTO `bk_controller_method` VALUES ('c1bf7b33-ee6e-11e6-b000-412b17f07060', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'edit', '主理人修改');
INSERT INTO `bk_controller_method` VALUES ('c35c115d-f71c-11e6-8a88-461ab128b514', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'add', '运营用户添加');
INSERT INTO `bk_controller_method` VALUES ('ca6684da-f71c-11e6-8a88-461ab128b514', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'edit', '运营用户修改');
INSERT INTO `bk_controller_method` VALUES ('cd45c198-0f9e-11e7-a0bb-519b17a1e492', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'menulist', '获取菜单列表');
INSERT INTO `bk_controller_method` VALUES ('cd58fe5d-0f9e-11e7-a0bb-519b17a1e492', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'menuget', '获取菜单信息');
INSERT INTO `bk_controller_method` VALUES ('cd5bff80-0f9e-11e7-a0bb-519b17a1e492', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'menuadd', '添加菜单信息');
INSERT INTO `bk_controller_method` VALUES ('cd60105a-0f9e-11e7-a0bb-519b17a1e492', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'menuedit', '编辑菜单信息');
INSERT INTO `bk_controller_method` VALUES ('cd64c33d-0f9e-11e7-a0bb-519b17a1e492', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'menudelete', '删除菜单信息');
INSERT INTO `bk_controller_method` VALUES ('ce703d25-ee6e-11e6-b000-412b17f07060', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'delete', '主理人删除');
INSERT INTO `bk_controller_method` VALUES ('d1bc30b9-6225-11e7-89d2-39a48ccf689c', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'operateStatusList', '银行理财产品操作状态列表');
INSERT INTO `bk_controller_method` VALUES ('d2aef97f-f71c-11e6-8a88-461ab128b514', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'delete', '运营用户删除');
INSERT INTO `bk_controller_method` VALUES ('d65aa0ea-f359-11e6-8a3c-ab6e287cf557', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'all', '页面全部');
INSERT INTO `bk_controller_method` VALUES ('d87d4a9b-bdad-45a6-aab8-0915a58e85eb', '47b271a9-97c2-47d2-b0a1-3da3e0c059e2', 'list', '获取投资者用户列表');
INSERT INTO `bk_controller_method` VALUES ('de1833a2-6225-11e7-89d2-39a48ccf689c', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'operateTypeList', '银行理财产品操作类型列表');
INSERT INTO `bk_controller_method` VALUES ('e0d97203-f71c-11e6-8a88-461ab128b514', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'get', '财务用户获取');
INSERT INTO `bk_controller_method` VALUES ('e30cedc9-6226-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'get', '银行理财产品投资获取');
INSERT INTO `bk_controller_method` VALUES ('e3f24925-45ca-11e7-af5a-d06899c61413', '26193227-f81a-11e6-ada9-d0295a4759e7', 'netvalueEdit', '基金每日净值编辑');
INSERT INTO `bk_controller_method` VALUES ('ea2bcfd4-0fef-47a9-8b23-dd5e829e3fc0', '7cc0431a-f41e-11e6-8e06-510d4a4da552', 'get', '获取用户个人信息');
INSERT INTO `bk_controller_method` VALUES ('eb8dda2d-f71c-11e6-8a88-461ab128b514', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'list', '财务用户列表');
INSERT INTO `bk_controller_method` VALUES ('eb9924c8-6226-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'list', '银行理财产品投资列表');
INSERT INTO `bk_controller_method` VALUES ('ec93074b-1cde-4830-a65d-423cd6ef6e1b', '7cc0431a-f41e-11e6-8e06-510d4a4da552', 'edit', '编辑用户个人信息');
INSERT INTO `bk_controller_method` VALUES ('ecd3ad7e-45ca-11e7-af5a-d06899c61413', '26193227-f81a-11e6-ada9-d0295a4759e7', 'netvalueDelete', '基金每日净值删除');
INSERT INTO `bk_controller_method` VALUES ('f166d70a-45d1-11e7-af5a-d06899c61413', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'netvalueEdit', '银行理财产品每日净值编辑');
INSERT INTO `bk_controller_method` VALUES ('f1bb3e37-f71c-11e6-8a88-461ab128b514', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'add', '财务用户添加');
INSERT INTO `bk_controller_method` VALUES ('f6f058e0-6226-11e7-89d2-39a48ccf689c', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'statusList', '银行理财产品投资状态列表');
INSERT INTO `bk_controller_method` VALUES ('f95d391d-f71c-11e6-8a88-461ab128b514', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'edit', '财务用户修改');
INSERT INTO `bk_controller_method` VALUES ('f9f39324-45d1-11e7-af5a-d06899c61413', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'netvalueDelete', '银行理财产品每日净值删除');
INSERT INTO `bk_controller_method` VALUES ('ff48c946-f71c-11e6-8a88-461ab128b514', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'delete', '财务用户删除');

-- ----------------------------
-- Table structure for `bk_menu`
-- ----------------------------
DROP TABLE IF EXISTS `bk_menu`;
CREATE TABLE `bk_menu` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `title` varchar(200) NOT NULL,
  `level` int(11) NOT NULL,
  `scode` varchar(20) NOT NULL,
  `pid` varchar(36) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL COMMENT '图表URL',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`name`),
  UNIQUE KEY `ak_key_3` (`scode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_menu
-- ----------------------------
INSERT INTO `bk_menu` VALUES ('2c94da6e-e9d9-11e6-9aec-ee0f7c43d97a', 'bank', '银行信息管理', '2', '00010002', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/bankInfoList.jsp', 'img/li0_0');
INSERT INTO `bk_menu` VALUES ('3a5b57c4-ee7a-11e6-b000-412b17f07060', 'roleMenu', '角色页面管理', '2', '00100014', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/roleMenuList.jsp', 'img/li1_0');
INSERT INTO `bk_menu` VALUES ('41936d9d-e9d9-11e6-9aec-ee0f7c43d97a', 'fund', '基金信息管理', '2', '00010003', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/fundList.jsp', 'img/li0_1');
INSERT INTO `bk_menu` VALUES ('5440253b-f70d-11e6-8a88-461ab128b514', 'operateOperator', '运营用户管理', '2', '00100012', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/operateOperatorList.jsp', 'img/li1_1');
INSERT INTO `bk_menu` VALUES ('5f479bd6-e9d9-11e6-9aec-ee0f7c43d97a', 'manager', '主理人信息管理', '2', '00010004', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/managerList.jsp', 'img/li0_2');
INSERT INTO `bk_menu` VALUES ('78739445-dcb5-45e1-8cd7-d700f1498dc9', 'bankFinancialProduct', '银行理财产品管理', '2', '00010005', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/bankFinancialProductList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'admin', '后台用户管理', '1', '0010', null, '', 'img/LIP1');
INSERT INTO `bk_menu` VALUES ('8f647846-f70d-11e6-8a88-461ab128b514', 'financeOperator', '财务用户管理', '2', '00100013', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/financeOperatorList.jsp', 'img/li1_2');
INSERT INTO `bk_menu` VALUES ('a449e2b1-f3ce-4ac0-9bd8-3c99c01b985a', 'permission', '用户权限管理', '1', '0002', '', '', 'img/li0_4');
INSERT INTO `bk_menu` VALUES ('b985bb5c-0f9c-11e7-a0bb-519b17a1e492', 'menu', '页面菜单信息管理', '2', '00010006', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/menuInfoList.jsp', 'img/li0_4');
INSERT INTO `bk_menu` VALUES ('bec34f69-f29e-11e6-9e84-962f283dbaeb', 'roleController', '角色功能管理', '2', '00100015', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/roleControllerList.jsp', 'img/li1_3');
INSERT INTO `bk_menu` VALUES ('c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'base', '基础数据管理', '1', '0001', null, null, 'img/LIP0');
INSERT INTO `bk_menu` VALUES ('d2433155-51cd-484e-9695-8e32e88580c3', 'controller', '功能信息管理', '2', '00010007', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/controllerInfoList.jsp', 'img/li0_5');
INSERT INTO `bk_menu` VALUES ('decf5bd2-6396-4d44-8f62-4c466cee0481', 'investor', '投资者用户管理', '2', '00100016', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/investorList.jsp', 'img/li0_2');
INSERT INTO `bk_menu` VALUES ('fab7fd70-f41e-11e6-8e06-510d4a4da552', 'superOperator', '系统管理员管理', '2', '00100011', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/superAdminList.jsp', 'img/li1_4');

-- ----------------------------
-- Table structure for `bk_operating_log`
-- ----------------------------
DROP TABLE IF EXISTS `bk_operating_log`;
CREATE TABLE `bk_operating_log` (
  `uuid` varchar(36) NOT NULL,
  `operate_type` varchar(10) NOT NULL COMMENT 'login,insert,update,delete',
  `operate_table` varchar(20) DEFAULT NULL,
  `operate_uuid` varchar(36) NOT NULL,
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `operator` varchar(36) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_operating_log
-- ----------------------------

-- ----------------------------
-- Table structure for `bk_operator`
-- ----------------------------
DROP TABLE IF EXISTS `bk_operator`;
CREATE TABLE `bk_operator` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `realname` varchar(50) NOT NULL,
  `password` varchar(256) NOT NULL,
  `role` varchar(36) NOT NULL,
  `mobile` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  `creator` varchar(36) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'normal',
  `lockedtime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`mobile`),
  UNIQUE KEY `ak_key_4` (`name`),
  KEY `fk_operator_reference_role` (`role`),
  CONSTRAINT `fk_operator_reference_role` FOREIGN KEY (`role`) REFERENCES `bk_operator_role` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_operator
-- ----------------------------
INSERT INTO `bk_operator` VALUES ('30eebddd-8471-4366-a43b-c47d2fc9453e', 'dasfdadsfa', '斯蒂芬', 'fee1ecb8f9304741125e9b1e6706fd04', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '18602214212', '', '2017-04-25 17:29:26', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'normal', null);
INSERT INTO `bk_operator` VALUES ('5a000011-c474-450c-8bfc-d6eaf400f802', 'sdafadsf', '答复', 'ae791b5560e610f11577b5c88d8e4241', '0922a25d-f57f-11e6-ac06-cacda7da5000', '13131313131', '', '2017-04-25 17:30:07', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'unopen', null);
INSERT INTO `bk_operator` VALUES ('71d6a517-8bdd-4e32-ae22-d8abb2f17cbe', '123456_#71d6a517-8bdd-4e32-ae22-d8abb2f17cbe', '123', '6582af77bf26ff4e08c3599528ecbc6b', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '18601142191_#71d6a517-8bdd-4e32-ae22-d8abb2f17cbe', '', '2017-02-20 17:26:54', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'deleted', null);
INSERT INTO `bk_operator` VALUES ('8061568a-6773-428d-8cc1-c2a6b6e70bf3', '小红aaaa_#8061568a-6773-428d-8cc1-c2a6b6e70bf3', '辣鸡', 'ae3c809e96ccfce73e3741409e33ac83', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '13838383838_#8061568a-6773-428d-8cc1-c2a6b6e70bf3', '', '2017-04-25 11:12:21', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'deleted', null);
INSERT INTO `bk_operator` VALUES ('90c6ef1b-5929-4109-9829-abc72bd9a262', 'nidaye', '你大爷', '0a9dd65bee06e606f6cab8b83fe62b33', '4ebd77bc-3025-4657-bca7-cf684647c666', '13141414141', '', '2017-04-25 17:46:35', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'normal', null);
INSERT INTO `bk_operator` VALUES ('9cb65543-5cd9-441c-9a0b-04fd02c12e60', '12312321', '1231', 'b44a1b888d99dbb22ad60fe2a7d891e4', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '18611221212', '', '2017-04-27 18:40:50', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'unopen', null);
INSERT INTO `bk_operator` VALUES ('a831432e-7083-4466-9f6b-568bc6a69eb4', 'sadfafd_#a831432e-7083-4466-9f6b-568bc6a69eb4', 'SaaS', 'e7bc54abc5f13ec3587832942fc7b994', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '18601142193_#a831432e-7083-4466-9f6b-568bc6a69eb4', '', '2017-02-20 18:19:59', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'deleted', null);
INSERT INTO `bk_operator` VALUES ('c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'rongjingfeng', '荣景峰', '60b474258188bbf0b48249e04bc8f4ef', '4ebd77bc-3025-4657-bca7-cf684647c666', '18611920344', 'rongjingfeng@zeppin.cn', '2016-09-29 09:29:09', '52a4099b-1948-4617-a84a-f6b313cd795d', 'normal', null);
INSERT INTO `bk_operator` VALUES ('dd88dba4-e49c-4e7b-aad0-5cb860ae726e', 'qinlong_#dd88dba4-e49c-4e7b-aad0-5cb860ae726e', '秦龙', 'ab9ccb33677b8baf197514d0c5e5266e', '49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6', '18600581016_#dd88dba4-e49c-4e7b-aad0-5cb860ae726e', 'qinlong@zeppin.cn', '2016-10-27 19:12:07', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'deleted', '2016-11-23 20:54:16');

-- ----------------------------
-- Table structure for `bk_operator_role`
-- ----------------------------
DROP TABLE IF EXISTS `bk_operator_role`;
CREATE TABLE `bk_operator_role` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_operator_role
-- ----------------------------
INSERT INTO `bk_operator_role` VALUES ('0127fcbe-f57f-11e6-ac06-cacda7da5000', 'operateManager', '运营经理');
INSERT INTO `bk_operator_role` VALUES ('0922a25d-f57f-11e6-ac06-cacda7da5000', 'financeEditor', '财务编辑');
INSERT INTO `bk_operator_role` VALUES ('0e15ae93-f57f-11e6-ac06-cacda7da5000', 'financeManager', '财务经理');
INSERT INTO `bk_operator_role` VALUES ('49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6', 'operateEditor', '运营编辑');
INSERT INTO `bk_operator_role` VALUES ('4ebd77bc-3025-4657-bca7-cf684647c666', 'superAdmin', '系统管理员');

-- ----------------------------
-- Table structure for `bk_role_controller_permission`
-- ----------------------------
DROP TABLE IF EXISTS `bk_role_controller_permission`;
CREATE TABLE `bk_role_controller_permission` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `controller` varchar(36) NOT NULL DEFAULT '',
  `method` varchar(36) NOT NULL DEFAULT '',
  PRIMARY KEY (`uuid`),
  KEY `fk_reference_03` (`role`),
  KEY `fk_reference_05` (`controller`),
  KEY `fk_reference_06` (`method`),
  CONSTRAINT `fk_reference_03` FOREIGN KEY (`role`) REFERENCES `bk_operator_role` (`uuid`),
  CONSTRAINT `fk_reference_05` FOREIGN KEY (`controller`) REFERENCES `bk_controller` (`uuid`),
  CONSTRAINT `fk_reference_06` FOREIGN KEY (`method`) REFERENCES `bk_controller_method` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_role_controller_permission
-- ----------------------------
INSERT INTO `bk_role_controller_permission` VALUES ('00ed2089-a75e-4fc6-bf6f-30096d302803', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '017f041b-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('0132f447-01da-46ed-b67b-00202c03b068', '4ebd77bc-3025-4657-bca7-cf684647c666', '8e1cbbb9-f35a-11e6-8a3c-ab6e287cf557', 'b0e23549-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('05b3a376-e815-479f-b098-edfbbb2a6650', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'abbb87e3-0d30-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('08b03eb1-aad9-464f-a99c-5a70655c50e7', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '1a462583-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('097076f9-144c-458f-8116-9a09b61a96b1', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '3000de6f-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('0c119834-56db-47c7-b028-476f4d747d52', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2feb6095-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('0dc85dc6-a1b8-49d5-a50d-843bfec17ee1', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '26628f5e-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('0ef3c84b-44ce-4d3a-9dd1-0c49c44df87a', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '418b0374-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('1259b39a-b59d-4c78-a433-c0fbab1a7e40', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'f95d391d-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('12be8e56-8904-40ba-a6f4-0b67eb2b7f9c', '49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'a804f9ec-ed1e-11e6-ae2e-d377d6cd7f14');
INSERT INTO `bk_role_controller_permission` VALUES ('1592e4bf-ebc3-43c2-85cd-302ecb0bf331', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', '4b8953fe-f740-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('19428375-8b26-404f-be12-2447d3396952', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'b305328a-f8dc-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('1d15f348-895a-4f0a-9f59-01cc372c7a91', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'c35c115d-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('1d3111a3-9733-4e4b-adbe-b2ee4d785659', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', 'aab7dc06-45ce-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('1ed0c4dc-5ec2-43de-bff8-5afb2585eb44', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '8a991da8-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('20a94155-89b4-47f6-833d-a3604dce0d1e', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'ade214d5-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('22ad24b6-7cfc-4b8f-ad8a-c96342e6e1c1', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '8288c144-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('232562d8-5094-48b7-bf6f-efe614acbf81', '4ebd77bc-3025-4657-bca7-cf684647c666', '47b271a9-97c2-47d2-b0a1-3da3e0c059e2', 'd87d4a9b-bdad-45a6-aab8-0915a58e85eb');
INSERT INTO `bk_role_controller_permission` VALUES ('25e337b8-2c63-46fd-aacc-4b35092d9998', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '51a4dc67-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('26be9540-49d1-458d-8673-045001b69a90', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '3007a7ec-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('28b188e9-8569-4715-b165-8979855c9d75', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '25691f5a-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('2b042689-88f7-4de9-a416-686af254a9dd', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '4c8341ee-45cf-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('2c09a8d0-c022-488d-a0ed-70ad2aabf633', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '0133e56d-45d2-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('2c1fab35-d60c-4ec6-a999-3e35ae69c052', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '8bdb03d3-ea8e-11e6-aba8-30e5e9e3b3d6');
INSERT INTO `bk_role_controller_permission` VALUES ('2c6087de-2c9e-4def-8963-17a8830f196f', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'e0d97203-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('2d53f171-eb97-4097-afef-155b6329f884', '4ebd77bc-3025-4657-bca7-cf684647c666', 'cf2e6a4b-0963-11e7-97f7-3a386a6ce01d', '2ed1a154-0964-11e7-97f7-3a386a6ce01d');
INSERT INTO `bk_role_controller_permission` VALUES ('2fc9284e-4d7a-48a5-aef2-f2fa8c59c29e', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '304ba42d-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('310b9d7a-32e5-4a20-a0f7-99559e2246bd', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '652ab2ef-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('311cda11-cdd1-41b3-ad9a-123f9bdaf752', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '6323a38a-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('3348e93c-6cd4-48db-a2ff-8dc5aa2a095f', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'f166d70a-45d1-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('33c97e91-c1c9-4002-bf5a-a19c38d31d2c', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '1170a4e9-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('34b01aa1-3f7d-4869-8cc7-ce04abd1b234', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'a804f9ec-ed1e-11e6-ae2e-d377d6cd7f14');
INSERT INTO `bk_role_controller_permission` VALUES ('3775758d-d21f-4865-949c-5a1e01c2cc9b', '4ebd77bc-3025-4657-bca7-cf684647c666', '7cc0431a-f41e-11e6-8e06-510d4a4da552', '93c9145a-b86d-4c4a-aa95-1fb997498b0e');
INSERT INTO `bk_role_controller_permission` VALUES ('3779eb5e-4eee-4778-8a7a-72c8fe0859a4', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2fe768bd-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('3d76f49b-19b6-4665-a276-df34547679d6', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '7b0d55db-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('4091ff15-4284-49b9-9b6c-a0053c917354', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'be2693d4-f8dc-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('45205dfc-7a33-4f64-9dca-1d84ca50e3d5', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', '3b5b53fa-f740-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('49a7a91c-74e0-475c-befb-1c5c01610466', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', '56da2617-f740-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('49ea6659-d794-4f3c-b74a-b542ec2dc7c4', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'f6f058e0-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('4e679a60-1474-4fa2-9bba-3851ee38df1e', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'd2aef97f-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('5050a29b-b353-494b-b26d-33d368bfa857', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', '9e2aafc5-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('50989784-a51b-49b7-ae7d-007d8fc0c91a', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '397ef2bf-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('532d104b-595b-42de-a013-c551319db984', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'b49844ef-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('55b7909b-e215-49f8-9865-8e4832f74fbe', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '1742f9b3-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('5781921e-cb37-441d-a96d-3925924182f2', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'f1bb3e37-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('57fcd46c-48f1-4f5a-a730-295724f765ec', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'b6c59f5b-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('58c04640-8a32-47e1-9fab-97c3f16d8205', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'a4cca581-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('5ad259e1-5d9f-4a93-8767-c952f8b7bd43', '4ebd77bc-3025-4657-bca7-cf684647c666', '097ffbf1-ea8f-11e6-aba8-30e5e9e3b3d6', '3f8f508b-ea8f-11e6-aba8-30e5e9e3b3d6');
INSERT INTO `bk_role_controller_permission` VALUES ('5b992eac-1940-46df-b68b-c76d1e5b7612', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'bd53d55f-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('5bfdd737-280f-44e4-942a-2b608773f8bb', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', '97dfbcc3-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('5e2404d8-df4d-4965-9be2-0a215ae11941', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '3a9c15f7-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('5eb3d806-7d0f-4215-9fad-927146f7059f', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '097ffbf1-ea8f-11e6-aba8-30e5e9e3b3d6', '3f8f508b-ea8f-11e6-aba8-30e5e9e3b3d6');
INSERT INTO `bk_role_controller_permission` VALUES ('6199b296-e401-4ee0-9369-09592c5adf54', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '46709ddd-ea8e-11e6-aba8-30e5e9e3b3d6');
INSERT INTO `bk_role_controller_permission` VALUES ('66ff8b94-3c1a-4724-a569-49c6cbf5dfcf', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd60105a-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('67657e65-f9bd-4795-afb5-3bf439bdedac', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '52d40173-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('67a8cb51-e30b-4e40-847d-db73ee9a9325', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd5bff80-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('6840f9fe-7538-4ea1-868a-1d56bfdef585', '4ebd77bc-3025-4657-bca7-cf684647c666', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', '68356af3-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('68b3b425-be20-4045-b281-18b133a0aa87', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '894359ec-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('6af5d686-b952-4059-98df-20896ab97c4d', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'd1bc30b9-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('6c1658ab-5baa-4ccd-8546-c3bed1ef478f', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'ff48c946-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('6e43f4c9-6004-40fd-9380-b3aec96b3acc', '4ebd77bc-3025-4657-bca7-cf684647c666', '7cc0431a-f41e-11e6-8e06-510d4a4da552', 'ea2bcfd4-0fef-47a9-8b23-dd5e829e3fc0');
INSERT INTO `bk_role_controller_permission` VALUES ('72bb6ed9-6e96-4b4f-8311-f805e4f28df3', '49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '04833cae-edeb-11e6-954c-704e382d3ba0');
INSERT INTO `bk_role_controller_permission` VALUES ('72be9569-674e-40db-af8b-07d5a3b3d720', '49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '1b886c10-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('73325bdc-4c46-4a43-a891-4d070edb8f21', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', 'ecd3ad7e-45ca-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('756ee113-57e9-4a4c-8297-c471832f1cf0', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'ac4c9214-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('7ae8f994-ce4a-405d-be03-22e5f0f73449', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '9e41f32b-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('7b733c70-44c9-4fd7-a007-d59c42b7db29', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '59194d3a-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('7eeeaf3e-108f-4f00-924a-18d377c34dd7', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd64c33d-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('8077bf83-380a-458e-8380-fd749273e1ee', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '495fe49f-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('80ca2f56-43fe-4012-a7f2-cf76f36a7e27', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '6f83a415-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('80f61141-3925-4422-b07d-7be9c55c0d98', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '7147f331-f8d7-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('8198c31d-f617-41af-9e0c-894db066c44e', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '26193227-f81a-11e6-ada9-d0295a4759e7', '418b0374-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('841ae8b2-6f0e-4943-a47b-4d7a78485e17', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '4e4b1c92-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('85b98ce0-5919-4149-8e61-9a360f242a3f', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '51a4dc67-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('87ceaf81-272f-4388-8d6f-259705b8789b', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'de1833a2-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('882730cb-5158-4d0a-8429-fe5625c5606a', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '4a0e1f69-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('88aaa4b9-ee8a-4819-ba25-d056846656ac', '4ebd77bc-3025-4657-bca7-cf684647c666', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', '73224aba-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('8be42128-429c-45e3-9dda-a91aa0be36bf', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '97ad9df0-f359-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('8c0fdc53-5cb6-4b90-9032-ba577e2c413e', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', '90b48cf1-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('8c145e1f-dd97-4c7c-9586-c8e743bef389', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '551e1dd4-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('8e57bdc8-a854-4c6e-b85d-2badfdbbaa15', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2ff869d3-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('8fe89e80-0754-421d-888e-6c7cded5c845', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd58fe5d-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('9356aeab-dbdc-407c-be47-0df3c1fe76fe', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', 'e3f24925-45ca-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('94ab8b04-7564-4698-9cdb-693604794e98', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '300c9227-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('951446ad-4d95-4f05-86b7-8fb2be03bce8', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '534f95b7-45cf-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('965f876b-11ac-49db-bab2-267a8b47a82b', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '3b219445-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('978c0973-a947-480b-a95f-00b90462eaaa', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '417a80ff-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('97e1dcb5-1bf8-4fb9-9304-e43991d0eed2', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '3b219445-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('9b8f6274-3bc3-4205-bb69-697e0d34aeb7', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'e30cedc9-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('9f25f23f-84b5-4b73-b0ff-8b81f1e85d11', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'abb3c663-0d30-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('a0047db0-e9f4-4712-84fe-9f9452d1b209', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'c1bf7b33-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('a10428ef-2db1-4a67-8931-a5b02dbb3813', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '4a0e1f69-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('a272be11-3dbb-4f35-bc48-e84237840c8e', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2fdddbdc-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('a3f38cd8-870a-45da-b9fe-6d5f85d27580', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '52a4dfe2-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('a44c577c-0115-4d17-b35a-f9cd3a528202', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd45c198-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('a72a9b32-d2dd-48ac-bed4-b5a3b282ba35', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'eb8dda2d-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('ab359ee4-07d9-4051-8791-c5b29b1a54fc', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '946fa323-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('ad47c2e5-1629-45df-a66c-082d3d18ce6c', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'd65aa0ea-f359-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('b06e05a2-d0b5-40c1-aaf1-2d2d02aefce6', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '82c6e003-f8d7-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('b4dde94c-1e95-4e49-af2a-6f6909b0cf1a', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '0a33aea5-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('b6e6eea7-f657-4b39-993d-b8406e0860dd', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', 'a7ac4d0a-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('b744280b-d58f-42fb-b532-c3ab295df2d1', '4ebd77bc-3025-4657-bca7-cf684647c666', '47b271a9-97c2-47d2-b0a1-3da3e0c059e2', '32295d11-d983-4d8f-9823-ae184e53b41b');
INSERT INTO `bk_role_controller_permission` VALUES ('b99d6aed-3588-40a8-af53-042ef42e104e', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2ff273b0-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('b9defdfa-a5f7-420f-ac4c-d10dc4139658', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '39fd0144-62e0-11e7-8018-127d3cb7df39');
INSERT INTO `bk_role_controller_permission` VALUES ('bf4e6b2f-2758-4332-8cb3-3194b1c353b7', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ffe60503-f359-11e6-8a3c-ab6e287cf557', '0ed32dc1-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('c0cb1d74-d209-4e41-b054-1565562188a3', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', 'b0aa4848-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('c4c2e0cb-225e-4bac-bf05-dbbdca20504a', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '445c79aa-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('c80a9427-6ff8-45e5-9b3d-02719078fa60', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2fee920f-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('c8d7ba30-df62-4f85-976a-c8977f84b669', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'f9f39324-45d1-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('cb5c0a07-0b5a-40a8-9d42-a2c05955a14f', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'ca6684da-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('cc0eeb7d-2a7f-4c69-b53e-1722023a9d5c', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'ce703d25-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('cc23e0fa-ab73-413d-ab83-81baec46d95f', '4ebd77bc-3025-4657-bca7-cf684647c666', '8e1cbbb9-f35a-11e6-8a3c-ab6e287cf557', 'a6d0db7c-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('cd078abb-1736-4b00-bd4c-f64a6ae07e8b', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '4966a6b2-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('cd7bc10e-0924-436b-97ca-c8667ff1c6f8', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '04833cae-edeb-11e6-954c-704e382d3ba0');
INSERT INTO `bk_role_controller_permission` VALUES ('ceccb4fa-269d-4df9-994a-f64aa320a060', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '1b886c10-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('cf7fa317-6668-482e-89ff-826c81c1256c', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '7a916418-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('d0a1a927-276a-408d-befe-f810ea87056a', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '4dac7e6c-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('d191a648-a26d-4f00-abbb-288c0e17e31d', '4ebd77bc-3025-4657-bca7-cf684647c666', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', '5b54c74c-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('d691042d-8c3d-4919-96d8-9ce9eb06ec53', '49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6', '097ffbf1-ea8f-11e6-aba8-30e5e9e3b3d6', '3f8f508b-ea8f-11e6-aba8-30e5e9e3b3d6');
INSERT INTO `bk_role_controller_permission` VALUES ('d9ec4947-f2fd-4381-8f17-d21948b0e429', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ffe60503-f359-11e6-8a3c-ab6e287cf557', '1b568f79-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('da22e555-69d5-4afd-80ba-1090a2d300f3', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'c13e8dd0-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('da76fa49-6bd0-4864-9975-d76641301e9d', '4ebd77bc-3025-4657-bca7-cf684647c666', '7cc0431a-f41e-11e6-8e06-510d4a4da552', 'ec93074b-1cde-4830-a65d-423cd6ef6e1b');
INSERT INTO `bk_role_controller_permission` VALUES ('deeab81c-73c0-42c4-8890-d39ff8449f00', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '816d0baa-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('dfde7874-3863-4279-b03f-d4975bb7914a', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '1f6f13e7-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('e4480834-e62d-412c-b6a2-d3662ea64621', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '3b9938b3-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('efe70ed4-167e-4695-93f2-0b12669edf69', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '5f79c327-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('f1180b63-e73c-430d-8152-cd2316085636', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '30120889-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('f7091c26-0a7b-47f4-997d-868437516803', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '417a80ff-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('fb67d891-f7f3-4c0d-be26-6bbe098381b4', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '59761e39-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('fc061aa9-bb98-4a37-b498-6634ef3c17ae', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'a7ea6cb3-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('fde2daa5-1ac9-40f1-bf3f-5786a8c96592', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'eb9924c8-6226-11e7-89d2-39a48ccf689c');

-- ----------------------------
-- Table structure for `bk_role_menu_permission`
-- ----------------------------
DROP TABLE IF EXISTS `bk_role_menu_permission`;
CREATE TABLE `bk_role_menu_permission` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `menu` varchar(36) NOT NULL,
  `alias` varchar(100) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`role`,`menu`),
  KEY `fk_reference_4` (`menu`),
  CONSTRAINT `fk_reference_4` FOREIGN KEY (`menu`) REFERENCES `bk_menu` (`uuid`),
  CONSTRAINT `fk_reference_5` FOREIGN KEY (`role`) REFERENCES `bk_operator_role` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bk_role_menu_permission
-- ----------------------------
INSERT INTO `bk_role_menu_permission` VALUES ('002f5bd1-5700-43db-8ba5-e928601b21e1', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '41936d9d-e9d9-11e6-9aec-ee0f7c43d97a', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('098d522b-4494-43be-8bba-38f5461e9e9c', '0127fcbe-f57f-11e6-ac06-cacda7da5000', 'd2433155-51cd-484e-9695-8e32e88580c3', null, '6');
INSERT INTO `bk_role_menu_permission` VALUES ('11042e2e-034d-4481-8f5f-2ebf8177d585', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('12a8ba4a-c22d-4adf-a4da-e683861e95e5', '4ebd77bc-3025-4657-bca7-cf684647c666', '3a5b57c4-ee7a-11e6-b000-412b17f07060', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('14ce6a08-141c-4fe5-b438-edec1cab0280', '4ebd77bc-3025-4657-bca7-cf684647c666', '5440253b-f70d-11e6-8a88-461ab128b514', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('195e5780-1d19-44c3-8fc9-07e858ad8c1d', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '78739445-dcb5-45e1-8cd7-d700f1498dc9', null, '4');
INSERT INTO `bk_role_menu_permission` VALUES ('1a6f2ae4-f54d-4915-9b68-d5ee4b9d986b', '0127fcbe-f57f-11e6-ac06-cacda7da5000', 'b985bb5c-0f9c-11e7-a0bb-519b17a1e492', null, '5');
INSERT INTO `bk_role_menu_permission` VALUES ('1cf91129-5ab3-4bdd-881b-173c2edf61c6', '4ebd77bc-3025-4657-bca7-cf684647c666', 'd2433155-51cd-484e-9695-8e32e88580c3', null, '6');
INSERT INTO `bk_role_menu_permission` VALUES ('1f4c5a13-a79f-403c-a05e-8a6282e229e7', '4ebd77bc-3025-4657-bca7-cf684647c666', 'b985bb5c-0f9c-11e7-a0bb-519b17a1e492', null, '5');
INSERT INTO `bk_role_menu_permission` VALUES ('2a886311-d1fb-480e-8645-f683ab418b7f', '4ebd77bc-3025-4657-bca7-cf684647c666', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('2e16415c-90a2-421f-95f6-79e4f22f0d68', '0127fcbe-f57f-11e6-ac06-cacda7da5000', 'decf5bd2-6396-4d44-8f62-4c466cee0481', null, '5');
INSERT INTO `bk_role_menu_permission` VALUES ('2e2cf792-89c3-46a7-97a3-6cabff92f9e8', '0127fcbe-f57f-11e6-ac06-cacda7da5000', 'a449e2b1-f3ce-4ac0-9bd8-3c99c01b985a', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('36715ba2-1ec2-42e8-a68e-40f56df73090', '4ebd77bc-3025-4657-bca7-cf684647c666', '41936d9d-e9d9-11e6-9aec-ee0f7c43d97a', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('5c9e74a7-ca8f-4045-afbc-85f71a3378fd', '0e15ae93-f57f-11e6-ac06-cacda7da5000', 'fab7fd70-f41e-11e6-8e06-510d4a4da552', null, '5');
INSERT INTO `bk_role_menu_permission` VALUES ('602819d5-e8fb-47c0-80c2-2c6644148e42', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '5440253b-f70d-11e6-8a88-461ab128b514', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('6b0969ea-8470-497e-8491-37b2f83b22fa', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('70e9d88c-ca13-4ccc-8457-82dc8df069e6', '0e15ae93-f57f-11e6-ac06-cacda7da5000', 'bec34f69-f29e-11e6-9e84-962f283dbaeb', null, '4');
INSERT INTO `bk_role_menu_permission` VALUES ('905ff2eb-aa42-4db0-ad44-c55701eafec4', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '8f647846-f70d-11e6-8a88-461ab128b514', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('9a0b4232-5b91-42e6-a657-32b734096ca5', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '3a5b57c4-ee7a-11e6-b000-412b17f07060', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('9d2b9ee6-6ed8-461c-ab29-b45bae5e81bc', '4ebd77bc-3025-4657-bca7-cf684647c666', 'bec34f69-f29e-11e6-9e84-962f283dbaeb', null, '4');
INSERT INTO `bk_role_menu_permission` VALUES ('a95134cf-fcaa-4083-b47b-b130e093074d', '0127fcbe-f57f-11e6-ac06-cacda7da5000', 'bec34f69-f29e-11e6-9e84-962f283dbaeb', null, '4');
INSERT INTO `bk_role_menu_permission` VALUES ('b06b92e8-4a09-41c6-93ff-b3d6ad785da0', '4ebd77bc-3025-4657-bca7-cf684647c666', '5f479bd6-e9d9-11e6-9aec-ee0f7c43d97a', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('ca89e2ad-8781-4df3-85b0-490d60c13646', '4ebd77bc-3025-4657-bca7-cf684647c666', '8f647846-f70d-11e6-8a88-461ab128b514', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('cd18e860-03db-423c-8ea3-54e80ac84569', '0127fcbe-f57f-11e6-ac06-cacda7da5000', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('d1232fe0-7b72-4d46-b7cd-d8e213fa8173', '4ebd77bc-3025-4657-bca7-cf684647c666', '2c94da6e-e9d9-11e6-9aec-ee0f7c43d97a', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('d55db94e-765e-4b9c-a0fb-17a927fbf253', '4ebd77bc-3025-4657-bca7-cf684647c666', '78739445-dcb5-45e1-8cd7-d700f1498dc9', null, '4');
INSERT INTO `bk_role_menu_permission` VALUES ('db211f74-7df1-457f-ad41-236996bc1af0', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fab7fd70-f41e-11e6-8e06-510d4a4da552', null, '6');
INSERT INTO `bk_role_menu_permission` VALUES ('dbca2767-947d-405f-8bfd-103ca0e76589', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('dfd8cca4-0744-44b8-a980-32611679f22d', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '3a5b57c4-ee7a-11e6-b000-412b17f07060', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('e3f418b2-4659-4602-aa42-50eef3784a93', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '2c94da6e-e9d9-11e6-9aec-ee0f7c43d97a', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('e567e6db-1e4b-4cbf-ab70-a242c1e60346', '0127fcbe-f57f-11e6-ac06-cacda7da5000', 'fab7fd70-f41e-11e6-8e06-510d4a4da552', null, '6');
INSERT INTO `bk_role_menu_permission` VALUES ('f4a7982f-1d9a-4bbc-8ef1-2249d56372bb', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '5f479bd6-e9d9-11e6-9aec-ee0f7c43d97a', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('f65536d7-0a89-4afd-a855-043b4d7d530a', '4ebd77bc-3025-4657-bca7-cf684647c666', 'decf5bd2-6396-4d44-8f62-4c466cee0481', null, '5');
INSERT INTO `bk_role_menu_permission` VALUES ('f6e12e71-e91a-4685-ab23-ac5b1044d9a2', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '8f647846-f70d-11e6-8a88-461ab128b514', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('fd1434f8-b74f-425f-a579-7418a7cb3652', '0e15ae93-f57f-11e6-ac06-cacda7da5000', '5440253b-f70d-11e6-8a88-461ab128b514', null, '2');

-- ----------------------------
-- Table structure for `branch_bank`
-- ----------------------------
DROP TABLE IF EXISTS `branch_bank`;
CREATE TABLE `branch_bank` (
  `uuid` varchar(36) NOT NULL,
  `bank` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `status` varchar(20) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `fk_reference_32` (`bank`),
  CONSTRAINT `fk_reference_32` FOREIGN KEY (`bank`) REFERENCES `bank` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of branch_bank
-- ----------------------------
INSERT INTO `branch_bank` VALUES ('0b163682-9abf-48d1-91a8-a69723b80fa5', 'af0c3380-d436-4783-a0e5-ce56c00c07b3', 'EWQE', 'sdafasdfasfadsfadsf', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-04-19 10:21:40');
INSERT INTO `branch_bank` VALUES ('630aef77-e154-11e6-bec8-7ce91bcbaeef', '630aef77-e154-11e6-bec8-7ce91bcbaaef', 'hehe', 'hehe', 'deleted', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-08 18:08:28');
INSERT INTO `branch_bank` VALUES ('cb91fa22-76ff-4459-80b5-f231d9c9cfee', 'af0c3380-d436-4783-a0e5-ce56c00c07b3', 'ewqR', 'RQ', 'deleted', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-08 18:49:14');
INSERT INTO `branch_bank` VALUES ('fe6f9650-6e50-4e69-b767-270bfb771f46', 'af0c3380-d436-4783-a0e5-ce56c00c07b3', '切勿1', '请问', 'disable', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-08 19:03:02');

-- ----------------------------
-- Table structure for `fund`
-- ----------------------------
DROP TABLE IF EXISTS `fund`;
CREATE TABLE `fund` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `scode` varchar(100) NOT NULL,
  `shortname` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'normal' COMMENT 'normal,stopped,deleted',
  `gp` varchar(50) DEFAULT NULL,
  `custodian` varchar(50) DEFAULT NULL,
  `flag_structured` tinyint(1) DEFAULT NULL,
  `structured_type` varchar(20) DEFAULT NULL,
  `structured_remark` varchar(1000) DEFAULT NULL,
  `style` varchar(20) DEFAULT NULL,
  `risk_level` varchar(20) DEFAULT NULL,
  `credit_level` varchar(20) DEFAULT NULL,
  `performance_level` varchar(30) DEFAULT NULL,
  `flag_purchase` tinyint(1) DEFAULT NULL COMMENT 'open,close',
  `flag_redemption` tinyint(1) DEFAULT NULL,
  `planing_scale` decimal(20,8) DEFAULT NULL,
  `actual_scale` decimal(20,8) DEFAULT NULL,
  `gp_purchase_scale` decimal(20,8) DEFAULT NULL,
  `lastest_scale` decimal(20,8) DEFAULT NULL,
  `setuptime` datetime DEFAULT NULL,
  `collect_starttime` datetime DEFAULT NULL,
  `collect_endtime` datetime DEFAULT NULL,
  `purchase_starttime` datetime DEFAULT NULL,
  `purchase_endtime` datetime DEFAULT NULL,
  `goal` varchar(1000) DEFAULT NULL,
  `invest_idea` varchar(1000) DEFAULT NULL,
  `invest_scope` varchar(1000) DEFAULT NULL,
  `invest_staregy` varchar(1000) DEFAULT NULL,
  `invest_standard` varchar(1000) DEFAULT NULL,
  `revenue_feature` varchar(1000) DEFAULT NULL,
  `risk_management` varchar(1000) DEFAULT NULL,
  `net_worth` decimal(20,8) DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `ak_key_2` (`scode`),
  KEY `fk_reference_20` (`creator`),
  CONSTRAINT `fk_reference_20` FOREIGN KEY (`creator`) REFERENCES `bk_operator` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund
-- ----------------------------
INSERT INTO `fund` VALUES ('03548b93-2c41-47e5-ac9c-a5fb19d9f64c', 'sdfasdfa', '110101', '简称', 'bond', 'checked', '', '', '0', 'priority', '', 'profit', 'R1', 'high', '1', null, null, '100000000.00000000', '0.00000000', null, null, '2017-02-07 00:00:00', '2017-02-08 00:00:00', '2017-02-09 23:59:59', '2017-02-07 00:00:00', '2017-02-13 23:59:59', '', '', '', '', '', '', '', '1.00000000', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-23 16:09:15');
INSERT INTO `fund` VALUES ('095d29ba-ba1a-4a39-ae72-d96ebf2ac615', 'asdf', 'sadf', '仨', 'currency', 'unchecked', '', 'asd', '0', 'priority', '', 'profit', 'R1', 'high', '1', null, null, '0.00000000', null, null, null, '2017-05-04 00:00:00', '2017-05-17 00:00:00', '2017-05-05 23:59:59', '2017-05-09 00:00:00', '2017-05-06 23:59:59', '', '', '', '', '', '', '', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-05-31 18:44:57');
INSERT INTO `fund` VALUES ('3b43e276-88f4-4748-8c91-9bde5f076fb0', '123', '123', '123', 'currency', 'unchecked', '1', '', '0', 'priority', '', 'profit', 'R1', 'high', '1', null, null, '0.00000000', null, null, null, '2017-07-05 00:00:00', '2017-07-03 00:00:00', '2017-07-04 00:00:00', '2017-07-04 00:00:00', '2017-07-21 00:00:00', '&lt;p&gt;123&lt;/p&gt;', '', '&lt;p&gt;123&lt;/p&gt;', '&lt;p&gt;123&lt;/p&gt;', '&lt;p&gt;123&lt;/p&gt;', '&lt;p&gt;123&lt;/p&gt;', '&lt;p&gt;123&lt;/p&gt;', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-07-04 10:34:13');
INSERT INTO `fund` VALUES ('5afd992f-871d-4bec-93c7-e6d730c9d3d0', '11', '1', '11', 'currency', 'unchecked', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-13 12:10:08');
INSERT INTO `fund` VALUES ('5ebd4fa1-1974-4005-b2c2-69aeb039db38', 'ASD', 'ASD', 'ASD', 'currency', 'unchecked', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-21 17:53:34');
INSERT INTO `fund` VALUES ('789b5dfb-b922-4ce2-a0e1-bf8653d4b83f', 'ASDF', 'ASDF', 'AF', 'currency', 'unchecked', '', '', '0', 'priority', '', 'profit', 'R1', 'high', '1', null, null, '0.00000000', null, null, null, '2017-06-07 00:00:00', '2017-06-06 00:00:00', '2017-06-16 23:59:59', '2017-06-28 00:00:00', '2017-06-29 23:59:59', '&lt;p&gt;SDFA&lt;/p&gt;', '&lt;p&gt;SDFA&lt;/p&gt;', '&lt;p&gt;ASDF&lt;/p&gt;', '&lt;p&gt;SDAF&lt;/p&gt;', '&lt;p&gt;ASDF&lt;/p&gt;', '&lt;p&gt;ASDF&lt;/p&gt;', '&lt;p&gt;ASDF&lt;/p&gt;', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-21 17:59:33');
INSERT INTO `fund` VALUES ('92ecc658-5ae3-4a49-84d0-f477fa3632db', 'saAA', 'SA', 'SA', 'currency', 'unpassed', '', '', '0', 'priority', '', 'profit', 'R1', 'high', '1', null, null, '123.00000000', '123.00000000', null, null, '2016-11-29 00:00:00', '2016-10-31 00:00:00', '2016-12-26 23:59:59', '2017-01-31 00:00:00', '2017-02-07 23:59:59', '', '', '', '', '', '', '', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-22 15:07:30');
INSERT INTO `fund` VALUES ('b48e1653-b96f-44f7-9861-5d08e5d33731', 'ASD', 'ASD', 'asFS', 'currency', 'unchecked', '', 'QWE', '0', 'priority', '', 'profit', 'R1', 'high', '1', null, null, '0.00000000', null, null, null, '2017-06-07 00:00:00', '2017-06-06 00:00:00', '2017-06-16 23:59:59', '2017-06-07 00:00:00', '2017-06-21 23:59:59', '&lt;p&gt;QWE&lt;/p&gt;', '&lt;p&gt;QWE&lt;/p&gt;', '&lt;p&gt;QWE&lt;/p&gt;', '&lt;p&gt;QWE&lt;/p&gt;', '&lt;p&gt;QWE&lt;/p&gt;', '&lt;p&gt;QWE&lt;/p&gt;', '&lt;p&gt;QWE&lt;/p&gt;', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-21 17:51:18');
INSERT INTO `fund` VALUES ('bac15e3f-08b2-482b-8fd8-ca5eab90715c', 'QWEQW', 'EWQE', 'EWQE', 'currency', 'unchecked', '', '', '0', 'priority', '', 'profit', 'R1', 'high', '1', null, null, '0.00000000', null, null, null, '2017-06-14 00:00:00', '2017-06-06 00:00:00', '2017-06-29 23:59:59', '2017-06-13 00:00:00', '2017-06-30 23:59:59', '', '', '', '', '', '', '', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-21 18:09:35');
INSERT INTO `fund` VALUES ('ebd28215-8a2c-41ce-8967-0af8936fc7e6', '123123', '213', '123', 'currency', 'unchecked', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-15 14:45:28');
INSERT INTO `fund` VALUES ('f39744fe-08ce-4435-885c-4587d27c315d', 'AAA', 'A', 'AAA', 'currency', 'unchecked', '', '', '0', 'priority', '', 'profit', 'R1', 'high', '1', null, null, '0.00000000', null, null, null, '2017-06-07 00:00:00', '2017-06-05 00:00:00', '2017-06-23 23:59:59', '2017-06-22 00:00:00', '2017-06-29 23:59:59', '', '', '', '', '', '', '', null, 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-06-21 18:04:32');

-- ----------------------------
-- Table structure for `fund_daily`
-- ----------------------------
DROP TABLE IF EXISTS `fund_daily`;
CREATE TABLE `fund_daily` (
  `uuid` varchar(36) NOT NULL,
  `fund` varchar(36) NOT NULL,
  `netvalue` decimal(16,8) NOT NULL,
  `statistime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createtime` datetime NOT NULL COMMENT '净值录入时间',
  `creator` varchar(36) NOT NULL COMMENT '录入人',
  PRIMARY KEY (`uuid`),
  KEY `ak_key_2` (`fund`) USING BTREE,
  CONSTRAINT `fk_reference_23` FOREIGN KEY (`fund`) REFERENCES `fund` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_daily
-- ----------------------------
INSERT INTO `fund_daily` VALUES ('0a0a1864-ff44-4a59-bcd1-aaf7a466a86c', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-02 00:00:00', '2017-05-31 16:02:03', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('257c29bb-4a65-4944-849c-04f7153c728a', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-06 00:00:00', '2017-05-31 16:02:26', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('3302132a-3fbe-4d36-aef5-aa8e4dd495f0', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-03 00:00:00', '2017-05-31 16:02:10', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('6bd94db4-7606-49ff-b4ed-a17965d00ab6', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-04 00:00:00', '2017-05-31 16:02:15', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('8a595628-4087-4e21-914b-5b998aab77d7', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-09 00:00:00', '2017-05-31 16:02:42', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('a1e44b4e-3be7-4cbb-aa7f-d0e47a392180', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-10 00:00:00', '2017-05-31 16:02:47', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('adcba259-181d-4720-93e8-6a151143cf5d', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '123.00000000', '2017-05-31 00:00:00', '2017-05-31 14:45:34', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('b1d8d7b9-530f-4584-8b9c-eb6152556f34', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-07 00:00:00', '2017-05-31 16:02:31', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('dab18a06-b9ad-485a-a95f-daa2dd4e85e3', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-05 00:00:00', '2017-05-31 16:02:21', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('e01b4497-0973-4526-99a5-d0cb9c1b6555', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-08 00:00:00', '2017-05-31 16:02:37', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');
INSERT INTO `fund_daily` VALUES ('eb75ed9a-54f3-41d4-8562-6df739eebc67', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', '1.00000000', '2017-05-01 00:00:00', '2017-05-31 16:01:58', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3');

-- ----------------------------
-- Table structure for `fund_manager`
-- ----------------------------
DROP TABLE IF EXISTS `fund_manager`;
CREATE TABLE `fund_manager` (
  `uuid` varchar(36) NOT NULL,
  `manager` varchar(36) NOT NULL,
  `fund` varchar(36) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`manager`,`fund`),
  KEY `fk_reference_11` (`fund`),
  CONSTRAINT `fk_reference_10` FOREIGN KEY (`manager`) REFERENCES `manager` (`uuid`),
  CONSTRAINT `fk_reference_11` FOREIGN KEY (`fund`) REFERENCES `fund` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_manager
-- ----------------------------

-- ----------------------------
-- Table structure for `fund_rate`
-- ----------------------------
DROP TABLE IF EXISTS `fund_rate`;
CREATE TABLE `fund_rate` (
  `uuid` varchar(36) NOT NULL,
  `fund` varchar(36) NOT NULL,
  `type` varchar(10) NOT NULL COMMENT '百分比/定额',
  `upperlimit` decimal(20,8) NOT NULL,
  `lowlimit` decimal(20,8) NOT NULL,
  `rate` decimal(20,8) NOT NULL,
  `openrate` decimal(20,8) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `fk_reference_9` (`fund`),
  CONSTRAINT `fk_reference_9` FOREIGN KEY (`fund`) REFERENCES `fund` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_rate
-- ----------------------------
INSERT INTO `fund_rate` VALUES ('2a711efa-b114-4076-a003-f46f9e6d5fc8', '3b43e276-88f4-4748-8c91-9bde5f076fb0', 'apply', '0.00000000', '0.00000000', '0.00000000', '0.00000000');
INSERT INTO `fund_rate` VALUES ('6673cb7f-b462-4977-b6a5-8b4c01266132', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', 'buy', '1000.00000000', '100.00000000', '0.06000000', '0.03000000');
INSERT INTO `fund_rate` VALUES ('6ad1e7f1-d05f-48a4-9630-8278c84a3fdf', 'bac15e3f-08b2-482b-8fd8-ca5eab90715c', 'apply', '0.00000000', '0.00000000', '0.00000000', '0.00000000');
INSERT INTO `fund_rate` VALUES ('70cfcc14-3306-4bad-b024-2932fff277cf', '03548b93-2c41-47e5-ac9c-a5fb19d9f64c', 'buy', '100.00000000', '10.00000000', '0.08000000', '0.04000000');
INSERT INTO `fund_rate` VALUES ('93b63816-f6ae-441f-b1f6-51e067f2227a', 'bac15e3f-08b2-482b-8fd8-ca5eab90715c', 'redeem', '0.00000000', '0.00000000', '0.00000000', '0.00000000');

-- ----------------------------
-- Table structure for `investor`
-- ----------------------------
DROP TABLE IF EXISTS `investor`;
CREATE TABLE `investor` (
  `uuid` varchar(36) NOT NULL,
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实名称',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `email` varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  `login_password` varchar(300) NOT NULL COMMENT '登录密码',
  `secret_password` varchar(300) DEFAULT NULL COMMENT '支付密码',
  `third_payment_account` varchar(50) DEFAULT NULL COMMENT '第三方支付账号',
  `third_payment_password` varchar(300) DEFAULT NULL COMMENT '第三方支付密码',
  `status` varchar(10) NOT NULL,
  `binding_mobile_flag` tinyint(1) NOT NULL COMMENT '是否绑定手机',
  `binding_email_flag` tinyint(1) NOT NULL COMMENT '是否绑定邮箱',
  `realname_auth_flag` tinyint(1) NOT NULL COMMENT '是否实名认证',
  `secret_password_flag` tinyint(1) NOT NULL COMMENT '是否设置支付密码',
  `total_amount` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '账户总资产',
  `total_return` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '历史总收益',
  `account_balance` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '账户余额',
  `createtime` datetime NOT NULL,
  `referrer` varchar(36) DEFAULT NULL COMMENT '推荐人',
  `regist_source` varchar(36) NOT NULL COMMENT '注册来源',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(30) DEFAULT NULL COMMENT '最后登录IP',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_3` (`mobile`),
  UNIQUE KEY `ak_key_2` (`idcard`),
  UNIQUE KEY `ak_key_4` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of investor
-- ----------------------------
INSERT INTO `investor` VALUES ('5f32ccff-1694-11e7-ae32-88870ca723b7', '忍者神龟', '朱克鑫', '370681198902201687', '18601142193', '12346@qq.com', '123', '123', '', '', 'normal', '1', '1', '1', '0', '3000000.00000000', '10000.00000000', '8888888.00000000', '2017-04-01 12:29:27', '', '1', '2017-04-01 12:45:46', '');

-- ----------------------------
-- Table structure for `investor_account_daily`
-- ----------------------------
DROP TABLE IF EXISTS `investor_account_daily`;
CREATE TABLE `investor_account_daily` (
  `uuid` varchar(36) NOT NULL,
  `investor` varchar(36) NOT NULL COMMENT '投资用户',
  `date` datetime NOT NULL COMMENT '日期时间',
  `total_invest_amount` decimal(20,8) NOT NULL COMMENT '当前投资总金额',
  `total_recailm_amount` decimal(20,8) NOT NULL COMMENT '当前收回总金额',
  `total_returns` decimal(20,8) NOT NULL COMMENT '当前总收益',
  `total_returns_rate` decimal(16,8) NOT NULL COMMENT '累计收益率',
  `statistime` datetime NOT NULL COMMENT '统计时间',
  PRIMARY KEY (`uuid`),
  KEY `fk_reference_33` (`investor`),
  CONSTRAINT `fk_reference_33` FOREIGN KEY (`investor`) REFERENCES `investor` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of investor_account_daily
-- ----------------------------

-- ----------------------------
-- Table structure for `investor_account_details`
-- ----------------------------
DROP TABLE IF EXISTS `investor_account_details`;
CREATE TABLE `investor_account_details` (
  `uuid` varchar(36) NOT NULL,
  `investor` varchar(36) DEFAULT NULL COMMENT '投资用户',
  `datetime` datetime NOT NULL COMMENT '日期时间',
  `type` varchar(20) NOT NULL COMMENT '操作类型',
  `income` decimal(20,8) DEFAULT NULL COMMENT '收入',
  `pay` decimal(20,8) DEFAULT NULL COMMENT '支出',
  `account_balance` decimal(20,8) NOT NULL COMMENT '账户余额',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `bankcard` varchar(36) DEFAULT NULL COMMENT '银行卡',
  `status` varchar(10) NOT NULL COMMENT '状态',
  PRIMARY KEY (`uuid`),
  KEY `index_1` (`datetime`),
  KEY `fk_reference_24` (`investor`),
  KEY `fk_reference_29` (`bankcard`),
  CONSTRAINT `fk_reference_24` FOREIGN KEY (`investor`) REFERENCES `investor` (`uuid`),
  CONSTRAINT `fk_reference_29` FOREIGN KEY (`bankcard`) REFERENCES `investor_bankcard` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of investor_account_details
-- ----------------------------

-- ----------------------------
-- Table structure for `investor_bankcard`
-- ----------------------------
DROP TABLE IF EXISTS `investor_bankcard`;
CREATE TABLE `investor_bankcard` (
  `uuid` varchar(36) NOT NULL,
  `investor` varchar(36) NOT NULL,
  `bank` varchar(36) NOT NULL COMMENT '开户银行',
  `branch_bank` varchar(36) NOT NULL COMMENT '开户支行',
  `bank_account_name` varchar(50) NOT NULL COMMENT '开户名',
  `binding_bank_card` varchar(30) NOT NULL COMMENT '银行卡号',
  `bandingtime` datetime NOT NULL COMMENT '绑定时间',
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`binding_bank_card`),
  KEY `fk_reference_27` (`investor`),
  KEY `fk_reference_36` (`branch_bank`),
  KEY `fk_reference_37` (`bank`),
  CONSTRAINT `fk_reference_27` FOREIGN KEY (`investor`) REFERENCES `investor` (`uuid`),
  CONSTRAINT `fk_reference_36` FOREIGN KEY (`branch_bank`) REFERENCES `branch_bank` (`uuid`),
  CONSTRAINT `fk_reference_37` FOREIGN KEY (`bank`) REFERENCES `bank` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of investor_bankcard
-- ----------------------------

-- ----------------------------
-- Table structure for `investor_jyb_investment`
-- ----------------------------
DROP TABLE IF EXISTS `investor_jyb_investment`;
CREATE TABLE `investor_jyb_investment` (
  `uuid` varchar(36) NOT NULL,
  `investor` varchar(36) NOT NULL,
  `jyb` varchar(36) NOT NULL,
  `total_amount` decimal(20,8) NOT NULL,
  `total_shares` decimal(20,8) NOT NULL,
  `per_shares_cost` decimal(16,8) NOT NULL,
  `current_netvalue` decimal(16,8) NOT NULL,
  `total_invest_amount` decimal(20,8) NOT NULL,
  `total_recailm_amount` decimal(20,8) NOT NULL,
  `returns` decimal(20,8) NOT NULL,
  `returns_rate` decimal(16,8) NOT NULL,
  `statistime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`investor`,`jyb`),
  KEY `fk_reference_30` (`jyb`),
  CONSTRAINT `fk_reference_30` FOREIGN KEY (`jyb`) REFERENCES `jyb` (`uuid`),
  CONSTRAINT `fk_reference_31` FOREIGN KEY (`investor`) REFERENCES `investor` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of investor_jyb_investment
-- ----------------------------

-- ----------------------------
-- Table structure for `investor_jyb_trade_record`
-- ----------------------------
DROP TABLE IF EXISTS `investor_jyb_trade_record`;
CREATE TABLE `investor_jyb_trade_record` (
  `uuid` varchar(36) NOT NULL,
  `datetime` datetime NOT NULL,
  `investor` varchar(36) NOT NULL,
  `jyb` varchar(36) NOT NULL,
  `type` varchar(10) NOT NULL,
  `account_details` varchar(36) NOT NULL,
  `trade_amount` decimal(20,8) NOT NULL,
  `trade_shares` decimal(16,8) NOT NULL,
  `trade_netvalue` decimal(16,8) NOT NULL,
  `status` varchar(10) NOT NULL COMMENT '是否投资成功\r\n            ',
  PRIMARY KEY (`uuid`),
  KEY `fk_reference_25` (`investor`),
  KEY `fk_reference_26` (`account_details`),
  KEY `fk_reference_28` (`jyb`),
  CONSTRAINT `fk_reference_25` FOREIGN KEY (`investor`) REFERENCES `investor` (`uuid`),
  CONSTRAINT `fk_reference_26` FOREIGN KEY (`account_details`) REFERENCES `investor_account_details` (`uuid`),
  CONSTRAINT `fk_reference_28` FOREIGN KEY (`jyb`) REFERENCES `jyb` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of investor_jyb_trade_record
-- ----------------------------

-- ----------------------------
-- Table structure for `jyb`
-- ----------------------------
DROP TABLE IF EXISTS `jyb`;
CREATE TABLE `jyb` (
  `uuid` varchar(36) NOT NULL,
  `scode` varchar(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `type` varchar(36) NOT NULL,
  `remark` varchar(1000) NOT NULL,
  `target_annualized_return_rate` decimal(10,2) NOT NULL,
  `min_annualized_return_rate` decimal(10,2) NOT NULL,
  `min_invest_amount` decimal(10,2) NOT NULL,
  `min_invest_amount_add` decimal(10,2) NOT NULL,
  `max_invest_amount` decimal(20,2) NOT NULL,
  `management_fee` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `return_fee` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `plan__total_subscribe_amount` decimal(20,2) NOT NULL,
  `total_subscribe_amount` decimal(20,2) NOT NULL DEFAULT '0.00',
  `total_shares` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `total_invest_amount` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `total_amount` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `netvalue` decimal(16,8) NOT NULL DEFAULT '1.00000000',
  `returns_rate` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `annualized_return_rate` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `positions` decimal(10,8) NOT NULL DEFAULT '0.00000000',
  `period` int(11) NOT NULL COMMENT '单位为天',
  `phases` varchar(10) NOT NULL DEFAULT 'unstart' COMMENT 'unstart; subscribing; subscribed; investing; returning; finished ;abnormal;',
  `status` varchar(10) NOT NULL DEFAULT 'normal' COMMENT 'normal;checked;deleted;published;stopped;',
  `subscribe_starttime` datetime NOT NULL,
  `subscribe_finishtime_plan` datetime NOT NULL,
  `subscribe_finishtime_real` datetime DEFAULT NULL,
  `invest_starttime_plan` datetime NOT NULL,
  `invest_starttime_real` datetime DEFAULT NULL,
  `invest_finishtime_plan` datetime NOT NULL,
  `invest_finishtime_real` datetime DEFAULT NULL,
  `returns_starttime_plan` datetime NOT NULL,
  `returns_starttime_real` datetime DEFAULT NULL,
  `returns_finishtime` datetime DEFAULT NULL,
  `guarantee_mode` varchar(20) NOT NULL,
  `guarantee_remark` varchar(1000) DEFAULT NULL,
  `repayment_mode` varchar(20) NOT NULL,
  `repayment_remark` varchar(1000) DEFAULT NULL,
  `risk_management` varchar(1000) DEFAULT NULL,
  `agreement` varchar(200) NOT NULL,
  `createtime` datetime NOT NULL,
  `creator` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`scode`),
  UNIQUE KEY `ak_key_3` (`name`),
  KEY `fk_reference_15` (`type`),
  CONSTRAINT `fk_reference_15` FOREIGN KEY (`type`) REFERENCES `jyb_type` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jyb
-- ----------------------------

-- ----------------------------
-- Table structure for `jyb_daily`
-- ----------------------------
DROP TABLE IF EXISTS `jyb_daily`;
CREATE TABLE `jyb_daily` (
  `uuid` varchar(36) NOT NULL,
  `jyb` varchar(36) NOT NULL,
  `date` date NOT NULL,
  `netvalue` decimal(16,8) NOT NULL,
  `netvalue_change` decimal(16,8) NOT NULL,
  `total_netvalue` decimal(16,8) NOT NULL,
  `daily_returns_rate` decimal(16,8) NOT NULL,
  `total_returns_rate` decimal(16,8) NOT NULL,
  `total_invest_amount` decimal(20,8) NOT NULL,
  `total_amount` decimal(20,8) NOT NULL,
  `total_shares` decimal(20,8) NOT NULL,
  `statistime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`jyb`,`date`),
  CONSTRAINT `fk_reference_17` FOREIGN KEY (`jyb`) REFERENCES `jyb` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jyb_daily
-- ----------------------------

-- ----------------------------
-- Table structure for `jyb_investment`
-- ----------------------------
DROP TABLE IF EXISTS `jyb_investment`;
CREATE TABLE `jyb_investment` (
  `uuid` varchar(36) NOT NULL,
  `jyb` varchar(36) NOT NULL,
  `invest_type` varchar(20) NOT NULL COMMENT 'fund; stock; currency;\r\n            ',
  `invest_product` varchar(36) NOT NULL,
  `total_amount` decimal(20,8) NOT NULL,
  `total_shares` decimal(20,8) NOT NULL,
  `per_shares_cost` decimal(16,8) NOT NULL,
  `current_netvalue` decimal(16,8) NOT NULL,
  `total_invest_amount` decimal(20,8) NOT NULL,
  `total_recailm_amount` decimal(20,8) NOT NULL,
  `cash_bonus` decimal(20,8) NOT NULL,
  `returns` decimal(20,8) NOT NULL,
  `returns_rate` decimal(16,8) NOT NULL,
  `statistime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`jyb`,`invest_type`,`invest_product`),
  KEY `fk_reference_19` (`invest_product`),
  CONSTRAINT `fk_reference_18` FOREIGN KEY (`jyb`) REFERENCES `jyb` (`uuid`),
  CONSTRAINT `fk_reference_19` FOREIGN KEY (`invest_product`) REFERENCES `fund` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jyb_investment
-- ----------------------------

-- ----------------------------
-- Table structure for `jyb_product_manager`
-- ----------------------------
DROP TABLE IF EXISTS `jyb_product_manager`;
CREATE TABLE `jyb_product_manager` (
  `uuid` varchar(36) NOT NULL,
  `jyb` varchar(36) NOT NULL,
  `manager` varchar(36) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_2` (`jyb`,`manager`),
  KEY `fk_reference_14` (`manager`),
  CONSTRAINT `fk_reference_13` FOREIGN KEY (`jyb`) REFERENCES `jyb` (`uuid`),
  CONSTRAINT `fk_reference_14` FOREIGN KEY (`manager`) REFERENCES `manager` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jyb_product_manager
-- ----------------------------

-- ----------------------------
-- Table structure for `jyb_trade_record`
-- ----------------------------
DROP TABLE IF EXISTS `jyb_trade_record`;
CREATE TABLE `jyb_trade_record` (
  `uuid` varchar(36) NOT NULL,
  `jyb` varchar(36) NOT NULL,
  `invest_type` varchar(20) NOT NULL COMMENT 'fund; stock; currency;\r\n            ',
  `invest_product` varchar(36) NOT NULL,
  `type` varchar(10) NOT NULL COMMENT 'purchase; redeem; dividend; ex-right;',
  `tradetime` datetime NOT NULL,
  `bargainday` date NOT NULL,
  `trade_netvalue` decimal(16,8) DEFAULT NULL,
  `trade_shares` decimal(20,8) DEFAULT NULL,
  `trade_amount` decimal(20,8) NOT NULL,
  `status` varchar(10) NOT NULL COMMENT 'success; failed;',
  `manager` varchar(36) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `fk_reference_12` (`invest_product`),
  KEY `fk_reference_16` (`manager`),
  KEY `fk_reference_21` (`creator`),
  KEY `fk_reference_6` (`jyb`),
  CONSTRAINT `fk_reference_12` FOREIGN KEY (`invest_product`) REFERENCES `fund` (`uuid`),
  CONSTRAINT `fk_reference_16` FOREIGN KEY (`manager`) REFERENCES `manager` (`uuid`),
  CONSTRAINT `fk_reference_21` FOREIGN KEY (`creator`) REFERENCES `bk_operator` (`uuid`),
  CONSTRAINT `fk_reference_6` FOREIGN KEY (`jyb`) REFERENCES `jyb` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jyb_trade_record
-- ----------------------------

-- ----------------------------
-- Table structure for `jyb_type`
-- ----------------------------
DROP TABLE IF EXISTS `jyb_type`;
CREATE TABLE `jyb_type` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(40) NOT NULL,
  `remark` varchar(2000) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='如日进斗金系列、招财进宝、年年有余系列';

-- ----------------------------
-- Records of jyb_type
-- ----------------------------

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` varchar(20) NOT NULL,
  `graduation` varchar(50) DEFAULT NULL,
  `education` varchar(20) DEFAULT NULL,
  `score` decimal(10,4) DEFAULT NULL,
  `resume` text,
  `workage` int(11) DEFAULT NULL,
  `mobile` varchar(100) NOT NULL,
  `idcard` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `photo` varchar(36) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `mobile_1` (`mobile`) USING BTREE,
  KEY `index_1` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('097ffbf1-ea8f-11e6-aba8-30e5e9e3b4d6', '超人1', 'superman', '哈尔滨佛学院', '大学本科', null, '&lt;p&gt;钱钱钱，我缺钱的说法 饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，&lt;br/&gt;&lt;/p&gt;&lt;p&gt;我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说&lt;/p&gt;&lt;p&gt;法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱&lt;/p&gt;&lt;p&gt;钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我&lt;/p&gt;&lt;p&gt;缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱钱钱，我缺钱的说法 饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱钱钱，我缺钱的说法 饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱钱钱，我缺钱的说法 饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱钱钱，我缺钱的说法 饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱钱钱，我缺钱的说法 饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我&lt;/p&gt;&lt;p style=&quot;white-space: normal;&quot;&gt;缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发钱钱钱，我缺钱的说法饭打发12321321321&lt;/p&gt;&lt;p&gt;&lt;br/&gt;&lt;/p&gt;', '0', '13858585858_097ffbf1-ea8f-11e6-aba8-30e5e9e3b4d6', '11010119900902151x_097ffbf1-ea8f-11e6-aba8-30e5e9e3b4d6', '5438@nidaye.cn', '65aab17e-08f9-47d8-b4e8-91668acbc3ca', 'deleted', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-09 10:46:40');
INSERT INTO `manager` VALUES ('563e4913-e5f4-4126-aa3d-ee98a8540a7f', 'A啊', '123', '', '', null, '', '1', '18612033494', '372330199210225111', '123', '0239e0b3-d843-495b-a0aa-3a8e19ed14a0', 'normal', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', '2017-02-08 11:44:47');

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(10) NOT NULL COMMENT '1视频 2图片',
  `filename` varchar(100) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `filetype` varchar(20) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'deleted/normal',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('0239e0b3-d843-495b-a0aa-3a8e19ed14a0', '2', 'ICBC', '/backadmin/img/ICBC.png', '.png', '11', 'normal');
INSERT INTO `resource` VALUES ('07a6bd76-8851-4c86-8ce2-8db251a333d7', '3', '12312321(232132)', '/upload/07a6bd76/8851/4c86/8ce2/8db251a333d7/12312321(232132).docx', 'docx', '7500563', 'normal');
INSERT INTO `resource` VALUES ('0a673d71-185e-4d26-83b0-ab917c7b121f', '3', '产品名称(产品编号)', 'upload/0a673d71/185e/4d26/83b0/ab917c7b121f/ab917c7b121f.docx产品名称(产品编号).docx', 'docx', '122321', 'normal');
INSERT INTO `resource` VALUES ('10c84c8d-1349-4773-923b-8a525927c312', '3', '8a525927c312', '/upload/10c84c8d/1349/4773/923b/8a525927c312/8a525927c312.docx', 'docx', '19550', 'normal');
INSERT INTO `resource` VALUES ('2b8d3a40-8d28-472d-ba2d-deec7b673c17', '2', 'deec7b673c17', '/upload/2b8d3a40/8d28/472d/ba2d/deec7b673c17/deec7b673c17.png', 'png', '968895', 'normal');
INSERT INTO `resource` VALUES ('2e210af9-b86f-453a-b6c8-bfa25f06728d', '3', 'bfa25f06728d', '/upload/2e210af9/b86f/453a/b6c8/bfa25f06728d/bfa25f06728d.doc', 'doc', '6499840', 'normal');
INSERT INTO `resource` VALUES ('2e69a615-25e1-4606-b675-cfe587c91a06', '3', 'cfe587c91a06', '/upload/2e69a615/25e1/4606/b675/cfe587c91a06/cfe587c91a06.doc', 'doc', '6499840', 'normal');
INSERT INTO `resource` VALUES ('35fcafa4-d3b7-46bd-b20c-0f457226118d', '3', '0f457226118d', '/upload/35fcafa4/d3b7/46bd/b20c/0f457226118d/0f457226118d.doc', 'doc', '6499840', 'normal');
INSERT INTO `resource` VALUES ('40076433-ba80-4c87-b60b-b37a8db5b263', '3', '产品名称2(111)', 'upload/40076433/ba80/4c87/b60b/b37a8db5b263/b37a8db5b263.docx产品名称2(111).docx', 'docx', '21678', 'normal');
INSERT INTO `resource` VALUES ('43652a2d-b7e1-47dc-9297-4fa84e310213', '2', '4fa84e310213', '/upload/43652a2d/b7e1/47dc/9297/4fa84e310213/4fa84e310213.jpg', 'jpg', '187726', 'normal');
INSERT INTO `resource` VALUES ('4ab465d4-7878-457a-9705-fd77b3b2cbf9', '2', 'fd77b3b2cbf9', '/upload/4ab465d4/7878/457a/9705/fd77b3b2cbf9/fd77b3b2cbf9.jpg', 'jpg', '187726', 'normal');
INSERT INTO `resource` VALUES ('5f2bb9ef-355d-4a6f-83ef-327d9382496b', '2', '327d9382496b', '/upload/5f2bb9ef/355d/4a6f/83ef/327d9382496b/327d9382496b.jpg', 'jpg', '187726', 'normal');
INSERT INTO `resource` VALUES ('65aab17e-08f9-47d8-b4e8-91668acbc3ca', '2', '91668acbc3ca', '/upload/65aab17e/08f9/47d8/b4e8/91668acbc3ca/91668acbc3ca.png', 'png', '5089', 'normal');
INSERT INTO `resource` VALUES ('6861990c-5102-4fd7-80ac-8388e7c6a2e2', '3', '【建设银行】乾元-私享型2017-74理财产品(ZH070417006067D11)', '/upload/6861990c/5102/4fd7/80ac/8388e7c6a2e2/【建设银行】乾元-私享型2017-74理财产品(ZH070417006067D11).doc', 'doc', '36352', 'normal');
INSERT INTO `resource` VALUES ('6f96ace0-62f9-4915-af6f-7e1742a14318', '3', '【建设银行】建行财富2017年第3期人民币非保本(ZH020220170200301)', '/upload/6f96ace0/62f9/4915/af6f/7e1742a14318/【建设银行】建行财富2017年第3期人民币非保本(ZH020220170200301).doc', 'doc', '72192', 'normal');
INSERT INTO `resource` VALUES ('7538d5e4-052f-4125-b69f-a0434ed53f0c', '3', 'a0434ed53f0c', '/upload/7538d5e4/052f/4125/b69f/a0434ed53f0c/a0434ed53f0c.docx', 'docx', '19550', 'normal');
INSERT INTO `resource` VALUES ('88744cf9-dbd6-4cc8-b7fc-aba9644c833b', '2', 'aba9644c833b', '/upload/88744cf9/dbd6/4cc8/b7fc/aba9644c833b/aba9644c833b.jpg', 'jpg', '187726', 'normal');
INSERT INTO `resource` VALUES ('8b1607f2-5ed0-4a7b-b807-b6d4ae84bb37', '2', 'b6d4ae84bb37', '/upload/8b1607f2/5ed0/4a7b/b807/b6d4ae84bb37/b6d4ae84bb37.png', 'png', '968895', 'normal');
INSERT INTO `resource` VALUES ('9530a31a-3751-4578-955d-6539674c35ba', '3', '6539674c35ba', '/upload/9530a31a/3751/4578/955d/6539674c35ba/6539674c35ba.docx', 'docx', '21678', 'normal');
INSERT INTO `resource` VALUES ('9f8cdc16-e4c5-4ea3-a8dc-06874e96d3b5', '3', '06874e96d3b5', '/upload/9f8cdc16/e4c5/4ea3/a8dc/06874e96d3b5/06874e96d3b5.docx', 'docx', '7500563', 'normal');
INSERT INTO `resource` VALUES ('a80f828d-0a89-4d7a-8770-e37ed0b2e4d5', '2', 'e37ed0b2e4d5', '/upload/a80f828d/0a89/4d7a/8770/e37ed0b2e4d5/e37ed0b2e4d5.jpg', 'jpg', '187726', 'normal');
INSERT INTO `resource` VALUES ('a8c24930-5c7d-4e3a-8006-b8a4a9bf14bb', '3', 'b8a4a9bf14bb', '/upload/a8c24930/5c7d/4e3a/8006/b8a4a9bf14bb/b8a4a9bf14bb.docx', 'docx', '64007', 'normal');
INSERT INTO `resource` VALUES ('aa86b1a1-0ed4-40f9-82ec-254e808028d1', '2', '254e808028d1', '/upload/aa86b1a1/0ed4/40f9/82ec/254e808028d1/254e808028d1.png', 'png', '968895', 'normal');
INSERT INTO `resource` VALUES ('bd5abc41-e149-4a1f-8e41-b928a4683b3a', '3', 'b928a4683b3a', '/upload/bd5abc41/e149/4a1f/8e41/b928a4683b3a/b928a4683b3a.docx', 'docx', '19550', 'normal');
INSERT INTO `resource` VALUES ('c419dfce-2afd-4c85-ae3d-f3e0f9fff46a', '3', 'f3e0f9fff46a', '/upload/c419dfce/2afd/4c85/ae3d/f3e0f9fff46a/f3e0f9fff46a.docx', 'docx', '19550', 'normal');
INSERT INTO `resource` VALUES ('d13cc10c-7536-4f42-9697-25a9e1ce904e', '2', '25a9e1ce904e', '/upload/d13cc10c/7536/4f42/9697/25a9e1ce904e/25a9e1ce904e.png', 'png', '968895', 'normal');
INSERT INTO `resource` VALUES ('d16c5d87-d44d-46cf-afec-53f94af2849f', '2', '53f94af2849f', '/upload/d16c5d87/d44d/46cf/afec/53f94af2849f/53f94af2849f.jpg', 'jpg', '187726', 'normal');
INSERT INTO `resource` VALUES ('d56b9086-2b60-4070-bc6c-8277d1e9f830', '3', '8277d1e9f830', '/upload/d56b9086/2b60/4070/bc6c/8277d1e9f830/8277d1e9f830.doc', 'doc', '6499840', 'normal');
INSERT INTO `resource` VALUES ('d8d756e8-05ce-4369-a6dd-344bcd9fdaec', '3', '344bcd9fdaec', '/upload/d8d756e8/05ce/4369/a6dd/344bcd9fdaec/344bcd9fdaec.docx', 'docx', '19550', 'normal');
INSERT INTO `resource` VALUES ('dd9ffab6-4fec-4e7b-b14d-7b1071ec4024', '3', '7b1071ec4024', '/upload/dd9ffab6/4fec/4e7b/b14d/7b1071ec4024/7b1071ec4024.docx', 'docx', '836360', 'normal');
INSERT INTO `resource` VALUES ('de455c0c-700e-4262-a47b-321653f97f80', '3', '321653f97f80', '/upload/de455c0c/700e/4262/a47b/321653f97f80/321653f97f80.docx', 'docx', '19550', 'normal');
INSERT INTO `resource` VALUES ('e049acc0-312b-4358-b76a-e43a3dbfeed5', '3', 'e43a3dbfeed5', '/upload/e049acc0/312b/4358/b76a/e43a3dbfeed5/e43a3dbfeed5.docx', 'docx', '19550', 'normal');
INSERT INTO `resource` VALUES ('eabdda2b-7c59-4790-b2df-d77b60f60aec', '3', 'd77b60f60aec', '/upload/eabdda2b/7c59/4790/b2df/d77b60f60aec/d77b60f60aec.docx', 'docx', '19550', 'normal');

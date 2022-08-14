/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2017-08-08 09:47:17
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
  `stage` varchar(20) DEFAULT 'unstart' COMMENT '产品状态',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `target` varchar(10) DEFAULT NULL COMMENT '面向对象',
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
  `network_fee` decimal(10,6) DEFAULT NULL COMMENT '销售渠道费用',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '产品规模',
  `collect_starttime` datetime DEFAULT NULL COMMENT '认购起始日',
  `collect_endtime` datetime DEFAULT NULL COMMENT '认购截止日',
  `term` int(11) DEFAULT NULL COMMENT '产品期限（多少天，起息日（含）至到期日（不含）的计算）',
  `record_date` datetime DEFAULT NULL COMMENT '登记日',
  `value_date` datetime DEFAULT NULL COMMENT '起息日',
  `maturity_date` datetime DEFAULT NULL COMMENT '到期日',
  `flag_purchase` tinyint(1) DEFAULT NULL COMMENT 'open,close(申购状态)',
  `flag_redemption` tinyint(1) DEFAULT NULL COMMENT '赎回状态',
  `flag_flexible` tinyint(1) DEFAULT NULL COMMENT '活跃期限',
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
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_financial_product
-- ----------------------------

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

-- ----------------------------
-- Table structure for `bank_financial_product_invest`
-- ----------------------------
DROP TABLE IF EXISTS `bank_financial_product_invest`;
CREATE TABLE `bank_financial_product_invest` (
  `uuid` varchar(36) NOT NULL,
  `bank_financial_product` varchar(36) NOT NULL COMMENT '银行理财产品',
  `bank_financial_product_publish` varchar(36) NOT NULL COMMENT '银行理财产品发布',
  `amount` decimal(20,2) NOT NULL COMMENT '投资金额',
  `redeem_amount` decimal(20,2) DEFAULT NULL COMMENT '赎回金额',
  `invest_income` decimal(20,2) DEFAULT NULL COMMENT '投资收益',
  `return_capital` decimal(20,2) DEFAULT NULL COMMENT '返还本金',
  `return_interest` decimal(20,2) DEFAULT NULL COMMENT '返还收益',
  `platfom_income` decimal(20,2) DEFAULT NULL COMMENT '平台收入',
  `stage` varchar(20) NOT NULL COMMENT '投资状态',
  `status` varchar(20) NOT NULL COMMENT '审核状态',
  `creator` varchar(36) NOT NULL COMMENT '创建者',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
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
  `bank_financial_product_invest` varchar(36) DEFAULT NULL COMMENT '投资UUID',
  `type` varchar(20) NOT NULL COMMENT '操作类型',
  `value` text NOT NULL COMMENT '操作值',
  `reason` varchar(500) DEFAULT NULL COMMENT '审核原因',
  `status` varchar(20) NOT NULL COMMENT '审核状态',
  `checker` varchar(36) DEFAULT NULL COMMENT '审核人',
  `checktime` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `creator` varchar(36) NOT NULL COMMENT '创建者',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `FK_BFPO_BFP` (`bank_financial_product_invest`),
  CONSTRAINT `FK_BFPIO_BFPI` FOREIGN KEY (`bank_financial_product_invest`) REFERENCES `bank_financial_product_invest` (`uuid`),
  CONSTRAINT `FK_BFPIO_C` FOREIGN KEY (`checker`) REFERENCES `bk_operator` (`uuid`)
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
  `bank_financial_product` varchar(36) DEFAULT NULL COMMENT '理财产品UUID',
  `type` varchar(20) NOT NULL COMMENT '操作类型',
  `value` text NOT NULL COMMENT '操作值',
  `reason` varchar(500) DEFAULT NULL COMMENT '审核原因',
  `status` varchar(20) NOT NULL COMMENT '审核状态',
  `checker` varchar(36) DEFAULT NULL COMMENT '审核人',
  `checktime` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `creator` varchar(36) NOT NULL COMMENT '创建者',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_BFP` (`bank_financial_product`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  CONSTRAINT `FK_BFPO_BFP` FOREIGN KEY (`bank_financial_product`) REFERENCES `bank_financial_product` (`uuid`),
  CONSTRAINT `FK_BFPO_CHECKER` FOREIGN KEY (`checker`) REFERENCES `bk_operator` (`uuid`)
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
  `stage` varchar(20) NOT NULL COMMENT '产品状态',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `target` varchar(10) DEFAULT NULL COMMENT '面向对象',
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
  `network_fee` decimal(10,6) DEFAULT NULL COMMENT '销售渠道费用',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '产品规模',
  `collect_starttime` datetime DEFAULT NULL COMMENT '认购起始日',
  `collect_endtime` datetime DEFAULT NULL COMMENT '认购截止日',
  `term` int(11) DEFAULT NULL COMMENT '产品期限（多少天，起息日（含）至到期日（不含）的计算）',
  `record_date` datetime DEFAULT NULL COMMENT '登记日',
  `value_date` datetime DEFAULT NULL COMMENT '起息日',
  `maturity_date` datetime DEFAULT NULL COMMENT '到期日',
  `flag_purchase` tinyint(1) DEFAULT NULL COMMENT 'open,close(申购状态)',
  `flag_redemption` tinyint(1) DEFAULT NULL COMMENT '赎回状态',
  `flag_flexible` tinyint(1) DEFAULT NULL COMMENT '灵活期限',
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
  KEY `FK_BFPP_BFP` (`bank_financial_product`) USING BTREE,
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
  `bank_financial_product_publish` varchar(36) DEFAULT NULL COMMENT '理财产品发布UUID',
  `type` varchar(20) NOT NULL COMMENT '操作类型',
  `value` text NOT NULL COMMENT '操作值',
  `reason` varchar(500) DEFAULT NULL COMMENT '审核原因',
  `status` varchar(20) NOT NULL COMMENT '审核状态',
  `checker` varchar(36) DEFAULT NULL COMMENT '审核者',
  `checktime` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `creator` varchar(36) NOT NULL COMMENT '创建者',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `FK_BFPO_BFP` (`bank_financial_product_publish`),
  CONSTRAINT `FK_BFPPO_BFPP` FOREIGN KEY (`bank_financial_product_publish`) REFERENCES `bank_financial_product_publish` (`uuid`),
  CONSTRAINT `FK_BFPPO_C` FOREIGN KEY (`checker`) REFERENCES `bk_operator` (`uuid`)
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
INSERT INTO `bk_controller_method` VALUES ('4feead1b-75d2-11e7-b50b-490e6ae956ee', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'redeem', '银行理财产品投资赎回');
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
INSERT INTO `bk_menu` VALUES ('2c94da6e-e9d9-11e6-9aec-ee0f7c43d97a', 'bank', '银行信息管理', '2', '00000001', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/bankInfoList.jsp', 'img/li0_0');
INSERT INTO `bk_menu` VALUES ('3a5b57c4-ee7a-11e6-b000-412b17f07060', 'roleMenu', '角色页面管理', '2', '00200023', '957a9425-ad15-63e1-d167-a700f159d3c2', 'backadmin/roleMenuList.jsp', 'img/li1_0');
INSERT INTO `bk_menu` VALUES ('41936d9d-e9d9-11e6-9aec-ee0f7c43d97a', 'fund', '基金信息管理', '2', '00000002', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/fundList.jsp', 'img/li0_1');
INSERT INTO `bk_menu` VALUES ('5440253b-f70d-11e6-8a88-461ab128b514', 'operateOperator', '运营用户管理', '2', '00100012', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/operateOperatorList.jsp', 'img/li1_1');
INSERT INTO `bk_menu` VALUES ('5f479bd6-e9d9-11e6-9aec-ee0f7c43d97a', 'manager', '主理人信息管理', '2', '00100014', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/managerList.jsp', 'img/li0_2');
INSERT INTO `bk_menu` VALUES ('78739445-dcb5-45e1-8cd7-d700f1498dc9', 'bankFinancialProduct', '理财产品信息管理', '2', '00300031', 'f5617841-a22d-c126-a7d8-0a5a9128b315', 'backadmin/bankFinancialProductList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('7a627841-1ad1-7196-8ad8-365ab128b514', 'check', '业务审核管理', '1', '0040', null, null, 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'admin', '后台用户管理', '1', '0010', null, null, 'img/LIP1');
INSERT INTO `bk_menu` VALUES ('8f647846-f70d-11e6-8a88-461ab128b514', 'financeOperator', '财务用户管理', '2', '00100013', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/financeOperatorList.jsp', 'img/li1_2');
INSERT INTO `bk_menu` VALUES ('957a9425-ad15-63e1-d167-a700f159d3c2', 'rolePermission', '用户权限管理', '1', '0020', null, null, 'img/LIP1');
INSERT INTO `bk_menu` VALUES ('a1ac35d1-63ad-252a-2b30-1d511a2d26a3', 'productInvest', '理财产品投资管理', '2', '00300033', 'f5617841-a22d-c126-a7d8-0a5a9128b315', 'backadmin/productInvestList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('aec7fd81-f41e-2ac6-9a16-420d4d4da531', 'productPublish', '理财产品发布管理', '2', '00300032', 'f5617841-a22d-c126-a7d8-0a5a9128b315', 'backadmin/productPublishList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('b985bb5c-0f9c-11e7-a0bb-519b17a1e492', 'menu', '页面菜单信息管理', '2', '00200021', '957a9425-ad15-63e1-d167-a700f159d3c2', 'backadmin/menuInfoList.jsp', 'img/li0_4');
INSERT INTO `bk_menu` VALUES ('bec34f69-f29e-11e6-9e84-962f283dbaeb', 'roleController', '角色功能管理', '2', '00200024', '957a9425-ad15-63e1-d167-a700f159d3c2', 'backadmin/roleControllerList.jsp', 'img/li1_3');
INSERT INTO `bk_menu` VALUES ('c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'base', '基础数据管理', '1', '0000', null, null, 'img/LIP0');
INSERT INTO `bk_menu` VALUES ('d2433155-51cd-484e-9695-8e32e88580c3', 'controller', '功能信息管理', '2', '00200022', '957a9425-ad15-63e1-d167-a700f159d3c2', 'backadmin/controllerInfoList.jsp', 'img/li0_5');
INSERT INTO `bk_menu` VALUES ('da2135c4-217a-11c6-1a10-2e3b142120a1', 'productInvestOperate', '理财产品投资审核管理', '2', '00400043', '7a627841-1ad1-7196-8ad8-365ab128b514', 'backadmin/productInvestOperateList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('da5167c4-ee7a-12e6-b700-413b17f07061', 'bankFinancialProductOperate', '理财产品信息审核管理', '2', '00400041', '7a627841-1ad1-7196-8ad8-365ab128b514', 'backadmin/bankFinancialProductOperateList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('decf5bd2-6396-4d44-8f62-4c466cee0481', 'investor', '投资者用户管理', '2', '00100015', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/investorList.jsp', 'img/li0_2');
INSERT INTO `bk_menu` VALUES ('ea2165c4-2e71-c2a6-b710-4e3b17207065', 'productPublishOperate', '理财产品发布审核管理', '2', '00400042', '7a627841-1ad1-7196-8ad8-365ab128b514', 'backadmin/productPublishOperateList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('f5617841-a22d-c126-a7d8-0a5a9128b315', 'business', '业务信息管理', '1', '0030', null, null, 'img/li0_3');
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
INSERT INTO `bk_operator` VALUES ('8ff7f0ec-bf72-4658-96ed-4dd7aa843a29', '13333333333', '13333333333', 'e9fe484d38c75e68975cb2fc4d8d2a37', '0127fcbe-f57f-11e6-ac06-cacda7da5000', '13333333333', '', '2017-08-02 16:36:32', 'c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3', 'unopen', null);
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
INSERT INTO `bk_role_controller_permission` VALUES ('0006b1a1-5362-4b7b-a14e-9cc66c2b3c27', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'de1833a2-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('03b1e178-9627-4cdb-b59a-4da32f41fd57', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd45c198-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('05a63653-f5d2-40d3-9eb6-8fd135ba0172', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '1a462583-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('06a75bad-bc4d-442f-9e79-e9869b1a7987', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'a804f9ec-ed1e-11e6-ae2e-d377d6cd7f14');
INSERT INTO `bk_role_controller_permission` VALUES ('09a34a3e-006a-4212-9528-98561072ae3c', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '551e1dd4-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('0e28d852-2148-48f5-b425-179939c30007', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '0a33aea5-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('0eb605fb-7b9c-4a9f-822e-50ee3e362217', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '8a991da8-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('1213fe54-077b-4f1a-a2e9-04635648c7b3', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ffe60503-f359-11e6-8a3c-ab6e287cf557', '1b568f79-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('141d2593-cdd6-47c5-93f0-42a1488af6e5', '4ebd77bc-3025-4657-bca7-cf684647c666', 'cf2e6a4b-0963-11e7-97f7-3a386a6ce01d', '2ed1a154-0964-11e7-97f7-3a386a6ce01d');
INSERT INTO `bk_role_controller_permission` VALUES ('14ca347d-0a56-413a-822b-ee05c9c1c41e', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'd65aa0ea-f359-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('168c06cb-0dc1-4597-b0d7-228cbbf675a3', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', '3b5b53fa-f740-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('17fdefe9-0bbd-418a-82fc-12e2c1d17fe8', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'c1bf7b33-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('19afb43f-e8c2-4db4-b8c4-c42274fb5208', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '5f79c327-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('1bc778ae-4721-4346-92d7-3d2b57743fa8', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'abbb87e3-0d30-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('20d9518a-52f6-49a8-b0a4-4495c27d1311', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '04833cae-edeb-11e6-954c-704e382d3ba0');
INSERT INTO `bk_role_controller_permission` VALUES ('21d0e9ab-6de2-4203-b7a6-a117e1197748', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '3007a7ec-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('22e47784-af1c-4063-ab24-9f9a935c20af', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '417a80ff-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('2449f549-80c4-4fe3-975a-74e5a827d620', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'eb8dda2d-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('24e143b9-dbaf-4017-bfd0-45c8e93dba8c', '4ebd77bc-3025-4657-bca7-cf684647c666', '7cc0431a-f41e-11e6-8e06-510d4a4da552', '93c9145a-b86d-4c4a-aa95-1fb997498b0e');
INSERT INTO `bk_role_controller_permission` VALUES ('26794cc6-3f03-4e84-912e-3cf12f01d406', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2fee920f-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('29bc4e64-4b35-422a-b8d0-0265d04e7106', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', 'ecd3ad7e-45ca-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('2a1e88d4-2391-4493-8490-c31acc4e4089', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', '4b8953fe-f740-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('2aa39776-a48a-419c-916e-8309f9cf0f1a', '4ebd77bc-3025-4657-bca7-cf684647c666', '8e1cbbb9-f35a-11e6-8a3c-ab6e287cf557', 'b0e23549-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('2c35ce41-93a2-4f69-917a-c9cf208fcd7a', '4ebd77bc-3025-4657-bca7-cf684647c666', '47b271a9-97c2-47d2-b0a1-3da3e0c059e2', '32295d11-d983-4d8f-9823-ae184e53b41b');
INSERT INTO `bk_role_controller_permission` VALUES ('2d1a292c-cf24-487d-9a4a-be92e2bcb650', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '6f83a415-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('31bc9935-c136-4ebb-bd31-e83a52522c1d', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'f95d391d-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('32031d13-517a-4886-99dd-50ee8f76e3fe', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '25691f5a-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('32998a97-69ce-4a24-95a4-75649fc9a4f8', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'ff48c946-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('33152a9b-361a-4a11-b143-c645fc2010fb', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'eb9924c8-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('3336e1c4-f1c9-4336-9443-07f4c9142929', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '397ef2bf-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('33c99486-846e-4c6c-a04d-87e16b57091b', '4ebd77bc-3025-4657-bca7-cf684647c666', '097ffbf1-ea8f-11e6-aba8-30e5e9e3b3d6', '3f8f508b-ea8f-11e6-aba8-30e5e9e3b3d6');
INSERT INTO `bk_role_controller_permission` VALUES ('34304db9-6879-44f2-b2df-a01ed324963f', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '8288c144-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('3460ef0e-3b26-43f3-b30e-7b32f52aa434', '4ebd77bc-3025-4657-bca7-cf684647c666', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', '68356af3-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('346ce00d-ea86-4dfa-87eb-4826106f29aa', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'ca6684da-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('35101e94-8741-4d57-90b5-ca2d9ab9037b', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '4c8341ee-45cf-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('36a1ae34-0fff-47ed-a4ac-cb29bc123bf2', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '652ab2ef-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('3ccb0865-8c0f-42c4-8a3a-6bd976d1ab71', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '1742f9b3-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('43ead320-f5e1-4e9b-806c-52f1ff16204f', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '0133e56d-45d2-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('47107da6-8481-4569-907a-966d5ec751b5', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'e0d97203-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('48208238-54f7-4239-87d7-2afd5ec2317b', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '1b886c10-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('4919c54a-ee35-4300-9eb4-ef14e216c568', '4ebd77bc-3025-4657-bca7-cf684647c666', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', '73224aba-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('4c1aa1c4-4e1c-4c6f-a118-6776a750c436', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '8bdb03d3-ea8e-11e6-aba8-30e5e9e3b3d6');
INSERT INTO `bk_role_controller_permission` VALUES ('4caa7c50-0b3d-4cc7-b7ca-c4f5244850fb', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'a4cca581-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('4fc4463c-d1f9-4901-99e5-290d188a13a7', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'c13e8dd0-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('502c1b2f-2c20-4fb9-a548-4c9e9c49bde2', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '39fd0144-62e0-11e7-8018-127d3cb7df39');
INSERT INTO `bk_role_controller_permission` VALUES ('517844a1-c67d-4913-89c6-3e1f2100be26', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'abb3c663-0d30-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('557b78a3-6fce-4e34-8c4a-64d80a0242da', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'ce703d25-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('57e30101-fc05-4613-9d93-024a917c8195', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', 'f1bb3e37-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('5bfeb0d5-8d24-43b9-8e07-6afbdd2d5888', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'd2aef97f-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('611d8213-0174-4f5d-a4f5-88e37c504ed3', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '51a4dc67-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('63280072-a9b5-470a-8451-4d55fa8a67e5', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'f166d70a-45d1-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('6467b357-6f2c-44b2-83d2-c113a6025350', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2fe768bd-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('667bda08-9df9-438f-8902-19ab3d1ff711', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', 'aab7dc06-45ce-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('6724b871-bf63-48c1-b822-bbfcdcba1b3b', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd5bff80-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('67ff8da3-395d-435d-876d-60005013b8a1', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '4a0e1f69-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('6ab84ced-271f-4146-844f-4e20ef61c622', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'b305328a-f8dc-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('6ae961fb-6f6a-4a8a-826a-9d4f04163304', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'c35c115d-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('6b5519b4-0d07-469a-bf13-8023aeab3b76', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '304ba42d-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('6dea3f3d-68a9-451c-8979-927ab38e827c', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '418b0374-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('6ee1ef33-9489-4cbc-878a-d3da3f09c496', '4ebd77bc-3025-4657-bca7-cf684647c666', '7cc0431a-f41e-11e6-8e06-510d4a4da552', 'ea2bcfd4-0fef-47a9-8b23-dd5e829e3fc0');
INSERT INTO `bk_role_controller_permission` VALUES ('6ef47577-c63c-45e7-be49-665ced30137b', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '59194d3a-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('70ffe1e2-4c78-4fe9-82eb-1837ef70ce2e', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '59761e39-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('7471feac-69dd-44cf-adc1-834d0e1c408b', '4ebd77bc-3025-4657-bca7-cf684647c666', '8e1cbbb9-f35a-11e6-8a3c-ab6e287cf557', 'a6d0db7c-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('75f536ab-0ad0-4e15-aae6-b94707505772', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '816d0baa-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('7758e1de-9d87-4a6e-8dc8-01e8e4711628', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '300c9227-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('79844f6c-80c8-4a9d-9f79-629eaa84e4bb', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '534f95b7-45cf-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('7c39dd17-8f3a-4d15-a4ae-23e818a9bb4c', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '3000de6f-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('7e159efe-45ab-4b0d-8155-57c52774ef22', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'f9f39324-45d1-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('7e1e6dad-c3ee-480a-9092-7787f212a509', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ffe60503-f359-11e6-8a3c-ab6e287cf557', '0ed32dc1-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('8140ae9c-dc31-4ca5-ba42-ec61e2629e0d', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '3a9c15f7-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('817c9902-ad2a-41d0-a04d-5cbf3cd3b099', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '7b0d55db-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('82124bc8-61fd-43fe-a90a-c66a5bc2e986', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '495fe49f-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('82cb633a-587f-40c3-b603-892d9bf84cbc', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '445c79aa-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('8486780c-c688-4951-b92a-a456741544c5', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2fdddbdc-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('859b1307-b9cf-4cde-974d-215a5146f76b', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'a7ea6cb3-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('88592aab-acd2-4c68-85c4-4824b5d53d39', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '946fa323-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('8bed1462-2810-4e53-a8a2-dbf072bd4671', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'f6f058e0-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('9206e4c6-6504-45fa-aefd-2e3304876457', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '4e4b1c92-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('9504e9f5-5f94-4e20-a423-b6a0d30945df', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'b49844ef-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('95502b1c-88df-4d0f-a85b-79093b049cc0', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '4feead1b-75d2-11e7-b50b-490e6ae956ee');
INSERT INTO `bk_role_controller_permission` VALUES ('96c94a17-b5f0-4db0-af6d-6181280f252a', '4ebd77bc-3025-4657-bca7-cf684647c666', '3cbba9c2-f35a-11e6-8a3c-ab6e287cf557', '5b54c74c-f35a-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('997983a2-e14d-4485-b980-ca5d3f40cbbf', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', '97dfbcc3-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('9c5d6c46-16a6-454d-88cc-77fa5c7b6be6', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'bd53d55f-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('9c651242-4360-493c-ba3e-14de6ea695d7', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd60105a-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('a38f2d7c-8f0c-4f56-ac8b-605c5b10aaa5', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', 'e30cedc9-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('a4d30cfc-5e67-4245-8dd8-82e7cc79b0e2', '4ebd77bc-3025-4657-bca7-cf684647c666', '8fe2946e-ed1e-11e6-ae2e-d377d6cd7f14', 'ac4c9214-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('b24e15e6-dd78-461e-b5c2-9a8d9f688a35', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2feb6095-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('bb7646e6-0550-48c3-9186-e2128ebaadf8', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '52d40173-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('c0dbaa2d-2ff5-4b6b-bb1a-012ee1f8410c', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '30120889-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('c31f7e6b-5ff4-4698-b8a4-3c1d3fef0975', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'd1bc30b9-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('c690016b-39ba-4113-ac4a-ab7d5315783f', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2ff869d3-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('c835fd99-895f-4f49-9ed5-5b97816fccee', '4ebd77bc-3025-4657-bca7-cf684647c666', '7cc0431a-f41e-11e6-8e06-510d4a4da552', 'ec93074b-1cde-4830-a65d-423cd6ef6e1b');
INSERT INTO `bk_role_controller_permission` VALUES ('cabee471-e953-4063-bc8a-f03687c91b0b', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', '9e2aafc5-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('cbcb9e61-1651-4c94-b081-89c1b362df6a', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '82c6e003-f8d7-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('d2c81bba-c815-4494-91b3-557c6e8bd05c', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '26628f5e-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('deb1130a-e157-47d2-9bb7-3e237820f7e3', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '2ff273b0-1041-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('e0496225-3b97-4a4f-b2f5-9b3a39f498ac', '4ebd77bc-3025-4657-bca7-cf684647c666', '47b271a9-97c2-47d2-b0a1-3da3e0c059e2', 'd87d4a9b-bdad-45a6-aab8-0915a58e85eb');
INSERT INTO `bk_role_controller_permission` VALUES ('e06d16c7-a976-4088-9e76-e75943af82c9', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '894359ec-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('e1dd21a4-6a04-4b05-b34f-1b37b59054b2', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', 'e3f24925-45ca-11e7-af5a-d06899c61413');
INSERT INTO `bk_role_controller_permission` VALUES ('e383bcd3-3c52-490a-a46e-188de7a20ffb', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fe869f54-ea8d-11e6-aba8-30e5e9e3b3d6', '46709ddd-ea8e-11e6-aba8-30e5e9e3b3d6');
INSERT INTO `bk_role_controller_permission` VALUES ('e4bf06ea-a0f0-4a39-801d-37d5027b47c1', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '9e41f32b-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('e6ceecd2-0667-4877-a21a-d1149db05f2a', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '017f041b-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('e78aa7b4-c32f-48ef-b08c-ae5bc0a3223f', '4ebd77bc-3025-4657-bca7-cf684647c666', '68e07731-f359-11e6-8a3c-ab6e287cf557', '97ad9df0-f359-11e6-8a3c-ab6e287cf557');
INSERT INTO `bk_role_controller_permission` VALUES ('e8166315-9114-4364-9054-4bc1d27c2c08', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', 'b0aa4848-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('e93bc5aa-fb27-42f5-958e-0d9da9f09140', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', '90b48cf1-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('ea8d1e24-4a1e-4e2b-9188-227cd5b64568', '4ebd77bc-3025-4657-bca7-cf684647c666', '796fc6f3-f71c-11e6-8a88-461ab128b514', 'b6c59f5b-f71c-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('eae80d8c-0952-48e3-8d3e-06d0db3331b3', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd64c33d-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('ee8a0167-c7c6-497c-aa7a-e9cdc150ba75', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '1f6f13e7-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('ef1268ca-2aa1-4999-9c86-1967dec2a43f', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c40d4270-ea8e-11e6-aba8-30e5e9e3b3d6', 'cd58fe5d-0f9e-11e7-a0bb-519b17a1e492');
INSERT INTO `bk_role_controller_permission` VALUES ('f100a346-50ef-49ce-b738-25b0a09b9c8e', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '1170a4e9-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('f2a36bf3-c2d2-4391-ab3f-f9a097a55855', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', 'ade214d5-6225-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('f2ac43dc-f814-4028-b889-8e5e5eb01701', '4ebd77bc-3025-4657-bca7-cf684647c666', '85ca07d0-f71c-11e6-8a88-461ab128b514', '56da2617-f740-11e6-8a88-461ab128b514');
INSERT INTO `bk_role_controller_permission` VALUES ('f36d4b47-b994-4ab3-a227-d097e0cbcfdf', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f0c7bc11-edea-11e6-954c-704e382d3ba0', '7a916418-ee6e-11e6-b000-412b17f07060');
INSERT INTO `bk_role_controller_permission` VALUES ('f5216b10-77e7-4e24-b962-c0bdec73bf68', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '7147f331-f8d7-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('f5b6a424-3055-4f84-b8e9-90e071d6421e', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '4dac7e6c-6227-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('f5fc7839-7e79-4b6d-9c3a-85fecc92602b', '4ebd77bc-3025-4657-bca7-cf684647c666', '479f5023-f716-11e6-8a88-461ab128b514', 'a7ac4d0a-f41e-11e6-8e06-510d4a4da552');
INSERT INTO `bk_role_controller_permission` VALUES ('f7ded0aa-5fbc-4fc5-a303-90390103ad90', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f789bacd-6225-11e7-89d2-39a48ccf689c', '4966a6b2-6226-11e7-89d2-39a48ccf689c');
INSERT INTO `bk_role_controller_permission` VALUES ('f847c938-89d4-4c89-9f05-62849ed32105', '4ebd77bc-3025-4657-bca7-cf684647c666', '10b51358-f7ea-11e6-ada9-d0295a4759e7', '3b219445-f7ea-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('fa3f13b8-0732-4829-bbb6-145bc52fb21b', '4ebd77bc-3025-4657-bca7-cf684647c666', '26193227-f81a-11e6-ada9-d0295a4759e7', '52a4dfe2-f81a-11e6-ada9-d0295a4759e7');
INSERT INTO `bk_role_controller_permission` VALUES ('ff44414a-ab14-4f62-aedf-0bc1390a410f', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ccb6e65f-6226-11e7-89d2-39a48ccf689c', '3b9938b3-6227-11e7-89d2-39a48ccf689c');

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
INSERT INTO `bk_role_menu_permission` VALUES ('0ea20df1-7e33-42fa-846d-c3f809d4e41b', '4ebd77bc-3025-4657-bca7-cf684647c666', 'bec34f69-f29e-11e6-9e84-962f283dbaeb', null, '4');
INSERT INTO `bk_role_menu_permission` VALUES ('14a0b464-f574-42b3-a548-75190964c895', '4ebd77bc-3025-4657-bca7-cf684647c666', '5f479bd6-e9d9-11e6-9aec-ee0f7c43d97a', null, '4');
INSERT INTO `bk_role_menu_permission` VALUES ('150373e2-c38e-4298-b105-fd24dbe41bae', '4ebd77bc-3025-4657-bca7-cf684647c666', 'f5617841-a22d-c126-a7d8-0a5a9128b315', null, '4');
INSERT INTO `bk_role_menu_permission` VALUES ('163a1759-7799-48e0-b75c-fa10fd499286', '4ebd77bc-3025-4657-bca7-cf684647c666', 'aec7fd81-f41e-2ac6-9a16-420d4d4da531', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('19fc2c02-593b-48ca-8723-3069af4b1fea', '4ebd77bc-3025-4657-bca7-cf684647c666', '7a627841-1ad1-7196-8ad8-365ab128b514', null, '5');
INSERT INTO `bk_role_menu_permission` VALUES ('1b750d35-18e1-45fa-8ce3-f53e0d8a7331', '4ebd77bc-3025-4657-bca7-cf684647c666', 'decf5bd2-6396-4d44-8f62-4c466cee0481', null, '5');
INSERT INTO `bk_role_menu_permission` VALUES ('42895e85-5e69-4acc-a49f-d62fdb9e9c73', '4ebd77bc-3025-4657-bca7-cf684647c666', '3a5b57c4-ee7a-11e6-b000-412b17f07060', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('4c9da662-c2b8-471a-a578-0b1350ed5e0c', '4ebd77bc-3025-4657-bca7-cf684647c666', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('4f5add66-58c0-42c7-9ce6-bb1a82628334', '4ebd77bc-3025-4657-bca7-cf684647c666', 'a1ac35d1-63ad-252a-2b30-1d511a2d26a3', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('535ac060-b840-4387-a86d-ac37f4b5fbb1', '4ebd77bc-3025-4657-bca7-cf684647c666', 'b985bb5c-0f9c-11e7-a0bb-519b17a1e492', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('5727f41e-87cb-4195-9d64-fae079a222d6', '4ebd77bc-3025-4657-bca7-cf684647c666', 'ea2165c4-2e71-c2a6-b710-4e3b17207065', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('6c1837b9-8940-4bff-adab-e2b9cb996292', '4ebd77bc-3025-4657-bca7-cf684647c666', '78739445-dcb5-45e1-8cd7-d700f1498dc9', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('94405c4c-69cd-46dd-9d30-486c04adc356', '4ebd77bc-3025-4657-bca7-cf684647c666', '957a9425-ad15-63e1-d167-a700f159d3c2', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('9b717e7d-92be-456b-8f0e-e79c102cf744', '4ebd77bc-3025-4657-bca7-cf684647c666', '5440253b-f70d-11e6-8a88-461ab128b514', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('a2de883d-b27a-4ead-9f00-9e822de5bc35', '4ebd77bc-3025-4657-bca7-cf684647c666', '2c94da6e-e9d9-11e6-9aec-ee0f7c43d97a', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('bbeeeff8-37a7-48bc-a4f7-ccdf306edd20', '4ebd77bc-3025-4657-bca7-cf684647c666', 'd2433155-51cd-484e-9695-8e32e88580c3', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('bfac3fc0-ed13-4857-a83c-7c1cceb957c3', '4ebd77bc-3025-4657-bca7-cf684647c666', 'da5167c4-ee7a-12e6-b700-413b17f07061', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('d224bc45-d304-44e9-bd27-3c192410c509', '4ebd77bc-3025-4657-bca7-cf684647c666', '8f647846-f70d-11e6-8a88-461ab128b514', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('e587d2fa-44ce-4a8a-b360-066a5e23f97e', '4ebd77bc-3025-4657-bca7-cf684647c666', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', null, '2');
INSERT INTO `bk_role_menu_permission` VALUES ('f86c11d0-7d0e-4234-a922-98462b17374b', '4ebd77bc-3025-4657-bca7-cf684647c666', 'fab7fd70-f41e-11e6-8e06-510d4a4da552', null, '1');
INSERT INTO `bk_role_menu_permission` VALUES ('f91e737d-9e25-497a-8734-92b88eb80ede', '4ebd77bc-3025-4657-bca7-cf684647c666', 'da2135c4-217a-11c6-1a10-2e3b142120a1', null, '3');
INSERT INTO `bk_role_menu_permission` VALUES ('f97d7171-e144-4542-871e-3d64d8cff520', '4ebd77bc-3025-4657-bca7-cf684647c666', '41936d9d-e9d9-11e6-9aec-ee0f7c43d97a', null, '2');

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
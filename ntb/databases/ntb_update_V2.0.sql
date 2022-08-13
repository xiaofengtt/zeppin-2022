ALTER TABLE bank_financial_product add COLUMN stage VARCHAR(20) DEFAULT 'unstart';
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

/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2017-07-07 14:49:08
*/

SET FOREIGN_KEY_CHECKS=0;

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


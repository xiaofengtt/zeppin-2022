/*
Navicat MySQL Data Transfer

Source Server         : xjjspxgl.zeppin.cn
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : ntb

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-08-21 15:47:37
*/

SET FOREIGN_KEY_CHECKS=0;

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
INSERT INTO `bk_menu` VALUES ('081b913e-7cb1-11e7-8e14-4aebbdef7785', 'productPublishOperate', '待审核事项(操作)', '2', '00400043', '7a627841-1ad1-7196-8ad8-365ab128b514', 'backadmin/productPublishOperateList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('17fc6011-863f-11e7-a99d-673bf20c66d8', 'invest', '财务管理', '1', '0050', null, null, 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('2c94da6e-e9d9-11e6-9aec-ee0f7c43d97a', 'bank', '银行信息管理', '2', '00000001', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/bankInfoList.jsp', 'img/li0_0');
INSERT INTO `bk_menu` VALUES ('3a5b57c4-ee7a-11e6-b000-412b17f07060', 'roleMenu', '角色页面管理', '2', '00200023', '957a9425-ad15-63e1-d167-a700f159d3c2', 'backadmin/roleMenuList.jsp', 'img/li1_0');
INSERT INTO `bk_menu` VALUES ('41936d9d-e9d9-11e6-9aec-ee0f7c43d97a', 'fund', '基金信息管理', '2', '00000002', 'c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'backadmin/fundList.jsp', 'img/li0_1');
INSERT INTO `bk_menu` VALUES ('5440253b-f70d-11e6-8a88-461ab128b514', 'operateOperator', '运营用户管理', '2', '00100012', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/operateOperatorList.jsp', 'img/li1_1');
INSERT INTO `bk_menu` VALUES ('5f479bd6-e9d9-11e6-9aec-ee0f7c43d97a', 'manager', '主理人信息管理', '2', '00100014', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/managerList.jsp', 'img/li0_2');
INSERT INTO `bk_menu` VALUES ('78739445-dcb5-45e1-8cd7-d700f1498dc9', 'bankFinancialProduct', '银行理财产品登记', '2', '00300031', 'f5617841-a22d-c126-a7d8-0a5a9128b315', 'backadmin/bankFinancialProductList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('7a627841-1ad1-7196-8ad8-365ab128b514', 'check', '牛投理财网站管理', '1', '0040', null, null, 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'admin', '后台用户管理', '1', '0010', null, null, 'img/LIP1');
INSERT INTO `bk_menu` VALUES ('8f647846-f70d-11e6-8a88-461ab128b514', 'financeOperator', '财务用户管理', '2', '00100013', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/financeOperatorList.jsp', 'img/li1_2');
INSERT INTO `bk_menu` VALUES ('957a9425-ad15-63e1-d167-a700f159d3c2', 'rolePermission', '用户权限管理', '1', '0020', null, null, 'img/LIP1');
INSERT INTO `bk_menu` VALUES ('a1ac35d1-63ad-252a-2b30-1d511a2d26a3', 'productInvest', '投资管理', '2', '00500051', '17fc6011-863f-11e7-a99d-673bf20c66d8', 'backadmin/productInvestList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('aec7fd81-f41e-2ac6-9a16-420d4d4da531', 'productPublish', '银行理财产品管理', '2', '00400041', '7a627841-1ad1-7196-8ad8-365ab128b514', 'backadmin/productPublishList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('b985bb5c-0f9c-11e7-a0bb-519b17a1e492', 'menu', '页面菜单信息管理', '2', '00200021', '957a9425-ad15-63e1-d167-a700f159d3c2', 'backadmin/menuInfoList.jsp', 'img/li0_4');
INSERT INTO `bk_menu` VALUES ('bec34f69-f29e-11e6-9e84-962f283dbaeb', 'roleController', '角色功能管理', '2', '00200024', '957a9425-ad15-63e1-d167-a700f159d3c2', 'backadmin/roleControllerList.jsp', 'img/li1_3');
INSERT INTO `bk_menu` VALUES ('c9abd12f-e9d8-11e6-9aec-ee0f7c43d97a', 'base', '基础数据管理', '1', '0000', null, null, 'img/LIP0');
INSERT INTO `bk_menu` VALUES ('cba4420e-7cb0-11e7-8e14-4aebbdef7785', 'bankFinancialProductOperate', '待审核事项(操作)', '2', '00300033', 'f5617841-a22d-c126-a7d8-0a5a9128b315', 'backadmin/bankFinancialProductOperateList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('d2433155-51cd-484e-9695-8e32e88580c3', 'controller', '功能信息管理', '2', '00200022', '957a9425-ad15-63e1-d167-a700f159d3c2', 'backadmin/controllerInfoList.jsp', 'img/li0_5');
INSERT INTO `bk_menu` VALUES ('da2135c4-217a-11c6-1a10-2e3b142120a1', 'productInvestOperate', '理财产品投资审核管理', '2', '00500052', '17fc6011-863f-11e7-a99d-673bf20c66d8', 'backadmin/productInvestOperateList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('da5167c4-ee7a-12e6-b700-413b17f07061', 'bankFinancialProductOperateCheck', '待审核事项(审核)', '2', '00300032', 'f5617841-a22d-c126-a7d8-0a5a9128b315', 'backadmin/bankFinancialProductOperateCheckList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('decf5bd2-6396-4d44-8f62-4c466cee0481', 'investor', '投资者用户管理', '2', '00100015', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/investorList.jsp', 'img/li0_2');
INSERT INTO `bk_menu` VALUES ('ea2165c4-2e71-c2a6-b710-4e3b17207065', 'productPublishOperateCheck', '待审核事项(审核)', '2', '00400042', '7a627841-1ad1-7196-8ad8-365ab128b514', 'backadmin/productPublishOperateCheckList.jsp', 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('f5617841-a22d-c126-a7d8-0a5a9128b315', 'business', '银行理财产品管理', '1', '0030', null, null, 'img/li0_3');
INSERT INTO `bk_menu` VALUES ('fab7fd70-f41e-11e6-8e06-510d4a4da552', 'superOperator', '系统管理员管理', '2', '00100011', '80fcc060-f1c0-11e6-b7fe-61f39f416b55', 'backadmin/superAdminList.jsp', 'img/li1_4');

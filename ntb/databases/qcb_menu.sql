/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.120_3306
Source Server Version : 50520
Source Host           : 192.168.1.120:3306
Source Database       : ntb_test1

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2018-01-22 11:54:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `qcb_menu`
-- ----------------------------
DROP TABLE IF EXISTS `qcb_menu`;
CREATE TABLE `qcb_menu` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `level` int(11) NOT NULL COMMENT '等级',
  `scode` varchar(20) NOT NULL COMMENT '编码',
  `pid` varchar(36) DEFAULT NULL COMMENT '父ID',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qcb_menu
-- ----------------------------
INSERT INTO `qcb_menu` VALUES ('013541d7-ff26-11e7-ac6d-fcaa14314cbe', 'companyManage', '企业管理', '1', '0030', null, '3');
INSERT INTO `qcb_menu` VALUES ('162fe2ff-ff26-11e7-ac6d-fcaa14314cbe', 'setting', '设置', '1', '0040', null, '4');
INSERT INTO `qcb_menu` VALUES ('3ffdb50c-ff27-11e7-ac6d-fcaa14314cbe', 'payrollGrant', '薪金发放', '2', '00200001', 'c09407e0-ff25-11e7-ac6d-fcaa14314cbe', '1');
INSERT INTO `qcb_menu` VALUES ('600eaeb1-ff26-11e7-ac6d-fcaa14314cbe', 'accountInfo', '账户信息', '2', '00100001', '811ffb07-ff25-11e7-ac6d-fcaa14314cbe', '1');
INSERT INTO `qcb_menu` VALUES ('6529ef10-ff27-11e7-ac6d-fcaa14314cbe', 'grantHistory', '发放记录', '2', '00200002', 'c09407e0-ff25-11e7-ac6d-fcaa14314cbe', '2');
INSERT INTO `qcb_menu` VALUES ('7e11389e-ff27-11e7-ac6d-fcaa14314cbe', 'financeService', '财税服务', '2', '00200003', 'c09407e0-ff25-11e7-ac6d-fcaa14314cbe', '3');
INSERT INTO `qcb_menu` VALUES ('811ffb07-ff25-11e7-ac6d-fcaa14314cbe', 'companyAccount', '企业账户', '1', '0010', null, '1');
INSERT INTO `qcb_menu` VALUES ('a051ed00-ff27-11e7-ac6d-fcaa14314cbe', 'companyInfo', '企业信息', '2', '00300001', '013541d7-ff26-11e7-ac6d-fcaa14314cbe', '1');
INSERT INTO `qcb_menu` VALUES ('b092aca4-ff27-11e7-ac6d-fcaa14314cbe', 'employeeManage', '员工管理', '2', '00300002', '013541d7-ff26-11e7-ac6d-fcaa14314cbe', '2');
INSERT INTO `qcb_menu` VALUES ('c09407e0-ff25-11e7-ac6d-fcaa14314cbe', 'companyPayroll', '员工薪金', '1', '0020', null, '2');
INSERT INTO `qcb_menu` VALUES ('c6c28908-ff27-11e7-ac6d-fcaa14314cbe', 'adminSetting', '管理员设置', '2', '00400001', '162fe2ff-ff26-11e7-ac6d-fcaa14314cbe', '1');
INSERT INTO `qcb_menu` VALUES ('c7b0a832-ff26-11e7-ac6d-fcaa14314cbe', 'companyRecharge', '企业充值', '2', '00100002', '811ffb07-ff25-11e7-ac6d-fcaa14314cbe', '2');
INSERT INTO `qcb_menu` VALUES ('d60d061e-ff26-11e7-ac6d-fcaa14314cbe', 'companyWithdrow', '企业提现', '2', '00100003', '811ffb07-ff25-11e7-ac6d-fcaa14314cbe', '3');
INSERT INTO `qcb_menu` VALUES ('e8d666c3-ff26-11e7-ac6d-fcaa14314cbe', 'accountDetail', '账务明细', '2', '00100004', '811ffb07-ff25-11e7-ac6d-fcaa14314cbe', '4');

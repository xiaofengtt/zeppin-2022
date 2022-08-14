/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : ntb_test

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2017-09-01 10:21:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `investor`
-- ----------------------------
DROP TABLE IF EXISTS `investor`;
CREATE TABLE `investor` (
  `uuid` varchar(36) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
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
  `openid` varchar(30) DEFAULT NULL COMMENT '微信唯一标识',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ak_key_3` (`mobile`),
  UNIQUE KEY `ak_key_2` (`idcard`),
  UNIQUE KEY `ak_key_4` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of investor
-- ----------------------------
INSERT INTO `investor` VALUES ('5f32ccff-1694-11e7-ae32-88870ca723b7', '忍者神龟', '朱克鑫', '370681198902201687', '18601142193', '12346@qq.com', '202cb962ac59075b964b07152d234b70', '123', '', '', 'normal', '1', '1', '1', '0', '3000000.00000000', '10000.00000000', '8888888.00000000', '2017-04-01 12:29:27', '', '1', '2017-08-31 16:33:18', '', null);
INSERT INTO `investor` VALUES ('b323ed33-4fc1-4c19-893f-a7ff4afeed89', null, null, null, '13161346073', null, '96e79218965eb72c92a549dd5a330112', null, null, null, 'normal', '1', '0', '0', '0', '0.00000000', '0.00000000', '0.00000000', '2017-09-01 09:54:32', null, 'weixin', '2017-09-01 10:00:02', null, 'oKT7-0BwQSfCLqbu2jgqj_unuiQY');

-- ----------------------------
-- Table structure for `mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `mobile_code`;
CREATE TABLE `mobile_code` (
  `uuid` varchar(36) NOT NULL COMMENT '手机号',
  `mobile` varchar(11) NOT NULL,
  `code` varchar(10) NOT NULL COMMENT '验证码',
  `content` varchar(200) NOT NULL COMMENT '短信内容',
  `type` varchar(10) NOT NULL COMMENT '短信类型1-验证码 2-**信息 。。。',
  `creator` varchar(36) DEFAULT NULL COMMENT '发送人',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `creator_type` varchar(10) NOT NULL COMMENT '发送角色类型',
  `status` varchar(10) NOT NULL COMMENT '短信状态',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mobile_code
-- ----------------------------
INSERT INTO `mobile_code` VALUES ('3b6923f2-2e87-4337-9215-0fe70642a146', '18612033494', '595024', '您本次操作的验证码为：595024，请及时使用，且勿告知他人，验证码将在5分钟后失效！', 'register', null, '2017-09-01 09:52:16', 'investor', 'disable');
INSERT INTO `mobile_code` VALUES ('a0bd1b55-8f8a-493d-b98e-b948c29caf1a', '13161346073', '762355', '您本次操作的验证码为：762355，请及时使用，且勿告知他人，验证码将在5分钟后失效！', 'register', null, '2017-09-01 09:53:14', 'investor', 'disable');
INSERT INTO `mobile_code` VALUES ('a27d45ab-678b-4a11-9370-60a74f7ffea6', '18601142193', '037255', '您本次操作的验证码为：037255，请及时使用，且勿告知他人，验证码将在5分钟后失效！', 'register', null, '2017-09-01 09:52:14', 'investor', 'disable');
INSERT INTO `mobile_code` VALUES ('bff32ba1-1054-4344-98e2-33addf5438fd', '13161346073', '789534', '您本次操作的验证码为：789534，请及时使用，且勿告知他人，验证码将在5分钟后失效！', 'register', null, '2017-09-01 09:42:18', 'investor', 'disable');
INSERT INTO `mobile_code` VALUES ('d31d3697-30ec-44ee-9fcc-a4ae0c3d65e0', '13161346073', '851363', '您本次操作的验证码为：851363，请及时使用，且勿告知他人，验证码将在5分钟后失效！', 'register', null, '2017-09-01 10:00:33', 'investor', 'disable');
INSERT INTO `mobile_code` VALUES ('ecc08ff8-0e7c-4a4f-9f74-c47663e8a6c1', '13161346073', '406218', '您本次操作的验证码为：406218，请及时使用，且勿告知他人，验证码将在5分钟后失效！', 'register', null, '2017-09-01 10:01:36', 'investor', 'disable');

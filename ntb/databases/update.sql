ALTER TABLE `bank_financial_product_publish`
ADD COLUMN `flag_buy`  tinyint(1) NULL COMMENT '是否可购买 1-是 0-否' AFTER `url`;

ALTER TABLE `bank`
ADD COLUMN `single_limit`  decimal(10,0) NOT NULL COMMENT '单笔限额' AFTER `createtime`,
ADD COLUMN `daily_limit`  decimal(10,0) NOT NULL COMMENT '每日限额' AFTER `single_limit`;

update bank set single_limit=0,daily_limit=0;

INSERT INTO `bk_controller_method` VALUES ('222614b0-888c-11e7-a99d-673bf20c66d8', 'f789bacd-6225-11e7-89d2-39a48ccf689c', 'synchro', '银行理财产品发布同步信息');

ALTER TABLE `bank`
ADD COLUMN `color`  varchar(10) NOT NULL COMMENT '显示色值' AFTER `daily_limit`,
ADD COLUMN `icon`  varchar(36) NOT NULL COMMENT '显示图标' AFTER `color`;

ALTER TABLE `bank`
ADD COLUMN `icon_color`  varchar(36) NOT NULL COMMENT '显示图标（带颜色）' AFTER `flag_binding`;

ALTER TABLE `investor_account_history`
MODIFY COLUMN `status`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交易状态' AFTER `pay`;


ALTER TABLE `orderinfo_by_thirdparty`
MODIFY COLUMN `status`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交易状态' AFTER `pay_time`,
ADD COLUMN `investor`  varchar(36) NOT NULL COMMENT '用户编号' AFTER `err_code`;


ALTER TABLE `investor`
ADD COLUMN `sex`  varchar(36) NULL DEFAULT '' COMMENT '性别' AFTER `idcard_img`;

ALTER TABLE `investor_product_buy_records`
MODIFY COLUMN `status`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买入状态' AFTER `bill`,
ADD COLUMN `stage`  varchar(20) NOT NULL COMMENT '持仓阶段' AFTER `status`;

ALTER TABLE `investor_account_history`
ADD COLUMN `order_num`  varchar(32) NOT NULL COMMENT '订单号' AFTER `type`;

ALTER TABLE `investor_account_history`
ADD COLUMN `company_account`  varchar(36) NOT NULL COMMENT '公司账户' AFTER `order_num`;

ALTER TABLE `investor`
CHANGE COLUMN `total_amount` `total_invest`  decimal(20,8) NOT NULL DEFAULT 0.00000000 COMMENT '账户总投资' AFTER `secret_password_flag`;

ALTER TABLE `investor_product_buy_records`
DROP COLUMN `stage`;

ALTER TABLE `bank`
ADD COLUMN `code`  varchar(10) NULL COMMENT '银行编码' AFTER `icon_color`;

ALTER TABLE `investor_account_history`
ADD COLUMN `poundage`  decimal(20,8) NOT NULL DEFAULT 0 COMMENT '手续费金额' AFTER `product_type`;

ALTER TABLE `orderinfo_by_thirdparty`
MODIFY COLUMN `err_code`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `err_code_des`;

ALTER TABLE `investor_product_buy`
ADD INDEX `ak_product_buy_type` USING BTREE (`type`) ;

ALTER TABLE `bank_financial_product_publish`
ADD INDEX `AK_CUSTODIAN_BFPP` (`custodian`) USING BTREE ;

ALTER TABLE `investor_account_history`
ADD COLUMN `processing_status`  varchar(20) NULL DEFAULT '' COMMENT '处理状态process--处理中 unprocessing--未处理 success--处理成功 fail--处理失败' AFTER `poundage`,
ADD COLUMN `bankcard`  varchar(36) NULL COMMENT '充值/提现绑定银行卡' AFTER `processing_status`,
ADD COLUMN `process_company_account`  varchar(36) NULL COMMENT '处理账户' AFTER `bankcard`,
ADD COLUMN `process_creator`  varchar(36) NULL COMMENT '处理人' AFTER `process_company_account`,
ADD COLUMN `process_createtime`  timestamp NULL COMMENT '处理时间' AFTER `process_creator`;

ALTER TABLE `bank_financial_product_invest_operate`
ADD COLUMN `receipt`  varchar(500) NULL COMMENT '上传凭证' AFTER `submittime`;

ALTER TABLE `company_account_transfer_operate`
ADD COLUMN `receipt`  varchar(500) NULL COMMENT '上传的凭证' AFTER `createtime`;

ALTER TABLE `investor`
ADD COLUMN `ali_photo`  varchar(100) NULL DEFAULT '' COMMENT '支付宝头像' AFTER `sex`,
ADD COLUMN `ali_userid`  varchar(16) NULL DEFAULT '' COMMENT '支付宝账号' AFTER `ali_photo`,
ADD COLUMN `ali_nickname`  varchar(50) NULL DEFAULT '' COMMENT '支付宝账号昵称' AFTER `ali_userid`;

ALTER TABLE `investor`
ADD INDEX `index_key_openid` USING BTREE (`openid`) ,
ADD INDEX `index_key_aliuserid` USING BTREE (`ali_userid`) ;

ALTER TABLE `auto_ali_transfer_process`
ADD COLUMN `process_index`  int(11) NOT NULL DEFAULT 0 AFTER `createtime`;

ALTER TABLE `orderinfo_by_thirdparty`
MODIFY COLUMN `type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付平台' AFTER `uuid`;

ALTER TABLE `bk_webmarket_switch`
MODIFY COLUMN `web_market`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '应用市场标识码' AFTER `uuid`;

INSERT INTO bk_controller VALUES ('5f99bdba-d4d7-11e7-94cb-10493f6129ab','bkpayment','支付方式管理',0);
INSERT INTO `bk_controller_method` VALUES ('d8b4d367-d4d9-11e7-94cb-10493f6129ab', '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'get', '支付方式获取信息',0);
INSERT INTO `bk_controller_method` VALUES ('d8ff25a9-d4d9-11e7-94cb-10493f6129ab', '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'list', '支付方式列表',0);
INSERT INTO `bk_controller_method` VALUES ('d9046ed4-d4d9-11e7-94cb-10493f6129ab', '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'add', '支付方式添加',0);
INSERT INTO `bk_controller_method` VALUES ('d909d680-d4d9-11e7-94cb-10493f6129ab', '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'edit', '支付方式编辑',0);
INSERT INTO `bk_controller_method` VALUES ('d924a1d7-d4d9-11e7-94cb-10493f6129ab', '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'delete', '支付方式删除',0);
INSERT INTO `bk_controller_method` VALUES ('d92c3768-d4d9-11e7-94cb-10493f6129ab', '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'change', '支付方式开关控制',0);
INSERT INTO `bk_controller_method` VALUES (UUID(), '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'operateGet', '支付方式操作信息获取',0);
INSERT INTO `bk_controller_method` VALUES (UUID(), '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'operateList', '支付方式操作列表',0);
INSERT INTO `bk_controller_method` VALUES (UUID(), '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'operateCheckList', '支付方式操作列表（管理员）',0);
INSERT INTO `bk_controller_method` VALUES (UUID(), '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'operateEdit', '支付方式操作编辑',0);
INSERT INTO `bk_controller_method` VALUES (UUID(), '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'operateDelete', '支付方式操作删除',0);
INSERT INTO `bk_controller_method` VALUES (UUID(), '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'operateSubmitCheck', '支付方式操作提交审核',0);
INSERT INTO `bk_controller_method` VALUES (UUID(), '5f99bdba-d4d7-11e7-94cb-10493f6129ab', 'operateCheck', '支付方式操作审核',0);

INSERT INTO bk_controller VALUES ('3936437e-d4dd-11e7-94cb-10493f6129ab','market','应用商店管理',0);
INSERT INTO `bk_controller_method` VALUES ('7f552ac0-d4dd-11e7-94cb-10493f6129ab', '3936437e-d4dd-11e7-94cb-10493f6129ab', 'get', '应用商店开关信息获取',0);
INSERT INTO `bk_controller_method` VALUES ('7f601653-d4dd-11e7-94cb-10493f6129ab', '3936437e-d4dd-11e7-94cb-10493f6129ab', 'list', '应用商店开关列表',0);
INSERT INTO `bk_controller_method` VALUES ('7f65292f-d4dd-11e7-94cb-10493f6129ab', '3936437e-d4dd-11e7-94cb-10493f6129ab', 'add', '应用商店开关添加',0);
INSERT INTO `bk_controller_method` VALUES ('7f6873a6-d4dd-11e7-94cb-10493f6129ab', '3936437e-d4dd-11e7-94cb-10493f6129ab', 'edit', '应用商店开关编辑',0);
INSERT INTO `bk_controller_method` VALUES ('7f6c630a-d4dd-11e7-94cb-10493f6129ab', '3936437e-d4dd-11e7-94cb-10493f6129ab', 'delete', '应用商店开关删除',0);
INSERT INTO `bk_controller_method` VALUES ('7f7270a9-d4dd-11e7-94cb-10493f6129ab', '3936437e-d4dd-11e7-94cb-10493f6129ab', 'change', '应用商店开关控制',0);

INSERT INTO bk_controller VALUES ('c7caf2fe-d4dd-11e7-94cb-10493f6129ab','version','后台版本控制管理',0);
INSERT INTO `bk_controller_method` VALUES ('249e2b91-d4de-11e7-94cb-10493f6129ab', 'c7caf2fe-d4dd-11e7-94cb-10493f6129ab', 'get', '版本控制信息获取',0);
INSERT INTO `bk_controller_method` VALUES ('24b059cd-d4de-11e7-94cb-10493f6129ab', 'c7caf2fe-d4dd-11e7-94cb-10493f6129ab', 'list', '版本控制列表',0);
INSERT INTO `bk_controller_method` VALUES ('24b566a6-d4de-11e7-94cb-10493f6129ab', 'c7caf2fe-d4dd-11e7-94cb-10493f6129ab', 'add', '版本控制添加',0);
INSERT INTO `bk_controller_method` VALUES ('24be116a-d4de-11e7-94cb-10493f6129ab', 'c7caf2fe-d4dd-11e7-94cb-10493f6129ab', 'edit', '版本控制编辑',0);
INSERT INTO `bk_controller_method` VALUES ('24d2a010-d4de-11e7-94cb-10493f6129ab', 'c7caf2fe-d4dd-11e7-94cb-10493f6129ab', 'delete', '版本控制删除',0);
INSERT INTO `bk_controller_method` VALUES ('24e77806-d4de-11e7-94cb-10493f6129ab', 'c7caf2fe-d4dd-11e7-94cb-10493f6129ab', 'change', '版本控制修改状态',0);

INSERT INTO bk_menu VALUES ('29145dd5-d4e0-11e7-94cb-10493f6129ab', 'version', '版本控制管理', '2', '00200025', '957a9425-ad15-63e1-d167-a700f159d3c2','backadmin/versionControl.jsp', 'img/li0_3',999);
INSERT INTO bk_menu VALUES ('46d4ead9-d4e0-11e7-94cb-10493f6129ab', 'bkpayment', '支付方式管理', '2', '00200026', '957a9425-ad15-63e1-d167-a700f159d3c2','backadmin/alipayStatusList.jsp', 'img/li0_3',1000);
INSERT INTO bk_menu VALUES ('46dd4c21-d4e0-11e7-94cb-10493f6129ab', 'bkpaymentOperate', '待审核事项(支付方式申请)', '2', '00200027', '957a9425-ad15-63e1-d167-a700f159d3c2','backadmin/alipayStatusListAuditing.jsp', 'img/li0_3',1001);
INSERT INTO bk_menu VALUES ('46e4333f-d4e0-11e7-94cb-10493f6129ab', 'bkpaymentOperateCheck', '待审核事项(支付方式审核)', '2', '00200028', '957a9425-ad15-63e1-d167-a700f159d3c2','backadmin/alipayStatusListAuditingCheck.jsp', 'img/li0_3',1002);
INSERT INTO bk_menu VALUES ('46e8e8a5-d4e0-11e7-94cb-10493f6129ab', 'market', '应用商店管理', '2', '00200029', '957a9425-ad15-63e1-d167-a700f159d3c2','backadmin/storeSwitch.jsp', 'img/li0_3',1003);

INSERT INTO bk_controller VALUES ('34e294c8-f0ee-11e7-bca9-fd28cf957eb7','couponStrategy','优惠券策略管理',0);
INSERT INTO `bk_controller_method` VALUES ('9ce63e16-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'get', '优惠券策略获取信息',0);
INSERT INTO `bk_controller_method` VALUES ('9cee0e54-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'list', '优惠券策略列表',0);
INSERT INTO `bk_controller_method` VALUES ('9cf260b0-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'add', '优惠券策略添加',0);
INSERT INTO `bk_controller_method` VALUES ('9cf6e880-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'edit', '优惠券策略编辑',0);
INSERT INTO `bk_controller_method` VALUES ('9cfb4472-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'delete', '优惠券策略删除',0);
INSERT INTO `bk_controller_method` VALUES ('9d070258-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'change', '优惠券策略开关控制',0);
INSERT INTO `bk_controller_method` VALUES ('9d0d25ab-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'operateGet', '优惠券策略操作信息获取',0);
INSERT INTO `bk_controller_method` VALUES ('9d133584-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'operateList', '优惠券策略操作列表',0);
INSERT INTO `bk_controller_method` VALUES ('9d1a837e-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'operateCheckList', '优惠券策略操作列表（管理员）',0);
INSERT INTO `bk_controller_method` VALUES ('9d24c93f-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'operateEdit', '优惠券策略操作编辑',0);
INSERT INTO `bk_controller_method` VALUES ('9d2ae961-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'operateDelete', '优惠券策略操作删除',0);
INSERT INTO `bk_controller_method` VALUES ('9d30c1af-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'operateSubmitCheck', '优惠券策略操作提交审核',0);
INSERT INTO `bk_controller_method` VALUES ('9d36c5a7-f0ee-11e7-bca9-fd28cf957eb7', '34e294c8-f0ee-11e7-bca9-fd28cf957eb7', 'operateCheck', '优惠券策略操作审核',0);

INSERT INTO bk_controller VALUES ('15fff069-f0ef-11e7-bca9-fd28cf957eb7','coupon','优惠券管理',0);
INSERT INTO `bk_controller_method` VALUES ('5b5ab6f9-f0ef-11e7-bca9-fd28cf957eb7', '15fff069-f0ef-11e7-bca9-fd28cf957eb7', 'get', '优惠券信息获取',0);
INSERT INTO `bk_controller_method` VALUES ('5b619033-f0ef-11e7-bca9-fd28cf957eb7', '15fff069-f0ef-11e7-bca9-fd28cf957eb7', 'list', '优惠券信息列表',0);
INSERT INTO `bk_controller_method` VALUES ('5b67c246-f0ef-11e7-bca9-fd28cf957eb7', '15fff069-f0ef-11e7-bca9-fd28cf957eb7', 'add', '优惠券添加',0);
INSERT INTO `bk_controller_method` VALUES ('5b77deba-f0ef-11e7-bca9-fd28cf957eb7', '15fff069-f0ef-11e7-bca9-fd28cf957eb7', 'edit', '优惠券编辑',0);
INSERT INTO `bk_controller_method` VALUES ('5b7e0f2a-f0ef-11e7-bca9-fd28cf957eb7', '15fff069-f0ef-11e7-bca9-fd28cf957eb7', 'delete', '优惠券删除',0);

INSERT INTO bk_menu VALUES ('cba4420e-7cb0-11e7-8e14-1aebbdef1781', 'ruleListAuditing', '投放策略申请', '2', '00400044', '7a627841-1ad1-7196-8ad8-365ab128b514','backadmin/ruleListAuditing.jsp', 'img/li0_3',6);
INSERT INTO bk_menu VALUES ('cba4420e-7cb0-11e7-8e14-4aebbdef1781', 'ruleList', '投放策略管理', '2', '00400045', '7a627841-1ad1-7196-8ad8-365ab128b514','backadmin/ruleList.jsp', 'img/li0_3',5);
INSERT INTO bk_menu VALUES ('cba4420e-7cb0-11e7-8e14-4aebbdef7781', 'cardList', '优惠券管理', '2', '00400046', '7a627841-1ad1-7196-8ad8-365ab128b514','backadmin/cardList.jsp', 'img/li0_3',4);
INSERT INTO bk_menu VALUES ('cba4420e-7cb0-11e7-8e14-4aedbdef178', 'ruleListAuditingCheck', '投放策略审核', '2', '00400047', '7a627841-1ad1-7196-8ad8-365ab128b514','backadmin/ruleListAuditingCheck.jsp', 'img/li0_3',7);

ALTER TABLE `bank`
ADD COLUMN `flag_bank`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否为银行' AFTER `code`,
ADD COLUMN `code_num`  varchar(10) NULL DEFAULT '' COMMENT '银行数字编码' AFTER `flag_bank`;

ALTER TABLE `investor`
MODIFY COLUMN `login_password`  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '登录密码' AFTER `email`;

ALTER TABLE `investor_coupon_history`
ADD COLUMN `investor_account_history`  varchar(36) NOT NULL DEFAULT '' COMMENT '交易记录' AFTER `status`;

ALTER TABLE `investor_coupon_history`
ADD COLUMN `dividend`  decimal(20,2) NULL DEFAULT 0 AFTER `investor_account_history`;

------------------------------------------------------------------------------------------------------------------
-----------------------------------------------201803更新上线脚本-------------------------------------------------
------------------------------------------------------------------------------------------------------------------
ALTER TABLE `investor_bankcard`
ADD COLUMN `binding_id`  varchar(50) NULL DEFAULT '' COMMENT '绑卡ID' AFTER `binding_card_cardholder`;

ALTER TABLE `mobile_code`
MODIFY COLUMN `code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码' AFTER `mobile`;

ALTER TABLE `company_account_history`
ADD COLUMN `qcb_company_account`  varchar(36) NULL AFTER `investor`;

ALTER TABLE `company_account_history`
ADD COLUMN `qcb_company_account_history`  varchar(36) NULL AFTER `investor_account_history`;

ALTER TABLE `company_account`
ADD COLUMN `branch_bank`  varchar(36) NULL AFTER `bank`;

ALTER TABLE `mobile_code`
MODIFY COLUMN `type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短信类型1-验证码 2-**信息 。。。' AFTER `content`;

INSERT bk_controller(uuid,name,description) VALUES('cf10b068-04c1-11e8-9883-fcaa14314cbe','qcbcompany','企财宝企业管理');
INSERT bk_controller(uuid,name,description) VALUES('cf172a54-04c1-11e8-9883-fcaa14314cbe','qcbfinance','企财宝财税服务');
INSERT bk_controller(uuid,name,description) VALUES('cf1c5ecc-04c1-11e8-9883-fcaa14314cbe','qcbVirtualAccounts','企财宝银行虚拟户');
INSERT bk_controller(uuid,name,description) VALUES('8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','qcbCompanyTransfer','企财宝企业交易');

INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf10b068-04c1-11e8-9883-fcaa14314cbe','list','企财宝企业列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf10b068-04c1-11e8-9883-fcaa14314cbe','get','企财宝企业获取');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf10b068-04c1-11e8-9883-fcaa14314cbe','historyList','企财宝企业流水列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf10b068-04c1-11e8-9883-fcaa14314cbe','historyGet','企财宝企业流水获取');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf10b068-04c1-11e8-9883-fcaa14314cbe','operateGet','企财宝企业审核信息获取');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf10b068-04c1-11e8-9883-fcaa14314cbe','operateCheck','企财宝企业审核');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf10b068-04c1-11e8-9883-fcaa14314cbe','virtualAccountBind','企财宝企业绑定虚拟账户');
INSERT INTO `bk_controller_method` 
VALUES (UUID(), 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'operateList', '企财宝企业审核列表', '0');
INSERT INTO `bk_controller_method` 
VALUES ('3de623c8-1d39-11e8-9686-98be942b6572', 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'changeFee', '企财宝企业设置费率', 0);
INSERT INTO `bk_controller_method` 
VALUES ('3deb087a-1d39-11e8-9686-98be942b6572', 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'operateFeeList', '企财宝企业费率操作列表', 0);
INSERT INTO `bk_controller_method` 
VALUES ('3df086ce-1d39-11e8-9686-98be942b6572', 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'operateFeeCheckList', '企财宝企业费率操作列表（管理员）', 0);
INSERT INTO `bk_controller_method` 
VALUES ('3df73bc2-1d39-11e8-9686-98be942b6572', 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'operateFeeGet', '企财宝企业费率操作信息获取', 0);
INSERT INTO `bk_controller_method` 
VALUES ('3e028770-1d39-11e8-9686-98be942b6572', 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'operateFeeEdit', '企财宝企业费率操作信息编辑', 0);
INSERT INTO `bk_controller_method` 
VALUES ('3e0878ba-1d39-11e8-9686-98be942b6572', 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'operateFeeDelete', '企财宝企业费率操作删除', 0);
INSERT INTO `bk_controller_method` 
VALUES ('3e0f7f34-1d39-11e8-9686-98be942b6572', 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'operateFeeSubmitCheck', '企财宝企业费率操作提交审核', 0);
INSERT INTO `bk_controller_method` 
VALUES ('3e14d6aa-1d39-11e8-9686-98be942b6572', 'cf10b068-04c1-11e8-9883-fcaa14314cbe', 'operateFeeCheck', '企财宝企业费率操作审核', 0);

INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf172a54-04c1-11e8-9883-fcaa14314cbe','list','企财宝财税服务列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf172a54-04c1-11e8-9883-fcaa14314cbe','get','企财宝财税服务获取');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf172a54-04c1-11e8-9883-fcaa14314cbe','check','企财宝财税服务审核');

INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf1c5ecc-04c1-11e8-9883-fcaa14314cbe','list','企财宝银行虚拟户列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf1c5ecc-04c1-11e8-9883-fcaa14314cbe','get','企财宝银行虚拟户获取');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf1c5ecc-04c1-11e8-9883-fcaa14314cbe','batchAdd','企财宝银行虚拟户批量添加');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf1c5ecc-04c1-11e8-9883-fcaa14314cbe','batchDelete','企财宝银行虚拟户批量删除');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf1c5ecc-04c1-11e8-9883-fcaa14314cbe','delete','企财宝银行虚拟户删除');


INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','recharge','企财宝企业充值');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','operateGet','企财宝企业交易操作获取');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','operateList','企财宝企业交易操作列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','operateCheckList','企财宝企业交易操作审核列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','operateEdit','企财宝企业交易操作修改');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','operateDelete','企财宝企业交易操作删除');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','operateSubmitCheck','企财宝企业交易操作提交审核');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8cdbb3ba-04dd-11e8-9883-fcaa14314cbe','operateCheck','企财宝企业交易操作审核');
INSERT INTO `bk_controller_method` 
VALUES ('98e7d01a-1d38-11e8-9686-98be942b6572', '8cdbb3ba-04dd-11e8-9883-fcaa14314cbe', 'expend', '企财宝企业费用扣除', 0);
INSERT INTO `bk_controller_method` 
VALUES ('98f033ea-1d38-11e8-9686-98be942b6572', '8cdbb3ba-04dd-11e8-9883-fcaa14314cbe', 'operateExpendEdit', '企财宝企业费用扣除操作修改', 0);

INSERT bk_menu(uuid,name,title,level,scode,pid,url,icon,sort) 
VALUES(UUID(),'qcbVirtualAccounts','银行虚拟账户管理',2,'00600068','ea511830-6880-4e50-90b0-cc8a82806853','backadmin/bankFictitiousAccountList.jsp','img/li0_3',7);

INSERT bk_menu(uuid,name,title,level,scode,pid,url,icon,sort) 
VALUES('fafe01a2-04c5-11e8-9883-fcaa14314cbe','qcbCompanyManage','企财宝网站管理',1,'0080',NULL,NULL,'img/LIP1',10);

INSERT bk_menu(uuid,name,title,level,scode,pid,url,icon,sort) 
VALUES(UUID(),'qcbCompany','企业用户管理',2,'00800081','fafe01a2-04c5-11e8-9883-fcaa14314cbe','backadmin/QCBcompanyList.jsp','img/li0_1',0);
INSERT bk_menu(uuid,name,title,level,scode,pid,url,icon,sort) 
VALUES(UUID(),'qcbCompanyFinance','企业财税申请',2,'00800082','fafe01a2-04c5-11e8-9883-fcaa14314cbe','backadmin/QCBfinanceAndTaxationOpen.jsp','img/li0_2',1);
INSERT bk_menu(uuid,name,title,level,scode,pid,url,icon,sort) 
VALUES(UUID(),'qcbCompanyOperateCheck','待审核事项&#40;审核&#41;',2,'00800083','fafe01a2-04c5-11e8-9883-fcaa14314cbe','backadmin/QCBRechargeAuditingCheck.jsp','img/li0_3',2);
INSERT bk_menu(uuid,name,title,level,scode,pid,url,icon,sort) 
VALUES(UUID(),'qcbCompanyOperate','待审核事项&#40;申请&#41;',2,'00800084','fafe01a2-04c5-11e8-9883-fcaa14314cbe','backadmin/QCBRechargeAuditing.jsp','img/li0_3',3);
INSERT INTO `bk_menu` 
VALUES ('9a97bf85-e450-48c9-aca0-111509021934', 'qcbCompanyWithdraw', '员工企业提现管理', 2, '00800088', 'fafe01a2-04c5-11e8-9883-fcaa14314cbe', 'backadmin/QCBhandDrawcash.jsp', 'img/li1_1', 8);
INSERT INTO `bk_menu` 
VALUES ('301ad7e8-0ec6-4422-ad7a-5cdedbb0bb0f', 'qcbCompanyFeeCheck', '费率设置审核', 2, '00800087', 'fafe01a2-04c5-11e8-9883-fcaa14314cbe', 'backadmin/QCBcompanyAccountRateAuditingCheck.jsp', 'img/li0_1', 7);
INSERT INTO `bk_menu` 
VALUES ('c4bf45a8-0aaf-42b6-ba55-92b747a59d2c', 'qcbCompanyFee', '费率设置申请', 2, '00800086', 'fafe01a2-04c5-11e8-9883-fcaa14314cbe', 'backadmin/QCBcompanyAccountRateAuditing.jsp', 'img/li0_1', 6);
INSERT INTO `bk_menu` 
VALUES ('4cd549f9-21ae-44df-9c6f-56842605cf80', 'qcbCompanyCheck', '企业用户审核', 2, '00800085', 'fafe01a2-04c5-11e8-9883-fcaa14314cbe', 'backadmin/QCBcompanyListCheck.jsp', 'img/li0_1', 0);
INSERT INTO `bk_menu` 
VALUES ('3d77d58d-eb3e-413e-a3b7-c651be4c4137', 'advertlist', '广告管理', 2, '00400048', '7a627841-1ad1-7196-8ad8-365ab128b514', 'backadmin/advertList.jsp', 'img/li0_3', 8);

ALTER TABLE `company_account_history`
ADD COLUMN `qcb_employee`  varchar(36) NULL AFTER `investor`;
ALTER TABLE `company_account_history`
ADD COLUMN `qcb_employee_history`  varchar(36) NULL AFTER `qcb_company_account_history`;


INSERT bk_controller(uuid,name,description) VALUES('cf1c5ecc-04c1-11e8-9883-fcaa14314cbe','advert','广告管理');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf172a54-04c1-11e8-9883-fcaa14314cbe','list','广告管理列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf172a54-04c1-11e8-9883-fcaa14314cbe','add','广告管理添加');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'cf172a54-04c1-11e8-9883-fcaa14314cbe','edit','广告管理编辑');


-------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------企财宝2.0---------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------
INSERT bk_controller(uuid,name,description) VALUES('8af51258-a75a-26e6-dd42-a1295a3729b5','qcbWithdraw','企财宝手动提现管理');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','employeeList','企财宝员工手动提现列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','employeeEdit','企财宝员工手动提现重新发起');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','employeeProcess','企财宝员工手动提现设置处理中');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','employeeExport','企财宝员工手动提现数据导出');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','employeeImport','企财宝员工手动提现数据导人');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','employeeRevoke','企财宝员工手动提现设置为失败');

INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','companyList','企财宝企业手动提现列表');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','companyEdit','企财宝企业手动提现重新发起');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','companyProcess','企财宝企业手动提现设置处理中');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','companyExport','企财宝企业手动提现数据导出');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','companyImport','企财宝企业手动提现数据导人');
INSERT bk_controller_method(uuid,controller,name,description) 
VALUES(UUID(),'8af51258-a75a-26e6-dd42-a1295a3729b5','companyRevoke','企财宝企业手动提现设置为失败');


ALTER TABLE `bank`
ADD COLUMN `credit_inquiry_phone`  varchar(50) NULL COMMENT '信用卡服务电话' AFTER `code_num`,
ADD COLUMN `credit_inquiry_command`  varchar(50) NULL COMMENT '信用卡账单查询命令' AFTER `credit_inquiry_phone`;


ALTER TABLE `qcb_employee_bankcard`
ADD COLUMN `flag_remind`  tinyint(1) NULL DEFAULT NULL COMMENT '是否提醒' AFTER `binding_card_cardholder`,
ADD COLUMN `remind_time`  int(11) NULL DEFAULT NULL COMMENT '还款日' AFTER `flag_remind`;


ALTER TABLE `qcb_employee_bankcard`
ADD COLUMN `binding_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '绑卡ID' AFTER `remind_time`,
ADD COLUMN `type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'debit' COMMENT '类型' AFTER `bank`;

ALTER TABLE `qcb_employee`
DROP COLUMN `wechat_name`;

-------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------3.22---------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------
ALTER TABLE `fund`
ADD COLUMN `account_balance`  decimal(20,8) NOT NULL DEFAULT 0 AFTER `net_worth`;

ALTER TABLE `company_account_history`
ADD COLUMN `fund`  varchar(36) NULL COMMENT '相关基金' AFTER `bank_financial_product`;

ALTER TABLE `qcb_employee`
ADD COLUMN `current_account`  decimal(20,8) NOT NULL DEFAULT 0 COMMENT '活期账户' AFTER `account_balance`,
ADD COLUMN `current_account_yesterday`  decimal(20,8) NOT NULL DEFAULT 0 COMMENT '昨日活期账户' AFTER `current_account`,
ADD COLUMN `flag_current`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否自动转入' AFTER `current_account`;

ALTER TABLE `investor`
ADD COLUMN `current_account`  decimal(20,8) NOT NULL DEFAULT 0 COMMENT '活期账户' AFTER `account_balance`,
ADD COLUMN `current_account_yesterday`  decimal(20,8) NOT NULL DEFAULT 0 COMMENT '昨日活期账户' AFTER `current_account`,
ADD COLUMN `flag_current`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否自动转入' AFTER `current_account`;

-- ----------------------------
-- Table structure for `fund_publish`
-- ----------------------------
DROP TABLE IF EXISTS `fund_publish`;
CREATE TABLE `fund_publish` (
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
  CONSTRAINT `fund_publish_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `bk_operator` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_publish
-- ----------------------------

-- ----------------------------
-- Table structure for `fund_publish_daily`
-- ----------------------------
DROP TABLE IF EXISTS `fund_publish_daily`;
CREATE TABLE `fund_publish_daily` (
  `uuid` varchar(36) NOT NULL,
  `fund_publish` varchar(36) NOT NULL,
  `netvalue` decimal(16,8) NOT NULL,
  `statistime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createtime` datetime NOT NULL COMMENT '净值录入时间',
  `creator` varchar(36) NOT NULL COMMENT '录入人',
  PRIMARY KEY (`uuid`),
  KEY `ak_key_2` (`fund_publish`) USING BTREE,
  CONSTRAINT `fund_publish_daily_ibfk_1` FOREIGN KEY (`fund_publish`) REFERENCES `fund_publish` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_publish_daily
-- ----------------------------
-- ----------------------------
-- Table structure for `fund_operate`
-- ----------------------------
DROP TABLE IF EXISTS `fund_operate`;
CREATE TABLE `fund_operate` (
  `uuid` varchar(36) NOT NULL,
  `fund` varchar(36) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `value` text NOT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `checker` varchar(36) DEFAULT NULL,
  `checktime` timestamp NULL DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `submittime` timestamp NULL DEFAULT NULL COMMENT '提交审核时间',
  `old` text,
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `fund` (`fund`),
  CONSTRAINT `fund_operate_ibfk_3` FOREIGN KEY (`fund`) REFERENCES `fund` (`uuid`),
  CONSTRAINT `fund_operate_ibfk_2` FOREIGN KEY (`checker`) REFERENCES `bk_operator` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_operate
-- ----------------------------

-- ----------------------------
-- Table structure for `fund_publish_operate`
-- ----------------------------
DROP TABLE IF EXISTS `fund_publish_operate`;
CREATE TABLE `fund_publish_operate` (
  `uuid` varchar(36) NOT NULL,
  `fund_publish` varchar(36) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `value` text NOT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `checker` varchar(36) DEFAULT NULL,
  `checktime` timestamp NULL DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `submittime` timestamp NULL DEFAULT NULL COMMENT '提交审核时间',
  `old` text,
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `fund` (`fund_publish`),
  CONSTRAINT `fund_publish_operate_ibfk_3` FOREIGN KEY (`fund_publish`) REFERENCES `fund_publish` (`uuid`),
  CONSTRAINT `fund_publish_operate_ibfk_2` FOREIGN KEY (`checker`) REFERENCES `bk_operator` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_publish_operate
-- ----------------------------

-- ----------------------------
-- Table structure for `fund_invest`
-- ----------------------------
DROP TABLE IF EXISTS `fund_invest`;
CREATE TABLE `fund_invest` (
  `uuid` varchar(36) NOT NULL,
  `fund` varchar(36) NOT NULL COMMENT '投资产品编号',
  `fund_publish` varchar(36) NOT NULL,
  `company_account` varchar(36) NOT NULL,
  `account_balance` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `total_amount` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '投资金额',
  `type` varchar(20) NOT NULL COMMENT '操作类型',
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_invest
-- ----------------------------

-- ----------------------------
-- Table structure for `fund_invest_operate`
-- ----------------------------
DROP TABLE IF EXISTS `fund_invest_operate`;
CREATE TABLE `fund_invest_operate` (
  `uuid` varchar(36) NOT NULL,
  `fund_invest` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL,
  `value` text NOT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `checker` varchar(36) DEFAULT NULL,
  `checktime` timestamp NULL DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `submittime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_BFPO_CREATOR` (`creator`),
  KEY `FK_BFPO_CHECKER` (`checker`),
  KEY `fk_BFP_OPERATE` (`fund_invest`),
  CONSTRAINT `fund_invest_operate_ibfk_1` FOREIGN KEY (`checker`) REFERENCES `bk_operator` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_invest_operate
-- ----------------------------


-------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------5.3---------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------
ALTER TABLE `qcb_company_account`
ADD COLUMN `virtual_account_type`  varchar(20) NULL AFTER `total_return`;
update qcb_company_account set virtual_account_type='virtual' where qcb_virtual_accounts is not null


------------------------------------------20180622
ALTER TABLE `company_account_history`
ADD COLUMN `account_balance`  decimal(20,8) NULL COMMENT '本次余额' AFTER `qcb_employee_history`;

ALTER TABLE `company_account_history`
ADD COLUMN `account_balance_in`  decimal(20,8) NULL COMMENT '本次余额（入账账户）' AFTER `account_balance`;


------------------------------------------20180726
DELETE mp FROM  bk_role_menu_permission mp
LEFT JOIN bk_menu m on mp.menu = m.uuid 
where m.NAME='fund';

DELETE from `bk_menu` where NAME='fund';

INSERT INTO `bk_menu` 
VALUES ('37fc6a11-863e-31ed-b9a2-673bf10c664a', 'current', '活期理财管理', '1', '0090', null, null, 'img/li0_3',5);

INSERT INTO `bk_menu` 
VALUES ('0ae36f97-90b4-11e8-95fb-fcaa14314cbe', 'fund', '投资产品管理', '2', '00900091', '37fc6a11-863e-31ed-b9a2-673bf10c664a', 'backadmin/fundList.jsp', 'img/li0_1',0);

INSERT INTO `bk_menu` 
VALUES ('0ae95e77-90b4-11e8-95fb-fcaa14314cbe', 'fundPublish', '发布产品管理', '2', '00900092', '37fc6a11-863e-31ed-b9a2-673bf10c664a', 'backadmin/fundPublishList.jsp', 'img/li0_2',1);

INSERT INTO `bk_menu` 
VALUES ('e1bb352c-90b4-11e8-95fb-fcaa14314cbe', 'fundOperateCheck', '待审核事项&#40;审核&#41;', '2', '00900093', '37fc6a11-863e-31ed-b9a2-673bf10c664a', 'backadmin/fundOperateCheckList.jsp', 'img/li0_3',2);

INSERT INTO `bk_menu` 
VALUES ('e1c159f8-90b4-11e8-95fb-fcaa14314cbe', 'fundOperate', '待审核事项&#40;申请&#41;', '2', '00900094', '37fc6a11-863e-31ed-b9a2-673bf10c664a', 'backadmin/fundOperateList.jsp', 'img/li0_4',3);

delete cp FROM  bk_role_controller_permission cp
LEFT JOIN bk_controller_method m on cp.method = m.uuid 
where m.name like 'netvalue%' and m.description like '基金%';

delete from bk_controller_method where name like 'netvalue%' and description like '基金%';

INSERT INTO bk_controller_method VALUES ('9df394ca-9468-11e8-95fb-fcaa14314cbe','26193227-f81a-11e6-ada9-d0295a4759e7','operateGet','活期理财操作获取',0);
INSERT INTO bk_controller_method VALUES ('73068d62-9469-11e8-95fb-fcaa14314cbe','26193227-f81a-11e6-ada9-d0295a4759e7','operateList','活期理财操作列表',0);
INSERT INTO bk_controller_method VALUES ('730b7b32-9469-11e8-95fb-fcaa14314cbe','26193227-f81a-11e6-ada9-d0295a4759e7','operateCheckList','活期理财操作列表&#40;管理员&#41;',0);
INSERT INTO bk_controller_method VALUES ('731064f9-9469-11e8-95fb-fcaa14314cbe','26193227-f81a-11e6-ada9-d0295a4759e7','operateEdit','活期理财操作修改',0);
INSERT INTO bk_controller_method VALUES ('7315aa07-9469-11e8-95fb-fcaa14314cbe','26193227-f81a-11e6-ada9-d0295a4759e7','operateDelete','活期理财操作删除',0);
INSERT INTO bk_controller_method VALUES ('731a90a2-9469-11e8-95fb-fcaa14314cbe','26193227-f81a-11e6-ada9-d0295a4759e7','operateSubmitCheck','活期理财操作提交审核',0);
INSERT INTO bk_controller_method VALUES ('7320e2cb-9469-11e8-95fb-fcaa14314cbe','26193227-f81a-11e6-ada9-d0295a4759e7','operateCheck','活期理财操作审核',0);

INSERT INTO bk_controller VALUES('7446c3fe-946a-11e8-95fb-fcaa14314cbe', 'fundPublish','募集活期理财管理',10001);
INSERT INTO bk_controller_method VALUES ('e15ac33b-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','getAccount','募集活期理财获取余额',0);
INSERT INTO bk_controller_method VALUES ('e15efbb9-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','get','募集活期理财获取',0);
INSERT INTO bk_controller_method VALUES ('e1649846-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','edit','募集活期理财修改',0);
INSERT INTO bk_controller_method VALUES ('e16a87a4-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','netvaluelist','募集活期理财净值列表',0);
INSERT INTO bk_controller_method VALUES ('e1724421-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','netvalueGet','募集活期理财净值获取',0);
INSERT INTO bk_controller_method VALUES ('e1777a7a-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','netvalueEdit','募集活期理财净值编辑',0);
INSERT INTO bk_controller_method VALUES ('e17cd5f6-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','operateNetvalueGet','募集活期理财操作净值获取',0);
INSERT INTO bk_controller_method VALUES ('e1836f51-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','operateGet','募集活期理财操作获取',0);
INSERT INTO bk_controller_method VALUES ('e189c521-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','operateEdit','募集活期理财操作修改',0);
INSERT INTO bk_controller_method VALUES ('e18f9544-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','operateDelete','募集活期理财操作删除',0);
INSERT INTO bk_controller_method VALUES ('e194ecea-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','operateList','募集活期理财操作列表',0);
INSERT INTO bk_controller_method VALUES ('e19b31cc-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','operateCheckList','募集活期理财操作列表&#40;管理员&#41;',0);
INSERT INTO bk_controller_method VALUES ('e1a1df0b-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','operateSubmitCheck','募集活期理财提交审核',0);
INSERT INTO bk_controller_method VALUES ('e1a8ef84-9486-11e8-95fb-fcaa14314cbe','7446c3fe-946a-11e8-95fb-fcaa14314cbe','operateCheck','募集活期理财审核',0);

INSERT INTO bk_controller VALUES('8e8c55df-9487-11e8-95fb-fcaa14314cbe', 'fundInvest','活期理财投资管理',10002);
INSERT INTO bk_controller_method VALUES ('cb4d1386-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','list','活期理财投资列表',0);
INSERT INTO bk_controller_method VALUES ('cb524fa4-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','invest','活期理财投资',0);
INSERT INTO bk_controller_method VALUES ('cb576a3c-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','redeem','活期理财赎回',0);
INSERT INTO bk_controller_method VALUES ('cb5c54c7-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','operateGet','活期理财投资操作获取',0);
INSERT INTO bk_controller_method VALUES ('cb6185d0-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','operateList','活期理财投资操作列表',0);
INSERT INTO bk_controller_method VALUES ('cb682e28-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','operateCheckList','活期理财投资操作列表&#40;管理员&#41;',0);
INSERT INTO bk_controller_method VALUES ('cb6ed7f8-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','operateDelete','活期理财投资操作删除',0);
INSERT INTO bk_controller_method VALUES ('cb757f6e-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','operateSubmitCheck','活期理财投资提交审核',0);
INSERT INTO bk_controller_method VALUES ('cb7bcf92-949a-11e8-95fb-fcaa14314cbe','8e8c55df-9487-11e8-95fb-fcaa14314cbe','operateCheck','活期理财投资审核',0);

-- ----------------------------
-- Table structure for `shbx_user`
-- ----------------------------
DROP TABLE IF EXISTS `shbx_user`;
CREATE TABLE `shbx_user` (
  `uuid` varchar(36) NOT NULL,
  `realname` varchar(50) DEFAULT NULL COMMENT '真实名称',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `login_password` varchar(300) DEFAULT NULL COMMENT '登录密码',
  `secret_password` varchar(300) DEFAULT NULL COMMENT '支付密码',
  `secret_password_flag` tinyint(1) NOT NULL COMMENT '是否设置支付密码',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `total_invest` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '当前总投资',
  `total_return` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '历史总收益',
  `account_balance` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '账户余额',
  `current_account` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '活期账户',
  `current_account_yesterday` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '昨日活期账户',
  `flag_current` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否自动转入',
  `openid` varchar(30) DEFAULT NULL COMMENT '微信唯一标识',
  `wechat_icon` varchar(200) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL COMMENT '性别',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(30) DEFAULT NULL COMMENT '最后登录IP',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `idcard` (`idcard`),
  UNIQUE KEY `openid` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `shbx_insured`
-- ----------------------------
DROP TABLE IF EXISTS `shbx_insured`;
CREATE TABLE `shbx_insured` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `idcard` varchar(20) NOT NULL COMMENT '身份证号',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `householdtype` varchar(30) NOT NULL COMMENT '户口性质',
  `householdarea` varchar(36) NOT NULL COMMENT '户口所在地',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `education` varchar(20) DEFAULT NULL COMMENT '学历',
  `worktime` datetime DEFAULT NULL COMMENT '参加工作时间',
  `nationality` varchar(20) DEFAULT NULL COMMENT '民族',
  `duty` varchar(20) DEFAULT NULL COMMENT '个人身份',
  `status` varchar(20) NOT NULL COMMENT '状态',
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `shbx_user_insured`
-- ----------------------------
DROP TABLE IF EXISTS `shbx_user_insured`;
CREATE TABLE `shbx_user_insured` (
  `uuid` varchar(36) NOT NULL,
  `shbx_user` varchar(36) NOT NULL,
  `shbx_insured` varchar(36) NOT NULL,
  `status` varchar(20) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `shbx_orderinfo_by_thirdparty`
-- ----------------------------
DROP TABLE IF EXISTS `shbx_orderinfo_by_thirdparty`;
CREATE TABLE `shbx_orderinfo_by_thirdparty` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT '支付平台',
  `order_num` varchar(32) NOT NULL COMMENT '商户订单号',
  `pay_num` varchar(32) DEFAULT NULL COMMENT '支付平台订单流水号',
  `prepay_id` varchar(64) DEFAULT NULL COMMENT '预支付交易会话标识',
  `body` varchar(100) DEFAULT NULL COMMENT '交易内容描述',
  `total_fee` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '支付金额',
  `pay_source` varchar(36) NOT NULL COMMENT '支付用户openid/buyer_id',
  `pay_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易完成时间',
  `status` varchar(20) NOT NULL COMMENT '交易状态',
  `message` varchar(128) NOT NULL COMMENT '交易结果反馈信息',
  `err_code_des` varchar(64) DEFAULT NULL,
  `err_code` varchar(64) DEFAULT NULL,
  `account` varchar(36) NOT NULL COMMENT '相关账户',
  `account_type` varchar(10) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '交易发起时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `union_order_num` (`order_num`) USING BTREE,
  KEY `index_order_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `shbx_user_bankcard`
-- ----------------------------
DROP TABLE IF EXISTS `shbx_user_bankcard`;
CREATE TABLE `shbx_user_bankcard` (
  `uuid` varchar(36) NOT NULL,
  `shbx_user` varchar(36) NOT NULL COMMENT '员工',
  `bank` varchar(36) NOT NULL COMMENT '银行',
  `type` varchar(20) NOT NULL,
  `branch_bank` varchar(36) DEFAULT NULL COMMENT '支行',
  `bank_account_name` varchar(50) DEFAULT NULL COMMENT '开户名',
  `binding_bank_card` varchar(64) NOT NULL COMMENT '银行卡号',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '绑定时间',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `binding_card_phone` varchar(11) NOT NULL COMMENT '预留手机号',
  `binding_card_cardholder` varchar(50) NOT NULL COMMENT '持卡人姓名',
  `flag_remind` tinyint(1) DEFAULT NULL COMMENT '是否提醒',
  `remind_time` int(11) DEFAULT NULL COMMENT '还款日',
  `binding_id` varchar(50) DEFAULT '' COMMENT '绑卡ID',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `shbx_security_order`
-- ----------------------------
DROP TABLE IF EXISTS `shbx_security_order`;
CREATE TABLE `shbx_security_order` (
  `uuid` varchar(36) NOT NULL,
  `order_number` varchar(36) NOT NULL COMMENT '订单编号',
  `shbx_insured` varchar(36) NOT NULL COMMENT '参保人信息外键',
  `starttime` varchar(50) NOT NULL COMMENT '参保开始时间',
  `duration` varchar(10) NOT NULL COMMENT '参保时长',
  `housing_fund` decimal(20,2) DEFAULT '0.00' COMMENT '公积金基数',
  `cardinal_number` decimal(20,2) NOT NULL COMMENT '社保基数',
  `benefits` decimal(20,8) NOT NULL COMMENT '代收社保费',
  `service_fee` decimal(20,8) NOT NULL COMMENT '订单服务费',
  `shbx_user_history` varchar(36) NOT NULL COMMENT '订单支付信息外键',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `status` varchar(20) NOT NULL COMMENT '订单处理状态',
  `creator` varchar(36) NOT NULL COMMENT '订单创建人',
  `company` varchar(100) DEFAULT '' COMMENT '参保公司',
  `receipt` varchar(500) DEFAULT '' COMMENT '参保凭证',
  `insured_type` varchar(20) NOT NULL COMMENT '参保类型',
  `source_company` varchar(100) DEFAULT '' COMMENT '所属公司',
  `process_creator` varchar(36) DEFAULT NULL COMMENT '处理人',
  `process_createtime` timestamp NULL DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UK_SHBX_SECORDER_NUMBER` (`order_number`) USING BTREE,
  KEY `AK_SHBX_SEC_shbx_insured` (`shbx_insured`) USING BTREE,
  KEY `ak_shbx_secstatus` (`status`) USING BTREE,
  KEY `ak_shbx_seccreator` (`creator`) USING BTREE,
  KEY `ak_shbx_seccompany` (`company`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `shbx_user_history`
-- ----------------------------
DROP TABLE IF EXISTS `shbx_user_history`;
CREATE TABLE `shbx_user_history` (
  `uuid` varchar(36) NOT NULL,
  `shbx_user` varchar(36) NOT NULL COMMENT '员工',
  `order_id` varchar(36) DEFAULT NULL COMMENT '订单',
  `order_num` varchar(32) NOT NULL COMMENT '订单号',
  `order_type` varchar(36) DEFAULT NULL COMMENT '订单类型',
  `account_balance` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '上次所剩余额',
  `income` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '收入金额',
  `pay` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '支出金额',
  `status` varchar(20) NOT NULL COMMENT '状态',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(10) NOT NULL COMMENT '账单类型',
  `poundage` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '手续费',
  `bankcard` varchar(36) DEFAULT NULL COMMENT '相关银行卡',
  `company_account` varchar(36) DEFAULT NULL COMMENT '相关企业账户',
  `product` varchar(36) DEFAULT NULL COMMENT '相关理财产品',
  `product_type` varchar(20) DEFAULT NULL COMMENT '相关理财产品类型',
  `processing_status` varchar(20) DEFAULT NULL COMMENT '处理状态',
  `process_company_account` varchar(36) DEFAULT NULL COMMENT '处理账户',
  `process_creator` varchar(36) DEFAULT NULL COMMENT '处理人',
  `process_createtime` datetime DEFAULT NULL COMMENT '处理时间',
  `shbx_security_order` varchar(36) DEFAULT NULL COMMENT '参保订单信息外键',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `bk_menu` VALUES ('d2e5a696-9aab-11e8-95fb-fcaa14314cbe', 'shbx', '社保熊管理', '1', '0100', null, null, 'img/li0_3',99999);
INSERT INTO `bk_menu` VALUES ('4bf2600c-9aac-11e8-95fb-fcaa14314cbe', 'shbxUser', '社保熊用户管理', '2', '01000101', 'd2e5a696-9aab-11e8-95fb-fcaa14314cbe', 'backadmin/shbxUserList.jsp', 'img/li0_1',0);
INSERT INTO `bk_menu` VALUES ('8022ca7d-9aac-11e8-95fb-fcaa14314cbe', 'shbxOrder', '社保熊订单管理', '2', '01000102', 'd2e5a696-9aab-11e8-95fb-fcaa14314cbe', 'backadmin/shbxOrderList.jsp', 'img/li0_2',1);

ALTER TABLE `shbx_user`
ADD COLUMN `realname_auth_flag`  tinyint(1) NOT NULL DEFAULT 0 AFTER `secret_password_flag`;

ALTER TABLE `company_account_history`
ADD COLUMN `shbx_user_history`  varchar(36) NULL COMMENT '社保熊用户流水' AFTER `account_balance_in`,
ADD COLUMN `shbx_user`  varchar(36) NULL COMMENT '社保熊用户ID' AFTER `shbx_user_history`;

-------------------------------------------------------------------
-----------------------------2018.圣诞节---------------------------
-------------------------------------------------------------------
CREATE TABLE `qcb_notice` (
  `uuid` varchar(36) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qcb_notice_employee` (
  `uuid` varchar(36) NOT NULL,
  `qcb_notice` varchar(36) NOT NULL,
  `qcb_employee` varchar(36) NOT NULL,
  `is_show` tinyint(1) NOT NULL,
  `is_read` tinyint(1) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

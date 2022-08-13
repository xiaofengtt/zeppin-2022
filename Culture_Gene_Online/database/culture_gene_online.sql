/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : culture_gene_online

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2016-04-13 17:22:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `level` int(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0 停用  1 正常',
  `scode` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reference_1` (`parent`),
  CONSTRAINT `fk_reference_1` FOREIGN KEY (`parent`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Records of category
-- ----------------------------

-- ----------------------------
-- Table structure for `funcation`
-- ----------------------------
DROP TABLE IF EXISTS `funcation`;
CREATE TABLE `funcation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `PATH` varchar(200) DEFAULT NULL,
  `LEVEL` tinyint(4) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `PARENT` int(11) DEFAULT NULL,
  `SCODE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_REFERENCE_43` (`PARENT`),
  CONSTRAINT `funcation_ibfk_1` FOREIGN KEY (`PARENT`) REFERENCES `funcation` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of funcation
-- ----------------------------
INSERT INTO `funcation` VALUES ('1', '用户管理', null, '1', '1', null, null);
INSERT INTO `funcation` VALUES ('2', '专家用户', '../admin/specialist.html', '2', '1', '1', null);
INSERT INTO `funcation` VALUES ('3', '普通用户', '../admin/userManage.html', '2', '1', '1', null);
INSERT INTO `funcation` VALUES ('4', '资源管理', null, '1', '1', null, null);
INSERT INTO `funcation` VALUES ('5', '分类管理', '../admin/category.html', '2', '1', '4', null);
INSERT INTO `funcation` VALUES ('6', '素材管理', '../admin/resource.html', '2', '1', '4', null);

-- ----------------------------
-- Table structure for `keyword`
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(200) NOT NULL,
  `user` int(11) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_15` (`user`),
  CONSTRAINT `fk_reference_15` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='搜索词';

-- ----------------------------
-- Records of keyword
-- ----------------------------

-- ----------------------------
-- Table structure for `national`
-- ----------------------------
DROP TABLE IF EXISTS `national`;
CREATE TABLE `national` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 COMMENT='民族表';

-- ----------------------------
-- Records of national
-- ----------------------------
INSERT INTO `national` VALUES ('10', '其他');
INSERT INTO `national` VALUES ('11', '汉族');
INSERT INTO `national` VALUES ('12', '蒙古族');
INSERT INTO `national` VALUES ('13', '回族');
INSERT INTO `national` VALUES ('14', '藏族');
INSERT INTO `national` VALUES ('15', '维吾尔族');
INSERT INTO `national` VALUES ('16', '苗族');
INSERT INTO `national` VALUES ('17', '彝族');
INSERT INTO `national` VALUES ('18', '壮族');
INSERT INTO `national` VALUES ('19', '满族');
INSERT INTO `national` VALUES ('21', '朝鲜族');
INSERT INTO `national` VALUES ('22', '达斡尔族');
INSERT INTO `national` VALUES ('23', '鄂温克族');
INSERT INTO `national` VALUES ('24', '鄂伦春族');
INSERT INTO `national` VALUES ('25', '赫哲族');
INSERT INTO `national` VALUES ('31', '土族');
INSERT INTO `national` VALUES ('32', '撒拉族');
INSERT INTO `national` VALUES ('33', '东乡族');
INSERT INTO `national` VALUES ('34', '保安族');
INSERT INTO `national` VALUES ('35', '裕固族');
INSERT INTO `national` VALUES ('36', '哈萨克族');
INSERT INTO `national` VALUES ('37', '柯尔克孜族');
INSERT INTO `national` VALUES ('38', '乌孜别克族');
INSERT INTO `national` VALUES ('41', '塔吉克族');
INSERT INTO `national` VALUES ('42', '塔塔尔族');
INSERT INTO `national` VALUES ('43', '锡伯族');
INSERT INTO `national` VALUES ('44', '俄罗斯族');
INSERT INTO `national` VALUES ('51', '瑶族');
INSERT INTO `national` VALUES ('52', '白族');
INSERT INTO `national` VALUES ('53', '傣族');
INSERT INTO `national` VALUES ('54', '哈民族');
INSERT INTO `national` VALUES ('55', '佤族');
INSERT INTO `national` VALUES ('56', '傈傈族');
INSERT INTO `national` VALUES ('57', '纳西族');
INSERT INTO `national` VALUES ('58', '拉祜族');
INSERT INTO `national` VALUES ('59', '景颇族');
INSERT INTO `national` VALUES ('61', '布朗族');
INSERT INTO `national` VALUES ('62', '阿昌族');
INSERT INTO `national` VALUES ('63', '怒族');
INSERT INTO `national` VALUES ('64', '德昂族');
INSERT INTO `national` VALUES ('65', '独龙族');
INSERT INTO `national` VALUES ('66', '普米族');
INSERT INTO `national` VALUES ('67', '门巴族');
INSERT INTO `national` VALUES ('68', '布依族');
INSERT INTO `national` VALUES ('69', '水族');
INSERT INTO `national` VALUES ('71', '仡佬族');
INSERT INTO `national` VALUES ('72', '侗族');
INSERT INTO `national` VALUES ('73', '土家族');
INSERT INTO `national` VALUES ('74', '羌族');
INSERT INTO `national` VALUES ('75', '仫佬族');
INSERT INTO `national` VALUES ('76', '毛南族');
INSERT INTO `national` VALUES ('77', '珞巴族');
INSERT INTO `national` VALUES ('78', '基诺族');
INSERT INTO `national` VALUES ('81', '黎族');
INSERT INTO `national` VALUES ('82', '京族');
INSERT INTO `national` VALUES ('83', '畲族');
INSERT INTO `national` VALUES ('84', '高山族');

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `level` int(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `owner` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `scode` varchar(100) NOT NULL,
  `comment` text,
  `national` int(11) DEFAULT NULL,
  `meaning` varchar(1000) DEFAULT NULL,
  `type` varchar(100) NOT NULL,
  `size` bigint(20) NOT NULL,
  `ratio` varchar(100) DEFAULT NULL,
  `source` int(11) NOT NULL COMMENT '1其他 2来自互联网 3现场拍照 4出版物扫描',
  `status` int(11) NOT NULL COMMENT '0草稿 1未审核 2已审核 3未通过',
  `url` varchar(200) NOT NULL,
  `eminent` int(11) NOT NULL DEFAULT '0',
  `is_object` int(11) NOT NULL,
  `source_path` varchar(100) DEFAULT NULL,
  `source_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reference_10` (`parent`),
  KEY `fk_reference_3` (`category`),
  KEY `fk_reference_5` (`owner`),
  KEY `fk_reference_9` (`national`),
  CONSTRAINT `fk_reference_10` FOREIGN KEY (`parent`) REFERENCES `resource` (`id`),
  CONSTRAINT `fk_reference_3` FOREIGN KEY (`category`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_reference_5` FOREIGN KEY (`owner`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_reference_9` FOREIGN KEY (`national`) REFERENCES `national` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of resource
-- ----------------------------

-- ----------------------------
-- Table structure for `resource_custom_tag`
-- ----------------------------
DROP TABLE IF EXISTS `resource_custom_tag`;
CREATE TABLE `resource_custom_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `value` varchar(40) NOT NULL,
  `user` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `fk_rct_r` (`resource`),
  KEY `fk_rct_u` (`user`),
  CONSTRAINT `fk_rct_r` FOREIGN KEY (`resource`) REFERENCES `resource` (`id`),
  CONSTRAINT `fk_rct_u` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource_custom_tag
-- ----------------------------

-- ----------------------------
-- Table structure for `resource_tag`
-- ----------------------------
DROP TABLE IF EXISTS `resource_tag`;
CREATE TABLE `resource_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `tag` varchar(40) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_1` (`tag`),
  KEY `fk_reference_6` (`resource`),
  KEY `fk_reference_8` (`user`),
  CONSTRAINT `fk_reference_6` FOREIGN KEY (`resource`) REFERENCES `resource` (`id`),
  CONSTRAINT `fk_reference_8` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源标引信息表';

-- ----------------------------
-- Records of resource_tag
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员');
INSERT INTO `role` VALUES ('2', '专家');
INSERT INTO `role` VALUES ('3', '普通用户');

-- ----------------------------
-- Table structure for `role_funcation`
-- ----------------------------
DROP TABLE IF EXISTS `role_funcation`;
CREATE TABLE `role_funcation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE` int(11) NOT NULL,
  `FUNCATION` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_KEY_2` (`ROLE`,`FUNCATION`),
  KEY `FK_REFERENCE_44` (`FUNCATION`),
  CONSTRAINT `role_funcation_ibfk_1` FOREIGN KEY (`ROLE`) REFERENCES `role` (`id`),
  CONSTRAINT `role_funcation_ibfk_2` FOREIGN KEY (`FUNCATION`) REFERENCES `funcation` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_funcation
-- ----------------------------
INSERT INTO `role_funcation` VALUES ('1', '1', '2');
INSERT INTO `role_funcation` VALUES ('2', '1', '3');
INSERT INTO `role_funcation` VALUES ('3', '1', '5');
INSERT INTO `role_funcation` VALUES ('4', '1', '6');
INSERT INTO `role_funcation` VALUES ('5', '2', '6');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `role` int(11) NOT NULL COMMENT '0超级管理员  1普通用户   2文化专家 ',
  `status` int(11) NOT NULL COMMENT '0 停用 1正常',
  `password` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `company` varchar(200) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fk_reference_only` (`role`,`phone`) USING BTREE,
  CONSTRAINT `fk_reference_2` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '秦龙', '1', '1', '123456', '18888888888', null, null, null);
INSERT INTO `user` VALUES ('2', '秦老师', '2', '1', '581016', '18600581016', '392091925@qq.com', '北京邮电大学', '教师');
INSERT INTO `user` VALUES ('3', '王老师', '2', '1', '581015', '18600581015', '2898920@qq.com', '北京科技大学', '教授');
INSERT INTO `user` VALUES ('4', '李老师', '2', '1', '581111', '18600581111', '98873848@qq.com', '北京交通大学', '副教授');
INSERT INTO `user` VALUES ('5', '张老师', '2', '1', '236578', '17891236578', '72837827@qq.com', '北京联合大学', '院士');

-- ----------------------------
-- Table structure for `user_download_resource`
-- ----------------------------
DROP TABLE IF EXISTS `user_download_resource`;
CREATE TABLE `user_download_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `resource` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_12` (`user`),
  KEY `fk_reference_14` (`resource`),
  CONSTRAINT `fk_reference_12` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_reference_14` FOREIGN KEY (`resource`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户下载表';

-- ----------------------------
-- Records of user_download_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `user_love_resource`
-- ----------------------------
DROP TABLE IF EXISTS `user_love_resource`;
CREATE TABLE `user_love_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `resource` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_11` (`user`),
  KEY `fk_reference_13` (`resource`),
  CONSTRAINT `fk_reference_11` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_reference_13` FOREIGN KEY (`resource`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收藏表';

-- ----------------------------
-- Records of user_love_resource
-- ----------------------------

/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : china_mobile_media

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2016-05-12 20:18:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` varchar(36) NOT NULL,
  `level` int(11) NOT NULL,
  `scode` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `parent` varchar(36) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'stoped/normal',
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_1` (`parent`),
  KEY `fk_reference_21` (`creator`),
  CONSTRAINT `fk_reference_1` FOREIGN KEY (`parent`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_reference_21` FOREIGN KEY (`creator`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------


-- ----------------------------
-- Table structure for `commodity`
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `id` varchar(36) NOT NULL,
  `url` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `cover` varchar(36) DEFAULT NULL,
  `price` decimal(15,2) NOT NULL,
  `original_price` decimal(15,2) DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(20) NOT NULL COMMENT 'deleted/normal',
  PRIMARY KEY (`id`),
  KEY `fk_reference_19` (`creator`),
  CONSTRAINT `fk_reference_19` FOREIGN KEY (`creator`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of commodity
-- ----------------------------

-- ----------------------------
-- Table structure for `commodity_display360`
-- ----------------------------
DROP TABLE IF EXISTS `commodity_display360`;
CREATE TABLE `commodity_display360` (
  `id` varchar(36) NOT NULL,
  `commodity` varchar(36) NOT NULL,
  `display_index` int(11) NOT NULL,
  `display_image` varchar(36) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_14` (`commodity`),
  KEY `fk_reference_15` (`display_image`),
  KEY `fk_reference_18` (`creator`),
  CONSTRAINT `fk_reference_14` FOREIGN KEY (`commodity`) REFERENCES `commodity` (`id`),
  CONSTRAINT `fk_reference_15` FOREIGN KEY (`display_image`) REFERENCES `resource` (`id`),
  CONSTRAINT `fk_reference_18` FOREIGN KEY (`creator`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of commodity_display360
-- ----------------------------

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` varchar(36) NOT NULL,
  `type` varchar(10) NOT NULL COMMENT '1视频 2图片',
  `path` varchar(200) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `filetype` varchar(20) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `dpi` varchar(50) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'deleted/normal',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL COMMENT 'admin/editor',
  `status` varchar(20) NOT NULL DEFAULT '1' COMMENT 'stoped/normal',
  `password` varchar(128) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `department` varchar(200) DEFAULT NULL,
  `jobtitle` varchar(100) DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ak_key_2` (`phone`),
  UNIQUE KEY `ak_key_3` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '秦龙', '1', 'normal', '581016', '18600581016', 'qinlong@zeppin.cn', '技术支持部', '经理', '1', '2016-05-15 10:32:50');

-- ----------------------------
-- Table structure for `videoinfo`
-- ----------------------------
DROP TABLE IF EXISTS `videoinfo`;
CREATE TABLE `videoinfo` (
  `id` varchar(36) NOT NULL,
  `title` varchar(200) NOT NULL,
  `context` text,
  `tag` varchar(200) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'deleted/uploaded/transcoding/unchecked/failed/checked\r\n            ',
  `thumbnail` varchar(200) DEFAULT NULL,
  `video` varchar(200) DEFAULT NULL,
  `time_length` varchar(20) DEFAULT NULL COMMENT '格式：hh:mm:ss.xx',
  `transcoding_flag` tinyint(1) DEFAULT NULL,
  `original_video` varchar(36) DEFAULT NULL COMMENT '转码前的视频资源\r\n            ',
  `source` varchar(200) DEFAULT NULL,
  `copyright` varchar(200) DEFAULT NULL,
  `author` varchar(200) DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_17` (`creator`),
  KEY `fk_reference_7` (`thumbnail`),
  KEY `fk_reference_8` (`video`),
  KEY `fk_reference_9` (`original_video`),
  CONSTRAINT `fk_reference_17` FOREIGN KEY (`creator`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `fk_reference_9` FOREIGN KEY (`original_video`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of videoinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `video_commodity_point`
-- ----------------------------
DROP TABLE IF EXISTS `video_commodity_point`;
CREATE TABLE `video_commodity_point` (
  `id` varchar(36) NOT NULL,
  `video` varchar(36) NOT NULL,
  `timepoint` varchar(20) NOT NULL COMMENT '毫秒',
  `iframe` varchar(36) NOT NULL,
  `commodity` varchar(36) NOT NULL,
  `show_type` varchar(20) NOT NULL,
  `show_message` varchar(200) DEFAULT NULL,
  `show_position` varchar(20) DEFAULT NULL,
  `show_gif` varchar(36) DEFAULT NULL,
  `show_banner` varchar(36) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_11` (`show_gif`),
  KEY `fk_reference_12` (`iframe`),
  KEY `fk_reference_13` (`commodity`),
  KEY `fk_reference_20` (`creator`),
  KEY `fk_reference_22` (`show_banner`),
  KEY `fk_reference_3` (`video`),
  CONSTRAINT `fk_reference_22` FOREIGN KEY (`show_banner`) REFERENCES `resource` (`id`),
  CONSTRAINT `fk_reference_11` FOREIGN KEY (`show_gif`) REFERENCES `resource` (`id`),
  CONSTRAINT `fk_reference_12` FOREIGN KEY (`iframe`) REFERENCES `video_iframe` (`id`),
  CONSTRAINT `fk_reference_13` FOREIGN KEY (`commodity`) REFERENCES `commodity` (`id`),
  CONSTRAINT `fk_reference_20` FOREIGN KEY (`creator`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `fk_reference_3` FOREIGN KEY (`video`) REFERENCES `videoinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_commodity_point
-- ----------------------------

-- ----------------------------
-- Table structure for `video_iframe`
-- ----------------------------
DROP TABLE IF EXISTS `video_iframe`;
CREATE TABLE `video_iframe` (
  `id` varchar(36) NOT NULL,
  `video` varchar(36) NOT NULL,
  `timepoint` varchar(20) NOT NULL COMMENT '格式：hh:mm:ss.xx',
  `path` varchar(200) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_10` (`path`),
  KEY `fk_reference_2` (`video`),
  CONSTRAINT `fk_reference_2` FOREIGN KEY (`video`) REFERENCES `videoinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频按照2秒一帧抽取\r\n';

-- ----------------------------
-- Records of video_iframe
-- ----------------------------

-- ----------------------------
-- Table structure for `video_publish`
-- ----------------------------
DROP TABLE IF EXISTS `video_publish`;
CREATE TABLE `video_publish` (
  `id` varchar(36) NOT NULL,
  `video` varchar(36) NOT NULL,
  `category` varchar(36) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT '0',
  `title` varchar(300) NOT NULL,
  `short_title` varchar(100) DEFAULT NULL,
  `cover` varchar(36) DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_reference_16` (`creator`),
  KEY `fk_reference_4` (`video`),
  KEY `fk_reference_5` (`category`),
  KEY `fk_reference_6` (`cover`),
  CONSTRAINT `fk_reference_16` FOREIGN KEY (`creator`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `fk_reference_4` FOREIGN KEY (`video`) REFERENCES `videoinfo` (`id`),
  CONSTRAINT `fk_reference_5` FOREIGN KEY (`category`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_reference_6` FOREIGN KEY (`cover`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_publish
-- ----------------------------

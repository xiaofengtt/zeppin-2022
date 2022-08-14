# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.18-log)
# Database: xjems
# Generation Time: 2017-08-28 09:12:34 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table ethnic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ethnic`;

CREATE TABLE `ethnic` (
  `ID` smallint(6) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `WEIGHT` tinyint(4) NOT NULL DEFAULT '0' COMMENT '把常用的民族排序在前',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ethnic` WRITE;
/*!40000 ALTER TABLE `ethnic` DISABLE KEYS */;

INSERT INTO `ethnic` (`ID`, `NAME`, `WEIGHT`)
VALUES
	(1,'汉族',13),
	(2,'蒙古族',8),
	(3,'回族',10),
	(4,'藏族',0),
	(5,'维吾尔族',12),
	(6,'苗族',0),
	(7,'彝族',0),
	(8,'壮族',0),
	(9,'布依族',0),
	(10,'朝鲜族',0),
	(11,'满族',5),
	(12,'侗族',0),
	(13,'瑶族',0),
	(14,'白族',0),
	(15,'土家族',0),
	(16,'哈尼族',0),
	(17,'哈萨克族',11),
	(18,'傣族',0),
	(19,'黎族',0),
	(20,'傈僳族',0),
	(21,'佤族',0),
	(22,'畲族',0),
	(23,'高山族',0),
	(24,'拉祜族',0),
	(25,'水族',0),
	(26,'东乡族',0),
	(27,'纳西族',0),
	(28,'景颇族',0),
	(29,'柯尔克孜族',9),
	(30,'土族',0),
	(31,'达斡尔族',2),
	(32,'仫佬族',0),
	(33,'羌族',0),
	(34,'撒拉族',0),
	(35,'毛南族',0),
	(36,'仡佬族',0),
	(37,'锡伯族',6),
	(38,'阿昌族',0),
	(39,'普米族',0),
	(40,'塔吉克族',7),
	(41,'怒族',0),
	(42,'乌孜别克族',4),
	(43,'俄罗斯族',3),
	(44,'鄂温克族',0),
	(45,'德昂族',0),
	(46,'保安族',0),
	(47,'裕固族',0),
	(48,'京族',0),
	(49,'塔塔尔族',0),
	(50,'独龙族',0),
	(51,'鄂伦春族',0),
	(52,'赫哲族',0),
	(53,'门巴族',0),
	(54,'珞巴族',0),
	(55,'基诺族',0),
	(56,'布朗族',0);

/*!40000 ALTER TABLE `ethnic` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table exam_information
# ------------------------------------------------------------

DROP TABLE IF EXISTS `exam_information`;

CREATE TABLE `exam_information` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL COMMENT '考试名称',
  `EXAM_STARTTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '考试开始时间',
  `EXAM_ENDTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '考试结束时间',
  `INTEGRAL` int(6) NOT NULL COMMENT '考试积分',
  `INFORMATION` text NOT NULL COMMENT '考试信息',
  `INVIGILATION_CONTRACT` text NOT NULL COMMENT '监考责任书',
  `STATUS` tinyint(4) NOT NULL COMMENT '考试状态 -1待发布 0已结束 1已发布（可申报） 2进行中（不可申报）',
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `APPLY_ENDTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '申报截止时间',
  `CHECK_ENDTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '二次确认截止时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `exam_information` WRITE;
/*!40000 ALTER TABLE `exam_information` DISABLE KEYS */;

INSERT INTO `exam_information` (`ID`, `NAME`, `EXAM_STARTTIME`, `EXAM_ENDTIME`, `INTEGRAL`, `INFORMATION`, `INVIGILATION_CONTRACT`, `STATUS`, `CREATOR`, `CREATETIME`, `APPLY_ENDTIME`, `CHECK_ENDTIME`)
VALUES
	(1,'关于2017年5月20日-21日考试监考教师相关事宜通知','2017-08-24 14:52:51','2018-08-02 00:00:00',1,'<p>结合目前互联网信息技术的发展方向和移动互联技术的应用模式，本系统主要用来解决一下几个模块的信息交互管理问题。一是管理员考试信息管理</p>','<p>1</p>',0,1,'2017-07-27 05:41:00','2017-09-09 13:35:20','2017-09-08 13:35:20'),
	(2,'关于2017年6月20日-21日考试监考教师相关事宜通知','2017-08-23 10:45:51','2017-09-27 05:41:00',1,'1','1',0,1,'2017-07-27 05:41:00','2017-07-27 05:41:00','2017-07-27 05:41:00'),
	(3,'关于2017年7月20日-21日考试监考教师相关事宜通知','2017-08-23 10:45:56','2017-07-27 05:41:00',1,'1','1',0,1,'2017-07-27 05:41:00','2017-07-27 05:41:00','2017-07-27 05:41:00'),
	(4,'关于2017年8月20日-21日考试监考教师相关事宜通知','2017-08-23 10:46:03','2017-07-27 05:41:00',1,'1','1',0,1,'2017-07-27 05:41:00','2017-07-27 05:41:00','2017-07-27 05:41:00'),
	(5,'关于2017年9月20日-21日考试监考教师相关事宜通知','2017-08-23 10:46:08','2017-07-27 05:41:00',1,'1','1',0,1,'2017-07-27 05:41:00','2017-07-27 05:41:00','2017-07-27 05:41:00'),
	(6,'关于2017年2月20日-21日考试监考教师相关事宜通知','2017-08-28 15:45:52','2017-09-09 00:00:00',100,'<p>2017年2月20日-21号将在新疆师范大学本部（昆仑校区）举行大型考试。</p><p>为做好监考教师晚上报名工作，现将相关事宜通知如下：</p><p>&nbsp; &nbsp;1.考试时间：2月20号-21号</p><p><span style=\"font-size: 16px;\"> &nbsp; 1.网上申报时间：即日起5月17日16：00</span></p><p>欢迎各位教师踊跃参加监考报名！</p>','<p>东方闪电辅导费的辅导费&nbsp;</p>',1,1,'2017-09-25 09:19:00','2017-08-26 12:50:33','2017-08-26 12:50:33');

/*!40000 ALTER TABLE `exam_information` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table exam_room
# ------------------------------------------------------------

DROP TABLE IF EXISTS `exam_room`;

CREATE TABLE `exam_room` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROOM_INDEX` varchar(20) NOT NULL COMMENT '考场号（格式：078（监理））',
  `ROOM_ADDRESS` varchar(30) NOT NULL COMMENT '考场地点',
  `EXAMINATION_TIME` varchar(200) NOT NULL COMMENT '单场考试时间(yyyy年mm月dd日hh:MM-hh:MM)',
  `EXAMINATION_INFORMATION` varchar(200) NOT NULL COMMENT '单场考试学科或内容',
  `CREATOR` int(11) NOT NULL COMMENT '上传人',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传时间',
  `EXAM` int(11) NOT NULL COMMENT '所属考试',
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态1-正常 0-删除（停用）',
  `ARRIVALTIME` varchar(30) NOT NULL COMMENT '到岗时间',
  PRIMARY KEY (`ID`),
  KEY `FK_EXAM_ROOM_INFORMATION` (`EXAM`),
  KEY `INDEX_ROOM_INDEX` (`ROOM_INDEX`) USING BTREE,
  KEY `INDEX_ROOM_ADDRESS` (`ROOM_ADDRESS`) USING BTREE,
  KEY `INDEX_ROOM_STATUS` (`STATUS`) USING BTREE,
  CONSTRAINT `FK_EXAM_ROOM_INFORMATION` FOREIGN KEY (`EXAM`) REFERENCES `exam_information` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `exam_room` WRITE;
/*!40000 ALTER TABLE `exam_room` DISABLE KEYS */;

INSERT INTO `exam_room` (`ID`, `ROOM_INDEX`, `ROOM_ADDRESS`, `EXAMINATION_TIME`, `EXAMINATION_INFORMATION`, `CREATOR`, `CREATETIME`, `EXAM`, `STATUS`, `ARRIVALTIME`)
VALUES
	(1,'078 (监理)','文史楼701教师','2017年5月20号9:00-11:00','建设工程合同管理',1,'2017-08-24 18:11:15',1,1,'2017年9月31日'),
	(2,'79 (监理)','文史楼702教师','2017年5月20号9:00-11:01','建设工程合同管理',1,'2017-08-24 18:11:15',1,1,'2017年9月31日'),
	(3,'80 (监理)','文史楼703教师','2017年5月20号9:00-11:02','建设工程合同管理',1,'2017-08-24 18:11:15',1,1,'2017年9月32日'),
	(4,'81 (监理)','文史楼704教师','2017年5月20号9:00-11:03','建设工程合同管理',1,'2017-08-24 18:11:15',1,1,'2017年9月33日'),
	(5,'82 (监理)','文史楼705教师','2017年5月20号9:00-11:04','建设工程合同管理',1,'2017-08-24 18:11:15',1,1,'2017年9月34日'),
	(6,'83 (监理)','文史楼706教师','2017年5月20号9:00-11:05','建设工程合同管理',1,'2017-08-25 11:10:50',1,1,'2017年9月35日'),
	(7,'84 (监理)','文史楼707教师','2017年5月20号9:00-11:06','建设工程合同管理',1,'2017-08-25 11:10:51',1,1,'2017年9月36日'),
	(8,'85 (监理)','文史楼708教师','2017年5月20号9:00-11:07','建设工程合同管理',1,'2017-08-25 11:10:51',1,1,'2017年9月37日'),
	(9,'86 (监理)','文史楼709教师','2017年5月20号9:00-11:08','建设工程合同管理',1,'2017-08-25 11:10:52',1,1,'2017年9月38日'),
	(10,'87 (监理)','文史楼710教师','2017年5月20号9:00-11:09','建设工程合同管理',1,'2017-08-25 11:10:53',1,1,'2017年9月39日'),
	(11,'88 (监理)','文史楼711教师','2017年5月20号9:00-11:10','建设工程合同管理',1,'2017-08-25 11:10:53',1,1,'2017年9月40日'),
	(12,'078 (监理)','文史楼701教师','2017年5月20号9:00-11:00','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月31日'),
	(13,'79 (监理)','文史楼702教师','2017年5月20号9:00-11:01','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月31日'),
	(14,'80 (监理)','文史楼703教师','2017年5月20号9:00-11:02','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月32日'),
	(15,'81 (监理)','文史楼704教师','2017年5月20号9:00-11:03','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月33日'),
	(16,'82 (监理)','文史楼705教师','2017年5月20号9:00-11:04','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月34日'),
	(17,'83 (监理)','文史楼706教师','2017年5月20号9:00-11:05','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月35日'),
	(18,'84 (监理)','文史楼707教师','2017年5月20号9:00-11:06','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月36日'),
	(19,'85 (监理)','文史楼708教师','2017年5月20号9:00-11:07','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月37日'),
	(20,'86 (监理)','文史楼709教师','2017年5月20号9:00-11:08','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月38日'),
	(21,'87 (监理)','文史楼710教师','2017年5月20号9:00-11:09','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月39日'),
	(22,'88 (监理)','文史楼711教师','2017年5月20号9:00-11:10','建设工程合同管理',1,'2017-08-25 11:11:38',6,1,'2017年9月40日');

/*!40000 ALTER TABLE `exam_room` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table exam_teacher_room
# ------------------------------------------------------------

DROP TABLE IF EXISTS `exam_teacher_room`;

CREATE TABLE `exam_teacher_room` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EXAM` int(11) NOT NULL COMMENT '考试',
  `EXAM_ROOM` int(11) DEFAULT NULL COMMENT '考场',
  `TEACHER` int(11) NOT NULL COMMENT '监考教师',
  `IS_CHIEF` tinyint(4) DEFAULT '0' COMMENT '是否为主考 0否 1是',
  `IS_MIXED` tinyint(4) DEFAULT '0' COMMENT '是否混考 0否 1是',
  `IS_CONFIRM` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否确认 0否 1是',
  `CONFIRM_TIME` timestamp NULL DEFAULT NULL COMMENT '确认时间',
  `OPERATER` int(11) DEFAULT NULL COMMENT '（审核）操作人',
  `IS_AUTO` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否自主申报 0否 1是',
  `CREATOR` int(11) NOT NULL COMMENT '（申报）操作人',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1-正常 0-删除 2禁用',
  `CREDIT` int(4) NOT NULL COMMENT '得分',
  `APPLY_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
  `REASON` varchar(20) DEFAULT NULL COMMENT '评价',
  `IS_FIRST_APPLY` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否初次申报',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FK_EXAM_T_EXAM` (`EXAM`),
  KEY `FK_EXAM_T_TEACHER` (`TEACHER`),
  KEY `FK_EXAM_T_ROOM` (`EXAM_ROOM`),
  KEY `INDEX_TEACHERROOM_STATUS` (`STATUS`) USING BTREE,
  KEY `INDEX_TEACHERROOM_ISCONFIRM` (`IS_CONFIRM`) USING BTREE,
  KEY `INDEX_TEACHERROOM_ISFIRST` (`IS_FIRST_APPLY`) USING BTREE,
  KEY `INDEX_TEACHERROOM_ISCHIEF` (`IS_CHIEF`) USING BTREE,
  CONSTRAINT `FK_EXAM_T_EXAM` FOREIGN KEY (`EXAM`) REFERENCES `exam_information` (`ID`),
  CONSTRAINT `FK_EXAM_T_ROOM` FOREIGN KEY (`EXAM_ROOM`) REFERENCES `exam_room` (`ID`),
  CONSTRAINT `FK_EXAM_T_TEACHER` FOREIGN KEY (`TEACHER`) REFERENCES `invigilation_teacher` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `exam_teacher_room` WRITE;
/*!40000 ALTER TABLE `exam_teacher_room` DISABLE KEYS */;

INSERT INTO `exam_teacher_room` (`ID`, `EXAM`, `EXAM_ROOM`, `TEACHER`, `IS_CHIEF`, `IS_MIXED`, `IS_CONFIRM`, `CONFIRM_TIME`, `OPERATER`, `IS_AUTO`, `CREATOR`, `CREATETIME`, `STATUS`, `CREDIT`, `APPLY_TIME`, `REASON`, `IS_FIRST_APPLY`, `REMARK`)
VALUES
	(3,1,1,1,1,0,1,'2017-07-27 17:57:24',1,1,1,'2017-07-27 17:57:24',1,10,'2017-08-18 10:30:20','不',1,'1'),
	(4,1,1,3,1,1,1,'2017-07-27 17:57:24',1,1,1,'2017-07-27 17:57:24',1,1,'2017-07-27 17:57:24','1',1,'1'),
	(5,1,1,8,0,NULL,1,'2017-08-18 18:05:08',NULL,0,0,'2017-08-04 12:11:00',1,100,NULL,'',1,NULL),
	(6,1,NULL,9,0,NULL,1,'2017-07-27 17:57:24',NULL,1,1,'2017-08-04 18:04:31',0,0,NULL,NULL,1,NULL),
	(7,1,NULL,4,1,0,1,'2017-08-17 13:35:35',1,1,1,'2017-08-04 18:05:00',2,100,'2017-08-18 10:30:22','',1,NULL),
	(8,1,4,5,1,0,1,'2017-07-27 17:57:24',1,1,1,'2017-08-04 18:08:14',2,100,'2017-08-18 10:15:06','',1,NULL),
	(9,1,NULL,10,0,0,0,'2017-07-27 17:57:24',1,1,1,'2017-08-04 18:08:49',1,100,'2017-08-19 21:58:37','',1,NULL),
	(10,1,NULL,11,0,0,1,NULL,1,1,1,'2017-08-04 18:24:33',1,100,'2017-08-18 09:35:59','',1,NULL),
	(11,1,NULL,12,0,NULL,0,NULL,NULL,1,1,'2017-08-04 18:24:56',0,0,NULL,NULL,1,NULL),
	(12,1,NULL,13,0,NULL,0,NULL,NULL,1,1,'2017-08-04 18:33:43',0,0,NULL,NULL,1,NULL),
	(14,1,NULL,6,1,0,1,'2017-08-18 18:05:17',1,0,1,'2017-08-15 13:45:52',2,100,'2017-08-18 10:29:21','',1,NULL),
	(15,2,2,1,NULL,NULL,0,NULL,NULL,0,1,'2017-08-15 18:57:36',1,1,NULL,'',1,NULL),
	(16,3,NULL,1,NULL,NULL,0,NULL,NULL,0,1,'2017-08-15 19:03:14',2,0,NULL,NULL,1,NULL),
	(22,1,7,14,0,0,0,NULL,1,0,1,'2017-08-19 23:05:19',1,100,'2017-08-19 23:07:09','',1,NULL),
	(23,1,6,15,0,0,0,NULL,1,0,1,'2017-08-19 23:06:28',1,100,'2017-08-19 23:07:05','',1,NULL),
	(24,1,10,16,0,0,0,NULL,1,0,1,'2017-08-19 23:06:44',1,100,'2017-08-22 17:27:41','',1,NULL),
	(25,1,10,18,0,0,0,NULL,1,0,1,'2017-08-21 09:50:48',1,100,'2017-08-22 17:27:40','',1,NULL),
	(28,1,5,7,1,0,1,'2017-08-24 14:51:39',1,1,1,'2017-08-24 14:40:10',1,1,'2017-08-24 14:46:51','',1,NULL),
	(30,6,12,1,1,0,0,NULL,1,1,1,'2017-08-25 12:52:19',1,100,'2017-08-25 12:59:59','',1,NULL);

/*!40000 ALTER TABLE `exam_teacher_room` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table funcation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `funcation`;

CREATE TABLE `funcation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `PATH` varchar(200) DEFAULT NULL,
  `LEVEL` tinyint(4) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `PARENT` int(11) DEFAULT NULL,
  `SCODE` varchar(100) DEFAULT NULL,
  `ICON` varchar(50) DEFAULT NULL COMMENT '图表地址',
  PRIMARY KEY (`ID`),
  KEY `FK_REFERENCE_43` (`PARENT`),
  CONSTRAINT `FK_REFERENCE_43` FOREIGN KEY (`PARENT`) REFERENCES `funcation` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `funcation` WRITE;
/*!40000 ALTER TABLE `funcation` DISABLE KEYS */;

INSERT INTO `funcation` (`ID`, `NAME`, `PATH`, `LEVEL`, `STATUS`, `PARENT`, `SCODE`, `ICON`)
VALUES
	(1,'考试管理',NULL,1,1,NULL,'0001','../img/examManage'),
	(2,'考试管理中心','../admin/main.jsp',2,1,1,'00010001',NULL),
	(3,'监考教师管理',NULL,1,1,NULL,'0002','../img/teacherManage'),
	(4,'监考教师信息管理','../admin/teachersMessage.jsp',2,1,3,'00020001',NULL),
	(5,'监考教师资格审核','../admin/teachersAuditing.jsp',2,1,3,'00020002',NULL),
	(6,'历史考试管理',NULL,1,1,NULL,'0003','../img/examHistoryManage'),
	(7,'历史考试信息查询','../admin/historyInfo.jsp',2,1,6,'00030001',NULL);

/*!40000 ALTER TABLE `funcation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table invigilation_teacher
# ------------------------------------------------------------

DROP TABLE IF EXISTS `invigilation_teacher`;

CREATE TABLE `invigilation_teacher` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL COMMENT '姓名',
  `PINYIN` varchar(100) NOT NULL COMMENT '姓名拼音',
  `IDCARD` varchar(20) NOT NULL COMMENT '身份证号',
  `MOBILE` varchar(12) NOT NULL COMMENT '手机号',
  `SEX` tinyint(4) NOT NULL COMMENT '性别1男 2女',
  `ETHNIC` smallint(6) NOT NULL COMMENT '民族',
  `PHOTO` int(11) DEFAULT '0' COMMENT '头像',
  `MAJOR` varchar(20) NOT NULL COMMENT '所学专业',
  `TYPE` smallint(6) NOT NULL DEFAULT '0' COMMENT '身份类别',
  `ORGANIZATION` varchar(30) NOT NULL COMMENT '所在学院或部门',
  `INSHCOOL_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '入校时间',
  `IS_CHIEF_EXAMINER` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有主考经验',
  `IS_MIXED_EXAMINER` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有混考经验',
  `INTEGRAL` int(6) NOT NULL DEFAULT '0' COMMENT '监考累计积分（可为负数）',
  `SPECIALTY` varchar(50) DEFAULT '' COMMENT '教师特长',
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1.正常 0删除 ',
  `REASON` varchar(100) DEFAULT '' COMMENT '停用原因',
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `CHECK_STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核状态 1未审核 0未通过 2已通过',
  `CHECK_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '审核时间',
  `CHECKER` int(11) DEFAULT '0' COMMENT '审核人',
  `CHECK_REASON` varchar(100) DEFAULT '' COMMENT '审核不通过原因',
  `INVIGILATE_CAMPUS` varchar(10) DEFAULT '0' COMMENT '监考校区',
  `INVIGILATE_TYPE` varchar(10) DEFAULT '0' COMMENT '监考类型',
  `INVIGILATE_COUNT` int(4) DEFAULT '0' COMMENT '监考次数默认0',
  `JOB_DUTY` varchar(20) DEFAULT '' COMMENT '职务',
  `STUDY_MAJOR` varchar(20) DEFAULT '' COMMENT '研究生所学专业',
  `STUDY_GRADE` varchar(20) DEFAULT '' COMMENT '研究生所在年级',
  `REMARK` varchar(100) DEFAULT '' COMMENT '备注',
  `BANK_CARD` varchar(20) DEFAULT '' COMMENT '交通银行银行卡卡号',
  `OPENID` varchar(64) DEFAULT '' COMMENT '微信识别唯一ID',
  `SID` varchar(20) DEFAULT '' COMMENT '学工号',
  `PASSWORD` varchar(20) DEFAULT '' COMMENT '教师登录密码，默认为身份证后6位',
  PRIMARY KEY (`ID`),
  KEY `FK_TEACHER_ETHNIC` (`ETHNIC`),
  KEY `FK_TEACHER_PHOTO` (`PHOTO`),
  KEY `FK_TEACHER_TYPE` (`TYPE`),
  KEY `INDEX_NAME_PINYIN_IDCARD_MOBILE` (`NAME`,`PINYIN`,`IDCARD`,`MOBILE`) USING BTREE,
  KEY `INDEX_NAME` (`NAME`) USING BTREE,
  KEY `INDEX_IDCARD` (`IDCARD`) USING BTREE,
  KEY `INDEX_PINYIN` (`PINYIN`) USING BTREE,
  CONSTRAINT `FK_TEACHER_ETHNIC` FOREIGN KEY (`ETHNIC`) REFERENCES `ethnic` (`ID`),
  CONSTRAINT `FK_TEACHER_PHOTO` FOREIGN KEY (`PHOTO`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `invigilation_teacher` WRITE;
/*!40000 ALTER TABLE `invigilation_teacher` DISABLE KEYS */;

INSERT INTO `invigilation_teacher` (`ID`, `NAME`, `PINYIN`, `IDCARD`, `MOBILE`, `SEX`, `ETHNIC`, `PHOTO`, `MAJOR`, `TYPE`, `ORGANIZATION`, `INSHCOOL_TIME`, `IS_CHIEF_EXAMINER`, `IS_MIXED_EXAMINER`, `INTEGRAL`, `SPECIALTY`, `STATUS`, `REASON`, `CREATOR`, `CREATETIME`, `CHECK_STATUS`, `CHECK_TIME`, `CHECKER`, `CHECK_REASON`, `INVIGILATE_CAMPUS`, `INVIGILATE_TYPE`, `INVIGILATE_COUNT`, `JOB_DUTY`, `STUDY_MAJOR`, `STUDY_GRADE`, `REMARK`, `BANK_CARD`, `OPENID`, `SID`, `PASSWORD`)
VALUES
	(1,'某某','mmm','120103199301010129','13090909090',1,1,1,'信息电子传播',1,'教务处','2017-08-25 18:11:46',0,0,100,'',1,'1',1,'2017-08-24 15:46:38',2,'2017-08-17 17:32:31',1,'','','',NULL,'1','','','','','o4TYt0pcNYiSsOl-i0d1Rx_Tkglo','无','111111'),
	(2,'李四','lisi','1','1',2,1,1,'信息电子传播',2,'教务处','2017-08-21 09:54:04',1,1,673,'1',0,'1',100,'2017-08-27 17:56:20',2,'2017-08-17 17:32:35',1,'','1','2',1,'1','1','1','','','ot3IawLet3ZqlcaoKdgyNpsBC6A0',NULL,'111111'),
	(3,'王五','wangwu','1','1',2,2,1,'信息电子传播',1,'教务处','2017-08-24 19:34:17',1,1,13,'1',1,'1',100,'2016-07-27 17:56:20',0,'2017-08-17 17:32:53',1,'审核不通过原因','1','3',1,'1','1','1','','','',NULL,'111111'),
	(4,'赵六','zhaoliu','1','1',1,1,1,'信息电子传播',1,'教务处','2017-08-22 11:28:26',1,1,1,'1',1,'1',100,'2017-06-27 17:56:20',2,'2017-08-17 17:37:28',1,'','1','1',1,'1','1','1','','','',NULL,'111111'),
	(5,'喀什','kashi','1','1',1,1,1,'信息电子传播',3,'教务处','2017-08-21 09:54:07',0,0,100,'',1,'',0,'2017-06-27 17:56:20',0,'2017-08-17 17:37:35',1,'1','1','0',1,'','','','','','','','111111'),
	(6,'王一','wangyi','1','1',1,3,1,'信息电子传播',3,'教务处','2017-08-21 09:54:08',1,1,101,'',1,'',0,'2017-06-27 17:56:20',0,'2017-08-17 17:38:26',1,'审核不通过原因','1','0',1,'','','','','','','','111111'),
	(7,'王二','wanger','1','1',2,1,1,'信息电子传播',2,'教务处','2017-08-21 09:54:10',0,0,1,'',1,'',0,'2017-06-27 17:56:20',2,'2017-08-17 17:38:30',1,'','1','0',NULL,'','','','','','','','111111'),
	(8,'王三','wangsan','1','1',2,4,1,'信息电子传播',2,'教务处','2017-08-21 09:54:09',0,0,0,'',1,'',0,'2017-06-27 17:56:20',0,'2017-08-17 18:20:39',1,'我觉得不行','1','0',NULL,'','','','','','','','111111'),
	(9,'王四','wangsi','1','1',2,1,1,'信息电子传播',2,'教务处','2017-08-21 09:54:11',0,0,100,'',1,'',0,'2017-06-27 17:56:20',0,'2017-08-17 18:20:03',1,'我觉得不行','1','0',NULL,'','','','','','','','111111'),
	(10,'王六','wangliu','1','1',2,5,1,'信息电子传播',3,'教务处','2017-08-21 09:54:12',0,0,0,'',1,'',0,'2017-06-27 17:56:20',0,'2017-08-17 18:21:26',1,'我觉得不行','1','0',NULL,'','','','','','','','111111'),
	(11,'王七','wangqi','1','1',1,6,1,'信息电子传播',2,'教务处','2017-08-21 09:54:13',0,0,100,'',1,'',0,'2017-06-27 17:56:20',1,'2017-06-27 17:56:20',0,'','1','0',NULL,'','','','','','','','111111'),
	(12,'王九','wangjiu','1','1',1,7,1,'信息电子传播',3,'教务处','2017-08-21 09:54:14',0,0,0,'',1,'',0,'2017-06-27 17:56:20',1,'2017-06-27 17:56:20',0,'','1','0',NULL,'','','','','','','','111111'),
	(13,'王十','wangshi','1','1',2,1,1,'信息电子传播',2,'教务处','2017-08-21 09:54:15',0,0,0,'',1,'',0,'2017-06-27 17:56:20',1,'2017-06-27 17:56:20',0,'','1','0',NULL,'','','','','','','','111111'),
	(14,'哈哈哈哈哈','hhhhh','11','11111111111',2,17,1,'1',2,'11','2017-08-29 00:00:00',1,1,12,'',1,NULL,0,'2017-08-18 16:22:56',1,'2017-08-18 16:23:38',NULL,NULL,'3','3',NULL,'11','','11','','11',NULL,'11','111111'),
	(15,'111','111','130222222291811228','13716640291',1,5,1,'哈哈哈',1,'斤斤计较','2017-08-21 09:54:17',1,1,12,'1',1,NULL,1,'2017-08-18 16:29:39',2,'2017-08-18 16:29:39',1,NULL,'2','2',NULL,'111','','','','111',NULL,'111','111111'),
	(16,'111','111','130222222291811228','13712640291',1,1,1,'哈哈哈',1,'斤斤计较','2017-08-21 09:54:18',1,1,12,'1',1,NULL,1,'2017-08-18 17:20:29',2,'2017-08-18 17:20:29',1,NULL,'2','2',NULL,'111','','','','111',NULL,'111','111111'),
	(17,'老七','lq','130000199312020202','13000000000',2,3,1,'JAVA',2,'北大电子信息','2017-08-31 00:00:00',1,1,95,'1',1,NULL,1,'2017-08-19 23:41:19',2,'2017-08-19 23:41:19',1,NULL,'1,3,2','1,2,3',NULL,'','JAVA','1','','10000000000000',NULL,'1000','111111'),
	(18,'哈哈哈啊','hhha','111','13713164531',1,29,1,'111',1,'611','2017-09-25 00:00:00',0,0,0,'',1,NULL,0,'2017-08-21 12:03:36',1,'2017-08-21 12:03:36',NULL,NULL,'3,2','1,3,2',NULL,'111',NULL,NULL,'','111',NULL,'111','111111'),
	(19,'李白','lb','130909199408973939','13716640748',2,5,1,'java',1,'web','2017-08-22 00:00:00',0,0,0,'',1,NULL,0,'2017-08-24 10:49:55',1,'2017-08-24 10:49:54',NULL,NULL,'1','2',NULL,'web',NULL,NULL,'','0101000333333','gh_c9075e546bed','010','333333'),
	(20,'刘小','lx','130727199112120909','18390909898',2,1,1,'软件',2,'部门','2017-08-15 00:00:00',1,2,100,'特长',1,NULL,1,'2017-08-24 16:08:00',2,'2017-08-24 16:08:00',1,NULL,'2,1,3','1,2,3',NULL,'','2','1','','0303000303030303',NULL,'0101','030303'),
	(21,'flksdjflkdsjf ','flksdjflkdsjf','130727199208088282','13023202020',1,17,1,'11',2,'1','2017-08-26 00:00:00',1,1,10,'dfsdfsdf',1,NULL,1,'2017-08-25 16:36:21',2,'2017-08-25 16:36:21',1,NULL,'2','2',NULL,'','fsdf','123','','20020',NULL,'02002',NULL);

/*!40000 ALTER TABLE `invigilation_teacher` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table invigilation_template
# ------------------------------------------------------------

DROP TABLE IF EXISTS `invigilation_template`;

CREATE TABLE `invigilation_template` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(20) DEFAULT '' COMMENT '标题',
  `CONTENT` text NOT NULL COMMENT '内容',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `CREATOR` int(11) NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `invigilation_template` WRITE;
/*!40000 ALTER TABLE `invigilation_template` DISABLE KEYS */;

INSERT INTO `invigilation_template` (`ID`, `TITLE`, `CONTENT`, `CREATETIME`, `CREATOR`)
VALUES
	(7,'模板21','哈哈哈哈哈哈哈','2017-07-28 16:54:46',199),
	(8,'模板31','哈哈哈哈哈哈哈','2017-07-28 17:13:48',199),
	(9,'模板5','哈哈哈哈哈哈哈','2017-07-28 17:15:13',199),
	(10,'模板4','<p>qwe<br/></p>','2017-08-14 17:34:32',1),
	(11,'模板5','<p>的点点滴滴多</p>','2017-08-18 18:38:12',1),
	(12,'模板6','<p>dddd</p>','2017-08-18 18:40:20',1),
	(13,'模板7','<p>dddd</p>','2017-08-18 18:44:21',1),
	(14,'模板8','<p>dddd</p>','2017-08-18 18:44:34',1),
	(15,'模板9','<p>dddd</p>','2017-08-18 18:51:17',1),
	(16,'模板10','jlksdjfjfljfl dsf\n','2017-08-18 18:59:55',1),
	(17,'模板11','fdflkdfjdjfd   ','2017-08-18 19:00:04',1),
	(18,'模板12','<p>你<br/></p>','2017-08-18 19:00:54',1),
	(19,'模板13','<p>你<br/></p>','2017-08-18 19:02:36',1),
	(20,'模板14','<p>东方闪电辅导费的辅导费&nbsp;</p>','2017-08-25 09:10:00',1),
	(21,'模板15','<p>东方闪电辅导费的辅导费&nbsp;</p>','2017-08-25 09:10:05',1),
	(22,'模板16','<p>东方闪电辅导费的辅导费&nbsp;</p>','2017-08-25 09:19:00',1);

/*!40000 ALTER TABLE `invigilation_template` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table mobile_code
# ------------------------------------------------------------

DROP TABLE IF EXISTS `mobile_code`;

CREATE TABLE `mobile_code` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER` int(11) DEFAULT NULL,
  `MOBILE` varchar(20) NOT NULL,
  `UUID` varchar(50) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1正常 0停用',
  `CODE` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `mobile_code` WRITE;
/*!40000 ALTER TABLE `mobile_code` DISABLE KEYS */;

INSERT INTO `mobile_code` (`ID`, `USER`, `MOBILE`, `UUID`, `CREATETIME`, `STATUS`, `CODE`)
VALUES
	(1,1,'13716640748','11889d0b6bc744afaded4cac1df8ba2d','2017-08-21 11:31:45',0,'745197'),
	(2,-1,'13716640748','bb2074c0e8674d14b3920fd896ff4220','2017-08-24 10:48:51',1,'649780'),
	(3,-1,'15090908989','410a346d4009421ca252a3fadfed987b','2017-08-24 15:27:20',1,'800872');

/*!40000 ALTER TABLE `mobile_code` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table resource
# ------------------------------------------------------------

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` tinyint(4) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `SOURCE_PATH` varchar(200) NOT NULL,
  `PATH` varchar(200) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `TITLE` varchar(200) NOT NULL,
  `SUFFIX` varchar(10) NOT NULL,
  `FILESIZE` int(11) NOT NULL DEFAULT '0',
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1正常2删除',
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;

INSERT INTO `resource` (`ID`, `TYPE`, `NAME`, `SOURCE_PATH`, `PATH`, `URL`, `TITLE`, `SUFFIX`, `FILESIZE`, `STATUS`, `CREATOR`, `CREATETIME`)
VALUES
	(1,1,'1','img/userBig.png','1','1','1','1',1,1,1,'2017-07-27 17:55:07'),
	(2,2,'teacherlist (66)','upload/9c4788f9/18a6/464c/bd12/ac9e77a4588e/teacherlist (66).xls','upload/9c4788f9/18a6/464c/bd12/ac9e77a4588e/teacherlist (66).xls',NULL,'teacherlist (66)','.xls',5632,1,1,'2017-08-16 13:54:11'),
	(3,2,'teacherlist (66)','upload/85973d1a/ba95/43a7/95d0/3663dabfe1cb/teacherlist (66).xls','upload/85973d1a/ba95/43a7/95d0/3663dabfe1cb/teacherlist (66).xls',NULL,'teacherlist (66)','.xls',5632,1,1,'2017-08-16 13:54:44'),
	(4,2,'teacherlist (66)','upload/e687e5d5/5370/4028/a664/db05a04c6a88/teacherlist (66).xls','upload/e687e5d5/5370/4028/a664/db05a04c6a88/teacherlist (66).xls',NULL,'teacherlist (66)','.xls',5632,1,1,'2017-08-16 13:54:57'),
	(5,2,'roomModel (2)','upload/18aa0843/73cc/4fa8/8210/ee4f15befd2e/roomModel (2).xlsx','upload/18aa0843/73cc/4fa8/8210/ee4f15befd2e/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10220,1,1,'2017-08-24 16:38:52'),
	(6,2,'roomModel (2)','upload/297deb95/08cc/4ce5/b877/867b808253fc/roomModel (2).xlsx','upload/297deb95/08cc/4ce5/b877/867b808253fc/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10220,1,1,'2017-08-24 16:39:30'),
	(7,2,'roomModel (2)','upload/959d63a5/9b83/4c19/8d25/192361c5d664/roomModel (2).xlsx','upload/959d63a5/9b83/4c19/8d25/192361c5d664/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10220,1,1,'2017-08-24 16:40:48'),
	(8,2,'roomModel (2)','upload/899e15a7/f62e/4d94/80e1/01f9fc83a0f8/roomModel (2).xlsx','upload/899e15a7/f62e/4d94/80e1/01f9fc83a0f8/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10220,1,1,'2017-08-24 16:42:26'),
	(9,2,'roomModel (2)','upload/74212a2a/fc01/40e4/ab63/19e787c5472b/roomModel (2).xlsx','upload/74212a2a/fc01/40e4/ab63/19e787c5472b/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10220,1,1,'2017-08-24 16:43:04'),
	(10,2,'roomModel (2)','upload/72cbac0e/daba/4f56/9e77/85a587940a80/roomModel (2).xlsx','upload/72cbac0e/daba/4f56/9e77/85a587940a80/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10220,1,1,'2017-08-24 17:47:36'),
	(11,2,'roomModel (2)','upload/168f221f/1b32/423e/a4e8/f435419d7927/roomModel (2).xlsx','upload/168f221f/1b32/423e/a4e8/f435419d7927/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10220,1,1,'2017-08-24 17:49:12'),
	(12,2,'roomModel (2)','upload/ead0eb52/4485/457e/b3a7/6f287c666332/roomModel (2).xlsx','upload/ead0eb52/4485/457e/b3a7/6f287c666332/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10220,1,1,'2017-08-24 17:58:19'),
	(13,2,'roomModel (2)','upload/3974b7b4/60d0/4509/afaa/310a4d764337/roomModel (2).xlsx','upload/3974b7b4/60d0/4509/afaa/310a4d764337/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10299,1,1,'2017-08-24 18:02:04'),
	(14,2,'roomModel (2)','upload/4732eb10/674a/424d/b333/04765e7a3efd/roomModel (2).xlsx','upload/4732eb10/674a/424d/b333/04765e7a3efd/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10299,1,1,'2017-08-24 18:03:43'),
	(15,2,'roomModel (2)','upload/c53a333c/c84a/43a0/a3a2/ac41d6613539/roomModel (2).xlsx','upload/c53a333c/c84a/43a0/a3a2/ac41d6613539/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10299,1,1,'2017-08-24 18:04:51'),
	(16,2,'roomModel (2)','upload/27074b1c/7144/44aa/89ac/c50c4c08a5ef/roomModel (2).xlsx','upload/27074b1c/7144/44aa/89ac/c50c4c08a5ef/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10299,1,1,'2017-08-24 18:05:54'),
	(17,2,'roomModel (2)','upload/0f60fdea/3a52/4b62/ac3c/925bbbddb966/roomModel (2).xlsx','upload/0f60fdea/3a52/4b62/ac3c/925bbbddb966/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10299,1,1,'2017-08-24 18:09:08'),
	(18,2,'roomModel (2)','upload/6f8ad93b/d278/471a/b74c/f42b1d1230b4/roomModel (2).xlsx','upload/6f8ad93b/d278/471a/b74c/f42b1d1230b4/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10299,1,1,'2017-08-24 18:10:51'),
	(19,2,'roomModel (2)','upload/f43c0633/2777/4f68/a4ae/14cfc9e3656a/roomModel (2).xlsx','upload/f43c0633/2777/4f68/a4ae/14cfc9e3656a/roomModel (2).xlsx',NULL,'roomModel (2)','.xlsx',10299,1,1,'2017-08-25 11:11:25');

/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_KEY_2` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`ID`, `NAME`, `STATUS`)
VALUES
	(1,'超级管理员',1),
	(2,'管理员',1);

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_funcation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_funcation`;

CREATE TABLE `role_funcation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE` int(11) NOT NULL,
  `FUNCATION` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_KEY_2` (`ROLE`,`FUNCATION`),
  KEY `FK_REFERENCE_44` (`FUNCATION`),
  CONSTRAINT `role_funcation_ibfk_1` FOREIGN KEY (`ROLE`) REFERENCES `role` (`ID`),
  CONSTRAINT `role_funcation_ibfk_2` FOREIGN KEY (`FUNCATION`) REFERENCES `funcation` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `role_funcation` WRITE;
/*!40000 ALTER TABLE `role_funcation` DISABLE KEYS */;

INSERT INTO `role_funcation` (`ID`, `ROLE`, `FUNCATION`)
VALUES
	(5,1,2),
	(6,1,4),
	(7,1,5),
	(8,1,7),
	(1,2,2),
	(2,2,4),
	(3,2,5),
	(4,2,7);

/*!40000 ALTER TABLE `role_funcation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(50) NOT NULL,
  `PHONE` varchar(20) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `ROLE` int(11) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `ORGANIZATION` int(11) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `CREATOR` int(11) DEFAULT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PHOTO` int(11) NOT NULL DEFAULT '1' COMMENT '管理员头像',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_KEY_2` (`EMAIL`),
  UNIQUE KEY `AK_KEY_3` (`PHONE`),
  KEY `FK_REFERENCE_34` (`CREATOR`),
  KEY `FK_REFERENCE_39` (`ROLE`),
  KEY `FK_REFERENCE_64` (`ORGANIZATION`),
  CONSTRAINT `FK_CREATOR` FOREIGN KEY (`CREATOR`) REFERENCES `sys_user` (`ID`),
  CONSTRAINT `FK_ROLE_SYS_USER` FOREIGN KEY (`ROLE`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`ID`, `EMAIL`, `PHONE`, `NAME`, `ROLE`, `PASSWORD`, `ORGANIZATION`, `STATUS`, `CREATOR`, `CREATETIME`, `PHOTO`)
VALUES
	(1,'admin@zeppin.cn','17777777777','超级管理员',1,'ZePpIn@)!%',1,1,1,'2015-01-04 15:21:52',1),
	(194,'qinlong@zeppin.cn','18701376560','秦龙',2,'61399256',1,1,1,'2015-02-09 15:10:42',1),
	(195,'zhangyahui@zeppin.cn','18660351920','张亚辉',2,'351920',1,0,1,'2015-02-09 15:13:13',1),
	(196,'740027729@qq.com','15049105964','刘莉莉',2,'105964',1,1,1,'2015-02-09 15:14:00',1),
	(197,'zhangxuning@zeppin.cn','18504410371','张旭宁',2,'s13110857',1,1,1,'2015-06-17 09:49:46',1),
	(198,'rongjingfeng@zeppin.cn','18611920344','荣景峰',2,'rongjingfeng!@#',1,1,1,'2015-06-19 12:07:46',1),
	(200,'liuyuzhen@zeppin.cn','15836329585','刘玉珍',2,'329585',1,1,1,'2015-07-03 22:24:04',1);

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

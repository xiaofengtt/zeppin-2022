
ALTER TABLE grade  ADD COLUMN ISSCHOOL tinyint(1) NULL DEFAULT '0';

ALTER TABLE project  ADD COLUMN ISTEST tinyint(1) NULL DEFAULT '0';

ALTER TABLE teacher_training_records  ADD COLUMN REPLACE_STATUS tinyint(1) NULL DEFAULT '0';

ALTER TABLE teacher_training_records  ADD COLUMN REPLACE_TEACHER int(11) NULL DEFAULT '0';

# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.42-log)
# Database: xjtts
# Generation Time: 2015-12-15 08:12:11 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table fun_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `fun_category`;

CREATE TABLE `fun_category` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) DEFAULT NULL,
  `PID` int(11) DEFAULT NULL,
  `PATH` varchar(200) DEFAULT NULL,
  `LEVEL` int(4) DEFAULT NULL,
  `CODE` int(2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_REFERENCE_91` (`PID`),
  CONSTRAINT `FK_REFERENCE_91` FOREIGN KEY (`PID`) REFERENCES `fun_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `fun_category` WRITE;
/*!40000 ALTER TABLE `fun_category` DISABLE KEYS */;

INSERT INTO `fun_category` (`ID`, `NAME`, `PID`, `PATH`, `LEVEL`, `CODE`)
VALUES
	(1,'基础业务数据管理',NULL,NULL,1,1),
	(2,'项目类型设置',1,'../admin/projectType_initPage.action',2,1),
	(3,'培训科目设置',1,'../admin/trainingSubject_initPage.action',2,1),
	(4,'承训单位设置',1,'../admin/trainingCollege_initPage.action',2,1),
	(5,'组织架构设置',1,'../admin/organization_initPage.action',2,1),
	(6,'账号管理',NULL,NULL,1,1),
	(7,'项目管理员管理',6,'../admin/projectAdmin_initPage.action',2,1),
	(8,'承训单位管理员管理',6,'../admin/trainingAdmin_initPage.action',2,1),
	(9,'项目管理',NULL,NULL,1,1),
	(10,'项目信息管理',9,'../admin/projectBase_initPage.action',2,1),
	(11,'项目申报结果监审',9,'../admin/projectApply_initPage.action',2,1),
	(12,'学员管理',NULL,NULL,1,1),
	(13,'学员基本信息管理',12,'../admin/teacherManage_initPage.action',2,1),
	(14,'学员名额分配管理',12,'../admin/assignTeacherTask_initPage.action',2,1),
	(15,'学员报送管理',12,'../admin/ttRecord_initReortAsstPage.action',2,1),
	(16,'学员审核管理',12,'../admin/ttRecord_initAduPage.action',2,1),
	(17,'学员报到及成绩管理',12,'../admin/trainingStudentOpt_initPage.action',2,1),
	(18,'项目申报管理',NULL,NULL,1,1),
	(19,'可申报项目',18,'../admin/trainingUnitProjectApply_initPage.action',NULL,NULL),
	(20,'项目申报记录',18,' ../admin/trainingUnitProjectApply_historyInit.action',NULL,NULL),
	(21,'问卷管理',NULL,NULL,1,1),
	(22,'学评教问卷',21,'../paper/paper_list.action?type=3',2,1),
	(23,'教评学问卷',21,'../paper/paper_list.action?type=2',2,1),
	(24,'专家评分问卷',21,'../paper/paper_list.action?type=1',2,1),
	(25,'查看学员成绩',12,'../admin/ttRecord_initMarkPage.action',2,1),
	(26,'项目中标统计',9,'../admin/projectBase_initProjectResult.action',2,1),
	(27,'名额分配统计',12,'../admin/assignTeacherTask_initAssignResult.action',2,1),
	(28,'报送情况统计',12,'../admin/ttRecord_initttRecordResult.action',2,1),
	(29,'评审专家管理',6,'../admin/projectExpertManage_initPage.action',2,1),
	(30,'项目评审管理',NULL,NULL,1,1),
	(31,'项目评审',30,'../admin/projectReview_initPage.action',2,1),
	(32,'教师培训全信息统计',12,'../admin/ttRecord_initTeacherInfo.action',2,1),
	(33,'自主报名教师信息管理',NULL,'',1,1),
	(34,'教师基本信息审核管理',33,'../admin/teacherInfo_initPage.action',2,1),
	(35,'自主报名学员审核管理',33,'../admin/ttRecord_initReviewPage.action',2,1),
	(36,'自主报名学员信息确认',33,'../admin/trainingStudentOpt_initConfirmPage.action',NULL,NULL),
	(37,'学科设置',1,'../admin/subject_initPage.action',2,1),
	(38,'学段设置',1,'../admin/grade_initPage.action',2,1),
	(39,'职称设置',1,'../admin/jobTitle_initPage.action',2,1),
	(40,'职务设置',1,'../admin/jobDuty_initPage.action',2,1),
	(41,'查看管理员权限',6,'../admin/projectAdmin_authority.action',2,1),
	(42,'查看承训单位管理员权限',6,'../admin/trainingAdmin_authority.action',2,1);

/*!40000 ALTER TABLE `fun_category` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table orga_cate_map
# ------------------------------------------------------------

DROP TABLE IF EXISTS `orga_cate_map`;

CREATE TABLE `orga_cate_map` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CATEGORY` int(11) DEFAULT NULL,
  `ORGANIZATION` tinyint(4) DEFAULT NULL,
  `ROLEID` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FK_CATE` (`CATEGORY`),
  KEY `FK_FK_ORG` (`ORGANIZATION`),
  CONSTRAINT `FK_FK_CATE` FOREIGN KEY (`CATEGORY`) REFERENCES `fun_category` (`ID`),
  CONSTRAINT `FK_FK_ORG` FOREIGN KEY (`ORGANIZATION`) REFERENCES `organization_level` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `orga_cate_map` WRITE;
/*!40000 ALTER TABLE `orga_cate_map` DISABLE KEYS */;

INSERT INTO `orga_cate_map` (`ID`, `CATEGORY`, `ORGANIZATION`, `ROLEID`)
VALUES
	(1,2,NULL,5),
	(2,3,NULL,5),
	(3,4,NULL,5),
	(4,5,NULL,5),
	(5,7,NULL,5),
	(6,8,NULL,5),
	(7,7,1,1),
	(8,8,1,1),
	(9,10,1,1),
	(10,11,1,1),
	(11,13,1,1),
	(12,14,1,1),
	(13,7,2,1),
	(14,13,2,1),
	(15,14,2,1),
	(16,7,3,1),
	(17,13,3,1),
	(18,14,3,1),
	(19,7,4,1),
	(20,13,4,1),
	(22,15,1,1),
	(23,16,1,1),
	(24,15,2,1),
	(25,16,2,1),
	(27,16,3,1),
	(28,15,4,1),
	(29,16,4,1),
	(30,15,3,1),
	(31,8,NULL,2),
	(32,17,NULL,2),
	(33,19,NULL,2),
	(34,20,NULL,2),
	(35,22,1,1),
	(36,23,1,1),
	(37,24,1,1),
	(38,25,1,1),
	(39,25,2,1),
	(40,25,3,1),
	(41,25,4,1),
	(42,26,1,1),
	(43,27,1,1),
	(44,27,2,1),
	(45,27,3,1),
	(46,27,4,1),
	(47,28,1,1),
	(48,28,2,1),
	(49,28,3,1),
	(50,29,NULL,4),
	(51,29,1,1),
	(52,29,NULL,5),
	(53,31,NULL,4),
	(54,32,1,1),
	(55,32,2,1),
	(56,32,3,1),
	(57,33,1,1),
	(58,33,2,1),
	(59,33,3,1),
	(60,33,4,1),
	(61,34,4,1),
	(62,34,3,1),
	(63,34,2,1),
	(64,34,1,1),
	(65,35,1,1),
	(66,35,2,1),
	(67,35,3,1),
	(68,35,4,1),
	(69,36,NULL,2),
	(70,37,NULL,5),
	(71,38,NULL,5),
	(72,39,NULL,5),
	(73,40,NULL,5),
	(74,41,1,1),
	(75,41,2,1),
	(76,41,3,1),
	(77,41,4,1),
	(78,42,NULL,2);

/*!40000 ALTER TABLE `orga_cate_map` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.42-log)
# Database: xjtts
# Generation Time: 2015-12-10 10:01:24 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table training_admin_authority
# ------------------------------------------------------------

DROP TABLE IF EXISTS `training_admin_authority`;

CREATE TABLE `training_admin_authority` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `TRAINING_ADMIN` int(11) NOT NULL,
  `PROJECT` int(11) NOT NULL,
  `TRAINING_SUBJECT` smallint(6) DEFAULT NULL,
  `CLASSES` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TAA_TA` (`TRAINING_ADMIN`),
  KEY `FK_TAA_P` (`PROJECT`),
  KEY `FK_TAA_TS` (`TRAINING_SUBJECT`),
  CONSTRAINT `FK_TAA_P` FOREIGN KEY (`PROJECT`) REFERENCES `project` (`ID`),
  CONSTRAINT `FK_TAA_TA` FOREIGN KEY (`TRAINING_ADMIN`) REFERENCES `training_admin` (`ID`),
  CONSTRAINT `FK_TAA_TS` FOREIGN KEY (`TRAINING_SUBJECT`) REFERENCES `training_subject` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2015-12-16 14:18:29
*/

-- ----------------------------
-- Table structure for `teacher_training_replace`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_training_replace`;
CREATE TABLE `teacher_training_replace` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `REPLACE_TTR` int(11) NOT NULL COMMENT '替换学员的培训记录ID',
  `BE_REPLACED_TTR` int(11) NOT NULL COMMENT '被替换学员的培训记录ID',
  `REPLACE_STATUS` tinyint(4) NOT NULL COMMENT '0-未通过 2-通过 1-未审核',
  `CREATOR` int(11) NOT NULL COMMENT '创建者ID',
  `CREAT_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `PROJECT` int(11) NOT NULL,
  `SUBJECT` smallint(6) NOT NULL,
  `TRAINING_COLLEGE` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_BEREP_TTR` (`BE_REPLACED_TTR`),
  KEY `FK_REP_TTR` (`REPLACE_TTR`),
  CONSTRAINT `FK_BEREP_TTR` FOREIGN KEY (`BE_REPLACED_TTR`) REFERENCES `teacher_training_records` (`ID`),
  CONSTRAINT `FK_REP_TTR` FOREIGN KEY (`REPLACE_TTR`) REFERENCES `teacher` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_training_replace
-- ----------------------------

INSERT INTO `fun_category` (`ID`, `NAME`, `PID`, `PATH`, `LEVEL`, `CODE`)
VALUES
(43,'审核替换学员',12,'../admin/ttRecord_initReplaceAdu.action',2,1);

INSERT INTO `orga_cate_map` (`ID`, `CATEGORY`, `ORGANIZATION`, `ROLEID`)
VALUES (79,43,1,1);
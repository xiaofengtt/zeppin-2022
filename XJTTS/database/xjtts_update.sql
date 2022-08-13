# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.42-log)
# Database: xjtts
# Generation Time: 2016-02-22 06:46:38 +0000
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
	(42,'查看承训单位管理员权限',6,'../admin/trainingAdmin_authority.action',2,1),
	(43,'审核替换学员',12,'../admin/ttRecord_initReplaceAdu.action',2,1),
	(44,'调整学员培训学科',12,'../admin/trainingStudentOpt_changeSubjectInit.action',2,1),
	(45,'查看上级管理员',6,'../admin/projectAdmin_higherAdmin.action',2,1),
	(46,'学员全信息管理',12,'../admin/trainingStudentOpt_getTeacherInfoListInit.action',2,1),
	(47,'审核学员异动信息',12,'../admin/trainingStudentOpt_getChangeSubjectRecordsInit.action',2,1),
	(48,'项目统计分析',9,'../admin/projectBase_analysis.action',2,1),
	(49,'日志系统',NULL,NULL,1,1),
	(50,'日志记录',49,'../admin/log_initPage.action',2,1),
	(51,'材料上传情况一览',18,'../admin/trainingUnitProjectApply_documentInit.action',2,1),
	(52,'总结评估问卷',21,'../paper/paper_list.action?type=4',2,1),
	(53,'项目总结评审',30,'../admin/projectReview_initSummarizePage.action',2,1),
	(54,'其他项目培训记录',9,'../admin/otherTrainingRecords_initPage.action',2,1),
	(55,'先报后分项目报名记录',33,'../admin/ttAssigned_initPage.action',2,1);

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
	(78,42,NULL,2),
	(79,43,1,1),
	(80,45,2,1),
	(81,45,3,1),
	(82,45,4,1),
	(83,44,NULL,2),
	(84,46,NULL,2),
	(85,47,NULL,2),
	(86,48,1,1),
	(87,50,NULL,5),
	(88,51,NULL,2),
	(89,52,1,1),
	(90,53,NULL,4),
	(91,54,1,1),
	(92,55,1,1);

/*!40000 ALTER TABLE `orga_cate_map` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;



ALTER TABLE `project_apply`
ADD COLUMN `CURRICULUM`  int(11) NULL AFTER `ENROLL_ENDTIME`;

ALTER TABLE `project_apply` ADD CONSTRAINT `FK_REF_CURRICULUM` FOREIGN KEY (`CURRICULUM`) REFERENCES `document` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

alter table project add column RED_HEAD_DOCUMENT int;
alter table project add constraint FK_PROJECT_DOCUMENT foreign key(RED_HEAD_DOCUMENT) REFERENCES `document` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;
alter table teacher add column AUTHORIZED tinyint(4) NOT NULL DEFAULT '1';
alter table project add column FUNDS_TYPE tinyint(4) NOT NULL DEFAULT '1';
alter table project_apply_expert add column STATUS tinyint(4) not null default 0;
update project_apply_expert a ,(select pae.id as id from project_apply_expert pae ,project_apply pa, project p, submit s where pae.project_apply = pa.id and pa.proejct=p.id and p.PROJECT_JUDGE_PSQ = s.psq and s.valuator=pae.project_expert and pa.subject=s.subject and pa.training_college=s.training_college) b set a.status=1 where a.id=b.id;

/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : xjttss

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-01-27 09:54:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `teacher_training_reversal`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_training_reversal`;
CREATE TABLE `teacher_training_reversal` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER_TRAINING_RECORD` int(11) NOT NULL,
  `REP_TRAINING_SUBJECT` smallint(6) NOT NULL,
  `REP_CLASSES` int(11) DEFAULT NULL,
  `CREATOR` int(11) NOT NULL,
  `CREATTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` tinyint(4) NOT NULL COMMENT '0-未通过 1-通过 -1-未审核',
  `TEACHER` int(11) NOT NULL,
  `PROJECT` int(11) NOT NULL,
  `SUBJECT` smallint(6) NOT NULL,
  `TRAINING_COLLEGE` int(11) NOT NULL,
  `CLASSES` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_REF_TTRSUBJECT` (`TEACHER_TRAINING_RECORD`),
  KEY `FK_REF_TRAININGSUBJECT` (`REP_TRAINING_SUBJECT`),
  CONSTRAINT `FK_REF_TRAININGSUBJECT` FOREIGN KEY (`REP_TRAINING_SUBJECT`) REFERENCES `training_subject` (`ID`),
  CONSTRAINT `FK_REF_TTRSUBJECT` FOREIGN KEY (`TEACHER_TRAINING_RECORD`) REFERENCES `teacher_training_records` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_training_reversal
-- ----------------------------

ALTER TABLE `teacher`
ADD COLUMN `UPDATER`  varchar(20) NULL AFTER `AUTHORIZED`;

# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.42-log)
# Database: xjtts
# Generation Time: 2016-02-01 09:36:03 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `USER_ROLE` tinyint(4) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `TYPE` tinyint(4) NOT NULL COMMENT '1增加 2修改 3删除',
  `TABLE_NAME` varchar(50) NOT NULL DEFAULT '',
  `TABLE_ID` varchar(50) NOT NULL DEFAULT '',
  `REMARK` text NOT NULL,
  `TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


ALTER TABLE `teacher_training_records` ADD COLUMN `RATER` INT;
ALTER TABLE `teacher_training_records` ADD CONSTRAINT `FK_TTR_TA` FOREIGN KEY (`RATER`) REFERENCES `TRAINING_ADMIN` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `project` ADD COLUMN `PROJECT_SUMMARIZE_PSQ` INT;
ALTER TABLE `project` ADD CONSTRAINT `FK_P_SUMMARIZE` FOREIGN KEY (`PROJECT_SUMMARIZE_PSQ`) REFERENCES `PSQ` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.42-log)
# Database: xjtts
# Generation Time: 2016-02-16 08:02:54 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table project_apply_work_report
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project_apply_work_report`;

CREATE TABLE `project_apply_work_report` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `PROJECT_APPLY` int(11) NOT NULL,
  `DOCUMENT` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_PA_D` (`PROJECT_APPLY`,`DOCUMENT`),
  KEY `FK_PAWR_DOCUMENT` (`DOCUMENT`),
  CONSTRAINT `FK_PAWR_DOCUMENT` FOREIGN KEY (`DOCUMENT`) REFERENCES `document` (`ID`),
  CONSTRAINT `FK_PAWR_PA` FOREIGN KEY (`PROJECT_APPLY`) REFERENCES `project_apply` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




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
# Generation Time: 2016-02-19 07:27:25 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table project_apply_evaluate
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project_apply_evaluate`;

CREATE TABLE `project_apply_evaluate` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `PROJECT_APPLY` int(11) NOT NULL,
  `PROJECT_EXPERT` int(11) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_PA_PE` (`PROJECT_EXPERT`,`PROJECT_APPLY`),
  KEY `FK_PAEV_PA` (`PROJECT_APPLY`),
  CONSTRAINT `FK_PAEV_PE` FOREIGN KEY (`PROJECT_EXPERT`) REFERENCES `project_expert` (`ID`),
  CONSTRAINT `FK_PAEV_PA` FOREIGN KEY (`PROJECT_APPLY`) REFERENCES `project_apply` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `project_apply_evaluate` WRITE;
/*!40000 ALTER TABLE `project_apply_evaluate` DISABLE KEYS */;

INSERT INTO `project_apply_evaluate` (`ID`, `PROJECT_APPLY`, `PROJECT_EXPERT`, `STATUS`)
VALUES
	(1,350001629,1,0),
	(2,350001629,2,0),
	(5,350001629,3,0);

/*!40000 ALTER TABLE `project_apply_evaluate` ENABLE KEYS */;
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
# Generation Time: 2016-02-23 07:29:27 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table other_training_records
# ------------------------------------------------------------

DROP TABLE IF EXISTS `other_training_records`;

CREATE TABLE `other_training_records` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `YEAR` char(4) NOT NULL DEFAULT '',
  `PROJECT_TYPE` int(11) NOT NULL,
  `PROJECT_NAME` varchar(50) NOT NULL DEFAULT '',
  `SHORT_NAME` varchar(50) NOT NULL DEFAULT '',
  `TEACHER` int(11) NOT NULL,
  `TRAINING_SUBJECT` smallint(6) NOT NULL,
  `TRAINING_COLLEGE` int(11) NOT NULL,
  `TRAINING_HOUR` int(11) NOT NULL,
  `TRAINING_ONLINE_HOUR` int(11) NOT NULL,
  `STARTTIME` varchar(50) NOT NULL DEFAULT '',
  `ENDTIME` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`ID`),
  KEY `FK_OTR_PT` (`PROJECT_TYPE`),
  KEY `FK_OTR_TEACHER` (`TEACHER`),
  KEY `FK_OTR_TS` (`TRAINING_SUBJECT`),
  KEY `FK_OTR_TC` (`TRAINING_COLLEGE`),
  CONSTRAINT `FK_OTR_TS` FOREIGN KEY (`TRAINING_SUBJECT`) REFERENCES `training_subject` (`ID`),
  CONSTRAINT `FK_OTR_PT` FOREIGN KEY (`PROJECT_TYPE`) REFERENCES `project_type` (`ID`),
  CONSTRAINT `FK_OTR_TC` FOREIGN KEY (`TRAINING_COLLEGE`) REFERENCES `training_college` (`ID`),
  CONSTRAINT `FK_OTR_TEACHER` FOREIGN KEY (`TEACHER`) REFERENCES `teacher` (`ID`)
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

Date: 2016-02-25 17:25:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `project_group`
-- ----------------------------
DROP TABLE IF EXISTS `project_group`;
CREATE TABLE `project_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(200) NOT NULL COMMENT '项目名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_group
-- ----------------------------


ALTER TABLE `project`
ADD COLUMN `PROJECT_GROUP`  int(11) NULL AFTER `PROJECT_SUMMARIZE_PSQ`,
ADD COLUMN `PROJECT_INDEX`  tinyint(4) NULL AFTER `PROJECT_GROUP`;

ALTER TABLE `project` ADD CONSTRAINT `FK_PROJECT_GROUP` FOREIGN KEY (`PROJECT_GROUP`) REFERENCES `project_group` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

UPDATE project SET PROJECT_INDEX=0;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `specialist`
-- ----------------------------
DROP TABLE IF EXISTS `specialist`;
CREATE TABLE `specialist` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `IDCARD` varchar(20) NOT NULL,
  `MOBILE` varchar(20) NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `PASSWORD` varchar(20) NOT NULL,
  `SEX` tinyint(4) NOT NULL,
  `NATIONALLY` smallint(6) NOT NULL,
  `ORGANIZATION` int(11) NOT NULL,
  `DEPARTMENT` varchar(100) DEFAULT NULL,
  `PHONE` varchar(20) NOT NULL,
  `FAX` varchar(20) DEFAULT NULL,
  `JOB_TITLE` varchar(20) DEFAULT NULL,
  `JOB_DUTY` varchar(20) DEFAULT NULL,
  `POLITICS` tinyint(4) DEFAULT NULL,
  `EDUCTION_BACKGROUND` tinyint(4) DEFAULT NULL,
  `AREA` int(11) DEFAULT NULL,
  `ADDRESS` varchar(200) DEFAULT NULL,
  `POSTCODE` varchar(10) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL COMMENT '1正常2停用',
  `RESEARCH` varchar(200) DEFAULT NULL,
  `TEACH` varchar(200) DEFAULT NULL,
  `RESUME` varchar(1000) DEFAULT NULL,
  `ACHIEVEMENT` varchar(1000) DEFAULT NULL,
  `EXPERIENCE` varchar(1000) DEFAULT NULL,
  `REMARK` varchar(1000) DEFAULT NULL,
  `CREATOR` int(11) NOT NULL,
  `CREATTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_KEY_2` (`IDCARD`) USING BTREE,
  UNIQUE KEY `AK_KEY_3` (`MOBILE`) USING BTREE,
  UNIQUE KEY `AK_KEY_4` (`EMAIL`) USING BTREE,
  UNIQUE KEY `UK_hnaxghvn6yj0sf7x4n57xokc9` (`IDCARD`) USING BTREE,
  UNIQUE KEY `UK_8y5fl1392hxrlf91aulnvc37k` (`EMAIL`) USING BTREE,
  UNIQUE KEY `UK_enu9iuqow97k14lghyo39grub` (`MOBILE`) USING BTREE,
  KEY `FK_REFERENCE_62` (`AREA`) USING BTREE,
  KEY `FK_REFERENCE_63` (`EDUCTION_BACKGROUND`) USING BTREE,
  KEY `FK_REFERENCE_64` (`NATIONALLY`) USING BTREE,
  KEY `FK_REFERENCE_68` (`POLITICS`) USING BTREE,
  KEY `specialist_ibfk_5` (`ORGANIZATION`),
  CONSTRAINT `specialist_ibfk_5` FOREIGN KEY (`ORGANIZATION`) REFERENCES `training_college` (`ID`),
  CONSTRAINT `specialist_ibfk_1` FOREIGN KEY (`AREA`) REFERENCES `area` (`ID`),
  CONSTRAINT `specialist_ibfk_2` FOREIGN KEY (`EDUCTION_BACKGROUND`) REFERENCES `eduction_background` (`ID`),
  CONSTRAINT `specialist_ibfk_3` FOREIGN KEY (`NATIONALLY`) REFERENCES `ethnic` (`ID`),
  CONSTRAINT `specialist_ibfk_4` FOREIGN KEY (`POLITICS`) REFERENCES `politics` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of specialist
-- ----------------------------

ALTER TABLE `project_apply` ADD COLUMN `TRAINING_ONLINE_HOUR`  int(11) NOT NULL default 0;
UPDATE project_apply pa , project p SET pa.TRAINING_ONLINE_HOUR = pa.TRAINING_CLASSHOUR WHERE pa.proejct = p.id and p.TRAINTYPE = 2;
UPDATE project_apply pa , project p SET pa.TRAINING_CLASSHOUR = 0 WHERE pa.proejct = p.id and p.TRAINTYPE = 2;

ALTER TABLE `project`
ADD COLUMN `ISADVANCE`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否先报后分' AFTER `PROJECT_INDEX`;

ALTER TABLE `organization` DROP FOREIGN KEY `FK_REFERENCE_84`;

ALTER TABLE `teacher`
ADD COLUMN `IS_FIRST_LOGIN`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否第一次登录' AFTER `UPDATER`;

ALTER TABLE `project`
ADD COLUMN `END_TIME`  datetime NULL COMMENT '先报后分项目-教师报名截止日期' AFTER `ISADVANCE`;

ALTER TABLE `organization`
ADD COLUMN `IS_POOR`  tinyint(4) NULL DEFAULT 0 COMMENT '是否为集中连片特困地区县0-否 1-是' AFTER `TYPE`,
ADD COLUMN `IS_COUNTRY_POOR`  tinyint(4) NULL DEFAULT 0 COMMENT '是否为国家级贫困县0-否 1-是' AFTER `IS_POOR`,
ADD COLUMN `ATTRIBUTE`  tinyint(4) NULL COMMENT '学校地域属性1-城市 2-县城 3-镇区 4-乡 5-村 6-教学点' AFTER `IS_COUNTRY_POOR`;

ALTER TABLE `teacher`
ADD COLUMN `IS_TEACHING_SCHOOL`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-否 1-是' AFTER `MAJOR`;

INSERT INTO `fun_category` VALUES ('56', '基础业务数据管理', null, null, '1', '1');
INSERT INTO `fun_category` VALUES ('57', '组织架构管理', '56', '../admin/organization_initPageForAdmin.action', '2', '1');
INSERT INTO `fun_category` VALUES ('58', '教师调入调出管理', '56', '../admin/teacherAdjust.jsp', '2', '1');

INSERT INTO `orga_cate_map` VALUES ('93', '57', '1', '1');
INSERT INTO `orga_cate_map` VALUES ('94', '57', '2', '1');
INSERT INTO `orga_cate_map` VALUES ('95', '57', '3', '1');
INSERT INTO `orga_cate_map` VALUES ('97', '58', '1', '1');
INSERT INTO `orga_cate_map` VALUES ('98', '58', '2', '1');
INSERT INTO `orga_cate_map` VALUES ('99', '58', '3', '1');
INSERT INTO `orga_cate_map` VALUES ('100', '58', '4', '1');
INSERT INTO `orga_cate_map` VALUES ('101', '10', '2', '1');
INSERT INTO `orga_cate_map` VALUES ('102', '10', '3', '1');
INSERT INTO `orga_cate_map` VALUES ('103', '10', '4', '1');
INSERT INTO `orga_cate_map` VALUES ('104', '11', '2', '1');
INSERT INTO `orga_cate_map` VALUES ('105', '11', '3', '1');

ALTER TABLE `teacher_adjust`
MODIFY COLUMN `CREATETIME`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' AFTER `CHECKER_TYPE`,
ADD COLUMN `CHECKTIME`  timestamp NULL AFTER `STATUS`;

ALTER TABLE `fun_category`
ADD COLUMN `WEIGHT`  int(11) NULL COMMENT '左侧列表显示权重' AFTER `CODE`;
update fun_category fc set fc.weight=fc.id;

ALTER TABLE `project_cycle`
ADD COLUMN `CENTRALIZE`  int(7) NOT NULL DEFAULT 0 AFTER `STATUS`,
ADD COLUMN `INFORMATION`  int(7) NOT NULL DEFAULT 0 AFTER `CENTRALIZE`,
ADD COLUMN `REGIONAL`  int(7) NOT NULL AFTER `INFORMATION`,
ADD COLUMN `SCHOOL`  int(7) NOT NULL DEFAULT 0 AFTER `REGIONAL`;


ALTER TABLE `project_apply`
ADD COLUMN `CENTRALIZE`  int(4) NULL DEFAULT 0 COMMENT '集中培训学时' AFTER `TRAINING_ONLINE_HOUR`,
ADD COLUMN `INFORMATION`  int(4) NULL DEFAULT 0 COMMENT '信息技术培训学时' AFTER `CENTRALIZE`,
ADD COLUMN `REGIONAL`  int(4) NULL DEFAULT 0 COMMENT '区域特色培训学时' AFTER `INFORMATION`,
ADD COLUMN `SCHOOL`  int(4) NULL DEFAULT 0 COMMENT '校本培训学时' AFTER `REGIONAL`;



ALTER TABLE `teacher_training_records`
ADD COLUMN `TEACHER_ORGANIZATION`  int(11) NULL DEFAULT NULL COMMENT '所属学校' AFTER `RATER`,
ADD COLUMN `JOB_TITLE`  tinyint(4) NULL DEFAULT NULL COMMENT '职称' AFTER `TEACHER_ORGANIZATION`,
ADD COLUMN `EDUCTION_BACKGROUND`  tinyint(4) NULL DEFAULT NULL COMMENT ' 学历' AFTER `JOB_TITLE`,
ADD COLUMN `POLITICS`  tinyint(4) NULL DEFAULT NULL COMMENT '政治面貌' AFTER `EDUCTION_BACKGROUND`,
ADD COLUMN `TEACHER_SUBJECT`  smallint(6) NULL DEFAULT NULL COMMENT '主要教学学科' AFTER `POLITICS`,
ADD COLUMN `GRADE`  tinyint(4) NULL DEFAULT NULL COMMENT '主要教学学科' AFTER `TEACHER_SUBJECT`,
ADD COLUMN `LANGUAGE`  smallint(6) NULL DEFAULT NULL COMMENT '主要教学语言' AFTER `GRADE`,
ADD COLUMN `CENTRALIZE`  int(4) NULL DEFAULT 0 COMMENT '集中培训学时' AFTER `LANGUAGE`,
ADD COLUMN `INFORMATION`  int(4) NULL DEFAULT 0 COMMENT '信息技术培训学时' AFTER `CENTRALIZE`,
ADD COLUMN `REGIONAL`  int(4) NULL DEFAULT 0 COMMENT '区域特色培训学时' AFTER `INFORMATION`,
ADD COLUMN `SCHOOL`  int(4) NULL DEFAULT NULL COMMENT '校本培训学时' AFTER `REGIONAL`;

ALTER TABLE `teacher_training_records`
ADD INDEX `INDEX_JOB_TITLE` (`JOB_TITLE`) USING BTREE ,
ADD INDEX `INDEX_EDUCTION_BACKGROUND` (`EDUCTION_BACKGROUND`) USING BTREE ,
ADD INDEX `INDEX_POLITICS` (`POLITICS`) USING BTREE ,
ADD INDEX `INDEX_GRADE` (`GRADE`) USING BTREE ,
ADD INDEX `INDEX_LANGUAGE` (`LANGUAGE`) USING BTREE ,
ADD INDEX `INDEX_CENTRALIZE` (`CENTRALIZE`) USING BTREE ,
ADD INDEX `INDEX_INFORMATION` (`INFORMATION`) USING BTREE ,
ADD INDEX `INDEX_REGIONAL` (`REGIONAL`) USING BTREE ,
ADD INDEX `INDEX_SCHOOL` (`SCHOOL`) USING BTREE ,
ADD INDEX `INDEX_TEACHER_SUBJECT` (`TEACHER_SUBJECT`) USING BTREE ;


ALTER TABLE `teacher_training_records` ADD CONSTRAINT `FK_REFERENCE_EDUCTION_BACKGROUND` FOREIGN KEY (`EDUCTION_BACKGROUND`) REFERENCES `eduction_background` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `teacher_training_records` ADD CONSTRAINT `FK_REFERENCE_GRADE` FOREIGN KEY (`GRADE`) REFERENCES `grade` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `teacher_training_records` ADD CONSTRAINT `FK_REFERENCE_JOB_TITLE` FOREIGN KEY (`JOB_TITLE`) REFERENCES `job_title` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `teacher_training_records` ADD CONSTRAINT `FK_REFERENCE_LANGUAGE` FOREIGN KEY (`LANGUAGE`) REFERENCES `language` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `teacher_training_records` ADD CONSTRAINT `FK_REFERENCE_POLITICS` FOREIGN KEY (`POLITICS`) REFERENCES `politics` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `teacher`
ADD INDEX `INDEXT_LOGIN3` USING BTREE (`NAME`, `IDCARD`, `MOBILE`, `PASSWORD`, `EMAIL`) ;

ALTER TABLE `project_admin`
ADD INDEX `INDEXT_LOGIN1` USING BTREE (`IDCARD`, `MOBILE`, `EMAIL`, `PASSWORD`) ;

ALTER TABLE `training_admin`
ADD INDEX `INDEXT_LOGIN2` USING BTREE (`IDCARD`, `MOBILE`, `EMAIL`, `PASSWORD`) ;

ALTER TABLE `project_expert`
ADD INDEX `INDEXT_LOGIN4` USING BTREE (`IDCARD`, `MOBILE`, `EMAIL`, `PASSWORD`) ;

ALTER TABLE `teacher_training_records`
ADD INDEX `INDEX_STATUS_FINALSTATUS` USING BTREE (`CHECK_STATUS`, `FINAL_STATUS`, `TRAINING_STATUS`) ;

ALTER TABLE `teacher`
ADD INDEX `INDEXT_TEACHER_ORGANIZATION` USING BTREE (`ORGANIZATION`) ;


ALTER TABLE `project_apply`
ADD COLUMN `REMARK`  varchar(200) NULL COMMENT '备注信息' AFTER `SCHOOL`;

ALTER TABLE `teacher`
ADD INDEX `INDEXT_COUNT_111` USING BTREE (`BIRTHDAY`) ,
ADD INDEX `INDEXT_COUNT_222` USING BTREE (`SEX`) ,
ADD INDEX `INDEXT_COUNT_333` USING BTREE (`TEACHING_AGE`) ;

ALTER TABLE `organization`
ADD INDEX `INDEX_COUNT_444` USING BTREE (`DISCTYPE`) ,
ADD INDEX `INDEX_COUNT_555` USING BTREE (`ATTRIBUTE`) ,
ADD INDEX `INDEX_COUNT_666` USING BTREE (`IS_POOR`) ,
ADD INDEX `INDEX_COUNT_777` USING BTREE (`IS_COUNTRY_POOR`) ;

ALTER TABLE `teacher`
MODIFY COLUMN `CREATTIME`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `CREATOR`;

ALTER TABLE `teacher_training_records`
MODIFY COLUMN `CREATTIME`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `CREATOR`;

ALTER TABLE `project_apply`
ADD COLUMN `TOTALHOURS`  int(4) NULL COMMENT '培训总学时' AFTER `REMARK`;

ALTER TABLE `teacher_training_records`
ADD COLUMN `TOTALHOURS`  int(4) NULL COMMENT '培训总学时' AFTER `SCHOOL`;

ALTER TABLE `teacher_training_replace`
ADD COLUMN `REPLACE_REASON`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '无' COMMENT '替换原因' AFTER `TRAINING_COLLEGE`,
ADD COLUMN `CHECKNOPASS_REASON`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '无' COMMENT '审核不通过原因' AFTER `REPLACE_REASON`;

ALTER TABLE `service_apply_reply` DROP FOREIGN KEY `FK_SERVICE_APPLY_reply`;

ALTER TABLE `service_apply_reply` ADD CONSTRAINT `FK_SERVICE_APPLY_reply` FOREIGN KEY (`SERVICE_APPLY`) REFERENCES `mail_information` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `identify_classhours`
ADD COLUMN `TOTALHOURS`  int(4) NULL COMMENT '总学时' AFTER `STATUS`,
ADD COLUMN `CREDIT`  int(4) NULL COMMENT '学分' AFTER `TOTALHOURS`;

ALTER TABLE `identify_classhours_subject`
ADD COLUMN `TOTALHOURS`  int(4) NULL COMMENT '总学时' AFTER `TRAINING_SUBJECT`,
ADD COLUMN `CREDIT`  int(4) NULL COMMENT '学分' AFTER `TOTALHOURS`;

ALTER TABLE `teacher_training_records`
ADD COLUMN `CREDIT`  int(4) NULL DEFAULT 0 COMMENT '获得学分' AFTER `TOTALHOURS`;

ALTER TABLE `project_apply`
ADD COLUMN `CREDIT`  int(4) NULL DEFAULT 0 COMMENT '获得学分' AFTER `TOTALHOURS`;

ALTER TABLE `teacher_training_records` ADD CONSTRAINT `FK_TTR_TEACHING_SUBJECT` FOREIGN KEY (`TEACHER_SUBJECT`) REFERENCES `subject` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

UPDATE teacher_training_records ttr,teacher t SET ttr.TEACHER_ORGANIZATION=t.ORGANIZATION where ttr.TEACHER = t.ID;


UPDATE teacher_training_records ttr,teacher t SET ttr.JOB_TITLE=t.JOB_TITLE where ttr.TEACHER = t.ID;

UPDATE teacher_training_records ttr,teacher t SET ttr.EDUCTION_BACKGROUND=t.EDUCTION_BACKGROUND where ttr.TEACHER = t.ID;

UPDATE teacher_training_records ttr,teacher t SET ttr.POLITICS=t.POLITICS where ttr.TEACHER = t.ID;

UPDATE teacher_training_records ttr,teacher t,teaching_grade tg SET ttr.GRADE=tg.GRADE where ttr.TEACHER = t.ID and tg.TEACHER=t.ID and tg.ISPRIME=1;

UPDATE teacher_training_records ttr,teacher t,teaching_language tg SET ttr.`LANGUAGE`=tg.`LANGUAGE` where ttr.TEACHER = t.ID and tg.TEACHER=t.ID and tg.ISPRIME=1;

UPDATE teacher_training_records ttr,teacher t,teaching_subject tg SET ttr.TEACHER_SUBJECT=tg.`SUBJECT` where ttr.TEACHER = t.ID and tg.TEACHER=t.ID and tg.ISPRIME=1;

ALTER TABLE `psq`
ADD COLUMN `TOTAL_SCORE`  int(10) NULL DEFAULT 0 COMMENT '问卷总分' AFTER `SURVEYDATA`;

ALTER TABLE `teaching_grade`
DROP INDEX `AK_KEY_2` ,
ADD UNIQUE INDEX `AK_KEY_2` (`TEACHER`, `GRADE`, `ISPRIME`) USING BTREE ;


ALTER TABLE `teaching_language`
DROP INDEX `AK_KEY_2` ,
ADD UNIQUE INDEX `AK_KEY_2` (`TEACHER`, `LANGUAGE`, `ISPRIME`) USING BTREE ;

ALTER TABLE `teaching_subject`
DROP INDEX `AK_KEY_2` ,
ADD UNIQUE INDEX `AK_KEY_2` (`TEACHER`, `SUBJECT`, `ISPRIME`) USING BTREE ;


ALTER TABLE `project_apply`
ADD COLUMN `PROJECTPLAN`  int(11) NULL COMMENT '实施方案' AFTER `CREDIT`;
ALTER TABLE `project_apply` ADD CONSTRAINT `FK_REF_PROJECTPLAYS` FOREIGN KEY (`PROJECTPLAN`) REFERENCES `document` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `teacher_adjust`
MODIFY COLUMN `N_ORGANIZATION`  int(11) NULL COMMENT '教师调入学校' AFTER `O_ORGANIZATION`;


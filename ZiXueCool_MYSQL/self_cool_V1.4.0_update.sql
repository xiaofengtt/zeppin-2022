alter table sso_user add AGE TINYINT(4);
alter table sso_user add GENDER TINYINT(4);

# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 192.168.1.120 (MySQL 5.5.20)
# Database: self_cool
# Generation Time: 2015-08-12 04:02:44 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table activity
# ------------------------------------------------------------

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` char(100) NOT NULL DEFAULT '',
  `TITLE` char(100) NOT NULL DEFAULT '',
  `URL` char(200) NOT NULL DEFAULT '',
  `IMAGE` int(11) NOT NULL,
  `CONTENT_TYPE` tinyint(4) NOT NULL,
  `CONTENT` text,
  `WEIGHT` tinyint(4) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `CREATER` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FK_ACTIVITY_RESCOURCE` (`IMAGE`),
  KEY `FK_ACTIVITY_SYSUSER` (`CREATER`),
  CONSTRAINT `FK_ACTIVITY_RESCOURCE` FOREIGN KEY (`IMAGE`) REFERENCES `resource` (`ID`),
  CONSTRAINT `FK_ACTIVITY_SYSUSER` FOREIGN KEY (`CREATER`) REFERENCES `sys_user` (`ID`)
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
# Host: 192.168.1.120 (MySQL 5.5.20)
# Database: self_cool
# Generation Time: 2015-08-20 04:00:26 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


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
  PRIMARY KEY (`ID`),
  KEY `FK_REFERENCE_43` (`PARENT`),
  CONSTRAINT `FK_REFERENCE_43` FOREIGN KEY (`PARENT`) REFERENCES `funcation` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `funcation` WRITE;
/*!40000 ALTER TABLE `funcation` DISABLE KEYS */;

INSERT INTO `funcation` (`ID`, `NAME`, `PATH`, `LEVEL`, `STATUS`, `PARENT`, `SCODE`)
VALUES
	(1,'账号权限管理',NULL,1,1,NULL,NULL),
	(2,'运营管理员管理','../admin/manager.html',2,1,1,NULL),
	(3,'基础数据管理',NULL,1,1,NULL,NULL),
	(4,'业务管理','../admin/business.html',2,1,3,NULL),
	(5,'分类管理','../admin/category.html',2,1,3,NULL),
	(6,'分类检索类别','../admin/retrieve.html',2,1,3,NULL),
	(7,'学科管理','../admin/subject.html',2,1,3,NULL),
	(8,'知识点管理','../admin/knowledge.html',2,1,3,NULL),
	(9,'题型管理','../admin/itemType.html',2,1,3,NULL),
	(10,'资讯管理','../admin/information.html',2,1,3,NULL),
	(11,'广告管理','../admin/advert.html',2,1,3,NULL),
	(12,'试题资源管理',NULL,1,1,NULL,NULL),
	(13,'试题管理','../admin/itemList.html',2,1,12,NULL),
	(14,'试卷管理','../admin/paperList.html',2,1,12,NULL),
	(15,'活动管理','../admin/activity.html',2,1,3,NULL),
	(16,'版本管理','../admin/version.html',2,1,3,NULL);

/*!40000 ALTER TABLE `funcation` ENABLE KEYS */;
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
  CONSTRAINT `FK_REFERENCE_42` FOREIGN KEY (`ROLE`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_REFERENCE_44` FOREIGN KEY (`FUNCATION`) REFERENCES `funcation` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `role_funcation` WRITE;
/*!40000 ALTER TABLE `role_funcation` DISABLE KEYS */;

INSERT INTO `role_funcation` (`ID`, `ROLE`, `FUNCATION`)
VALUES
	(1,1,2),
	(2,1,4),
	(3,1,5),
	(4,1,6),
	(5,1,7),
	(6,1,8),
	(7,1,9),
	(8,1,10),
	(9,1,11),
	(10,1,13),
	(12,1,14),
	(14,1,15),
	(15,1,16),
	(11,2,13),
	(13,2,14);

/*!40000 ALTER TABLE `role_funcation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table version
# ------------------------------------------------------------

DROP TABLE IF EXISTS `version`;

CREATE TABLE `version` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `VERSION` varchar(20) NOT NULL DEFAULT '',
  `FORCED_UPDATE` tinyint(1) NOT NULL DEFAULT '0',
  `STATUS` tinyint(4) NOT NULL DEFAULT '0',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DEVICE` tinyint(4) NOT NULL,
  `FILE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_VERSION_RESOURCE` (`FILE`),
  CONSTRAINT `FK_VERSION_RESOURCE` FOREIGN KEY (`FILE`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `version` WRITE;
/*!40000 ALTER TABLE `version` DISABLE KEYS */;

INSERT INTO `version` (`ID`, `VERSION`, `FORCED_UPDATE`, `STATUS`, `CREATETIME`, `DEVICE`, `FILE`)
VALUES
	(1,'1.2.0',0,1,'2015-05-16 18:17:57',1,NULL),
	(2,'1.3.0',0,1,'2015-07-30 13:35:34',1,NULL),
	(3,'1.4.0',0,0,'2015-07-30 14:13:03',1,NULL);

/*!40000 ALTER TABLE `version` ENABLE KEYS */;
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
# Host: 192.168.1.120 (MySQL 5.5.20)
# Database: self_cool
# Generation Time: 2015-08-19 03:51:14 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


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




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


ALTER TABLE subject ADD ICON int(11) ;
ALTER TABLE subject ADD FOREIGN KEY subject ( ICON ) REFERENCES resource ( ID );
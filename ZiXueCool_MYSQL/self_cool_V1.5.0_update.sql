alter table paper add ISFREE TINYINT(1);
alter table paper add PRICE INT(11);
update paper set ISFREE = 1 , PRICE = 0 where TYPE <> 2;
alter table subject add PRICE INT(11);
update subject set PRICE = 0;

# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 192.168.1.120 (MySQL 5.5.20)
# Database: self_cool_beta1
# Generation Time: 2015-10-15 07:09:36 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table sso_user_pay
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sso_user_pay`;

CREATE TABLE `sso_user_pay` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `SSOID` int(11) NOT NULL,
  `SUBJECT` int(11) NOT NULL,
  `PAPER` int(11) DEFAULT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PRICE` int(11) NOT NULL,
  `PAYTYPE` tinyint(4) NOT NULL,
  `DEVICE` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SUPP_SSO` (`SSOID`),
  KEY `FK_SUPP_SUBJECT` (`SUBJECT`),
  KEY `FK_SUPP_PAPER` (`PAPER`),
  CONSTRAINT `FK_SUPP_PAPER` FOREIGN KEY (`PAPER`) REFERENCES `paper` (`ID`),
  CONSTRAINT `FK_SUPP_SSO` FOREIGN KEY (`SSOID`) REFERENCES `sso_user` (`ID`),
  CONSTRAINT `FK_SUPP_SUBJECT` FOREIGN KEY (`SUBJECT`) REFERENCES `subject` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;



/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/6/5 15:52:01                            */
/*==============================================================*/

ALTER TABLE PROJECT DROP FOREIGN KEY FK_REFERENCE_75;

DROP TABLE IF EXISTS RESULT;

DROP TABLE IF EXISTS SUBMIT;

DROP TABLE IF EXISTS ANSWER;

DROP TABLE IF EXISTS QUESTION;

DROP TABLE IF EXISTS PRJECT_PSQ;

DROP TABLE IF EXISTS PSQ;

DROP TABLE IF EXISTS LOGINKEY;

DROP TABLE IF EXISTS orga_cate_map;

DROP TABLE IF EXISTS fun_category;


/*==============================================================*/
/* Table: PROJECT                                               */
/*==============================================================*/
ALTER TABLE PROJECT ADD COLUMN EVALUATION_TEACHER_PSQ INT;
ALTER TABLE PROJECT ADD COLUMN EVALUATION_TRAINING_PSQ INT;
ALTER TABLE PROJECT MODIFY COLUMN NUMBER INT NOT NULL;
ALTER TABLE PROJECT_APPLY MODIFY COLUMN APPROVE_NUMBER INT NOT NULL;

/*==============================================================*/
/**/
/*==============================================================*/
ALTER TABLE teacher_training_records CHANGE `TRAINING_SCORE` `TRAINING_SCORE` FLOAT(11) NULL;

/*==============================================================*/
/* Table: ANSWER                                                */
/*==============================================================*/
CREATE TABLE ANSWER
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   PSQ                  INT NOT NULL,
   QUESTION             INT NOT NULL,
   INX                  INT NOT NULL,
   NAME                 VARCHAR(2000) NOT NULL COMMENT '如果是填空题就是空的正确答案',
   ISRIGHT              BOOLEAN NOT NULL,
   ISDEFAULT            BOOLEAN NOT NULL,
   PIC                  VARCHAR(200),
   SCORE                INT NOT NULL DEFAULT 0,
   JUMP                 INT,
   ABOUT                VARCHAR(500),
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (QUESTION, INX)
);

/*==============================================================*/
/* Table: PSQ                                                   */
/*==============================================================*/
CREATE TABLE PSQ
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   TITLE                VARCHAR(1000) NOT NULL,
   ABOUT                VARCHAR(2000) NOT NULL,
   TYPE                 TINYINT NOT NULL COMMENT '1项目招标专家评分 
            2教评学
            3学评教',
   STATUS               TINYINT NOT NULL COMMENT '1草稿2发布3停止',
   CLOSING              VARCHAR(2000) NOT NULL,
   GOTOURL              VARCHAR(400),
   THEWAY               TINYINT NOT NULL DEFAULT 1 COMMENT '1在一页中呈现整张卷子答题
            2在一页只答一道题',
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   SURVEYDATA		VARCHAR(10000),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: QUESTION                                              */
/*==============================================================*/
CREATE TABLE QUESTION
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   PSQ                  INT NOT NULL,
   NAME                 VARCHAR(2000) NOT NULL,
   INX                  INT NOT NULL,
   TYPE                 TINYINT NOT NULL COMMENT '1列表单选2评分单选3测试单选题4列表多选题5评分多选题6测试多选题',
   ISMUST               BOOLEAN NOT NULL DEFAULT 0,
   ARRANGE              TINYINT NOT NULL DEFAULT 1 COMMENT '1竖向排列
            2横向排列
            3横向二列
            4横向三列',
   HINT                 VARCHAR(2000),
   SCALE                INT NOT NULL DEFAULT 1 COMMENT '1列表2星形3心形4大拇指',
   IS_COUNT             BOOL NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RESULT                                                */
/*==============================================================*/
CREATE TABLE RESULT
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   SUBMIT               INT NOT NULL,
   QUESTION             INT NOT NULL,
   ANSWER               INT NOT NULL,
   CONTENT              VARCHAR(2000) NOT NULL COMMENT '选择题',
   SCORE                INT NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: SUBMIT                                                */
/*==============================================================*/
CREATE TABLE SUBMIT
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   VALUATOR             INT NOT NULL,
   PSQ                  INT NOT NULL,
   PROJECT              INT NOT NULL,
   SUBJECT              SMALLINT NOT NULL,
   TRAINING_COLLEGE     INT NOT NULL,
   CREATER              INT NOT NULL,
   CREATETIME           TIMESTAMP NOT NULL,
   IP			VARCHAR(60),
   UUID			VARCHAR(50),
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (PROJECT, SUBJECT, TRAINING_COLLEGE, VALUATOR, PSQ)
);

ALTER TABLE ANSWER ADD CONSTRAINT FK_REFERENCE_45 FOREIGN KEY (QUESTION)
      REFERENCES QUESTION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ANSWER ADD CONSTRAINT FK_REFERENCE_46 FOREIGN KEY (PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE QUESTION ADD CONSTRAINT FK_REFERENCE_44 FOREIGN KEY (PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE RESULT ADD CONSTRAINT FK_REFERENCE_102 FOREIGN KEY (SUBMIT)
      REFERENCES SUBMIT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE RESULT ADD CONSTRAINT FK_REFERENCE_48 FOREIGN KEY (ANSWER)
      REFERENCES ANSWER (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE RESULT ADD CONSTRAINT FK_REFERENCE_49 FOREIGN KEY (QUESTION)
      REFERENCES QUESTION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SUBMIT ADD CONSTRAINT FK_REFERENCE_96 FOREIGN KEY (PROJECT)
      REFERENCES PROJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SUBMIT ADD CONSTRAINT FK_REFERENCE_97 FOREIGN KEY (SUBJECT)
      REFERENCES TRAINING_SUBJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SUBMIT ADD CONSTRAINT FK_REFERENCE_98 FOREIGN KEY (TRAINING_COLLEGE)
      REFERENCES TRAINING_COLLEGE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SUBMIT ADD CONSTRAINT FK_REFERENCE_99 FOREIGN KEY (PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT ADD CONSTRAINT FK_REFERENCE_75 FOREIGN KEY (PROJECT_JUDGE_PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT ADD CONSTRAINT FK_REFERENCE_94 FOREIGN KEY (EVALUATION_TRAINING_PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;
      
ALTER TABLE PROJECT ADD CONSTRAINT FK_REFERENCE_100 FOREIGN KEY (EVALUATION_TEACHER_PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;


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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

insert  into `fun_category`(`ID`,`NAME`,`PID`,`PATH`,`LEVEL`,`CODE`) values (1,'基础业务数据管理',NULL,NULL,1,1),(2,'项目类型设置',1,'../admin/projectType_initPage.action',2,1),(3,'培训科目设置',1,'../admin/trainingSubject_initPage.action',2,1),(4,'承训单位设置',1,'../admin/trainingCollege_initPage.action',2,1),(5,'组织架构设置',1,'../admin/organization_initPage.action',2,1),(6,'账号管理',NULL,NULL,1,1),(7,'项目管理员管理',6,'../admin/projectAdmin_initPage.action',2,1),(8,'承训单位管理员管理',6,'../admin/trainingAdmin_initPage.action',2,1),(9,'项目管理',NULL,NULL,1,1),(10,'项目信息管理',9,'../admin/projectBase_initPage.action',2,1),(11,'项目申报结果监审',9,'../admin/projectApply_initPage.action',2,1),(12,'学员管理',NULL,NULL,1,1),(13,'学员基本信息管理',12,'../admin/teacherManage_initPage.action',2,1),(14,'学员名额分配管理',12,'../admin/assignTeacherTask_initPage.action',2,1),(15,'学员报送管理',12,'../admin/ttRecord_initReortAsstPage.action',2,1),(16,'学员审核管理',12,'../admin/ttRecord_initAduPage.action',2,1),(17,'学员报到及成绩管理',12,'../admin/trainingStudentOpt_initPage.action',2,1),(18,'项目申报管理',NULL,NULL,1,1),(19,'申报项目',18,'../admin/trainingUnitProjectApply_initPage.action',NULL,NULL),(20,'项目申报记录',18,'../admin/trainingUnitProjectApplyHistory.jsp',NULL,NULL),(21,'问卷管理',NULL,NULL,1,1),(22,'学评教问卷',21,'../paper/paper_list.action?type=3',2,1),(23,'教评学问卷',21,'../paper/paper_list.action?type=2',2,1),(24,'专家评分问卷',21,'../paper/paper_list.action?type=1',2,1),(25,'查看学员成绩',12,'../admin/ttRecord_initMarkPage.action',2,1),(26,'项目中标统计',9,'../admin/projectBase_initProjectResult.action',2,1),(27,'名额分配统计', 12, '../admin/assignTeacherTask_initAssignResult.action', 2, 1),(28,'报送情况统计', 12, '../admin/ttRecord_initttRecordResult.action', 2, 1),(29, '评审专家管理', 6, '../admin/projectExpertManage_initPage.action', 2, 1),(30, '项目评审管理', NULL, NULL, 1, 1),(31, '项目评审', 30, '../admin/projectReview_initPage.action', 2, 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

insert  into `orga_cate_map`(`ID`,`CATEGORY`,`ORGANIZATION`,`ROLEID`) values (1,2,NULL,5),(2,3,NULL,5),(3,4,NULL,5),(4,5,NULL,5),(5,7,NULL,5),(6,8,NULL,5),(7,7,1,1),(8,8,1,1),(9,10,1,1),(10,11,1,1),(11,13,1,1),(12,14,1,1),(13,7,2,1),(14,13,2,1),(15,14,2,1),(16,7,3,1),(17,13,3,1),(18,14,3,1),(19,7,4,1),(20,13,4,1),(22,15,1,1),(23,16,1,1),(24,15,2,1),(25,16,2,1),(27,16,3,1),(28,15,4,1),(29,16,4,1),(30,15,3,1),(31,8,NULL,2),(32,17,NULL,2),(33,19,NULL,2),(34,20,NULL,2),(35,22,1,1),(36,23,1,1),(37,24,1,1),(38,25,1,1),(39,25,2,1),(40,25,3,1),(41,25,4,1),(42,26,1,1),(43,27,1,1),(44,27,2,1),(45,27,3,1),(46,27,4,1),(47,28,1,1),(48,28,2,1),(49,28,3,1),(50,29,1,1),(51,29,NULL,5),(52,31,NULL,4);

ALTER TABLE `project_apply` ADD column  `START_MESSAGE` int(11) DEFAULT NULL;
ALTER TABLE `project_apply` ADD column  `WORK_REPORT` int(11) DEFAULT NULL;
ALTER TABLE `project_apply` ADD column  `PROFORMANCE_REPORT` int(11) DEFAULT NULL;
ALTER TABLE `project_apply` ADD CONSTRAINT `FK_REFERENCE_104` FOREIGN KEY (`PROFORMANCE_REPORT`) REFERENCES `document` (`ID`);
ALTER TABLE `project_apply` ADD CONSTRAINT `FK_REFERENCE_103` FOREIGN KEY (`START_MESSAGE`) REFERENCES `document` (`ID`);
ALTER TABLE `project_apply` ADD CONSTRAINT `FK_REFERENCE_101` FOREIGN KEY (`WORK_REPORT`) REFERENCES `document` (`ID`);

INSERT `subject`(NAME) VALUES ('无');
INSERT `language`(NAME) VALUES ('无');
INSERT grade(NAME) VALUES ('无');
INSERT chinese_language_level(NAME) VALUES('未测试');


/*==============================================================*/
/* ORGANIZATION加SCODE                                          */
/*==============================================================*/
ALTER TABLE organization ADD COLUMN SCODE VARCHAR(100);


create TEMPORARY table temp1 select * from organization where 1=2 ;

delete from temp1;
update organization a set a.SCODE = convert(1000000000 + id, char(10)) where a.level =1;

insert into temp1 select * from organization; 
update organization set SCODE = CONCAT((select temp1.SCODE from temp1 where temp1.id = organization.pid) , convert(1000000000 + id, char(10))) where level=2;
delete from temp1;

insert into temp1 select * from organization; 
update organization set SCODE = CONCAT((select temp1.SCODE from temp1 where temp1.id = organization.pid) , convert(1000000000 + id, char(10))) where level=3;
delete from temp1;

insert into temp1 select * from organization; 
update organization set SCODE = CONCAT((select temp1.SCODE from temp1 where temp1.id = organization.pid) , convert(1000000000 + id, char(10))) where level=4;
delete from temp1;


/*==============================================================*/
/* PROJECT_TYPE加SCODE                                          */
/*==============================================================*/
ALTER TABLE PROJECT_TYPE ADD COLUMN SCODE VARCHAR(100);

create TEMPORARY table temp2 select * from PROJECT_TYPE where 1=2 ;

delete from temp2;
update PROJECT_TYPE a set a.SCODE = convert(1000000000 + id, char(10)) where a.level =1;


insert into temp2 select * from PROJECT_TYPE; 
update PROJECT_TYPE set SCODE = CONCAT((select temp2.SCODE from temp2 where temp2.id = PROJECT_TYPE.pid) , convert(1000000000 + id, char(10))) where level=2;
delete from temp2;

/*==============================================================*/
/* 创建表 project_apply_expert                                  */
/*==============================================================*/
CREATE TABLE `project_apply_expert` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_APPLY` int(11) NOT NULL,
  `PROJECT_EXPERT` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_PA_PE` (`PROJECT_APPLY`,`PROJECT_EXPERT`) USING BTREE,
  KEY `FK_PAE_PE` (`PROJECT_EXPERT`),
  CONSTRAINT `FK_PAE_PA` FOREIGN KEY (`PROJECT_APPLY`) REFERENCES `project_apply` (`ID`),
  CONSTRAINT `FK_PAE_PE` FOREIGN KEY (`PROJECT_EXPERT`) REFERENCES `project_expert` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*
SQLyog Community v11.13 (32 bit)
MySQL - 5.1.73-log : Database - xjtts
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`xjtts` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `xjtts`;

/*Table structure for table `hsdtestscore` */

CREATE TABLE `hsdtestscore` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plane` float NOT NULL,
  `organize` float NOT NULL,
  `assess` float NOT NULL,
  `skill` float NOT NULL,
  `learn` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `hsdtestscore` */

/*Table structure for table `hsdtest` */

CREATE TABLE `hsdtest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `ch` int(11) DEFAULT NULL,
  `op` int(11) DEFAULT NULL,
  `teacher` int(11) NOT NULL,
  `st` varchar(1000) NOT NULL,
  `asw` varchar(1000) NOT NULL,
  `reason` varchar(1000) NOT NULL,
  `recommend` varchar(1000) NOT NULL,
  `suggest` varchar(1000) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ch` (`ch`),
  KEY `op` (`op`),
  KEY `teacher` (`teacher`),
  CONSTRAINT `hsdtest_ibfk_1` FOREIGN KEY (`ch`) REFERENCES `hsdtestscore` (`id`),
  CONSTRAINT `hsdtest_ibfk_2` FOREIGN KEY (`op`) REFERENCES `hsdtestscore` (`id`),
  CONSTRAINT `hsdtest_ibfk_3` FOREIGN KEY (`teacher`) REFERENCES `teacher` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `hsdtest` */



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


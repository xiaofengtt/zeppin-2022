ALTER TABLE `project_cycle`
ADD COLUMN `STUDYHOUR`  varchar(500) NULL AFTER `TOTALHOURS`;

ALTER TABLE `identify_classhours`
ADD COLUMN `STUDYHOUR`  varchar(500) NULL COMMENT '学时结构' AFTER `CREDIT`;

ALTER TABLE `identify_classhours_project_year`
ADD COLUMN `STUDYHOUR`  varchar(500) NULL COMMENT '学时结构' AFTER `CREATETIME`;

ALTER TABLE `identify_classhours_subject`
ADD COLUMN `STUDYHOUR`  varchar(500) NULL COMMENT '学时结构' AFTER `CREDIT`;

ALTER TABLE `identify_classhours_subject_year`
ADD COLUMN `STUDYHOUR`  varchar(500) NULL AFTER `YEAR`;

ALTER TABLE `project_apply`
ADD COLUMN `STUDYHOUR`  varchar(500) NULL AFTER `PROJECTPLAN`;

ALTER TABLE `teacher_training_records`
ADD COLUMN `STUDYHOUR`  varchar(500) NULL AFTER `CREDIT`;

update project_cycle p 
set p.STUDYHOUR=CONCAT('[{"name":"certralize","nameCN":"集中培训学时","value":',p.CENTRALIZE,'},{"name":"information","nameCN":"信息技术培训学时","value":',p.INFORMATION,'},{"name":"regional","nameCN":"区域特色培训学时","value":',p.REGIONAL,'},{"name":"school","nameCN":"校本培训学时","value":',p.SCHOOL,'},{"name":"totalhours","nameCN":"培训总学时","value":',p.TOTALHOURS,'}]');

update identify_classhours p 
set p.STUDYHOUR=CONCAT('[{"name":"certralize","nameCN":"集中培训学时","value":',p.CENTRALIZE,'},{"name":"information","nameCN":"信息技术培训学时","value":',p.INFORMATION,'},{"name":"regional","nameCN":"区域特色培训学时","value":',p.REGIONAL,'},{"name":"school","nameCN":"校本培训学时","value":',p.SCHOOL,'},{"name":"totalhours","nameCN":"培训总学时","value":',p.TOTALHOURS,'}]');

update identify_classhours_project_year p 
set p.STUDYHOUR=CONCAT('[{"name":"certralize","nameCN":"集中培训学时","value":',p.CENTRALIZE,'},{"name":"information","nameCN":"信息技术培训学时","value":',p.INFORMATION,'},{"name":"regional","nameCN":"区域特色培训学时","value":',p.REGIONAL,'},{"name":"school","nameCN":"校本培训学时","value":',p.SCHOOL,'},{"name":"totalhours","nameCN":"培训总学时","value":',p.TOTALHOURS,'}]');

update identify_classhours_subject p 
set p.STUDYHOUR=CONCAT('[{"name":"certralize","nameCN":"集中培训学时","value":',p.CENTRALIZE,'},{"name":"information","nameCN":"信息技术培训学时","value":',p.INFORMATION,'},{"name":"regional","nameCN":"区域特色培训学时","value":',p.REGIONAL,'},{"name":"school","nameCN":"校本培训学时","value":',p.SCHOOL,'},{"name":"totalhours","nameCN":"培训总学时","value":',p.TOTALHOURS,'}]');

update identify_classhours_subject_year p 
set p.STUDYHOUR=CONCAT('[{"name":"certralize","nameCN":"集中培训学时","value":',p.CENTRALIZE,'},{"name":"information","nameCN":"信息技术培训学时","value":',p.INFORMATION,'},{"name":"regional","nameCN":"区域特色培训学时","value":',p.REGIONAL,'},{"name":"school","nameCN":"校本培训学时","value":',p.SCHOOL,'},{"name":"totalhours","nameCN":"培训总学时","value":',p.TOTALHOURS,'}]');


ALTER TABLE `teacher_training_records`
ADD COLUMN `PROJECT_CYCLE`  int(11) NOT NULL AFTER `STUDYHOUR`;

ALTER TABLE `teacher_training_records`
ADD INDEX `INDEX_PROJECT_CYCLE_TTR` (`PROJECT_CYCLE`) USING BTREE ;

ALTER TABLE `project_apply`
ADD COLUMN `PROJECT_CYCLE`  int(11) NOT NULL AFTER `STUDYHOUR`,
ADD INDEX `INDEX_PROJECT_CYCLE_PA` (`PROJECT_CYCLE`) USING BTREE ;

ALTER TABLE `project`
ADD COLUMN `PROJECT_CYCLE`  int(11) NOT NULL COMMENT '周期概念' AFTER `END_TIME`,
ADD INDEX `INDEX_PROJECT_CYCLE_PROJECT` (`PROJECT_CYCLE`) USING BTREE ;

-- 处理ttr和 pa的数据
INSERT INTO `project_cycle` VALUES ('2', '“六五”周期', '2019', '2023', '0', '2019-07-30 11:28:47', '1', '0', '0', '0', '0', '0', '[{\"name\":\"infomationfuse\",\"nameCN\":\"信息技术与融合学时（集中）\",\"value\":160},{\"name\":\"infomationfusedl\",\"nameCN\":\"信息技术与融合学时（远程）\",\"value\":160},{\"name\":\"publicsub\",\"nameCN\":\"公共课及专业课学时（集中）\",\"value\":50},{\"name\":\"publicsubdl\",\"nameCN\":\"公共课及专业课学时（远程）\",\"value\":160},{\"name\":\"electives\",\"nameCN\":\"选修课学时（集中）\",\"value\":60},{\"name\":\"electivesdl\",\"nameCN\":\"选修课学时（远程）\",\"value\":60},{\"name\":\"schoolsub\",\"nameCN\":\"校本课程学时（集中）\",\"value\":90},{\"name\":\"schoolsubdl\",\"nameCN\":\"校本课程学时（远程）\",\"value\":90}]');

update project pa set pa.PROJECT_CYCLE=1 where pa.`YEAR`<'2019';
update project pa set pa.PROJECT_CYCLE=2 where pa.`YEAR`>='2019';

update project_apply pa LEFT JOIN project p on pa.PROEJCT=p.id set pa.PROJECT_CYCLE=1 where p.`YEAR`<'2019';
update project_apply pa LEFT JOIN project p on pa.PROEJCT=p.id set pa.PROJECT_CYCLE=2 where p.`YEAR`>='2019';


update teacher_training_records pa LEFT JOIN project p on pa.PROJECT=p.id set pa.PROJECT_CYCLE=1 where p.`YEAR`<'2019';
update teacher_training_records pa LEFT JOIN project p on pa.PROJECT=p.id set pa.PROJECT_CYCLE=2 where p.`YEAR`>='2019';


update project_apply p 
set p.STUDYHOUR=CONCAT('[{"name":"certralize","nameCN":"集中培训学时","value":',p.CENTRALIZE,'},{"name":"information","nameCN":"信息技术培训学时","value":',p.INFORMATION,'},{"name":"regional","nameCN":"区域特色培训学时","value":',p.REGIONAL,'},{"name":"school","nameCN":"校本培训学时","value":',p.SCHOOL,'},{"name":"totalhours","nameCN":"培训总学时","value":',p.TOTALHOURS,'}]')
where p.PROJECT_CYCLE = 1;

update teacher_training_records p 
set p.STUDYHOUR=CONCAT('[{"name":"certralize","nameCN":"集中培训学时","value":',p.CENTRALIZE,'},{"name":"information","nameCN":"信息技术培训学时","value":',p.INFORMATION,'},{"name":"regional","nameCN":"区域特色培训学时","value":',p.REGIONAL,'},{"name":"school","nameCN":"校本培训学时","value":',p.SCHOOL,'},{"name":"totalhours","nameCN":"培训总学时","value":',p.TOTALHOURS,'}]')
 where p.INFORMATION is not NULL and p.PROJECT_CYCLE = 1;

-- ----------------------------
-- Table structure for `identify_studyhour`
-- ----------------------------
DROP TABLE IF EXISTS `identify_studyhour`;
CREATE TABLE `identify_studyhour` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_CYCLE` int(11) NOT NULL,
  `PROJECT_TYPE` int(11) NOT NULL,
  `YEAR` char(4) DEFAULT NULL,
  `PROJECT` int(11) DEFAULT NULL,
  `TRAINING_SUBJECT` smallint(6) DEFAULT NULL,
  `STUDYHOUR` varchar(500) DEFAULT NULL COMMENT '学时结构',
  `CREDIT` int(4) DEFAULT '0' COMMENT '学分',
  `STATUS` tinyint(4) NOT NULL,
  `CREATOR` int(11) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


INSERT INTO identify_studyhour(project_cycle,project_type,year,project,training_subject,studyhour,credit,status,creator,createtime)
	SELECT 1, ic.PROJECT_TYPE, NULL, NULL, NULL, ic.STUDYHOUR, ic.CREDIT, ic.`STATUS`, ic.CREATOR, ic.CREATETIME
	FROM identify_classhours ic;

INSERT INTO identify_studyhour(project_cycle,project_type,year,project,training_subject,studyhour,credit,status,creator,createtime)
	SELECT 1, ic.PROJECT_TYPE, NULL, NULL, ic.TRAINING_SUBJECT, ic.STUDYHOUR, ic.CREDIT, ic.`STATUS`, ic.CREATOR, ic.CREATETIME
	FROM identify_classhours_subject ic;

INSERT INTO identify_studyhour(project_cycle,project_type,year,project,training_subject,studyhour,credit,status,creator,createtime)
	SELECT 1, ic.PROJECT_TYPE, ic.year, NULL, ic.TRAINING_SUBJECT, ic.STUDYHOUR, ic.CREDIT, ic.`STATUS`, ic.CREATOR, ic.CREATETIME
	FROM identify_classhours_subject_year ic;

INSERT INTO identify_studyhour(project_cycle,project_type,year,project,training_subject,studyhour,credit,status,creator,createtime)
	SELECT 1, p.TYPE, ic.year, ic.PROJECT, ic.TRAINING_SUBJECT, ic.STUDYHOUR, ic.CREDIT, ic.`STATUS`, ic.CREATOR, ic.CREATETIME
	FROM identify_classhours_project_year ic 
	LEFT JOIN project p on ic.PROJECT = p.ID




select * from  project_apply pa LEFT JOIN project p on pa.PROEJCT=p.id where p.`YEAR`>='2019'

select * from project_apply where PROJECT_CYCLE is null



INSERT INTO `fun_category` VALUES ('100', '六五周期学员审核管理', '9', '../admin/ttRecord_initAdu65Page.action', '2', '1', '100');
INSERT INTO `fun_category` VALUES ('101', '六五周期自主报名学员审核管理', '9', '../admin/ttRecord_initReview65Page.action', '2', '1', '101');
INSERT INTO `fun_category` VALUES ('102', '六五周期查看学员成绩', '9', '../admin/ttRecord_initMark65Page.action', '2', '1', '102');
INSERT INTO `fun_category` VALUES ('103', '六五周期学员成绩管理', '66', '../admin/ttResult_init65Page.action', '2', '1', '103');

INSERT INTO `fun_category` VALUES ('104', '六五周期学员成绩管理', '68', '../admin/ttResult_init65Page.action', '2', '1', '104');

INSERT INTO `fun_category` VALUES ('105', '六五周期学员成绩管理', '70', '../admin/ttResult_init65Page.action', '2', '1', '105');
INSERT INTO `fun_category` VALUES ('106', '六五周期其他培训项目学员成绩管理', '87', '../admin/ttResult_initOther65Page.action', '2', '1', '106');




































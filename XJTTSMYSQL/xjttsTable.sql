/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/5/30 21:41:57                           */
/*==============================================================*/


DROP TABLE IF EXISTS ANSWER;

DROP TABLE IF EXISTS AREA;

DROP TABLE IF EXISTS ASSIGN_TEACHER_CHECK;

DROP TABLE IF EXISTS ASSIGN_TEACHER_TASK;

DROP TABLE IF EXISTS CHINESE_LANGUAGE_LEVEL;

DROP TABLE IF EXISTS DOCUMENT;

DROP TABLE IF EXISTS EDUCTION_BACKGROUND;

DROP TABLE IF EXISTS ETHNIC;

DROP TABLE IF EXISTS GRADE;

DROP TABLE IF EXISTS JOB_DUTY;

DROP TABLE IF EXISTS JOB_TITLE;

DROP TABLE IF EXISTS LANGUAGE;

DROP TABLE IF EXISTS LOGINKEY;

DROP TABLE IF EXISTS ORGANIZATION;

DROP TABLE IF EXISTS ORGANIZATION_LEVEL;

DROP TABLE IF EXISTS POLITICS;

DROP TABLE IF EXISTS PROJECT;

DROP TABLE IF EXISTS PROJECT_ADMIN;

DROP TABLE IF EXISTS PROJECT_ADMIN_RIGHT;

DROP TABLE IF EXISTS PROJECT_APPLY;

DROP TABLE IF EXISTS PROJECT_COLLEGE_RANGE;

DROP TABLE IF EXISTS PROJECT_EXPERT;

DROP TABLE IF EXISTS PROJECT_LEVEL;

DROP TABLE IF EXISTS PROJECT_SUBJECT_RANGE;

DROP TABLE IF EXISTS PROJECT_TYPE;

DROP TABLE IF EXISTS PSQ;

DROP TABLE IF EXISTS QUESTION;

DROP TABLE IF EXISTS RESULT;

DROP TABLE IF EXISTS SUBJECT;

DROP TABLE IF EXISTS TEACHER;

DROP TABLE IF EXISTS TEACHER_TRAINING_RECORDS;

DROP TABLE IF EXISTS TEACHEXPERT;

DROP TABLE IF EXISTS TEACHING_GRADE;

DROP TABLE IF EXISTS TEACHING_LANGUAGE;

DROP TABLE IF EXISTS TEACHING_SUBJECT;

DROP TABLE IF EXISTS TRAINING_ADMIN;

DROP TABLE IF EXISTS TRAINING_COLLEGE;

DROP TABLE IF EXISTS TRAINING_SUBJECT;

DROP TABLE IF EXISTS UNIVERSITY_INFO;

DROP TABLE IF EXISTS FUN_CATEGORY;

DROP TABLE IF EXISTS ORGA_CATE_MAP;

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
/* Table: AREA                                                  */
/*==============================================================*/
CREATE TABLE AREA
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   LEVEL                TINYINT NOT NULL COMMENT '1是省2是市3是区县',
   PID                  INT,
   WEIGHT               TINYINT NOT NULL DEFAULT 0 COMMENT '排序用',
   CODE                 VARCHAR(30) NOT NULL,
   PARENTCODE           VARCHAR(45) NOT NULL DEFAULT '0',
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ASSIGN_TEACHER_CHECK                                  */
/*==============================================================*/
CREATE TABLE ASSIGN_TEACHER_CHECK
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   TEACHER_TRAINING_RECORDS INT NOT NULL,
   TYPE                 TINYINT NOT NULL,
   CHECKER              INT NOT NULL,
   CHECKTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ASSIGN_TEACHER_TASK                                   */
/*==============================================================*/
CREATE TABLE ASSIGN_TEACHER_TASK
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   PROJECT              INT NOT NULL,
   SUBJECT              SMALLINT NOT NULL,
   TRAINING_COLLEGE     INT NOT NULL,
   G_ORGANIZATION       INT NOT NULL,
   P_ORGANIZATION       INT NOT NULL,
   PID                  INT,
   LEVEL                TINYINT NOT NULL DEFAULT 1,
   TIMEUP               DATETIME NOT NULL,
   STATUS               TINYINT NOT NULL DEFAULT 1 COMMENT '1可报送
            2停止报送',
   TEACHER_NUMBER       INT NOT NULL,
   RECEIVE_FLAG         TINYINT NOT NULL DEFAULT 1 COMMENT '1未读
            2已读',
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (PROJECT, SUBJECT, TRAINING_COLLEGE, G_ORGANIZATION, P_ORGANIZATION)
);

/*==============================================================*/
/* Table: CHINESE_LANGUAGE_LEVEL                                */
/*==============================================================*/
CREATE TABLE CHINESE_LANGUAGE_LEVEL
(
   ID                   TINYINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   LEVEL                CHAR(3) NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: DOCUMENT                                              */
/*==============================================================*/
CREATE TABLE DOCUMENT
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   TYPE                 TINYINT NOT NULL COMMENT '1项目申报模板
            2项目申报书
            3项目实施方案',
   TITLE                VARCHAR(200) NOT NULL,
   NAME                 VARCHAR(200) NOT NULL,
   RESOURCE_TYPE        TINYINT NOT NULL COMMENT '1WORD
            2PDF
            3EXCEL',
   RESOURCE_PATH        VARCHAR(500),
   RESOURCE_URL         VARCHAR(500),
   SIZE                 INT,
   CREATER              INT NOT NULL,
   CREATETIME           TIMESTAMP NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: EDUCTION_BACKGROUND                                   */
/*==============================================================*/
CREATE TABLE EDUCTION_BACKGROUND
(
   ID                   TINYINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(30) NOT NULL,
   LEVEL                TINYINT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ETHNIC                                                */
/*==============================================================*/
CREATE TABLE ETHNIC
(
   ID                   SMALLINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   WEIGHT               TINYINT NOT NULL DEFAULT 0 COMMENT '把常用的民族排序在前',
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: GRADE                                                 */
/*==============================================================*/
CREATE TABLE GRADE
(
   ID                   TINYINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(80) NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: JOB_DUTY                                              */
/*==============================================================*/
CREATE TABLE JOB_DUTY
(
   ID                   TINYINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: JOB_TITLE                                             */
/*==============================================================*/
CREATE TABLE JOB_TITLE
(
   ID                   TINYINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   LEVEL                CHAR(3),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: LANGUAGE                                              */
/*==============================================================*/
CREATE TABLE LANGUAGE
(
   ID                   SMALLINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   WEIGHT               TINYINT NOT NULL DEFAULT 0,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: LOGINKEY                                              */
/*==============================================================*/
CREATE TABLE LOGINKEY
(
   ID                   VARCHAR(30) NOT NULL,
   PROJECT              INT NOT NULL,
   PLANSUM              SMALLINT,
   REALSUM              SMALLINT NOT NULL,
   STATUS               CHAR(1) NOT NULL COMMENT '1正常2停用',
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_3 (PROJECT)
);

/*==============================================================*/
/* Table: ORGANIZATION                                          */
/*==============================================================*/
CREATE TABLE ORGANIZATION
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(200) NOT NULL,
   SHORT_NAME           VARCHAR(200) NOT NULL,
   LEVEL                TINYINT NOT NULL COMMENT '1省教育厅2市州教育局3地县区教育局4学校',
   PID                  INT,
   ISSCHOOL             BOOLEAN NOT NULL,
   GRADE                TINYINT COMMENT '1幼儿园
            2小学
            3初中
            4高中',
   ADRESS               VARCHAR(200),
   AREA                 INT NOT NULL,
   CONTACTS             VARCHAR(50),
   PHONE                VARCHAR(20),
   FAX                  VARCHAR(20),
   STATUS               TINYINT NOT NULL COMMENT '1正常2停用',
   CREATOR              INT,
   CREATTIME            TIMESTAMP NOT NULL,
   PINYING              VARCHAR(50),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ORGANIZATION_LEVEL                                    */
/*==============================================================*/
CREATE TABLE ORGANIZATION_LEVEL
(
   ID                   TINYINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(80) NOT NULL,
   LEVEL                TINYINT NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: POLITICS                                              */
/*==============================================================*/
CREATE TABLE POLITICS
(
   ID                   TINYINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(20) NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PROJECT                                               */
/*==============================================================*/
CREATE TABLE PROJECT
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(200) NOT NULL,
   SHORTNAME            VARCHAR(200) NOT NULL,
   TYPE                 INT NOT NULL,
   YEAR                 CHAR(4) NOT NULL,
   TRAINTYPE            TINYINT NOT NULL COMMENT '1集中面授
            2远程培训',
   SUBJECT_MAX          TINYINT NOT NULL,
   TIMEUP               DATETIME NOT NULL,
   FUNDS                DOUBLE(10,2) NOT NULL,
   NUMBER               SMALLINT NOT NULL,
   PROJECT_APPLY_TEMPLATE INT,
   RESTRICT_COLLEGE     BOOL NOT NULL,
   RESTRICT_SUBJECT     BOOL NOT NULL,
   PROJECT_JUDGE_PSQ    INT,
   STATUS               TINYINT NOT NULL DEFAULT 1 COMMENT '1未发布
            2已发布
            3已完成
            4关闭',
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   REMARK               VARCHAR(200),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PROJECT_ADMIN                                         */
/*==============================================================*/
CREATE TABLE PROJECT_ADMIN
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   IDCARD               VARCHAR(20),
   MOBILE               VARCHAR(20) NOT NULL,
   EMAIL                VARCHAR(50) NOT NULL,
   PASSWORD             VARCHAR(20) NOT NULL,
   SEX                  TINYINT NOT NULL,
   NATIONALLY           SMALLINT,
   ORGANIZATION         INT NOT NULL,
   DEPARTMENT           VARCHAR(100),
   LEVEL                TINYINT NOT NULL COMMENT '1派出单位2承训单位',
   POLITICS             TINYINT,
   CREATEUSER           BOOL NOT NULL,
   RESTRICT_RIGHT       BOOL NOT NULL,
   PHONE                VARCHAR(20) NOT NULL,
   FAX                  VARCHAR(20),
   JOB_DUTY             VARCHAR(20),
   AREA                 INT NOT NULL,
   ADDRESS              VARCHAR(200),
   POSTCODE             VARCHAR(10),
   STATUS               TINYINT NOT NULL COMMENT '1正常2停用',
   REMARK               VARCHAR(200),
   CREATOR              INT,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (IDCARD),
   UNIQUE KEY AK_KEY_3 (MOBILE),
   UNIQUE KEY AK_KEY_4 (EMAIL)
);

/*==============================================================*/
/* Table: PROJECT_ADMIN_RIGHT                                   */
/*==============================================================*/
CREATE TABLE PROJECT_ADMIN_RIGHT
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   PROJECT_ADMIN        INT NOT NULL,
   PROJECT_TYPE         INT NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (PROJECT_ADMIN, PROJECT_TYPE)
);

/*==============================================================*/
/* Table: PROJECT_APPLY                                         */
/*==============================================================*/
CREATE TABLE PROJECT_APPLY
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   TYPE                 TINYINT NOT NULL COMMENT '1系统申报
            2管理员补录
            3学员自选',
   PROEJCT              INT NOT NULL,
   SUBJECT              SMALLINT NOT NULL,
   TRAINING_COLLEGE     INT NOT NULL,
   PROJECT_APPLY_DOCUMENT INT,
   STATUS               TINYINT NOT NULL COMMENT '1未审核
            2已审核',
   TRAINING_STARTTIME   DATETIME,
   TRAINING_ENDTIME     DATETIME,
   TRAINING_CLASSHOUR   INT NOT NULL,
   TRAINING_ADDRESS     VARCHAR(200),
   CONTACTS             VARCHAR(100),
   PHONE                VARCHAR(20),
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   APPROVER             INT,
   APPROVETIME          TIMESTAMP,
   APPROVE_NUMBER       SMALLINT,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (PROEJCT, SUBJECT, TRAINING_COLLEGE)
);

/*==============================================================*/
/* Table: PROJECT_COLLEGE_RANGE                                 */
/*==============================================================*/
CREATE TABLE PROJECT_COLLEGE_RANGE
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   PROJECT              INT NOT NULL,
   TRAINING_COLLEGE     INT NOT NULL,
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (PROJECT, TRAINING_COLLEGE)
);

/*==============================================================*/
/* Table: PROJECT_EXPERT                                        */
/*==============================================================*/
CREATE TABLE PROJECT_EXPERT
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   IDCARD               VARCHAR(20) NOT NULL,
   MOBILE               VARCHAR(20) NOT NULL,
   EMAIL                VARCHAR(50) NOT NULL,
   PASSWORD             VARCHAR(20) NOT NULL,
   SEX                  TINYINT NOT NULL,
   NATIONALLY           SMALLINT NOT NULL,
   ORGANIZATION         VARCHAR(50) NOT NULL,
   DEPARTMENT           VARCHAR(100),
   PHONE                VARCHAR(20) NOT NULL,
   FAX                  VARCHAR(20),
   JOB_TITLE            VARCHAR(20),
   JOB_DUTY             VARCHAR(20),
   POLITICS             TINYINT,
   EDUCTION_BACKGROUND  TINYINT,
   AREA                 INT,
   ADDRESS              VARCHAR(200),
   POSTCODE             VARCHAR(10),
   STATUS               TINYINT NOT NULL COMMENT '1正常2停用',
   RESEARCH             VARCHAR(200),
   TEACH                VARCHAR(200),
   RESUME               VARCHAR(1000),
   ACHIEVEMENT          VARCHAR(1000),
   EXPERIENCE           VARCHAR(1000),
   REMARK               VARCHAR(1000),
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (IDCARD),
   UNIQUE KEY AK_KEY_3 (MOBILE),
   UNIQUE KEY AK_KEY_4 (EMAIL)
);

/*==============================================================*/
/* Table: PROJECT_LEVEL                                         */
/*==============================================================*/
CREATE TABLE PROJECT_LEVEL
(
   ID                   TINYINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(80) NOT NULL,
   LEVEL                TINYINT NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PROJECT_SUBJECT_RANGE                                 */
/*==============================================================*/
CREATE TABLE PROJECT_SUBJECT_RANGE
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   PROJECT              INT NOT NULL,
   SUBJECT              SMALLINT NOT NULL,
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (PROJECT, SUBJECT)
);

/*==============================================================*/
/* Table: PROJECT_TYPE                                          */
/*==============================================================*/
CREATE TABLE PROJECT_TYPE
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(200) NOT NULL,
   SHORTNAME            VARCHAR(200) NOT NULL,
   PROJECT_LEVEL        TINYINT NOT NULL,
   LEVEL                TINYINT NOT NULL,
   PID                  INT,
   AREA                 INT NOT NULL,
   STATUS               TINYINT NOT NULL COMMENT '1正常2停用',
   CREATOR              INT,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PSQ                                                   */
/*==============================================================*/
CREATE TABLE PSQ
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   TITLE                VARCHAR(1000) NOT NULL,
   ABOUT                VARCHAR(2000) NOT NULL,
   TYPE                 CHAR(1) NOT NULL COMMENT '1项目招标专家评分 
            2教评学
            3学评教',
   STATUS               CHAR(1) NOT NULL COMMENT '1草稿2发布3停止',
   CLOSING              VARCHAR(2000) NOT NULL,
   ANSWERNUM            INT NOT NULL DEFAULT 0,
   GOTOURL              VARCHAR(400),
   THEWAY               CHAR(1) NOT NULL DEFAULT '1' COMMENT '1在一页中呈现整张卷子答题
            2在一页只答一道题',
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
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
   LOGINKEY             VARCHAR(30) NOT NULL,
   QUESTION             INT NOT NULL,
   ANSWER               INT NOT NULL,
   CONTENT              VARCHAR(2000) NOT NULL COMMENT '选择题',
   SCORE                INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (LOGINKEY, QUESTION, ANSWER)
);

/*==============================================================*/
/* Table: SUBJECT                                               */
/*==============================================================*/
CREATE TABLE SUBJECT
(
   ID                   SMALLINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: TEACHER                                               */
/*==============================================================*/
CREATE TABLE TEACHER
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   IDCARD               VARCHAR(20) NOT NULL,
   MOBILE               VARCHAR(20) NOT NULL,
   PASSWORD             VARCHAR(20) NOT NULL,
   EMAIL                VARCHAR(50) NOT NULL,
   SEX                  TINYINT NOT NULL COMMENT '1男2女',
   BIRTHDAY             DATE NOT NULL,
   ETHNIC               SMALLINT NOT NULL,
   JOB_TITLE            TINYINT NOT NULL,
   AREA                 INT NOT NULL,
   ORGANIZATION         INT NOT NULL,
   TEACHING_AGE         DATE NOT NULL,
   MULTI_LANGUAGE       BOOLEAN NOT NULL,
   POLITICS             TINYINT,
   JOB_DUTY             TINYINT NOT NULL,
   CHINESE_LANGUAGE_LEVEL TINYINT NOT NULL,
   EDUCTION_BACKGROUND  TINYINT NOT NULL,
   HEAD_PIC_PATH        VARCHAR(255),
   REMARK1              VARCHAR(200),
   REMARK2              VARCHAR(200),
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   STATUS               TINYINT NOT NULL DEFAULT 1 COMMENT '1正常2停用3转出',
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (IDCARD),
   UNIQUE KEY AK_KEY_3 (MOBILE),
   UNIQUE KEY AK_KEY_4 (EMAIL)
);

/*==============================================================*/
/* Table: TEACHER_TRAINING_RECORDS                              */
/*==============================================================*/
CREATE TABLE TEACHER_TRAINING_RECORDS
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   PROJECT              INT NOT NULL,
   SUBJECT              SMALLINT NOT NULL,
   TRAINING_COLLEGE     INT NOT NULL,
   TEACHER              INT NOT NULL,
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   ORGANIZATION         INT NOT NULL,
   CHECK_STATUS         TINYINT NOT NULL DEFAULT 1 COMMENT '0未通过审核
            1教育厅级审核
            2地/市/州/厅直属学校级审核
            3县/区/市/局直属学校级审核
            4派出学校级审核
            ',
   UUID                 VARCHAR(50) NOT NULL,
   IS_PREPARE           BOOLEAN NOT NULL COMMENT '超过学员限额的报送被认定为是预备学员，修改分配任务的限额会刷新此状态，按先后报送时间认定',
   FINAL_STATUS         TINYINT NOT NULL COMMENT '1未最终审核
            2已最终审核',
   TRAINING_REGISTERTIME DATETIME,
   TRAINING_CLASSHOUR   INT COMMENT '学时',
   TRAINING_STATUS      TINYINT NOT NULL DEFAULT 1 COMMENT '0学员异动
            1未报到
            2已报到培训中
            3培训合格(已结业)
            4培训不合格',
   TRAINING_REASON      VARCHAR(200),
   TRAINING_SCORE       INT,
   CERTIFICATE          VARCHAR(50),
   REGISTRANT           INT,
   REGISTTIME           TIMESTAMP,
   REMARK1              VARCHAR(200),
   REMARK2              VARCHAR(200),
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_3 (PROJECT, SUBJECT, TRAINING_COLLEGE, TEACHER),
   UNIQUE KEY AK_KEY_5 (UUID)
);

/*==============================================================*/
/* Table: TEACHEXPERT                                           */
/*==============================================================*/
CREATE TABLE TEACHEXPERT
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(30) NOT NULL,
   IDCARD               VARCHAR(20) NOT NULL,
   TRAINING_COLLEGE     INT NOT NULL,
   NATIONALLY           SMALLINT NOT NULL,
   AREA                 INT NOT NULL,
   EDUCTION             TINYINT NOT NULL,
   JOB_DUTY             VARCHAR(20),
   JOB_TITLE            VARCHAR(20),
   EDUCATIONAGE         TINYINT NOT NULL,
   MOBILE               VARCHAR(20) NOT NULL,
   PHONE                VARCHAR(20),
   FAX                  VARCHAR(20),
   ADDRESS              VARCHAR(200),
   POLITICS             VARCHAR(20) NOT NULL,
   EMAIL                VARCHAR(50) NOT NULL,
   RESEARCH             VARCHAR(200),
   TEACH                VARCHAR(200),
   REMARK               VARCHAR(200),
   RESUME               VARCHAR(1000),
   ACHIEVEMENT          VARCHAR(1000),
   EXPERIENCE           VARCHAR(1000),
   OTHER                VARCHAR(1000),
   CREATOR              INT NOT NULL,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (IDCARD),
   UNIQUE KEY AK_KEY_3 (MOBILE)
);

/*==============================================================*/
/* Table: TEACHING_GRADE                                        */
/*==============================================================*/
CREATE TABLE TEACHING_GRADE
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   TEACHER              INT NOT NULL,
   GRADE                TINYINT NOT NULL,
   ISPRIME              BOOLEAN NOT NULL COMMENT '1主要2其他',
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (TEACHER, GRADE)
);

/*==============================================================*/
/* Table: TEACHING_LANGUAGE                                     */
/*==============================================================*/
CREATE TABLE TEACHING_LANGUAGE
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   TEACHER              INT NOT NULL,
   LANGUAGE             SMALLINT NOT NULL,
   ISPRIME              BOOLEAN NOT NULL COMMENT '1主要2其他',
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (TEACHER, LANGUAGE)
);

/*==============================================================*/
/* Table: TEACHING_SUBJECT                                      */
/*==============================================================*/
CREATE TABLE TEACHING_SUBJECT
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   TEACHER              INT NOT NULL,
   SUBJECT              SMALLINT NOT NULL,
   ISPRIME              BOOLEAN NOT NULL COMMENT '1主要2其他',
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (TEACHER, SUBJECT)
);

/*==============================================================*/
/* Table: TRAINING_ADMIN                                        */
/*==============================================================*/
CREATE TABLE TRAINING_ADMIN
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50) NOT NULL,
   IDCARD               VARCHAR(20) NOT NULL,
   MOBILE               VARCHAR(20) NOT NULL,
   EMAIL                VARCHAR(50) NOT NULL,
   PASSWORD             VARCHAR(20) NOT NULL,
   SEX                  TINYINT NOT NULL,
   NATIONALLY           SMALLINT NOT NULL,
   ORGANIZATION         INT NOT NULL,
   DEPARTMENT           VARCHAR(100),
   CREATEUSER           BOOL NOT NULL,
   RESTRICT_RIGHT       BOOL NOT NULL,
   PHONE                VARCHAR(20) NOT NULL,
   FAX                  VARCHAR(20),
   JOB_DUTY             VARCHAR(20),
   POLITICS             TINYINT,
   AREA                 INT,
   ADDRESS              VARCHAR(200),
   POSTCODE             VARCHAR(10),
   STATUS               TINYINT NOT NULL COMMENT '1正常2停用',
   REMARK               VARCHAR(200),
   CREATOR              INT,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY AK_KEY_2 (IDCARD),
   UNIQUE KEY AK_KEY_3 (MOBILE),
   UNIQUE KEY AK_KEY_4 (EMAIL)
);

/*==============================================================*/
/* Table: TRAINING_COLLEGE                                      */
/*==============================================================*/
CREATE TABLE TRAINING_COLLEGE
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(100) NOT NULL,
   SHORT_NAME           VARCHAR(100),
   PROJECT_LEVEL        TINYINT NOT NULL,
   AREA                 INT NOT NULL,
   ADRESS               VARCHAR(200),
   CONTACTS             VARCHAR(50),
   PHONE                VARCHAR(20),
   FAX                  VARCHAR(20),
   STATUS               TINYINT NOT NULL COMMENT '1正常2停用',
   CREATOR              INT,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: TRAINING_SUBJECT                                      */
/*==============================================================*/
CREATE TABLE TRAINING_SUBJECT
(
   ID                   SMALLINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(30) NOT NULL,
   INTRODUCE            VARCHAR(200),
   STATUS               TINYINT NOT NULL DEFAULT 0 COMMENT '1正常2停用',
   CREATOR              INT,
   CREATTIME            TIMESTAMP NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: UNIVERSITY_INFO                                       */
/*==============================================================*/
CREATE TABLE UNIVERSITY_INFO
(
   ID                   INT NOT NULL,
   NAME                 VARCHAR(100) NOT NULL,
   CODE                 TINYINT NOT NULL,
   TYPE                 TINYINT NOT NULL,
   TYPE_NAME            VARCHAR(100) NOT NULL,
   XZLB                 TINYINT NOT NULL,
   XZLB_NAME            VARCHAR(20) NOT NULL,
   PINYIN               VARCHAR(200) NOT NULL,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FUN_CATEGORY                                          */
/*==============================================================*/
CREATE TABLE FUN_CATEGORY
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(50),
   PID                  INT,
   PATH                 VARCHAR(200),
   LEVEL                INT(4),
   CODE                 INT(2),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ORGA_CATE_MAP                                         */
/*==============================================================*/
CREATE TABLE ORGA_CATE_MAP
(
   ID                   INT NOT NULL AUTO_INCREMENT,
   CATEGORY             INT,
   ORGANIZATION         TINYINT,
   ROLEID               TINYINT,
   PRIMARY KEY (ID)
);

ALTER TABLE ANSWER ADD CONSTRAINT FK_REFERENCE_45 FOREIGN KEY (QUESTION)
      REFERENCES QUESTION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ANSWER ADD CONSTRAINT FK_REFERENCE_46 FOREIGN KEY (PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE AREA ADD CONSTRAINT FK_REFERENCE_1 FOREIGN KEY (PID)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ASSIGN_TEACHER_CHECK ADD CONSTRAINT FK_REFERENCE_92 FOREIGN KEY (TEACHER_TRAINING_RECORDS)
      REFERENCES TEACHER_TRAINING_RECORDS (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ASSIGN_TEACHER_TASK ADD CONSTRAINT FK_REFERENCE_76 FOREIGN KEY (SUBJECT)
      REFERENCES TRAINING_SUBJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ASSIGN_TEACHER_TASK ADD CONSTRAINT FK_REFERENCE_85 FOREIGN KEY (PID)
      REFERENCES ASSIGN_TEACHER_TASK (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ASSIGN_TEACHER_TASK ADD CONSTRAINT FK_REFERENCE_86 FOREIGN KEY (G_ORGANIZATION)
      REFERENCES ORGANIZATION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ASSIGN_TEACHER_TASK ADD CONSTRAINT FK_REFERENCE_87 FOREIGN KEY (PROJECT)
      REFERENCES PROJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ASSIGN_TEACHER_TASK ADD CONSTRAINT FK_REFERENCE_88 FOREIGN KEY (P_ORGANIZATION)
      REFERENCES ORGANIZATION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ASSIGN_TEACHER_TASK ADD CONSTRAINT FK_REFERENCE_95 FOREIGN KEY (TRAINING_COLLEGE)
      REFERENCES TRAINING_COLLEGE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE LOGINKEY ADD CONSTRAINT FK_REFERENCE_41 FOREIGN KEY (PROJECT)
      REFERENCES PROJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ORGANIZATION ADD CONSTRAINT FK_REFERENCE_31 FOREIGN KEY (LEVEL)
      REFERENCES ORGANIZATION_LEVEL (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ORGANIZATION ADD CONSTRAINT FK_REFERENCE_33 FOREIGN KEY (AREA)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ORGANIZATION ADD CONSTRAINT FK_REFERENCE_8 FOREIGN KEY (PID)
      REFERENCES ORGANIZATION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ORGANIZATION ADD CONSTRAINT FK_REFERENCE_84 FOREIGN KEY (GRADE)
      REFERENCES GRADE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT ADD CONSTRAINT FK_REFERENCE_70 FOREIGN KEY (TYPE)
      REFERENCES PROJECT_TYPE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT ADD CONSTRAINT FK_REFERENCE_75 FOREIGN KEY (PROJECT_JUDGE_PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT ADD CONSTRAINT FK_REFERENCE_77 FOREIGN KEY (PROJECT_APPLY_TEMPLATE)
      REFERENCES DOCUMENT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_ADMIN ADD CONSTRAINT FK_REFERENCE_12 FOREIGN KEY (NATIONALLY)
      REFERENCES ETHNIC (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_ADMIN ADD CONSTRAINT FK_REFERENCE_13 FOREIGN KEY (AREA)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_ADMIN ADD CONSTRAINT FK_REFERENCE_58 FOREIGN KEY (ORGANIZATION)
      REFERENCES ORGANIZATION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_ADMIN ADD CONSTRAINT FK_REFERENCE_66 FOREIGN KEY (POLITICS)
      REFERENCES POLITICS (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_ADMIN_RIGHT ADD CONSTRAINT FK_REFERENCE_82 FOREIGN KEY (PROJECT_ADMIN)
      REFERENCES PROJECT_ADMIN (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_ADMIN_RIGHT ADD CONSTRAINT FK_REFERENCE_83 FOREIGN KEY (PROJECT_TYPE)
      REFERENCES PROJECT_TYPE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_APPLY ADD CONSTRAINT FK_REFERENCE_78 FOREIGN KEY (PROEJCT)
      REFERENCES PROJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_APPLY ADD CONSTRAINT FK_REFERENCE_79 FOREIGN KEY (TRAINING_COLLEGE)
      REFERENCES TRAINING_COLLEGE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_APPLY ADD CONSTRAINT FK_REFERENCE_80 FOREIGN KEY (SUBJECT)
      REFERENCES TRAINING_SUBJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_APPLY ADD CONSTRAINT FK_REFERENCE_81 FOREIGN KEY (PROJECT_APPLY_DOCUMENT)
      REFERENCES DOCUMENT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_COLLEGE_RANGE ADD CONSTRAINT FK_REFERENCE_71 FOREIGN KEY (PROJECT)
      REFERENCES PROJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_COLLEGE_RANGE ADD CONSTRAINT FK_REFERENCE_72 FOREIGN KEY (TRAINING_COLLEGE)
      REFERENCES TRAINING_COLLEGE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_EXPERT ADD CONSTRAINT FK_REFERENCE_62 FOREIGN KEY (AREA)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_EXPERT ADD CONSTRAINT FK_REFERENCE_63 FOREIGN KEY (EDUCTION_BACKGROUND)
      REFERENCES EDUCTION_BACKGROUND (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_EXPERT ADD CONSTRAINT FK_REFERENCE_64 FOREIGN KEY (NATIONALLY)
      REFERENCES ETHNIC (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_EXPERT ADD CONSTRAINT FK_REFERENCE_68 FOREIGN KEY (POLITICS)
      REFERENCES POLITICS (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_SUBJECT_RANGE ADD CONSTRAINT FK_REFERENCE_73 FOREIGN KEY (PROJECT)
      REFERENCES PROJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_SUBJECT_RANGE ADD CONSTRAINT FK_REFERENCE_74 FOREIGN KEY (SUBJECT)
      REFERENCES TRAINING_SUBJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_TYPE ADD CONSTRAINT FK_REFERENCE_28 FOREIGN KEY (PROJECT_LEVEL)
      REFERENCES PROJECT_LEVEL (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_TYPE ADD CONSTRAINT FK_REFERENCE_29 FOREIGN KEY (PID)
      REFERENCES PROJECT_TYPE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT_TYPE ADD CONSTRAINT FK_REFERENCE_30 FOREIGN KEY (AREA)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE QUESTION ADD CONSTRAINT FK_REFERENCE_44 FOREIGN KEY (PSQ)
      REFERENCES PSQ (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE RESULT ADD CONSTRAINT FK_REFERENCE_47 FOREIGN KEY (LOGINKEY)
      REFERENCES LOGINKEY (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE RESULT ADD CONSTRAINT FK_REFERENCE_48 FOREIGN KEY (ANSWER)
      REFERENCES ANSWER (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE RESULT ADD CONSTRAINT FK_REFERENCE_49 FOREIGN KEY (QUESTION)
      REFERENCES QUESTION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER ADD CONSTRAINT FK_REFERENCE_3 FOREIGN KEY (EDUCTION_BACKGROUND)
      REFERENCES EDUCTION_BACKGROUND (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER ADD CONSTRAINT FK_REFERENCE_37 FOREIGN KEY (AREA)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER ADD CONSTRAINT FK_REFERENCE_4 FOREIGN KEY (ETHNIC)
      REFERENCES ETHNIC (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER ADD CONSTRAINT FK_REFERENCE_43 FOREIGN KEY (ORGANIZATION)
      REFERENCES ORGANIZATION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER ADD CONSTRAINT FK_REFERENCE_5 FOREIGN KEY (JOB_TITLE)
      REFERENCES JOB_TITLE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER ADD CONSTRAINT FK_REFERENCE_50 FOREIGN KEY (JOB_DUTY)
      REFERENCES JOB_DUTY (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER ADD CONSTRAINT FK_REFERENCE_51 FOREIGN KEY (CHINESE_LANGUAGE_LEVEL)
      REFERENCES CHINESE_LANGUAGE_LEVEL (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER ADD CONSTRAINT FK_REFERENCE_65 FOREIGN KEY (POLITICS)
      REFERENCES POLITICS (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER_TRAINING_RECORDS ADD CONSTRAINT FK_REFERENCE_23 FOREIGN KEY (TEACHER)
      REFERENCES TEACHER (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER_TRAINING_RECORDS ADD CONSTRAINT FK_REFERENCE_32 FOREIGN KEY (PROJECT)
      REFERENCES PROJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER_TRAINING_RECORDS ADD CONSTRAINT FK_REFERENCE_89 FOREIGN KEY (TRAINING_COLLEGE)
      REFERENCES TRAINING_COLLEGE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER_TRAINING_RECORDS ADD CONSTRAINT FK_REFERENCE_90 FOREIGN KEY (SUBJECT)
      REFERENCES TRAINING_SUBJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHER_TRAINING_RECORDS ADD CONSTRAINT FK_REFERENCE_93 FOREIGN KEY (ORGANIZATION)
      REFERENCES ORGANIZATION (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHEXPERT ADD CONSTRAINT FK_REFERENCE_36 FOREIGN KEY (EDUCTION)
      REFERENCES EDUCTION_BACKGROUND (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHEXPERT ADD CONSTRAINT FK_REFERENCE_38 FOREIGN KEY (NATIONALLY)
      REFERENCES ETHNIC (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHEXPERT ADD CONSTRAINT FK_REFERENCE_40 FOREIGN KEY (AREA)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHEXPERT ADD CONSTRAINT FK_REFERENCE_42 FOREIGN KEY (TRAINING_COLLEGE)
      REFERENCES TRAINING_COLLEGE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHING_GRADE ADD CONSTRAINT FK_REFERENCE_56 FOREIGN KEY (TEACHER)
      REFERENCES TEACHER (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHING_GRADE ADD CONSTRAINT FK_REFERENCE_57 FOREIGN KEY (GRADE)
      REFERENCES GRADE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHING_LANGUAGE ADD CONSTRAINT FK_REFERENCE_52 FOREIGN KEY (LANGUAGE)
      REFERENCES LANGUAGE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHING_LANGUAGE ADD CONSTRAINT FK_REFERENCE_53 FOREIGN KEY (TEACHER)
      REFERENCES TEACHER (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHING_SUBJECT ADD CONSTRAINT FK_REFERENCE_54 FOREIGN KEY (TEACHER)
      REFERENCES TEACHER (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEACHING_SUBJECT ADD CONSTRAINT FK_REFERENCE_55 FOREIGN KEY (SUBJECT)
      REFERENCES SUBJECT (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TRAINING_ADMIN ADD CONSTRAINT FK_REFERENCE_59 FOREIGN KEY (AREA)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TRAINING_ADMIN ADD CONSTRAINT FK_REFERENCE_60 FOREIGN KEY (NATIONALLY)
      REFERENCES ETHNIC (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TRAINING_ADMIN ADD CONSTRAINT FK_REFERENCE_67 FOREIGN KEY (POLITICS)
      REFERENCES POLITICS (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TRAINING_ADMIN ADD CONSTRAINT FK_REFERENCE_69 FOREIGN KEY (ORGANIZATION)
      REFERENCES TRAINING_COLLEGE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TRAINING_COLLEGE ADD CONSTRAINT FK_REFERENCE_34 FOREIGN KEY (PROJECT_LEVEL)
      REFERENCES PROJECT_LEVEL (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TRAINING_COLLEGE ADD CONSTRAINT FK_REFERENCE_39 FOREIGN KEY (AREA)
      REFERENCES AREA (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE FUN_CATEGORY ADD CONSTRAINT FK_REFERENCE_91 FOREIGN KEY (PID)
      REFERENCES FUN_CATEGORY (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ORGA_CATE_MAP ADD CONSTRAINT FK_FK_CATE FOREIGN KEY (CATEGORY)
      REFERENCES FUN_CATEGORY (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ORGA_CATE_MAP ADD CONSTRAINT FK_FK_ORG FOREIGN KEY (ORGANIZATION)
      REFERENCES ORGANIZATION_LEVEL (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;


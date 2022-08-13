/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2010-10-13 20:35:40                          */
/*==============================================================*/


alter table TCHR_TRAINING1.PE_BRIEF_REPORT
   drop constraint FK_BRIEF_REPORT_APPLYNO_1;

alter table TCHR_TRAINING1.PE_BULLETIN
   drop constraint FK_PE_BULLETIN_FLAGISTOP;

alter table TCHR_TRAINING1.PE_BULLETIN
   drop constraint FK_PE_BULLETIN_FLAGISVALID;

alter table TCHR_TRAINING1.PE_BULLETIN
   drop constraint FK_PE_BULLETIN_PEMANAGER;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   drop constraint FK_COURSE_PLAN_R_APP04;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   drop constraint FK_COURSE_PLAN_R_ENU05;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   drop constraint FK__COURSE_PLAN_R_ENU06;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   drop constraint FK__COURSE_PLAN_R_EXP01;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   drop constraint FK__COURSE_PLAN_R_PRO03;

alter table TCHR_TRAINING1.PE_EXPERT_SEARCH_HISTORY
   drop constraint FK_EXPERT_ID;

alter table TCHR_TRAINING1.PE_EXPERT_SEARCH_HISTORY
   drop constraint FK_SEARCHER_ID;

alter table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET
   drop constraint FK_FEE_ACTUAL_R_DETAIL01;

alter table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET
   drop constraint FK_FEE_ACTUAL_R_PRO_APP01;

alter table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET
   drop constraint FK_FEE_ACTUAL_R_UNIT01;

alter table TCHR_TRAINING1.PE_FEE_BUDGET
   drop constraint FK_PE_BUDGET_R_FEE_DETAIL;

alter table TCHR_TRAINING1.PE_FEE_BUDGET
   drop constraint FK_PE_FEE_R_PE_PRO_APP03;

alter table TCHR_TRAINING1.PE_FEE_BUDGET
   drop constraint FK_PE_FEE_R_PE_UNIT02;

alter table TCHR_TRAINING1.PE_JOB
   drop constraint FK_PE_JOB_PRI;

alter table TCHR_TRAINING1.PE_MANAGER
   drop constraint FK_PE_MANAG_RE2;

alter table TCHR_TRAINING1.PE_MANAGER
   drop constraint FK_PE_MANAG_RE3;

alter table TCHR_TRAINING1.PE_MANAGER
   drop constraint FK_PE_MANAG_RE4;

alter table TCHR_TRAINING1.PE_MANAGER
   drop constraint FK_PE_MANAG_REF;

alter table TCHR_TRAINING1.PE_MANAGER
   drop constraint FK_UNIT;

alter table TCHR_TRAINING1.PE_MANAGER
   drop constraint FLAG_ISVALID;

alter table TCHR_TRAINING1.PE_MEETING
   drop constraint FK_MEETING_REFER_UNIT;

alter table TCHR_TRAINING1.PE_MEETING
   drop constraint FK_PEMEETING_PEMANAGER_ID;

alter table TCHR_TRAINING1.PE_MEETING_RESOURCE
   drop constraint FK_MEETING_ID;

alter table TCHR_TRAINING1.PE_MEETING_RESOURCE
   drop constraint FK_MEETING_PERSON;

alter table TCHR_TRAINING1.PE_MEETING_RESOURCE
   drop constraint FK_MEETING_UNIT;

alter table TCHR_TRAINING1.PE_OTHER_MATERIAL
   drop constraint FK_OTHER_MATERIAL_R_APPLYNO_01;

alter table TCHR_TRAINING1.PE_OTHER_MATERIAL
   drop constraint FK_OTHER_MATERIAL_R_UNIT_01;

alter table TCHR_TRAINING1.PE_PRIORITY
   drop constraint FK_PE_PRIOR_REF;

alter table TCHR_TRAINING1.PE_PRI_CATEGORY
   drop constraint FK_PE_PRI_C_REF;

alter table TCHR_TRAINING1.PE_PRI_ROLE
   drop constraint FK_KEY1_PE_PRICATE1;

alter table TCHR_TRAINING1.PE_PRI_ROLE
   drop constraint FK_KEY1_PE_PRICATE2;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   drop constraint FK_PE_PROGR_R10;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   drop constraint FK_PE_PROGR_RE12;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   drop constraint FK_PE_PROGR_RE13;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   drop constraint FK_PE_PROGR_RE6;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   drop constraint FK_PE_PROGR_RE7;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   drop constraint FK_PE_PROGR_RE8;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   drop constraint FK_PE_PROGR_RE9;

alter table TCHR_TRAINING1.PE_PRO_APPLYNO
   drop constraint FK_PE_PROGR_RE2;

alter table TCHR_TRAINING1.PE_PRO_APPLYNO
   drop constraint FK_PE_PROGR_RE3;

alter table TCHR_TRAINING1.PE_PRO_APPLYNO
   drop constraint FK_PE_PROGR_RE4;

alter table TCHR_TRAINING1.PE_PRO_IMPLEMT
   drop constraint FK_PE_PROGRAM_R_AP_02;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_R15;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_R16;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RE3;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RE4;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RE5;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RE6;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RE7;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RE8;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RS9;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RT8;

alter table TCHR_TRAINING1.PE_TRAINEE
   drop constraint FK_PE_TRAIN_RT9;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   drop constraint FK_PE_TRAIN_R10;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   drop constraint FK_PE_TRAIN_R11;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   drop constraint FK_PE_TRAIN_R12;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   drop constraint FK_PE_TRAIN_R13;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   drop constraint FK_PE_TRAIN_R14;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   drop constraint FK_PE_TRAIN_REF;

alter table TCHR_TRAINING1.PE_UNIT
   drop constraint FK_PE_UNIT_REF2;

alter table TCHR_TRAINING1.PE_UNIT
   drop constraint FK_PE_UNIT_REFE;

alter table TCHR_TRAINING1.PE_UNIT
   drop constraint FK_PE_UNIT_REFERENCE_ENUM_CON;

alter table TCHR_TRAINING1.PE_VALUA_EXPERT
   drop constraint FK_PE_VALUA_RE2;

alter table TCHR_TRAINING1.PE_VALUA_EXPERT
   drop constraint FK_PE_VALUA_RE3;

alter table TCHR_TRAINING1.PE_VALUA_EXPERT
   drop constraint FK_PE_VALUA_REF;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   drop constraint FK_FLAG_CAN_SUGGEST;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   drop constraint FK_FLAG_ISVALID;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   drop constraint FK_FLAG_LIMIT_DIFFIP;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   drop constraint FK_FLAG_LIMIT_DIFFSESSION;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   drop constraint FK_FLAG_TYPE;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   drop constraint FK_FLAG_VIEW_SUGGEST;

alter table TCHR_TRAINING1.PR_JOB_UNIT
   drop constraint FK_PR_JOB_U_PE_UNITT;

alter table TCHR_TRAINING1.PR_JOB_UNIT
   drop constraint FK_PR_JOB_U_REF;

alter table TCHR_TRAINING1.PR_JOB_UNIT
   drop constraint FK_PR_JOB_U_REFERENCE_ENUM_CO2;

alter table TCHR_TRAINING1.PR_JOB_UNIT
   drop constraint FK_PR_JOB_U_REFERENCE_ENUM_CON;

alter table TCHR_TRAINING1.PR_MEET_PERSON
   drop constraint FK_PR_MEETI_RE2;

alter table TCHR_TRAINING1.PR_MEET_PERSON
   drop constraint FK_PR_MEETI_REF;

alter table TCHR_TRAINING1.PR_PRI_ROLE
   drop constraint FK_PR_PRI_R_RE2;

alter table TCHR_TRAINING1.PR_PRI_ROLE
   drop constraint FK_PR_PRI_R_REF;

alter table TCHR_TRAINING1.PR_PROGRAM_UNIT
   drop constraint FK_PR_PROGR_RE2;

alter table TCHR_TRAINING1.PR_PROGRAM_UNIT
   drop constraint FK_PR_PROGR_REF;

alter table TCHR_TRAINING1.PR_PRO_EXPERT
   drop constraint FK_PR_PROGR_RE3;

alter table TCHR_TRAINING1.PR_PRO_EXPERT
   drop constraint FK_PR_PROGR_RE4;

alter table TCHR_TRAINING1.PR_PRO_SUMMARY
   drop constraint FK_PRO_SUMMARY_R_APPLYNO_3;

alter table TCHR_TRAINING1.PR_VOTE_ANSWER
   drop constraint FK_VOTE_QUESTION;

alter table TCHR_TRAINING1.PR_VOTE_QUESTION
   drop constraint FK_PE_VOTE_PAPER;

alter table TCHR_TRAINING1.PR_VOTE_QUESTION
   drop constraint FK_PR_VOTE_ENUMCONST;

alter table TCHR_TRAINING1.PR_VOTE_QUESTION
   drop constraint FK_PR_VOTE_ENUMCONST2;

alter table TCHR_TRAINING1.PR_VOTE_RECORD
   drop constraint FK_PR_VOTE_PAPER;

alter table TCHR_TRAINING1.PR_VOTE_SUGGEST
   drop constraint FK_PR_VOTE_SUGGEST_FLAG_CHECK;

alter table TCHR_TRAINING1.PR_VOTE_SUGGEST
   drop constraint FK_PR_VOTE__REFERENCE_PE_VOTE2;

alter table TCHR_TRAINING1.SSO_USER
   drop constraint FK_SSO_USER_REF;

alter table TCHR_TRAINING1.SSO_USER
   drop constraint FK_SSO_USER_REF1;

alter table TCHR_TRAINING1.SSO_USER
   drop constraint FK_SSO_USER_REF2;

drop table TCHR_TRAINING1.ENUM_CONST cascade constraints;

drop table TCHR_TRAINING1.PE_BRIEF_REPORT cascade constraints;

drop table TCHR_TRAINING1.PE_BULLETIN cascade constraints;

drop table TCHR_TRAINING1.PE_COURSE_PLAN cascade constraints;

drop table TCHR_TRAINING1.PE_EXPERT_SEARCH_HISTORY cascade constraints;

drop table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET cascade constraints;

drop table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL cascade constraints;

drop table TCHR_TRAINING1.PE_FEE_BUDGET cascade constraints;

drop table TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL cascade constraints;

drop table TCHR_TRAINING1.PE_JOB cascade constraints;

drop table TCHR_TRAINING1.PE_MANAGER cascade constraints;

drop table TCHR_TRAINING1.PE_MEETING cascade constraints;

drop table TCHR_TRAINING1.PE_MEETING_RESOURCE cascade constraints;

drop table TCHR_TRAINING1.PE_NOTE_HISTORY cascade constraints;

drop table TCHR_TRAINING1.PE_OTHER_MATERIAL cascade constraints;

drop table TCHR_TRAINING1.PE_PRIORITY cascade constraints;

drop table TCHR_TRAINING1.PE_PRI_CATEGORY cascade constraints;

drop table TCHR_TRAINING1.PE_PRI_ROLE cascade constraints;

drop table TCHR_TRAINING1.PE_PROVINCE cascade constraints;

drop table TCHR_TRAINING1.PE_PRO_APPLY cascade constraints;

drop table TCHR_TRAINING1.PE_PRO_APPLYNO cascade constraints;

drop table TCHR_TRAINING1.PE_PRO_IMPLEMT cascade constraints;

drop table TCHR_TRAINING1.PE_SUBJECT cascade constraints;

drop table TCHR_TRAINING1.PE_TRAINEE cascade constraints;

drop table TCHR_TRAINING1.PE_TRAIN_EXPERT cascade constraints;

drop table TCHR_TRAINING1.PE_UNIT cascade constraints;

drop table TCHR_TRAINING1.PE_VALUA_EXPERT cascade constraints;

drop table TCHR_TRAINING1.PE_VOTE_PAPER cascade constraints;

drop table TCHR_TRAINING1.PE_WORKPLACE cascade constraints;

drop table TCHR_TRAINING1.PR_JOB_UNIT cascade constraints;

drop table TCHR_TRAINING1.PR_MEET_PERSON cascade constraints;

drop table TCHR_TRAINING1.PR_PRI_ROLE cascade constraints;

drop table TCHR_TRAINING1.PR_PROGRAM_UNIT cascade constraints;

drop table TCHR_TRAINING1.PR_PRO_EXPERT cascade constraints;

drop table TCHR_TRAINING1.PR_PRO_SUMMARY cascade constraints;

drop table TCHR_TRAINING1.PR_VOTE_ANSWER cascade constraints;

drop table TCHR_TRAINING1.PR_VOTE_QUESTION cascade constraints;

drop table TCHR_TRAINING1.PR_VOTE_RECORD cascade constraints;

drop table TCHR_TRAINING1.PR_VOTE_SUGGEST cascade constraints;

drop table TCHR_TRAINING1.SSO_USER cascade constraints;

drop table TCHR_TRAINING1.SYSTEM_VARIABLES cascade constraints;

drop table TCHR_TRAINING1.WHATYUSER_LOG4J cascade constraints;

drop user TCHR_TRAINING1;

/*==============================================================*/
/* User: TCHR_TRAINING1                                         */
/*==============================================================*/
create user TCHR_TRAINING1 identified by '';

/*==============================================================*/
/* Table: ENUM_CONST                                            */
/*==============================================================*/
create table TCHR_TRAINING1.ENUM_CONST  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50),
   NAMESPACE            VARCHAR2(50),
   IS_DEFAULT           CHAR                           default '0',
   CREATE_DATE          DATE,
   NOTE                 VARCHAR2(100),
   constraint PK_ENUM_CONST primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

/*==============================================================*/
/* Table: PE_BRIEF_REPORT                                       */
/*==============================================================*/
create table TCHR_TRAINING1.PE_BRIEF_REPORT  (
   ID                   VARCHAR2(50)                    not null,
   REPORT_NAME          VARCHAR2(100),
   REPORT_FILE          VARCHAR2(100),
   FK_PRO_APPLY         VARCHAR2(50),
   UPLOAD_DATE          DATE,
   constraint PK_BRIEF_REPORT_1 primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_BRIEF_REPORT is
'简报';

comment on column TCHR_TRAINING1.PE_BRIEF_REPORT.REPORT_NAME is
'简报名称';

comment on column TCHR_TRAINING1.PE_BRIEF_REPORT.REPORT_FILE is
'上传文件';

comment on column TCHR_TRAINING1.PE_BRIEF_REPORT.UPLOAD_DATE is
'上传日期';

/*==============================================================*/
/* Table: PE_BULLETIN                                           */
/*==============================================================*/
create table TCHR_TRAINING1.PE_BULLETIN  (
   ID                   VARCHAR2(50)                    not null,
   FK_MANAGER_ID        VARCHAR2(50),
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_ISTOP           VARCHAR2(50),
   TITLE                VARCHAR2(200)                   not null,
   PUBLISH_DATE         DATE,
   UPDATE_DATE          DATE,
   SCOPE_STRING         VARCHAR2(1000),
   NOTE                 CLOB,
   FK_SITEMANAGER_ID    VARCHAR2(50),
   FK_ENTERPRISEMANAGER_ID VARCHAR2(50),
   constraint PK_PE_BULLETIN_ID primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

/*==============================================================*/
/* Table: PE_COURSE_PLAN                                        */
/*==============================================================*/
create table TCHR_TRAINING1.PE_COURSE_PLAN  (
   ID                   VARCHAR2(50)                    not null,
   TRAINING_BEGIN_TIME  DATE,
   PRELECT_WAY          VARCHAR2(100),
   FK_TRAIN_EXPERT      VARCHAR2(50),
   EXPERT_NAME          VARCHAR2(50),
   WORK_PLACE           VARCHAR2(50),
   ZHICHENG             VARCHAR2(50),
   FK_PROVINCE          VARCHAR2(50),
   NOTE                 VARCHAR2(200),
   COMMENTS             VARCHAR2(100),
   FK_PRO_APPLY         VARCHAR2(50),
   FLAG_VALUATION       VARCHAR2(50),
   THEME                VARCHAR2(100),
   FLAG_BAK             VARCHAR2(50),
   TRAIN_PLACE          VARCHAR2(100),
   TRAINING_END_TIME    DATE,
   FIRSTVOTE            NUMBER                         default 0,
   SECONDVOTE           NUMBER                         default 0,
   THIRDVOTE            NUMBER                         default 0,
   FOUTHVOTE            NUMBER                         default 0,
   FIFTHVOTE            NUMBER                         default 0,
   constraint PK__COURSE_PLAN primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_COURSE_PLAN is
'开班计划';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.ID is
'id';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.TRAINING_BEGIN_TIME is
'培训开始时间';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.PRELECT_WAY is
'授课方式';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FK_TRAIN_EXPERT is
'专家';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.EXPERT_NAME is
'专家姓名 如果专家不在专家库中需要填写';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.WORK_PLACE is
'专家单位  如果专家不在专家库中需要填写';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.ZHICHENG is
'专家职称  如果专家不在专家库中需要填写';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FK_PROVINCE is
'专家省份  如果专家不在专家库中需要填写';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.NOTE is
'备注';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.COMMENTS is
'评语';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FK_PRO_APPLY is
'关联项目';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FLAG_VALUATION is
'评价';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.THEME is
'主题';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.TRAIN_PLACE is
'培训地点';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.TRAINING_END_TIME is
'培训结束时间';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FIRSTVOTE is
'被学生投票第一受欢迎的次数';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.SECONDVOTE is
'被学生投票第二受欢迎的次数';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.THIRDVOTE is
'被学生投票第三受欢迎的次数';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FOUTHVOTE is
'被学生投票第四受欢迎的次数';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FIFTHVOTE is
'被学生投票第五受欢迎的次数';

/*==============================================================*/
/* Table: PE_EXPERT_SEARCH_HISTORY                              */
/*==============================================================*/
create table TCHR_TRAINING1.PE_EXPERT_SEARCH_HISTORY  (
   ID                   VARCHAR2(50)                    not null,
   EXPERT_ID            VARCHAR2(50),
   SEACHER_ID           VARCHAR2(50),
   SEARCH_TIME          DATE,
   constraint PE_EXPERT_SEARCH_PK primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

/*==============================================================*/
/* Table: PE_FEE_ACTUAL_BUDGET                                  */
/*==============================================================*/
create table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET  (
   ID                   VARCHAR2(50)                    not null,
   FK_UNIT              VARCHAR2(50),
   FK_PRO_APPLYNO       VARCHAR2(50),
   TRAINING_DATE        DATE,
   TRAINING_SPACE       VARCHAR2(100),
   PAYEE_NAME           VARCHAR2(100),
   OPENING_BANK         VARCHAR2(50),
   ACCOUNT_NUMBER       VARCHAR2(50),
   CONTACT_INFO         VARCHAR2(50),
   FK_FEE_DETAIL        VARCHAR2(50)                    not null,
   PERSON_COUNT         VARCHAR2(100),
   UNIT_NAME            VARCHAR2(100),
   constraint PK_PE_FEE_ACTUAL_BUDGET primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET is
'决算表';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.FK_UNIT is
'承办单位';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.FK_PRO_APPLYNO is
'申报批次';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.TRAINING_DATE is
'培训时间';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.TRAINING_SPACE is
'培训地点';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.PAYEE_NAME is
'收款单位名称';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.OPENING_BANK is
'开户行';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.ACCOUNT_NUMBER is
'账号';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.CONTACT_INFO is
'联系人及电话';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.FK_FEE_DETAIL is
'决算详细';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.PERSON_COUNT is
'申报人数';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.UNIT_NAME is
'承办单位名称';

/*==============================================================*/
/* Table: PE_FEE_ACTUAL_BUDGET_DETAIL                           */
/*==============================================================*/
create table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL  (
   ID                   VARCHAR2(50)                    not null,
   FEE_SURVEY           NUMBER(9,2),
   FEE_RESEARCH         NUMBER(9,2),
   FEE_ARGUMENT         NUMBER(9,2),
   FEE_MEAL             NUMBER(9,2),
   FEE_ACCOMMODATION    NUMBER(9,2),
   FEE_TRAFFIC_STU      NUMBER(9,2),
   FEE_TEACH            NUMBER(9,2),
   FEE_TRAFFIC_EXPERT   NUMBER(9,2),
   FEE_MEAL_ACC_EXPERT  NUMBER(9,2),
   FEE_MATERIALS        NUMBER(9,2),
   FEE_COURSE           NUMBER(9,2),
   FEE_ELECTRON_COURSE  NUMBER(9,2),
   FEE_AREA_RENT        NUMBER(9,2),
   FEE_EQUIPMENT_RENT   NUMBER(9,2),
   FEE_APPRAISE         NUMBER(9,2),
   FEE_SUMMARY_APPRAISE NUMBER(9,2),
   FEE_LABOUR_SERVICE   NUMBER(9,2),
   FEE_PUBLICITY        NUMBER(9,2),
   FEE_PETTY            NUMBER(9,2),
   FEE_OTHER            NUMBER(9,2),
   NOTE_SURVEY          VARCHAR2(200),
   NOTE_RESEARCH        VARCHAR2(200),
   NOTE_ARGUMENT        VARCHAR2(200),
   NOTE_MEAL            VARCHAR2(200),
   NOTE_ACCOMMODATION   VARCHAR2(200),
   NOTE_TRAFFIC_STU     VARCHAR2(200),
   NOTE_TEACH           VARCHAR2(200),
   NOTE_TRAFFIC_EXPERT  VARCHAR2(200),
   NOTE_MEAL_ACC_EXPERT VARCHAR2(200),
   NOTE_MATERIALS       VARCHAR2(200),
   NOTE_TEXT_COURSE     VARCHAR2(200),
   NOTE_ELECTRON_COURSE VARCHAR2(200),
   NOTE_AREA_RENT       VARCHAR2(200),
   NOTE_EQUIPMENT_RENT  VARCHAR2(200),
   NOTE_APPRAISE        VARCHAR2(200),
   NOTE_SUMMARY_APPRAISE VARCHAR2(200),
   NOTE_LABOUR_SERVICE  VARCHAR2(200),
   NOTE_PUBLICITY       VARCHAR2(200),
   NOTE_PETTY           VARCHAR2(200),
   NOTE_OTHER           VARCHAR2(200),
   constraint PK_PE_FEE_ACTUAL_BUDGET_DETAIL primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL is
'决算表详细';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_SURVEY is
'前期调研费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_RESEARCH is
'方案研制费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ARGUMENT is
'项目论证费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MEAL is
'餐费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ACCOMMODATION is
'学员住宿费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TRAFFIC_STU is
'交通费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TEACH is
'授课费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TRAFFIC_EXPERT is
'专家交通费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MEAL_ACC_EXPERT is
'专家食宿费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MATERIALS is
'学员学习资料费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_COURSE is
'文本课程资源
开发费用';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ELECTRON_COURSE is
'电子课程资源';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_AREA_RENT is
'场地租用费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_EQUIPMENT_RENT is
'设备租用费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_APPRAISE is
'项目评估费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_SUMMARY_APPRAISE is
'项目总结评估费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_LABOUR_SERVICE is
'劳务费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_PUBLICITY is
'宣传费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_PETTY is
'公杂费';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_OTHER is
'其他';

/*==============================================================*/
/* Table: PE_FEE_BUDGET                                         */
/*==============================================================*/
create table TCHR_TRAINING1.PE_FEE_BUDGET  (
   ID                   VARCHAR2(50)                    not null,
   UNIT_NAME            VARCHAR2(50),
   FK_PRO_APPLYNO       VARCHAR2(50),
   TRAINING_DATE        DATE,
   TRAINING_SPACE       VARCHAR2(100),
   PAYEE_NAME           VARCHAR2(100),
   OPENING_BANK         VARCHAR2(50),
   ACCOUNT_NUMBER       VARCHAR2(50),
   CONTACT_INFO         VARCHAR2(50),
   FK_FEE_DETAIL        VARCHAR2(50),
   PERSON_COUNT         VARCHAR2(100),
   FK_UNIT              VARCHAR2(50),
   constraint PK_PE_FEE_BUDGET primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_FEE_BUDGET is
'预算表';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.UNIT_NAME is
'承办单位名称';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.FK_PRO_APPLYNO is
'申报批次';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.TRAINING_DATE is
'培训时间';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.TRAINING_SPACE is
'培训地点';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.PAYEE_NAME is
'收款单位名称';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.OPENING_BANK is
'开户行';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.ACCOUNT_NUMBER is
'账号';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.CONTACT_INFO is
'联系人及电话';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.FK_FEE_DETAIL is
'预算详细';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.PERSON_COUNT is
'申报人数';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.FK_UNIT is
'承办单位';

/*==============================================================*/
/* Table: PE_FEE_BUDGET_DETAIL                                  */
/*==============================================================*/
create table TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL  (
   ID                   VARCHAR2(50)                    not null,
   FEE_SURVEY           NUMBER(9,2),
   FEE_RESEARCH         NUMBER(9,2),
   FEE_ARGUMENT         NUMBER(9,2),
   FEE_MEAL             NUMBER(9,2),
   FEE_ACCOMMODATION    NUMBER(9,2),
   FEE_TRAFFIC_STU      NUMBER(9,2),
   FEE_TEACH            NUMBER(9,2),
   FEE_TRAFFIC_EXPERT   NUMBER(9,2),
   FEE_MEAL_ACC_EXPERT  NUMBER(9,2),
   FEE_MATERIALS        NUMBER(9,2),
   FEE_COURSE           NUMBER(9,2),
   FEE_ELECTRON_COURSE  NUMBER(9,2),
   FEE_AREA_RENT        NUMBER(9,2),
   FEE_EQUIPMENT_RENT   NUMBER(9,2),
   FEE_APPRAISE         NUMBER(9,2),
   FEE_SUMMARY_APPRAISE NUMBER(9,2),
   FEE_LABOUR_SERVICE   NUMBER(9,2),
   FEE_PUBLICITY        NUMBER(9,2),
   FEE_PETTY            NUMBER(9,2),
   FEE_NOPLAN           NUMBER(9,2),
   NOTE_SURVEY          VARCHAR2(200),
   NOTE_RESEARCH        VARCHAR2(200),
   NOTE_ARGUMENT        VARCHAR2(200),
   NOTE_MEAL            VARCHAR2(200),
   NOTE_ACCOMMODATION   VARCHAR2(200),
   NOTE_TRAFFIC_STU     VARCHAR2(200),
   NOTE_TEACH           VARCHAR2(200),
   NOTE_TRAFFIC_EXPERT  VARCHAR2(200),
   NOTE_MEAL_ACC_EXPERT VARCHAR2(200),
   NOTE_MATERIALS       VARCHAR2(200),
   NOTE_TEXT_COURSE     VARCHAR2(200),
   NOTE_ELECTRON_COURSE VARCHAR2(200),
   NOTE_AREA_RENT       VARCHAR2(200),
   NOTE_EQUIPMENT_RENT  VARCHAR2(200),
   NOTE_APPRAISE        VARCHAR2(200),
   NOTE_SUMMARY_APPRAISE VARCHAR2(200),
   NOTE_LABOUR_SERVICE  VARCHAR2(200),
   NOTE_PUBLICITY       VARCHAR2(200),
   NOTE_PETTY           VARCHAR2(200),
   NOTE_NOPLAN          VARCHAR2(200),
   constraint PK_PE_FEE_BUDGET_DETAIL primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL is
'预算表详细';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_SURVEY is
'前期调研费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_RESEARCH is
'方案研制费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_ARGUMENT is
'项目论证费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_MEAL is
'餐费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_ACCOMMODATION is
'学员住宿费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_TRAFFIC_STU is
'交通费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_TEACH is
'授课费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_TRAFFIC_EXPERT is
'专家交通费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_MEAL_ACC_EXPERT is
'专家食宿费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_MATERIALS is
'学员学习资料费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_COURSE is
'文本课程资源
开发费用';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_ELECTRON_COURSE is
'电子课程资源';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_AREA_RENT is
'场地租用费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_EQUIPMENT_RENT is
'设备租用费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_APPRAISE is
'项目评估费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_SUMMARY_APPRAISE is
'项目总结评估费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_LABOUR_SERVICE is
'劳务费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_PUBLICITY is
'宣传费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_PETTY is
'公杂费';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_NOPLAN is
'不可预计支出';

/*==============================================================*/
/* Table: PE_JOB                                                */
/*==============================================================*/
create table TCHR_TRAINING1.PE_JOB  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(100),
   FINISH_DATE          DATE,
   SEND_UNIT            VARCHAR2(50),
   NOTE                 CLOB,
   FK_JOBPRIORITY       VARCHAR2(50),
   START_DATE           DATE,
   constraint PK_PE_JOB primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_JOB is
'任务';

comment on column TCHR_TRAINING1.PE_JOB.NAME is
'任务名称';

comment on column TCHR_TRAINING1.PE_JOB.FINISH_DATE is
'截至时间';

comment on column TCHR_TRAINING1.PE_JOB.SEND_UNIT is
'发送单位';

comment on column TCHR_TRAINING1.PE_JOB.NOTE is
'备注';

comment on column TCHR_TRAINING1.PE_JOB.FK_JOBPRIORITY is
'优先级';

comment on column TCHR_TRAINING1.PE_JOB.START_DATE is
'任务开始时间';

/*==============================================================*/
/* Table: PE_MANAGER                                            */
/*==============================================================*/
create table TCHR_TRAINING1.PE_MANAGER  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(100),
   LOGIN_ID             VARCHAR2(50),
   FK_UNIT              VARCHAR2(50),
   DEPARTMENT           VARCHAR2(100),
   TELEPHONE            VARCHAR2(50),
   EMAIL                VARCHAR2(50),
   OFFICE_PHONE         VARCHAR2(50),
   FAX                  VARCHAR2(50),
   FK_GENDER            VARCHAR2(50),
   BIRTHDAY             DATE,
   ZHIWUZHICHENG        VARCHAR2(100),
   ADDRESS              VARCHAR2(100),
   ZIP                  VARCHAR2(50),
   FOLK                 VARCHAR2(50),
   POLITICS             VARCHAR2(50),
   EDUCATION            VARCHAR2(50),
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_STATUS            VARCHAR2(50),
   NOTE                 CLOB,
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_PROPERTY        VARCHAR2(50),
   constraint PK_PE_MANAGER primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on column TCHR_TRAINING1.PE_MANAGER.NAME is
'姓名';

comment on column TCHR_TRAINING1.PE_MANAGER.LOGIN_ID is
'登录ID';

comment on column TCHR_TRAINING1.PE_MANAGER.FK_UNIT is
'单位';

comment on column TCHR_TRAINING1.PE_MANAGER.DEPARTMENT is
'部门';

comment on column TCHR_TRAINING1.PE_MANAGER.TELEPHONE is
'手机号';

comment on column TCHR_TRAINING1.PE_MANAGER.EMAIL is
'电子邮箱';

comment on column TCHR_TRAINING1.PE_MANAGER.OFFICE_PHONE is
'办公电话';

comment on column TCHR_TRAINING1.PE_MANAGER.FAX is
'传真';

comment on column TCHR_TRAINING1.PE_MANAGER.FK_GENDER is
'性别';

comment on column TCHR_TRAINING1.PE_MANAGER.BIRTHDAY is
'出生年月';

comment on column TCHR_TRAINING1.PE_MANAGER.ZHIWUZHICHENG is
'职务/职称';

comment on column TCHR_TRAINING1.PE_MANAGER.ADDRESS is
'通信地址';

comment on column TCHR_TRAINING1.PE_MANAGER.ZIP is
'邮编';

comment on column TCHR_TRAINING1.PE_MANAGER.FOLK is
'民族';

comment on column TCHR_TRAINING1.PE_MANAGER.POLITICS is
'政治面貌';

comment on column TCHR_TRAINING1.PE_MANAGER.EDUCATION is
'学历';

comment on column TCHR_TRAINING1.PE_MANAGER.FK_SSO_USER_ID is
'sso_user';

comment on column TCHR_TRAINING1.PE_MANAGER.FK_STATUS is
'用户状态';

comment on column TCHR_TRAINING1.PE_MANAGER.NOTE is
'备注';

comment on column TCHR_TRAINING1.PE_MANAGER.FLAG_ISVALID is
'用户是否有效';

comment on column TCHR_TRAINING1.PE_MANAGER.FLAG_PROPERTY is
'属性';

/*==============================================================*/
/* Table: PE_MEETING                                            */
/*==============================================================*/
create table TCHR_TRAINING1.PE_MEETING  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(100),
   MEETING_DATE         DATE,
   CREATE_DATE          DATE,
   FK_UNIT              VARCHAR2(50),
   PLACE                VARCHAR2(100),
   NOTE                 CLOB,
   RECEIPR_UNIT         VARCHAR2(4000),
   RESOURCE_UNIT        VARCHAR2(4000),
   FK_MANAGER_ID        VARCHAR2(50),
   SCOPSTRING           VARCHAR2(4000),
   constraint PK_PE_MEETING primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on column TCHR_TRAINING1.PE_MEETING.NAME is
'名称';

comment on column TCHR_TRAINING1.PE_MEETING.MEETING_DATE is
'会议时间';

comment on column TCHR_TRAINING1.PE_MEETING.CREATE_DATE is
'创建时间';

comment on column TCHR_TRAINING1.PE_MEETING.FK_UNIT is
'举办单位';

comment on column TCHR_TRAINING1.PE_MEETING.PLACE is
'地点';

comment on column TCHR_TRAINING1.PE_MEETING.NOTE is
'备注';

comment on column TCHR_TRAINING1.PE_MEETING.RECEIPR_UNIT is
'回执单位';

comment on column TCHR_TRAINING1.PE_MEETING.RESOURCE_UNIT is
'上传资源单位';

comment on column TCHR_TRAINING1.PE_MEETING.FK_MANAGER_ID is
'添加人';

comment on column TCHR_TRAINING1.PE_MEETING.SCOPSTRING is
'存储单位信息';

/*==============================================================*/
/* Table: PE_MEETING_RESOURCE                                   */
/*==============================================================*/
create table TCHR_TRAINING1.PE_MEETING_RESOURCE  (
   ID                   VARCHAR2(50)                    not null,
   FK_MEETING           VARCHAR2(50),
   FK_UNIT              VARCHAR2(50),
   UPLOAD_DATE          DATE,
   NOTE                 CLOB,
   MEETINGRESOURCE      VARCHAR2(100),
   UPLOAD_PERSON        VARCHAR2(50),
   NAME                 VARCHAR2(100),
   constraint PK_MEETING_RESOUR primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_MEETING_RESOURCE is
'会议资源表';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.FK_MEETING is
'会议';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.FK_UNIT is
'单位';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.UPLOAD_DATE is
'时间';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.NOTE is
'备注';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.MEETINGRESOURCE is
'上传资源路径';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.UPLOAD_PERSON is
'上传人';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.NAME is
'文件名';

/*==============================================================*/
/* Table: PE_NOTE_HISTORY                                       */
/*==============================================================*/
create table TCHR_TRAINING1.PE_NOTE_HISTORY  (
   ID                   VARCHAR2(50)                    not null,
   SENDER_ID            VARCHAR2(50),
   RECEIVER_ID          VARCHAR2(50),
   SEND_TIME            DATE,
   CONTENT              VARCHAR2(255),
   constraint NOTE_HISTORY_PK primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on column TCHR_TRAINING1.PE_NOTE_HISTORY.SENDER_ID is
'短信发送者id';

comment on column TCHR_TRAINING1.PE_NOTE_HISTORY.RECEIVER_ID is
'短信接收者id';

comment on column TCHR_TRAINING1.PE_NOTE_HISTORY.SEND_TIME is
'短信发送时间';

comment on column TCHR_TRAINING1.PE_NOTE_HISTORY.CONTENT is
'短信内容';

/*==============================================================*/
/* Table: PE_OTHER_MATERIAL                                     */
/*==============================================================*/
create table TCHR_TRAINING1.PE_OTHER_MATERIAL  (
   ID                   VARCHAR2(50)                    not null,
   MATERIAL_NAME        VARCHAR2(100),
   MATERIAL_FILE        VARCHAR2(100),
   FK_PRO_APPLYNO       VARCHAR2(50),
   FK_UNIT              VARCHAR2(50),
   UPLOAD_DATE          DATE,
   constraint PK_OTHER_MATERIAL_1 primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on column TCHR_TRAINING1.PE_OTHER_MATERIAL.MATERIAL_NAME is
'材料名称';

comment on column TCHR_TRAINING1.PE_OTHER_MATERIAL.MATERIAL_FILE is
'上传文件';

comment on column TCHR_TRAINING1.PE_OTHER_MATERIAL.UPLOAD_DATE is
'上传日期';

/*==============================================================*/
/* Table: PE_PRIORITY                                           */
/*==============================================================*/
create table TCHR_TRAINING1.PE_PRIORITY  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_PRI_CAT_ID        VARCHAR2(50),
   NAMESPACE            VARCHAR2(50)                    not null,
   ACTION               VARCHAR2(50)                    not null,
   METHOD               VARCHAR2(50)                    not null,
   constraint PK_PE_PRIORITY primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_PRIORITY is
'权限表';

comment on column TCHR_TRAINING1.PE_PRIORITY.NAMESPACE is
'Struts NAMESPACE';

comment on column TCHR_TRAINING1.PE_PRIORITY.ACTION is
'Struts ACTION';

comment on column TCHR_TRAINING1.PE_PRIORITY.METHOD is
'Struts METHOD';

/*==============================================================*/
/* Table: PE_PRI_CATEGORY                                       */
/*==============================================================*/
create table TCHR_TRAINING1.PE_PRI_CATEGORY  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_PARENT_ID         VARCHAR2(50),
   CODE                 VARCHAR2(50)                    not null,
   PATH                 VARCHAR2(1000),
   FLAG_LEFT_MENU       CHAR,
   constraint PK_PE_PRI_CATET primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging,
   constraint AK_KEY_3_PE_PRI unique (CODE)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_PRI_CATEGORY is
'权限类别表';

comment on column TCHR_TRAINING1.PE_PRI_CATEGORY.FK_PARENT_ID is
'父类别权限分类，为空则表示为根分类';

comment on column TCHR_TRAINING1.PE_PRI_CATEGORY.CODE is
'编号（js使用）';

comment on column TCHR_TRAINING1.PE_PRI_CATEGORY.PATH is
'点击时的目标地址';

comment on column TCHR_TRAINING1.PE_PRI_CATEGORY.FLAG_LEFT_MENU is
'是否是左菜单 1左菜单 0上菜单';

/*==============================================================*/
/* Table: PE_PRI_ROLE                                           */
/*==============================================================*/
create table TCHR_TRAINING1.PE_PRI_ROLE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FLAG_ROLE_TYPE       VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_PE_PRI_ROLE primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging,
   constraint AK_KEY_1_PE_PRI unique (NAME)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_PRI_ROLE is
'用户角色表';

comment on column TCHR_TRAINING1.PE_PRI_ROLE.FLAG_ROLE_TYPE is
'类型 1总站 2分站 3教师 4学生  总之 要跟ssoUser的USER_TYPEG 一样';

/*==============================================================*/
/* Table: PE_PROVINCE                                           */
/*==============================================================*/
create table TCHR_TRAINING1.PE_PROVINCE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(50),
   NOTE                 CLOB,
   constraint PK_PE_PROVINCE primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on column TCHR_TRAINING1.PE_PROVINCE.NAME is
'省份名称';

comment on column TCHR_TRAINING1.PE_PROVINCE.CODE is
'编码';

comment on column TCHR_TRAINING1.PE_PROVINCE.NOTE is
'备注';

/*==============================================================*/
/* Table: PE_PRO_APPLY                                          */
/*==============================================================*/
create table TCHR_TRAINING1.PE_PRO_APPLY  (
   ID                   VARCHAR2(50)                    not null,
   FK_UNIT              VARCHAR2(50),
   FK_SUBJECT           VARCHAR2(50),
   DECLARATION          VARCHAR2(200),
   FK_CHECK_RESULT_PROVINCE VARCHAR2(50),
   FK_CHECK_NATIONAL    VARCHAR2(50),
   FK_CHECK_FIRST       VARCHAR2(50),
   NOTE_FIRST           CLOB,
   FK_CHECK_FINAL       VARCHAR2(50),
   NOTE_FINAL           CLOB,
   DECLARE_DATE         DATE,
   FK_APPLYNO           VARCHAR2(50),
   SCHEME               VARCHAR2(200),
   CLASS_IDENTIFIER     VARCHAR2(15),
   constraint PK_PE_PROGRAM_2 primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE_FIRST )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( NOTE_FINAL )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_PRO_APPLY is
'申报单位申请的申报项目';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_UNIT is
'申报单位';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_SUBJECT is
'申报学科';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.DECLARATION is
'申报书';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_CHECK_RESULT_PROVINCE is
'省级审核结果';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_CHECK_NATIONAL is
'师范司审核结果';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_CHECK_FIRST is
'初审结果';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.NOTE_FIRST is
'初审备注';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_CHECK_FINAL is
'终审结果';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.NOTE_FINAL is
'终审备注';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.DECLARE_DATE is
'申报时间';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_APPLYNO is
'申报批次';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.SCHEME is
'实施方案';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.CLASS_IDENTIFIER is
'班级标识码';

/*==============================================================*/
/* Table: PE_PRO_APPLYNO                                        */
/*==============================================================*/
create table TCHR_TRAINING1.PE_PRO_APPLYNO  (
   ID                   VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(200),
   YEAR                 VARCHAR2(50),
   FK_PROGRAM_TYPE      VARCHAR2(50),
   REPLY_BOOK           VARCHAR2(100),
   FK_PROGRAM_STATUS    VARCHAR2(50),
   DEADLINE             DATE,
   FK_PROVINCE_CHECK    VARCHAR2(50),
   NOTE                 CLOB,
   FEE_STANDARD         NUMBER(6,2),
   LIMIT                NUMBER(2),
   constraint PK_PE_PROGRAM_A primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_PRO_APPLYNO is
'申报批次';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.CODE is
'批次编号';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.NAME is
'批次名称';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.YEAR is
'所属年度';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.FK_PROGRAM_TYPE is
'申报类型';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.REPLY_BOOK is
'项目申报书格式';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.FK_PROGRAM_STATUS is
'申报书状态，活动，关闭';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.DEADLINE is
'申报时限';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.FK_PROVINCE_CHECK is
'是否需要省级的审核';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.NOTE is
'备注';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.FEE_STANDARD is
'人均经费标准';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.LIMIT is
'申报科目上限';

/*==============================================================*/
/* Table: PE_PRO_IMPLEMT                                        */
/*==============================================================*/
create table TCHR_TRAINING1.PE_PRO_IMPLEMT  (
   ID                   VARCHAR2(50)                    not null,
   STARTCOURSE_DATE     DATE,
   NOTICE_NAME          VARCHAR2(100),
   BRIEF_REPORT_NAME    VARCHAR2(100),
   BRIEF_REPORT_FILE    VARCHAR2(100),
   FK_PRO_APPLY         VARCHAR2(50),
   NOTICE_CONTENT       CLOB,
   constraint PK_PE_PROGRAM_I primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTICE_CONTENT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.STARTCOURSE_DATE is
'开班时间';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.NOTICE_NAME is
'开班通知名称';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.BRIEF_REPORT_NAME is
'简报名称';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.BRIEF_REPORT_FILE is
'简报文件';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.FK_PRO_APPLY is
'申报项目外键';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.NOTICE_CONTENT is
'开班通知内容';

/*==============================================================*/
/* Table: PE_SUBJECT                                            */
/*==============================================================*/
create table TCHR_TRAINING1.PE_SUBJECT  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(50),
   NOTE                 CLOB,
   constraint PK_PE_SUBJECT primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_SUBJECT is
'学科';

comment on column TCHR_TRAINING1.PE_SUBJECT.NAME is
'学科名称';

comment on column TCHR_TRAINING1.PE_SUBJECT.CODE is
'编码';

comment on column TCHR_TRAINING1.PE_SUBJECT.NOTE is
'备注';

/*==============================================================*/
/* Table: PE_TRAINEE                                            */
/*==============================================================*/
create table TCHR_TRAINING1.PE_TRAINEE  (
   ID                   VARCHAR2(50)                    not null,
   LOGIN_ID             VARCHAR2(50),
   NAME                 VARCHAR2(100),
   FK_UNIT_FROM         VARCHAR2(50),
   TELEPHONE            VARCHAR2(50),
   EMAIL                VARCHAR2(50),
   OFFICE_PHONE         VARCHAR2(50),
   FK_GENDER            VARCHAR2(50),
   AGE                  NUMBER(30),
   FK_GRADUTED          VARCHAR2(50),
   FK_MODIFY_CHECKED    VARCHAR2(50),
   FK_CHECKED_TRAINEE   VARCHAR2(50),
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_SUBJECT           VARCHAR2(50),
   FK_PROVINCE          VARCHAR2(50),
   NOTE                 CLOB,
   FK_STATUS_TRAINING   VARCHAR2(50),
   FOLK                 VARCHAR2(50),
   WORK_PLACE           VARCHAR2(100),
   EDUCATION            VARCHAR2(100),
   ZHIWU                VARCHAR2(50),
   WORKYEAR             VARCHAR2(50),
   FK_TRAINING_UNIT     VARCHAR2(50),
   FK_PRO_APPLYNO       VARCHAR2(50),
   SUBJECT              VARCHAR2(255),
   PROVINCE             VARCHAR2(50),
   ZHICHENG             VARCHAR2(50),
   NOTESECOND           CLOB,
   CERTIFICATE_NUMBER   VARCHAR2(20),
   constraint PK_PE_TRAINEE primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( NOTESECOND )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_TRAINEE is
'学员表';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_UNIT_FROM is
'培训单位';

comment on column TCHR_TRAINING1.PE_TRAINEE.TELEPHONE is
'手机';

comment on column TCHR_TRAINING1.PE_TRAINEE.EMAIL is
'Email';

comment on column TCHR_TRAINING1.PE_TRAINEE.OFFICE_PHONE is
'电话';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_GENDER is
'性别';

comment on column TCHR_TRAINING1.PE_TRAINEE.AGE is
'年龄';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_GRADUTED is
'状态,是否毕业';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_MODIFY_CHECKED is
'修改的资料是否审核';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_CHECKED_TRAINEE is
'是否审核过';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_SSO_USER_ID is
'sso_user';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_SUBJECT is
'学科';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_PROVINCE is
'省份';

comment on column TCHR_TRAINING1.PE_TRAINEE.NOTE is
'备注';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_STATUS_TRAINING is
'培训状态，是否参加培训，还是培训单位新加的';

comment on column TCHR_TRAINING1.PE_TRAINEE.FOLK is
'民族';

comment on column TCHR_TRAINING1.PE_TRAINEE.WORK_PLACE is
'单位';

comment on column TCHR_TRAINING1.PE_TRAINEE.EDUCATION is
'学历';

comment on column TCHR_TRAINING1.PE_TRAINEE.ZHIWU is
'职务';

comment on column TCHR_TRAINING1.PE_TRAINEE.WORKYEAR is
'教龄';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_TRAINING_UNIT is
'所在的培训单位（承办单位）';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_PRO_APPLYNO is
'申报批次';

comment on column TCHR_TRAINING1.PE_TRAINEE.SUBJECT is
'学科';

comment on column TCHR_TRAINING1.PE_TRAINEE.PROVINCE is
'省份';

comment on column TCHR_TRAINING1.PE_TRAINEE.ZHICHENG is
'职称';

comment on column TCHR_TRAINING1.PE_TRAINEE.NOTESECOND is
'备注2';

comment on column TCHR_TRAINING1.PE_TRAINEE.CERTIFICATE_NUMBER is
'证书编号';

/*==============================================================*/
/* Table: PE_TRAIN_EXPERT                                       */
/*==============================================================*/
create table TCHR_TRAINING1.PE_TRAIN_EXPERT  (
   ID                   VARCHAR2(50)                    not null,
   LOGIN_ID             VARCHAR2(100),
   NAME                 VARCHAR2(100)                   not null,
   FK_UNIT              VARCHAR2(50),
   TELEPHONE            VARCHAR2(50),
   EMAIL                VARCHAR2(50),
   OFFICE_PHONE         VARCHAR2(50),
   FK_GENDER            VARCHAR2(50),
   AGE                  VARCHAR2(50),
   FK_STATUS            VARCHAR2(50),
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_SUBJECT           VARCHAR2(50),
   FK_PROVINCE          VARCHAR2(50),
   NOTE_1               CLOB,
   EDUCATION            VARCHAR2(50),
   POLITICS             VARCHAR2(50),
   ZHIWU                VARCHAR2(50),
   ZHICHENG             VARCHAR2(50),
   RESEARCH_AREA        VARCHAR2(200),
   TRAINING_AREA        VARCHAR2(250),
   IDCARD               VARCHAR2(50),
   WORKPLACE            VARCHAR2(100),
   ADDRESS              VARCHAR2(100),
   ZIP                  VARCHAR2(50),
   HOME_PHONE           VARCHAR2(50),
   FAX                  VARCHAR2(50),
   PERSONAL_RESUME      CLOB,
   TRAINING_RESULT      CLOB,
   TRAINING_EXPERIENCE  CLOB,
   OTHER_ITEMS          CLOB,
   UNIT_COMMENT         CLOB,
   RECOMMEND_COMMENT    CLOB,
   FINAL_COMMENT        CLOB,
   FOLK                 VARCHAR2(50),
   BIRTHDATE            DATE,
   RECOMMEND_DATE       DATE,
   MAJOR                VARCHAR2(50),
   WORKPLACE_P          VARCHAR2(100),
   INPUT_DATE           DATE,
   SEARCH_COUNT         VARCHAR2(50)                   default '0',
   NOTE                 VARCHAR2(100),
   constraint PK_PE_TRAINNING primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 256K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 16384K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( PERSONAL_RESUME )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( TRAINING_RESULT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( TRAINING_EXPERIENCE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( OTHER_ITEMS )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( UNIT_COMMENT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( RECOMMEND_COMMENT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( FINAL_COMMENT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( NOTE_1 )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.NAME is
'姓名';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_UNIT is
'推荐单位';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.TELEPHONE is
'手机号';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.EMAIL is
'邮箱';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.OFFICE_PHONE is
'电话号码';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_GENDER is
'性别';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.AGE is
'年龄';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_STATUS is
'状态';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_SSO_USER_ID is
'sso_user';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_SUBJECT is
'学科';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_PROVINCE is
'省份';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.NOTE_1 is
'备注';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.EDUCATION is
'学历';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.POLITICS is
'政治面貌';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.ZHIWU is
'职务';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.ZHICHENG is
'职称';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.RESEARCH_AREA is
'研究专长';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.TRAINING_AREA is
'教学专长';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.IDCARD is
'身份证号';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.WORKPLACE is
'工作单位';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.ADDRESS is
'通讯地址';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.ZIP is
'邮编';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.HOME_PHONE is
'住宅电话';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FAX is
'传真';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.PERSONAL_RESUME is
'个人简历';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.TRAINING_RESULT is
'教育教学成果';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.TRAINING_EXPERIENCE is
'教师培训经验';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.OTHER_ITEMS is
'其他需要说明事项';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.UNIT_COMMENT is
'所在单位意见';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.RECOMMEND_COMMENT is
'推荐部门意见';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FINAL_COMMENT is
'教育部师范司组织专家审核意见';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FOLK is
'民族';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.BIRTHDATE is
'出生年月';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.RECOMMEND_DATE is
'推荐时间';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.MAJOR is
'所学专业';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.INPUT_DATE is
'入库时间';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.SEARCH_COUNT is
'培训专家查询次数';

/*==============================================================*/
/* Table: PE_UNIT                                               */
/*==============================================================*/
create table TCHR_TRAINING1.PE_UNIT  (
   ID                   VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(100),
   FK_UNIT_TYPE         VARCHAR2(50),
   FK_PROVINCE          VARCHAR2(50),
   FLAG_ISVALID         VARCHAR2(50),
   NOTE                 CLOB,
   constraint PK_PE_UNIT primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_UNIT is
'单位表';

comment on column TCHR_TRAINING1.PE_UNIT.CODE is
'编码';

comment on column TCHR_TRAINING1.PE_UNIT.NAME is
'单位名称';

comment on column TCHR_TRAINING1.PE_UNIT.FK_UNIT_TYPE is
'单位类型';

comment on column TCHR_TRAINING1.PE_UNIT.FK_PROVINCE is
'所属省市';

comment on column TCHR_TRAINING1.PE_UNIT.FLAG_ISVALID is
'是否有效';

comment on column TCHR_TRAINING1.PE_UNIT.NOTE is
'备注';

/*==============================================================*/
/* Table: PE_VALUA_EXPERT                                       */
/*==============================================================*/
create table TCHR_TRAINING1.PE_VALUA_EXPERT  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(100),
   TELEPHONE            VARCHAR2(50),
   EMAIL                VARCHAR2(50),
   OFFICE_PHONE         VARCHAR2(50),
   FK_GENDER            VARCHAR2(50),
   AGE                  VARCHAR2(50),
   FK_STATUS            VARCHAR2(50),
   FK_SSO_USER_ID       VARCHAR2(50),
   NOTE_1               CLOB,
   FOLK                 VARCHAR2(50),
   BIRTHYEARMONTH       VARCHAR2(50),
   EDUCATION            VARCHAR2(50),
   MAJOR                VARCHAR2(50),
   POLITICS             VARCHAR2(50),
   ZHIWU                VARCHAR2(50),
   ZHICHENG             VARCHAR2(50),
   RESEARCH_AREA        VARCHAR2(200),
   IDCARD               VARCHAR2(50),
   TRAINING_AREA        VARCHAR2(250),
   WORKPLACE            VARCHAR2(100),
   ADDRESS              VARCHAR2(100),
   ZIP                  VARCHAR2(50),
   HOME_PHONE           VARCHAR2(50),
   FAX                  VARCHAR2(50),
   PERSONAL_RESUME      CLOB,
   TRAINING_RESULT      CLOB,
   TRAINING_EXPERIENCE  CLOB,
   OTHER_ITEMS          CLOB,
   UNIT_COMMENT         CLOB,
   RECOMMEND_COMMENT    CLOB,
   FINAL_COMMENT        CLOB,
   INPUT_DATE           DATE,
   LOGIN_ID             VARCHAR2(50),
   NOTE                 VARCHAR2(100),
   constraint PK_PE_VALUATE_E primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE_1 )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( PERSONAL_RESUME )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( TRAINING_RESULT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( TRAINING_EXPERIENCE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( OTHER_ITEMS )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( UNIT_COMMENT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( RECOMMEND_COMMENT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( FINAL_COMMENT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_VALUA_EXPERT is
'评审专家';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.NAME is
'姓名';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.TELEPHONE is
'手机号';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.EMAIL is
'邮箱';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.OFFICE_PHONE is
'电话号码';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FK_GENDER is
'性别';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.AGE is
'年龄';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FK_STATUS is
'状态';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FK_SSO_USER_ID is
'sso_user';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.NOTE_1 is
'备注';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FOLK is
'民族';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.BIRTHYEARMONTH is
'出生年月';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.EDUCATION is
'学历';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.MAJOR is
'专业';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.POLITICS is
'政治面貌';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.ZHIWU is
'职务';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.ZHICHENG is
'职称';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.RESEARCH_AREA is
'研究专长';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.IDCARD is
'身份证号';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.TRAINING_AREA is
'教学专长';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.WORKPLACE is
'工作单位';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.ADDRESS is
'通讯地址';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.ZIP is
'邮编';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.HOME_PHONE is
'住宅电话';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FAX is
'传真';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.PERSONAL_RESUME is
'个人简历';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.TRAINING_RESULT is
'学术成果';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.TRAINING_EXPERIENCE is
'教师培训工作经验';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.OTHER_ITEMS is
'其他需要说明事项';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.UNIT_COMMENT is
'所在单位意见';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.RECOMMEND_COMMENT is
'推荐部门意见';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FINAL_COMMENT is
'教育部师范司组织专家审核意见';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.INPUT_DATE is
'入库时间';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.LOGIN_ID is
'登录ID';

/*==============================================================*/
/* Table: PE_VOTE_PAPER                                         */
/*==============================================================*/
create table TCHR_TRAINING1.PE_VOTE_PAPER  (
   ID                   VARCHAR2(50)                    not null,
   TITLE                VARCHAR2(500)                   not null,
   PICTITLE             VARCHAR2(500),
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_CAN_SUGGEST     VARCHAR2(50),
   FLAG_VIEW_SUGGEST    VARCHAR2(50),
   FLAG_LIMIT_DIFFIP    VARCHAR2(50),
   FLAG_LIMIT_DIFFSESSION VARCHAR2(50),
   FLAG_TYPE            VARCHAR2(50),
   FOUND_DATE           DATE,
   START_DATE           DATE,
   END_DATE             DATE,
   KEYWORDS             VARCHAR2(4000),
   NOTE                 CLOB,
   constraint PK_PE_VOTE_PAPER primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PE_VOTE_PAPER is
'投票表';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.TITLE is
'标题';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.PICTITLE is
'图片标题（来自M4,暂确定是否使用）';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_ISVALID is
'ENUMCONST是否有效';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_CAN_SUGGEST is
'ENUMCONST是否有建议功能';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_VIEW_SUGGEST is
'ENUMCONST是否可查看建议';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_LIMIT_DIFFIP is
'ENUMCONST是否限制IP';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_LIMIT_DIFFSESSION is
'ENUMCONST是否限制SESSION';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_TYPE is
'根据培训项目（peProApplyno）作为设计调查问卷类型';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FOUND_DATE is
'创建日期';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.START_DATE is
'投票开始时间';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.END_DATE is
'投票结束时间';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.KEYWORDS is
'关键字';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.NOTE is
'正文';

/*==============================================================*/
/* Table: PE_WORKPLACE                                          */
/*==============================================================*/
create table TCHR_TRAINING1.PE_WORKPLACE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(50),
   NOTE                 VARCHAR2(50),
   constraint PK_PE_WORKPLACE primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 192K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 192K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

/*==============================================================*/
/* Table: PR_JOB_UNIT                                           */
/*==============================================================*/
create table TCHR_TRAINING1.PR_JOB_UNIT  (
   ID                   VARCHAR2(50)                    not null,
   FK_UNIT              VARCHAR2(50),
   FK_JOB               VARCHAR2(50),
   FK_JOB_STATUS        VARCHAR2(50),
   FK_JOB_CHECK         VARCHAR2(50),
   REPLY                CLOB,
   UPLOAD_FILE          VARCHAR2(1000),
   CHECK_NOTE           CLOB,
   constraint PK_PR_JOB_UNIT primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( REPLY )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( CHECK_NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_JOB_UNIT is
'任务和单位关系表';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.FK_UNIT is
'承办单位';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.FK_JOB is
'任务';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.FK_JOB_STATUS is
'任务状态，是否完成';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.FK_JOB_CHECK is
'审核是否通过';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.REPLY is
'回复内容';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.UPLOAD_FILE is
'上传附件';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.CHECK_NOTE is
'审核评语';

/*==============================================================*/
/* Table: PR_MEET_PERSON                                        */
/*==============================================================*/
create table TCHR_TRAINING1.PR_MEET_PERSON  (
   ID                   VARCHAR2(50)                    not null,
   FK_UNIT              VARCHAR2(50),
   FK_MEETING           VARCHAR2(50),
   ATTENDING_PERSON     VARCHAR2(100),
   NOTE                 CLOB,
   constraint PK_PR_MEETING_P primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_MEET_PERSON is
'参会者人员';

comment on column TCHR_TRAINING1.PR_MEET_PERSON.FK_UNIT is
'承办单位';

comment on column TCHR_TRAINING1.PR_MEET_PERSON.FK_MEETING is
'会议';

comment on column TCHR_TRAINING1.PR_MEET_PERSON.ATTENDING_PERSON is
'参会者';

comment on column TCHR_TRAINING1.PR_MEET_PERSON.NOTE is
'备注';

/*==============================================================*/
/* Table: PR_PRI_ROLE                                           */
/*==============================================================*/
create table TCHR_TRAINING1.PR_PRI_ROLE  (
   ID                   VARCHAR2(50)                    not null,
   FK_ROLE_ID           VARCHAR2(50),
   FK_PRIORITY_ID       VARCHAR2(50),
   FLAG_ISVALID         CHAR,
   constraint PK_PR_PRI_ROLE primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_PRI_ROLE is
'角色-权限 关系';

comment on column TCHR_TRAINING1.PR_PRI_ROLE.FLAG_ISVALID is
'是否有效  表记录不变 是否有权限靠本字段';

/*==============================================================*/
/* Table: PR_PROGRAM_UNIT                                       */
/*==============================================================*/
create table TCHR_TRAINING1.PR_PROGRAM_UNIT  (
   ID                   VARCHAR2(50)                    not null,
   FK_UNIT              VARCHAR2(50),
   FK_PROGRAM_ID        VARCHAR2(50),
   constraint PK_PR_PROGRAM_U primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on column TCHR_TRAINING1.PR_PROGRAM_UNIT.FK_UNIT is
'申报单位';

comment on column TCHR_TRAINING1.PR_PROGRAM_UNIT.FK_PROGRAM_ID is
'项目申报批次ID';

/*==============================================================*/
/* Table: PR_PRO_EXPERT                                         */
/*==============================================================*/
create table TCHR_TRAINING1.PR_PRO_EXPERT  (
   ID                   VARCHAR2(50)                    not null,
   FK_VALUATE_EXPERT    VARCHAR2(50)                    not null,
   FK_PROGRAM           VARCHAR2(50),
   RESULT_FIRST         NUMBER(5,2),
   NOTE_FIRST           CLOB,
   RESULT_FINAL         NUMBER(5,2),
   NOTE_FINLA           CLOB,
   constraint PK_PR_PROGRAM_E primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging,
   constraint FK_PR_PROGR_RE7 unique (FK_VALUATE_EXPERT, FK_PROGRAM)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE_FIRST )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( NOTE_FINLA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_PRO_EXPERT is
'评审专家对申报项目的评审意见';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.FK_VALUATE_EXPERT is
'评审专家';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.FK_PROGRAM is
'申报项目';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.RESULT_FIRST is
'初审评分';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.NOTE_FIRST is
'初审意见';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.RESULT_FINAL is
'终审评分';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.NOTE_FINLA is
'终审意见';

/*==============================================================*/
/* Table: PR_PRO_SUMMARY                                        */
/*==============================================================*/
create table TCHR_TRAINING1.PR_PRO_SUMMARY  (
   ID                   VARCHAR2(50)                    not null,
   FK_UNIT              VARCHAR2(50),
   FK_PRO_APPLYNO       VARCHAR2(50),
   SUMMARY_FILE         VARCHAR2(100),
   constraint PK_PRO_SUMMARY_1 primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_PRO_SUMMARY is
'总结报告';

comment on column TCHR_TRAINING1.PR_PRO_SUMMARY.SUMMARY_FILE is
'总结报告';

/*==============================================================*/
/* Table: PR_VOTE_ANSWER                                        */
/*==============================================================*/
create table TCHR_TRAINING1.PR_VOTE_ANSWER  (
   ID                   VARCHAR2(50)                    not null,
   PR_VOTE_QUESTION     VARCHAR2(50),
   CLASS_IDENTIFIER     VARCHAR2(15),
   VOTE_DATE            DATE,
   ANSWER1              NUMBER(8)                      default 0,
   ANSWER2              NUMBER(8)                      default 0,
   ANSWER3              NUMBER(8)                      default 0,
   ANSWER4              NUMBER(8)                      default 0,
   ANSWER5              NUMBER(8)                      default 0,
   ANSWER6              NUMBER(8)                      default 0,
   ANSWER7              NUMBER(8)                      default 0,
   ANSWER8              NUMBER(8)                      default 0,
   ANSWER9              NUMBER(8)                      default 0,
   ANSWER10             NUMBER(8)                      default 0,
   ANSWER11             NUMBER(8)                      default 0,
   ANSWER12             NUMBER(8)                      default 0,
   ANSWER13             NUMBER(8)                      default 0,
   ANSWER14             NUMBER(8)                      default 0,
   ANSWER15             NUMBER(8)                      default 0,
   constraint PK_VOTE_ANSWER primary key (ID)
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_VOTE_ANSWER is
'调查问卷学员答案表';

comment on column TCHR_TRAINING1.PR_VOTE_ANSWER.PR_VOTE_QUESTION is
'调查问卷题目';

comment on column TCHR_TRAINING1.PR_VOTE_ANSWER.CLASS_IDENTIFIER is
'班级标识符';

comment on column TCHR_TRAINING1.PR_VOTE_ANSWER.VOTE_DATE is
'投票时间';

/*==============================================================*/
/* Table: PR_VOTE_QUESTION                                      */
/*==============================================================*/
create table TCHR_TRAINING1.PR_VOTE_QUESTION  (
   ID                   VARCHAR2(50)                    not null,
   FK_VOTE_PAPER_ID     VARCHAR2(50),
   FLAG_QUESTION_TYPE   VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   QUESTION_NOTE        VARCHAR2(1000),
   ITEM_NUM             NUMBER(2),
   ITEM1                VARCHAR2(50),
   ITEM2                VARCHAR2(50),
   ITEM3                VARCHAR2(50),
   ITEM4                VARCHAR2(50),
   ITEM5                VARCHAR2(50),
   ITEM6                VARCHAR2(50),
   ITEM7                VARCHAR2(50),
   ITEM8                VARCHAR2(50),
   ITEM9                VARCHAR2(50),
   ITEM10               VARCHAR2(50),
   ITEM11               VARCHAR2(50),
   ITEM12               VARCHAR2(50),
   ITEM13               VARCHAR2(50),
   ITEM14               VARCHAR2(50),
   ITEM15               VARCHAR2(50),
   constraint PK_PR_VOTE_QUESTION primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_VOTE_QUESTION is
'投票问卷题目表';

comment on column TCHR_TRAINING1.PR_VOTE_QUESTION.FLAG_QUESTION_TYPE is
'ENUMCONST类型 1单选 2多选';

comment on column TCHR_TRAINING1.PR_VOTE_QUESTION.QUESTION_NOTE is
'内容';

comment on column TCHR_TRAINING1.PR_VOTE_QUESTION.ITEM_NUM is
'选项数量（来自M4，暂不知是否使用）';

comment on column TCHR_TRAINING1.PR_VOTE_QUESTION.ITEM1 is
'选项';

/*==============================================================*/
/* Table: PR_VOTE_RECORD                                        */
/*==============================================================*/
create table TCHR_TRAINING1.PR_VOTE_RECORD  (
   ID                   VARCHAR2(50)                    not null,
   FK_VOTE_PAPER_ID     VARCHAR2(50),
   IP                   VARCHAR2(50),
   USER_SESSION         VARCHAR2(500),
   VOTE_DATE            DATE,
   CLASS_IDENTIFIER     VARCHAR2(15),
   constraint PK_PR_VOTE_RECORD primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_VOTE_RECORD is
'投票记录表（记录IP、SESSION）';

comment on column TCHR_TRAINING1.PR_VOTE_RECORD.IP is
'IP（用于限制同IP只投一次）';

comment on column TCHR_TRAINING1.PR_VOTE_RECORD.USER_SESSION is
'SESSION号（用于限制同SESSION只投一次）';

comment on column TCHR_TRAINING1.PR_VOTE_RECORD.VOTE_DATE is
'投票时间';

comment on column TCHR_TRAINING1.PR_VOTE_RECORD.CLASS_IDENTIFIER is
'班级标识码';

/*==============================================================*/
/* Table: PR_VOTE_SUGGEST                                       */
/*==============================================================*/
create table TCHR_TRAINING1.PR_VOTE_SUGGEST  (
   ID                   VARCHAR2(50)                    not null,
   FK_VOTE_PAPER_ID     VARCHAR2(50),
   FLAG_CHECK           VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   IP                   VARCHAR2(50),
   FOUND_DATE           DATE,
   NOTE                 CLOB,
   CLASS_IDENTIFIER     VARCHAR2(15),
   constraint PK_PR_VOTE_SUGGEST primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
  lob
 ( NOTE )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
monitoring
  noparallel;

comment on table TCHR_TRAINING1.PR_VOTE_SUGGEST is
'投票建议表';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.FLAG_CHECK is
'是否通过审核（不通过将不显示在前台）';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.IP is
'建议人IP';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.FOUND_DATE is
'建议时间';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.NOTE is
'建议内容';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.CLASS_IDENTIFIER is
'班级标示码';

/*==============================================================*/
/* Table: SSO_USER                                              */
/*==============================================================*/
create table TCHR_TRAINING1.SSO_USER  (
   ID                   VARCHAR2(50)                    not null,
   LOGIN_ID             VARCHAR2(50)                    not null,
   PASSWORD             VARCHAR2(50)                    not null,
   FK_ROLE_ID           VARCHAR2(50),
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   LOGIN_NUM            NUMBER(8)                      default 0,
   LAST_LOGIN_DATE      DATE                           default sysdate,
   LAST_LOGIN_IP        VARCHAR2(100),
   CHECKED_INFO         VARCHAR2(1),
   CHECKED_PW           VARCHAR2(1),
   constraint PK_SSO_USER primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 128K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging,
   constraint AK_KEY_1_SSO_US unique (LOGIN_ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 128K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on column TCHR_TRAINING1.SSO_USER.FLAG_ISVALID is
'ENUMCONST是否有效';

comment on column TCHR_TRAINING1.SSO_USER.LOGIN_NUM is
'登录次数';

comment on column TCHR_TRAINING1.SSO_USER.LAST_LOGIN_DATE is
'最后登录时间';

comment on column TCHR_TRAINING1.SSO_USER.LAST_LOGIN_IP is
'最后登录IP';

comment on column TCHR_TRAINING1.SSO_USER.CHECKED_INFO is
'是否确认过个人信息';

comment on column TCHR_TRAINING1.SSO_USER.CHECKED_PW is
'是否确认过密码';

/*==============================================================*/
/* Table: SYSTEM_VARIABLES                                      */
/*==============================================================*/
create table TCHR_TRAINING1.SYSTEM_VARIABLES  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   VALUE                VARCHAR2(50)                    not null,
   PATTERN              VARCHAR2(500)                   not null,
   FLAG_PLATFORM_SECTION VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_SYSTEM_VARIABLES primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging,
   constraint AK_KEY_2_SYSTEM_V unique (NAME)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.SYSTEM_VARIABLES is
'系统变量表';

comment on column TCHR_TRAINING1.SYSTEM_VARIABLES.PATTERN is
'正则';

comment on column TCHR_TRAINING1.SYSTEM_VARIABLES.FLAG_PLATFORM_SECTION is
'ENUMCONST 所属模块（如果分权限则用到）';

/*==============================================================*/
/* Table: WHATYUSER_LOG4J                                       */
/*==============================================================*/
create table TCHR_TRAINING1.WHATYUSER_LOG4J  (
   ID                   VARCHAR2(50)                    not null,
   USERID               VARCHAR2(50),
   OPERATE_TIME         DATE,
   BEHAVIOR             VARCHAR2(500),
   STATUS               VARCHAR2(10),
   NOTES                VARCHAR2(4000),
   LOGTYPE              VARCHAR2(50),
   PRIORITY             VARCHAR2(50),
   IP                   VARCHAR2(50),
   constraint PK_WHATYUSER_LOG4J primary key (ID)
           using index
       pctfree 10
       initrans 2
       storage
       (
           initial 2048K
           minextents 1
           maxextents unlimited
       )
       tablespace TCHR_TRAINING1
        logging
)
  pctfree 10
initrans 1
storage
(
    initial 4096K
    minextents 1
    maxextents unlimited
)
tablespace TCHR_TRAINING1
logging
monitoring
  noparallel;

comment on table TCHR_TRAINING1.WHATYUSER_LOG4J is
'日志表';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.USERID is
'SSO LOGINID';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.OPERATE_TIME is
'操作时间';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.BEHAVIOR is
'动作';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.STATUS is
'状态 有效无效';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.NOTES is
'说明';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.LOGTYPE is
'登陆人角色';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.PRIORITY is
'来自M4';

alter table TCHR_TRAINING1.PE_BRIEF_REPORT
   add constraint FK_BRIEF_REPORT_APPLYNO_1 foreign key (FK_PRO_APPLY)
      references TCHR_TRAINING1.PE_PRO_APPLY (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_BULLETIN
   add constraint FK_PE_BULLETIN_FLAGISTOP foreign key (FLAG_ISTOP)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_BULLETIN
   add constraint FK_PE_BULLETIN_FLAGISVALID foreign key (FLAG_ISVALID)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_BULLETIN
   add constraint FK_PE_BULLETIN_PEMANAGER foreign key (FK_MANAGER_ID)
      references TCHR_TRAINING1.PE_MANAGER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   add constraint FK_COURSE_PLAN_R_APP04 foreign key (FK_PRO_APPLY)
      references TCHR_TRAINING1.PE_PRO_APPLY (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   add constraint FK_COURSE_PLAN_R_ENU05 foreign key (FLAG_VALUATION)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   add constraint FK__COURSE_PLAN_R_ENU06 foreign key (FLAG_BAK)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   add constraint FK__COURSE_PLAN_R_EXP01 foreign key (FK_TRAIN_EXPERT)
      references TCHR_TRAINING1.PE_TRAIN_EXPERT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_COURSE_PLAN
   add constraint FK__COURSE_PLAN_R_PRO03 foreign key (FK_PROVINCE)
      references TCHR_TRAINING1.PE_PROVINCE (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_EXPERT_SEARCH_HISTORY
   add constraint FK_EXPERT_ID foreign key (EXPERT_ID)
      references TCHR_TRAINING1.PE_TRAIN_EXPERT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_EXPERT_SEARCH_HISTORY
   add constraint FK_SEARCHER_ID foreign key (SEACHER_ID)
      references TCHR_TRAINING1.SSO_USER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET
   add constraint FK_FEE_ACTUAL_R_DETAIL01 foreign key (FK_FEE_DETAIL)
      references TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET
   add constraint FK_FEE_ACTUAL_R_PRO_APP01 foreign key (FK_PRO_APPLYNO)
      references TCHR_TRAINING1.PE_PRO_APPLYNO (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET
   add constraint FK_FEE_ACTUAL_R_UNIT01 foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_FEE_BUDGET
   add constraint FK_PE_BUDGET_R_FEE_DETAIL foreign key (FK_FEE_DETAIL)
      references TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_FEE_BUDGET
   add constraint FK_PE_FEE_R_PE_PRO_APP03 foreign key (FK_PRO_APPLYNO)
      references TCHR_TRAINING1.PE_PRO_APPLYNO (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_FEE_BUDGET
   add constraint FK_PE_FEE_R_PE_UNIT02 foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_JOB
   add constraint FK_PE_JOB_PRI foreign key (FK_JOBPRIORITY)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MANAGER
   add constraint FK_PE_MANAG_RE2 foreign key (FK_STATUS)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MANAGER
   add constraint FK_PE_MANAG_RE3 foreign key (FK_GENDER)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MANAGER
   add constraint FK_PE_MANAG_RE4 foreign key (FLAG_PROPERTY)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MANAGER
   add constraint FK_PE_MANAG_REF foreign key (FK_SSO_USER_ID)
      references TCHR_TRAINING1.SSO_USER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MANAGER
   add constraint FK_UNIT foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MANAGER
   add constraint FLAG_ISVALID foreign key (FLAG_ISVALID)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MEETING
   add constraint FK_MEETING_REFER_UNIT foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MEETING
   add constraint FK_PEMEETING_PEMANAGER_ID foreign key (FK_MANAGER_ID)
      references TCHR_TRAINING1.PE_MANAGER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MEETING_RESOURCE
   add constraint FK_MEETING_ID foreign key (FK_MEETING)
      references TCHR_TRAINING1.PE_MEETING (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MEETING_RESOURCE
   add constraint FK_MEETING_PERSON foreign key (UPLOAD_PERSON)
      references TCHR_TRAINING1.PE_MANAGER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_MEETING_RESOURCE
   add constraint FK_MEETING_UNIT foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_OTHER_MATERIAL
   add constraint FK_OTHER_MATERIAL_R_APPLYNO_01 foreign key (FK_PRO_APPLYNO)
      references TCHR_TRAINING1.PE_PRO_APPLYNO (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_OTHER_MATERIAL
   add constraint FK_OTHER_MATERIAL_R_UNIT_01 foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRIORITY
   add constraint FK_PE_PRIOR_REF foreign key (FK_PRI_CAT_ID)
      references TCHR_TRAINING1.PE_PRI_CATEGORY (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRI_CATEGORY
   add constraint FK_PE_PRI_C_REF foreign key (FK_PARENT_ID)
      references TCHR_TRAINING1.PE_PRI_CATEGORY (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRI_ROLE
   add constraint FK_KEY1_PE_PRICATE1 foreign key (FLAG_ROLE_TYPE)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRI_ROLE
   add constraint FK_KEY1_PE_PRICATE2 foreign key (FLAG_BAK)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   add constraint FK_PE_PROGR_R10 foreign key (FK_CHECK_FINAL)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   add constraint FK_PE_PROGR_RE12 foreign key (FK_APPLYNO)
      references TCHR_TRAINING1.PE_PRO_APPLYNO (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   add constraint FK_PE_PROGR_RE13 foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   add constraint FK_PE_PROGR_RE6 foreign key (FK_SUBJECT)
      references TCHR_TRAINING1.PE_SUBJECT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   add constraint FK_PE_PROGR_RE7 foreign key (FK_CHECK_RESULT_PROVINCE)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   add constraint FK_PE_PROGR_RE8 foreign key (FK_CHECK_NATIONAL)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLY
   add constraint FK_PE_PROGR_RE9 foreign key (FK_CHECK_FIRST)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLYNO
   add constraint FK_PE_PROGR_RE2 foreign key (FK_PROGRAM_TYPE)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLYNO
   add constraint FK_PE_PROGR_RE3 foreign key (FK_PROGRAM_STATUS)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_APPLYNO
   add constraint FK_PE_PROGR_RE4 foreign key (FK_PROVINCE_CHECK)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_PRO_IMPLEMT
   add constraint FK_PE_PROGRAM_R_AP_02 foreign key (FK_PRO_APPLY)
      references TCHR_TRAINING1.PE_PRO_APPLY (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_R15 foreign key (FK_MODIFY_CHECKED)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_R16 foreign key (FK_CHECKED_TRAINEE)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RE3 foreign key (FK_GENDER)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RE4 foreign key (FK_SSO_USER_ID)
      references TCHR_TRAINING1.SSO_USER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RE5 foreign key (FK_GRADUTED)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RE6 foreign key (FK_SUBJECT)
      references TCHR_TRAINING1.PE_SUBJECT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RE7 foreign key (FK_PROVINCE)
      references TCHR_TRAINING1.PE_PROVINCE (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RE8 foreign key (FK_STATUS_TRAINING)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RS9 foreign key (FK_PRO_APPLYNO)
      references TCHR_TRAINING1.PE_PRO_APPLYNO (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RT8 foreign key (FK_TRAINING_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAINEE
   add constraint FK_PE_TRAIN_RT9 foreign key (FK_UNIT_FROM)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   add constraint FK_PE_TRAIN_R10 foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   add constraint FK_PE_TRAIN_R11 foreign key (FK_GENDER)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   add constraint FK_PE_TRAIN_R12 foreign key (FK_STATUS)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   add constraint FK_PE_TRAIN_R13 foreign key (FK_SUBJECT)
      references TCHR_TRAINING1.PE_SUBJECT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   add constraint FK_PE_TRAIN_R14 foreign key (FK_PROVINCE)
      references TCHR_TRAINING1.PE_PROVINCE (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_TRAIN_EXPERT
   add constraint FK_PE_TRAIN_REF foreign key (FK_SSO_USER_ID)
      references TCHR_TRAINING1.SSO_USER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_UNIT
   add constraint FK_PE_UNIT_REF2 foreign key (FK_PROVINCE)
      references TCHR_TRAINING1.PE_PROVINCE (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_UNIT
   add constraint FK_PE_UNIT_REFE foreign key (FK_UNIT_TYPE)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_UNIT
   add constraint FK_PE_UNIT_REFERENCE_ENUM_CON foreign key (FLAG_ISVALID)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VALUA_EXPERT
   add constraint FK_PE_VALUA_RE2 foreign key (FK_GENDER)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VALUA_EXPERT
   add constraint FK_PE_VALUA_RE3 foreign key (FK_STATUS)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VALUA_EXPERT
   add constraint FK_PE_VALUA_REF foreign key (FK_SSO_USER_ID)
      references TCHR_TRAINING1.SSO_USER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   add constraint FK_FLAG_CAN_SUGGEST foreign key (FLAG_CAN_SUGGEST)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   add constraint FK_FLAG_ISVALID foreign key (FLAG_ISVALID)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   add constraint FK_FLAG_LIMIT_DIFFIP foreign key (FLAG_LIMIT_DIFFIP)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   add constraint FK_FLAG_LIMIT_DIFFSESSION foreign key (FLAG_LIMIT_DIFFSESSION)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   add constraint FK_FLAG_TYPE foreign key (FLAG_TYPE)
      references TCHR_TRAINING1.PE_PRO_APPLYNO (ID)
      not deferrable;

alter table TCHR_TRAINING1.PE_VOTE_PAPER
   add constraint FK_FLAG_VIEW_SUGGEST foreign key (FLAG_VIEW_SUGGEST)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_JOB_UNIT
   add constraint FK_PR_JOB_U_PE_UNITT foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_JOB_UNIT
   add constraint FK_PR_JOB_U_REF foreign key (FK_JOB)
      references TCHR_TRAINING1.PE_JOB (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_JOB_UNIT
   add constraint FK_PR_JOB_U_REFERENCE_ENUM_CO2 foreign key (FK_JOB_CHECK)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_JOB_UNIT
   add constraint FK_PR_JOB_U_REFERENCE_ENUM_CON foreign key (FK_JOB_STATUS)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_MEET_PERSON
   add constraint FK_PR_MEETI_RE2 foreign key (FK_MEETING)
      references TCHR_TRAINING1.PE_MEETING (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_MEET_PERSON
   add constraint FK_PR_MEETI_REF foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_PRI_ROLE
   add constraint FK_PR_PRI_R_RE2 foreign key (FK_ROLE_ID)
      references TCHR_TRAINING1.PE_PRI_ROLE (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_PRI_ROLE
   add constraint FK_PR_PRI_R_REF foreign key (FK_PRIORITY_ID)
      references TCHR_TRAINING1.PE_PRIORITY (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_PROGRAM_UNIT
   add constraint FK_PR_PROGR_RE2 foreign key (FK_PROGRAM_ID)
      references TCHR_TRAINING1.PE_PRO_APPLYNO (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_PROGRAM_UNIT
   add constraint FK_PR_PROGR_REF foreign key (FK_UNIT)
      references TCHR_TRAINING1.PE_UNIT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_PRO_EXPERT
   add constraint FK_PR_PROGR_RE3 foreign key (FK_PROGRAM)
      references TCHR_TRAINING1.PE_PRO_APPLY (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_PRO_EXPERT
   add constraint FK_PR_PROGR_RE4 foreign key (FK_VALUATE_EXPERT)
      references TCHR_TRAINING1.PE_VALUA_EXPERT (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_PRO_SUMMARY
   add constraint FK_PRO_SUMMARY_R_APPLYNO_3 foreign key (FK_PRO_APPLYNO)
      references TCHR_TRAINING1.PE_PRO_APPLYNO (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_VOTE_ANSWER
   add constraint FK_VOTE_QUESTION foreign key (PR_VOTE_QUESTION)
      references TCHR_TRAINING1.PR_VOTE_QUESTION (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_VOTE_QUESTION
   add constraint FK_PE_VOTE_PAPER foreign key (FK_VOTE_PAPER_ID)
      references TCHR_TRAINING1.PE_VOTE_PAPER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_VOTE_QUESTION
   add constraint FK_PR_VOTE_ENUMCONST foreign key (FLAG_QUESTION_TYPE)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_VOTE_QUESTION
   add constraint FK_PR_VOTE_ENUMCONST2 foreign key (FLAG_BAK)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_VOTE_RECORD
   add constraint FK_PR_VOTE_PAPER foreign key (FK_VOTE_PAPER_ID)
      references TCHR_TRAINING1.PE_VOTE_PAPER (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_VOTE_SUGGEST
   add constraint FK_PR_VOTE_SUGGEST_FLAG_CHECK foreign key (FLAG_CHECK)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.PR_VOTE_SUGGEST
   add constraint FK_PR_VOTE__REFERENCE_PE_VOTE2 foreign key (FK_VOTE_PAPER_ID)
      references TCHR_TRAINING1.PE_VOTE_PAPER (ID)
      not deferrable;

alter table TCHR_TRAINING1.SSO_USER
   add constraint FK_SSO_USER_REF foreign key (FK_ROLE_ID)
      references TCHR_TRAINING1.PE_PRI_ROLE (ID)
      not deferrable;

alter table TCHR_TRAINING1.SSO_USER
   add constraint FK_SSO_USER_REF1 foreign key (FLAG_ISVALID)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;

alter table TCHR_TRAINING1.SSO_USER
   add constraint FK_SSO_USER_REF2 foreign key (FLAG_BAK)
      references TCHR_TRAINING1.ENUM_CONST (ID)
      not deferrable;


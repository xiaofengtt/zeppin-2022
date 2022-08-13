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
'��';

comment on column TCHR_TRAINING1.PE_BRIEF_REPORT.REPORT_NAME is
'������';

comment on column TCHR_TRAINING1.PE_BRIEF_REPORT.REPORT_FILE is
'�ϴ��ļ�';

comment on column TCHR_TRAINING1.PE_BRIEF_REPORT.UPLOAD_DATE is
'�ϴ�����';

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
'����ƻ�';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.ID is
'id';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.TRAINING_BEGIN_TIME is
'��ѵ��ʼʱ��';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.PRELECT_WAY is
'�ڿη�ʽ';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FK_TRAIN_EXPERT is
'ר��';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.EXPERT_NAME is
'ר������ ���ר�Ҳ���ר�ҿ�����Ҫ��д';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.WORK_PLACE is
'ר�ҵ�λ  ���ר�Ҳ���ר�ҿ�����Ҫ��д';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.ZHICHENG is
'ר��ְ��  ���ר�Ҳ���ר�ҿ�����Ҫ��д';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FK_PROVINCE is
'ר��ʡ��  ���ר�Ҳ���ר�ҿ�����Ҫ��д';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.NOTE is
'��ע';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.COMMENTS is
'����';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FK_PRO_APPLY is
'������Ŀ';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FLAG_VALUATION is
'����';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.THEME is
'����';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.TRAIN_PLACE is
'��ѵ�ص�';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.TRAINING_END_TIME is
'��ѵ����ʱ��';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FIRSTVOTE is
'��ѧ��ͶƱ��һ�ܻ�ӭ�Ĵ���';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.SECONDVOTE is
'��ѧ��ͶƱ�ڶ��ܻ�ӭ�Ĵ���';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.THIRDVOTE is
'��ѧ��ͶƱ�����ܻ�ӭ�Ĵ���';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FOUTHVOTE is
'��ѧ��ͶƱ�����ܻ�ӭ�Ĵ���';

comment on column TCHR_TRAINING1.PE_COURSE_PLAN.FIFTHVOTE is
'��ѧ��ͶƱ�����ܻ�ӭ�Ĵ���';

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
'�����';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.FK_UNIT is
'�а쵥λ';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.FK_PRO_APPLYNO is
'�걨����';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.TRAINING_DATE is
'��ѵʱ��';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.TRAINING_SPACE is
'��ѵ�ص�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.PAYEE_NAME is
'�տλ����';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.OPENING_BANK is
'������';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.ACCOUNT_NUMBER is
'�˺�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.CONTACT_INFO is
'��ϵ�˼��绰';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.FK_FEE_DETAIL is
'������ϸ';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.PERSON_COUNT is
'�걨����';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET.UNIT_NAME is
'�а쵥λ����';

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
'�������ϸ';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_SURVEY is
'ǰ�ڵ��з�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_RESEARCH is
'�������Ʒ�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ARGUMENT is
'��Ŀ��֤��';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MEAL is
'�ͷ�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ACCOMMODATION is
'ѧԱס�޷�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TRAFFIC_STU is
'��ͨ��';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TEACH is
'�ڿη�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TRAFFIC_EXPERT is
'ר�ҽ�ͨ��';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MEAL_ACC_EXPERT is
'ר��ʳ�޷�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MATERIALS is
'ѧԱѧϰ���Ϸ�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_COURSE is
'�ı��γ���Դ
��������';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ELECTRON_COURSE is
'���ӿγ���Դ';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_AREA_RENT is
'�������÷�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_EQUIPMENT_RENT is
'�豸���÷�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_APPRAISE is
'��Ŀ������';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_SUMMARY_APPRAISE is
'��Ŀ�ܽ�������';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_LABOUR_SERVICE is
'�����';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_PUBLICITY is
'������';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_PETTY is
'���ӷ�';

comment on column TCHR_TRAINING1.PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_OTHER is
'����';

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
'Ԥ���';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.UNIT_NAME is
'�а쵥λ����';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.FK_PRO_APPLYNO is
'�걨����';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.TRAINING_DATE is
'��ѵʱ��';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.TRAINING_SPACE is
'��ѵ�ص�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.PAYEE_NAME is
'�տλ����';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.OPENING_BANK is
'������';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.ACCOUNT_NUMBER is
'�˺�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.CONTACT_INFO is
'��ϵ�˼��绰';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.FK_FEE_DETAIL is
'Ԥ����ϸ';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.PERSON_COUNT is
'�걨����';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET.FK_UNIT is
'�а쵥λ';

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
'Ԥ�����ϸ';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_SURVEY is
'ǰ�ڵ��з�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_RESEARCH is
'�������Ʒ�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_ARGUMENT is
'��Ŀ��֤��';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_MEAL is
'�ͷ�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_ACCOMMODATION is
'ѧԱס�޷�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_TRAFFIC_STU is
'��ͨ��';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_TEACH is
'�ڿη�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_TRAFFIC_EXPERT is
'ר�ҽ�ͨ��';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_MEAL_ACC_EXPERT is
'ר��ʳ�޷�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_MATERIALS is
'ѧԱѧϰ���Ϸ�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_COURSE is
'�ı��γ���Դ
��������';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_ELECTRON_COURSE is
'���ӿγ���Դ';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_AREA_RENT is
'�������÷�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_EQUIPMENT_RENT is
'�豸���÷�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_APPRAISE is
'��Ŀ������';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_SUMMARY_APPRAISE is
'��Ŀ�ܽ�������';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_LABOUR_SERVICE is
'�����';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_PUBLICITY is
'������';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_PETTY is
'���ӷ�';

comment on column TCHR_TRAINING1.PE_FEE_BUDGET_DETAIL.FEE_NOPLAN is
'����Ԥ��֧��';

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
'����';

comment on column TCHR_TRAINING1.PE_JOB.NAME is
'��������';

comment on column TCHR_TRAINING1.PE_JOB.FINISH_DATE is
'����ʱ��';

comment on column TCHR_TRAINING1.PE_JOB.SEND_UNIT is
'���͵�λ';

comment on column TCHR_TRAINING1.PE_JOB.NOTE is
'��ע';

comment on column TCHR_TRAINING1.PE_JOB.FK_JOBPRIORITY is
'���ȼ�';

comment on column TCHR_TRAINING1.PE_JOB.START_DATE is
'����ʼʱ��';

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
'����';

comment on column TCHR_TRAINING1.PE_MANAGER.LOGIN_ID is
'��¼ID';

comment on column TCHR_TRAINING1.PE_MANAGER.FK_UNIT is
'��λ';

comment on column TCHR_TRAINING1.PE_MANAGER.DEPARTMENT is
'����';

comment on column TCHR_TRAINING1.PE_MANAGER.TELEPHONE is
'�ֻ���';

comment on column TCHR_TRAINING1.PE_MANAGER.EMAIL is
'��������';

comment on column TCHR_TRAINING1.PE_MANAGER.OFFICE_PHONE is
'�칫�绰';

comment on column TCHR_TRAINING1.PE_MANAGER.FAX is
'����';

comment on column TCHR_TRAINING1.PE_MANAGER.FK_GENDER is
'�Ա�';

comment on column TCHR_TRAINING1.PE_MANAGER.BIRTHDAY is
'��������';

comment on column TCHR_TRAINING1.PE_MANAGER.ZHIWUZHICHENG is
'ְ��/ְ��';

comment on column TCHR_TRAINING1.PE_MANAGER.ADDRESS is
'ͨ�ŵ�ַ';

comment on column TCHR_TRAINING1.PE_MANAGER.ZIP is
'�ʱ�';

comment on column TCHR_TRAINING1.PE_MANAGER.FOLK is
'����';

comment on column TCHR_TRAINING1.PE_MANAGER.POLITICS is
'������ò';

comment on column TCHR_TRAINING1.PE_MANAGER.EDUCATION is
'ѧ��';

comment on column TCHR_TRAINING1.PE_MANAGER.FK_SSO_USER_ID is
'sso_user';

comment on column TCHR_TRAINING1.PE_MANAGER.FK_STATUS is
'�û�״̬';

comment on column TCHR_TRAINING1.PE_MANAGER.NOTE is
'��ע';

comment on column TCHR_TRAINING1.PE_MANAGER.FLAG_ISVALID is
'�û��Ƿ���Ч';

comment on column TCHR_TRAINING1.PE_MANAGER.FLAG_PROPERTY is
'����';

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
'����';

comment on column TCHR_TRAINING1.PE_MEETING.MEETING_DATE is
'����ʱ��';

comment on column TCHR_TRAINING1.PE_MEETING.CREATE_DATE is
'����ʱ��';

comment on column TCHR_TRAINING1.PE_MEETING.FK_UNIT is
'�ٰ쵥λ';

comment on column TCHR_TRAINING1.PE_MEETING.PLACE is
'�ص�';

comment on column TCHR_TRAINING1.PE_MEETING.NOTE is
'��ע';

comment on column TCHR_TRAINING1.PE_MEETING.RECEIPR_UNIT is
'��ִ��λ';

comment on column TCHR_TRAINING1.PE_MEETING.RESOURCE_UNIT is
'�ϴ���Դ��λ';

comment on column TCHR_TRAINING1.PE_MEETING.FK_MANAGER_ID is
'�����';

comment on column TCHR_TRAINING1.PE_MEETING.SCOPSTRING is
'�洢��λ��Ϣ';

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
'������Դ��';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.FK_MEETING is
'����';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.FK_UNIT is
'��λ';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.UPLOAD_DATE is
'ʱ��';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.NOTE is
'��ע';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.MEETINGRESOURCE is
'�ϴ���Դ·��';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.UPLOAD_PERSON is
'�ϴ���';

comment on column TCHR_TRAINING1.PE_MEETING_RESOURCE.NAME is
'�ļ���';

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
'���ŷ�����id';

comment on column TCHR_TRAINING1.PE_NOTE_HISTORY.RECEIVER_ID is
'���Ž�����id';

comment on column TCHR_TRAINING1.PE_NOTE_HISTORY.SEND_TIME is
'���ŷ���ʱ��';

comment on column TCHR_TRAINING1.PE_NOTE_HISTORY.CONTENT is
'��������';

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
'��������';

comment on column TCHR_TRAINING1.PE_OTHER_MATERIAL.MATERIAL_FILE is
'�ϴ��ļ�';

comment on column TCHR_TRAINING1.PE_OTHER_MATERIAL.UPLOAD_DATE is
'�ϴ�����';

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
'Ȩ�ޱ�';

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
'Ȩ������';

comment on column TCHR_TRAINING1.PE_PRI_CATEGORY.FK_PARENT_ID is
'�����Ȩ�޷��࣬Ϊ�����ʾΪ������';

comment on column TCHR_TRAINING1.PE_PRI_CATEGORY.CODE is
'��ţ�jsʹ�ã�';

comment on column TCHR_TRAINING1.PE_PRI_CATEGORY.PATH is
'���ʱ��Ŀ���ַ';

comment on column TCHR_TRAINING1.PE_PRI_CATEGORY.FLAG_LEFT_MENU is
'�Ƿ�����˵� 1��˵� 0�ϲ˵�';

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
'�û���ɫ��';

comment on column TCHR_TRAINING1.PE_PRI_ROLE.FLAG_ROLE_TYPE is
'���� 1��վ 2��վ 3��ʦ 4ѧ��  ��֮ Ҫ��ssoUser��USER_TYPEG һ��';

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
'ʡ������';

comment on column TCHR_TRAINING1.PE_PROVINCE.CODE is
'����';

comment on column TCHR_TRAINING1.PE_PROVINCE.NOTE is
'��ע';

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
'�걨��λ������걨��Ŀ';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_UNIT is
'�걨��λ';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_SUBJECT is
'�걨ѧ��';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.DECLARATION is
'�걨��';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_CHECK_RESULT_PROVINCE is
'ʡ����˽��';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_CHECK_NATIONAL is
'ʦ��˾��˽��';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_CHECK_FIRST is
'������';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.NOTE_FIRST is
'����ע';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_CHECK_FINAL is
'������';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.NOTE_FINAL is
'����ע';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.DECLARE_DATE is
'�걨ʱ��';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.FK_APPLYNO is
'�걨����';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.SCHEME is
'ʵʩ����';

comment on column TCHR_TRAINING1.PE_PRO_APPLY.CLASS_IDENTIFIER is
'�༶��ʶ��';

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
'�걨����';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.CODE is
'���α��';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.NAME is
'��������';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.YEAR is
'�������';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.FK_PROGRAM_TYPE is
'�걨����';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.REPLY_BOOK is
'��Ŀ�걨���ʽ';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.FK_PROGRAM_STATUS is
'�걨��״̬������ر�';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.DEADLINE is
'�걨ʱ��';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.FK_PROVINCE_CHECK is
'�Ƿ���Ҫʡ�������';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.NOTE is
'��ע';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.FEE_STANDARD is
'�˾����ѱ�׼';

comment on column TCHR_TRAINING1.PE_PRO_APPLYNO.LIMIT is
'�걨��Ŀ����';

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
'����ʱ��';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.NOTICE_NAME is
'����֪ͨ����';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.BRIEF_REPORT_NAME is
'������';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.BRIEF_REPORT_FILE is
'���ļ�';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.FK_PRO_APPLY is
'�걨��Ŀ���';

comment on column TCHR_TRAINING1.PE_PRO_IMPLEMT.NOTICE_CONTENT is
'����֪ͨ����';

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
'ѧ��';

comment on column TCHR_TRAINING1.PE_SUBJECT.NAME is
'ѧ������';

comment on column TCHR_TRAINING1.PE_SUBJECT.CODE is
'����';

comment on column TCHR_TRAINING1.PE_SUBJECT.NOTE is
'��ע';

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
'ѧԱ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_UNIT_FROM is
'��ѵ��λ';

comment on column TCHR_TRAINING1.PE_TRAINEE.TELEPHONE is
'�ֻ�';

comment on column TCHR_TRAINING1.PE_TRAINEE.EMAIL is
'Email';

comment on column TCHR_TRAINING1.PE_TRAINEE.OFFICE_PHONE is
'�绰';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_GENDER is
'�Ա�';

comment on column TCHR_TRAINING1.PE_TRAINEE.AGE is
'����';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_GRADUTED is
'״̬,�Ƿ��ҵ';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_MODIFY_CHECKED is
'�޸ĵ������Ƿ����';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_CHECKED_TRAINEE is
'�Ƿ���˹�';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_SSO_USER_ID is
'sso_user';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_SUBJECT is
'ѧ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_PROVINCE is
'ʡ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.NOTE is
'��ע';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_STATUS_TRAINING is
'��ѵ״̬���Ƿ�μ���ѵ��������ѵ��λ�¼ӵ�';

comment on column TCHR_TRAINING1.PE_TRAINEE.FOLK is
'����';

comment on column TCHR_TRAINING1.PE_TRAINEE.WORK_PLACE is
'��λ';

comment on column TCHR_TRAINING1.PE_TRAINEE.EDUCATION is
'ѧ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.ZHIWU is
'ְ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.WORKYEAR is
'����';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_TRAINING_UNIT is
'���ڵ���ѵ��λ���а쵥λ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.FK_PRO_APPLYNO is
'�걨����';

comment on column TCHR_TRAINING1.PE_TRAINEE.SUBJECT is
'ѧ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.PROVINCE is
'ʡ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.ZHICHENG is
'ְ��';

comment on column TCHR_TRAINING1.PE_TRAINEE.NOTESECOND is
'��ע2';

comment on column TCHR_TRAINING1.PE_TRAINEE.CERTIFICATE_NUMBER is
'֤����';

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
'����';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_UNIT is
'�Ƽ���λ';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.TELEPHONE is
'�ֻ���';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.EMAIL is
'����';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.OFFICE_PHONE is
'�绰����';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_GENDER is
'�Ա�';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.AGE is
'����';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_STATUS is
'״̬';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_SSO_USER_ID is
'sso_user';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_SUBJECT is
'ѧ��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FK_PROVINCE is
'ʡ��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.NOTE_1 is
'��ע';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.EDUCATION is
'ѧ��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.POLITICS is
'������ò';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.ZHIWU is
'ְ��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.ZHICHENG is
'ְ��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.RESEARCH_AREA is
'�о�ר��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.TRAINING_AREA is
'��ѧר��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.IDCARD is
'���֤��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.WORKPLACE is
'������λ';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.ADDRESS is
'ͨѶ��ַ';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.ZIP is
'�ʱ�';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.HOME_PHONE is
'סլ�绰';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FAX is
'����';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.PERSONAL_RESUME is
'���˼���';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.TRAINING_RESULT is
'������ѧ�ɹ�';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.TRAINING_EXPERIENCE is
'��ʦ��ѵ����';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.OTHER_ITEMS is
'������Ҫ˵������';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.UNIT_COMMENT is
'���ڵ�λ���';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.RECOMMEND_COMMENT is
'�Ƽ��������';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FINAL_COMMENT is
'������ʦ��˾��֯ר��������';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.FOLK is
'����';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.BIRTHDATE is
'��������';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.RECOMMEND_DATE is
'�Ƽ�ʱ��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.MAJOR is
'��ѧרҵ';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.INPUT_DATE is
'���ʱ��';

comment on column TCHR_TRAINING1.PE_TRAIN_EXPERT.SEARCH_COUNT is
'��ѵר�Ҳ�ѯ����';

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
'��λ��';

comment on column TCHR_TRAINING1.PE_UNIT.CODE is
'����';

comment on column TCHR_TRAINING1.PE_UNIT.NAME is
'��λ����';

comment on column TCHR_TRAINING1.PE_UNIT.FK_UNIT_TYPE is
'��λ����';

comment on column TCHR_TRAINING1.PE_UNIT.FK_PROVINCE is
'����ʡ��';

comment on column TCHR_TRAINING1.PE_UNIT.FLAG_ISVALID is
'�Ƿ���Ч';

comment on column TCHR_TRAINING1.PE_UNIT.NOTE is
'��ע';

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
'����ר��';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.NAME is
'����';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.TELEPHONE is
'�ֻ���';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.EMAIL is
'����';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.OFFICE_PHONE is
'�绰����';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FK_GENDER is
'�Ա�';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.AGE is
'����';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FK_STATUS is
'״̬';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FK_SSO_USER_ID is
'sso_user';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.NOTE_1 is
'��ע';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FOLK is
'����';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.BIRTHYEARMONTH is
'��������';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.EDUCATION is
'ѧ��';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.MAJOR is
'רҵ';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.POLITICS is
'������ò';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.ZHIWU is
'ְ��';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.ZHICHENG is
'ְ��';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.RESEARCH_AREA is
'�о�ר��';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.IDCARD is
'���֤��';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.TRAINING_AREA is
'��ѧר��';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.WORKPLACE is
'������λ';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.ADDRESS is
'ͨѶ��ַ';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.ZIP is
'�ʱ�';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.HOME_PHONE is
'סլ�绰';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FAX is
'����';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.PERSONAL_RESUME is
'���˼���';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.TRAINING_RESULT is
'ѧ���ɹ�';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.TRAINING_EXPERIENCE is
'��ʦ��ѵ��������';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.OTHER_ITEMS is
'������Ҫ˵������';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.UNIT_COMMENT is
'���ڵ�λ���';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.RECOMMEND_COMMENT is
'�Ƽ��������';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.FINAL_COMMENT is
'������ʦ��˾��֯ר��������';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.INPUT_DATE is
'���ʱ��';

comment on column TCHR_TRAINING1.PE_VALUA_EXPERT.LOGIN_ID is
'��¼ID';

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
'ͶƱ��';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.TITLE is
'����';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.PICTITLE is
'ͼƬ���⣨����M4,��ȷ���Ƿ�ʹ�ã�';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_ISVALID is
'ENUMCONST�Ƿ���Ч';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_CAN_SUGGEST is
'ENUMCONST�Ƿ��н��鹦��';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_VIEW_SUGGEST is
'ENUMCONST�Ƿ�ɲ鿴����';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_LIMIT_DIFFIP is
'ENUMCONST�Ƿ�����IP';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_LIMIT_DIFFSESSION is
'ENUMCONST�Ƿ�����SESSION';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FLAG_TYPE is
'������ѵ��Ŀ��peProApplyno����Ϊ��Ƶ����ʾ�����';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.FOUND_DATE is
'��������';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.START_DATE is
'ͶƱ��ʼʱ��';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.END_DATE is
'ͶƱ����ʱ��';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.KEYWORDS is
'�ؼ���';

comment on column TCHR_TRAINING1.PE_VOTE_PAPER.NOTE is
'����';

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
'����͵�λ��ϵ��';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.FK_UNIT is
'�а쵥λ';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.FK_JOB is
'����';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.FK_JOB_STATUS is
'����״̬���Ƿ����';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.FK_JOB_CHECK is
'����Ƿ�ͨ��';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.REPLY is
'�ظ�����';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.UPLOAD_FILE is
'�ϴ�����';

comment on column TCHR_TRAINING1.PR_JOB_UNIT.CHECK_NOTE is
'�������';

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
'�λ�����Ա';

comment on column TCHR_TRAINING1.PR_MEET_PERSON.FK_UNIT is
'�а쵥λ';

comment on column TCHR_TRAINING1.PR_MEET_PERSON.FK_MEETING is
'����';

comment on column TCHR_TRAINING1.PR_MEET_PERSON.ATTENDING_PERSON is
'�λ���';

comment on column TCHR_TRAINING1.PR_MEET_PERSON.NOTE is
'��ע';

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
'��ɫ-Ȩ�� ��ϵ';

comment on column TCHR_TRAINING1.PR_PRI_ROLE.FLAG_ISVALID is
'�Ƿ���Ч  ���¼���� �Ƿ���Ȩ�޿����ֶ�';

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
'�걨��λ';

comment on column TCHR_TRAINING1.PR_PROGRAM_UNIT.FK_PROGRAM_ID is
'��Ŀ�걨����ID';

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
'����ר�Ҷ��걨��Ŀ���������';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.FK_VALUATE_EXPERT is
'����ר��';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.FK_PROGRAM is
'�걨��Ŀ';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.RESULT_FIRST is
'��������';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.NOTE_FIRST is
'�������';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.RESULT_FINAL is
'��������';

comment on column TCHR_TRAINING1.PR_PRO_EXPERT.NOTE_FINLA is
'�������';

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
'�ܽᱨ��';

comment on column TCHR_TRAINING1.PR_PRO_SUMMARY.SUMMARY_FILE is
'�ܽᱨ��';

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
'�����ʾ�ѧԱ�𰸱�';

comment on column TCHR_TRAINING1.PR_VOTE_ANSWER.PR_VOTE_QUESTION is
'�����ʾ���Ŀ';

comment on column TCHR_TRAINING1.PR_VOTE_ANSWER.CLASS_IDENTIFIER is
'�༶��ʶ��';

comment on column TCHR_TRAINING1.PR_VOTE_ANSWER.VOTE_DATE is
'ͶƱʱ��';

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
'ͶƱ�ʾ���Ŀ��';

comment on column TCHR_TRAINING1.PR_VOTE_QUESTION.FLAG_QUESTION_TYPE is
'ENUMCONST���� 1��ѡ 2��ѡ';

comment on column TCHR_TRAINING1.PR_VOTE_QUESTION.QUESTION_NOTE is
'����';

comment on column TCHR_TRAINING1.PR_VOTE_QUESTION.ITEM_NUM is
'ѡ������������M4���ݲ�֪�Ƿ�ʹ�ã�';

comment on column TCHR_TRAINING1.PR_VOTE_QUESTION.ITEM1 is
'ѡ��';

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
'ͶƱ��¼����¼IP��SESSION��';

comment on column TCHR_TRAINING1.PR_VOTE_RECORD.IP is
'IP����������ͬIPֻͶһ�Σ�';

comment on column TCHR_TRAINING1.PR_VOTE_RECORD.USER_SESSION is
'SESSION�ţ���������ͬSESSIONֻͶһ�Σ�';

comment on column TCHR_TRAINING1.PR_VOTE_RECORD.VOTE_DATE is
'ͶƱʱ��';

comment on column TCHR_TRAINING1.PR_VOTE_RECORD.CLASS_IDENTIFIER is
'�༶��ʶ��';

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
'ͶƱ�����';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.FLAG_CHECK is
'�Ƿ�ͨ����ˣ���ͨ��������ʾ��ǰ̨��';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.IP is
'������IP';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.FOUND_DATE is
'����ʱ��';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.NOTE is
'��������';

comment on column TCHR_TRAINING1.PR_VOTE_SUGGEST.CLASS_IDENTIFIER is
'�༶��ʾ��';

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
'ENUMCONST�Ƿ���Ч';

comment on column TCHR_TRAINING1.SSO_USER.LOGIN_NUM is
'��¼����';

comment on column TCHR_TRAINING1.SSO_USER.LAST_LOGIN_DATE is
'����¼ʱ��';

comment on column TCHR_TRAINING1.SSO_USER.LAST_LOGIN_IP is
'����¼IP';

comment on column TCHR_TRAINING1.SSO_USER.CHECKED_INFO is
'�Ƿ�ȷ�Ϲ�������Ϣ';

comment on column TCHR_TRAINING1.SSO_USER.CHECKED_PW is
'�Ƿ�ȷ�Ϲ�����';

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
'ϵͳ������';

comment on column TCHR_TRAINING1.SYSTEM_VARIABLES.PATTERN is
'����';

comment on column TCHR_TRAINING1.SYSTEM_VARIABLES.FLAG_PLATFORM_SECTION is
'ENUMCONST ����ģ�飨�����Ȩ�����õ���';

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
'��־��';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.USERID is
'SSO LOGINID';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.OPERATE_TIME is
'����ʱ��';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.BEHAVIOR is
'����';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.STATUS is
'״̬ ��Ч��Ч';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.NOTES is
'˵��';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.LOGTYPE is
'��½�˽�ɫ';

comment on column TCHR_TRAINING1.WHATYUSER_LOG4J.PRIORITY is
'����M4';

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


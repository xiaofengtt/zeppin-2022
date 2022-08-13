alter table PE_FEE_ACTUAL_BUDGET
   drop constraint FK_FEE_ACTUAL_R_DETAIL01;

alter table PE_FEE_ACTUAL_BUDGET
   drop constraint FK_FEE_ACTUAL_R_UNIT01;

alter table PE_FEE_ACTUAL_BUDGET
   drop constraint FK_FEE_ACTUAL_R_PRO_APP01;

drop table PE_FEE_ACTUAL_BUDGET cascade constraints;

/*==============================================================*/
/* Table: PE_FEE_ACTUAL_BUDGET                                  */
/*==============================================================*/
create table PE_FEE_ACTUAL_BUDGET  (
   ID                   varchar2(50)                    not null,
   FK_UNIT              varchar2(50),
   FK_PRO_APPLYNO       varchar2(50),
   TRAINING_DATE        DATE,
   TRAINING_SPACE       varchar2(100),
   PAYEE_NAME           varchar2(100),
   OPENING_BANK         varchar2(50),
   ACCOUNT_NUMBER       varchar2(50),
   CONTACT_INFO         varchar2(50),
   FK_FEE_DETAIL        varchar2(50)                    not null,
   constraint PK_PE_FEE_ACTUAL_BUDGET primary key (ID)
);

comment on table PE_FEE_ACTUAL_BUDGET is
'决算表';

comment on column PE_FEE_ACTUAL_BUDGET.FK_UNIT is
'承办单位';

comment on column PE_FEE_ACTUAL_BUDGET.FK_PRO_APPLYNO is
'申报批次';

comment on column PE_FEE_ACTUAL_BUDGET.TRAINING_DATE is
'培训时间';

comment on column PE_FEE_ACTUAL_BUDGET.TRAINING_SPACE is
'培训地点';

comment on column PE_FEE_ACTUAL_BUDGET.PAYEE_NAME is
'收款单位名称';

comment on column PE_FEE_ACTUAL_BUDGET.OPENING_BANK is
'开户行';

comment on column PE_FEE_ACTUAL_BUDGET.ACCOUNT_NUMBER is
'账号';

comment on column PE_FEE_ACTUAL_BUDGET.CONTACT_INFO is
'联系人及电话';

comment on column PE_FEE_ACTUAL_BUDGET.FK_FEE_DETAIL is
'决算详细';

alter table PE_FEE_ACTUAL_BUDGET
   add constraint FK_FEE_ACTUAL_R_DETAIL01 foreign key (FK_FEE_DETAIL)
      references PE_FEE_ACTUAL_BUDGET_DETAIL (ID);

alter table PE_FEE_ACTUAL_BUDGET
   add constraint FK_FEE_ACTUAL_R_UNIT01 foreign key (FK_UNIT)
      references PE_UNIT (ID);

alter table PE_FEE_ACTUAL_BUDGET
   add constraint FK_FEE_ACTUAL_R_PRO_APP01 foreign key (FK_PRO_APPLYNO)
      references PE_PRO_APPLYNO (ID);
alter table PE_FEE_ACTUAL_BUDGET
   drop constraint FK_FEE_ACTUAL_R_DETAIL01;

drop table PE_FEE_ACTUAL_BUDGET_DETAIL cascade constraints;

/*==============================================================*/
/* Table: PE_FEE_ACTUAL_BUDGET_DETAIL                           */
/*==============================================================*/
create table PE_FEE_ACTUAL_BUDGET_DETAIL  (
   ID                   varchar2(50)                    not null,
   FEE_SURVEY           number(6,2),
   FEE_RESEARCH         number(6,2),
   FEE_ARGUMENT         number(6,2),
   FEE_MEAL             number(6,2),
   FEE_ACCOMMODATION    number(6,2),
   FEE_TRAFFIC_STU      number(6,2),
   FEE_TEACH            number(6,2),
   FEE_TRAFFIC_EXPERT   number(6,2),
   FEE_MEAL_ACC_EXPERT  number(6,2),
   FEE_MATERIALS        number(6,2),
   FEE_COURSE           number(6,2),
   FEE_ELECTRON_COURSE  number(6,2),
   FEE_AREA_RENT        number(6,2),
   FEE_EQUIPMENT_RENT   number(6,2),
   FEE_APPRAISE         number(6,2),
   FEE_SUMMARY_APPRAISE number(6,2),
   FEE_LABOUR_SERVICE   number(6,2),
   FEE_PUBLICITY        number(6,2),
   FEE_PETTY            number(6,2),
   FEE_OTHER            number(6,2),
   NOTE_SURVEY          varchar2(200),
   NOTE_RESEARCH        varchar2(200),
   NOTE_ARGUMENT        varchar2(200),
   NOTE_MEAL            varchar2(200),
   NOTE_ACCOMMODATION   varchar2(200),
   NOTE_TRAFFIC_STU     varchar2(200),
   NOTE_TEACH           varchar2(200),
   NOTE_TRAFFIC_EXPERT  varchar2(200),
   NOTE_MEAL_ACC_EXPERT varchar2(200),
   NOTE_MATERIALS       varchar2(200),
   NOTE_TEXT_COURSE     varchar2(200),
   NOTE_ELECTRON_COURSE varchar2(200),
   NOTE_AREA_RENT       varchar2(200),
   NOTE_EQUIPMENT_RENT  varchar2(200),
   NOTE_APPRAISE        varchar2(200),
   NOTE_SUMMARY_APPRAISE varchar2(200),
   NOTE_LABOUR_SERVICE  varchar2(200),
   NOTE_PUBLICITY       varchar2(200),
   NOTE_PETTY           varchar2(200),
   NOTE_OTHER           varchar2(200),
   constraint PK_PE_FEE_ACTUAL_BUDGET_DETAIL primary key (ID)
);

comment on table PE_FEE_ACTUAL_BUDGET_DETAIL is
'决算表详细';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_SURVEY is
'前期调研费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_RESEARCH is
'方案研制费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ARGUMENT is
'项目论证费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MEAL is
'餐费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ACCOMMODATION is
'学员住宿费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TRAFFIC_STU is
'交通费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TEACH is
'授课费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_TRAFFIC_EXPERT is
'专家交通费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MEAL_ACC_EXPERT is
'专家食宿费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_MATERIALS is
'学员学习资料费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_COURSE is
'文本课程资源
开发费用
';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_ELECTRON_COURSE is
'电子课程资源';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_AREA_RENT is
'场地租用费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_EQUIPMENT_RENT is
'设备租用费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_APPRAISE is
'项目评估费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_SUMMARY_APPRAISE is
'项目总结评估费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_LABOUR_SERVICE is
'劳务费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_PUBLICITY is
'宣传费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_PETTY is
'公杂费';

comment on column PE_FEE_ACTUAL_BUDGET_DETAIL.FEE_OTHER is
'其他';
alter table PE_FEE_BUDGET
   drop constraint FK_PE_BUDGET_R_FEE_DETAIL;

alter table PE_FEE_BUDGET
   drop constraint FK_PE_FEE_R_PE_PRO_APP03;

alter table PE_FEE_BUDGET
   drop constraint FK_PE_FEE_R_PE_UNIT02;

drop table PE_FEE_BUDGET cascade constraints;

/*==============================================================*/
/* Table: PE_FEE_BUDGET                                         */
/*==============================================================*/
create table PE_FEE_BUDGET  (
   ID                   varchar2(50)                    not null,
   FK_UNIT              varchar2(50),
   FK_PRO_APPLYNO       varchar2(50),
   TRAINING_DATE        DATE,
   TRAINING_SPACE       varchar2(100),
   PAYEE_NAME           varchar2(100),
   OPENING_BANK         varchar2(50),
   ACCOUNT_NUMBER       varchar2(50),
   CONTACT_INFO         varchar2(50),
   FK_FEE_DETAIL        varchar2(50),
   constraint PK_PE_FEE_BUDGET primary key (ID)
);

comment on table PE_FEE_BUDGET is
'预算表';

comment on column PE_FEE_BUDGET.FK_UNIT is
'承办单位';

comment on column PE_FEE_BUDGET.FK_PRO_APPLYNO is
'申报批次';

comment on column PE_FEE_BUDGET.TRAINING_DATE is
'培训时间';

comment on column PE_FEE_BUDGET.TRAINING_SPACE is
'培训地点';

comment on column PE_FEE_BUDGET.PAYEE_NAME is
'收款单位名称';

comment on column PE_FEE_BUDGET.OPENING_BANK is
'开户行';

comment on column PE_FEE_BUDGET.ACCOUNT_NUMBER is
'账号';

comment on column PE_FEE_BUDGET.CONTACT_INFO is
'联系人及电话';

comment on column PE_FEE_BUDGET.FK_FEE_DETAIL is
'预算详细';

alter table PE_FEE_BUDGET
   add constraint FK_PE_BUDGET_R_FEE_DETAIL foreign key (FK_FEE_DETAIL)
      references PE_FEE_BUDGET_DETAIL (ID);

alter table PE_FEE_BUDGET
   add constraint FK_PE_FEE_R_PE_PRO_APP03 foreign key (FK_PRO_APPLYNO)
      references PE_PRO_APPLYNO (ID);

alter table PE_FEE_BUDGET
   add constraint FK_PE_FEE_R_PE_UNIT02 foreign key (FK_UNIT)
      references PE_UNIT (ID);
alter table PE_FEE_BUDGET
   drop constraint FK_PE_BUDGET_R_FEE_DETAIL;

drop table PE_FEE_BUDGET_DETAIL cascade constraints;

/*==============================================================*/
/* Table: PE_FEE_BUDGET_DETAIL                                  */
/*==============================================================*/
create table PE_FEE_BUDGET_DETAIL  (
   ID                   varchar2(50)                    not null,
   FEE_SURVEY           number(6,2),
   FEE_RESEARCH         number(6,2),
   FEE_ARGUMENT         number(6,2),
   FEE_MEAL             number(6,2),
   FEE_ACCOMMODATION    number(6,2),
   FEE_TRAFFIC_STU      number(6,2),
   FEE_TEACH            number(6,2),
   FEE_TRAFFIC_EXPERT   number(6,2),
   FEE_MEAL_ACC_EXPERT  number(6,2),
   FEE_MATERIALS        number(6,2),
   FEE_COURSE           number(6,2),
   FEE_ELECTRON_COURSE  number(6,2),
   FEE_AREA_RENT        number(6,2),
   FEE_EQUIPMENT_RENT   number(6,2),
   FEE_APPRAISE         number(6,2),
   FEE_SUMMARY_APPRAISE number(6,2),
   FEE_LABOUR_SERVICE   number(6,2),
   FEE_PUBLICITY        number(6,2),
   FEE_PETTY            number(6,2),
   FEE_NOPLAN           number(6,2),
   NOTE_SURVEY          varchar2(200),
   NOTE_RESEARCH        varchar2(200),
   NOTE_ARGUMENT        varchar2(200),
   NOTE_MEAL            varchar2(200),
   NOTE_ACCOMMODATION   varchar2(200),
   NOTE_TRAFFIC_STU     varchar2(200),
   NOTE_TEACH           varchar2(200),
   NOTE_TRAFFIC_EXPERT  varchar2(200),
   NOTE_MEAL_ACC_EXPERT varchar2(200),
   NOTE_MATERIALS       varchar2(200),
   NOTE_TEXT_COURSE     varchar2(200),
   NOTE_ELECTRON_COURSE varchar2(200),
   NOTE_AREA_RENT       varchar2(200),
   NOTE_EQUIPMENT_RENT  varchar2(200),
   NOTE_APPRAISE        varchar2(200),
   NOTE_SUMMARY_APPRAISE varchar2(200),
   NOTE_LABOUR_SERVICE  varchar2(200),
   NOTE_PUBLICITY       varchar2(200),
   NOTE_PETTY           varchar2(200),
   NOTE_NOPLAN          varchar2(200),
   constraint PK_PE_FEE_BUDGET_DETAIL primary key (ID)
);

comment on table PE_FEE_BUDGET_DETAIL is
'预算表详细';

comment on column PE_FEE_BUDGET_DETAIL.FEE_SURVEY is
'前期调研费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_RESEARCH is
'方案研制费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_ARGUMENT is
'项目论证费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_MEAL is
'餐费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_ACCOMMODATION is
'学员住宿费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_TRAFFIC_STU is
'交通费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_TEACH is
'授课费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_TRAFFIC_EXPERT is
'专家交通费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_MEAL_ACC_EXPERT is
'专家食宿费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_MATERIALS is
'学员学习资料费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_COURSE is
'文本课程资源
开发费用
';

comment on column PE_FEE_BUDGET_DETAIL.FEE_ELECTRON_COURSE is
'电子课程资源';

comment on column PE_FEE_BUDGET_DETAIL.FEE_AREA_RENT is
'场地租用费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_EQUIPMENT_RENT is
'设备租用费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_APPRAISE is
'项目评估费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_SUMMARY_APPRAISE is
'项目总结评估费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_LABOUR_SERVICE is
'劳务费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_PUBLICITY is
'宣传费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_PETTY is
'公杂费';

comment on column PE_FEE_BUDGET_DETAIL.FEE_NOPLAN is
'不可预计支出';

alter table PE_FEE_ACTUAL_BUDGET
   add constraint FK_FEE_ACTUAL_R_DETAIL01 foreign key (FK_FEE_DETAIL)
      references PE_FEE_ACTUAL_BUDGET_DETAIL (ID);
 alter table PE_FEE_BUDGET
   add constraint FK_PE_BUDGET_R_FEE_DETAIL foreign key (FK_FEE_DETAIL)
      references PE_FEE_BUDGET_DETAIL (ID);
--增加一个调查问卷学员回答答案表
-- Create table
create table PR_VOTE_ANSWER
(
  ID                VARCHAR2(50) not null,
  PR_VOTE_QUESTION  VARCHAR2(50),
  FK_TRAINEE        VARCHAR2(50),
  ANSWER            VARCHAR2(100),
  VOTE_DATE         DATE,
  TRAINING_UNIT     VARCHAR2(50),
  VOTETEACHERANSWER VARCHAR2(100)
)
tablespace TCHR_TRAINING1
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table PR_VOTE_ANSWER
  is '调查问卷学员答案表';
-- Add comments to the columns 
comment on column PR_VOTE_ANSWER.PR_VOTE_QUESTION
  is '调查问卷题目';
comment on column PR_VOTE_ANSWER.FK_TRAINEE
  is '学员';
comment on column PR_VOTE_ANSWER.ANSWER
  is '学员答案';
comment on column PR_VOTE_ANSWER.VOTE_DATE
  is '投票时间';
comment on column PR_VOTE_ANSWER.TRAINING_UNIT
  is '培训单位';
comment on column PR_VOTE_ANSWER.VOTETEACHERANSWER
  is '选择的前5名最受欢迎的老师和专题';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_VOTE_ANSWER
  add constraint PK_VOTE_ANSWER primary key (ID)
  disable;
alter table PR_VOTE_ANSWER
  add constraint FK_VOTE_QUESTION foreign key (PR_VOTE_QUESTION)
  references PR_VOTE_QUESTION (ID);
alter table PR_VOTE_ANSWER
  add constraint FK_VOTE_TRAINEE foreign key (FK_TRAINEE)
  references PE_TRAINEE (ID);
alter table PR_VOTE_ANSWER
  add constraint FK_VOTE_TRAIN_UNIT foreign key (TRAINING_UNIT)
  references PE_UNIT (ID);



--修改调查问卷 建议表，增加一个学员字段
-- Add/modify columns 
alter table PR_VOTE_SUGGEST add FK_TRAINEE varchar2(50);
-- Add comments to the columns 
comment on column PR_VOTE_SUGGEST.FK_TRAINEE
  is '学员';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_VOTE_SUGGEST
  add constraint FK_PR_VOTE_TRAINEE_SUGEEST foreign key (FK_TRAINEE)
  references pe_trainee (ID);

--修改调查问卷学员答案表
-- Add/modify columns 
alter table PR_VOTE_ANSWER add VOTE_DATE date;
alter table PR_VOTE_ANSWER add TRAINING_UNIT varchar2(50);
-- Add comments to the columns 
comment on column PR_VOTE_ANSWER.VOTE_DATE
  is '投票时间';
comment on column PR_VOTE_ANSWER.TRAINING_UNIT
  is '培训单位';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_VOTE_ANSWER
  add constraint FK_VOTE_TRAIN_UNIT foreign key (TRAINING_UNIT)
  references pe_unit (ID);


  --------------------修改调查问卷问题表
  alter table PR_VOTE_QUESTION
  add constraint FK_PE_VOTE_PAPER foreign key (FK_VOTE_PAPER_ID)
  references PE_VOTE_PAPER (ID);
alter table PR_VOTE_QUESTION
  add constraint FK_PR_VOTE_ENUMCONST foreign key (FLAG_QUESTION_TYPE)
  references ENUM_CONST (ID);
alter table PR_VOTE_QUESTION
  add constraint FK_PR_VOTE_ENUMCONST2 foreign key (FLAG_BAK)
  references ENUM_CONST (ID);

------------------------修改调查问卷学员答案表 删除学员单位外键

alter table PR_VOTE_ANSWER
  delete constraint FK_VOTE_TRAIN_UNIT ;



-----------------------修改课程表添加5个字段
alter table PE_COURSE_PLAN add FIRSTVOTE number default 0;
alter table PE_COURSE_PLAN add SECONDVOTE number default 0;
alter table PE_COURSE_PLAN add THIRDVOTE number default 0;
alter table PE_COURSE_PLAN add FOUTHVOTE number default 0;
alter table PE_COURSE_PLAN add FIFTHVOTE number default 0;
-- Add comments to the columns 
comment on column PE_COURSE_PLAN.FIRSTVOTE
  is '被学生投票第一受欢迎的次数';
comment on column PE_COURSE_PLAN.SECONDVOTE
  is '被学生投票第二受欢迎的次数';
comment on column PE_COURSE_PLAN.THIRDVOTE
  is '被学生投票第三受欢迎的次数';
comment on column PE_COURSE_PLAN.FOUTHVOTE
  is '被学生投票第四受欢迎的次数';
comment on column PE_COURSE_PLAN.FIFTHVOTE
  is '被学生投票第五受欢迎的次数';

-----------------------修改调查问卷表调查问卷类型的外键为培训项目
alter table PE_VOTE_PAPER
  drop constraint FK_FLAG_TYPE;
alter table PE_VOTE_PAPER
  add constraint FK_FLAG_TYPE foreign key (FLAG_TYPE)
  references pe_pro_applyno (ID);

------------------------修改会议表添加存储单位信息字段 
alter table PE_MEETING add SCOPSTRING varchar2(2000);
-- Add comments to the columns 
comment on column PE_MEETING.SCOPSTRING
  is '存储单位信息';


--张以会修改 
alter table PE_PRO_APPLY add CLASS_IDENTIFIER varchar(15); -- Add comments to the columns comment on column PE_PRO_APPLY.CLASS_IDENTIFIER is '班级标示码';

alter table PE_COURSE_PLAN drop constraint FK__COURSE_PLAN_R_UNI02;
alter table PE_COURSE_PLAN  rename column FK_UNIT to WORK_PLACE;
ALTER TABLE PE_COURSE_PLAN ADD  TRAIN_PLACE varchar2(100) ;
comment on column PE_COURSE_PLAN.TRAIN_PLACE is '培训地点';
alter table PE_COURSE_PLAN  rename column COURSE_TIME to TRAINING_BEGIN_TIME;
ALTER TABLE PE_COURSE_PLAN ADD  TRAINING_END_TIME date ;
comment on column PE_COURSE_PLAN.TRAINING_BEGIN_TIME is '培训开始时间';
comment on column PE_COURSE_PLAN.TRAINING_END_TIME is '培训结束时间';
ALTER TABLE PR_PROGRAM_UNIT add summary_File varchar2(100);
comment on column PR_PROGRAM_UNIT.summary_File is '总结报告';
ALTER TABLE PE_FEE_BUDGET add person_count varchar2(100);
comment on column PE_FEE_BUDGET.person_count is '申报人数';
ALTER TABLE PE_FEE_ACTUAL_BUDGET add person_count varchar2(100);
comment on column PE_FEE_ACTUAL_BUDGET.person_count is '申报人数';
ALTER TABLE PE_FEE_ACTUAL_BUDGET add UNIT_NAME varchar2(100);
comment on column PE_FEE_ACTUAL_BUDGET.UNIT_NAME is '承办单位名称';
ALTER TABLE PE_FEE_BUDGET add UNIT_NAME varchar2(100);
comment on column PE_FEE_BUDGET.UNIT_NAME is '承办单位名称';


-- Create table
create table PE_BRIEF_REPORT
(
  ID           VARCHAR2(50) not null,
  REPORT_NAME  VARCHAR2(100),
  REPORT_FILE  VARCHAR2(100),
  FK_PRO_APPLY VARCHAR2(50),
  UPLOAD_DATE  DATE
)
tablespace TCHR_TRAINING1
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table PE_BRIEF_REPORT
  is '简报';
-- Add comments to the columns 
comment on column PE_BRIEF_REPORT.REPORT_NAME
  is '简报名称';
comment on column PE_BRIEF_REPORT.REPORT_FILE
  is '上传文件';
comment on column PE_BRIEF_REPORT.UPLOAD_DATE
  is '上传日期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_BRIEF_REPORT
  add constraint PK_BRIEF_REPORT_1 primary key (ID)
  using index 
  tablespace TCHR_TRAINING1
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table PE_BRIEF_REPORT
  add constraint FK_BRIEF_REPORT_APPLYNO_1 foreign key (FK_PRO_APPLY)
  references PE_PRO_APPLY (ID);

-- Create table
create table PE_OTHER_MATERIAL
(
  ID             VARCHAR2(50) not null,
  MATERIAL_NAME  VARCHAR2(100),
  MATERIAL_FILE  VARCHAR2(100),
  FK_PRO_APPLYNO VARCHAR2(50),
  FK_UNIT        VARCHAR2(50),
  UPLOAD_DATE    DATE
)
tablespace TCHR_TRAINING1
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column PE_OTHER_MATERIAL.MATERIAL_NAME
  is '材料名称';
comment on column PE_OTHER_MATERIAL.MATERIAL_FILE
  is '上传文件';
comment on column PE_OTHER_MATERIAL.UPLOAD_DATE
  is '上传日期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_OTHER_MATERIAL
  add constraint PK_OTHER_MATERIAL_1 primary key (ID)
  using index 
  tablespace TCHR_TRAINING1
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table PE_OTHER_MATERIAL
  add constraint FK_OTHER_MATERIAL_R_APPLYNO_01 foreign key (FK_PRO_APPLYNO)
  references PE_PRO_APPLYNO (ID);
alter table PE_OTHER_MATERIAL
  add constraint FK_OTHER_MATERIAL_R_UNIT_01 foreign key (FK_UNIT)
  references PE_UNIT (ID);

alter table PE_PRO_IMPLEMT drop column SUMMARY_FILE;
alter table PR_PROGRAM_UNIT drop column SUMMARY_FILE;
-- Create table
create table PR_PRO_SUMMARY
(
  ID             VARCHAR2(50) not null,
  FK_UNIT        VARCHAR2(50),
  FK_PRO_APPLYNO VARCHAR2(50),
  SUMMARY_FILE   VARCHAR2(100)
)
tablespace TCHR_TRAINING1
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table PR_PRO_SUMMARY
  is '总结报告';
-- Add comments to the columns 
comment on column PR_PRO_SUMMARY.SUMMARY_FILE
  is '总结报告';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_PRO_SUMMARY
  add constraint PK_PRO_SUMMARY_1 primary key (ID);
alter table PR_PRO_SUMMARY
  add constraint FK_PRO_SUMMARY_R_UNIT_2 foreign key (FK_UNIT)
  references pe_unit (ID);
alter table PR_PRO_SUMMARY
  add constraint FK_PRO_SUMMARY_R_APPLYNO_3 foreign key (FK_PRO_APPLYNO)
  references pe_pro_applyno (ID);



-- Add/modify columns 
alter table PE_MANAGER add FLAG_PROPERTY VARCHAR2(50);
-- Add comments to the columns 
comment on column PE_MANAGER.FLAG_PROPERTY
  is '属性';
  -- Create/Recreate primary, unique and foreign key constraints 
alter table PE_MANAGER
  add constraint FK_PE_MANAG_RE4 foreign key (FLAG_PROPERTY)
  references enum_const (ID);
 
 alter table PE_COURSE_PLAN modify NOTE VARCHAR2(200);
 

 -- Add/modify columns 
alter table PE_PRO_APPLYNO add CLASS_HOUR NUMBER(3);
-- Add comments to the columns 
comment on column PE_PRO_APPLYNO.CLASS_HOUR
  is '学时';
 -- Add/modify columns 
alter table PE_TRAINEE add start_date date;
alter table PE_TRAINEE add end_date date;
-- Add comments to the columns 
comment on column PE_TRAINEE.start_date
  is '培训开始时间';
comment on column PE_TRAINEE.end_date
  is '培训结束时间';
   --以上均已添加的服务器1015
insert into enum_const values (sys_guid(),'其他','1528','FkUnitType','0',to_date('2010-10-17','yyyy-MM-dd'),'单位级别');
insert into pe_pri_role values(sys_guid(),'其他','402880962a5fa8b9012a5fde38b20016','');
insert into pr_pri_role  (select sys_guid() as id, (select id  from pe_pri_role where name='其他'),fk_priority_id,flag_isvalid from pr_pri_role where  fk_role_id='ff8080812b493236012b50c139cc02cf');
-- Drop columns 
alter table PE_MEETING drop column FK_UNIT;
alter table PE_MEETING drop column SCOPSTRING;
-- Add/modify columns 
alter table PR_MEET_PERSON rename column ATTENDING_PERSON to NAME;
alter table PR_MEET_PERSON modify NAME VARCHAR2(50);
-- Add/modify columns 
alter table PR_MEET_PERSON add Fk_Gender VARCHAR2(50);
alter table PR_MEET_PERSON add FOLK VARCHAR2(50);
alter table PR_MEET_PERSON add zhicheng VARCHAR2(50);
-- Add comments to the columns 
comment on column PR_MEET_PERSON.Fk_Gender
  is '性别';
comment on column PR_MEET_PERSON.FOLK
  is '民族';
comment on column PR_MEET_PERSON.zhicheng
  is '职称';
  alter table PR_MEET_PERSON add flag_bak VARCHAR2(50);
  -- Create/Recreate primary, unique and foreign key constraints 
alter table PR_MEET_PERSON
  add constraint FK_PR_MEET_R_PE1 foreign key (FK_GENDER)
  references enum_const (ID);
alter table PR_MEET_PERSON
  add constraint FK_PR_MEET_R_PE2 foreign key (FLAG_BAK)
  references enum_const (ID);
-- Add/modify columns 
alter table PR_MEET_PERSON add CREATE_DATE date;
-- Add comments to the columns 
comment on column PR_MEET_PERSON.CREATE_DATE
  is '上传时间';
  -- Drop columns 
alter table PE_JOB drop column SEND_UNIT;
alter table PE_JOB add FK_MANAGER_ID VARCHAR2(50);
-- Add comments to the columns 
comment on column PE_JOB.FK_MANAGER_ID
  is '发布人';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_JOB
  add constraint FK_PE_JOB_MANAGER foreign key (FK_MANAGER_ID)
  references pe_manager (ID);
-- Add/modify columns 
alter table PE_JOB add flag_BAK VARCHAR2(50);
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_JOB
  add constraint FK_PE_JOB_bak foreign key (FLAG_BAK)
  references enum_const (ID);
-- Add/modify columns 
alter table PE_JOB rename column FK_JOBPRIORITY to FK_JOB_PRIORITY;
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_JOB
  drop constraint FK_PE_JOB_PRI;
alter table PE_JOB
  add constraint FK_PE_JOB_PRI foreign key (FK_JOB_PRIORITY)
  references ENUM_CONST (ID);
  -- Add/modify columns 
alter table PE_JOB add SCOPE_STRING clob;
-- Add comments to the columns 
comment on column PE_JOB.SCOPE_STRING
  is '人员范围';
-- Drop columns 
alter table PR_JOB_UNIT drop column FK_JOB_STATUS;
alter table PR_JOB_UNIT add name VARCHAR2(50);
alter table PR_JOB_UNIT add create_date date;
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_JOB_UNIT
  drop constraint FK_PR_JOB_U_REFERENCE_ENUM_CON;

  
update pe_pri_category t set t.path='/entity/information/peJob_showAddJob.action' where t.id='4028808c2a8d8500012a8d882acb0001';
update pe_priority t set t.action='peJob' where t.id='4028808c2a89cfcf012a8a3fbd130003';
-- Add/modify columns 
alter table PR_JOB_UNIT rename column FK_JOB_CHECK to FK_JOB_STATUS;
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_JOB_UNIT
  drop constraint FK_PR_JOB_U_REFERENCE_ENUM_CO2;
alter table PR_JOB_UNIT
  add constraint FK_PR_JOB_U_REFERENCE_ENUM_CO2 foreign key (FK_JOB_STATUS)
  references ENUM_CONST (ID);
alter table PE_SUBJECT
  add constraint AK_PE_SUBJECT_NAME unique (NAME);
alter table PE_SUBJECT
  add constraint AK_PE_SUBJECT_CODE unique (CODE);
alter table PE_UNIT
  add constraint AK_PE_UNIT_NAME unique (NAME);
alter table PE_UNIT
  add constraint AK_PE_UNIT_CODE unique (CODE);
alter table PE_PRO_APPLYNO
  add constraint AK_PE_PRO_APPLYNO_NAME unique (NAME);
alter table PE_PRO_APPLYNO
  add constraint AK_PE_PRO_APPLYNO_CODE_YEAR unique (CODE, YEAR);

alter table PE_TRAIN_EXPERT modify NOTE VARCHAR2(200);
alter table PE_VALUA_EXPERT modify NOTE VARCHAR2(200);

alter table PE_BULLETIN rename column SCOPE_STRING to SCOPE_STRING1;
alter table PE_BULLETIN add SCOPE_STRING clob;
update  pe_bulletin t set  t.scope_string= t.scope_string1;
alter table PE_BULLETIN drop column SCOPE_STRING1;

alter table pe_meeting rename column RECEIPR_UNIT to RECEIPR_UNIT1;
alter table pe_meeting add RECEIPR_UNIT clob;
update  pe_meeting t set  t.RECEIPR_UNIT= t.RECEIPR_UNIT1;
alter table pe_meeting drop column RECEIPR_UNIT1;

alter table pe_meeting rename column RESOURCE_UNIT to RESOURCE_UNIT1;
alter table pe_meeting add RESOURCE_UNIT clob;
update  pe_meeting t set  t.RESOURCE_UNIT= t.RESOURCE_UNIT1;
alter table pe_meeting drop column RESOURCE_UNIT1;
alter table PE_FEE_BUDGET modify PERSON_COUNT VARCHAR2(300);
alter table PE_FEE_ACTUAL_BUDGET modify PERSON_COUNT VARCHAR2(300);

alter table PR_VOTE_RECORD add content clob;
comment on column PR_VOTE_RECORD.content
  is '填写内容';
alter table PR_VOTE_RECORD
  drop constraint FK_PR_VOTE_TRAINEE;
alter table PR_VOTE_RECORD
  add constraint FK_PR_RECORD_VOTE_PAPER foreign key (FK_VOTE_PAPER_ID)
  references pe_vote_paper (ID);
-- Add/modify columns 
alter table PR_VOTE_ANSWER rename column VOTETEACHERANSWER to ANSWER1;
alter table PR_VOTE_ANSWER modify ANSWER1 NUMBER(8);
-- Add comments to the columns 
comment on column PR_VOTE_ANSWER.ANSWER1
  is '';

alter table PE_PROVINCE
  add constraint AK_PE_PROVINCE_NAME unique (NAME);
alter table PE_PROVINCE
  add constraint AK_PE_PROVINCE_CODE unique (CODE);
alter table PE_PRO_APPLYNO modify FEE_STANDARD NUMBER(8,2);
alter table PE_COURSE_PLAN modify COMMENTS VARCHAR2(200);
alter table PE_PRO_APPLYNO modify FK_PROGRAM_TYPE not null;
alter table PE_PRO_APPLYNO modify FK_PROVINCE_CHECK not null;
alter table PR_VOTE_SUGGEST
  add constraint FK_PR_VOTE__REFERENCE_ENUM1 foreign key (FLAG_CHECK)
  references enum_const (ID);
alter table PR_VOTE_SUGGEST
  add constraint FK_PR_VOTE__REFERENCE_ENUM2 foreign key (FLAG_BAK)
  references enum_const (ID);
 --一 密码
insert into pr_pri_role values(sys_guid(),'ff8080812b493236012b50c139cc02cf','402880962a5be9fc012a5c0cd13e0010','1');
insert into pr_pri_role values(sys_guid(),'930D9DCF61CEA81CE040007F01005BCD','402880962a5be9fc012a5c0cd13e0010','1');
---- 任务
insert into pr_pri_role values(sys_guid(),'ff8080812b493236012b50c139cc02cf','4028808c2a9ea9e7012a9eb2b1d10009','1');
insert into pr_pri_role values(sys_guid(),'930D9DCF61CEA81CE040007F01005BCD','4028808c2a9ea9e7012a9eb2b1d10009','1');

alter table PE_COURSE_PLAN modify WORK_PLACE VARCHAR2(100);
---一   --以上均已添加的服务器1109

alter table PR_VOTE_ANSWER add ANSWER16 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER17 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER18 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER19 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER20 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER21 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER22 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER23 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER24 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER25 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER26 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER27 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER28 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER29 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER30 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER31 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER32 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER33 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER34 NUMBER(8);
alter table PR_VOTE_ANSWER add ANSWER35 NUMBER(8);

alter table PR_VOTE_QUESTION add ITEM16 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM17 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM18 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM19 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM20 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM21 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM22 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM23 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM24 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM25 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM26 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM27 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM28 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM29 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM30 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM31 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM32 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM33 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM34 VARCHAR2(50);
alter table PR_VOTE_QUESTION add ITEM35 VARCHAR2(50);

delete from pr_vote_question where flag_question_type not in (select id from enum_const);
alter table PR_VOTE_QUESTION
  add constraint FK_PR_VOTE_ENUMCONST1 foreign key (FLAG_QUESTION_TYPE)
  references enum_const (ID);
 --------以上均已添加到服务器-----
-- Create table
create table PE_VOTE_DETAIL
(
  ID     varchar2(50) not null,
  ITEM1  varchar2(1000),
  ITEM2  varchar2(1000),
  ITEM3  varchar2(1000),
  ITEM4  varchar2(1000),
  ITEM5  varchar2(1000),
  ITEM6  varchar2(1000),
  ITEM7  varchar2(1000),
  ITEM8  varchar2(1000),
  ITEM9  varchar2(1000),
  ITEM10 varchar2(1000),
  ITEM11 varchar2(1000),
  ITEM12 varchar2(1000),
  ITEM13 varchar2(1000),
  ITEM14 varchar2(1000),
  ITEM15 varchar2(1000),
  ITEM16 varchar2(1000),
  ITEM17 varchar2(1000),
  ITEM18 varchar2(1000),
  ITEM19 varchar2(1000),
  ITEM20 varchar2(1000),
  ITEM21 varchar2(1000),
  ITEM22 varchar2(1000),
  ITEM23 varchar2(1000),
  ITEM24 varchar2(1000),
  ITEM25 varchar2(1000),
  ITEM26 varchar2(1000),
  ITEM27 varchar2(1000),
  ITEM28 varchar2(1000),
  ITEM29 varchar2(1000),
  ITEM30 varchar2(1000),
  ITEM31 varchar2(1000),
  ITEM32 varchar2(1000),
  ITEM33 varchar2(1000),
  ITEM34 varchar2(1000),
  ITEM35 varchar2(1000),
  ITEM36 varchar2(1000),
  ITEM37 varchar2(1000),
  ITEM38 varchar2(1000),
  ITEM39 varchar2(1000),
  ITEM40 varchar2(1000),
  ITEM41 varchar2(1000),
  ITEM42 varchar2(1000),
  ITEM43 varchar2(1000),
  ITEM44 varchar2(1000),
  ITEM45 varchar2(1000),
  ITEM46 varchar2(1000),
  ITEM47 varchar2(1000),
  ITEM48 varchar2(1000),
  ITEM49 varchar2(1000),
  ITEM50 varchar2(1000)
)
tablespace TCHR_TRAINING1
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table PE_VOTE_DETAIL
  is '问卷详情';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_VOTE_DETAIL
  add constraint PK_PE_VOTE_DETAIL primary key (ID);

alter table PR_VOTE_RECORD add FK_VOTE_DETAIL varchar2(50);
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_VOTE_RECORD
  add constraint FK_PE_VOTE_DETAIL foreign key (FK_VOTE_DETAIL)
  references pe_vote_detail (ID);

alter table PR_VOTE_RECORD add FK_VOTE_SUGGEST varchar2(50);
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_VOTE_RECORD
  add constraint FK_PR_RECORD_VOTE_SUGGEST foreign key (FK_VOTE_SUGGEST)
  references pr_vote_suggest (ID);
alter table PE_VOTE_PAPER
  add constraint PK_VOTE_R_APPLYNO foreign key (FLAG_TYPE)
  references pe_pro_applyno (ID);
alter table PR_VOTE_QUESTION add sequences_NO number;
insert into pe_pri_category values(sys_guid(),'查看投票记录',(select id from pe_pri_category where code='09'),'0904','/entity/information/prVoteRecord_searchToVoteRecord.action','1');
insert into pe_priority values(sys_guid(),'查看投票记录_*',(select id from pe_pri_category where code='0904'),'/entity/information','prVoteRecord','*');
insert into pr_pri_role values(sys_guid(),'402880a92137be1c012137db62100008',(select id from pe_priority where name = '查看投票记录_*'),'1');
insert into pr_pri_role values(sys_guid(),'402880a92137be1c012137db62100007',(select id from pe_priority where name = '查看投票记录_*'),'1');

--2011-07-19 韩高升 增加短信、邮件查询功能
insert into pe_subject (ID, NAME, CODE, NOTE)
values ('40288ab33126389a0131266db1ae0001', '自动申报项目', '00', '自动申报项目时使用本学科');

alter table pe_fee_actual_budget add input_date date;
alter table pe_fee_budget add input_date date;
alter table pr_pro_summary add input_date date;

create table pe_email_history
(
  id             varchar2(50) not null,
  SENDER_ID      varchar2(50),
  RECEIVER_ID    varchar2(50),
  RECEIVER_email varchar2(60),
  SEND_TIME      date,
  title          varchar2(100),
  content        clob,
  attachments    clob
)
;
-- Add comments to the table 
comment on table pe_email_history
  is '已发邮件表';
alter table PE_EMAIL_HISTORY add constraint PK_PE_EMAIL_HISTORY primary key (ID);
-- Add comments to the columns 
comment on column pe_email_history.SENDER_ID
  is '邮件发送者id';
comment on column pe_email_history.RECEIVER_ID
  is '邮件接收者id';
comment on column pe_email_history.RECEIVER_email
  is '邮件接收者邮箱';
comment on column pe_email_history.SEND_TIME
  is '邮件发送时间';
comment on column pe_email_history.title
  is '邮件主题';
comment on column pe_email_history.content
  is '邮件内容';
comment on column pe_email_history.attachments
  is '附件';

insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ab33141486e013141766ae20003', '短信邮件查询', '402880962b2cbd08012b2ccea8150001', '0804', 'to_top_menu', '1');

insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ab33141486e013141781acd0005', '专家短信查询', '40288ab33141486e013141766ae20003', '080401', '/entity/information/msg4PeValueExpertSearch.action', '0');

insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ab33141486e01314179507a0007', '联系人短信查询', '40288ab33141486e013141766ae20003', '080402', '/entity/information/msg4PeManagerSearch.action', '0');

insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ab33141486e0131417a08120009', '学员短信查询', '40288ab33141486e013141766ae20003', '080403', '/entity/information/msg4PeTraineeSearch.action', '0');

insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ab33141486e0131418018ce0011', '专家邮件查询', '40288ab33141486e013141766ae20003', '080404', '/entity/information/email4PeValueExpertSearch.action', '0');

insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ab33141486e01314180e1230013', '联系人邮件查询', '40288ab33141486e013141766ae20003', '080405', '/entity/information/email4PeManagerSearch.action', '0');

insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ab33141486e013141819d950015', '学员邮件查询', '40288ab33141486e013141766ae20003', '080406', '/entity/information/email4PeTraineeSearch.action', '0');

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ab33141486e0131417bcf82000b', '专家短信查询_*', '40288ab33141486e013141781acd0005', '/entity/information', 'msg4PeValueExpertSearch', '*');

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ab33141486e0131417cdd09000d', '联系人短信查询_*', '40288ab33141486e01314179507a0007', '/entity/information', 'msg4PeManagerSearch', '*');

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ab33141486e0131417d89f5000f', '学员短信查询_*', '40288ab33141486e0131417a08120009', '/entity/information', 'msg4PeTraineeSearch', '*');

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ab33141486e01314182b98b0017', '专家邮件查询_*', '40288ab33141486e0131418018ce0011', '/entity/information', 'email4PeValueExpertSearch', '*');

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ab33141486e0131418366b00019', '联系人邮件查询_*', '40288ab33141486e01314180e1230013', '/entity/information', 'email4PeManagerSearch', '*');

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ab33141486e013141841f93001b', '学员邮件查询_*', '40288ab33141486e013141819d950015', '/entity/information', 'email4PeTraineeSearch', '*');

insert into pr_pri_role (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ab33141486e01314184e17c001e', '402880a92137be1c012137db62100008', '40288ab33141486e0131417bcf82000b', '1');

insert into pr_pri_role (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ab33141486e01314184e1ba001f', '402880a92137be1c012137db62100008', '40288ab33141486e0131417cdd09000d', '1');

insert into pr_pri_role (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ab33141486e01314184e1d40020', '402880a92137be1c012137db62100008', '40288ab33141486e0131417d89f5000f', '1');

insert into pr_pri_role (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ab33141486e01314184e1f60021', '402880a92137be1c012137db62100008', '40288ab33141486e01314182b98b0017', '1');

insert into pr_pri_role (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ab33141486e01314184e20c0022', '402880a92137be1c012137db62100008', '40288ab33141486e0131418366b00019', '1');

insert into pr_pri_role (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ab33141486e01314184e21d0023', '402880a92137be1c012137db62100008', '40288ab33141486e013141841f93001b', '1');

alter table pe_pro_implemt add last_upload_date date;
comment on column pe_pro_implemt.last_upload_date
  is '简报最后上传时间';
alter table pr_pro_summary add last_upload_date date;
comment on column pr_pro_summary.last_upload_date
  is '其他材料最后上传时间';

--2011-07-22 整理权限
update pe_priority set name='参训学员' where id='402880a92af013c1012af02056120007';
update pe_priority set name='查看初审意见' where id='402880962a8a6a4f012a8a88817e0016';
update pe_priority set name='查看培训项目' where id='402880962a6a744b012a6a7b26340003';
update pe_priority set name='查看申报结果' where id='402880962a9d18bc012a9d88a94f0003';
update pe_priority set name='查看投票记录' where id='965CCFB4AC24C62DE040007F01007217';
update pe_priority set name='查看终审意见' where id='402880962a9df74f012a9e1fcc0b0008';
update pe_priority set name='调查问卷列表' where id='4028808c2a55aaae012a55b237b20004';
update pe_priority set name='发布会议通知' where id='4028808c2a5ac9ea012a5b4db8480013';
update pe_priority set name='分配任务' where id='4028808c2a89cfcf012a8a3fbd130003';
update pe_priority set name='分配专家' where id='402880962a8a6a4f012a8a723ffc0003';
update pe_priority set name='个人信息查看' where id='402880a92b29f226012b2a1f0a9a0007';
update pe_priority set name='工作简报' where id='402880962aa6c6c3012aa6ed8e91000e';
update pe_priority set name='管理员评审' where id='40288ab331022abe0131023e69c60003';
update pe_priority set name='会议通知列表' where id='4028808c2a5ac9ea012a5b4eba760014';
update pe_priority set name='计划学员' where id='402880a92af013c1012af01fd4bd0005';
update pe_priority set name='决算表' where id='402880962aa6c6c3012aa6f807c80018';
update pe_priority set name='决算详情' where id='402880e72ab384a4012ab3862b4d0003';
update pe_priority set name='开班时间' where id='402880962aa27352012aa2b23087000b';
update pe_priority set name='开班通知' where id='402880962aa6c6c3012aa6e59f510006';
update pe_priority set name='联系人短信查询' where id='40288ab33141486e0131417cdd09000d';
update pe_priority set name='联系人邮件查询' where id='40288ab33141486e0131418366b00019';
update pe_priority set name='培训日程' where id='402880962aa6c6c3012aa6eafe13000a';
update pe_priority set name='培训项目管理' where id='402880962a5be9fc012a5bfd37d50004';
update pe_priority set name='其他材料' where id='402880962adbd5a9012adc37e6c10005';
update pe_priority set name='任务列表' where id='4028808c2a8d8500012a8d8a63180006';
update pe_priority set name='提交任务材料' where id='402880962b23ac6b012b23d5a0b70002';
update pe_priority set name='提交申报材料' where id='402880962a74dc6a012a74f9c09e0003';
update pe_priority set name='添加调查问卷' where id='4028808c2a55aaae012a55b10e930003';
update pe_priority set name='通知公告列表' where id='4028808c2a5ac9ea012a5ad61a360005';
update pe_priority set name='推荐学员' where id='402880962a8d6e0b012a8d706ab70002';
update pe_priority set name='系统公告' where id='4028808c2a65b0fb012a65b3acab0003';
update pe_priority set name='项目初审' where id='402880962a8a6a4f012a8a7ec4e1000c';
update pe_priority set name='项目审核' where id='402880962a5be9fc012a5c0cd13e0009';
update pe_priority set name='项目终审' where id='402880962a8a6a4f012a8a83084f0010';
update pe_priority set name='学员短信查询' where id='40288ab33141486e0131417d89f5000f';
update pe_priority set name='学员邮件查询' where id='40288ab33141486e013141841f93001b';
update pe_priority set name='预算表' where id='402880962aa27352012aa2b32612000d';
update pe_priority set name='预算详情' where id='402880e72ab2e988012ab2f2a9c40006';
update pe_priority set name='专家短信查询' where id='40288ab33141486e0131417bcf82000b';
update pe_priority set name='专家邮件查询' where id='40288ab33141486e01314182b98b0017';
update pe_priority set name='总结报告' where id='402880962aa6c6c3012aa6fcb79c0022';
update pe_priority set name = '学员短信与邮件管理' where id = '402880962b2cbd08012b2cd6cd2a0009';
update pe_priority set name = '联系人短信与邮件管理' where id = '402880a92ac1e9eb012ac23c01ed000a';
update pe_priority set name = '评审专家短信与邮件管理' where id = '402880a92ac2505f012ac25c15f60005';
update pe_priority set name = '材料一' where id = '402880962ac11ee2012ac14140240003';
update pe_priority set name = '查看其他材料' where id = '402880962ae1635d012ae1654f030001';
update pe_priority set name = '查看其他材料' where id = '402880962ae5a943012ae5b2976b0003';
update pe_priority set name = '查看培训日程' where id = '402880962ac64766012ac662feaf0001';
update pe_priority set name = '联系人信息管理' where id = '402880142a0ff6b8012a0ffa82060006';
update pe_priority set name = '培训教学专家查询' where id = '402880142a0ff6b8012a0ffa82060008';
update pe_priority set name = '联系人信息修改' where id = '402880142a0ff6b8012a0ffa82060009';
update pe_priority set name = '培训教学专家管理' where id = '402880142a0ff6b8012a0ffa82060007';
update pe_priority set name = '联系人密码修改' where id = '402880962a5be9fc012a5c0cd13e0010';
update pe_priority set name = '评审评估专家管理' where id = '402880142a0ff6b8012a0ffa82060013';
update pe_priority set name = '通知公告列表' where id = '4028808c2a5ac9ea012a5ad862510007';
update pe_priority set name = '任务状态列表' where id = '4028808c2a9ea9e7012a9eb2b1d10009';
update pe_priority set name = '查看简报' where id = '402880962adc6b46012adc8b8a8f0001';
update pe_priority set name = '设置申报单位' where id = '402880962a6425fe012a642954f30001';
update pe_priority set name = '查看、指定评审专家' where id = '402880962a8d6e0b012a8d706ab70001';
update pe_priority set name = '参会人员管理' where id = '4028808c2aa97759012aa97f946d0009';
update pe_priority set name = '查看培训日程' where id = '402880962aae7010012aae7357f90001';
update pe_priority set name = '查看工作简报' where id = '402880962ae55f41012ae5833b460002';
update pe_priority set name = '材料二' where id = '402880962ae55f41012ae594beeb0012';
update pe_priority set name = '查看老师和专题' where id = '4028808c2ae5d30e012ae5d8fdcd0001';
update pe_priority set name = '联系人信息修改_显示信息' where id = '402880a92b29f226012b2a0ba8520001';
update pe_priority set name = '项目执行办项目审核' where id = '402880962a8960e0012a896e21f50001';


-- Add/modify columns 
alter table PE_PRO_IMPLEMT add course_modify_date date;
-- Add comments to the columns 
comment on column PE_PRO_IMPLEMT.course_modify_date
  is '课程最后上传时间';
  
 create or replace trigger update_upload_date
 after update or insert  on pe_course_plan for each row
begin
if :new.FK_PRO_APPLY is not null  then
  update pe_pro_implemt t set t.course_modify_date = sysdate where t.fk_pro_apply=:new.FK_PRO_APPLY;
end if;
end;

-- Add/modify columns 
alter table PR_VOTE_QUESTION add item36 varchar2(50);
alter table PR_VOTE_QUESTION add item37 varchar2(50);
alter table PR_VOTE_QUESTION add item38 varchar2(50);
alter table PR_VOTE_QUESTION add item39 varchar2(50);
alter table PR_VOTE_QUESTION add item40 varchar2(50);

create index INDEX_SEQUENCES_NO on PR_VOTE_QUESTION (sequences_no);

alter table PR_VOTE_ANSWER add answer36 NUMBER(8);
alter table PR_VOTE_ANSWER add answer37 NUMBER(8);
alter table PR_VOTE_ANSWER add answer38 NUMBER(8);
alter table PR_VOTE_ANSWER add answer39 NUMBER(8);
alter table PR_VOTE_ANSWER add answer40 NUMBER(8);

-- Create table
create table MAIN_TEACHING_GRADE
(
  id   varchar2(50) not null,
  name varchar2(100)
)
tablespace TCHR_TRAINING1
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column MAIN_TEACHING_GRADE.name
  is '主要教学学段';
-- Create/Recreate primary, unique and foreign key constraints 
alter table MAIN_TEACHING_GRADE
  add constraint PK_MAIN_TEACHING_GRADE primary key (ID);
alter table MAIN_TEACHING_GRADE
  add constraint AK_MAIN_TEACHING_GRADE_NAME unique (NAME);

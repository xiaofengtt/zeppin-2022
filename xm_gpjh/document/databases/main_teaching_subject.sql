-- Create table
create table MAIN_TEACHING_SUBJECT
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
-- Create/Recreate primary, unique and foreign key constraints 
alter table MAIN_TEACHING_SUBJECT
  add constraint PK_MAIN_TEACHING_SUBJECT primary key (ID);
alter table MAIN_TEACHING_SUBJECT
  add constraint AK_MAIN_TEACHING_SUBJECT_NAME unique (NAME);

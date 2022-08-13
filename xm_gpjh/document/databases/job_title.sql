-- Create table
create table JOB_TITLE
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
comment on column JOB_TITLE.name
  is 'Ö°³Æ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table JOB_TITLE
  add constraint PK_JOB_TITLE primary key (ID);
alter table JOB_TITLE
  add constraint AK_JOB_TITLE unique (NAME);

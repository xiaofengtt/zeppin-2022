-- Create table
create table FOLK
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
comment on column FOLK.name
  is 'Ãñ×å';
-- Create/Recreate primary, unique and foreign key constraints 
alter table FOLK
  add constraint PK_FOLK primary key (ID);
alter table FOLK
  add constraint AK_FOLK_NAME unique (NAME);

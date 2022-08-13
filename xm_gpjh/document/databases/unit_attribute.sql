-- Create table
create table UNIT_ATTRIBUTE
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
comment on column UNIT_ATTRIBUTE.name
  is '单位属性名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table UNIT_ATTRIBUTE
  add constraint PK_UNIT_ATTRIBUTE primary key (ID);
alter table UNIT_ATTRIBUTE
  add constraint AK_UNIT_ATTRIBUTE_NAME unique (NAME);

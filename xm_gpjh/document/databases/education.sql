-- Create table
create table EDUCATION
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
comment on column EDUCATION.name
  is 'Ñ§Àú';
-- Create/Recreate primary, unique and foreign key constraints 
alter table EDUCATION
  add constraint PK_EDUCATION primary key (ID);
alter table EDUCATION
  add constraint AK_EDUCATION_NAME unique (NAME);

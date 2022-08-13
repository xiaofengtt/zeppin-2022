-- Create table
create table CITY
(
  id          varchar2(50) not null,
  name        varchar2(100),
  code        varchar2(50),
  note        clob,
  fk_province varchar2(50)
)
tablespace TCHR_TRAINING1
  pctfree 10
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
alter table CITY
  add constraint PK_CITY primary key (ID);
alter table CITY
  add constraint AK_CITY_CODE unique (CODE);
alter table CITY
  add constraint AK_CITY_NAME unique (NAME);
alter table CITY
  add constraint FK_PE_PROVINCE_ID foreign key (FK_PROVINCE)
  references pe_province (ID);

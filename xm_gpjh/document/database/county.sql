-- Create table
create table COUNTY
(
  id             varchar2(50) not null,
  name           varchar2(100),
  code           varchar2(50),
  note           clob,
  fk_city        varchar2(50),
  is_poor        varchar2(50),
  is_countrypoor varchar2(50)
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
-- Add comments to the columns 
comment on column COUNTY.is_poor
  is '是否是贫困县';
comment on column COUNTY.is_countrypoor
  is '是否是国家贫困县';
-- Create/Recreate primary, unique and foreign key constraints 
alter table COUNTY
  add constraint PK_COUNTY primary key (ID);
alter table COUNTY
  add constraint AK_COUNTY_CODE unique (CODE);
alter table COUNTY
  add constraint AK_COUNTY_NAME unique (NAME);
alter table COUNTY
  add constraint FK_CITY_ID foreign key (FK_CITY)
  references city (ID);

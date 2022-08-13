prompt PL/SQL Developer import file
prompt Created on 2016年5月20日 by Administrator
set feedback off
set define off
prompt Dropping UNIT_ATTRIBUTE...
drop table UNIT_ATTRIBUTE cascade constraints;
prompt Creating UNIT_ATTRIBUTE...
create table UNIT_ATTRIBUTE
(
  id   VARCHAR2(50) not null,
  name VARCHAR2(100)
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
comment on column UNIT_ATTRIBUTE.name
  is '单位属性名称';
alter table UNIT_ATTRIBUTE
  add constraint PK_UNIT_ATTRIBUTE primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table UNIT_ATTRIBUTE
  add constraint AK_UNIT_ATTRIBUTE_NAME unique (NAME)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for UNIT_ATTRIBUTE...
alter table UNIT_ATTRIBUTE disable all triggers;
prompt Loading UNIT_ATTRIBUTE...
insert into UNIT_ATTRIBUTE (id, name)
values ('402881c554c8257b0154c82dd22c0042', '城市教师');
insert into UNIT_ATTRIBUTE (id, name)
values ('402881c554c8257b0154c82df5720044', '县城所在地教师');
insert into UNIT_ATTRIBUTE (id, name)
values ('402881c554c8257b0154c82e14110046', '镇区教师');
insert into UNIT_ATTRIBUTE (id, name)
values ('402881c554c8257b0154c82e3c240048', '乡教师');
insert into UNIT_ATTRIBUTE (id, name)
values ('402881c554c8257b0154c82e5d70004a', '村教师');
insert into UNIT_ATTRIBUTE (id, name)
values ('402881c554c8257b0154c82e7df4004c', '教学点教师');
commit;
prompt 6 records loaded
prompt Enabling triggers for UNIT_ATTRIBUTE...
alter table UNIT_ATTRIBUTE enable all triggers;
set feedback on
set define on
prompt Done.

-- Create/Recreate indexes 
create index AK_FK_STATUS_TRAINING001 on PE_TRAINEE (fk_status_training);
create index AK_FK_TRAINING_UNIT_001 on PE_TRAINEE (fk_unit_from);
create index AK_FK_TRAINING_UNIT_002 on PE_TRAINEE (fk_training_unit);
create index AK_FK_PE_PROVINCE_001 on PE_TRAINEE (fk_province);
create index AK_FK_PE_CITY_001 on PE_TRAINEE (fk_city);
create index AK_FK_CHECKSTATUS_001 on PE_TRAINEE (fk_checked_trainee);

drop index AK_FK_CHECKSTATUS_001;
drop index AK_FK_TRAINING_UNIT_001;
drop index AK_FK_TRAINING_UNIT_002;
drop index AK_FK_PE_PROVINCE_001;
drop index AK_FK_PE_CITY_001;
drop index AK_FK_STATUS_TRAINING001;
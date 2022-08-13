-- Create table
create table PR_MAN_PRONO
(
  ID            VARCHAR2(50) not null,
  FK_MANAGER_ID VARCHAR2(50),
  FK_PRONO_ID   VARCHAR2(50)
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
comment on table PR_MAN_PRONO
  is '项目权限表';
-- Add comments to the columns 
comment on column PR_MAN_PRONO.FK_MANAGER_ID
  is '管理员id';
comment on column PR_MAN_PRONO.FK_PRONO_ID
  is '项目id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PR_MAN_PRONO
  add constraint PK_PR_MAN_PRONO primary key (ID)
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
alter table PR_MAN_PRONO
  add constraint FK_MAN_PRO_REF_MANAGER foreign key (FK_MANAGER_ID)
  references PE_MANAGER (ID);
alter table PR_MAN_PRONO
  add constraint FK_MAN_PRO_REF_PRONO foreign key (FK_PRONO_ID)
  references PE_PRO_APPLYNO (ID);

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ab33103e18d013103e99e0a0003', '分配项目管理员*', '40288ab33103e18d013103e8fc840001', '/entity/basic', 'peProApplyPriManager', '*');

insert into pr_pri_role (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ab33103e18d013103eb8f2a0007', '402880a92137be1c012137db62100008', '40288ab33103e18d013103e99e0a0003', '1');

commit;

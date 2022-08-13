insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ab331022abe0131023d37450001', '管理员评审', '402880962a8a6a4f012a8a6d6a310001', '0606', '/entity/programApply/managerJudgmentAction.action', '1');

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ab331022abe0131023e69c60003', '管理员评审_*', '40288ab331022abe0131023d37450001', '/entity/programApply', 'managerJudgmentAction', '*');

insert into pr_pri_role (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ab331022abe0131023f9c570005', '402880a92137be1c012137db62100008', '40288ab331022abe0131023e69c60003', '1');


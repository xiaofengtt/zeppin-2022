insert into organizaiton(id, type, name) values(1,1,'果实网');
insert into role(id, name, status) values(1, '超级管理员', 1);
insert into role(id, name, status) values(2, '运营管理者', 1);
insert into role(id, name, status) values(3, '果实网编辑', 1);
insert into role(id, name, status) values(4, '合作机构负责人', 1);
insert into role(id, name, status) values(5, '合作机构编辑', 1);
insert into sys_user(id, email, phone, name, role, password, organization, status, creater) values(1,'admin@guoshi.com','18866666666','果实淘宝',1,'666666',1,1,1);
insert into strategy_type(id, name) values(1,'学习目标');
insert into strategy_type(id, name) values(2,'学习建议');
alter table sso_user_test_item_count add column is_wrongbook_item tinyint(4) NULL DEFAULT '0';
alter table sso_user_test_item_count add column is_wrongbook_item_tested tinyint(4) NULL DEFAULT '0';
alter table sso_user_test_item_count add column is_wrongbook_item_mastered tinyint(4)  NULL DEFAULT '0';

update sso_user_test_item_count set is_wrongbook_item=0,is_wrongbook_item_tested=0,is_wrongbook_item_mastered=0;

alter table sso_user_test_item_count modify column is_wrongbook_item tinyint(4) NOT NULL DEFAULT '0';
alter table sso_user_test_item_count modify column is_wrongbook_item_tested tinyint(4) NOT NULL DEFAULT '0';
alter table sso_user_test_item_count modify column is_wrongbook_item_mastered tinyint(4)  NOT NULL DEFAULT '0';

alter table sso_user_test_item_count add column wrongbook_item_createtime datetime NULL;
alter table sso_user_test_item_count add column wrongbook_item_testtime datetime NULL;
alter table sso_user_test_item_count add column wrongbook_item_mastertime datetime NULL;

--subject_item_type表中新增字段PROPORTION
alter table subject_item_type add PROPORTION float(11,1) NOT NULL DEFAULT '0.0' COMMENT '占总分的百分比';
alter table subject_item_type  add RELEASED_ITEMCOUNT int(11) NOT NULL DEFAULT '0' COMMENT '该学科下该题型的总数';

update item set is_group=0;
update item i set i.is_group=1 where i.level=2;
update item set is_group=1 where id in (select id from (select distinct aa.parent id from item aa  where aa.level=2 ) b);

alter table item_type drop column is_group;
alter table item add column MODELTYPE tinyint(4) NOT NULL DEFAULT 1;

update item i set i.modeltype=4 where i.level=1 and i.is_group=1;
update item i set i.modeltype = (select it.modeltype from item_type it where it.id=i.type) where (i.level=1 and i.is_group=0) or (i.level=2 and i.is_group=1);


CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(20) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_KEY` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

update version v set v.version='1.2.0' where v.version='1.2'
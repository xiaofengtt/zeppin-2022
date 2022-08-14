alter table `exam_room` add column `ROOM_TYPE2` varchar(50) DEFAULT '' COMMENT '考场类型，通过excel导入，内容自定义'

alter table `exam_room` add column `INVIGILATION_NOTICE1` text COMMENT '考场对应的监考注意事项'
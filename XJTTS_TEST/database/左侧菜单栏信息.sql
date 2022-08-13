INSERT INTO `fun_category` VALUES ('33', '自主报名教师信息管理', null, '', '1', '1');
INSERT INTO `fun_category` VALUES ('34', '教师基本信息审核管理', '33', '../admin/teacherInfo_initPage.action', '2', '1');
INSERT INTO `fun_category` VALUES ('35', '自主报名学员审核管理', '33', '../admin/ttRecord_initReviewPage.action', '2', '1');
INSERT INTO `fun_category` VALUES ('36', '自主报名学员信息确认', '33', '../admin/trainingStudentOpt_initConfirmPage.action', null, null);

INSERT INTO `orga_cate_map` VALUES ('57', '33', '1', '1');
INSERT INTO `orga_cate_map` VALUES ('58', '33', '2', '1');
INSERT INTO `orga_cate_map` VALUES ('59', '33', '3', '1');
INSERT INTO `orga_cate_map` VALUES ('60', '33', '4', '1');
INSERT INTO `orga_cate_map` VALUES ('61', '34', '4', '1');
INSERT INTO `orga_cate_map` VALUES ('62', '34', '3', '1');
INSERT INTO `orga_cate_map` VALUES ('63', '34', '2', '1');
INSERT INTO `orga_cate_map` VALUES ('64', '34', '1', '1');
INSERT INTO `orga_cate_map` VALUES ('65', '35', '1', '1');
INSERT INTO `orga_cate_map` VALUES ('66', '35', '2', '1');
INSERT INTO `orga_cate_map` VALUES ('67', '35', '3', '1');
INSERT INTO `orga_cate_map` VALUES ('68', '35', '4', '1');
INSERT INTO `orga_cate_map` VALUES ('69', '36', null, '2');
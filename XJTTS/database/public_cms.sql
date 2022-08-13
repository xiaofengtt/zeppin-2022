/*
Navicat MySQL Data Transfer

Source Server         : xjjspxgl.zeppin.cn
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : public_cms

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-12-14 21:46:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cms_category`
-- ----------------------------
DROP TABLE IF EXISTS `cms_category`;
CREATE TABLE `cms_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父分类ID',
  `type_id` int(11) DEFAULT NULL COMMENT '分类类型',
  `child_ids` text COMMENT '所有子分类ID',
  `tag_type_ids` text COMMENT '标签分类',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `template_path` varchar(255) DEFAULT NULL COMMENT '模板路径',
  `path` varchar(2000) NOT NULL COMMENT '首页路径',
  `only_url` tinyint(1) NOT NULL COMMENT '外链',
  `has_static` tinyint(1) NOT NULL COMMENT '已经静态化',
  `url` varchar(2048) DEFAULT NULL COMMENT '首页地址',
  `content_path` varchar(500) DEFAULT NULL COMMENT '内容路径',
  `page_size` int(11) DEFAULT NULL COMMENT '每页数据条数',
  `allow_contribute` tinyint(1) NOT NULL COMMENT '允许投稿',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `hidden` tinyint(1) NOT NULL COMMENT '隐藏',
  `disabled` tinyint(1) NOT NULL COMMENT '是否删除',
  `contents` int(11) NOT NULL DEFAULT '0' COMMENT '内容数',
  `extend_id` int(11) DEFAULT NULL COMMENT '扩展ID',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `disabled` (`disabled`),
  KEY `sort` (`sort`),
  KEY `site_id` (`site_id`),
  KEY `type_id` (`type_id`),
  KEY `allow_contribute` (`allow_contribute`),
  KEY `hidden` (`hidden`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='分类';

-- ----------------------------
-- Records of cms_category
-- ----------------------------
INSERT INTO `cms_category` VALUES ('1', '1', '演示', null, null, '17,15,12,9,8,7,6,18', '', 'demonstrate', '/category/parent.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/demonstrate/index.html', '${content.publishDate?string(\'yyyy/MM/dd\')}/${content.id}.html', '10', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_category` VALUES ('6', '1', '汽车', '1', null, null, '', 'car', '/category/list.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/car/index.html', '${category.code}/${content.publishDate?string(\'yyyy/MM-dd\')}/${content.id}.html', '10', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_category` VALUES ('7', '1', '社会', '1', null, null, '', 'social', '/category/list.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/social/index.html', '${category.code}/${content.publishDate?string(\'yyyy/MM-dd\')}/${content.id}.html', '10', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_category` VALUES ('8', '1', '美图', '1', null, null, '', 'picture', '/category/list.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/picture/index.html', '${category.code}/${content.publishDate?string(\'yyyy/MM-dd\')}/${content.id}.html', '10', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_category` VALUES ('9', '1', '系统介绍', '1', null, null, '', 'introduction', '/category/list.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/introduction/index.html', '${category.code}/${content.publishDate?string(\'yyyy/MM-dd\')}/${content.id}.html', '10', '0', '0', '0', '0', '2', null);
INSERT INTO `cms_category` VALUES ('12', '1', '文章', '1', null, null, '', 'article', '/category/list.html', '${category.code}/index.html', '0', '0', '//223.202.64.24/cms/article/index.html', '${category.code}/${content.publishDate?string(\'yyyy/MM-dd\')}/${content.id}.html', '20', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_category` VALUES ('11', '1', '测试', null, null, null, null, 'test', '/category/parent.html', '${category.code}/index.html', '0', '0', 'test/index.html', '${content.publishDate?string(\'yyyy/MM/dd\')}/${content.id}.html', '20', '0', '0', '0', '1', '0', null);
INSERT INTO `cms_category` VALUES ('13', '1', '下载', null, null, null, null, 'download', '', 'https://github.com/sanluan/PublicCMS', '0', '0', 'https://github.com/sanluan/PublicCMS', '', '20', '0', '0', '0', '1', '0', null);
INSERT INTO `cms_category` VALUES ('14', '1', '图书', '1', null, null, null, 'book', '/category/parent.html', 'demonstrate/${category.code}/index.html', '0', '0', 'demonstrate/book/index.html', '${content.publishDate?string(\'yyyy/MM/dd\')}/${content.id}.html', '20', '0', '0', '0', '1', '0', null);
INSERT INTO `cms_category` VALUES ('15', '1', '小说', '1', null, null, '', 'novel', '/category/list.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/novel/index.html', '${category.code}/${content.publishDate?string(\'yyyy/MM-dd\')}/${content.id}.html', '20', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_category` VALUES ('16', '1', 'OSChina下载', '13', null, null, null, 'download', '', 'http://git.oschina.net/sanluan/PublicCMS', '0', '0', 'http://git.oschina.net/sanluan/PublicCMS', '', '20', '0', '0', '0', '1', '0', null);
INSERT INTO `cms_category` VALUES ('17', '1', '科技', '1', null, null, '', 'science', '/category/list.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/science/index.html', '${category.code}/${content.publishDate?string(\'yyyy/MM-dd\')}/${content.id}.html', '20', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_category` VALUES ('18', '1', '商品', '1', null, null, '', 'product', '/category/product_list.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/product/index.html', '${category.code}/${content.publishDate?string(\'yyyy/MM-dd\')}/${content.id}.html', '10', '0', '0', '0', '0', '-3', null);
INSERT INTO `cms_category` VALUES ('19', '1', '案例', null, null, null, '', 'case', '/category/parent.html', '${category.code}/index.html', '0', '1', '//www.publiccms.com/case/index.html', '${content.publishDate?string(\'yyyy/MM/dd\')}/${content.id}.html', '20', '0', '0', '0', '0', '2', '2');

-- ----------------------------
-- Table structure for `cms_category_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `cms_category_attribute`;
CREATE TABLE `cms_category_attribute` (
  `category_id` int(11) NOT NULL COMMENT '分类ID',
  `title` varchar(80) DEFAULT NULL COMMENT '标题',
  `keywords` varchar(100) DEFAULT NULL COMMENT '关键词',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='分类扩展';

-- ----------------------------
-- Records of cms_category_attribute
-- ----------------------------
INSERT INTO `cms_category_attribute` VALUES ('3', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('1', '演示', 'PublicCMS,如何使用', 'PublicCMS如何使用', null);
INSERT INTO `cms_category_attribute` VALUES ('2', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('4', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('5', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('6', '汽车 - PublicCMS', '汽车,car', '汽车', null);
INSERT INTO `cms_category_attribute` VALUES ('7', '社会', '社会', '社会', null);
INSERT INTO `cms_category_attribute` VALUES ('8', '美图', '美图,美女', '美图美女', null);
INSERT INTO `cms_category_attribute` VALUES ('9', '系统介绍', 'PublicCMS,系统介绍', 'PublicCMS系统介绍', null);
INSERT INTO `cms_category_attribute` VALUES ('10', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('11', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('12', '文章', '文章', '文章', null);
INSERT INTO `cms_category_attribute` VALUES ('13', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('14', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('15', '小说', '小说,在线阅读', '小说,在线阅读', null);
INSERT INTO `cms_category_attribute` VALUES ('16', null, null, null, '{}');
INSERT INTO `cms_category_attribute` VALUES ('17', '科技', '科技', '科技频道', null);
INSERT INTO `cms_category_attribute` VALUES ('18', '商品', '商品,导购', '商品', null);
INSERT INTO `cms_category_attribute` VALUES ('19', '案例', 'PublicCMS案例', 'PublicCMS案例', null);

-- ----------------------------
-- Table structure for `cms_category_model`
-- ----------------------------
DROP TABLE IF EXISTS `cms_category_model`;
CREATE TABLE `cms_category_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL COMMENT '分类ID',
  `model_id` int(11) NOT NULL COMMENT '模型ID',
  `template_path` varchar(200) DEFAULT NULL COMMENT '内容模板路径',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `model_id` (`model_id`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='分类模型';

-- ----------------------------
-- Records of cms_category_model
-- ----------------------------
INSERT INTO `cms_category_model` VALUES ('1', '9', '1', '/system/article.html');
INSERT INTO `cms_category_model` VALUES ('2', '8', '3', '/system/picture.html');
INSERT INTO `cms_category_model` VALUES ('3', '7', '3', '/system/picture.html');
INSERT INTO `cms_category_model` VALUES ('4', '7', '1', '/system/article.html');
INSERT INTO `cms_category_model` VALUES ('46', '6', '2', '');
INSERT INTO `cms_category_model` VALUES ('6', '12', '2', '');
INSERT INTO `cms_category_model` VALUES ('7', '12', '1', '/system/article.html');
INSERT INTO `cms_category_model` VALUES ('8', '15', '4', '/system/book.html');
INSERT INTO `cms_category_model` VALUES ('9', '15', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('10', '15', '5', '');
INSERT INTO `cms_category_model` VALUES ('11', '9', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('12', '9', '5', '');
INSERT INTO `cms_category_model` VALUES ('13', '9', '3', '/system/picture.html');
INSERT INTO `cms_category_model` VALUES ('14', '9', '2', '');
INSERT INTO `cms_category_model` VALUES ('15', '16', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('16', '16', '5', '');
INSERT INTO `cms_category_model` VALUES ('17', '6', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('18', '6', '5', '');
INSERT INTO `cms_category_model` VALUES ('47', '6', '1', '/system/article.html');
INSERT INTO `cms_category_model` VALUES ('45', '6', '3', '/system/picture.html');
INSERT INTO `cms_category_model` VALUES ('21', '8', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('22', '8', '5', '');
INSERT INTO `cms_category_model` VALUES ('23', '7', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('24', '7', '5', '');
INSERT INTO `cms_category_model` VALUES ('25', '17', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('26', '17', '5', '');
INSERT INTO `cms_category_model` VALUES ('27', '17', '3', '/system/picture.html');
INSERT INTO `cms_category_model` VALUES ('28', '17', '2', '');
INSERT INTO `cms_category_model` VALUES ('29', '17', '1', '/system/article.html');
INSERT INTO `cms_category_model` VALUES ('30', '7', '2', '');
INSERT INTO `cms_category_model` VALUES ('31', '14', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('32', '14', '5', '');
INSERT INTO `cms_category_model` VALUES ('33', '12', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('34', '12', '5', '');
INSERT INTO `cms_category_model` VALUES ('35', '1', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('36', '1', '5', '');
INSERT INTO `cms_category_model` VALUES ('37', '18', '8', '');
INSERT INTO `cms_category_model` VALUES ('38', '18', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('39', '18', '5', '');
INSERT INTO `cms_category_model` VALUES ('41', '19', '6', '/system/chapter.html');
INSERT INTO `cms_category_model` VALUES ('42', '19', '5', '');
INSERT INTO `cms_category_model` VALUES ('43', '19', '2', '');
INSERT INTO `cms_category_model` VALUES ('44', '18', '7', '');

-- ----------------------------
-- Table structure for `cms_category_type`
-- ----------------------------
DROP TABLE IF EXISTS `cms_category_type`;
CREATE TABLE `cms_category_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `siteId` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL COMMENT '排序',
  `extend_id` int(11) DEFAULT NULL COMMENT '扩展ID',
  PRIMARY KEY (`id`),
  KEY `siteId` (`siteId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_category_type
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_content`
-- ----------------------------
DROP TABLE IF EXISTS `cms_content`;
CREATE TABLE `cms_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `user_id` bigint(20) NOT NULL COMMENT '发表用户',
  `check_user_id` bigint(20) DEFAULT NULL COMMENT '审核用户',
  `category_id` int(11) NOT NULL COMMENT '分类',
  `model_id` int(11) NOT NULL COMMENT '模型',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父内容ID',
  `copied` tinyint(1) NOT NULL COMMENT '是否转载',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `editor` varchar(50) DEFAULT NULL COMMENT '编辑',
  `only_url` tinyint(1) NOT NULL COMMENT '外链',
  `has_images` tinyint(1) NOT NULL COMMENT '拥有图片列表',
  `has_files` tinyint(1) NOT NULL COMMENT '拥有附件列表',
  `has_static` tinyint(1) NOT NULL COMMENT '已经静态化',
  `url` varchar(2048) DEFAULT NULL COMMENT '地址',
  `description` varchar(300) DEFAULT NULL COMMENT '简介',
  `tag_ids` text COMMENT '标签',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面',
  `childs` int(11) NOT NULL COMMENT '内容页数',
  `scores` int(11) NOT NULL COMMENT '分数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `publish_date` datetime NOT NULL COMMENT '发布日期',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `status` int(11) NOT NULL COMMENT '状态：0、草稿 1、已发布 2、待审核',
  `disabled` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `publish_date` (`publish_date`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  KEY `model_id` (`model_id`),
  KEY `parent_id` (`parent_id`),
  KEY `status` (`status`),
  KEY `childs` (`childs`),
  KEY `scores` (`scores`),
  KEY `comments` (`comments`),
  KEY `clicks` (`clicks`),
  KEY `title` (`title`),
  KEY `check_user_id` (`check_user_id`),
  KEY `site_id` (`site_id`),
  KEY `has_files` (`has_files`),
  KEY `has_images` (`has_images`),
  KEY `only_url` (`only_url`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='内容';

-- ----------------------------
-- Records of cms_content
-- ----------------------------
INSERT INTO `cms_content` VALUES ('1', '1', 'PublicCMS合作伙伴招募', '1', '1', '9', '1', null, '0', '', '', '0', '0', '0', '1', '//www.publiccms.com/introduction/2015/11-10/194.html', 'Public CMS V1.0 8月6号预发布，10月9号发布第一份文档，已经积累了超出作者预期的用户数量。作为技能比较单一的技术人员，我一个人开发的Public CMS有着各种局限性，因此诚邀各位加入。', '1', '2015/11/10/12-05-5404301838588841.jpg', '0', '0', '0', '808', '2015-11-10 12:05:58', '2015-11-10 12:05:58', '1', '0');
INSERT INTO `cms_content` VALUES ('2', '1', 'PublicCMS 2016新版本即将发布', '1', '1', '9', '1', null, '0', '', '', '0', '0', '0', '1', '//www.publiccms.com/introduction/2016/03-21/215.html', '经过三个多月的研发，PublicCMS 2016即将发布。现在已经进入内测阶段，诚邀技术人员加入到测试与新版体验中。', '1', '2016/03/09/10-39-540052697476660.png', '0', '0', '0', '250', '2016-03-21 22:47:31', '2016-03-09 10:39:56', '1', '0');
INSERT INTO `cms_content` VALUES ('3', '1', 'Apache FreeMarker从入门到精通教程', '1', '1', '9', '2', null, '0', '湖水没了', '', '1', '0', '0', '0', 'http://www.elsyy.com/course/6841', 'PublicCMS的作者，签约的一个FreeMarker课程', '', '2016/03/05/15-56-080730-1247100853.jpg', '0', '0', '0', '2', '2016-03-05 15:56:13', '2016-03-05 15:56:13', '1', '0');

-- ----------------------------
-- Table structure for `cms_content_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_attribute`;
CREATE TABLE `cms_content_attribute` (
  `content_id` bigint(20) NOT NULL,
  `source` varchar(50) DEFAULT NULL COMMENT '内容来源',
  `source_url` varchar(2048) DEFAULT NULL COMMENT '来源地址',
  `data` longtext COMMENT '数据JSON',
  `text` longtext COMMENT '内容',
  `word_count` int(11) NOT NULL COMMENT '字数',
  PRIMARY KEY (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='内容扩展';

-- ----------------------------
-- Records of cms_content_attribute
-- ----------------------------
INSERT INTO `cms_content_attribute` VALUES ('1', '', '', '{}', '<p style=\"text-indent: 2em;\">Public CMS V1.0 8月6号预发布<span style=\"text-indent: 32px;\">，</span>10月9号发布第一份文档<span style=\"text-indent: 32px;\">，</span>已经积累了超出作者预期的用户数量。作为技能比较单一的技术人员，我一个人开发的Public CMS有着各种局限性，因此诚邀各位加入<span style=\"text-indent: 32px;\">，</span>共同维护这一产品并制定今后的发展方向等。</p><p style=\"text-indent: 2em;\">Public CMS的QQ群目前已经有了70人<span style=\"text-indent: 32px;\">，</span>群号：191381542。偶尔在其他技术类的QQ群竟能遇到Public CMS的用户，让我欣喜不已，同时也知道原来除了Public CMS的交流群中的群友，PublicCMS还有很多没加群的用户。为了更好的交流，大家可以加群。</p><p style=\"text-indent: 2em;\">以下是我一些初步的想法：</p><h3 style=\"text-indent: 2em;\">技术方向<br/></h3><p>&nbsp; &nbsp; 短期（一年左右）内Public CMS的大致发展方向主要集中在功能完善上，包括：</p><ul class=\" list-paddingleft-2\" style=\"list-style-type: disc;\"><li><p style=\"text-indent: 2em;\">后台UI：功能加强，浏览器兼容性完善。或者寻找其他更完善的UI替换掉现有的dwz。</p></li><li><p style=\"text-indent: 2em;\">后台功能：内容维护扩展；页面元数据扩展；分类等排序；推荐位类型扩展；推荐位可选数据类型扩充；模板在线开发功能完善；统计；附件管理等。</p></li><li><p style=\"text-indent: 2em;\">前台模板:前台模板丰富性，美观度提升。</p></li><li><p style=\"text-indent: 2em;\">纯动态站点屏蔽静态化方面配置方面的完善。<br/></p></li></ul><p>&nbsp; &nbsp; 长期规划：多站点，集群，云端内容共享，模板定制平台，二次开发代码在线定制生成等</p><h3 style=\"text-indent: 2em;\">文档方面</h3><ul class=\" list-paddingleft-2\" style=\"list-style-type: disc;\"><li><p style=\"text-indent: 2em;\">在现有文档基础上完善操作步骤细节，二次开发部分完善。</p></li><li><p style=\"text-indent: 2em;\">以Public CMS为基础产品，结合其他产品完成满足不同业务场景的解决方案级文档。</p></li><li><p style=\"text-indent: 2em;\">Public CMS相关的第三放产品的使用、配置、二次开发手册。</p></li><li><p style=\"text-indent: 2em;\">Public CMS产品使用过程中的问题库建设。</p></li><li><p style=\"text-indent: 2em;\">开发或者使用其他BBS架设社区。</p></li></ul><h3 style=\"text-indent: 2em;\">商务方面</h3><p style=\"text-indent: 2em;\">纯公益的行为是不能长久的。Public CMS本身将永久免费开源，不收取任何授权费用，允许用户自由修改开发。在此原则下，可以在模板定制，功能定制，项目承接，技术培训，产品使用培训，或开发商业版产品等方式尝试创收。</p>', '1717');
INSERT INTO `cms_content_attribute` VALUES ('2', '', '', '{}', '<p style=\"text-indent: 2em;\">经过三个多月的研发，PublicCMS 2016即将发布。现在已经进入内测阶段，诚邀技术人员加入到测试与新版体验中。</p><p>&nbsp;&nbsp;&nbsp;&nbsp;需要注意的是现在的版本并不是稳定版，请不要使用在正式项目中。<br/></p><p>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"http://git.oschina.net/sanluan/PublicCMS-preview\" _src=\"http://git.oschina.net/sanluan/PublicCMS-preview\">http://git.oschina.net/sanluan/PublicCMS-preview</a></p>', '355');
INSERT INTO `cms_content_attribute` VALUES ('3', '', '', '{}', null, '0');

-- ----------------------------
-- Table structure for `cms_content_file`
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_file`;
CREATE TABLE `cms_content_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_id` bigint(20) NOT NULL COMMENT '内容',
  `user_id` bigint(20) NOT NULL COMMENT '用户',
  `file_path` varchar(255) NOT NULL COMMENT '文件路径',
  `image` tinyint(1) NOT NULL COMMENT '是否图片',
  `size` int(11) NOT NULL COMMENT '大小',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `sort` int(11) NOT NULL COMMENT '排序',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `content_id` (`content_id`),
  KEY `sort` (`sort`),
  KEY `image` (`image`),
  KEY `size` (`size`),
  KEY `clicks` (`clicks`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='内容附件';

-- ----------------------------
-- Records of cms_content_file
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_content_related`
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_related`;
CREATE TABLE `cms_content_related` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_id` bigint(20) NOT NULL COMMENT '内容',
  `related_content_id` bigint(20) DEFAULT NULL COMMENT '推荐内容',
  `user_id` bigint(20) NOT NULL COMMENT '推荐用户',
  `url` varchar(2048) DEFAULT NULL COMMENT '推荐链接地址',
  `title` varchar(255) DEFAULT NULL COMMENT '推荐标题',
  `description` varchar(300) DEFAULT NULL COMMENT '推荐简介',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `content_id` (`content_id`),
  KEY `related_content_id` (`related_content_id`),
  KEY `sort` (`sort`),
  KEY `user_id` (`user_id`),
  KEY `clicks` (`clicks`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='推荐推荐';

-- ----------------------------
-- Records of cms_content_related
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_content_tag`
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_tag`;
CREATE TABLE `cms_content_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `content_id` bigint(20) NOT NULL COMMENT '内容ID',
  PRIMARY KEY (`id`),
  KEY `tag_id` (`tag_id`),
  KEY `content_id` (`content_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='内容标签';

-- ----------------------------
-- Records of cms_content_tag
-- ----------------------------
INSERT INTO `cms_content_tag` VALUES ('1', '1', '1');
INSERT INTO `cms_content_tag` VALUES ('2', '1', '2');
INSERT INTO `cms_content_tag` VALUES ('3', '2', '3');

-- ----------------------------
-- Table structure for `cms_lottery`
-- ----------------------------
DROP TABLE IF EXISTS `cms_lottery`;
CREATE TABLE `cms_lottery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `start_date` datetime NOT NULL COMMENT '开始日期',
  `end_date` datetime NOT NULL COMMENT '结束日期',
  `interval_hour` int(11) NOT NULL COMMENT '抽奖间隔小时',
  `gift` int(11) NOT NULL COMMENT '每次可抽奖数量',
  `total_gift` int(11) NOT NULL COMMENT '奖品总数',
  `last_gift` int(11) NOT NULL COMMENT '剩余数量',
  `lottery_count` int(11) NOT NULL COMMENT '可抽奖次数',
  `fractions` int(11) NOT NULL COMMENT '概率分子',
  `numerator` int(11) NOT NULL COMMENT '概率分母',
  `url` varchar(2048) DEFAULT NULL COMMENT '地址',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `extend_id` int(11) DEFAULT NULL COMMENT '扩展ID',
  PRIMARY KEY (`id`),
  KEY `start_date` (`start_date`,`end_date`),
  KEY `disabled` (`disabled`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_lottery
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_lottery_user`
-- ----------------------------
DROP TABLE IF EXISTS `cms_lottery_user`;
CREATE TABLE `cms_lottery_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_id` int(11) NOT NULL COMMENT '抽奖ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `winning` tinyint(1) NOT NULL COMMENT '是否中奖',
  `ip` varchar(64) NOT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `lottery_id` (`lottery_id`),
  KEY `user_id` (`user_id`),
  KEY `winning` (`winning`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_lottery_user
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_lottery_user_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `cms_lottery_user_attribute`;
CREATE TABLE `cms_lottery_user_attribute` (
  `lottery_user_id` bigint(20) NOT NULL COMMENT '抽奖用户ID',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY (`lottery_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='抽奖用户扩展';

-- ----------------------------
-- Records of cms_lottery_user_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_model`
-- ----------------------------
DROP TABLE IF EXISTS `cms_model`;
CREATE TABLE `cms_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '父模型',
  `name` varchar(50) NOT NULL COMMENT '内容模型名称',
  `template_path` varchar(200) DEFAULT NULL COMMENT '默认内容模板路径',
  `has_child` tinyint(1) NOT NULL COMMENT '拥有子模型',
  `only_url` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是链接',
  `has_images` tinyint(1) NOT NULL COMMENT '拥有图片列表',
  `has_files` tinyint(1) NOT NULL COMMENT '拥有附件列表',
  `disabled` tinyint(1) NOT NULL COMMENT '是否删除',
  `extend_id` int(11) DEFAULT NULL COMMENT '扩展ID',
  PRIMARY KEY (`id`),
  KEY `disabled` (`disabled`),
  KEY `parent_id` (`parent_id`),
  KEY `has_child` (`has_child`),
  KEY `site_id` (`site_id`),
  KEY `has_images` (`has_images`),
  KEY `has_files` (`has_files`),
  KEY `only_url` (`only_url`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='模型';

-- ----------------------------
-- Records of cms_model
-- ----------------------------
INSERT INTO `cms_model` VALUES ('1', '1', null, '文章', '/system/article.html', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_model` VALUES ('2', '1', null, '链接', '', '0', '1', '0', '0', '0', null);
INSERT INTO `cms_model` VALUES ('3', '1', null, '图集', '/system/picture.html', '0', '0', '1', '0', '0', null);
INSERT INTO `cms_model` VALUES ('4', '1', null, '图书', '/system/book.html', '1', '0', '0', '0', '0', null);
INSERT INTO `cms_model` VALUES ('5', '1', '4', '卷', '', '1', '0', '0', '0', '0', null);
INSERT INTO `cms_model` VALUES ('6', '1', '5', '章节', '/system/chapter.html', '0', '0', '0', '0', '0', null);
INSERT INTO `cms_model` VALUES ('7', '1', null, '商品', '', '0', '1', '0', '0', '0', '1');

-- ----------------------------
-- Table structure for `cms_place`
-- ----------------------------
DROP TABLE IF EXISTS `cms_place`;
CREATE TABLE `cms_place` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `path` varchar(255) NOT NULL COMMENT '模板路径',
  `user_id` bigint(20) NOT NULL COMMENT '提交用户',
  `item_type` varchar(50) DEFAULT NULL COMMENT '推荐项目类型',
  `item_id` int(11) DEFAULT NULL COMMENT '推荐项目ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `url` varchar(2048) DEFAULT NULL COMMENT '超链接',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面图',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `publish_date` datetime NOT NULL COMMENT '发布日期',
  `status` int(11) NOT NULL COMMENT '状态：0、前台提交 1、已发布 ',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY (`id`),
  KEY `path` (`path`),
  KEY `disabled` (`disabled`),
  KEY `publish_date` (`publish_date`),
  KEY `create_date` (`create_date`),
  KEY `site_id` (`site_id`),
  KEY `status` (`status`),
  KEY `item_id` (`item_id`),
  KEY `item_type` (`item_type`),
  KEY `user_id` (`user_id`),
  KEY `clicks` (`clicks`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='页面数据';

-- ----------------------------
-- Records of cms_place
-- ----------------------------
INSERT INTO `cms_place` VALUES ('1', '1', '/index.html/94fe86e5-45b3-4896-823a-37c6d7d6c578.html', '1', 'content', '142', 'PublicCMS后台截图', '//www.publiccms.com/introduction/2015/08-11/142.html', '2015/11/15/17-35-240834-18490682.jpg', '2016-03-21 21:25:19', '2016-03-21 21:24:54', '1', '6', '0');
INSERT INTO `cms_place` VALUES ('2', '1', '/index.html/94fe86e5-45b3-4896-823a-37c6d7d6c578.html', '1', 'content', '159', '美食', '//www.publiccms.com/picture/2015/08-13/159.html', '2015/11/15/17-35-150887-240130090.jpg', '2016-03-21 21:26:26', '2016-03-21 21:26:08', '1', '4', '0');
INSERT INTO `cms_place` VALUES ('3', '1', '/index.html/94fe86e5-45b3-4896-823a-37c6d7d6c578.html', '1', 'content', '9', '昂科拉', '//www.publiccms.com/car/2015/08-06/9.html', '2015/11/15/17-35-0606061972977756.jpg', '2016-03-21 21:28:57', '2016-03-21 21:28:36', '1', '8', '0');
INSERT INTO `cms_place` VALUES ('4', '1', '/index.html/94fe86e5-45b3-4896-823a-37c6d7d6c578.html', '1', 'content', '179', 'PublicCMS系统使用手册下载', '//www.publiccms.com/introduction/2015/10-09/179.html', '2015/11/15/17-34-560426-203327271.jpg', '2016-03-21 21:30:25', '2016-03-21 21:43:45', '1', '18', '0');
INSERT INTO `cms_place` VALUES ('5', '1', '/index.html/94fe86e5-45b3-4896-823a-37c6d7d6c578.html', '1', 'content', '195', '我们的婚纱照', '//www.publiccms.com/picture/2015/11-15/195.html', '2015/11/15/17-34-450591-326203189.jpg', '2016-03-21 21:31:04', '2016-03-20 21:30:46', '1', '4', '0');
INSERT INTO `cms_place` VALUES ('6', '1', '/index.html/11847f87-7f1b-4891-ace4-818659ce397b.html', '1', 'custom', null, 'Public CMS QQ群', 'http://shang.qq.com/wpa/qunwpa?idkey=8a633f84fb2475068182d3c447319977faca6a14dc3acf8017a160d65962a175', '', '2016-03-21 22:10:33', '2016-03-21 22:10:26', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('7', '1', '/index.html/11847f87-7f1b-4891-ace4-818659ce397b.html', '1', 'custom', null, 'FreeMarker语法在线测试', 'http://sanluan.com/freemarker_test.html', '', '2016-03-21 22:11:57', '2016-03-21 22:11:47', '1', '4', '0');
INSERT INTO `cms_place` VALUES ('8', '1', '/index.html/11847f87-7f1b-4891-ace4-818659ce397b.html', '1', 'custom', null, '百度搜索：PublicCMS', 'https://www.baidu.com/s?wd=publiccms', '', '2016-03-21 22:12:12', '2016-03-21 22:12:00', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('9', '1', '/index.html/11847f87-7f1b-4891-ace4-818659ce397b.html', '1', 'custom', null, 'FreeMarker2.3.23中文手册', 'http://www.kerneler.com/freemarker2.3.23/', '', '2016-03-21 22:12:24', '2016-03-21 22:12:14', '1', '5', '0');
INSERT INTO `cms_place` VALUES ('10', '1', '/index.html/11847f87-7f1b-4891-ace4-818659ce397b.html', '1', 'custom', null, 'FreeMarker2.3.23视频教程', 'http://www.elsyy.com/course/6841', '', '2016-03-21 22:12:51', '2016-03-21 22:12:37', '1', '4', '0');
INSERT INTO `cms_place` VALUES ('11', '1', '/index.html/5cf1b463-8d14-4ba4-a904-890ec224dc99.html', '1', 'custom', null, '管理后台', '//cms.publiccms.com/admin/', '', '2016-03-21 22:13:54', '2016-03-21 22:13:49', '1', '0', '1');
INSERT INTO `cms_place` VALUES ('12', '1', '/index.html/5cf1b463-8d14-4ba4-a904-890ec224dc99.html', '1', 'custom', null, '后台UI', '//image.publiccms.com/ui/', '', '2016-03-21 22:14:06', '2016-03-21 22:13:56', '1', '22', '0');
INSERT INTO `cms_place` VALUES ('13', '1', '/index.html/c6ae8ea8-103d-4c93-8ff2-79d67a38b3ae.html', '1', 'custom', null, '洪越源码', 'http://www.softhy.net/soft/36775.htm', '', '2016-03-23 11:03:50', '2016-03-23 11:03:31', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('14', '1', '/index.html/c6ae8ea8-103d-4c93-8ff2-79d67a38b3ae.html', '1', 'custom', null, 'ASP300源码', 'http://www.asp300.com/SoftView/13/SoftView_59265.html', '', '2016-03-23 11:04:10', '2016-03-23 11:03:53', '1', '2', '0');
INSERT INTO `cms_place` VALUES ('15', '1', '/index.html/c6ae8ea8-103d-4c93-8ff2-79d67a38b3ae.html', '1', 'custom', null, '脚本之家', 'http://www.jb51.net/codes/389534.html', '', '2016-03-23 11:04:24', '2016-03-23 11:04:11', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('16', '1', '/index.html/c6ae8ea8-103d-4c93-8ff2-79d67a38b3ae.html', '1', 'custom', null, '站长之家下载', 'http://down.chinaz.com/soft/37488.htm', '', '2016-03-23 11:04:42', '2016-03-23 11:04:33', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('17', '1', '/index.html/d1bef19f-ec32-4c3b-90f9-b25ca0fe19e3.html', '1', 'custom', null, '成品网站模板超市', 'http://demo.edge-cloud.cn/', '', '2016-03-23 11:12:03', '2016-03-23 11:12:32', '1', '6', '1');
INSERT INTO `cms_place` VALUES ('18', '1', '/index.html/d1bef19f-ec32-4c3b-90f9-b25ca0fe19e3.html', '1', 'custom', null, 'QQ联系作者', 'http://wpa.qq.com/msgrd?v=3&uin=315415433&site=qq&menu=yes', '', '2016-03-23 11:12:23', '2016-03-23 11:12:05', '1', '4', '0');
INSERT INTO `cms_place` VALUES ('19', '1', '/index.html/895b6167-c2ce-43ad-b936-b1a10cd1ad5d.html', '1', 'custom', null, 'PublicCMS@Github', 'https://github.com/sanluan/PublicCMS.', '', '2016-03-23 11:13:33', '2016-03-23 11:13:25', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('20', '1', '/index.html/895b6167-c2ce-43ad-b936-b1a10cd1ad5d.html', '1', 'custom', null, 'PublicCMS@开源中国', 'http://git.oschina.net/sanluan/PublicCMS', '', '2016-03-23 11:13:48', '2016-03-23 11:13:35', '1', '5', '0');
INSERT INTO `cms_place` VALUES ('21', '1', '/index.html/895b6167-c2ce-43ad-b936-b1a10cd1ad5d.html', '1', 'custom', null, 'PublicCMS@CSDN', 'https://code.csdn.net/zyyy358/publiccms', '', '2016-03-23 11:14:03', '2016-03-23 11:13:50', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('22', '1', '/index.html/895b6167-c2ce-43ad-b936-b1a10cd1ad5d.html', '1', 'custom', null, 'PublicCMS-preview@开源中国', 'http://git.oschina.net/sanluan/PublicCMS-preview', '', '2016-03-23 11:14:30', '2016-03-23 11:14:09', '1', '5', '0');
INSERT INTO `cms_place` VALUES ('23', '1', '/index.html/cfdc226d-8abc-48ec-810d-f3941b175b20.html', '1', 'custom', null, '搞机哥-博客', 'http://www.gaojig.com/', '', '2016-03-23 11:15:16', '2016-03-23 11:15:07', '1', '14', '0');
INSERT INTO `cms_place` VALUES ('24', '1', '/index.html/cfdc226d-8abc-48ec-810d-f3941b175b20.html', '1', 'custom', null, '锋云科技', 'http://www.edge-cloud.cn/', '', '2016-03-23 11:15:28', '2016-03-23 11:15:21', '1', '20', '0');
INSERT INTO `cms_place` VALUES ('25', '1', '/category/list.html/3435e9a7-565a-4f93-8670-9c272a1d51cc.html', '1', 'content', '4', '唯美动漫图片', '//www.publiccms.com/8/4.html', '2015/08/07/11-24-1308292097994334.jpg', '2016-03-23 11:22:57', '2016-03-23 11:22:04', '1', '4', '0');
INSERT INTO `cms_place` VALUES ('26', '1', '/category/list.html/3435e9a7-565a-4f93-8670-9c272a1d51cc.html', '1', 'content', '9', '昂科拉', '//www.publiccms.com/6/9.html', '2015/08/07/11-24-3602801209954489.jpg', '2016-03-23 11:23:55', '2016-03-23 11:23:31', '1', '2', '0');
INSERT INTO `cms_place` VALUES ('27', '1', '/category/list.html/49d393ca-f0f1-4723-a9b0-6f9b6d7cc04d.html', '1', 'content', '19', '微软：不要在Win10中使用第三方“隐私保护”工具', '//www.publiccms.com/2015/08/06/19.html', '', '2016-03-23 11:27:26', '2016-03-23 11:27:06', '1', '0', '0');
INSERT INTO `cms_place` VALUES ('28', '1', '/category/list.html/49d393ca-f0f1-4723-a9b0-6f9b6d7cc04d.html', '1', 'content', '30', '女子吃了泡发2天的木耳 致多器官衰竭不治身亡', '//www.publiccms.com/2015/08-07/30.html', '', '2016-03-23 11:27:42', '2016-03-23 11:27:28', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('29', '1', '/category/list.html/49d393ca-f0f1-4723-a9b0-6f9b6d7cc04d.html', '1', 'content', '22', '江苏仪征新集一玩具厂起大火 火光冲天', '//www.publiccms.com/7/22.html', '', '2016-03-23 11:27:55', '2016-03-23 11:27:44', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('30', '1', '/category/list.html/49d393ca-f0f1-4723-a9b0-6f9b6d7cc04d.html', '1', 'content', '142', 'PublicCMS后台截图', '//www.publiccms.com/9/142.html', '', '2016-03-23 11:28:08', '2016-03-23 11:27:57', '1', '6', '0');
INSERT INTO `cms_place` VALUES ('31', '1', '/category/list.html/49d393ca-f0f1-4723-a9b0-6f9b6d7cc04d.html', '1', 'content', '18', 'PublicCMS进入测试阶段，即将正式发布', '//www.publiccms.com/9/18.html', '', '2016-03-23 11:28:21', '2016-03-23 11:28:14', '1', '7', '0');
INSERT INTO `cms_place` VALUES ('32', '1', '/category/list.html/49d393ca-f0f1-4723-a9b0-6f9b6d7cc04d.html', '1', 'content', '217', '酷冷至尊烈焰枪旗舰版机械键盘 有线104键游戏全背光 樱桃轴正品', 'http://s.click.taobao.com/t?e=m%3D2%26s%3Dk%2FRaMwaPpnYcQipKwQzePOeEDrYVVa64K7Vc7tFgwiFRAdhuF14FMV3pVOinSGgeRitN3%2FurF3zO1KWqeCJhFmPYiLpdxhFe%2B6GA20g%2FvatSQhIbbzwChQUDqeizZVd13GFiMU8U2DTHAGIcyZQCxSGFCzYOOqAQ&pvid=50_106.2.199.138_346_1458707425019', '', '2016-03-28 11:21:01', '2016-03-28 11:17:37', '1', '3', '0');
INSERT INTO `cms_place` VALUES ('33', '1', '/index.html/cfdc226d-8abc-48ec-810d-f3941b175b20.html', '1', 'custom', null, 'BD工具网', 'http://www.bdtool.net/', '', '2016-03-28 14:29:39', '2016-03-28 14:29:34', '1', '23', '0');
INSERT INTO `cms_place` VALUES ('34', '1', '/index.html/cfdc226d-8abc-48ec-810d-f3941b175b20.html', '1', 'custom', null, '在线项目计划', 'http://www.oiplan.com/user/index.do', '', '2016-03-28 16:35:01', '2016-03-28 16:34:48', '1', '25', '0');
INSERT INTO `cms_place` VALUES ('35', '1', '/index.html/5cf1b463-8d14-4ba4-a904-890ec224dc99.html', '1', 'custom', null, '动态站点', '//cms.publiccms.com/', '', '2016-03-31 18:50:06', '2016-03-31 18:49:54', '1', '16', '1');
INSERT INTO `cms_place` VALUES ('36', '1', '/index.html/d1bef19f-ec32-4c3b-90f9-b25ca0fe19e3.html', '1', 'custom', null, '成品网站模板超市', 'http://www.edge-cloud.cn/wangzhanjianshe-30-1.html', '', '2016-04-13 16:05:14', '2016-04-13 16:04:55', '1', '2', '0');
INSERT INTO `cms_place` VALUES ('37', '1', '/web/index.html/c1e71b3b-9423-4bfb-94c2-f32c1dd03abe.html', '1', 'custom', null, '拓展链接1', 'www.baidu.com', '', '2016-11-28 17:46:53', '2016-11-28 17:46:15', '1', '1', '1');

-- ----------------------------
-- Table structure for `cms_place_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `cms_place_attribute`;
CREATE TABLE `cms_place_attribute` (
  `place_id` bigint(20) NOT NULL COMMENT '位置ID',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY (`place_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='推荐位数据扩展';

-- ----------------------------
-- Records of cms_place_attribute
-- ----------------------------
INSERT INTO `cms_place_attribute` VALUES ('1', '{}');
INSERT INTO `cms_place_attribute` VALUES ('2', '{}');
INSERT INTO `cms_place_attribute` VALUES ('3', '{}');
INSERT INTO `cms_place_attribute` VALUES ('4', '{}');
INSERT INTO `cms_place_attribute` VALUES ('5', '{}');
INSERT INTO `cms_place_attribute` VALUES ('6', '{}');
INSERT INTO `cms_place_attribute` VALUES ('7', '{}');
INSERT INTO `cms_place_attribute` VALUES ('8', '{}');
INSERT INTO `cms_place_attribute` VALUES ('9', '{}');
INSERT INTO `cms_place_attribute` VALUES ('10', '{}');
INSERT INTO `cms_place_attribute` VALUES ('11', '{}');
INSERT INTO `cms_place_attribute` VALUES ('12', '{}');
INSERT INTO `cms_place_attribute` VALUES ('13', '{}');
INSERT INTO `cms_place_attribute` VALUES ('14', '{}');
INSERT INTO `cms_place_attribute` VALUES ('15', '{}');
INSERT INTO `cms_place_attribute` VALUES ('16', '{}');
INSERT INTO `cms_place_attribute` VALUES ('17', '{}');
INSERT INTO `cms_place_attribute` VALUES ('18', '{}');
INSERT INTO `cms_place_attribute` VALUES ('19', '{}');
INSERT INTO `cms_place_attribute` VALUES ('20', '{}');
INSERT INTO `cms_place_attribute` VALUES ('21', '{}');
INSERT INTO `cms_place_attribute` VALUES ('22', '{}');
INSERT INTO `cms_place_attribute` VALUES ('23', '{}');
INSERT INTO `cms_place_attribute` VALUES ('24', '{}');
INSERT INTO `cms_place_attribute` VALUES ('25', '{}');
INSERT INTO `cms_place_attribute` VALUES ('26', '{}');
INSERT INTO `cms_place_attribute` VALUES ('27', '{}');
INSERT INTO `cms_place_attribute` VALUES ('28', '{}');
INSERT INTO `cms_place_attribute` VALUES ('29', '{}');
INSERT INTO `cms_place_attribute` VALUES ('30', '{}');
INSERT INTO `cms_place_attribute` VALUES ('31', '{}');
INSERT INTO `cms_place_attribute` VALUES ('32', '{}');
INSERT INTO `cms_place_attribute` VALUES ('33', '{}');
INSERT INTO `cms_place_attribute` VALUES ('34', '{}');
INSERT INTO `cms_place_attribute` VALUES ('35', '{}');
INSERT INTO `cms_place_attribute` VALUES ('36', '{}');
INSERT INTO `cms_place_attribute` VALUES ('37', '{\"index1\":\"2016/11/28/17-34-2203261376104339.png\",\"index3\":\"2016/11/28/17-34-2203261376104339.png\",\"index2\":\"2016/11/28/17-34-2203261376104339.png\",\"index4\":\"2016/11/28/17-34-2203261376104339.png\"}');

-- ----------------------------
-- Table structure for `cms_tag`
-- ----------------------------
DROP TABLE IF EXISTS `cms_tag`;
CREATE TABLE `cms_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `type_id` int(11) DEFAULT NULL COMMENT '分类ID',
  `search_count` int(11) NOT NULL COMMENT '搜索次数',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='标签';

-- ----------------------------
-- Records of cms_tag
-- ----------------------------
INSERT INTO `cms_tag` VALUES ('1', '1', 'PublicCMS', null, '0');
INSERT INTO `cms_tag` VALUES ('2', '1', 'FreeMarker', null, '0');

-- ----------------------------
-- Table structure for `cms_tag_type`
-- ----------------------------
DROP TABLE IF EXISTS `cms_tag_type`;
CREATE TABLE `cms_tag_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `count` int(11) NOT NULL COMMENT '标签数',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='标签类型';

-- ----------------------------
-- Records of cms_tag_type
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_vote`
-- ----------------------------
DROP TABLE IF EXISTS `cms_vote`;
CREATE TABLE `cms_vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `start_date` datetime NOT NULL COMMENT '开始日期',
  `end_date` datetime NOT NULL COMMENT '结束日期',
  `interval_hour` int(11) NOT NULL COMMENT '投票间隔小时',
  `max_vote` int(11) NOT NULL COMMENT '最大投票数',
  `anonymous` tinyint(1) NOT NULL COMMENT '匿名投票',
  `user_counts` int(11) NOT NULL COMMENT '参与用户数',
  `url` varchar(2048) NOT NULL COMMENT '地址',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  `item_extend_id` int(11) NOT NULL COMMENT '扩展ID',
  PRIMARY KEY (`id`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_vote
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_vote_item`
-- ----------------------------
DROP TABLE IF EXISTS `cms_vote_item`;
CREATE TABLE `cms_vote_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vote_id` int(11) NOT NULL COMMENT '投票',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `scores` int(11) NOT NULL COMMENT '票数',
  `sort` int(11) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `lottery_id` (`vote_id`),
  KEY `user_id` (`title`),
  KEY `create_date` (`sort`),
  KEY `scores` (`scores`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_vote_item
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_vote_item_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `cms_vote_item_attribute`;
CREATE TABLE `cms_vote_item_attribute` (
  `vote_item_id` bigint(20) NOT NULL COMMENT '选项ID',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY (`vote_item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='投票选项扩展';

-- ----------------------------
-- Records of cms_vote_item_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_vote_user`
-- ----------------------------
DROP TABLE IF EXISTS `cms_vote_user`;
CREATE TABLE `cms_vote_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_id` int(11) NOT NULL COMMENT '抽奖ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_ids` text NOT NULL COMMENT '投票选项',
  `ip` varchar(64) NOT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `lottery_id` (`lottery_id`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_vote_user
-- ----------------------------

-- ----------------------------
-- Table structure for `cms_word`
-- ----------------------------
DROP TABLE IF EXISTS `cms_word`;
CREATE TABLE `cms_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `site_id` int(11) NOT NULL COMMENT '站点',
  `search_count` int(11) NOT NULL COMMENT '搜索次数',
  `hidden` tinyint(1) NOT NULL COMMENT '隐藏',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`site_id`),
  KEY `hidden` (`hidden`),
  KEY `search_count` (`search_count`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_word
-- ----------------------------

-- ----------------------------
-- Table structure for `home_active`
-- ----------------------------
DROP TABLE IF EXISTS `home_active`;
CREATE TABLE `home_active` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `item_type` varchar(20) NOT NULL COMMENT '项目类型',
  `item_id` bigint(20) NOT NULL COMMENT '项目ID',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_type` (`user_id`,`item_type`,`item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间动态';

-- ----------------------------
-- Records of home_active
-- ----------------------------

-- ----------------------------
-- Table structure for `home_article`
-- ----------------------------
DROP TABLE IF EXISTS `home_article`;
CREATE TABLE `home_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `directory_id` bigint(20) DEFAULT NULL COMMENT '目录ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面图',
  `scores` int(11) NOT NULL COMMENT '分数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  `create_date` datetime NOT NULL COMMENT '发布日期',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`,`directory_id`,`user_id`,`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间文章';

-- ----------------------------
-- Records of home_article
-- ----------------------------

-- ----------------------------
-- Table structure for `home_article_content`
-- ----------------------------
DROP TABLE IF EXISTS `home_article_content`;
CREATE TABLE `home_article_content` (
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `content` longtext COMMENT '内容',
  PRIMARY KEY (`article_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='文章内容';

-- ----------------------------
-- Records of home_article_content
-- ----------------------------

-- ----------------------------
-- Table structure for `home_attention`
-- ----------------------------
DROP TABLE IF EXISTS `home_attention`;
CREATE TABLE `home_attention` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `attention_id` bigint(20) NOT NULL COMMENT '关注ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`user_id`,`attention_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间关注';

-- ----------------------------
-- Records of home_attention
-- ----------------------------

-- ----------------------------
-- Table structure for `home_broadcast`
-- ----------------------------
DROP TABLE IF EXISTS `home_broadcast`;
CREATE TABLE `home_broadcast` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `scores` int(11) NOT NULL COMMENT '分数',
  `reposts` int(11) NOT NULL COMMENT '转发数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `message` varchar(300) NOT NULL COMMENT '消息',
  `reposted` tinyint(1) NOT NULL COMMENT '转发',
  `repost_id` bigint(20) NOT NULL COMMENT '转发广播ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY (`id`),
  KEY `reposted` (`reposted`,`repost_id`),
  KEY `site_id` (`site_id`,`user_id`,`create_date`,`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间广播';

-- ----------------------------
-- Records of home_broadcast
-- ----------------------------

-- ----------------------------
-- Table structure for `home_directory`
-- ----------------------------
DROP TABLE IF EXISTS `home_directory`;
CREATE TABLE `home_directory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面图',
  `files` int(11) NOT NULL COMMENT '文件数',
  `secret` tinyint(1) NOT NULL COMMENT '私密目录',
  `create_date` datetime NOT NULL COMMENT '发布日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`,`user_id`,`create_date`,`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间目录';

-- ----------------------------
-- Records of home_directory
-- ----------------------------

-- ----------------------------
-- Table structure for `home_file`
-- ----------------------------
DROP TABLE IF EXISTS `home_file`;
CREATE TABLE `home_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `directory_id` bigint(20) DEFAULT NULL COMMENT '目录',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `file_path` varchar(255) NOT NULL COMMENT '封面图',
  `image` tinyint(1) NOT NULL COMMENT '是否图片',
  `file_size` int(11) NOT NULL COMMENT '文件大小',
  `scores` int(11) NOT NULL COMMENT '分数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `create_date` datetime NOT NULL COMMENT '发布日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`,`user_id`,`directory_id`,`image`,`create_date`,`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间文件';

-- ----------------------------
-- Records of home_file
-- ----------------------------

-- ----------------------------
-- Table structure for `home_message`
-- ----------------------------
DROP TABLE IF EXISTS `home_message`;
CREATE TABLE `home_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '所属用户',
  `send_user_id` bigint(20) NOT NULL COMMENT '发送用户',
  `receive_user_id` bigint(20) NOT NULL COMMENT '接收用户',
  `message_id` bigint(20) DEFAULT NULL COMMENT '关联消息',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `content` text NOT NULL COMMENT '消息',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`),
  KEY `message_id` (`message_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户消息';

-- ----------------------------
-- Records of home_message
-- ----------------------------

-- ----------------------------
-- Table structure for `home_user`
-- ----------------------------
DROP TABLE IF EXISTS `home_user`;
CREATE TABLE `home_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `signature` varchar(300) DEFAULT NULL,
  `friends` int(11) NOT NULL COMMENT '好友数',
  `messages` int(11) NOT NULL COMMENT '消息数',
  `questions` int(11) NOT NULL COMMENT '问题数',
  `answers` int(11) NOT NULL COMMENT '回答数',
  `articles` int(11) NOT NULL COMMENT '文章数',
  `clicks` int(11) NOT NULL COMMENT '点击数数',
  `broadcasts` int(11) NOT NULL COMMENT '广播数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `attention_ids` text COMMENT '关注用户',
  `attentions` int(11) NOT NULL COMMENT '关注数',
  `fans` int(11) NOT NULL COMMENT '粉丝数',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登陆日期',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY (`user_id`),
  KEY `site_id` (`site_id`,`last_login_date`,`create_date`,`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户空间';

-- ----------------------------
-- Records of home_user
-- ----------------------------

-- ----------------------------
-- Table structure for `log_login`
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `ip` varchar(64) NOT NULL COMMENT 'IP',
  `channel` varchar(50) NOT NULL DEFAULT 'web' COMMENT '登陆渠道',
  `result` tinyint(1) NOT NULL COMMENT '结果',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `error_password` varchar(100) DEFAULT NULL COMMENT '错误密码',
  PRIMARY KEY (`id`),
  KEY `result` (`result`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`),
  KEY `ip` (`ip`),
  KEY `site_id` (`site_id`),
  KEY `channel` (`channel`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='登陆日志';

-- ----------------------------
-- Records of log_login
-- ----------------------------
INSERT INTO `log_login` VALUES ('1', '1', 'admin', '1', '127.0.0.1', 'web_manager', '1', '2016-11-24 14:45:51', null);
INSERT INTO `log_login` VALUES ('2', '1', 'admin', '1', '127.0.0.1', 'web_manager', '1', '2016-11-24 15:06:20', null);
INSERT INTO `log_login` VALUES ('3', '1', 'admin', '1', '192.168.1.233', 'web_manager', '1', '2016-11-24 15:29:37', null);
INSERT INTO `log_login` VALUES ('4', '1', 'admin', '1', '127.0.0.1', 'web_manager', '1', '2016-11-25 11:42:18', null);
INSERT INTO `log_login` VALUES ('5', '1', 'admin', '1', '127.0.0.1', 'web_manager', '1', '2016-11-25 12:10:15', null);
INSERT INTO `log_login` VALUES ('6', '1', 'admin', '1', '127.0.0.1', 'web_manager', '1', '2016-11-25 13:40:55', null);
INSERT INTO `log_login` VALUES ('7', '1', '18611920344', null, '106.2.184.219', 'web_manager', '0', '2016-11-25 19:48:48', '920344');
INSERT INTO `log_login` VALUES ('8', '1', 'admin', '1', '106.2.184.219', 'web_manager', '1', '2016-11-25 19:48:56', null);
INSERT INTO `log_login` VALUES ('9', '1', 'admin', '1', '106.2.184.219', 'web_manager', '1', '2016-11-25 19:55:02', null);
INSERT INTO `log_login` VALUES ('10', '1', 'admin', '1', '106.2.184.219', 'web_manager', '1', '2016-11-25 20:18:15', null);
INSERT INTO `log_login` VALUES ('11', '1', 'admin', '1', '106.2.184.219', 'web_manager', '1', '2016-11-28 10:17:10', null);
INSERT INTO `log_login` VALUES ('12', '1', 'admin', '1', '106.2.184.219', 'web_manager', '1', '2016-11-28 12:32:41', null);
INSERT INTO `log_login` VALUES ('13', '1', 'admin', '1', '106.2.184.219', 'web_manager', '1', '2016-11-28 12:39:00', null);
INSERT INTO `log_login` VALUES ('14', '1', 'admin', '1', '106.2.184.219', 'web_manager', '1', '2016-11-28 16:16:21', null);
INSERT INTO `log_login` VALUES ('15', '1', '18611920344', null, '124.117.185.167', 'web_manager', '0', '2016-11-29 17:47:29', '920344');
INSERT INTO `log_login` VALUES ('16', '1', 'admin', '1', '124.117.185.167', 'web_manager', '1', '2016-11-29 17:47:37', null);
INSERT INTO `log_login` VALUES ('17', '1', 'admin', '1', '124.117.166.228', 'web_manager', '1', '2016-11-29 22:48:56', null);
INSERT INTO `log_login` VALUES ('18', '1', 'admin', '1', '124.117.166.228', 'web_manager', '1', '2016-11-29 22:50:22', null);
INSERT INTO `log_login` VALUES ('19', '1', 'admin', '1', '120.205.17.124', 'web_manager', '1', '2016-11-30 09:45:16', null);
INSERT INTO `log_login` VALUES ('20', '1', 'admin', '1', '120.205.17.124', 'web_manager', '1', '2016-11-30 10:25:06', null);
INSERT INTO `log_login` VALUES ('21', '1', 'admin', '1', '61.148.244.148', 'web_manager', '0', '2016-12-01 16:08:49', '199808');
INSERT INTO `log_login` VALUES ('22', '1', 'admin', '1', '61.148.244.148', 'web_manager', '1', '2016-12-01 16:08:57', null);
INSERT INTO `log_login` VALUES ('23', '1', 'admin', '1', '120.205.17.124', 'web_manager', '1', '2016-12-01 16:11:17', null);
INSERT INTO `log_login` VALUES ('24', '1', 'admin', '1', '61.148.242.208', 'web_manager', '0', '2016-12-01 17:33:44', '123456');
INSERT INTO `log_login` VALUES ('25', '1', 'admin', '1', '61.148.242.208', 'web_manager', '1', '2016-12-01 17:33:57', null);
INSERT INTO `log_login` VALUES ('26', '1', 'admin', '1', '120.205.17.124', 'web_manager', '1', '2016-12-01 17:35:20', null);
INSERT INTO `log_login` VALUES ('27', '1', 'admin', '1', '120.205.17.124', 'web_manager', '1', '2016-12-01 18:31:14', null);
INSERT INTO `log_login` VALUES ('28', '1', 'admin', '1', '114.242.248.62', 'web_manager', '1', '2016-12-01 19:10:42', null);
INSERT INTO `log_login` VALUES ('29', '1', 'admin', '1', '124.117.175.141', 'web_manager', '1', '2016-12-01 23:40:43', null);
INSERT INTO `log_login` VALUES ('30', '1', 'admin', '1', '124.117.175.141', 'web_manager', '1', '2016-12-02 01:10:33', null);
INSERT INTO `log_login` VALUES ('31', '1', 'admin', '1', '120.205.17.124', 'web_manager', '1', '2016-12-02 09:42:10', null);
INSERT INTO `log_login` VALUES ('32', '1', 'admin', '1', '120.205.17.124', 'web_manager', '1', '2016-12-02 10:23:36', null);

-- ----------------------------
-- Table structure for `log_operate`
-- ----------------------------
DROP TABLE IF EXISTS `log_operate`;
CREATE TABLE `log_operate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `channel` varchar(50) NOT NULL COMMENT '操作取到',
  `operate` varchar(40) NOT NULL COMMENT '操作',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `content` varchar(500) NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `operate` (`operate`),
  KEY `create_date` (`create_date`),
  KEY `ip` (`ip`),
  KEY `site_id` (`site_id`),
  KEY `channel` (`channel`)
) ENGINE=MyISAM AUTO_INCREMENT=266 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of log_operate
-- ----------------------------
INSERT INTO `log_operate` VALUES ('1', '1', '1', 'web_manager', 'update.template.meta', '127.0.0.1', '2016-11-24 14:49:50', '/index.html');
INSERT INTO `log_operate` VALUES ('2', '1', '1', 'web_manager', 'update.site', '127.0.0.1', '2016-11-24 14:57:14', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('3', '1', '1', 'web_manager', 'delete.web.template', '127.0.0.1', '2016-11-24 14:58:35', '/index.html');
INSERT INTO `log_operate` VALUES ('4', '1', '1', 'web_manager', 'update.template.meta', '127.0.0.1', '2016-11-24 14:58:50', '/index.html');
INSERT INTO `log_operate` VALUES ('5', '1', '1', 'web_manager', 'update.template.meta', '127.0.0.1', '2016-11-24 15:01:57', '/index.html');
INSERT INTO `log_operate` VALUES ('6', '1', '1', 'web_manager', 'update.site', '127.0.0.1', '2016-11-24 15:06:51', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('7', '1', '1', 'web_manager', 'update.web.template', '127.0.0.1', '2016-11-24 15:07:42', '/index.html');
INSERT INTO `log_operate` VALUES ('8', '1', '1', 'web_manager', 'update.template.meta', '127.0.0.1', '2016-11-24 15:39:39', '/list.html');
INSERT INTO `log_operate` VALUES ('9', '1', '1', 'web_manager', 'update.web.template', '127.0.0.1', '2016-11-24 15:40:18', '/list.html');
INSERT INTO `log_operate` VALUES ('10', '1', '1', 'web_manager', 'update.web.template', '127.0.0.1', '2016-11-24 15:43:23', '/index.html');
INSERT INTO `log_operate` VALUES ('11', '1', '1', 'web_manager', 'update.web.template', '127.0.0.1', '2016-11-25 11:51:12', '/index.html');
INSERT INTO `log_operate` VALUES ('12', '1', '1', 'web_manager', 'update.web.template', '127.0.0.1', '2016-11-25 11:54:34', '/list.html');
INSERT INTO `log_operate` VALUES ('13', '1', '1', 'web_manager', 'update.web.template', '127.0.0.1', '2016-11-25 12:14:31', '/index.html');
INSERT INTO `log_operate` VALUES ('14', '1', '1', 'web_manager', 'update.site', '127.0.0.1', '2016-11-25 13:41:50', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('15', '1', '1', 'web_manager', 'update.web.template', '127.0.0.1', '2016-11-25 13:44:00', '/index.html');
INSERT INTO `log_operate` VALUES ('16', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-25 19:51:57', '/teacher/index.html');
INSERT INTO `log_operate` VALUES ('17', '1', '1', 'web_manager', 'update.site', '106.2.184.219', '2016-11-25 19:53:10', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('18', '1', '1', 'web_manager', 'update.site', '106.2.184.219', '2016-11-28 11:06:56', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('19', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 11:07:16', '/web/index.html');
INSERT INTO `log_operate` VALUES ('20', '1', '1', 'web_manager', 'delete.web.template', '106.2.184.219', '2016-11-28 11:07:42', '/teacher');
INSERT INTO `log_operate` VALUES ('21', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 11:37:24', '/web/index.html');
INSERT INTO `log_operate` VALUES ('22', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 11:38:05', '/web/index.html');
INSERT INTO `log_operate` VALUES ('23', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 11:40:27', '/web/index.html');
INSERT INTO `log_operate` VALUES ('24', '1', '1', 'web_manager', 'update.site', '106.2.184.219', '2016-11-28 11:41:52', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('25', '1', '1', 'web_manager', 'update.site', '106.2.184.219', '2016-11-28 11:47:35', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('26', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 11:49:16', '/web/index.html');
INSERT INTO `log_operate` VALUES ('27', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 11:51:14', '/web/index.html');
INSERT INTO `log_operate` VALUES ('28', '1', '1', 'web_manager', 'update.site', '106.2.184.219', '2016-11-28 11:51:48', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('29', '1', '1', 'web_manager', 'update.site', '106.2.184.219', '2016-11-28 11:53:15', '1:PublicCMS');
INSERT INTO `log_operate` VALUES ('30', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 12:17:18', '/web/index.html');
INSERT INTO `log_operate` VALUES ('31', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:18:09', '/web/index.html');
INSERT INTO `log_operate` VALUES ('32', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 12:18:33', '/web/index.html');
INSERT INTO `log_operate` VALUES ('33', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 12:19:41', '/web/index.html');
INSERT INTO `log_operate` VALUES ('34', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 12:19:51', '/web/index.html');
INSERT INTO `log_operate` VALUES ('35', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:38:03', '/web/index.html');
INSERT INTO `log_operate` VALUES ('36', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:41:37', '/web/index.html');
INSERT INTO `log_operate` VALUES ('37', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:42:15', '/web/index.html');
INSERT INTO `log_operate` VALUES ('38', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:43:38', '/web/index.html');
INSERT INTO `log_operate` VALUES ('39', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:43:58', '/web/index.html');
INSERT INTO `log_operate` VALUES ('40', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:44:22', '/web/index.html');
INSERT INTO `log_operate` VALUES ('41', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:44:53', '/web/index.html');
INSERT INTO `log_operate` VALUES ('42', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:45:06', '/web/index.html');
INSERT INTO `log_operate` VALUES ('43', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:46:27', '/web/index.html');
INSERT INTO `log_operate` VALUES ('44', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 12:47:01', '/web/index.html');
INSERT INTO `log_operate` VALUES ('45', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 13:26:49', '/web/index.html');
INSERT INTO `log_operate` VALUES ('46', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 14:59:28', '/web/index.html');
INSERT INTO `log_operate` VALUES ('47', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 15:00:38', '/web/list.html');
INSERT INTO `log_operate` VALUES ('48', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:08:44', '/web/list.html');
INSERT INTO `log_operate` VALUES ('49', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:09:01', '/web/list.html');
INSERT INTO `log_operate` VALUES ('50', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:09:20', '/web/list.html');
INSERT INTO `log_operate` VALUES ('51', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:10:17', '/web/list.html');
INSERT INTO `log_operate` VALUES ('52', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:10:54', '/web/list.html');
INSERT INTO `log_operate` VALUES ('53', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:11:04', '/web/list.html');
INSERT INTO `log_operate` VALUES ('54', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:11:33', '/web/list.html');
INSERT INTO `log_operate` VALUES ('55', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:11:46', '/web/list.html');
INSERT INTO `log_operate` VALUES ('56', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:13:38', '/web/list.html');
INSERT INTO `log_operate` VALUES ('57', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:14:27', '/web/list.html');
INSERT INTO `log_operate` VALUES ('58', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:14:40', '/web/index.html');
INSERT INTO `log_operate` VALUES ('59', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:18:24', '/web/list.html');
INSERT INTO `log_operate` VALUES ('60', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:20:13', '/web/list.html');
INSERT INTO `log_operate` VALUES ('61', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 15:22:21', '/ftl/logo.html');
INSERT INTO `log_operate` VALUES ('62', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:23:17', '/web/list.html');
INSERT INTO `log_operate` VALUES ('63', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:23:24', '/ftl/logo.html');
INSERT INTO `log_operate` VALUES ('64', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:23:39', '/web/list.html');
INSERT INTO `log_operate` VALUES ('65', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 15:26:22', '/web/list.html');
INSERT INTO `log_operate` VALUES ('66', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:27:01', '/web/list.html');
INSERT INTO `log_operate` VALUES ('67', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 15:27:46', '/web/list.html');
INSERT INTO `log_operate` VALUES ('68', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 15:42:06', '/ftl/loginkey.html');
INSERT INTO `log_operate` VALUES ('69', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:42:14', '/ftl/loginkey.html');
INSERT INTO `log_operate` VALUES ('70', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:42:38', '/web/list.html');
INSERT INTO `log_operate` VALUES ('71', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:43:19', '/web/index.html');
INSERT INTO `log_operate` VALUES ('72', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 15:48:35', '/ftl/footer.html');
INSERT INTO `log_operate` VALUES ('73', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:48:50', '/ftl/footer.html');
INSERT INTO `log_operate` VALUES ('74', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:49:06', '/web/list.html');
INSERT INTO `log_operate` VALUES ('75', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:49:26', '/web/index.html');
INSERT INTO `log_operate` VALUES ('76', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 15:51:24', '/web/index.html');
INSERT INTO `log_operate` VALUES ('77', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 15:51:31', '/web/list.html');
INSERT INTO `log_operate` VALUES ('78', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 15:52:48', '/web/content.html');
INSERT INTO `log_operate` VALUES ('79', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 15:52:58', '/web/content.html');
INSERT INTO `log_operate` VALUES ('80', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:54:52', '/web/content.html');
INSERT INTO `log_operate` VALUES ('81', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:56:18', '/web/content.html');
INSERT INTO `log_operate` VALUES ('82', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:57:11', '/web/content.html');
INSERT INTO `log_operate` VALUES ('83', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 15:57:53', '/web/content.html');
INSERT INTO `log_operate` VALUES ('84', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 17:33:49', '/web/index.html');
INSERT INTO `log_operate` VALUES ('85', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 17:35:36', '/web/index.html');
INSERT INTO `log_operate` VALUES ('86', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 17:40:28', '/web/index.html');
INSERT INTO `log_operate` VALUES ('87', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 17:41:32', '/web/index.html');
INSERT INTO `log_operate` VALUES ('88', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 17:43:06', '/web/index.html');
INSERT INTO `log_operate` VALUES ('89', '1', '1', 'web_manager', 'update.template.meta', '106.2.184.219', '2016-11-28 17:45:13', '/include/web/index.html/c1e71b3b-9423-4bfb-94c2-f32c1dd03abe.html');
INSERT INTO `log_operate` VALUES ('90', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 17:45:35', '/web/index.html');
INSERT INTO `log_operate` VALUES ('91', '1', '1', 'web_manager', 'save.place', '106.2.184.219', '2016-11-28 17:46:53', '/web/index.html/c1e71b3b-9423-4bfb-94c2-f32c1dd03abe.html');
INSERT INTO `log_operate` VALUES ('92', '1', '1', 'web_manager', 'check.place', '106.2.184.219', '2016-11-28 17:47:38', '37');
INSERT INTO `log_operate` VALUES ('93', '1', '1', 'web_manager', 'check.place', '106.2.184.219', '2016-11-28 17:47:47', '37');
INSERT INTO `log_operate` VALUES ('94', '1', '1', 'web_manager', 'update.place', '106.2.184.219', '2016-11-28 17:48:22', '/web/index.html/c1e71b3b-9423-4bfb-94c2-f32c1dd03abe.html');
INSERT INTO `log_operate` VALUES ('95', '1', '1', 'web_manager', 'delete.place', '106.2.184.219', '2016-11-28 17:48:55', '37');
INSERT INTO `log_operate` VALUES ('96', '1', '1', 'web_manager', 'update.template.data', '106.2.184.219', '2016-11-28 17:49:09', '/web/index.html');
INSERT INTO `log_operate` VALUES ('97', '1', '1', 'web_manager', 'update.web.template', '106.2.184.219', '2016-11-28 17:49:25', '/web/index.html');
INSERT INTO `log_operate` VALUES ('98', '1', '1', 'web_manager', 'update.web.template', '124.117.166.228', '2016-11-29 23:19:49', '/web/index.html');
INSERT INTO `log_operate` VALUES ('99', '1', '1', 'web_manager', 'update.web.template', '124.117.166.228', '2016-11-29 23:20:44', '/ftl/loginkey.html');
INSERT INTO `log_operate` VALUES ('100', '1', '1', 'web_manager', 'update.web.template', '124.117.166.228', '2016-11-29 23:22:08', '/web/list.html');
INSERT INTO `log_operate` VALUES ('101', '1', '1', 'web_manager', 'update.web.template', '124.117.166.228', '2016-11-29 23:24:03', '/web/content.html');
INSERT INTO `log_operate` VALUES ('102', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:12:35', '/other/list.html');
INSERT INTO `log_operate` VALUES ('103', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:14:30', '/other/list.html');
INSERT INTO `log_operate` VALUES ('104', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:15:00', '/other/list.html');
INSERT INTO `log_operate` VALUES ('105', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:15:34', '/other/list.html');
INSERT INTO `log_operate` VALUES ('106', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:17:45', '/other/list.html');
INSERT INTO `log_operate` VALUES ('107', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:21:27', '/other/list.html');
INSERT INTO `log_operate` VALUES ('108', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 16:22:43', '/other/list.html');
INSERT INTO `log_operate` VALUES ('109', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:24:02', '/other/list.html');
INSERT INTO `log_operate` VALUES ('110', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:25:07', '/other/lists.html');
INSERT INTO `log_operate` VALUES ('111', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:26:08', '/other/list.html');
INSERT INTO `log_operate` VALUES ('112', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 16:30:28', '/other/list.html');
INSERT INTO `log_operate` VALUES ('113', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:32:11', '/other/index.html');
INSERT INTO `log_operate` VALUES ('114', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:34:29', '/other/index.html');
INSERT INTO `log_operate` VALUES ('115', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:34:49', '/other/index.html');
INSERT INTO `log_operate` VALUES ('116', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 16:35:15', '/other/index.html');
INSERT INTO `log_operate` VALUES ('117', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:36:14', '/other/index.html');
INSERT INTO `log_operate` VALUES ('118', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:37:00', '/other/index.html');
INSERT INTO `log_operate` VALUES ('119', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:37:39', '/other/index.html');
INSERT INTO `log_operate` VALUES ('120', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 16:38:40', '/other/index.html');
INSERT INTO `log_operate` VALUES ('121', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:39:16', '/other/list.html');
INSERT INTO `log_operate` VALUES ('122', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:40:06', '/other/list.html');
INSERT INTO `log_operate` VALUES ('123', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 16:40:29', '/other/list.html');
INSERT INTO `log_operate` VALUES ('124', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 16:40:40', '/other/list.html');
INSERT INTO `log_operate` VALUES ('125', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:41:29', '/other/list.html');
INSERT INTO `log_operate` VALUES ('126', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:42:12', '/other/list.html');
INSERT INTO `log_operate` VALUES ('127', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:43:19', '/other/index.html');
INSERT INTO `log_operate` VALUES ('128', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:43:42', '/other/index.html');
INSERT INTO `log_operate` VALUES ('129', '1', '1', 'web_manager', 'static.category', '120.205.17.124', '2016-12-01 16:45:08', '12,pageSize:1');
INSERT INTO `log_operate` VALUES ('130', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:57:20', '/other/index.html');
INSERT INTO `log_operate` VALUES ('131', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:58:37', '/other/list.html');
INSERT INTO `log_operate` VALUES ('132', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:58:50', '/other/list.html');
INSERT INTO `log_operate` VALUES ('133', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 16:59:34', '/other/list.html');
INSERT INTO `log_operate` VALUES ('134', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:01:14', '/other/index.html');
INSERT INTO `log_operate` VALUES ('135', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 17:04:50', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('136', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:07:36', '/other/index.html');
INSERT INTO `log_operate` VALUES ('137', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:11:21', '/other/index.html');
INSERT INTO `log_operate` VALUES ('138', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:35:22', '/other/list.html');
INSERT INTO `log_operate` VALUES ('139', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 17:37:04', '/other/list.html');
INSERT INTO `log_operate` VALUES ('140', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 17:39:32', '/other/list.html');
INSERT INTO `log_operate` VALUES ('141', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:42:44', '/other/list.html');
INSERT INTO `log_operate` VALUES ('142', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:44:04', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('143', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:44:16', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('144', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 17:44:30', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('145', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:52:47', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('146', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 17:55:14', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('147', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 17:55:36', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('148', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:56:14', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('149', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:56:36', '/other/list.html');
INSERT INTO `log_operate` VALUES ('150', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 17:58:48', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('151', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 17:59:00', '/other/index.html');
INSERT INTO `log_operate` VALUES ('152', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 17:59:47', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('153', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:00:00', '/other/index.html');
INSERT INTO `log_operate` VALUES ('154', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:00:14', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('155', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:08:44', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('156', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:08:57', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('157', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:09:35', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('158', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:09:48', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('159', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:10:19', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('160', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:10:59', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('161', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:11:27', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('162', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:11:38', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('163', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:12:36', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('164', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:14:02', '/other/index.html');
INSERT INTO `log_operate` VALUES ('165', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:15:18', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('166', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:15:42', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('167', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:15:56', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('168', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:18:24', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('169', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:18:48', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('170', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:19:09', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('171', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:20:19', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('172', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:20:38', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('173', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:21:39', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('174', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:22:11', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('175', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:22:49', '/other/index.html');
INSERT INTO `log_operate` VALUES ('176', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:23:03', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('177', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:25:20', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('178', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:25:39', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('179', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:26:03', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('180', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:26:16', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('181', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:26:29', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('182', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:28:20', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('183', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:29:37', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('184', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:37:02', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('185', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:37:30', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('186', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:38:02', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('187', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:38:22', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('188', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:38:33', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('189', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:39:55', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('190', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:44:17', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('191', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:44:23', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('192', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:44:42', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('193', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:46:09', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('194', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:46:30', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('195', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:46:44', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('196', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:46:59', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('197', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:47:11', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('198', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:47:29', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('199', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:48:08', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('200', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:48:18', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('201', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:48:50', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('202', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:49:57', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('203', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:50:19', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('204', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:50:46', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('205', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:50:57', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('206', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:51:12', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('207', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:51:34', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('208', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:51:41', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('209', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:52:18', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('210', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:53:11', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('211', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:54:19', '/other/index.html');
INSERT INTO `log_operate` VALUES ('212', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:55:54', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('213', '1', '1', 'web_manager', 'update.template.meta', '120.205.17.124', '2016-12-01 18:56:06', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('214', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:56:32', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('215', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-01 18:57:10', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('216', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-01 18:58:04', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('217', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-01 23:46:22', '/other/index.html');
INSERT INTO `log_operate` VALUES ('218', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-01 23:48:39', '/other/index.html');
INSERT INTO `log_operate` VALUES ('219', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-01 23:52:00', '/other/index.html');
INSERT INTO `log_operate` VALUES ('220', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-01 23:52:17', '/other/index.html');
INSERT INTO `log_operate` VALUES ('221', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-01 23:54:52', '/other/index.html');
INSERT INTO `log_operate` VALUES ('222', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-01 23:55:10', '/other/index.html');
INSERT INTO `log_operate` VALUES ('223', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-01 23:57:17', '/other/index.html');
INSERT INTO `log_operate` VALUES ('224', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-01 23:59:36', '/other/index.html');
INSERT INTO `log_operate` VALUES ('225', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 00:00:10', '/other/index.html');
INSERT INTO `log_operate` VALUES ('226', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:21:30', '/other/list.html');
INSERT INTO `log_operate` VALUES ('227', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 01:22:46', '/other/list.html');
INSERT INTO `log_operate` VALUES ('228', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:23:16', '/other/list.html');
INSERT INTO `log_operate` VALUES ('229', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 01:24:30', '/other/list.html');
INSERT INTO `log_operate` VALUES ('230', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:26:00', '/other/list.html');
INSERT INTO `log_operate` VALUES ('231', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:26:27', '/other/list.html');
INSERT INTO `log_operate` VALUES ('232', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:27:03', '/other/list.html');
INSERT INTO `log_operate` VALUES ('233', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:28:40', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('234', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:31:40', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('235', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 01:32:40', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('236', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:33:29', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('237', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:34:01', '/other/index.html');
INSERT INTO `log_operate` VALUES ('238', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:38:55', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('239', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 01:39:41', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('240', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:40:19', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('241', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:40:53', '/other/index.html');
INSERT INTO `log_operate` VALUES ('242', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:41:06', '/other/list.html');
INSERT INTO `log_operate` VALUES ('243', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:41:15', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('244', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:41:23', '/other/list6.html');
INSERT INTO `log_operate` VALUES ('245', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:43:49', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('246', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 01:44:32', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('247', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:45:07', '/other/list7.html');
INSERT INTO `log_operate` VALUES ('248', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:51:12', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('249', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 01:52:25', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('250', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:52:58', '/other/list2.html');
INSERT INTO `log_operate` VALUES ('251', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:55:23', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('252', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 01:56:11', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('253', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:56:44', '/other/list8.html');
INSERT INTO `log_operate` VALUES ('254', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 01:58:26', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('255', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 01:59:09', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('256', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 01:59:38', '/other/list3.html');
INSERT INTO `log_operate` VALUES ('257', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 02:01:39', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('258', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 02:02:25', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('259', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 02:02:55', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('260', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 02:03:45', '/other/list10.html');
INSERT INTO `log_operate` VALUES ('261', '1', '1', 'web_manager', 'update.web.template', '124.117.175.141', '2016-12-02 02:05:56', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('262', '1', '1', 'web_manager', 'update.template.meta', '124.117.175.141', '2016-12-02 02:06:29', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('263', '1', '1', 'web_manager', 'update.template.data', '124.117.175.141', '2016-12-02 02:07:07', '/other/list4.html');
INSERT INTO `log_operate` VALUES ('264', '1', '1', 'web_manager', 'update.template.data', '120.205.17.124', '2016-12-02 09:47:36', '/other/list5.html');
INSERT INTO `log_operate` VALUES ('265', '1', '1', 'web_manager', 'update.web.template', '120.205.17.124', '2016-12-02 10:24:43', '/other/index.html');

-- ----------------------------
-- Table structure for `log_task`
-- ----------------------------
DROP TABLE IF EXISTS `log_task`;
CREATE TABLE `log_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `task_id` int(11) NOT NULL COMMENT '任务',
  `begintime` datetime NOT NULL COMMENT '开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '结束时间',
  `success` tinyint(1) NOT NULL COMMENT '执行成功',
  `result` longtext COMMENT '执行结果',
  PRIMARY KEY (`id`),
  KEY `task_id` (`task_id`),
  KEY `success` (`success`),
  KEY `site_id` (`site_id`),
  KEY `begintime` (`begintime`)
) ENGINE=MyISAM AUTO_INCREMENT=310 DEFAULT CHARSET=utf8 COMMENT='任务计划日志';

-- ----------------------------
-- Records of log_task
-- ----------------------------
INSERT INTO `log_task` VALUES ('1', '1', '1', '2016-11-24 16:00:01', '2016-11-24 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"E:\\data\\publiccms\\task\", canonicalBasePath=\"E:\\data\\publiccms\\task\\\").');
INSERT INTO `log_task` VALUES ('2', '1', '1', '2016-11-25 12:00:01', '2016-11-25 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"E:\\data\\publiccms\\task\", canonicalBasePath=\"E:\\data\\publiccms\\task\\\").');
INSERT INTO `log_task` VALUES ('3', '1', '5', '2016-11-25 12:00:01', '2016-11-25 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"E:\\data\\publiccms\\task\", canonicalBasePath=\"E:\\data\\publiccms\\task\\\").');
INSERT INTO `log_task` VALUES ('4', '1', '1', '2016-11-25 20:00:01', '2016-11-25 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('5', '1', '1', '2016-11-25 22:00:01', '2016-11-25 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('6', '1', '1', '2016-11-26 00:00:01', '2016-11-26 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('7', '1', '5', '2016-11-26 00:00:01', '2016-11-26 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('8', '1', '1', '2016-11-26 02:00:01', '2016-11-26 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('9', '1', '1', '2016-11-26 04:00:01', '2016-11-26 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('10', '1', '5', '2016-11-26 06:00:01', '2016-11-26 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('11', '1', '1', '2016-11-26 06:00:01', '2016-11-26 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('12', '1', '1', '2016-11-26 08:00:01', '2016-11-26 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('13', '1', '1', '2016-11-26 10:00:01', '2016-11-26 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('14', '1', '5', '2016-11-26 12:00:01', '2016-11-26 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('15', '1', '1', '2016-11-26 12:00:01', '2016-11-26 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('16', '1', '1', '2016-11-26 14:00:01', '2016-11-26 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('17', '1', '1', '2016-11-26 16:00:01', '2016-11-26 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('18', '1', '1', '2016-11-26 18:00:01', '2016-11-26 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('19', '1', '5', '2016-11-26 18:00:01', '2016-11-26 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('20', '1', '1', '2016-11-26 20:00:01', '2016-11-26 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('21', '1', '1', '2016-11-26 22:00:01', '2016-11-26 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('22', '1', '1', '2016-11-27 00:00:01', '2016-11-27 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('23', '1', '5', '2016-11-27 00:00:01', '2016-11-27 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('24', '1', '1', '2016-11-27 02:00:01', '2016-11-27 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('25', '1', '1', '2016-11-27 04:00:01', '2016-11-27 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('26', '1', '1', '2016-11-27 06:00:01', '2016-11-27 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('27', '1', '5', '2016-11-27 06:00:01', '2016-11-27 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('28', '1', '1', '2016-11-27 08:00:01', '2016-11-27 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('29', '1', '1', '2016-11-27 10:00:01', '2016-11-27 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('30', '1', '1', '2016-11-27 12:00:01', '2016-11-27 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('31', '1', '5', '2016-11-27 12:00:01', '2016-11-27 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('32', '1', '1', '2016-11-27 14:00:01', '2016-11-27 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('33', '1', '1', '2016-11-27 16:00:01', '2016-11-27 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('34', '1', '5', '2016-11-27 18:00:01', '2016-11-27 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('35', '1', '1', '2016-11-27 18:00:01', '2016-11-27 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('36', '1', '1', '2016-11-27 20:00:01', '2016-11-27 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('37', '1', '1', '2016-11-27 22:00:01', '2016-11-27 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('38', '1', '1', '2016-11-28 00:00:01', '2016-11-28 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('39', '1', '5', '2016-11-28 00:00:01', '2016-11-28 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('40', '1', '1', '2016-11-28 02:00:01', '2016-11-28 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('41', '1', '1', '2016-11-28 04:00:01', '2016-11-28 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('42', '1', '1', '2016-11-28 06:00:01', '2016-11-28 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('43', '1', '5', '2016-11-28 06:00:01', '2016-11-28 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('44', '1', '1', '2016-11-28 08:00:01', '2016-11-28 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('45', '1', '1', '2016-11-28 10:00:01', '2016-11-28 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('46', '1', '1', '2016-11-28 12:00:01', '2016-11-28 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('47', '1', '5', '2016-11-28 12:00:01', '2016-11-28 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/data/publiccms/task\", canonicalBasePath=\"/data/publiccms/task/\").');
INSERT INTO `log_task` VALUES ('48', '1', '1', '2016-11-28 14:00:01', '2016-11-28 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('49', '1', '1', '2016-11-28 16:00:01', '2016-11-28 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('50', '1', '1', '2016-11-28 18:00:01', '2016-11-28 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('51', '1', '5', '2016-11-28 18:00:01', '2016-11-28 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('52', '1', '1', '2016-11-28 20:00:01', '2016-11-28 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('53', '1', '1', '2016-11-28 22:00:01', '2016-11-28 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('54', '1', '1', '2016-11-29 00:00:01', '2016-11-29 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('55', '1', '5', '2016-11-29 00:00:01', '2016-11-29 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('56', '1', '1', '2016-11-29 02:00:01', '2016-11-29 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('57', '1', '1', '2016-11-29 04:00:01', '2016-11-29 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('58', '1', '1', '2016-11-29 06:00:01', '2016-11-29 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('59', '1', '5', '2016-11-29 06:00:01', '2016-11-29 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('60', '1', '1', '2016-11-29 08:00:01', '2016-11-29 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('61', '1', '1', '2016-11-29 10:00:01', '2016-11-29 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('62', '1', '1', '2016-11-29 12:00:01', '2016-11-29 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('63', '1', '5', '2016-11-29 12:00:01', '2016-11-29 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('64', '1', '1', '2016-11-29 14:00:01', '2016-11-29 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('65', '1', '1', '2016-11-29 16:00:01', '2016-11-29 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('66', '1', '1', '2016-11-29 18:00:01', '2016-11-29 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('67', '1', '5', '2016-11-29 18:00:01', '2016-11-29 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('68', '1', '1', '2016-11-29 20:00:01', '2016-11-29 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('69', '1', '1', '2016-11-29 22:00:01', '2016-11-29 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('70', '1', '5', '2016-11-30 00:00:01', '2016-11-30 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('71', '1', '1', '2016-11-30 00:00:01', '2016-11-30 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('72', '1', '1', '2016-11-30 02:00:01', '2016-11-30 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('73', '1', '1', '2016-11-30 04:00:01', '2016-11-30 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('74', '1', '5', '2016-11-30 06:00:01', '2016-11-30 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('75', '1', '1', '2016-11-30 06:00:01', '2016-11-30 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('76', '1', '1', '2016-11-30 08:00:01', '2016-11-30 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('77', '1', '1', '2016-11-30 10:00:01', '2016-11-30 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('78', '1', '1', '2016-11-30 12:00:01', '2016-11-30 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('79', '1', '5', '2016-11-30 12:00:01', '2016-11-30 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('80', '1', '1', '2016-11-30 14:00:01', '2016-11-30 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('81', '1', '1', '2016-11-30 16:00:01', '2016-11-30 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('82', '1', '1', '2016-11-30 18:00:01', '2016-11-30 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('83', '1', '5', '2016-11-30 18:00:01', '2016-11-30 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('84', '1', '1', '2016-11-30 20:00:01', '2016-11-30 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('85', '1', '1', '2016-11-30 22:00:01', '2016-11-30 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('86', '1', '1', '2016-12-01 00:00:01', '2016-12-01 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('87', '1', '3', '2016-12-01 00:00:01', '2016-12-01 00:00:01', '1', 'Template not found for name \"/site_1/clearLog.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('88', '1', '5', '2016-12-01 00:00:01', '2016-12-01 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('89', '1', '1', '2016-12-01 02:00:01', '2016-12-01 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('90', '1', '1', '2016-12-01 04:00:01', '2016-12-01 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('91', '1', '1', '2016-12-01 06:00:01', '2016-12-01 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('92', '1', '5', '2016-12-01 06:00:01', '2016-12-01 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('93', '1', '1', '2016-12-01 08:00:01', '2016-12-01 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('94', '1', '1', '2016-12-01 10:00:01', '2016-12-01 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('95', '1', '5', '2016-12-01 12:00:01', '2016-12-01 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('96', '1', '1', '2016-12-01 12:00:01', '2016-12-01 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('97', '1', '1', '2016-12-01 14:00:01', '2016-12-01 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('98', '1', '1', '2016-12-01 16:00:01', '2016-12-01 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('99', '1', '1', '2016-12-01 18:00:01', '2016-12-01 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('100', '1', '5', '2016-12-01 18:00:01', '2016-12-01 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('101', '1', '1', '2016-12-01 20:00:01', '2016-12-01 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('102', '1', '1', '2016-12-01 22:00:01', '2016-12-01 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('103', '1', '1', '2016-12-02 00:00:01', '2016-12-02 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('104', '1', '5', '2016-12-02 00:00:01', '2016-12-02 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('105', '1', '1', '2016-12-02 02:00:01', '2016-12-02 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('106', '1', '1', '2016-12-02 04:00:01', '2016-12-02 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('107', '1', '5', '2016-12-02 06:00:01', '2016-12-02 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('108', '1', '1', '2016-12-02 06:00:01', '2016-12-02 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('109', '1', '1', '2016-12-02 08:00:01', '2016-12-02 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('110', '1', '1', '2016-12-02 10:00:01', '2016-12-02 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('111', '1', '5', '2016-12-02 12:00:01', '2016-12-02 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('112', '1', '1', '2016-12-02 12:00:01', '2016-12-02 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('113', '1', '1', '2016-12-02 14:00:01', '2016-12-02 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('114', '1', '1', '2016-12-02 16:00:01', '2016-12-02 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('115', '1', '1', '2016-12-02 18:00:01', '2016-12-02 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('116', '1', '5', '2016-12-02 18:00:01', '2016-12-02 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('117', '1', '1', '2016-12-02 20:00:01', '2016-12-02 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('118', '1', '1', '2016-12-02 22:00:01', '2016-12-02 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('119', '1', '1', '2016-12-03 00:00:01', '2016-12-03 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('120', '1', '5', '2016-12-03 00:00:01', '2016-12-03 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('121', '1', '1', '2016-12-03 02:00:01', '2016-12-03 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('122', '1', '1', '2016-12-03 04:00:01', '2016-12-03 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('123', '1', '5', '2016-12-03 06:00:01', '2016-12-03 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('124', '1', '1', '2016-12-03 06:00:01', '2016-12-03 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('125', '1', '1', '2016-12-03 08:00:01', '2016-12-03 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('126', '1', '1', '2016-12-03 10:00:01', '2016-12-03 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('127', '1', '5', '2016-12-03 12:00:01', '2016-12-03 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('128', '1', '1', '2016-12-03 12:00:01', '2016-12-03 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('129', '1', '1', '2016-12-03 14:00:01', '2016-12-03 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('130', '1', '1', '2016-12-03 16:00:01', '2016-12-03 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('131', '1', '1', '2016-12-03 18:00:01', '2016-12-03 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('132', '1', '5', '2016-12-03 18:00:01', '2016-12-03 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('133', '1', '1', '2016-12-03 20:00:01', '2016-12-03 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('134', '1', '1', '2016-12-03 22:00:01', '2016-12-03 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('135', '1', '5', '2016-12-04 00:00:01', '2016-12-04 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('136', '1', '1', '2016-12-04 00:00:01', '2016-12-04 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('137', '1', '1', '2016-12-04 02:00:01', '2016-12-04 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('138', '1', '1', '2016-12-04 04:00:01', '2016-12-04 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('139', '1', '1', '2016-12-04 06:00:01', '2016-12-04 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('140', '1', '5', '2016-12-04 06:00:01', '2016-12-04 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('141', '1', '1', '2016-12-04 08:00:01', '2016-12-04 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('142', '1', '1', '2016-12-04 10:00:01', '2016-12-04 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('143', '1', '5', '2016-12-04 12:00:01', '2016-12-04 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('144', '1', '1', '2016-12-04 12:00:01', '2016-12-04 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('145', '1', '1', '2016-12-04 14:00:01', '2016-12-04 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('146', '1', '1', '2016-12-04 16:00:01', '2016-12-04 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('147', '1', '5', '2016-12-04 18:00:01', '2016-12-04 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('148', '1', '1', '2016-12-04 18:00:01', '2016-12-04 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('149', '1', '1', '2016-12-04 20:00:01', '2016-12-04 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('150', '1', '1', '2016-12-04 22:00:01', '2016-12-04 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('151', '1', '1', '2016-12-05 00:00:01', '2016-12-05 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('152', '1', '5', '2016-12-05 00:00:01', '2016-12-05 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('153', '1', '1', '2016-12-05 02:00:01', '2016-12-05 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('154', '1', '1', '2016-12-05 04:00:01', '2016-12-05 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('155', '1', '1', '2016-12-05 06:00:01', '2016-12-05 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('156', '1', '5', '2016-12-05 06:00:01', '2016-12-05 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('157', '1', '1', '2016-12-05 08:00:01', '2016-12-05 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('158', '1', '1', '2016-12-05 10:00:01', '2016-12-05 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('159', '1', '1', '2016-12-05 12:00:01', '2016-12-05 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('160', '1', '5', '2016-12-05 12:00:01', '2016-12-05 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('161', '1', '1', '2016-12-05 14:00:01', '2016-12-05 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('162', '1', '1', '2016-12-05 16:00:01', '2016-12-05 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('163', '1', '5', '2016-12-05 18:00:01', '2016-12-05 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('164', '1', '1', '2016-12-05 18:00:01', '2016-12-05 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('165', '1', '1', '2016-12-05 20:00:01', '2016-12-05 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('166', '1', '1', '2016-12-05 22:00:01', '2016-12-05 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('167', '1', '5', '2016-12-06 00:00:01', '2016-12-06 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('168', '1', '1', '2016-12-06 00:00:01', '2016-12-06 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('169', '1', '1', '2016-12-06 02:00:01', '2016-12-06 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('170', '1', '1', '2016-12-06 04:00:01', '2016-12-06 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('171', '1', '1', '2016-12-06 06:00:01', '2016-12-06 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('172', '1', '5', '2016-12-06 06:00:01', '2016-12-06 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('173', '1', '1', '2016-12-06 08:00:01', '2016-12-06 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('174', '1', '1', '2016-12-06 10:00:01', '2016-12-06 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('175', '1', '5', '2016-12-06 12:00:01', '2016-12-06 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('176', '1', '1', '2016-12-06 12:00:01', '2016-12-06 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('177', '1', '1', '2016-12-06 14:00:01', '2016-12-06 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('178', '1', '1', '2016-12-06 16:00:01', '2016-12-06 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('179', '1', '1', '2016-12-06 18:00:01', '2016-12-06 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('180', '1', '5', '2016-12-06 18:00:01', '2016-12-06 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('181', '1', '1', '2016-12-06 20:00:01', '2016-12-06 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('182', '1', '1', '2016-12-06 22:00:01', '2016-12-06 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('183', '1', '1', '2016-12-07 00:00:01', '2016-12-07 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('184', '1', '5', '2016-12-07 00:00:01', '2016-12-07 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('185', '1', '1', '2016-12-07 02:00:01', '2016-12-07 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('186', '1', '1', '2016-12-07 04:00:01', '2016-12-07 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('187', '1', '5', '2016-12-07 06:00:01', '2016-12-07 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('188', '1', '1', '2016-12-07 06:00:01', '2016-12-07 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('189', '1', '1', '2016-12-07 08:00:01', '2016-12-07 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('190', '1', '1', '2016-12-07 10:00:01', '2016-12-07 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('191', '1', '5', '2016-12-07 12:00:01', '2016-12-07 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('192', '1', '1', '2016-12-07 12:00:01', '2016-12-07 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('193', '1', '1', '2016-12-07 14:00:01', '2016-12-07 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('194', '1', '1', '2016-12-07 16:00:01', '2016-12-07 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('195', '1', '1', '2016-12-07 18:00:01', '2016-12-07 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('196', '1', '5', '2016-12-07 18:00:01', '2016-12-07 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('197', '1', '1', '2016-12-07 20:00:01', '2016-12-07 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('198', '1', '1', '2016-12-07 22:00:01', '2016-12-07 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('199', '1', '1', '2016-12-08 00:00:01', '2016-12-08 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('200', '1', '5', '2016-12-08 00:00:01', '2016-12-08 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('201', '1', '1', '2016-12-08 02:00:01', '2016-12-08 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('202', '1', '1', '2016-12-08 04:00:01', '2016-12-08 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('203', '1', '1', '2016-12-08 06:00:01', '2016-12-08 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('204', '1', '5', '2016-12-08 06:00:01', '2016-12-08 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('205', '1', '1', '2016-12-08 08:00:01', '2016-12-08 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('206', '1', '1', '2016-12-08 10:00:01', '2016-12-08 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('207', '1', '1', '2016-12-08 12:00:01', '2016-12-08 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('208', '1', '5', '2016-12-08 12:00:01', '2016-12-08 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('209', '1', '1', '2016-12-08 14:00:01', '2016-12-08 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('210', '1', '1', '2016-12-08 16:00:01', '2016-12-08 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('211', '1', '5', '2016-12-08 18:00:01', '2016-12-08 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('212', '1', '1', '2016-12-08 18:00:01', '2016-12-08 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('213', '1', '1', '2016-12-08 20:00:01', '2016-12-08 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('214', '1', '1', '2016-12-08 22:00:01', '2016-12-08 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('215', '1', '5', '2016-12-09 00:00:01', '2016-12-09 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('216', '1', '1', '2016-12-09 00:00:01', '2016-12-09 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('217', '1', '1', '2016-12-09 02:00:01', '2016-12-09 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('218', '1', '1', '2016-12-09 04:00:01', '2016-12-09 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('219', '1', '5', '2016-12-09 06:00:01', '2016-12-09 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('220', '1', '1', '2016-12-09 06:00:01', '2016-12-09 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('221', '1', '1', '2016-12-09 08:00:01', '2016-12-09 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('222', '1', '1', '2016-12-09 10:00:01', '2016-12-09 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('223', '1', '1', '2016-12-09 12:00:01', '2016-12-09 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('224', '1', '5', '2016-12-09 12:00:01', '2016-12-09 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('225', '1', '1', '2016-12-09 14:00:01', '2016-12-09 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('226', '1', '1', '2016-12-09 16:00:01', '2016-12-09 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('227', '1', '1', '2016-12-09 18:00:01', '2016-12-09 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('228', '1', '5', '2016-12-09 18:00:01', '2016-12-09 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('229', '1', '1', '2016-12-09 20:00:01', '2016-12-09 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('230', '1', '1', '2016-12-09 22:00:01', '2016-12-09 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('231', '1', '1', '2016-12-10 00:00:01', '2016-12-10 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('232', '1', '5', '2016-12-10 00:00:01', '2016-12-10 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('233', '1', '1', '2016-12-10 02:00:01', '2016-12-10 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('234', '1', '1', '2016-12-10 04:00:01', '2016-12-10 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('235', '1', '5', '2016-12-10 06:00:01', '2016-12-10 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('236', '1', '1', '2016-12-10 06:00:01', '2016-12-10 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('237', '1', '1', '2016-12-10 08:00:01', '2016-12-10 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('238', '1', '1', '2016-12-10 10:00:01', '2016-12-10 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('239', '1', '5', '2016-12-10 12:00:01', '2016-12-10 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('240', '1', '1', '2016-12-10 12:00:01', '2016-12-10 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('241', '1', '1', '2016-12-10 14:00:01', '2016-12-10 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('242', '1', '1', '2016-12-10 16:00:01', '2016-12-10 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('243', '1', '1', '2016-12-10 18:00:01', '2016-12-10 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('244', '1', '5', '2016-12-10 18:00:01', '2016-12-10 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('245', '1', '1', '2016-12-10 20:00:01', '2016-12-10 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('246', '1', '1', '2016-12-10 22:00:01', '2016-12-10 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('247', '1', '5', '2016-12-11 00:00:01', '2016-12-11 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('248', '1', '1', '2016-12-11 00:00:01', '2016-12-11 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('249', '1', '1', '2016-12-11 02:00:01', '2016-12-11 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('250', '1', '1', '2016-12-11 04:00:01', '2016-12-11 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('251', '1', '5', '2016-12-11 06:00:01', '2016-12-11 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('252', '1', '1', '2016-12-11 06:00:01', '2016-12-11 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('253', '1', '1', '2016-12-11 08:00:01', '2016-12-11 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('254', '1', '1', '2016-12-11 10:00:01', '2016-12-11 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('255', '1', '5', '2016-12-11 12:00:01', '2016-12-11 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('256', '1', '1', '2016-12-11 12:00:01', '2016-12-11 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('257', '1', '1', '2016-12-11 14:00:01', '2016-12-11 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('258', '1', '1', '2016-12-11 16:00:01', '2016-12-11 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('259', '1', '5', '2016-12-11 18:00:01', '2016-12-11 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('260', '1', '1', '2016-12-11 18:00:01', '2016-12-11 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('261', '1', '1', '2016-12-11 20:00:01', '2016-12-11 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('262', '1', '1', '2016-12-11 22:00:01', '2016-12-11 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('263', '1', '1', '2016-12-12 00:00:01', '2016-12-12 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('264', '1', '5', '2016-12-12 00:00:01', '2016-12-12 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('265', '1', '1', '2016-12-12 02:00:01', '2016-12-12 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('266', '1', '1', '2016-12-12 04:00:01', '2016-12-12 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('267', '1', '5', '2016-12-12 06:00:01', '2016-12-12 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('268', '1', '1', '2016-12-12 06:00:01', '2016-12-12 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('269', '1', '1', '2016-12-12 08:00:01', '2016-12-12 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('270', '1', '1', '2016-12-12 10:00:01', '2016-12-12 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('271', '1', '1', '2016-12-12 12:00:01', '2016-12-12 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('272', '1', '5', '2016-12-12 12:00:01', '2016-12-12 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('273', '1', '1', '2016-12-12 14:00:01', '2016-12-12 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('274', '1', '1', '2016-12-12 16:00:01', '2016-12-12 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('275', '1', '5', '2016-12-12 18:00:01', '2016-12-12 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('276', '1', '1', '2016-12-12 18:00:01', '2016-12-12 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('277', '1', '1', '2016-12-12 20:00:01', '2016-12-12 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('278', '1', '1', '2016-12-12 22:00:01', '2016-12-12 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('279', '1', '5', '2016-12-13 00:00:01', '2016-12-13 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('280', '1', '1', '2016-12-13 00:00:01', '2016-12-13 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('281', '1', '1', '2016-12-13 02:00:01', '2016-12-13 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('282', '1', '1', '2016-12-13 04:00:01', '2016-12-13 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('283', '1', '5', '2016-12-13 06:00:01', '2016-12-13 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('284', '1', '1', '2016-12-13 06:00:01', '2016-12-13 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('285', '1', '1', '2016-12-13 08:00:01', '2016-12-13 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('286', '1', '1', '2016-12-13 10:00:01', '2016-12-13 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('287', '1', '5', '2016-12-13 12:00:01', '2016-12-13 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('288', '1', '1', '2016-12-13 12:00:01', '2016-12-13 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('289', '1', '1', '2016-12-13 14:00:01', '2016-12-13 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('290', '1', '1', '2016-12-13 16:00:01', '2016-12-13 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('291', '1', '1', '2016-12-13 18:00:01', '2016-12-13 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('292', '1', '5', '2016-12-13 18:00:01', '2016-12-13 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('293', '1', '1', '2016-12-13 20:00:01', '2016-12-13 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('294', '1', '1', '2016-12-13 22:00:01', '2016-12-13 22:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('295', '1', '5', '2016-12-14 00:00:01', '2016-12-14 00:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('296', '1', '1', '2016-12-14 00:00:01', '2016-12-14 00:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('297', '1', '1', '2016-12-14 02:00:01', '2016-12-14 02:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('298', '1', '1', '2016-12-14 04:00:01', '2016-12-14 04:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('299', '1', '1', '2016-12-14 06:00:01', '2016-12-14 06:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('300', '1', '5', '2016-12-14 06:00:01', '2016-12-14 06:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('301', '1', '1', '2016-12-14 08:00:01', '2016-12-14 08:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('302', '1', '1', '2016-12-14 10:00:01', '2016-12-14 10:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('303', '1', '1', '2016-12-14 12:00:01', '2016-12-14 12:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('304', '1', '5', '2016-12-14 12:00:01', '2016-12-14 12:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('305', '1', '1', '2016-12-14 14:00:01', '2016-12-14 14:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('306', '1', '1', '2016-12-14 16:00:01', '2016-12-14 16:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('307', '1', '1', '2016-12-14 18:00:01', '2016-12-14 18:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('308', '1', '5', '2016-12-14 18:00:01', '2016-12-14 18:00:01', '1', 'Template not found for name \"/site_1/publishCategory.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');
INSERT INTO `log_task` VALUES ('309', '1', '1', '2016-12-14 20:00:01', '2016-12-14 20:00:01', '1', 'Template not found for name \"/site_1/publishPage.task\".\nThe name was interpreted by this TemplateLoader: FileTemplateLoader(baseDir=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task\", canonicalBasePath=\"/usr/local/apache-tomcat-8.0.26/webapps/jspxgl_zeppin/cms-V2016.0828/resource/task/\").');

-- ----------------------------
-- Table structure for `log_upload`
-- ----------------------------
DROP TABLE IF EXISTS `log_upload`;
CREATE TABLE `log_upload` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `channel` varchar(50) NOT NULL COMMENT '操作取到',
  `image` tinyint(1) NOT NULL COMMENT '图片',
  `file_size` bigint(20) NOT NULL COMMENT '文件大小',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `file_path` varchar(500) NOT NULL COMMENT '文件路径',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`),
  KEY `ip` (`ip`),
  KEY `site_id` (`site_id`),
  KEY `channel` (`channel`),
  KEY `image` (`image`),
  KEY `file_size` (`file_size`)
) ENGINE=MyISAM AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 COMMENT='上传日志';

-- ----------------------------
-- Records of log_upload
-- ----------------------------
INSERT INTO `log_upload` VALUES ('1', '1', '1', 'web_manager', '0', '5089', '106.2.184.219', '2016-11-28 11:37:54', '2016/11/28/11-37-540499-1819752166.png');
INSERT INTO `log_upload` VALUES ('2', '1', '1', 'web_manager', '0', '11716', '106.2.184.219', '2016-11-28 12:19:37', '2016/11/28/12-19-370268-1448288023.png');
INSERT INTO `log_upload` VALUES ('3', '1', '1', 'web_manager', '0', '20771', '106.2.184.219', '2016-11-28 15:51:08', '2016/11/28/15-51-0805111831391442.png');
INSERT INTO `log_upload` VALUES ('4', '1', '1', 'web_manager', '1', '5089', '106.2.184.219', '2016-11-28 17:34:06', '2016/11/28/17-34-0602051880043443.png');
INSERT INTO `log_upload` VALUES ('5', '1', '1', 'web_manager', '1', '11716', '106.2.184.219', '2016-11-28 17:34:22', '2016/11/28/17-34-2203261376104339.png');
INSERT INTO `log_upload` VALUES ('6', '1', '1', 'web_manager', '1', '14939', '106.2.184.219', '2016-11-28 17:34:34', '2016/11/28/17-34-340036-1500223533.png');
INSERT INTO `log_upload` VALUES ('7', '1', '1', 'web_manager', '1', '968895', '106.2.184.219', '2016-11-28 17:42:51', '2016/11/28/17-42-510576821903819.png');
INSERT INTO `log_upload` VALUES ('8', '1', '1', 'web_manager', '0', '11794', '120.205.17.124', '2016-12-01 16:22:25', '2016/12/01/16-22-2508721758581462.docx');
INSERT INTO `log_upload` VALUES ('9', '1', '1', 'web_manager', '0', '53322', '120.205.17.124', '2016-12-01 16:30:20', '2016/12/01/16-30-200379-1779416023.pdf');
INSERT INTO `log_upload` VALUES ('10', '1', '1', 'web_manager', '0', '18934', '120.205.17.124', '2016-12-01 16:38:31', '2016/12/01/16-38-310155526336540.jpg');
INSERT INTO `log_upload` VALUES ('11', '1', '1', 'web_manager', '0', '1349673', '120.205.17.124', '2016-12-01 17:37:29', '2016/12/01/17-37-290366-629943869.pdf');
INSERT INTO `log_upload` VALUES ('12', '1', '1', 'web_manager', '0', '1766464', '120.205.17.124', '2016-12-01 17:37:36', '2016/12/01/17-37-3609521684647795.pdf');
INSERT INTO `log_upload` VALUES ('13', '1', '1', 'web_manager', '0', '795032', '120.205.17.124', '2016-12-01 17:37:48', '2016/12/01/17-37-480149-1472558327.pdf');
INSERT INTO `log_upload` VALUES ('14', '1', '1', 'web_manager', '0', '1552623', '120.205.17.124', '2016-12-01 17:37:54', '2016/12/01/17-37-540920-232625671.pdf');
INSERT INTO `log_upload` VALUES ('15', '1', '1', 'web_manager', '0', '441506', '120.205.17.124', '2016-12-01 17:38:01', '2016/12/01/17-38-0107451756507355.pdf');
INSERT INTO `log_upload` VALUES ('16', '1', '1', 'web_manager', '0', '1609652', '120.205.17.124', '2016-12-01 17:38:09', '2016/12/01/17-38-090458-177335222.pdf');
INSERT INTO `log_upload` VALUES ('17', '1', '1', 'web_manager', '0', '1707759', '120.205.17.124', '2016-12-01 17:38:17', '2016/12/01/17-38-170284-656730871.pdf');
INSERT INTO `log_upload` VALUES ('18', '1', '1', 'web_manager', '0', '2814213', '120.205.17.124', '2016-12-01 17:38:28', '2016/12/01/17-38-280730326815639.pdf');
INSERT INTO `log_upload` VALUES ('19', '1', '1', 'web_manager', '0', '2639035', '120.205.17.124', '2016-12-01 17:38:39', '2016/12/01/17-38-390999-1562474359.pdf');
INSERT INTO `log_upload` VALUES ('20', '1', '1', 'web_manager', '0', '2528403', '120.205.17.124', '2016-12-01 17:38:51', '2016/12/01/17-38-510173-94165018.pdf');
INSERT INTO `log_upload` VALUES ('21', '1', '1', 'web_manager', '0', '2207797', '120.205.17.124', '2016-12-01 17:38:59', '2016/12/01/17-38-5908211385932630.pdf');
INSERT INTO `log_upload` VALUES ('22', '1', '1', 'web_manager', '0', '2337718', '120.205.17.124', '2016-12-01 17:39:09', '2016/12/01/17-39-0906001995910535.pdf');
INSERT INTO `log_upload` VALUES ('23', '1', '1', 'web_manager', '0', '1974153', '120.205.17.124', '2016-12-01 17:39:17', '2016/12/01/17-39-1701821577716472.pdf');
INSERT INTO `log_upload` VALUES ('24', '1', '1', 'web_manager', '0', '1403135', '120.205.17.124', '2016-12-01 17:39:23', '2016/12/01/17-39-2301991364371514.pdf');
INSERT INTO `log_upload` VALUES ('25', '1', '1', 'web_manager', '0', '1826351', '120.205.17.124', '2016-12-01 17:39:31', '2016/12/01/17-39-3103061305094146.pdf');
INSERT INTO `log_upload` VALUES ('26', '1', '1', 'web_manager', '0', '367954', '120.205.17.124', '2016-12-01 17:56:53', '2016/12/01/17-56-530175703818399.pdf');
INSERT INTO `log_upload` VALUES ('27', '1', '1', 'web_manager', '0', '514756', '120.205.17.124', '2016-12-01 17:56:58', '2016/12/01/17-56-5809911106786315.pdf');
INSERT INTO `log_upload` VALUES ('28', '1', '1', 'web_manager', '0', '301717', '120.205.17.124', '2016-12-01 17:57:04', '2016/12/01/17-57-040453-1627385626.pdf');
INSERT INTO `log_upload` VALUES ('29', '1', '1', 'web_manager', '0', '1007745', '120.205.17.124', '2016-12-01 17:57:11', '2016/12/01/17-57-110601-92405517.pdf');
INSERT INTO `log_upload` VALUES ('30', '1', '1', 'web_manager', '0', '295014', '120.205.17.124', '2016-12-01 17:57:19', '2016/12/01/17-57-190950989524953.pdf');
INSERT INTO `log_upload` VALUES ('31', '1', '1', 'web_manager', '0', '460593', '120.205.17.124', '2016-12-01 17:57:26', '2016/12/01/17-57-2600871089605485.pdf');
INSERT INTO `log_upload` VALUES ('32', '1', '1', 'web_manager', '0', '350559', '120.205.17.124', '2016-12-01 17:57:31', '2016/12/01/17-57-310955323117496.pdf');
INSERT INTO `log_upload` VALUES ('33', '1', '1', 'web_manager', '0', '574968', '120.205.17.124', '2016-12-01 17:57:37', '2016/12/01/17-57-3703101021066782.pdf');
INSERT INTO `log_upload` VALUES ('34', '1', '1', 'web_manager', '0', '299040', '120.205.17.124', '2016-12-01 17:57:43', '2016/12/01/17-57-43078398324602.pdf');
INSERT INTO `log_upload` VALUES ('35', '1', '1', 'web_manager', '0', '1007700', '120.205.17.124', '2016-12-01 17:57:51', '2016/12/01/17-57-5100601550088165.pdf');
INSERT INTO `log_upload` VALUES ('36', '1', '1', 'web_manager', '0', '314783', '120.205.17.124', '2016-12-01 17:57:57', '2016/12/01/17-57-5702631045776756.pdf');
INSERT INTO `log_upload` VALUES ('37', '1', '1', 'web_manager', '0', '460445', '120.205.17.124', '2016-12-01 17:58:03', '2016/12/01/17-58-020999-775759115.pdf');
INSERT INTO `log_upload` VALUES ('38', '1', '1', 'web_manager', '0', '1260044', '120.205.17.124', '2016-12-01 17:58:09', '2016/12/01/17-58-090529-1222683530.pdf');
INSERT INTO `log_upload` VALUES ('39', '1', '1', 'web_manager', '0', '1053266', '120.205.17.124', '2016-12-01 17:58:16', '2016/12/01/17-58-160224-72774674.pdf');
INSERT INTO `log_upload` VALUES ('40', '1', '1', 'web_manager', '0', '731640', '120.205.17.124', '2016-12-01 17:58:21', '2016/12/01/17-58-210623-808732457.pdf');
INSERT INTO `log_upload` VALUES ('41', '1', '1', 'web_manager', '0', '851222', '120.205.17.124', '2016-12-01 17:58:27', '2016/12/01/17-58-270232-1948847330.pdf');
INSERT INTO `log_upload` VALUES ('42', '1', '1', 'web_manager', '0', '4424213', '120.205.17.124', '2016-12-01 17:58:46', '2016/12/01/17-58-460262146904338.pdf');
INSERT INTO `log_upload` VALUES ('43', '1', '1', 'web_manager', '0', '1058994', '120.205.17.124', '2016-12-01 18:10:35', '2016/12/01/18-10-3505321480694662.pdf');
INSERT INTO `log_upload` VALUES ('44', '1', '1', 'web_manager', '0', '1691881', '120.205.17.124', '2016-12-01 18:10:44', '2016/12/01/18-10-4408501776951769.pdf');
INSERT INTO `log_upload` VALUES ('45', '1', '1', 'web_manager', '0', '1092010', '120.205.17.124', '2016-12-01 18:10:51', '2016/12/01/18-10-510112-1334261148.pdf');
INSERT INTO `log_upload` VALUES ('46', '1', '1', 'web_manager', '0', '1712703', '120.205.17.124', '2016-12-01 18:10:57', '2016/12/01/18-10-57048936897739.pdf');
INSERT INTO `log_upload` VALUES ('47', '1', '1', 'web_manager', '0', '1784632', '120.205.17.124', '2016-12-01 18:20:54', '2016/12/01/18-20-540638-1313212602.pdf');
INSERT INTO `log_upload` VALUES ('48', '1', '1', 'web_manager', '0', '1176433', '120.205.17.124', '2016-12-01 18:21:01', '2016/12/01/18-21-010608-1546721485.pdf');
INSERT INTO `log_upload` VALUES ('49', '1', '1', 'web_manager', '0', '966773', '120.205.17.124', '2016-12-01 18:21:07', '2016/12/01/18-21-0704412094784498.pdf');
INSERT INTO `log_upload` VALUES ('50', '1', '1', 'web_manager', '0', '949620', '120.205.17.124', '2016-12-01 18:21:12', '2016/12/01/18-21-120778281465718.pdf');
INSERT INTO `log_upload` VALUES ('51', '1', '1', 'web_manager', '0', '1012544', '120.205.17.124', '2016-12-01 18:21:24', '2016/12/01/18-21-240009-1731405646.pdf');
INSERT INTO `log_upload` VALUES ('52', '1', '1', 'web_manager', '0', '1854983', '120.205.17.124', '2016-12-01 18:21:32', '2016/12/01/18-21-320035-1238122464.pdf');
INSERT INTO `log_upload` VALUES ('53', '1', '1', 'web_manager', '0', '1694863', '120.205.17.124', '2016-12-01 18:21:38', '2016/12/01/18-21-3803401689288893.pdf');
INSERT INTO `log_upload` VALUES ('54', '1', '1', 'web_manager', '0', '11190938', '120.205.17.124', '2016-12-01 18:29:16', '2016/12/01/18-29-160224-1435825240.pdf');
INSERT INTO `log_upload` VALUES ('55', '1', '1', 'web_manager', '0', '13631259', '120.205.17.124', '2016-12-01 18:31:20', '2016/12/01/18-31-200067-1104741422.pdf');
INSERT INTO `log_upload` VALUES ('56', '1', '1', 'web_manager', '0', '887064', '120.205.17.124', '2016-12-01 18:33:31', '2016/12/01/18-33-310130-100985754.pdf');
INSERT INTO `log_upload` VALUES ('57', '1', '1', 'web_manager', '0', '752010', '120.205.17.124', '2016-12-01 18:33:40', '2016/12/01/18-33-4006821085246786.pdf');
INSERT INTO `log_upload` VALUES ('58', '1', '1', 'web_manager', '0', '359557', '120.205.17.124', '2016-12-01 18:33:46', '2016/12/01/18-33-460981213610071.pdf');
INSERT INTO `log_upload` VALUES ('59', '1', '1', 'web_manager', '0', '887064', '120.205.17.124', '2016-12-01 18:37:16', '2016/12/01/18-37-160604854900225.pdf');
INSERT INTO `log_upload` VALUES ('60', '1', '1', 'web_manager', '0', '752010', '120.205.17.124', '2016-12-01 18:37:23', '2016/12/01/18-37-2300651902679191.pdf');
INSERT INTO `log_upload` VALUES ('61', '1', '1', 'web_manager', '0', '359557', '120.205.17.124', '2016-12-01 18:37:28', '2016/12/01/18-37-280730-349149583.pdf');
INSERT INTO `log_upload` VALUES ('62', '1', '1', 'web_manager', '0', '357416', '120.205.17.124', '2016-12-01 18:45:05', '2016/12/01/18-45-050011-1532324927.pdf');
INSERT INTO `log_upload` VALUES ('63', '1', '1', 'web_manager', '0', '673609', '120.205.17.124', '2016-12-01 18:45:11', '2016/12/01/18-45-110664-383658492.pdf');
INSERT INTO `log_upload` VALUES ('64', '1', '1', 'web_manager', '0', '1177633', '120.205.17.124', '2016-12-01 18:45:18', '2016/12/01/18-45-180064-1391160684.pdf');
INSERT INTO `log_upload` VALUES ('65', '1', '1', 'web_manager', '0', '344816', '120.205.17.124', '2016-12-01 18:45:23', '2016/12/01/18-45-2309951862022632.pdf');
INSERT INTO `log_upload` VALUES ('66', '1', '1', 'web_manager', '0', '747213', '120.205.17.124', '2016-12-01 18:45:29', '2016/12/01/18-45-2904272034655372.pdf');
INSERT INTO `log_upload` VALUES ('67', '1', '1', 'web_manager', '0', '612592', '120.205.17.124', '2016-12-01 18:45:44', '2016/12/01/18-45-440797-866911907.pdf');
INSERT INTO `log_upload` VALUES ('68', '1', '1', 'web_manager', '0', '771183', '120.205.17.124', '2016-12-01 18:45:52', '2016/12/01/18-45-520863-526524846.pdf');
INSERT INTO `log_upload` VALUES ('69', '1', '1', 'web_manager', '0', '753389', '120.205.17.124', '2016-12-01 18:46:00', '2016/12/01/18-46-000854537986404.pdf');
INSERT INTO `log_upload` VALUES ('70', '1', '1', 'web_manager', '0', '967062', '120.205.17.124', '2016-12-01 18:46:07', '2016/12/01/18-46-070696885733934.pdf');
INSERT INTO `log_upload` VALUES ('71', '1', '1', 'web_manager', '0', '766751', '120.205.17.124', '2016-12-01 18:46:42', '2016/12/01/18-46-420834-463863071.pdf');
INSERT INTO `log_upload` VALUES ('72', '1', '1', 'web_manager', '0', '1910180', '120.205.17.124', '2016-12-01 18:48:35', '2016/12/01/18-48-3508551360407264.pdf');
INSERT INTO `log_upload` VALUES ('73', '1', '1', 'web_manager', '0', '1268839', '120.205.17.124', '2016-12-01 18:48:43', '2016/12/01/18-48-430109-1707452044.pdf');
INSERT INTO `log_upload` VALUES ('74', '1', '1', 'web_manager', '0', '1058535', '120.205.17.124', '2016-12-01 18:48:48', '2016/12/01/18-48-4808081552653710.pdf');
INSERT INTO `log_upload` VALUES ('75', '1', '1', 'web_manager', '0', '469615', '120.205.17.124', '2016-12-01 18:52:53', '2016/12/01/18-52-5309981263431304.pdf');
INSERT INTO `log_upload` VALUES ('76', '1', '1', 'web_manager', '0', '404504', '120.205.17.124', '2016-12-01 18:52:59', '2016/12/01/18-52-590753476135336.pdf');
INSERT INTO `log_upload` VALUES ('77', '1', '1', 'web_manager', '0', '625531', '120.205.17.124', '2016-12-01 18:53:04', '2016/12/01/18-53-040910-1920187537.pdf');
INSERT INTO `log_upload` VALUES ('78', '1', '1', 'web_manager', '0', '386704', '120.205.17.124', '2016-12-01 18:53:10', '2016/12/01/18-53-100021409467883.pdf');
INSERT INTO `log_upload` VALUES ('79', '1', '1', 'web_manager', '0', '1812917', '120.205.17.124', '2016-12-01 18:57:27', '2016/12/01/18-57-270164642072196.pdf');
INSERT INTO `log_upload` VALUES ('80', '1', '1', 'web_manager', '0', '1460602', '120.205.17.124', '2016-12-01 18:57:35', '2016/12/01/18-57-350362-578848268.pdf');
INSERT INTO `log_upload` VALUES ('81', '1', '1', 'web_manager', '0', '2906703', '120.205.17.124', '2016-12-01 18:57:46', '2016/12/01/18-57-460696-2144788825.pdf');
INSERT INTO `log_upload` VALUES ('82', '1', '1', 'web_manager', '0', '2456674', '120.205.17.124', '2016-12-01 18:57:56', '2016/12/01/18-57-560341-538115587.pdf');
INSERT INTO `log_upload` VALUES ('83', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-01 23:53:55', '2016/12/01/23-53-5503991548393876.png');
INSERT INTO `log_upload` VALUES ('84', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-01 23:55:33', '2016/12/01/23-55-330855-633314636.png');
INSERT INTO `log_upload` VALUES ('85', '1', '1', 'web_manager', '0', '81304', '124.117.175.141', '2016-12-01 23:56:35', '2016/12/01/23-56-350754-1098289954.png');
INSERT INTO `log_upload` VALUES ('86', '1', '1', 'web_manager', '0', '36790', '124.117.175.141', '2016-12-01 23:56:41', '2016/12/01/23-56-410711736931161.png');
INSERT INTO `log_upload` VALUES ('87', '1', '1', 'web_manager', '0', '39939', '124.117.175.141', '2016-12-01 23:56:47', '2016/12/01/23-56-470063771281561.png');
INSERT INTO `log_upload` VALUES ('88', '1', '1', 'web_manager', '0', '63079', '124.117.175.141', '2016-12-01 23:56:52', '2016/12/01/23-56-520574727866494.png');
INSERT INTO `log_upload` VALUES ('89', '1', '1', 'web_manager', '0', '47754', '124.117.175.141', '2016-12-01 23:56:57', '2016/12/01/23-56-570472-889158152.png');
INSERT INTO `log_upload` VALUES ('90', '1', '1', 'web_manager', '0', '48077', '124.117.175.141', '2016-12-01 23:57:02', '2016/12/01/23-57-020443969462809.png');
INSERT INTO `log_upload` VALUES ('91', '1', '1', 'web_manager', '0', '100382', '124.117.175.141', '2016-12-01 23:57:06', '2016/12/01/23-57-060931-1790879383.png');
INSERT INTO `log_upload` VALUES ('92', '1', '1', 'web_manager', '0', '52292', '124.117.175.141', '2016-12-01 23:57:10', '2016/12/01/23-57-100827-319771179.png');
INSERT INTO `log_upload` VALUES ('93', '1', '1', 'web_manager', '0', '75494', '124.117.175.141', '2016-12-01 23:57:15', '2016/12/01/23-57-150354-508555534.png');
INSERT INTO `log_upload` VALUES ('94', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 01:23:08', '2016/12/02/01-23-080542938214075.png');
INSERT INTO `log_upload` VALUES ('95', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 01:23:14', '2016/12/02/01-23-140785309212386.png');
INSERT INTO `log_upload` VALUES ('96', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 01:26:19', '2016/12/02/01-26-190376-1174312019.png');
INSERT INTO `log_upload` VALUES ('97', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 01:26:24', '2016/12/02/01-26-240911-221811694.png');
INSERT INTO `log_upload` VALUES ('98', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 01:32:55', '2016/12/02/01-32-550660861393993.png');
INSERT INTO `log_upload` VALUES ('99', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 01:33:01', '2016/12/02/01-33-010619773705282.png');
INSERT INTO `log_upload` VALUES ('100', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 01:33:10', '2016/12/02/01-33-100903151586862.png');
INSERT INTO `log_upload` VALUES ('101', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 01:33:17', '2016/12/02/01-33-170839-1727290784.png');
INSERT INTO `log_upload` VALUES ('102', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 01:33:25', '2016/12/02/01-33-250338-1828837190.png');
INSERT INTO `log_upload` VALUES ('103', '1', '1', 'web_manager', '0', '32609', '124.117.175.141', '2016-12-02 01:33:53', '2016/12/02/01-33-53015721820854.png');
INSERT INTO `log_upload` VALUES ('104', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 01:40:00', '2016/12/02/01-40-000019-183400008.png');
INSERT INTO `log_upload` VALUES ('105', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 01:40:05', '2016/12/02/01-40-050530-1854448556.png');
INSERT INTO `log_upload` VALUES ('106', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 01:40:11', '2016/12/02/01-40-110897957144472.png');
INSERT INTO `log_upload` VALUES ('107', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 01:40:17', '2016/12/02/01-40-170247-643311568.png');
INSERT INTO `log_upload` VALUES ('108', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 01:44:47', '2016/12/02/01-44-4706901753198119.png');
INSERT INTO `log_upload` VALUES ('109', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 01:44:54', '2016/12/02/01-44-540399-1987443248.png');
INSERT INTO `log_upload` VALUES ('110', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 01:44:59', '2016/12/02/01-44-590101-894833438.png');
INSERT INTO `log_upload` VALUES ('111', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 01:45:05', '2016/12/02/01-45-050137-90964355.png');
INSERT INTO `log_upload` VALUES ('112', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 01:52:39', '2016/12/02/01-52-390034-940540329.png');
INSERT INTO `log_upload` VALUES ('113', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 01:52:46', '2016/12/02/01-52-4606871742222159.png');
INSERT INTO `log_upload` VALUES ('114', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 01:52:51', '2016/12/02/01-52-510054-1911024077.png');
INSERT INTO `log_upload` VALUES ('115', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 01:52:56', '2016/12/02/01-52-5608961207085462.png');
INSERT INTO `log_upload` VALUES ('116', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 01:56:25', '2016/12/02/01-56-250331996021738.png');
INSERT INTO `log_upload` VALUES ('117', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 01:56:33', '2016/12/02/01-56-330263-1663121313.png');
INSERT INTO `log_upload` VALUES ('118', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 01:56:37', '2016/12/02/01-56-370777-1810205149.png');
INSERT INTO `log_upload` VALUES ('119', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 01:56:42', '2016/12/02/01-56-420577-1480843211.png');
INSERT INTO `log_upload` VALUES ('120', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 01:59:20', '2016/12/02/01-59-200799-1587371192.png');
INSERT INTO `log_upload` VALUES ('121', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 01:59:25', '2016/12/02/01-59-2508771681756492.png');
INSERT INTO `log_upload` VALUES ('122', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 01:59:31', '2016/12/02/01-59-310622-303058964.png');
INSERT INTO `log_upload` VALUES ('123', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 01:59:36', '2016/12/02/01-59-3602621082769835.png');
INSERT INTO `log_upload` VALUES ('124', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 02:02:38', '2016/12/02/02-02-3802391885135847.png');
INSERT INTO `log_upload` VALUES ('125', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 02:02:43', '2016/12/02/02-02-430665-275430371.png');
INSERT INTO `log_upload` VALUES ('126', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 02:02:48', '2016/12/02/02-02-480760-610038448.png');
INSERT INTO `log_upload` VALUES ('127', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 02:02:53', '2016/12/02/02-02-530694-1506938384.png');
INSERT INTO `log_upload` VALUES ('128', '1', '1', 'web_manager', '0', '927797', '124.117.175.141', '2016-12-02 02:06:48', '2016/12/02/02-06-480961732537035.png');
INSERT INTO `log_upload` VALUES ('129', '1', '1', 'web_manager', '0', '193479', '124.117.175.141', '2016-12-02 02:06:53', '2016/12/02/02-06-530785932191428.png');
INSERT INTO `log_upload` VALUES ('130', '1', '1', 'web_manager', '0', '2862', '124.117.175.141', '2016-12-02 02:07:00', '2016/12/02/02-07-000070-1593820998.png');
INSERT INTO `log_upload` VALUES ('131', '1', '1', 'web_manager', '0', '3431', '124.117.175.141', '2016-12-02 02:07:06', '2016/12/02/02-07-060262265695995.png');
INSERT INTO `log_upload` VALUES ('132', '1', '1', 'web_manager', '0', '1599673', '120.205.17.124', '2016-12-02 09:45:24', '2016/12/02/09-45-2400761146744656.pdf');
INSERT INTO `log_upload` VALUES ('133', '1', '1', 'web_manager', '0', '2191611', '120.205.17.124', '2016-12-02 09:45:33', '2016/12/02/09-45-330158341543056.pdf');
INSERT INTO `log_upload` VALUES ('134', '1', '1', 'web_manager', '0', '2601882', '120.205.17.124', '2016-12-02 09:45:46', '2016/12/02/09-45-460099-396986973.pdf');
INSERT INTO `log_upload` VALUES ('135', '1', '1', 'web_manager', '0', '1630314', '120.205.17.124', '2016-12-02 09:47:11', '2016/12/02/09-47-110975-31129772.pdf');
INSERT INTO `log_upload` VALUES ('136', '1', '1', 'web_manager', '0', '1356520', '120.205.17.124', '2016-12-02 09:47:19', '2016/12/02/09-47-190511-452576729.pdf');
INSERT INTO `log_upload` VALUES ('137', '1', '1', 'web_manager', '0', '1992562', '120.205.17.124', '2016-12-02 09:47:34', '2016/12/02/09-47-3402581749849317.pdf');

-- ----------------------------
-- Table structure for `plugin_lottery`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_lottery`;
CREATE TABLE `plugin_lottery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `start_date` datetime NOT NULL COMMENT '开始日期',
  `end_date` datetime NOT NULL COMMENT '结束日期',
  `interval_hour` int(11) NOT NULL COMMENT '抽奖间隔小时',
  `gift` int(11) NOT NULL COMMENT '每次可抽奖数量',
  `total_gift` int(11) NOT NULL COMMENT '奖品总数',
  `last_gift` int(11) NOT NULL COMMENT '剩余数量',
  `lottery_count` int(11) NOT NULL COMMENT '可抽奖次数',
  `fractions` int(11) NOT NULL COMMENT '概率分子',
  `numerator` int(11) NOT NULL COMMENT '概率分母',
  `url` varchar(2048) DEFAULT NULL COMMENT '地址',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `extend_id` int(11) DEFAULT NULL COMMENT '扩展ID',
  PRIMARY KEY (`id`),
  KEY `start_date` (`start_date`,`end_date`),
  KEY `disabled` (`disabled`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plugin_lottery
-- ----------------------------

-- ----------------------------
-- Table structure for `plugin_lottery_user`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_lottery_user`;
CREATE TABLE `plugin_lottery_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_id` int(11) NOT NULL COMMENT '抽奖ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `winning` tinyint(1) NOT NULL COMMENT '是否中奖',
  `ip` varchar(64) NOT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `lottery_id` (`lottery_id`),
  KEY `user_id` (`user_id`),
  KEY `winning` (`winning`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plugin_lottery_user
-- ----------------------------

-- ----------------------------
-- Table structure for `plugin_lottery_user_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_lottery_user_attribute`;
CREATE TABLE `plugin_lottery_user_attribute` (
  `lottery_user_id` bigint(20) NOT NULL COMMENT '抽奖用户ID',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY (`lottery_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='抽奖用户扩展';

-- ----------------------------
-- Records of plugin_lottery_user_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for `plugin_site`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_site`;
CREATE TABLE `plugin_site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点',
  `plugin_code` varchar(50) NOT NULL COMMENT '插件',
  `widget_template` varchar(255) DEFAULT NULL COMMENT '内容插件模板',
  `static_template` varchar(255) DEFAULT NULL COMMENT '静态化模板',
  `path` varchar(2000) DEFAULT NULL COMMENT '静态化路径',
  PRIMARY KEY (`id`),
  UNIQUE KEY `site_id` (`site_id`,`plugin_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plugin_site
-- ----------------------------

-- ----------------------------
-- Table structure for `plugin_vote`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_vote`;
CREATE TABLE `plugin_vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `start_date` datetime NOT NULL COMMENT '开始日期',
  `end_date` datetime NOT NULL COMMENT '结束日期',
  `interval_hour` int(11) NOT NULL COMMENT '投票间隔小时',
  `max_vote` int(11) NOT NULL COMMENT '最大投票数',
  `anonymous` tinyint(1) NOT NULL COMMENT '匿名投票',
  `user_counts` int(11) NOT NULL COMMENT '参与用户数',
  `url` varchar(2048) NOT NULL COMMENT '地址',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  `item_extend_id` int(11) NOT NULL COMMENT '扩展ID',
  PRIMARY KEY (`id`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plugin_vote
-- ----------------------------

-- ----------------------------
-- Table structure for `plugin_vote_item`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_vote_item`;
CREATE TABLE `plugin_vote_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vote_id` int(11) NOT NULL COMMENT '投票',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `scores` int(11) NOT NULL COMMENT '票数',
  `sort` int(11) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `lottery_id` (`vote_id`),
  KEY `user_id` (`title`),
  KEY `create_date` (`sort`),
  KEY `scores` (`scores`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plugin_vote_item
-- ----------------------------

-- ----------------------------
-- Table structure for `plugin_vote_item_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_vote_item_attribute`;
CREATE TABLE `plugin_vote_item_attribute` (
  `vote_item_id` bigint(20) NOT NULL COMMENT '选项ID',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY (`vote_item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='投票选项扩展';

-- ----------------------------
-- Records of plugin_vote_item_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for `plugin_vote_user`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_vote_user`;
CREATE TABLE `plugin_vote_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_id` int(11) NOT NULL COMMENT '抽奖ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_ids` text NOT NULL COMMENT '投票选项',
  `ip` varchar(64) NOT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `lottery_id` (`lottery_id`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plugin_vote_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_app`
-- ----------------------------
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `app_key` varchar(50) NOT NULL COMMENT 'APP key',
  `app_secret` varchar(50) NOT NULL COMMENT 'APP secret',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`app_key`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_app
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_app_client`
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_client`;
CREATE TABLE `sys_app_client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `channel` varchar(20) NOT NULL COMMENT '渠道',
  `uuid` varchar(50) NOT NULL COMMENT '唯一标识',
  `user_id` bigint(20) DEFAULT NULL COMMENT '绑定用户',
  `client_version` varchar(50) NOT NULL COMMENT '版本',
  `allow_push` tinyint(1) NOT NULL COMMENT '允许推送',
  `push_token` varchar(50) DEFAULT NULL COMMENT '推送授权码',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '上次登录IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `site_id` (`site_id`,`uuid`,`channel`),
  KEY `user_id` (`user_id`),
  KEY `disabled` (`disabled`),
  KEY `create_date` (`create_date`),
  KEY `allow_push` (`allow_push`),
  KEY `channel` (`channel`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_app_client
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_app_token`
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_token`;
CREATE TABLE `sys_app_token` (
  `auth_token` varchar(40) NOT NULL COMMENT '授权验证',
  `app_id` int(11) NOT NULL COMMENT '应用ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`auth_token`),
  KEY `app_id` (`app_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_app_token
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_cluster`
-- ----------------------------
DROP TABLE IF EXISTS `sys_cluster`;
CREATE TABLE `sys_cluster` (
  `uuid` varchar(40) NOT NULL COMMENT 'uuid',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `heartbeat_date` datetime NOT NULL COMMENT '心跳时间',
  `master` tinyint(1) NOT NULL COMMENT '是否管理',
  PRIMARY KEY (`uuid`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='服务器集群';

-- ----------------------------
-- Records of sys_cluster
-- ----------------------------
INSERT INTO `sys_cluster` VALUES ('455188f7-fcfb-4c1e-ae1d-48dd537676aa', '2016-11-30 00:27:36', '2016-12-14 21:59:44', '0');
INSERT INTO `sys_cluster` VALUES ('632affeb-c463-4292-9e2a-96dd00e1a642', '2016-11-30 00:27:30', '2016-12-14 21:59:03', '1');

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `code` varchar(50) NOT NULL COMMENT '配置项编码',
  `subcode` varchar(50) NOT NULL COMMENT '子编码',
  `data` longtext NOT NULL COMMENT '值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `site_id` (`site_id`,`code`,`subcode`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='站点配置';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父部门ID',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `user_id` bigint(20) DEFAULT NULL COMMENT '负责人',
  `owns_all_category` tinyint(1) NOT NULL COMMENT '拥有全部分类权限',
  `owns_all_page` tinyint(1) NOT NULL COMMENT '拥有全部页面权限',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '1', '总公司', null, '集团总公司', '1', '1', '1');
INSERT INTO `sys_dept` VALUES ('2', '2', '技术部', null, '', '3', '1', '1');

-- ----------------------------
-- Table structure for `sys_dept_category`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_category`;
CREATE TABLE `sys_dept_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `category_id` int(11) NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`),
  KEY `dept_id` (`dept_id`),
  KEY `category_id` (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='部门分类';

-- ----------------------------
-- Records of sys_dept_category
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dept_page`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_page`;
CREATE TABLE `sys_dept_page` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `page` varchar(255) NOT NULL COMMENT '页面',
  PRIMARY KEY (`id`),
  KEY `dept_id` (`dept_id`),
  KEY `page` (`page`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='部门页面';

-- ----------------------------
-- Records of sys_dept_page
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_domain`
-- ----------------------------
DROP TABLE IF EXISTS `sys_domain`;
CREATE TABLE `sys_domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '域名',
  `site_id` int(11) NOT NULL COMMENT '站点',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`),
  KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='域名';

-- ----------------------------
-- Records of sys_domain
-- ----------------------------
INSERT INTO `sys_domain` VALUES ('1', 'dev.publiccms.com', '1', '');
INSERT INTO `sys_domain` VALUES ('2', 'dev.publiccms.com:8080', '1', '');
INSERT INTO `sys_domain` VALUES ('3', 'member.dev.publiccms.com', '1', '/member/');
INSERT INTO `sys_domain` VALUES ('4', 'member.dev.publiccms.com:8080', '1', '/member/');
INSERT INTO `sys_domain` VALUES ('5', 'search.dev.publiccms.com', '1', '/search/');
INSERT INTO `sys_domain` VALUES ('6', 'search.dev.publiccms.com:8080', '1', '/search/');
INSERT INTO `sys_domain` VALUES ('7', 'site2.dev.publiccms.com', '2', '');
INSERT INTO `sys_domain` VALUES ('8', 'site2.dev.publiccms.com:8080', '2', '');

-- ----------------------------
-- Table structure for `sys_email_token`
-- ----------------------------
DROP TABLE IF EXISTS `sys_email_token`;
CREATE TABLE `sys_email_token` (
  `auth_token` varchar(40) NOT NULL COMMENT '验证码',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `email` varchar(100) NOT NULL COMMENT '邮件地址',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`auth_token`),
  KEY `create_date` (`create_date`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='邮件地址验证日志';

-- ----------------------------
-- Records of sys_email_token
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_extend`
-- ----------------------------
DROP TABLE IF EXISTS `sys_extend`;
CREATE TABLE `sys_extend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_type` varchar(20) NOT NULL COMMENT '扩展类型',
  `item_id` int(11) NOT NULL COMMENT '扩展项目ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_extend
-- ----------------------------
INSERT INTO `sys_extend` VALUES ('1', 'model', '7');
INSERT INTO `sys_extend` VALUES ('2', 'category', '19');

-- ----------------------------
-- Table structure for `sys_extend_field`
-- ----------------------------
DROP TABLE IF EXISTS `sys_extend_field`;
CREATE TABLE `sys_extend_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `extend_id` int(11) NOT NULL COMMENT '扩展ID',
  `required` tinyint(1) NOT NULL COMMENT '是否必填',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `description` varchar(100) DEFAULT NULL COMMENT '解释',
  `code` varchar(20) NOT NULL COMMENT '编码',
  `input_type` varchar(20) NOT NULL COMMENT '表单类型',
  `default_value` varchar(50) DEFAULT NULL COMMENT '默认值',
  PRIMARY KEY (`id`),
  KEY `item_id` (`extend_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='扩展';

-- ----------------------------
-- Records of sys_extend_field
-- ----------------------------
INSERT INTO `sys_extend_field` VALUES ('2', '1', '1', '价格', '', 'price', 'number', '');

-- ----------------------------
-- Table structure for `sys_ftp_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_ftp_user`;
CREATE TABLE `sys_ftp_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_ftp_user
-- ----------------------------
INSERT INTO `sys_ftp_user` VALUES ('1', '1', 'admin', '21232f297a57a5a743894a0e4a801fc3', null);

-- ----------------------------
-- Table structure for `sys_moudle`
-- ----------------------------
DROP TABLE IF EXISTS `sys_moudle`;
CREATE TABLE `sys_moudle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `url` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `authorized_url` text COMMENT '授权地址',
  `attached` varchar(300) DEFAULT NULL COMMENT '标题附加',
  `parent_id` int(11) DEFAULT NULL COMMENT '父模块',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `url` (`url`),
  KEY `parent_id` (`parent_id`),
  KEY `sort` (`sort`)
) ENGINE=MyISAM AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='模块';

-- ----------------------------
-- Records of sys_moudle
-- ----------------------------
INSERT INTO `sys_moudle` VALUES ('1', '个人', null, null, '<i class=\"icon-user icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('2', '内容', null, null, '<i class=\"icon-book icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('3', '分类', null, null, '<i class=\"icon-folder-open icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('4', '页面', null, null, '<i class=\"icon-globe icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('5', '维护', null, null, '<i class=\"icon-cog icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('6', '与我相关', null, null, '<i class=\"icon-user icon-large\"></i>', '1', '0');
INSERT INTO `sys_moudle` VALUES ('7', '修改密码', 'myself/password', 'changePassword', '<i class=\"icon-key icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('8', '我的内容', 'myself/contentList', null, '<i class=\"icon-book icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('9', '我的操作日志', 'myself/logOperate', null, '<i class=\"icon-list-alt icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('10', '我的登陆日志', 'myself/logLogin', null, '<i class=\"icon-signin icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('11', ' 我的登陆授权', 'myself/userTokenList', null, '<i class=\"icon-unlock-alt icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('12', '内容管理', 'cmsContent/list', 'sysUser/lookup', '<i class=\"icon-book icon-large\"></i>', '2', '0');
INSERT INTO `sys_moudle` VALUES ('13', '内容扩展', null, null, '<i class=\"icon-road icon-large\"></i>', '2', '0');
INSERT INTO `sys_moudle` VALUES ('14', '标签管理', 'cmsTag/list', 'cmsTagType/lookup', '<i class=\"icon-tag icon-large\"></i>', '13', '0');
INSERT INTO `sys_moudle` VALUES ('15', '增加/修改', 'cmsTag/add', 'cmsTagType/lookup,cmsTag/save', null, '14', '0');
INSERT INTO `sys_moudle` VALUES ('16', '删除', null, 'cmsTag/delete', null, '14', '0');
INSERT INTO `sys_moudle` VALUES ('17', '增加/修改', 'cmsContent/add', 'cmsContent/addMore,cmsContent/lookup,cmsContent/lookup_list,cmsContent/save,ueditor', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('18', '删除', null, 'cmsContent/delete', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('19', '审核', null, 'cmsContent/check', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('20', '刷新', null, 'cmsContent/refresh', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('21', '生成', null, 'cmsContent/publish', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('22', '移动', 'cmsContent/moveParameters', 'cmsContent/move', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('23', '推荐', 'cmsContent/push', 'cmsContent/push_content,cmsContent/push_content_list,cmsContent/push_to_content,cmsContent/push_page,cmsContent/push_page_list,cmsPage/placeDataAdd,cmsPlace/save,cmsContent/related', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('24', '分类管理', 'cmsCategory/list', null, '<i class=\"icon-folder-open icon-large\"></i>', '3', '0');
INSERT INTO `sys_moudle` VALUES ('25', '增加/修改', 'cmsCategory/add', 'cmsCategory/addMore,cmsTemplate/lookup,cmsCategory/categoryPath,cmsCategory/contentPath,file/doUpload,cmsCategory/save', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('26', '删除', null, 'cmsCategory/delete', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('27', '生成', 'cmsCategory/publishParameters', 'cmsCategory/publish', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('28', '移动', 'cmsCategory/moveParameters', 'cmsCategory/move,cmsCategory/lookup', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('29', '推荐', 'cmsCategory/push_page', 'cmsCategory/push_page_list,cmsPage/placeDataAdd,cmsPlace/save', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('30', '页面管理', 'cmsPage/placeList', 'sysUser/lookup,sysUser/lookup_content_list,cmsPage/placeDataList,cmsPage/placeDataAdd,cmsPlace/save,cmsTemplate/publishPlace,cmsPage/publishPlace,cmsPage/push_page,cmsPage/push_page_list', '<i class=\"icon-globe icon-large\"></i>', '4', '0');
INSERT INTO `sys_moudle` VALUES ('31', '分类扩展', null, null, '<i class=\"icon-road icon-large\"></i>', '3', '0');
INSERT INTO `sys_moudle` VALUES ('32', '分类类型', 'cmsCategoryType/list', null, '<i class=\"icon-road icon-large\"></i>', '31', '0');
INSERT INTO `sys_moudle` VALUES ('33', '标签分类', 'cmsTagType/list', null, '<i class=\"icon-tags icon-large\"></i>', '31', '0');
INSERT INTO `sys_moudle` VALUES ('34', '增加/修改', 'cmsTagType/add', 'cmsTagType/save', null, '33', '0');
INSERT INTO `sys_moudle` VALUES ('35', '删除', null, 'cmsTagType/delete', null, '33', '0');
INSERT INTO `sys_moudle` VALUES ('36', '增加/修改', 'cmsCategoryType/add', 'cmsCategoryType/save', null, '32', '0');
INSERT INTO `sys_moudle` VALUES ('37', '删除', null, 'cmsCategoryType/delete', null, '32', '0');
INSERT INTO `sys_moudle` VALUES ('38', '模板管理', null, null, '<i class=\"icon-code icon-large\"></i>', '5', '0');
INSERT INTO `sys_moudle` VALUES ('39', '页面模板', 'cmsTemplate/list', 'cmsTemplate/directory', '<i class=\"icon-globe icon-large\"></i>', '38', '0');
INSERT INTO `sys_moudle` VALUES ('40', '修改模板元数据', 'cmsTemplate/metadata', 'cmsTemplate/saveMetadata', null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('41', '修改模板', 'cmsTemplate/content', 'cmsTemplate/save,cmsTemplate/chipLookup', null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('42', '修改推荐位', 'cmsTemplate/placeList', 'cmsTemplate/placeMetadata,cmsTemplate/placeContent,cmsTemplate/placeForm,cmsTemplate/saveMetadata,cmsTemplate/createPlace', null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('43', '删除模板', null, 'cmsTemplate/delete', null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('44', '搜索词管理', 'cmsWord/list', null, '<i class=\"icon-search icon-large\"></i>', '13', '0');
INSERT INTO `sys_moudle` VALUES ('47', '生成页面', null, 'cmsTemplate/publish', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('48', '保存页面元数据', '', 'cmsPage/saveMetaData,file/doUpload,cmsPage/clearCache', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('49', '增加/修改推荐位数据', 'cmsPage/placeDataAdd', 'cmsContent/lookup,cmsPage/lookup_content_list,file/doUpload,cmsPlace/save', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('50', '删除推荐位数据', null, 'cmsPlace/delete', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('51', '刷新推荐位数据', null, 'cmsPlace/refresh', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('52', '审核推荐位数据', null, 'cmsPlace/check', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('53', '发布推荐位', null, 'cmsTemplate/publishPlace', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('54', '清空推荐位数据', null, 'cmsPlace/clear', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('60', '文件上传日志', 'log/upload', 'sysUser/lookup', '<i class=\"icon-list-alt icon-large\"></i>', '63', '0');
INSERT INTO `sys_moudle` VALUES ('61', '用户管理', null, null, '<i class=\"icon-user icon-large\"></i>', '5', '0');
INSERT INTO `sys_moudle` VALUES ('62', '系统维护', null, null, '<i class=\"icon-cogs icon-large\"></i>', '5', '0');
INSERT INTO `sys_moudle` VALUES ('63', '日志管理', null, null, '<i class=\"icon-list-alt icon-large\"></i>', '5', '0');
INSERT INTO `sys_moudle` VALUES ('64', '操作日志', 'log/operate', 'sysUser/lookup', '<i class=\"icon-list-alt icon-large\"></i>', '63', '0');
INSERT INTO `sys_moudle` VALUES ('65', '登录日志', 'log/login', 'sysUser/lookup', '<i class=\"icon-signin icon-large\"></i>', '63', '0');
INSERT INTO `sys_moudle` VALUES ('66', '任务计划日志', 'log/task', 'sysUser/lookup', '<i class=\"icon-time icon-large\"></i>', '63', '0');
INSERT INTO `sys_moudle` VALUES ('67', '删除', null, 'logOperate/delete', null, '64', '0');
INSERT INTO `sys_moudle` VALUES ('68', '删除', null, 'logLogin/delete', null, '65', '0');
INSERT INTO `sys_moudle` VALUES ('69', '删除', null, 'logTask/delete', null, '66', '0');
INSERT INTO `sys_moudle` VALUES ('70', '查看', 'log/taskView', null, null, '66', '0');
INSERT INTO `sys_moudle` VALUES ('71', '用户管理', 'sysUser/list', null, '<i class=\"icon-user icon-large\"></i>', '61', '0');
INSERT INTO `sys_moudle` VALUES ('72', '部门管理', 'sysDept/list', 'sysDept/lookup,sysUser/lookup', '<i class=\"icon-group icon-large\"></i>', '61', '0');
INSERT INTO `sys_moudle` VALUES ('73', '角色管理', 'sysRole/list', null, '<i class=\"icon-user-md icon-large\"></i>', '61', '0');
INSERT INTO `sys_moudle` VALUES ('74', '增加/修改', 'sysUser/add', 'sysDept/lookup,sysUser/save', null, '71', '0');
INSERT INTO `sys_moudle` VALUES ('75', '启用', null, 'sysUser/enable', null, '71', '0');
INSERT INTO `sys_moudle` VALUES ('76', '禁用', null, 'sysUser/disable', null, '71', '0');
INSERT INTO `sys_moudle` VALUES ('77', '增加/修改', 'sysDept/add', 'sysDept/lookup,sysUser/lookup,sysDept/save', null, '72', '0');
INSERT INTO `sys_moudle` VALUES ('78', '删除', null, 'sysDept/delete', null, '72', '0');
INSERT INTO `sys_moudle` VALUES ('79', '增加/修改', 'sysRole/add', 'sysRole/save', null, '73', '0');
INSERT INTO `sys_moudle` VALUES ('80', '删除', null, 'sysRole/delete', null, '73', '0');
INSERT INTO `sys_moudle` VALUES ('81', '内容模型管理', 'cmsModel/list', null, '<i class=\"icon-th-large icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('82', '任务计划', 'sysTask/list', null, '<i class=\"icon-time icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('83', 'FTP用户', 'cmsFtpUser/list', null, '<i class=\"icon-folder-open-alt icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('84', '动态域名', 'cmsDomain/list', null, '<i class=\"icon-qrcode icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('85', '任务计划脚本', 'taskTemplate/list', null, '<i class=\"icon-time icon-large\"></i>', '38', '0');
INSERT INTO `sys_moudle` VALUES ('86', '修改脚本', 'taskTemplate/metadata', 'cmsTemplate/saveMetadata,taskTemplate/content,cmsTemplate/save,taskTemplate/chipLookup', null, '85', '0');
INSERT INTO `sys_moudle` VALUES ('87', '删除脚本', null, 'cmsTemplate/delete', null, '85', '0');
INSERT INTO `sys_moudle` VALUES ('88', '用户登录授权', 'sysUserToken/list', 'sysUser/lookup', '<i class=\"icon-unlock-alt icon-large\"></i>', '61', '0');
INSERT INTO `sys_moudle` VALUES ('89', '删除', null, 'sysUserToken/delete', null, '88', '0');
INSERT INTO `sys_moudle` VALUES ('90', '增加/修改', 'cmsModel/add', 'cmsModel/save,cmsTemplate/lookup', null, '81', '0');
INSERT INTO `sys_moudle` VALUES ('91', '删除', null, 'cmsModel/delete', null, '81', '0');
INSERT INTO `sys_moudle` VALUES ('92', '增加/修改', 'sysTask/add', 'sysTask/save,sysTask/example,taskTemplate/lookup', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('93', '删除', null, 'sysTask/delete', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('94', '立刻执行', null, 'sysTask/runOnce', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('95', '暂停', null, 'sysTask/pause', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('96', '恢复', null, 'sysTask/resume', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('97', '重新初始化', null, 'sysTask/recreate', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('98', '增加/修改', 'cmsFtpUser/add', 'cmsFtpUser/save', null, '83', '0');
INSERT INTO `sys_moudle` VALUES ('99', '删除', null, 'cmsFtpUser/delete', null, '83', '0');
INSERT INTO `sys_moudle` VALUES ('100', '修改', 'cmsDomain/add', 'cmsDomain/save,cmsTemplate/directoryLookup,cmsTemplate/lookup', null, '84', '0');
INSERT INTO `sys_moudle` VALUES ('101', '配置中心', 'sysConfig/list', 'sysConfig/subcode', '<i class=\"icon-cogs icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('102', '修改', 'cmsContent/add', 'cmsContent/addMore,file/doUpload,cmsContent/lookup,cmsContent/lookup_list,cmsContent/save,ueditor', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('103', '删除', null, 'cmsContent/delete', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('104', '刷新', null, 'cmsContent/refresh', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('105', '生成', null, 'cmsContent/publish', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('106', '推荐', 'cmsContent/push', 'cmsContent/push_content,cmsContent/push_content_list,cmsContent/push_to_content,cmsContent/push_page,cmsContent/push_page_list,cmsContent/push_to_place,cmsContent/related', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('107', '推荐位数据列表', 'cmsPage/placeDataList', null, null, '29', '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `owns_all_right` tinyint(1) NOT NULL COMMENT '拥有全部权限',
  `show_all_moudle` tinyint(1) NOT NULL COMMENT '显示全部模块',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '超级管理员', '1', '0');
INSERT INTO `sys_role` VALUES ('2', '1', '测试管理员', '0', '1');
INSERT INTO `sys_role` VALUES ('3', '2', '站长', '1', '0');

-- ----------------------------
-- Table structure for `sys_role_authorized`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authorized`;
CREATE TABLE `sys_role_authorized` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `url` varchar(255) NOT NULL COMMENT '授权地址',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `url` (`url`)
) ENGINE=MyISAM AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='角色授权地址';

-- ----------------------------
-- Records of sys_role_authorized
-- ----------------------------
INSERT INTO `sys_role_authorized` VALUES ('1', '2', 'taskTemplate/metadata');
INSERT INTO `sys_role_authorized` VALUES ('2', '2', 'sysRole/add');
INSERT INTO `sys_role_authorized` VALUES ('3', '2', 'myself/contentList');
INSERT INTO `sys_role_authorized` VALUES ('5', '2', 'cmsTag/list');
INSERT INTO `sys_role_authorized` VALUES ('6', '2', 'myself/userTokenList');
INSERT INTO `sys_role_authorized` VALUES ('7', '2', 'sysTask/list');
INSERT INTO `sys_role_authorized` VALUES ('8', '2', 'cmsTagType/list');
INSERT INTO `sys_role_authorized` VALUES ('9', '2', 'log/login');
INSERT INTO `sys_role_authorized` VALUES ('10', '2', 'cmsCategoryType/add');
INSERT INTO `sys_role_authorized` VALUES ('11', '2', 'sysDept/list');
INSERT INTO `sys_role_authorized` VALUES ('12', '2', 'sysUserToken/list');
INSERT INTO `sys_role_authorized` VALUES ('13', '2', 'cmsTag/add');
INSERT INTO `sys_role_authorized` VALUES ('15', '2', 'sysUser/list');
INSERT INTO `sys_role_authorized` VALUES ('16', '2', 'cmsPage/placeDataAdd');
INSERT INTO `sys_role_authorized` VALUES ('18', '2', 'cmsContent/moveParameters');
INSERT INTO `sys_role_authorized` VALUES ('19', '2', 'cmsCategory/moveParameters');
INSERT INTO `sys_role_authorized` VALUES ('20', '2', 'sysUser/add');
INSERT INTO `sys_role_authorized` VALUES ('21', '2', 'myself/logLogin');
INSERT INTO `sys_role_authorized` VALUES ('22', '2', 'myself/logOperate');
INSERT INTO `sys_role_authorized` VALUES ('25', '2', 'myself/password');
INSERT INTO `sys_role_authorized` VALUES ('26', '2', 'cmsCategory/list');
INSERT INTO `sys_role_authorized` VALUES ('28', '2', 'log/operate');
INSERT INTO `sys_role_authorized` VALUES ('29', '2', 'cmsCategory/publishParameters');
INSERT INTO `sys_role_authorized` VALUES ('30', '2', 'cmsCategory/add');
INSERT INTO `sys_role_authorized` VALUES ('31', '2', 'cmsDomain/list');
INSERT INTO `sys_role_authorized` VALUES ('34', '2', 'sysRole/list');
INSERT INTO `sys_role_authorized` VALUES ('35', '2', 'cmsModel/list');
INSERT INTO `sys_role_authorized` VALUES ('36', '2', 'cmsFtpUser/add');
INSERT INTO `sys_role_authorized` VALUES ('37', '2', 'sysTask/add');
INSERT INTO `sys_role_authorized` VALUES ('38', '2', 'cmsDomain/add');
INSERT INTO `sys_role_authorized` VALUES ('39', '2', 'cmsTagType/add');
INSERT INTO `sys_role_authorized` VALUES ('40', '2', 'sysDept/add');
INSERT INTO `sys_role_authorized` VALUES ('41', '2', 'cmsFtpUser/list');
INSERT INTO `sys_role_authorized` VALUES ('42', '2', 'cmsContent/list');
INSERT INTO `sys_role_authorized` VALUES ('44', '2', 'log/taskView');
INSERT INTO `sys_role_authorized` VALUES ('45', '2', 'cmsCategoryType/list');
INSERT INTO `sys_role_authorized` VALUES ('46', '2', 'cmsModel/add');
INSERT INTO `sys_role_authorized` VALUES ('47', '2', 'cmsContent/add');
INSERT INTO `sys_role_authorized` VALUES ('48', '2', 'taskTemplate/list');
INSERT INTO `sys_role_authorized` VALUES ('49', '2', 'cmsContent/push');
INSERT INTO `sys_role_authorized` VALUES ('50', '2', 'log/task');
INSERT INTO `sys_role_authorized` VALUES ('51', '2', 'cmsPage/placeDataList');
INSERT INTO `sys_role_authorized` VALUES ('52', '2', 'cmsTemplate/metadata');
INSERT INTO `sys_role_authorized` VALUES ('53', '2', 'cmsPage/placeList');
INSERT INTO `sys_role_authorized` VALUES ('54', '2', 'cmsTemplate/list');
INSERT INTO `sys_role_authorized` VALUES ('55', '2', 'cmsTemplate/placeList');

-- ----------------------------
-- Table structure for `sys_role_moudle`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_moudle`;
CREATE TABLE `sys_role_moudle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `moudle_id` int(11) NOT NULL COMMENT '模块ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `moudle_id` (`moudle_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色授权模块';

-- ----------------------------
-- Records of sys_role_moudle
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1', '1');
INSERT INTO `sys_role_user` VALUES ('2', '2', '2');
INSERT INTO `sys_role_user` VALUES ('3', '3', '3');

-- ----------------------------
-- Table structure for `sys_site`
-- ----------------------------
DROP TABLE IF EXISTS `sys_site`;
CREATE TABLE `sys_site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `use_static` tinyint(1) NOT NULL COMMENT '启用静态化',
  `site_path` varchar(255) NOT NULL COMMENT '站点地址',
  `use_ssi` tinyint(1) NOT NULL COMMENT '启用服务器端包含',
  `dynamic_path` varchar(255) NOT NULL COMMENT '动态站点地址',
  `resource_path` varchar(255) NOT NULL COMMENT '资源站点地址',
  `disabled` tinyint(1) NOT NULL COMMENT '禁用',
  PRIMARY KEY (`id`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='站点';

-- ----------------------------
-- Records of sys_site
-- ----------------------------
INSERT INTO `sys_site` VALUES ('1', 'PublicCMS', '0', '//223.202.64.24/cms/', '0', '//223.202.64.24/cms/', '//223.202.64.24/cms/resource/resource/site_1/', '0');
INSERT INTO `sys_site` VALUES ('2', '演示站点1', '0', '//site2.dev.publiccms.com/', '0', 'site2.dev.publiccms.com', '//resource.site2.dev.publiccms.com/', '0');

-- ----------------------------
-- Table structure for `sys_task`
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `status` int(11) NOT NULL COMMENT '状态',
  `cron_expression` varchar(50) NOT NULL COMMENT '表达式',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `status` (`status`),
  KEY `site_id` (`site_id`),
  KEY `update_date` (`update_date`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='任务计划';

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO `sys_task` VALUES ('1', '1', '重新生成所有页面', '0', '0 0/2 * * ?', '重新生成所有页面', '/publishPage.task', null);
INSERT INTO `sys_task` VALUES ('2', '1', '重建索引', '0', '0 0 1 1 ? 2099', '重建全部索引', '/reCreateIndex.task', null);
INSERT INTO `sys_task` VALUES ('3', '1', '清理日志', '0', '0 0 1 * ?', '清理三个月以前的日志', '/clearLog.task', null);
INSERT INTO `sys_task` VALUES ('4', '1', '重新生成内容页面', '0', '0 0 1 1 ? 2099', '重新生成内容页面', '/publishContent.task', null);
INSERT INTO `sys_task` VALUES ('5', '1', '重新生成所有分类页面', '0', '0 0/6 * * ?', '重新生成所有分类页面', '/publishCategory.task', null);
INSERT INTO `sys_task` VALUES ('7', '1', '重新生成全站', '0', '0 0 1 1 ? 2099', '重新生成全站', '/publishAll.task', null);

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nick_name` varchar(45) NOT NULL COMMENT '昵称',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门',
  `roles` text COMMENT '角色',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `email_checked` tinyint(1) NOT NULL COMMENT '已验证邮箱',
  `superuser_access` tinyint(1) NOT NULL COMMENT '是否管理员',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后登录日期',
  `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登录ip',
  `login_count` int(11) NOT NULL COMMENT '登录次数',
  `registered_date` datetime DEFAULT NULL COMMENT '注册日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`site_id`),
  UNIQUE KEY `nick_name` (`nick_name`,`site_id`),
  KEY `email` (`email`),
  KEY `disabled` (`disabled`),
  KEY `lastLoginDate` (`last_login_date`),
  KEY `email_checked` (`email_checked`),
  KEY `dept_id` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', '1', '1', 'master@sanluan.com', '0', '1', '0', '2016-12-02 10:23:36', '120.205.17.124', '76', '2016-03-22 00:00:00');
INSERT INTO `sys_user` VALUES ('2', '1', 'test', '098f6bcd4621d373cade4e832627b4f6', '演示账号', '1', '2', 'test@test.com', '0', '1', '0', '2016-03-24 18:20:41', '112.23.82.255', '5455', '2016-03-22 00:00:00');
INSERT INTO `sys_user` VALUES ('3', '2', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', '2', '3', '', '0', '1', '0', '2016-03-23 11:51:11', '106.2.199.138', '6', '2016-03-22 17:42:26');

-- ----------------------------
-- Table structure for `sys_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `auth_token` varchar(40) NOT NULL COMMENT '登陆授权',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `login_ip` varchar(20) NOT NULL COMMENT '登陆IP',
  PRIMARY KEY (`auth_token`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`),
  KEY `channel` (`channel`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户令牌';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------

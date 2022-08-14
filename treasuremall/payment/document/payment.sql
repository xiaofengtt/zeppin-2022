/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : payment

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2020-04-03 15:05:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `username` varchar(50) NOT NULL,
  `realname` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `role` (`role`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1d0db2b5-2a10-482c-a6c2-57adabdccbf6', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'admin', '管理员', '928bfd2577490322a6e19b793691467e', 'normal', '2020-03-24 16:53:55');

-- ----------------------------
-- Table structure for `bank`
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `short_name` varchar(200) NOT NULL,
  `logo` varchar(36) NOT NULL,
  `sort` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank
-- ----------------------------
INSERT INTO `bank` VALUES ('fc7e7bbf-48e6-4484-b453-deee5e20edfc', '中国大唐很行', '大唐很行', 'aa', '0', 'normal');

-- ----------------------------
-- Table structure for `callback`
-- ----------------------------
DROP TABLE IF EXISTS `callback`;
CREATE TABLE `callback` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `order_info` varchar(36) NOT NULL,
  `url` varchar(200) NOT NULL,
  `body` text,
  `times` int(11) NOT NULL DEFAULT '0',
  `status` varchar(20) NOT NULL COMMENT 'normal/finish',
  `processtime` varchar(200) DEFAULT NULL,
  `lasttime` timestamp NULL DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  CONSTRAINT `callback_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of callback
-- ----------------------------
INSERT INTO `callback` VALUES ('099d64ae-ab74-4adf-bf71-4af13221544d', 'user_recharge', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '6953c74f-e82f-4afd-a5c0-7e467d036efe', 'http://192.168.1.102:28080/notice/recharge/byUnion', ';1:fail;2:fail', '8', 'fail', ';1585648189326;1585648250334;1585648637164;1585649259110;1585651059124;1585709244871;1585795392702;1585882053571', '2020-04-03 10:47:33', '2020-03-31 17:03:03');
INSERT INTO `callback` VALUES ('1075f929-5f6d-4d54-a64a-f728e0c92d01', 'user_recharge', '1075f929-5f6d-4d54-a64a-f728e0c92d04', 'e2b47270-371b-4bc5-9151-6ca71d8a638d', 'http://192.168.1.120:28080/notice/recharge/demo', null, '1', 'success', ';1585035321114', '2020-03-24 15:35:21', '2020-03-24 14:53:59');
INSERT INTO `callback` VALUES ('1075f929-5f6d-4d54-a64a-f728e0c92d05', 'user_recharge', '1075f929-5f6d-4d54-a64a-f728e0c92d04', 'e2b47270-371b-4bc5-9151-6ca71d8a638d', 'http://192.168.1.120:28080/notice/recharge/demo', null, '1', 'success', ';1585035302631', '2020-03-24 15:35:02', '2020-03-24 11:12:55');
INSERT INTO `callback` VALUES ('489e1e02-08ff-4096-89a1-d71008dda185', 'user_withdraw', '5075f929-5f6d-1d54-a64a-f728e0c92e12', '20c93a54-fc2b-4aa2-b84d-5739d2fa0e03', 'https://www.baidu.com', ';1:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;2:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;3:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;4:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>', '7', 'normal', ';1585734838745;1585734899102;1585735199357;1585739330088;1585795393858;1585797193092;1585878782464', '2020-04-03 09:53:02', '2020-04-01 17:53:58');
INSERT INTO `callback` VALUES ('713cc82b-fac5-4a0d-bbd7-e108fa706ab5', 'user_withdraw', '5075f929-5f6d-1d54-a64a-f728e0c92e12', '418d6941-e8d6-49b9-9f0c-93fa7d72461b', 'https://www.baidu.com', ';1:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;2:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;3:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;4:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>', '7', 'normal', ';1585731580900;1585731641300;1585731941435;1585739330088;1585795392702;1585797192153;1585878781508', '2020-04-03 09:53:01', '2020-04-01 16:59:40');
INSERT INTO `callback` VALUES ('7bd3d571-6627-49ae-89c1-64f37e89bb21', 'user_withdraw', '5075f929-5f6d-1d54-a64a-f728e0c92e12', '20c93a54-fc2b-4aa2-b84d-5739d2fa0e03', 'https://www.baidu.com', ';1:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;2:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>', '8', 'fail', ';1585719729968;1585719790468;1585795392702;1585878781508', '2020-04-03 09:53:01', '2020-04-01 13:42:09');
INSERT INTO `callback` VALUES ('94faf153-b98b-4893-9bf7-1caf4069d30d', 'user_recharge', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '1f1da42d-10c0-4e98-89d4-d4f671bc376e', 'https://www.baidu.com', ';1:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;2:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;3:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;4:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;5:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;6:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>', '8', 'fail', ';1585651280984;1585651341463;1585651641593;1585709244871;1585711214758;1585714814164;1585795392702;1585882053571', '2020-04-03 10:47:33', '2020-03-31 18:41:20');
INSERT INTO `callback` VALUES ('a4bad74b-bc2c-4628-93f3-adcbd6aa71c0', 'user_withdraw', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'ce419939-f645-4606-ab37-ab937e6c570a', 'http://192.168.1.102:28080/notice/recharge/byUnion', '{\"amount\":\"1000\",\"channel\":\"d141c2ff-2c36-4f90-92e0-6c30618dd93a\",\"company\":\"2c41c2ff-2c36-4f90-92e0-6c30618dd91d\",\"orderNum\":\"33915968329748480\",\"passbackParams\":\"3b6ceab3-86b5-4f16-8eff-657730a215f5\",\"paymentOrderNum\":\"11016651722382010617856\",\"poundage\":\"10\",\"status\":\"success\"}', '1', 'success', ';1585894338100', '2020-04-03 14:12:18', '2020-04-03 14:12:17');
INSERT INTO `callback` VALUES ('ec0aaa84-4d9c-432f-8319-c786d2952e10', 'user_recharge', '1075f929-5f6d-4d54-a64a-f728e0c92d04', 'd0fd6dad-0d49-4d3e-ab21-52503825d088', 'https://www.baidu.com', ';1:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;2:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;3:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;4:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;5:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>;6:<!DOCTYPE html><!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus=autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\" autofocus></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=https://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(\'<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=\'+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \'\" name=\"tj_login\" class=\"lb\">登录</a>\');                </script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>', '8', 'fail', ';1585651413116;1585651473221;1585651773335;1585709244871;1585711214758;1585714814164;1585795392702;1585882053571', '2020-04-03 10:47:33', '2020-03-31 18:43:33');

-- ----------------------------
-- Table structure for `channel`
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `code` varchar(2) NOT NULL,
  `data` text NOT NULL,
  `callback_url` varchar(100) NOT NULL COMMENT '回调地址',
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('1075f929-5f6d-4d54-a64a-f728e0c92d04', '企业支付宝', '01', '{\"appid\":\"AppID\",\"privateKey\":\"商户私钥\",\"publicKey\":\"蚂蚁公钥\"}', 'https://backadmin.niutoulicai.com/payment/', 'recharge', 'normal');
INSERT INTO `channel` VALUES ('5075f929-5f6d-1d54-a64a-f728e0c92e12', '个人银行卡', '10', '{\"bank\":\"所属银行\",\"holder\":\"持卡人\"}', 'https://', 'withdraw', 'normal');

-- ----------------------------
-- Table structure for `channel_account`
-- ----------------------------
DROP TABLE IF EXISTS `channel_account`;
CREATE TABLE `channel_account` (
  `uuid` varchar(255) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `account_num` varchar(50) NOT NULL,
  `data` text NOT NULL,
  `balance` decimal(20,0) NOT NULL,
  `poundage` decimal(20,0) DEFAULT NULL COMMENT '手续费（定额）',
  `poundage_rate` decimal(10,4) DEFAULT NULL COMMENT '手续费（百分比）',
  `max` decimal(20,0) NOT NULL,
  `min` decimal(20,0) NOT NULL,
  `daily_max` decimal(20,0) NOT NULL,
  `total_max` decimal(20,0) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  CONSTRAINT `channel_account_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel_account
-- ----------------------------
INSERT INTO `channel_account` VALUES ('04ac649b-43f3-4f85-888c-dd85bbca21e0', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '大唐玄奘文化有限公司', '2088021828014590', '{\"appid\":\"2016071901635869\",\"privateKey\":\"MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCj6KRLrowbcRAWew0Agn9pgY7nbernUIJVGC/RxIs1uo4LGJsC2p8cxpqGsopTurRvQszGKeQAd4s6nds2vPGO78AUjtxPSd+9cHiPf5No8xCCrBFivJhvIYDXeMYJ6/+KO6+9Y9CYsiNfam62GtqhEZSyTvgsaW22l8VQ5EZKKBxHVUS/j4OAKXDtDUjH5tyKNT71sjpVjgNdIRZxt0S+jQuFSS5lej+Oa2hcCJ1vnqZyvUOpss7n2cBi/j24r0BAgiPf5snY8ti7wpAc637QH6YrrHKAlS3pMn91qDVTPFSDYkRnkRB33GhRJic4v1U3AwDKNdXNHc3hXnSycIevAgMBAAECggEBAKKg1ErnL8qWftDfXIIx+Ls1OhXz4IuMPRSzP9cQ/NLde8wUqNDHG/IQOAgHo+n5qMdv7v97VucDtZf+Qh/ojoA07082g+8DrEQpEOXIPfl2md4dXc6qs1AoXM7t3QjBKLX+DJuMKs8miKRGVPzIXj5L1E6qveBK5vmxUqy1Iey2jW7AV0ANKbDXzi1myKvhtAxarR1Yc1mzhWw8rJtdPNOQGZnz12OUVMbJ2ZkDP9TaT8KPaXmaVOMjs0rurUWojYsxU/aL/4Upm6C+AkA32JQ5+ytkZsp+SnhJh79vZSxbBtUSFs5uVubLBEjR/FRU2gNPmUecIDrwS+eWvL7MSkkCgYEA8bPGllJDd+U+DjnQjUYLkSPNfZAbYd1IRqJI6W6n3pQmFKIKwNhGlqPFBE8h6ntWR9P3MJYh7QmYmPPyjN14soujsSHTlDDnI3jVDeE+lueZlK9nGXWTGVhs6bm7RaS3p+5hM3rbXNAEWMV9BlJqQZRBgV1gL1ELhjIBGy03M8MCgYEArZrMXIt110p9WAa5a1Ihp+sFv0te8jF3h//UyGUuaeIFj0to6QI2gT1Uzc8kk5+kRnWs3bkNZu5jxhsjO6TpVl+shOx95o39kkCya0qntb1PP6oTNMW2rhin0knVDWYV5EMMjfrPtDnZlYI6BvtHtZvoNx5LeNPfAoBh7VK3eaUCgYBLtTQPAdWASJ4XdqSMm9QjskM7gVgSX220MkEEXVTXsy/6ZodXwGbb6JBduSu2dsuf1BUpct1NkiPqRP9EgFq+El9DrITJdkfwJHkXz+X6/rBskkSJBPr+hWQYEcVHG0ErqM9pgKIVgFLcO3/d6xK9V+Ls0oK+T3R8pE0UZiVUYQKBgQCgNXri7NCTHesOkSYMJH9qtzlWj/fPCleE6lMznCx5ClyXIMBwR9qE6lSYmdDnayvu2intdBkqJFVvPRwGrumnDCPph1WoruCTV6FP4lVjIpE/73RJ/yvW/mnhZsF22/7X6Aht/kgvyjNCBiwGxV4n+vkR5KNBnkTvygqVOQCZAQKBgQDfn4HB8ur60y9lwXxcI6CRezV4b7c/8ULSfBAePulLHfYv4PZfEoFeVLYpA6sLgNks2nFOwAWLtTA8xf0EHC1VfJJm4t9IjWgdUICIjvwTQ0W1vSj713G09T5rpFV0Aj0n0Xreb7gqZeOGOkfYXzfkgt1YG09SwceQGcFzM5GF0w==\",\"publicKey\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs6eAj+rXslrvOh68ntdtbOltViD9eDSG3ds/fE5rMeUIB+kpaKoN38k0DNIIwj29h+1bj4vY6tZipu1JBPz74fzJaYAnQ5QpjssR2/rV22PvL5XITj+vrX9nVPkVHOnKLvoTM/CDkd8C9/gtXIm3hwa0ZtO8y60GND6JZ429C91iuKyFASoC6L2ZlOw73+QzRGkcI+mJBHJhIwss/v2l9tBAFH7wHzAeaY4njTp+S7w2UMF2C1+jeEzA3dP5oCT8ReqiYCWLpV2lRO9Sz/VS3DpxJ5q6+RLmYssrQ1t3zUOnWNggT7HfCfDhMLxW56wO+OLW1/KGz4xWdJIttkLvvQIDAQAB\"}', '1988', null, '0.0060', '1000000', '1', '10000000', '100000000000000', 'recharge', 'normal', '2020-03-17 10:34:19');
INSERT INTO `channel_account` VALUES ('d4ac649b-43f3-4f85-888c-dd85bbca25a5', '5075f929-5f6d-1d54-a64a-f728e0c92e12', '沙悟净的银行卡', '6222012312421412', '{\"bank\":\"大唐很行\",\"holder\":\"沙悟净\"}', '-2010', '10', null, '100000', '100', '10000000', '10000000000000', 'withdraw', 'normal', '2020-03-30 11:44:32');

-- ----------------------------
-- Table structure for `channel_account_history`
-- ----------------------------
DROP TABLE IF EXISTS `channel_account_history`;
CREATE TABLE `channel_account_history` (
  `uuid` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `channel_account` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `order_info` varchar(36) NOT NULL COMMENT '订单uuid',
  `order_num` varchar(30) NOT NULL,
  `poundage` decimal(20,0) NOT NULL,
  `amount` decimal(20,0) NOT NULL COMMENT '变动金额',
  `balance` decimal(20,0) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  KEY `channel_account` (`channel_account`),
  KEY `company` (`company`),
  CONSTRAINT `channel_account_history_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `channel_account_history_ibfk_2` FOREIGN KEY (`channel_account`) REFERENCES `channel_account` (`uuid`),
  CONSTRAINT `channel_account_history_ibfk_3` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel_account_history
-- ----------------------------
INSERT INTO `channel_account_history` VALUES ('46fe9c64-4e4a-4a45-8058-0293ec4c9f98', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'user_recharge', 'd0fd6dad-0d49-4d3e-ab21-52503825d088', '10126650214023604342784', '6', '994', '1988', '2020-03-31 18:43:33');
INSERT INTO `channel_account_history` VALUES ('9cd06f08-a964-40ca-a090-a293e4390697', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'd4ac649b-43f3-4f85-888c-dd85bbca25a5', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'user_withdraw', 'ce419939-f645-4606-ab37-ab937e6c570a', '11016651722382010617856', '10', '1000', '-2010', '2020-04-03 14:12:17');
INSERT INTO `channel_account_history` VALUES ('9f58a6de-9b41-45fa-be57-965487786a77', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'd4ac649b-43f3-4f85-888c-dd85bbca25a5', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'user_withdraw', '20c93a54-fc2b-4aa2-b84d-5739d2fa0e03', '11016650327730850238464', '10', '1010', '-1010', '2020-04-01 17:53:58');
INSERT INTO `channel_account_history` VALUES ('f8ec4fc3-809f-4c7a-b571-d012e90d47d8', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'user_recharge', '1f1da42d-10c0-4e98-89d4-d4f671bc376e', '10126650223726300368896', '6', '994', '994', '2020-03-31 18:41:20');

-- ----------------------------
-- Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `code` varchar(50) NOT NULL,
  `company_private` text COMMENT '用户储存',
  `company_public` text COMMENT '用户上传',
  `system_private` text NOT NULL COMMENT '自己生成',
  `system_public` text NOT NULL COMMENT '给用户',
  `white_url` text NOT NULL,
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `code` (`code`),
  KEY `creator` (`creator`),
  CONSTRAINT `company_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '天竺贸易有限公司', '202003260000', 'MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHRYIuSsyjzT5UlUEXRw5D+Sy4COzg6ky73pUOtncFomfezB3uhXoE4R5ncYGmBElg2YgELb2zU8UA7jAC9q21vCsuAN7r9bRzBjBaZV7XtzPmoIrBMLQ61QIuFulA6XZxghCzx4y+8ycvoz5vHJRHB3c6LS8rLCH02qPEHdNrVElMKxRE1LlPHfYuGPQ3v9hxE19aLeSID35zj2GyAolmLSsTLXMMEPK3Z+byC8ZEYaY4jzpmrI0FwE/c2oECjJe5rb9R6WAtGUVtCHa8R32b4yx/GqrdhS8BlPBBGVEaGBvmVcb9bJKNW9XEdVx6rGTkxaGadzi8Rt678mQz1sG1AgMBAAECggEAVDJXjSjRp0MhANTF5w87OePmicZEatprWKR6cuXvv3oO3QZ/uu8pu8/DdO+ZmxQf+EBuAOvXn78MaD/Zcn7IMM4mskOofYY6Az5e6mUeW3aUv4A93XHLPLNI4BcyqEyohI5Ihx415MTEJ0qcmdxYDr3+4B8vtNeLazSLqo5GAg9JnExLOqO2j9ZHkGAyiKR1yOPjupz9GPGiIWfCZaL3OR4RFT1BJHU3t/F/xy7AXlx/l/ANXmLxZiizNOzj6pzR0XMssfJPKlNFJ0l4fr3OlqxNHIe7M0WINPwASxt8xQV0GDX/5LLbb01KiAFj/7iaw0vkEeJTSfQhWsqnrAr5AQKBgQD9ipNBpqkq5JHh56teqCy920zIrG78NEj61t1fFVhZTpBm3N+v3EX1r6mbUW+HtUTzRSy4wOSivv1sGZJ8MlWgT2jVBEoAlGKhZOgg08oG6WJOmnFRTXYIVw6svD0f0W2TEldKFJLDdy/xE+sHTWh1xoNuBJ8bVy9oPSIFOekUyQKBgQCIlVMYAlZstKvDTSg7SQqnbebXjdxHoY1pIm6cKHRjBo8ycY6jfvpbSNpP4/7zuFnP/fr/rJtiQvuQSQd/uHZy+5WB5JkXOIDsdnzQ9ighXop7iNrN69S9XlXuug3/YD+9TtW4ViHA0IgCeotcpoizWUJOTxBc+pvI8LS+BjZXjQKBgF4PH8w2QQIJo9kz8g5+w4J3rgeweMgVuZFdTujsaUdQbx/KBy4dwNKBpaFaV873v6mkWw/7d5as1iVZY0+x+LQtYY3NNor6gZCwd7FeANAJmw/gGfP4kbZMXcRVXTPFpSnvnvR7p2red6mvIv0liYBP8ghJrKEe+hZov4tCzaaJAoGAa8zf7p05tUbMIrNtQ42c2RUHE66l4+uAfzBr+Nh9NANjVj8Gg8ietRnFJTUgbyBb0qv1RYLoiR+xCep5/raK1qn3ELRqmEdCil/il9MRTXUe++3CNLEkEeq5DFjQ33UKGdJ1IK7qqRJtpvcts4zFbDjQ+pmwopIyDuDc5vZkQakCgYEAqN12rnYAQK9FCMgqIJXsvqcXBpZ5TqvVqnyEmdhCPWTqlK0fMWI2SSJAmEvhk32pT/1l5KsIVb6ny3mVSxc0XV8uUDEqvokQZU+3Elh50Iy0UjAMNip9lughVz2zpSicEsONPCc6X81ajqY7ahXeZcGwsGohR+go/48qIllEMsY=', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh0WCLkrMo80+VJVBF0cOQ/ksuAjs4OpMu96VDrZ3BaJn3swd7oV6BOEeZ3GBpgRJYNmIBC29s1PFAO4wAvattbwrLgDe6/W0cwYwWmVe17cz5qCKwTC0OtUCLhbpQOl2cYIQs8eMvvMnL6M+bxyURwd3Oi0vKywh9NqjxB3Ta1RJTCsURNS5Tx32Lhj0N7/YcRNfWi3kiA9+c49hsgKJZi0rEy1zDBDyt2fm8gvGRGGmOI86ZqyNBcBP3NqBAoyXua2/UelgLRlFbQh2vEd9m+Msfxqq3YUvAZTwQRlRGhgb5lXG/WySjVvVxHVceqxk5MWhmnc4vEbeu/JkM9bBtQIDAQAB', 'MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCn2iw7VUGZ8KuD/JIuYBKA8cgTHRGdgtWY2vIIN7OI0fI9e2ZY9EwweVWvn8X9yE/4SvkahePKJ2OkYQtw9J+Zeq212KvvGJlxnyt7xY10g7qWhNPUg+VD059LnUJ6HOB5UfYEumfWkX65v3GE/CzOZmuBv+vOsaTJpby/a8WctowVMd1udDU+Jmr63vFQRihJTAEoHS2Bx+QMh6HY630F3w0GakJNQiTlkPY/I2RY28sj1Jm6zuFiKwq6Ixqxc7i9/KZSqAn1RIFAC8FuvYJ7WC2LiKnkQWcShBTd1oseldCNQIACUteP8yC2LbQlzdDCbIZWE5xWtuJvO3Uc6YZ/AgMBAAECggEAHS1PgwDO5nNvXQIKZwVMMh3VKX1kEn0ro+ZrLAKCssXL1eQdZlkQ5VwyCNU9FWA8hmyB4Jplfj5ak7hKxNfA5mzsxS9ks6sTAS0p4dn1PMupI4DzUX2b3K5sgjhT1eYvHuegXDIK2sFvxdoBYZx0LWygQ22ZD9TRUDv/E0cokpTfQcXVHvYN1UeIAnuLMj9PYQuVel95YmNpTvJu62gxbpxxqZpXuwKHIbvOTyihx1B44FUoiibwwWQNUiUzWTwO79ivjlm/SHvFnBz4l1Epajv5F5b2Z/gwmjAW8jz47NdSbvKQxohiUuMhsWpBIdpbSgj0n4NZxke8S8ImGbxoAQKBgQD9LHk/0DxtcRLzK18Sa9lyF7QqBfm8X3outu1OTxxZexXmLdsWe1j7y2pXyWGP/15v7bVbCZ2I3kFpPWjCoZQu4vNp2QavAbpf9fXL2yRZ0DxLMSUfqW2yeo/w0SHxLt7itPMN/jmYZR0+OB/G2jqVsq+UIZE5Pe3n2bpW9yF3twKBgQCpud1+lsaYFfG7LldLKqkUY1MFBZ9nBlSi8b5UTlLOLP2j1Wm/eZciiNOP1OHO9R/ebeWEdeDrvmlM2oORoBD5DHuWNGM0yfFdKDrjJKM1tzZqiu1K+Q+OyY5czmpIdRoubFM0VBKTaI47Mv/Y42gmG94OD0vi4WFe0fGdhV2XeQKBgHigyhzHoSMcL0VBswjZ7j9YX/Vt53IDo/nCGwum0gEojmSgELWDjABdCYxOXXf42wxFnJWF3VOrYks8gT/LMQmuBQC38WFNV3oFfVnmTkmk2DWTcvbFCYlysEazvpJmHLi0MuLF8LdV5jsRDsL2k9djzhisbb9ybHp92TU6hu29AoGALUw4zDdLa3231WAp1hrdo15a4uU8RM/QuOhSBsgcm4AwdZBYt8yoxIPGCwYi+gw75C5qai7Xx7M7AVL0eGJDBWUKeu0iaQ1YiKdbCBJYKNbsBAVuDWtpdo9emnBQt2Phh/BLSJUDnCn0g+I5nFYdDsazPgdhFn1WcmsP1B2q++kCgYEAoWkkU3Yr2GnBpn8bya8EtVsnQYhhN33HiGImD/u1cJr/hY2TnrZ5bdR5kj+9SYVkGOKSvuua1DJJOIKj5jXJY+pR9ZyIOqkfQeAG7p2N6ijXoPByXZix37xf1c8jJahULERCl4NaXShds9N6/benz9DOrUpjBsTcr2xfdm5L058=', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp9osO1VBmfCrg/ySLmASgPHIEx0RnYLVmNryCDeziNHyPXtmWPRMMHlVr5/F/chP+Er5GoXjyidjpGELcPSfmXqttdir7xiZcZ8re8WNdIO6loTT1IPlQ9OfS51CehzgeVH2BLpn1pF+ub9xhPwszmZrgb/rzrGkyaW8v2vFnLaMFTHdbnQ1PiZq+t7xUEYoSUwBKB0tgcfkDIeh2Ot9Bd8NBmpCTUIk5ZD2PyNkWNvLI9SZus7hYisKuiMasXO4vfymUqgJ9USBQAvBbr2Ce1gti4ip5EFnEoQU3daLHpXQjUCAAlLXj/Mgti20Jc3QwmyGVhOcVrbibzt1HOmGfwIDAQAB', ' ', 'normal', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-03-17 17:18:25');

-- ----------------------------
-- Table structure for `company_account`
-- ----------------------------
DROP TABLE IF EXISTS `company_account`;
CREATE TABLE `company_account` (
  `uuid` varchar(36) NOT NULL,
  `balance` decimal(20,0) NOT NULL,
  `balance_lock` decimal(20,0) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_account
-- ----------------------------
INSERT INTO `company_account` VALUES ('2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '6990', '1000');

-- ----------------------------
-- Table structure for `company_account_history`
-- ----------------------------
DROP TABLE IF EXISTS `company_account_history`;
CREATE TABLE `company_account_history` (
  `uuid` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `channel_account` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `company_channel` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `order_info` varchar(36) NOT NULL COMMENT '订单uuid',
  `order_num` varchar(30) NOT NULL,
  `poundage` decimal(20,0) NOT NULL,
  `amount` decimal(20,0) NOT NULL COMMENT '变动金额',
  `balance` decimal(20,0) NOT NULL,
  `company_order_num` varchar(50) NOT NULL COMMENT '商户订单号',
  `company_data` text COMMENT '商户数据',
  `submittime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '提交申请时间',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '完成交易时间',
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  KEY `company` (`company`),
  KEY `company_channel` (`company_channel`),
  KEY `channel_account` (`channel_account`),
  CONSTRAINT `company_account_history_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `company_account_history_ibfk_2` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `company_account_history_ibfk_3` FOREIGN KEY (`company_channel`) REFERENCES `company_channel` (`uuid`),
  CONSTRAINT `company_account_history_ibfk_4` FOREIGN KEY (`channel_account`) REFERENCES `channel_account` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_account_history
-- ----------------------------
INSERT INTO `company_account_history` VALUES ('2e2d4895-16eb-4458-b809-868e937efe57', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'd4ac649b-43f3-4f85-888c-dd85bbca25a5', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'd141c2ff-2c36-4f90-92e0-6c30618dd93a', 'user_withdraw', '20c93a54-fc2b-4aa2-b84d-5739d2fa0e03', '11016650327730850238464', '10', '1010', '8990', '138543541524254152', null, '2020-03-30 17:48:08', '2020-04-01 17:53:58');
INSERT INTO `company_account_history` VALUES ('4c4ebb8d-c3f8-421a-83a7-bf93d3ee1e14', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'd4ac649b-43f3-4f85-888c-dd85bbca25a5', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'd141c2ff-2c36-4f90-92e0-6c30618dd93a', 'user_withdraw', 'ce419939-f645-4606-ab37-ab937e6c570a', '11016651722382010617856', '10', '1000', '6990', '33915968329748480', '3b6ceab3-86b5-4f16-8eff-657730a215f5', '2020-04-03 14:09:58', '2020-04-03 14:12:17');
INSERT INTO `company_account_history` VALUES ('869c17fc-62fe-409e-b1cb-c12f613c8eff', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', 'user_recharge', '1f1da42d-10c0-4e98-89d4-d4f671bc376e', '10126650223726300368896', '0', '1000', '7990', '138543541524254152', '{\"name\":\"value\"}', '2020-03-30 10:54:51', '2020-03-31 18:41:20');
INSERT INTO `company_account_history` VALUES ('cfa5dc80-de0d-4b02-88f7-f004b0df660a', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', 'user_recharge', 'd0fd6dad-0d49-4d3e-ab21-52503825d088', '10126650214023604342784', '0', '1000', '8990', '138543541524254152', 'asdas', '2020-03-30 10:16:18', '2020-03-31 18:43:33');

-- ----------------------------
-- Table structure for `company_admin`
-- ----------------------------
DROP TABLE IF EXISTS `company_admin`;
CREATE TABLE `company_admin` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `company` (`company`),
  CONSTRAINT `company_admin_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_admin
-- ----------------------------

-- ----------------------------
-- Table structure for `company_channel`
-- ----------------------------
DROP TABLE IF EXISTS `company_channel`;
CREATE TABLE `company_channel` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `max` decimal(10,0) NOT NULL COMMENT '最大金额',
  `min` decimal(10,0) NOT NULL COMMENT '最小金额',
  `poundage` decimal(20,0) DEFAULT NULL COMMENT '手续费（定额）',
  `poundage_rate` decimal(10,4) DEFAULT NULL COMMENT '手续费（百分比）',
  `status` varchar(20) NOT NULL COMMENT 'normal/disable/delete',
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `company` (`company`),
  KEY `channel` (`channel`),
  KEY `creator` (`creator`),
  CONSTRAINT `company_channel_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `company_channel_ibfk_2` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `company_channel_ibfk_3` FOREIGN KEY (`creator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_channel
-- ----------------------------
INSERT INTO `company_channel` VALUES ('8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', 'recharge', '10000000', '1', null, '0.0030', 'normal', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-04-03 14:19:41');
INSERT INTO `company_channel` VALUES ('d141c2ff-2c36-4f90-92e0-6c30618dd93a', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'withdraw', '10000000', '1000', null, '0.0030', 'normal', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-04-03 14:17:48');

-- ----------------------------
-- Table structure for `company_trade`
-- ----------------------------
DROP TABLE IF EXISTS `company_trade`;
CREATE TABLE `company_trade` (
  `uuid` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL,
  `company` varchar(36) NOT NULL,
  `order_num` varchar(50) NOT NULL,
  `amount` decimal(20,0) NOT NULL COMMENT '扣除手续费金额',
  `poundage` decimal(20,0) NOT NULL,
  `total_amount` decimal(20,0) NOT NULL COMMENT '总金额',
  `data` text NOT NULL,
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NULL DEFAULT NULL,
  `proof` varchar(36) DEFAULT NULL COMMENT 'checking/checked/fail/close/success',
  `creator` varchar(36) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `order_num` (`order_num`),
  KEY `company` (`company`),
  KEY `operator` (`operator`),
  KEY `creator` (`creator`),
  KEY `proof` (`proof`),
  CONSTRAINT `company_trade_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `company_trade_ibfk_2` FOREIGN KEY (`operator`) REFERENCES `admin` (`uuid`),
  CONSTRAINT `company_trade_ibfk_3` FOREIGN KEY (`creator`) REFERENCES `admin` (`uuid`),
  CONSTRAINT `company_trade_ibfk_4` FOREIGN KEY (`proof`) REFERENCES `resource` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_trade
-- ----------------------------

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  `parent` varchar(36) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `sort` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('11c18105-6344-11ea-93bd-89a085bb1b54', '渠道管理', '2', '7d664978-6343-11ea-93bd-89a085bb1b54', 'channelList.html', '1');
INSERT INTO `menu` VALUES ('4b377a88-6344-11ea-93bd-89a085bb1b54', '账号管理', '2', 'ad4b66ae-6343-11ea-93bd-89a085bb1b54', 'channelAccountList.html', '0');
INSERT INTO `menu` VALUES ('6bab521b-6344-11ea-93bd-89a085bb1b54', '商户管理', '2', '875f8b2b-6343-11ea-93bd-89a085bb1b54', 'companyList.html', '0');
INSERT INTO `menu` VALUES ('7d664978-6343-11ea-93bd-89a085bb1b54', '系统管理', '1', null, null, '0');
INSERT INTO `menu` VALUES ('875f8b2b-6343-11ea-93bd-89a085bb1b54', '商户管理', '1', null, null, '2');
INSERT INTO `menu` VALUES ('ad4b66ae-6343-11ea-93bd-89a085bb1b54', '账号管理', '1', null, null, '1');
INSERT INTO `menu` VALUES ('afc8343f-6344-11ea-93bd-89a085bb1b54', '用户充值管理', '2', 'b292a778-6343-11ea-93bd-89a085bb1b54', 'userRechargeList.html', '0');
INSERT INTO `menu` VALUES ('b292a778-6343-11ea-93bd-89a085bb1b54', '财务管理', '1', null, null, '3');
INSERT INTO `menu` VALUES ('dfc8143a-7341-21e6-13ad-79a08ab11b55', '用户提现管理', '2', 'b292a778-6343-11ea-93bd-89a085bb1b54', 'userWithdrawList.html', '1');
INSERT INTO `menu` VALUES ('fce2736e-6343-11ea-93bd-89a085bb1b54', '管理员管理', '2', '7d664978-6343-11ea-93bd-89a085bb1b54', 'adminList.html', '0');

-- ----------------------------
-- Table structure for `method`
-- ----------------------------
DROP TABLE IF EXISTS `method`;
CREATE TABLE `method` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(300) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `level` int(11) NOT NULL,
  `parent` varchar(36) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_Reference_5` (`parent`),
  CONSTRAINT `method_ibfk_1` FOREIGN KEY (`parent`) REFERENCES `method` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='API方法名';

-- ----------------------------
-- Records of method
-- ----------------------------
INSERT INTO `method` VALUES ('099d64ae-ab74-4adf-bf71-4af132215446', '后台系统管理', '/system', '1', null, '1');
INSERT INTO `method` VALUES ('099d64ae-ab74-4adf-bf71-4af132215448', '系统设置', '/system/admin', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '1');
INSERT INTO `method` VALUES ('099d64ae-ab74-4adf-bf71-4af132215449', '功能设置', '/system/sroleMetho', '2', '099d64ae-ab74-4adf-bf71-4af132215446', '1');
INSERT INTO `method` VALUES ('099d64ae-ab74-4adf-bf71-4af13221544c', '功能设置列表', '/system/sroleMetho/list', '3', '099d64ae-ab74-4adf-bf71-4af132215449', '1');
INSERT INTO `method` VALUES ('099d64ae-ab74-4adf-bf71-4af13221544d', '功能设置修改', '/system/sroleMetho/edit', '3', '099d64ae-ab74-4adf-bf71-4af132215449', '1');
INSERT INTO `method` VALUES ('099d64ae-ab74-4adf-bf71-4af13221544v', '功能设置查询', '/system/sroleMetho/all', '3', '099d64ae-ab74-4adf-bf71-4af132215449', '1');

-- ----------------------------
-- Table structure for `mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `mobile_code`;
CREATE TABLE `mobile_code` (
  `uuid` varchar(36) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `content` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mobile_code
-- ----------------------------

-- ----------------------------
-- Table structure for `notice_info`
-- ----------------------------
DROP TABLE IF EXISTS `notice_info`;
CREATE TABLE `notice_info` (
  `uuid` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'recharge/withdraw',
  `order_num` varchar(30) NOT NULL,
  `data` text NOT NULL,
  `source` text NOT NULL COMMENT '第三方返回数据',
  `status` varchar(20) NOT NULL COMMENT 'normal/success/fail',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `channel` (`channel`),
  CONSTRAINT `notice_info_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice_info
-- ----------------------------

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `size` bigint(20) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('2c47c7d3-b00f-4ae7-81b1-67f61cc14b29', '67f61cc14b29', '/upload/2c47c7d3/b00f/4ae7/81b1/67f61cc14b29/67f61cc14b29.JPG', 'JPG', '292076', '2020-04-01 17:41:47');
INSERT INTO `resource` VALUES ('40679f39-6f01-4166-a97b-474f33a56c82', '474f33a56c82', '/upload/40679f39/6f01/4166/a97b/474f33a56c82/474f33a56c82.JPG', 'JPG', '292076', '2020-04-01 17:31:10');
INSERT INTO `resource` VALUES ('4dfd0ae9-b5ae-4e16-bb64-dc905da608c5', 'dc905da608c5', '/upload/4dfd0ae9/b5ae/4e16/bb64/dc905da608c5/dc905da608c5.png', 'png', '207599', '2020-04-01 17:41:25');
INSERT INTO `resource` VALUES ('61f4e0ae-dab0-493b-8738-86306dbaf48b', '86306dbaf48b', '/upload/61f4e0ae/dab0/493b/8738/86306dbaf48b/86306dbaf48b.JPG', 'JPG', '292076', '2020-04-01 17:53:37');
INSERT INTO `resource` VALUES ('6ec6a22b-cbd9-451a-b56c-ad96a82b101e', 'ad96a82b101e', '/upload/6ec6a22b/cbd9/451a/b56c/ad96a82b101e/ad96a82b101e.JPG', 'JPG', '292076', '2020-04-01 17:50:25');
INSERT INTO `resource` VALUES ('7d0584f7-72d4-4df5-82b2-4114c16ff1c9', '4114c16ff1c9', '/upload/7d0584f7/72d4/4df5/82b2/4114c16ff1c9/4114c16ff1c9.png', 'png', '415148', '2020-04-01 17:40:53');
INSERT INTO `resource` VALUES ('87f6396e-ac3a-4e4b-9ed3-a247809e3119', 'a247809e3119', '/upload/87f6396e/ac3a/4e4b/9ed3/a247809e3119/a247809e3119.png', 'png', '415148', '2020-04-01 17:46:53');
INSERT INTO `resource` VALUES ('89985833-393f-47cd-b13d-1c0ed0d6ac93', '1c0ed0d6ac93', '/upload/89985833/393f/47cd/b13d/1c0ed0d6ac93/1c0ed0d6ac93.png', 'png', '415148', '2020-04-01 17:50:40');
INSERT INTO `resource` VALUES ('8d394bfd-86d0-490a-86bf-49421813af02', '49421813af02', '/upload/8d394bfd/86d0/490a/86bf/49421813af02/49421813af02.png', 'png', '415148', '2020-04-01 17:32:29');
INSERT INTO `resource` VALUES ('8dccd00e-7c60-4eb5-ad6c-2cee3916ceeb', '2cee3916ceeb', '/upload/8dccd00e/7c60/4eb5/ad6c/2cee3916ceeb/2cee3916ceeb.JPG', 'JPG', '292076', '2020-04-01 17:45:07');
INSERT INTO `resource` VALUES ('956451ea-5835-43fa-a7e7-b52ef1f096f3', 'b52ef1f096f3', '/upload/956451ea/5835/43fa/a7e7/b52ef1f096f3/b52ef1f096f3.png', 'png', '207599', '2020-04-01 17:40:33');
INSERT INTO `resource` VALUES ('a1a329ce-d433-456f-9257-ba3af5b50e7e', 'ba3af5b50e7e', '/upload/a1a329ce/d433/456f/9257/ba3af5b50e7e/ba3af5b50e7e.png', 'png', '415148', '2020-04-01 17:44:00');
INSERT INTO `resource` VALUES ('b10de935-1cac-40e9-9239-b833f86fb560', 'b833f86fb560', '/upload/b10de935/1cac/40e9/9239/b833f86fb560/b833f86fb560.JPG', 'JPG', '292076', '2020-04-01 17:23:36');
INSERT INTO `resource` VALUES ('b1c7ee8b-39ca-42c6-80e3-0dc87126d5b2', '0dc87126d5b2', '/upload/b1c7ee8b/39ca/42c6/80e3/0dc87126d5b2/0dc87126d5b2.JPG', 'JPG', '292076', '2020-04-01 17:46:23');
INSERT INTO `resource` VALUES ('b5960df9-cbd5-49d8-b893-100292603c0a', '100292603c0a', '/upload/b5960df9/cbd5/49d8/b893/100292603c0a/100292603c0a.png', 'png', '207599', '2020-04-01 17:33:00');
INSERT INTO `resource` VALUES ('b5c170d2-258e-49e6-86d0-161968d0874e', '161968d0874e', '/upload/b5c170d2/258e/49e6/86d0/161968d0874e/161968d0874e.JPG', 'JPG', '292076', '2020-04-01 18:13:28');
INSERT INTO `resource` VALUES ('b795a089-1a55-4cb7-96b1-7fe99421b267', '7fe99421b267', '/upload/b795a089/1a55/4cb7/96b1/7fe99421b267/7fe99421b267.JPG', 'JPG', '292076', '2020-04-01 17:35:23');
INSERT INTO `resource` VALUES ('c4673993-49c0-4270-bb55-5f13b9b95e24', '5f13b9b95e24', '/upload/c4673993/49c0/4270/bb55/5f13b9b95e24/5f13b9b95e24.png', 'png', '415148', '2020-04-01 17:42:59');
INSERT INTO `resource` VALUES ('cadf36c2-fe36-491d-ae30-916f41ba4694', '916f41ba4694', '/upload/cadf36c2/fe36/491d/ae30/916f41ba4694/916f41ba4694.png', 'png', '207599', '2020-04-01 17:42:28');
INSERT INTO `resource` VALUES ('d4a2b539-1caf-446d-83e7-a8f774bfb567', 'a8f774bfb567', '/upload/d4a2b539/1caf/446d/83e7/a8f774bfb567/a8f774bfb567.png', 'png', '415148', '2020-04-03 14:12:12');
INSERT INTO `resource` VALUES ('da1d4a54-99b5-48e6-9aa9-a6ba8ac28bf0', 'a6ba8ac28bf0', '/upload/da1d4a54/99b5/48e6/9aa9/a6ba8ac28bf0/a6ba8ac28bf0.png', 'png', '415148', '2020-04-01 17:30:50');
INSERT INTO `resource` VALUES ('eeaa0543-7476-4dba-8681-3bf3921c68e6', '3bf3921c68e6', '/upload/eeaa0543/7476/4dba/8681/3bf3921c68e6/3bf3921c68e6.png', 'png', '415148', '2020-04-01 17:18:04');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('9f82edd6-98a1-4e0e-ad64-059346525d82', '财务');
INSERT INTO `role` VALUES ('e71fd95e-adcd-4092-b230-21a457703a1d', '管理员');

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `menu` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `role` (`role`),
  KEY `menu` (`menu`),
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu`) REFERENCES `menu` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('51018870-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', '11c18105-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51018a29-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', '4b377a88-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51018aba-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', '6bab521b-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51018b43-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', '7d664978-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51018bd0-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', '875f8b2b-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51018c61-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'ad4b66ae-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51018ce6-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'afc8343f-6344-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51018d66-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'b292a778-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51018def-6f4c-11ea-9221-82820592cd4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'fce2736e-6343-11ea-93bd-89a085bb1b54');
INSERT INTO `role_menu` VALUES ('51c8143a-7341-21e6-13ad-79a08ab11b4c', 'e71fd95e-adcd-4092-b230-21a457703a1d', 'dfc8143a-7341-21e6-13ad-79a08ab11b55');

-- ----------------------------
-- Table structure for `role_method`
-- ----------------------------
DROP TABLE IF EXISTS `role_method`;
CREATE TABLE `role_method` (
  `uuid` varchar(36) NOT NULL,
  `role` varchar(36) NOT NULL,
  `method` varchar(36) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `role` (`role`),
  KEY `method` (`method`),
  CONSTRAINT `role_method_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`uuid`),
  CONSTRAINT `role_method_ibfk_2` FOREIGN KEY (`method`) REFERENCES `method` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_method
-- ----------------------------

-- ----------------------------
-- Table structure for `user_recharge`
-- ----------------------------
DROP TABLE IF EXISTS `user_recharge`;
CREATE TABLE `user_recharge` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `company_channel` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `channel_account` varchar(36) NOT NULL,
  `order_num` varchar(30) NOT NULL,
  `total_amount` decimal(20,0) NOT NULL COMMENT '总金额',
  `amount` decimal(20,0) DEFAULT NULL COMMENT '扣减手续费金额',
  `poundage` decimal(10,0) DEFAULT NULL,
  `company_order_num` varchar(50) NOT NULL COMMENT '商户订单号',
  `company_data` text COMMENT '商户携带信息',
  `company_notify_url` varchar(200) NOT NULL COMMENT '商户回调地址',
  `timeout` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `trans_data` text COMMENT '平台交易信息',
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NULL DEFAULT NULL,
  `notice_info` varchar(36) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'checking/checked/fail/close/success',
  `process_code` varchar(20) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `order_num` (`order_num`) USING BTREE,
  KEY `company` (`company`),
  KEY `channel` (`channel`),
  KEY `channel_account` (`channel_account`),
  KEY `company_recharge_ibfk_4` (`company_channel`),
  KEY `operator` (`operator`),
  CONSTRAINT `user_recharge_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `user_recharge_ibfk_2` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `user_recharge_ibfk_3` FOREIGN KEY (`channel_account`) REFERENCES `channel_account` (`uuid`),
  CONSTRAINT `user_recharge_ibfk_4` FOREIGN KEY (`company_channel`) REFERENCES `company_channel` (`uuid`),
  CONSTRAINT `user_recharge_ibfk_5` FOREIGN KEY (`operator`) REFERENCES `admin` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_recharge
-- ----------------------------
INSERT INTO `user_recharge` VALUES ('1f1da42d-10c0-4e98-89d4-d4f671bc376e', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650223726300368896', '1000', '1000', '0', '138543541524254152', '{\"name\":\"value\"}', 'https://www.baidu.com', '2020-03-30 11:04:15', '{\"infomation\":\"充值\",\"title\":\"用户充值\"}', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-03-31 18:41:20', null, 'success', null, '2020-03-30 10:54:51');
INSERT INTO `user_recharge` VALUES ('2059460f-6d65-4139-a32a-0cbf617ab00f', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650226838553628672', '1000', '1000', '0', '138543541524254152', '{\"name\":\"value\"}', 'https://www.baidu.com', '2020-03-30 11:16:37', '{\"infomation\":\"充值\",\"title\":\"用户充值\"}', null, '2020-03-30 16:46:02', null, 'close', null, '2020-03-30 11:07:13');
INSERT INTO `user_recharge` VALUES ('3497080f-13a7-4c35-accd-0ab0e0677595', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126646618078367059968', '200', null, null, '1233131213', '11122211345', 'https://www.baidu.com', '2020-03-20 12:17:13', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-27 17:39:04', null, 'close', null, '2020-03-20 12:07:18');
INSERT INTO `user_recharge` VALUES ('42f78138-68e5-4cbd-a39e-83d964fd0c98', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650219189019742208', '2', '2', '0', '177A3{}11a213', 'ASD', 'https://www.baidu.com', '2020-03-30 10:46:48', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-30 16:46:03', null, 'close', null, '2020-03-30 10:36:49');
INSERT INTO `user_recharge` VALUES ('48d7f555-d6e2-458e-af63-377a917ce3ad', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650226299266797568', '1000', '1000', '0', '138543541524254152', '{\"name\":\"value\"}', 'https://www.baidu.com', '2020-03-30 11:14:28', '{\"infomation\":\"充值\",\"title\":\"用户充值\"}', null, '2020-03-30 16:46:03', null, 'close', null, '2020-03-30 11:05:04');
INSERT INTO `user_recharge` VALUES ('65cdc232-da59-42b8-9d5a-6aeb0ea56806', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10121348452093952', '10000', null, null, '1232131', '12345', 'https://www.baidu.com', '2020-03-18 14:45:41', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-18 16:41:36', null, 'close', null, '2020-03-18 14:35:42');
INSERT INTO `user_recharge` VALUES ('68ee8222-bfb0-4339-ae93-a26aec2548f0', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650226699504062464', '1000', '1000', '0', '138543541524254152', '{\"name\":\"value\"}', 'https://www.baidu.com', '2020-03-30 11:16:04', '{\"infomation\":\"充值\",\"title\":\"用户充值\"}', null, '2020-03-30 16:46:02', null, 'close', null, '2020-03-30 11:06:40');
INSERT INTO `user_recharge` VALUES ('6953c74f-e82f-4afd-a5c0-7e467d036efe', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126646600552186777600', '10000', null, null, '1231111213', '111211345', 'https://www.baidu.com', '2020-03-20 11:07:38', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-27 17:39:05', null, 'close', null, '2020-03-20 10:57:39');
INSERT INTO `user_recharge` VALUES ('76133934-2e8e-4eb2-b97f-f57a196e8122', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650217959300141056', '2', '2', '0', '177A3{}11213', 'ASD', 'https://www.baidu.com', '2020-03-30 10:41:55', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-30 16:46:03', null, 'close', null, '2020-03-30 10:31:56');
INSERT INTO `user_recharge` VALUES ('916072a6-8590-420f-aca2-a582a07cf1fd', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650226510806519808', '1000', '1000', '0', '138543541524254152', '{\"name\":\"value\"}', 'https://www.baidu.com', '2020-03-30 11:15:19', '{\"infomation\":\"充值\",\"title\":\"用户充值\"}', null, '2020-03-30 16:46:02', null, 'close', null, '2020-03-30 11:05:55');
INSERT INTO `user_recharge` VALUES ('b2b780b8-39fd-4af7-9b13-3dd6dfd6df38', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126646633535547510784', '2', null, null, '17731213', '117211345', 'https://www.baidu.com', '2020-03-20 13:18:42', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-27 17:39:03', null, 'close', null, '2020-03-20 13:08:43');
INSERT INTO `user_recharge` VALUES ('b6773b77-7dd4-44c6-b8a4-437f564096b6', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126646617641442217984', '2', null, null, '123113131213', '1112222211345', 'https://www.baidu.com', '2020-03-20 12:15:31', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-27 17:39:04', null, 'close', null, '2020-03-20 12:05:33');
INSERT INTO `user_recharge` VALUES ('c2c39b72-96f9-45e0-b068-792dd5b21a02', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126646614376382074880', '2', null, null, '1231131111213', '111222211345', 'https://www.baidu.com', '2020-03-20 12:02:32', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-27 17:39:05', null, 'close', null, '2020-03-20 11:52:35');
INSERT INTO `user_recharge` VALUES ('c9bc661b-1b3b-4627-a22b-4407a3eb3b44', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650220757551026176', '1000', '1000', '0', '138543541524254152', '{\"name\":\"value\"}', 'https://www.baidu.com', '2020-03-30 10:52:27', '{\"infomation\":\"充值\",\"title\":\"用户充值\"}', null, '2020-03-30 16:46:03', null, 'close', null, '2020-03-30 10:43:03');
INSERT INTO `user_recharge` VALUES ('d0fd6dad-0d49-4d3e-ab21-52503825d088', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126650214023604342784', '1000', '1000', '0', '138543541524254152', 'asdas', 'https://www.baidu.com', '2020-03-30 10:25:42', '{\"infomation\":\"充值\",\"title\":\"用户充值\"}', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-03-31 18:43:33', null, 'success', null, '2020-03-30 10:16:18');
INSERT INTO `user_recharge` VALUES ('d3419658-1d11-4c68-a8bf-34120b1202f4', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126646593509849698304', '10000', null, null, '12311213', '1112345', 'https://www.baidu.com', '2020-03-20 10:39:39', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-27 17:39:06', null, 'close', null, '2020-03-20 10:29:40');
INSERT INTO `user_recharge` VALUES ('e2b47270-371b-4bc5-9151-6ca71d8a638d', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10126646597541242736640', '10000', null, null, '123111213', '11121345', 'https://www.baidu.com', '2020-03-20 10:55:41', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-27 17:39:05', null, 'close', null, '2020-03-20 10:45:41');
INSERT INTO `user_recharge` VALUES ('eb93aad8-6896-4540-b63a-0b2aaaea3aae', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', '8c41c2ff-2c36-4f90-92e0-6c30618dd92d', '1075f929-5f6d-4d54-a64a-f728e0c92d04', '04ac649b-43f3-4f85-888c-dd85bbca21e0', '10122223116288', '10000', null, null, '123213', '12345', 'https://www.baidu.com', '2020-03-18 14:47:18', '{\"infomation\":\"一件\",\"title\":\"商品\"}', null, '2020-03-27 17:39:06', null, 'close', null, '2020-03-18 14:37:20');

-- ----------------------------
-- Table structure for `user_withdraw`
-- ----------------------------
DROP TABLE IF EXISTS `user_withdraw`;
CREATE TABLE `user_withdraw` (
  `uuid` varchar(36) NOT NULL,
  `company` varchar(36) NOT NULL,
  `company_channel` varchar(36) NOT NULL,
  `channel` varchar(36) NOT NULL,
  `channel_account` varchar(36) DEFAULT NULL,
  `order_num` varchar(30) NOT NULL,
  `total_amount` decimal(20,0) NOT NULL COMMENT '总金额',
  `amount` decimal(20,0) DEFAULT NULL COMMENT '扣减手续费金额',
  `poundage` decimal(20,0) DEFAULT NULL,
  `company_order_num` varchar(50) NOT NULL COMMENT '商户订单号',
  `company_data` text COMMENT '商户携带信息',
  `company_notify_url` varchar(200) NOT NULL COMMENT '商户回调地址',
  `trans_data` text COMMENT '平台交易信息',
  `operator` varchar(36) DEFAULT NULL,
  `operattime` timestamp NULL DEFAULT NULL,
  `proof` varchar(36) DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT 'checking/checked/fail/close/success',
  `fail_reason` varchar(100) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `order_num` (`order_num`) USING BTREE,
  KEY `company` (`company`),
  KEY `company_channel` (`company_channel`),
  KEY `channel` (`channel`),
  KEY `channel_account` (`channel_account`),
  KEY `operator` (`operator`),
  KEY `proof` (`proof`),
  CONSTRAINT `user_withdraw_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_2` FOREIGN KEY (`company_channel`) REFERENCES `company_channel` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_3` FOREIGN KEY (`channel`) REFERENCES `channel` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_4` FOREIGN KEY (`channel_account`) REFERENCES `channel_account` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_5` FOREIGN KEY (`operator`) REFERENCES `admin` (`uuid`),
  CONSTRAINT `user_withdraw_ibfk_6` FOREIGN KEY (`proof`) REFERENCES `resource` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_withdraw
-- ----------------------------
INSERT INTO `user_withdraw` VALUES ('20c93a54-fc2b-4aa2-b84d-5739d2fa0e03', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'd141c2ff-2c36-4f90-92e0-6c30618dd93a', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'd4ac649b-43f3-4f85-888c-dd85bbca25a5', '11016650327730850238464', '1010', '1000', '10', '138543541524254152', null, 'https://www.baidu.com', '{\"bank\":\"中国银行\",\"bankcard\":\"165464343463486385\",\"holder\":\"朱某某\",\"title\":\"用户充值\"}', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-04-01 18:13:11', null, 'checked', '100001', '2020-03-30 17:48:08');
INSERT INTO `user_withdraw` VALUES ('418d6941-e8d6-49b9-9f0c-93fa7d72461b', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'd141c2ff-2c36-4f90-92e0-6c30618dd93a', '5075f929-5f6d-1d54-a64a-f728e0c92e12', null, '11016650239011615019008', '2000', '1990', '10', '1412421521521', 'hehe', 'https://www.baidu.com', '{\"bank\":\"大唐很行\",\"bankcard\":\"62220231242151\",\"holder\":\"猪悟能\",\"title\":\"提现\"}', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-04-01 16:59:40', null, 'fail', 'asf', '2020-03-30 11:55:35');
INSERT INTO `user_withdraw` VALUES ('bce0f89f-7c1d-4754-89f3-1074673854be', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'd141c2ff-2c36-4f90-92e0-6c30618dd93a', '5075f929-5f6d-1d54-a64a-f728e0c92e12', null, '11016651721397691355136', '1000', '990', '10', '33914983419088896', '10215c0a-d6be-414d-9924-a94b8993ddae', 'http://192.168.1.102:28080/notice/recharge/byUnion', '{\"bank\":\"中国大唐很行\",\"bankcard\":\"6214830164014598\",\"holder\":\"刘海迪\",\"title\":\"用户提现\"}', null, null, null, 'checking', null, '2020-04-03 14:06:04');
INSERT INTO `user_withdraw` VALUES ('cc7f26f2-7cb9-44bf-be20-1c95e8c555ab', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'd141c2ff-2c36-4f90-92e0-6c30618dd93a', '5075f929-5f6d-1d54-a64a-f728e0c92e12', null, '11016650238375368462336', '2000', '1990', '10', '142421521521', 'hehe', 'https://www.baidu.com', '{\"bank\":\"大唐很行\",\"bankcard\":\"62220231242151\",\"holder\":\"猪悟能\",\"title\":\"提现\"}', null, '2020-03-30 11:55:07', null, 'close', '', '2020-03-30 11:53:04');
INSERT INTO `user_withdraw` VALUES ('ce419939-f645-4606-ab37-ab937e6c570a', '2c41c2ff-2c36-4f90-92e0-6c30618dd91d', 'd141c2ff-2c36-4f90-92e0-6c30618dd93a', '5075f929-5f6d-1d54-a64a-f728e0c92e12', 'd4ac649b-43f3-4f85-888c-dd85bbca25a5', '11016651722382010617856', '1000', '990', '10', '33915968329748480', '3b6ceab3-86b5-4f16-8eff-657730a215f5', 'http://192.168.1.102:28080/notice/recharge/byUnion', '{\"bank\":\"中国大唐很行\",\"bankcard\":\"6214830164014598\",\"holder\":\"刘海迪\",\"title\":\"用户提现\"}', '1d0db2b5-2a10-482c-a6c2-57adabdccbf6', '2020-04-03 14:12:17', null, 'success', null, '2020-04-03 14:09:58');

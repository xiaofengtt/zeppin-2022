<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教育部  财政部中小学教师国家级培训计划</title>
<link href="/entity/query_expert/css/zjcx.css" rel="stylesheet" type="text/css" />
</head>

<body style="text-align:center;">
	<div id="wrap">
	  <div id="header"></div>
	  <div id="main">
	 	<div id="main1"></div>
		<div id="main1_b"></div>
		<div id="main2">
			<div id="main2_l"></div>
			<div id="main2_2">
				<div id="user" style="text-align:left">
					<div class="userlist"><span class="user_tit">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><span class="user_info"><s:property value="expert[0][0]"/></span></div>
					<div class="userlist"><span class="user_tit">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span><span class="user_info"><s:property value="expert[0][1]"/></span></div>
					<div class="userlist"><span class="user_tit">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</span><span class="user_info"><s:property value="expert[0][2]"/></span></div>
					<div class="userlist"><span class="user_tit">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</span><span class="user_info"><s:property value="expert[0][3]"/></span></div>
					<div class="userlist"><span class="user_tit">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：</span><span class="user_info"><s:property value="expert[0][4]"/></span></div>
					<div class="userlist"><span class="user_tit">电子邮件：</span><span class="user_info"><s:property value="expert[0][5]"/></span></div>
					<div class="userlist"><span class="user_tit">办公电话：</span><span class="user_info"><s:property value="expert[0][6]"/></span></div>
				</div>
			</div>
			<div id="main2_3"></div>
		</div><div class="clear"></div>
		<div id="main3">
			<div id="back"><a href="/entity/first/userQueryTrainExpertAction_initData.action"><img src="/entity/query_expert/images/fh.jpg" width="65" height="24" border="0" /></a></div>
		
		</div> <div class="clear"></div>
	  </div>
		<div id="bottom">主管单位：教育部师范教育司 主办单位：国培计划—中小学骨干教师培训项目执行办公室<br />
		  联系电话：010-58800182　 传真：010-58802946　 电子邮箱：xmb@gpjh.cn<br />
		  地址：北京市新街口外大街19号北京师范大学继续教育与教师培训学院<br />
	    京ICP备10031106号</div>
		
	</div>
</body>
</html>

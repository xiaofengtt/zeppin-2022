<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教育部  财政部中小学教师国家级培训计划</title>
<link href="/entity/query_expert/css/zscx.css" rel="stylesheet" type="text/css" />
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
					<div class="userlist"><span class="user_tit">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="user_info"><s:property value="peTrainee.name"/></span></div>
					<div class="userlist"><span class="user_tit">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="user_info"><s:property value="peTrainee.enumConstByFkGender.name"/></span></div>
					<div class="userlist"><span class="user_tit">参训项目批次：</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="user_info"><s:property value="peTrainee.peProApplyno.name"/></span></div>
					<div class="userlist"><span class="user_tit">参&nbsp;&nbsp;&nbsp;训&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;位：</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="user_info"><s:property value="peTrainee.peUnitByFkTrainingUnit.name"/></span></div>
					<div class="userlist"><span class="user_tit">学&nbsp;科&nbsp;/&nbsp;子项目：</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="user_info"><s:property value="peTrainee.peSubject.name"/></span></div>
					<div class="userlist"><span class="user_tit">参训起止时间：</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="user_info"><s:date name="peTrainee.startDate" format="yyyy-MM-dd"/>&nbsp;&nbsp到&nbsp;&nbsp<s:date name="peTrainee.endDate" format="yyyy-MM-dd"/></span></div>
					<div class="userlist"><span class="user_tit">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时：</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="user_info"><s:property value="peTrainee.peProApplyno.classHour"/></span></div>
					<div class="userlist"><span class="user_tit">证&nbsp;&nbsp;&nbsp;书&nbsp;&nbsp;&nbsp;编&nbsp;&nbsp;&nbsp;号：</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="user_info"><s:property value="peTrainee.certificateNumber"/></span></div>
				</div>
			</div>
			<div id="main2_3"></div>
		</div><div class="clear"></div>
		<div id="main3">
			<div id="back"><a href="/entity/first/userQueryCertificateNo_userQuery.action"><img src="/entity/query_expert/images/fh.jpg" width="65" height="24" border="0" /></a></div>
		
		</div> <div class="clear"></div>
	  </div>
		<div id="bottom">主管单位：教育部师范教育司 主办单位：国培计划—中小学骨干教师培训项目执行办公室<br />
		  联系电话：010-58800182　 传真：010-58802946　 电子邮箱：xmb@gpjh.cn<br />
		  地址：北京市新街口外大街19号北京师范大学继续教育与教师培训学院<br />
	    京ICP备10031106号</div>
		
	</div>
</body>
</html>

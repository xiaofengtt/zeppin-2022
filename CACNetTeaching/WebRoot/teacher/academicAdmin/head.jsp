<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
     <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="">
   <title><s:property value="#parameters.title"/></title>
    <!-- CSS -->
   <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/app.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui.css" >
   <!--   <link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet"> -->
	 <link href="css/ui.jqgrid.css" rel="stylesheet">

   
   
    <link rel="shortcut icon" href="ico/favicon.png"/>
      <!-- JS -->
   
<script src="js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="js/app.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

  </head>
  
 <body>
 <div class="cca-userbar">
		  <div class="cca-user">
			  <span><img class="icon" src="img/u.gif" style="width:25px;height:25px;">&nbsp;&nbsp; <s:property value="#session.academic.name"/>&nbsp;&nbsp;&nbsp;</span>
			  <a href="login_logout.action">[退出]&nbsp;&nbsp;&nbsp;</a>
			  <a href="admin_userInfoInit.action"><img src="img/setting.png"></a>
		  </div>
	  </div>
	  <div class="cca-header clearfix">
		  <h1 class="logo"><img src="img/logo-mini.png" alt="中央美术学院 建筑学院网络教学系统"></h1>
		  <ul class="cca-nav">
			  <li class="news active"><a href="admin_messageInit.action">消息通知</a></li>
			  <li class="setting"><a href="admin_studentManageInit.action">院系设置</a></li>
			  
			  <li class="courses"><a href="course_planManageInit.action">课程管理</a></li>
			  
			 <!--<li class="questions"><a href="#">问卷管理</a></li>  --> 
			  
		  </ul>
	  </div>
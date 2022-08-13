<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>华南师大</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <br><br><br><br><br><a href="/entity/manager/index.jsp?type=1">管理员工作室</a> <br>
    <br><a href="/entity/teacher/teacher_announce.jsp">教师工作室</a> <br>
    <br><a href="/entity/student/main_frame.jsp">学生工作室</a> <br>
    <br><a href="/admin/admin_index.jsp">系统管理员工作室</a> <br>
  </body>
</html>

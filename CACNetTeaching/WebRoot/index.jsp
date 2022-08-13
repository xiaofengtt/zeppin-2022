<%@page import="com.zeppin.util.cryptogram.ECrypMethod"%>
<%@page import="com.zeppin.util.cryptogram.ECrypType"%>
<%@page import="com.zeppin.util.cryptogram.GetCriptString"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String s1=GetCriptString.getString("好好学习", ECrypType.TRIDES, ECrypMethod.ENCODE);
String s2=GetCriptString.getString(s1, ECrypType.TRIDES, ECrypMethod.DECODE);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
    This is my JSP page. <br>
    <%=s1 %><br>
    <%=s2 %>
    <form action="login.action">
 
    <input type="submit"> 
    </form>
    
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder, com.whaty.platform.config.*" %>
<%@ page import ="com.whaty.platform.config.ForumConfig" %>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
	UserSession userSession = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	response.setHeader("Expires", "0");
%>
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
    <form  name="forum" action="http://www.google.cn" target="_blank" method="post">
    </form>
  </body>
<script type="text/javascript" >
<!--
//alert("没有开通答疑室");
	window.parent.open('http://210.76.98.124:8080/mcs2/vlog.asp?roomid=9025&op=0&user='+escape("<s:property value="peEnterperisemanager.getName()"/>")+'&nick='+escape("<s:property value="peEnterperisemanager.getName()"/>")+'&roomname='+escape("中国移动通信集团公司")+'&roomgroup='+escape("企业答疑室"),"mcs2");
//-->
</script>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.net.URLEncoder, com.whaty.platform.config.*" %>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@ include file="./pub/priv.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<%

	ForumConfig forumConfig = (ForumConfig)application.getAttribute("forumConfig");
	
	String basePath =forumConfig.getForum_Url();
	
	String stName = request.getParameter("teacherName");
	String stLoginId = request.getParameter("teacherLoginId");
	
	
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
    <form  name="forum" action="<%=basePath%>login.whaty?action=external" method="post" target="_blank">
		<input type="hidden" name="autoCreateForum" value="1">  
		<input type="hidden" name="autoBeBM" value="0">
		<input type="hidden" name="autoBeAuth" value="1">
		<input type="hidden" name="pid" value="<%=forumConfig.getPaper_Board()%>">   
		<input type="hidden" name="username" value="<%=session_student.getLoginId()%>">
		<input type="hidden" name="passwd" value="<%=session_student.getPassword()%>">
		<input type="hidden" name="applicationType" value="<%=SsoConstant.INTERACTION_PAPER_FORUM_APPLICTION_TYPE %>">
		<input type="hidden" name="applicationId" value="<%=stLoginId%>">
		<input type="hidden" name="boardName" value="<%=stName%>">
    </form>
  </body>
<script type="text/javascript">
<!--
	 document.forum.submit();
	window.history.back();
//-->
</script>
</html>

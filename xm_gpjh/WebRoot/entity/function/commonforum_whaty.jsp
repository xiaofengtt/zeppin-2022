<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.net.URLEncoder, com.whaty.platform.config.*" %>
<%@ page import ="com.whaty.platform.config.ForumConfig" %>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>

 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<%
	UserSession userSession = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	ForumConfig forumConfig = (ForumConfig)application.getAttribute("forumConfig");
	
	String basePath =forumConfig.getForum_Url();
	
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
    <form  name="forum" action="<%=basePath%>login.whaty?action=external"  method="post">
		<input type="hidden" name="autoCreateForum" value="1">  
		
		<input type="hidden" name="autoBeBM" value="0">
		
		<input type="hidden" name="autoBeAuth" value="1">
		<input type="hidden" name="pid" value="0">         
		<input type="hidden" name="username" value="<%=userSession.getLoginId()%>">
		<input type="hidden" name="passwd" value="<%=userSession.getSsoUser().getPassword()%>">
		<input type="hidden" name="applicationType" value="<%=SsoConstant.INTERACTION_COMMON_FORUM_APPLICATION_TYPE %>">
		<input type="hidden" name="applicationId" value="<%=forumConfig.getClass_Board()%>">
		<input type="hidden" name="boardName" value="<%=SsoConstant.INTERACTION_COMMON_FORUM_BOARDNAME %>">
    </form>
  </body>
<script type="text/javascript">
<!--
	 document.forum.submit();

//-->
</script>
</html>

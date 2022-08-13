<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/> 
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java" %>
<html>
  <head>
    <title></title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=request.getContextPath()%>/entity/student/message/message_ajax.js" ></script>

  </head>
  <% UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
  	 String str = "";
  	 if(us != null){
  	   str = us.getLoginId();
  	 }else{
  	 	%>
  	 	<script type="text/javascript">
  	 	</script>
  	 	<%
  	 	return;
  	 }
   %>
 <body onload='makeRequest("<%=request.getContextPath()%>/message/msginfo_status.action?userId=<%=str%>")'>
 	<div id="msgview" name="msgview"></div>
  </body>
</html>

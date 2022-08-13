<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("expires", "0"); %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
	 	UserSession userSession = (UserSession)session.getAttribute("user_session");
		if (userSession == null) {
%>
		<script>
			window.top.alert("登录超时，为了您的帐户安全，请重新登录。");
			window.top.location = "/";
		</script>
<%
		return;
		}
		
%>	

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<s:property value="topMenu" escape="false"/>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
 %>
<html>
  <head>
    
    <title></title>
	<script type="text/javascript">
	
	function checkLogin()
	{
		//alert('<s:property value="loginId"/>');
		if(confirm("本帐号已经登录，是否注销已登录帐号，重新登录?"))
		{
			 //window.top.location.href="<%=basePath%>/sso/login_login.action?loginId=<s:property value="loginId"/>&passwd=<s:property value="passwd"/>";
			 window.document.loginform.submit();
		}
		else
			 window.top.location.href="<%=basePath%>/entity/first/firstInfoNews_toIndex.action";
	}
	//checkLogin();
	</script>
  </head>
<body onload="checkLogin()">
<form id="loginform" name="loginform" method="post" action="/sso/login_login.action" target="_self" >
<input type="hidden" name="loginId" value="<s:property value="loginId"/>">
<input type="hidden" name="passwd" value="<s:property value="passwd"/>">
</form>
</body>
</html>

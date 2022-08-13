<i><%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@taglib prefix="s" uri="/struts-tags"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	This is my JSP page.
	<br>
	<form id="sysUser_login" name="sysUser_login" action="sysUser_login" method="post">
		<table class="wwFormTable">
			<tr>
				<td class="tdLabel"><label for="sysUser_login_loginName"
					class="label">用户名:</label></td>
				<td><input type="text" name="loginname" value=""
					id="loginname"></td>
			</tr>

			<tr>
				<td class="tdLabel"><label for="sysUser_login_passWord"
					class="label">密码:</label></td>
				<td><input type="password" name="password"
					id="password"></td>
			</tr>

			<tr>
				<td colspan="2"><div align="right">
						<button id="submit" type="submit">立即登录</button>
					</div></td>
			</tr>

		</table>
	</form>
</body>
	</html> </i>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.database.*"%>
<%@ include file="./pub/priv.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
		<title>系统管理</title>
		<link href="css.css" rel="stylesheet" type="text/css" />
	</head>
	<%
		String jndiname = request.getParameter("jndiname");
		
	%>
	<body topmargin="0" leftmargin="0" bottommargin="0" rightmargin="0" style="background-color:transparent" scroll="no">

		<table cellpadding="6" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="1%" valign="top">

				</td>
				<td width="99%" valign="top">

					<b>数据库连接设置</b>
					<hr size="0">

					<font size="-1"> whaty需要使用数据库完成其功能。请填写以下各项完成数据库连接需要的数据。 <br><B>注意：</B>你应该已经使用数据库安装脚本完成数据库的建立工作。
					</font>
					<p>
					<form action="manage_setdb.jsp" method="post">
						<input type="hidden" name="validate" value="true">

						<table align=center width=80% cellpadding="4" cellspacing="0"
							border="0">

							<tr>

								<td class="zhengwen">
									教务系统数据库连接池参数：
								</td>
								<td class="zhengwen">
									<input name=jndiname value="">
									(例如：jdbc/whatymanager)
								</td>
							</tr>
							
						</table>

						<p>
							<input type=hidden name=mit value='1'>
						<hr size="0">
						<div align="center">
							<input type="submit" value="连接测试...">
							<br>
							<font size="-2">(这可能会花费几分钟)</font>
					</form>
				</td>
			</tr>
		</table>
		<%
			if(jndiname != null && !"".equals(jndiname)) {
				DbConnector dbConn = new DbConnector();
				String connMsg = dbConn.connect(jndiname);
				out.print("<center><font color=red>"+connMsg+"</font></center>");
			}
		%>
	</body>
</html>

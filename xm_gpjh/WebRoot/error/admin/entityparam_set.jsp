<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.entity.config.EntityConfig" %>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>系统管理</title>
</head>
<body>

<%
	String prefix =  getServletContext().getRealPath("/");
	String configdir=prefix+"WEB-INF"+File.separator+"config"+File.separator;
	EntityConfig entityConfig = new EntityConfig(configdir);
	entityConfig.getConfig();
	
	
	String entityConfigAbsPath = entityConfig.getEntityConfigAbsPath();
	
	String entityWebIncomingAbsPath = entityConfig.getEntityWebIncomingAbsPath();
	
	String entityWebIncomingUriPath = entityConfig.getEntityWebIncomingUriPath();	
	
	String entityWebFtpPort = entityConfig.getEntityWebFtpPort();
	
	String entitySystemName = entityConfig.getEntitySystemName();
	
	String entityCopyRight = entityConfig.getEntityCopyRight();
	
	String entityLink = entityConfig.getEntityLink();	
	
	String entityCollege = entityConfig.getEntityCollege();
%>
<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center>
<form action="entityparam_setexe.jsp" method=post>


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">教务系统Config文件绝对目录：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=entityConfigAbsPath value="<%=entityConfigAbsPath%>" size=50></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">教务系统上载目录绝对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=entityWebIncomingAbsPath value="<%=entityWebIncomingAbsPath%>" size=50 ></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">教务系统上载目录相对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=entityWebIncomingUriPath value="<%=entityWebIncomingUriPath%>" size=50></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">教务系统FTP端口：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=entityWebFtpPort value="<%=entityWebFtpPort%>" size=50></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">教务系统名称：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=entitySystemName value="<%=entitySystemName%>" size=50></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">教务系统版权说明：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=entityCopyRight value="<%=entityCopyRight%>" size=50 ></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">联系方式：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=entityLink value="<%=entityLink%>" size=50></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">所属学校：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=entityCollege value="<%=entityCollege%>" size=50></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan=2 align="center"><input type="submit" value="提交"></td>
</tr>
<form>
</table>
</html>
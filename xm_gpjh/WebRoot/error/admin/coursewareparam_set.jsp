<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.courseware.config.CoursewareConfig" %>
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
	CoursewareConfig coursewareConfig = new CoursewareConfig(configdir);
	coursewareConfig.getConfig();	
	
	String coursewareConfigAbsPath = coursewareConfig.getCoursewareConfigAbsPath();

	String coursewareTemplateAbsPath = coursewareConfig.getCoursewareTemplateAbsPath();

	String coursewareTemplateRefPath = coursewareConfig.getCoursewareTemplateRefPath();

	String coursewareTemplateImgAbsPath = coursewareConfig.getCoursewareTemplateImgAbsPath();

	String coursewareTemplateImgRefPath = coursewareConfig.getCoursewareTemplateImgRefPath();

	String coursewareAbsPath = coursewareConfig.getCoursewareAbsPath();

	String coursewareRefPath = coursewareConfig.getCoursewareRefPath();

	String coursewareURI = coursewareConfig.getCoursewareURI();

	String cryptType = coursewareConfig.getCryptType();
%>
<form action="coursewareparam_setexe.jsp" method=post>
<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件Config绝对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=coursewareConfigAbsPath value="<%=coursewareConfigAbsPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件模板绝对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=coursewareTemplateAbsPath value="<%=coursewareTemplateAbsPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件模板相对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=coursewareTemplateRefPath value="<%=coursewareTemplateRefPath%>"  size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件模板图片绝对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=coursewareTemplateImgAbsPath value="<%=coursewareTemplateImgAbsPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件模板图片相对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=coursewareTemplateImgRefPath value="<%=coursewareTemplateImgRefPath%>" size=50></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件绝对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=coursewareAbsPath value="<%=coursewareAbsPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件相对地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=coursewareRefPath value="<%=coursewareRefPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件URI：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=coursewareURI value="<%=coursewareURI%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">课件加密方式：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=cryptType value="<%=cryptType%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan=2 align="center"><input type="submit" value="提交"></td>
</tr>
</table>
</form>
<body>
</html>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
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
	PlatformConfig platformConfig = new PlatformConfig(configdir);
	platformConfig.getConfig();
	
	
	String platformConfigAbsPath = platformConfig.getPlatformConfigAbsPath();
	
	String platformWebAppAbsPath = platformConfig.getPlatformWebAppAbsPath();
	
	String platformWebAppUriPath = platformConfig.getPlatformWebAppUriPath();
	
	String platformConfigRefPath = platformConfig.getPlatformConfigRefPath();
	
	String platformWebINFRefPath = platformConfig.getPlatformWebINFRefPath();
	
	String platformIP = platformConfig.getPlatformIP();
	
	String platformPort = platformConfig.getPlatformPort();
	
	String platformDomainName = platformConfig.getPlatformDomainName();
%>
<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center>
<form action="platformparam_setexe.jsp" method=post>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">服务器IP地址：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=platformIP value="<%=platformIP%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">端口：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=platformPort value="<%=platformPort%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">服务器域名：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=platformDomainName value="<%=platformDomainName%>"  size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">Config文件绝对目录：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=platformConfigAbsPath value="<%=configdir%>" size=50 readonly></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">web应用程序绝对目录：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=platformWebAppAbsPath value="<%=prefix%>" size=50 readonly></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">web应用目录：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=platformWebAppUriPath value="<%=platformWebAppUriPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">Config文件相对目录：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=platformConfigRefPath value="<%=platformConfigRefPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">WEB-INF相对目录：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=platformWebINFRefPath value="<%=platformWebINFRefPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan=2 align="center"><input type="submit" value="提交"></td>
</tr>
<form>
</table>
</html>
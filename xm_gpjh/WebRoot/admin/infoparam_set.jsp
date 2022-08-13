<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.info.config.InfoConfig" %>
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
	InfoConfig infoConfig = new InfoConfig(configdir);
	infoConfig.getConfig();	
	
	String infoConfigAbsPath = infoConfig.getInfoConfigAbsPath();

	String infoCollege = infoConfig.getInfoCollege();

	String infoCopyRight = infoConfig.getInfoCopyRight();

	String infoLink = infoConfig.getInfoLink();

	String infoSystemName = infoConfig.getInfoSystemName();

	String infoWebFtpPort = infoConfig.getInfoWebFtpPort();

	String infoWebIncomingAbsPath = infoConfig.getInfoWebIncomingAbsPath();

	String infoWebIncomingUriPath = infoConfig.getInfoWebIncomingUriPath();
%>
<form action="infoparam_setexe.jsp" method=post>
<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">Config文件绝对目录：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=infoConfigAbsPath value="<%=infoConfigAbsPath%>" size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">信息系统上载绝对路径：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=infoWebIncomingAbsPath value="<%=infoWebIncomingAbsPath%>" size=50 ></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">信息系统上载相对路径：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=infoWebIncomingUriPath value="<%=infoWebIncomingUriPath%>" size=50 ></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">信息系统FTP端口：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=infoWebFtpPort value="<%=infoWebFtpPort%>" size=50 ></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">信息系统名称：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=infoSystemName value="<%=infoSystemName%>" size=50 ></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">信息系统版权说明：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=infoCopyRight value="<%=infoCopyRight%>" size=50 ></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">联系方式：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=infoLink value="<%=infoLink%>" size=50 ></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">所属学校：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=infoCollege value="<%=infoCollege%>" size=50 ></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan=2 align="center"><input type="submit" value="提交"></td>
</tr>
</table>
<form>
</html>
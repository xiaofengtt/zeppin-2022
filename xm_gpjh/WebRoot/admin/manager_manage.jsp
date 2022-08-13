<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>管理员管理</title>
</head>
<body>


<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">
<form action="platformparam_setexe.jsp" method=post>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">管理员管理</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="/sso/admin/peManager.action">师范司项目办</a></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">管理员管理</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="/sso/admin/peSitemanager.action">分站管理员</a></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">管理员管理</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="/sso/admin/peEnterprisemanager.action">企业管理员</a></td>
</tr>



<form>
</table>
</body>
</html>
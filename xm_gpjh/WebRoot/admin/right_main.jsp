<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>权限管理</title>
</head>
<body>


<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">
<form action="platformparam_setexe.jsp" method=post>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">EnumConst</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="/test/enumConst.action">EnumConst管理(仅供开发内测用)</a></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">SystemVariables</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="/test/systemVariables.action">SystemVariables管理(仅供开发内测用)</a></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">权限管理</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="/sso/admin/pricatetory.action">管理权限类别(仅供开发内测用)</a></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">权限管理</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="/sso/admin/priority.action">管理权限(仅供开发内测用)</a></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">权限管理</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="/sso/admin/pePriRole.action">管理角色</a></td>
</tr>



<form>
</table>
</body>
</html>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="./pub/priv.jsp"%>


<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>修改密码</title>
<script type="text/javascript">
	function pageGuarding() {
		if(document.forms["pwd"].oldpwd.value == "") {
			alert("原密码不能为空");
			document.forms["pwd"].oldpwd.focus();
			return false;
		}
		
		if(document.forms["pwd"].newpwd.value == "") {
			alert("新密码不能为空");
			document.forms["pwd"].newpwd.focus();
			return false;
		}
		
		if(document.forms["pwd"].newpwd_confirm.value != document.forms["pwd"].newpwd.value) {
			alert("两次密码输入不一致!");
			document.forms["pwd"].newpwd.focus();
			return false;
		}
	}
</script>
</head>
<body>
<form name="pwd" action="manager_password_changeexe.jsp" method=post onsubmit="return pageGuarding();">
<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">原密码：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=password name=oldpwd size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">新密码：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=password name=newpwd size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen">重复新密码：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type=password name=newpwd_confirm size=50></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan=2 align="center"><input type="submit" value="提交"></td>
</tr>
</table>
</form>
</html>
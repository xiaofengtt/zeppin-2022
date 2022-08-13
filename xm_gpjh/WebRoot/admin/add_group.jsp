<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>

<%
PlatformFactory platformfactory = PlatformFactory.getInstance();
BasicRightManage rightManage = platformfactory.creatBasicRightManage();




%>
<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>权限管理</title>

</head>
<script language="javascript">
   
   function pageGuarding()
   {
   
    if (document.change.change_name.value.length==0)
       {
           alert("请填写名称！");
           document.change.change_name.focus();
           return false;
       }
      
      document.change.submit();
   }

</script>
<body>

<form action="add_groupexe.jsp" method=post name="change">

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%" colspan="2" align="center">添加权限组</td>
	
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">权限组名:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="change_name" size="30"></td>
</tr>



</table>
<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="#" onclick="javascript:pageGuarding();">确定</a> &nbsp;&nbsp; <a href="right_group.jsp">返回</a></div>

</form>
</body>
</html>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>权限组管理</title>
</head>

<body>
<script language=javascript>			
		function del(url)
		{
		if(confirm("请先确认该权限组下没有管理员,你确实要删除吗?"))
		{
			window.location=url;
		}
		
		}
</script>

<%
PlatformFactory platformfactory = PlatformFactory.getInstance();
BasicRightManage rightManage = platformfactory.creatBasicRightManage();
List groupRight=rightManage.getRightGroup();
%>

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">
<form action="platformparam_setexe.jsp" method=post>

<tr > 
    <td colspan="6"  align="left" bgcolor="#D4E4EB" class="zhengwen"><div align="center">权限组列表</div></td>
  </tr>
  <tr> 
    <td  align="left"  width="18%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center">权限组名称</div></td>
    <td  align="left"  width="21%" bgcolor="#D4E4EB" class="zhengwen">该组的管理员</td>
    <td  align="left" colspan="4" bgcolor="#D4E4EB" class="zhengwen"><div align="center">操作</div></td>
  </tr>
  
  <%
  if(groupRight!=null){
     for(int i=0;i<groupRight.size();i++){
     
      RightGroup group=(RightGroup) groupRight.get(i); 
    
 
  
  %>
  <tr> 
    <td align="center"  height="21" bgcolor="#D4E4EB" class="zhengwen"><%=group.getName()%></td>
    <td align="center"  bgcolor="#D4E4EB" class="zhengwen"><a href="query_group_main.jsp?group_id=<%=group.getId()%>&group_name=<%=group.getName()%>">查看</a></td>
    <td align="center"  width="16%" bgcolor="#D4E4EB" class="zhengwen"><a href="change_group_name.jsp?group_id=<%=group.getId()%>&group_name=<%=group.getName()%>">更改组名</a></td>
    <td align="center"  width="16%" bgcolor="#D4E4EB" class="zhengwen"><a href="change_group_right.jsp?group_id=<%=group.getId()%>&group_name=<%=group.getName()%>">更改权限</a></td>
    <!-- td align="center"  width="16%" bgcolor="#D4E4EB" class="zhengwen"><a href="change_range_right.jsp?group_id=<%=group.getId()%>&group_name=<%=group.getName()%>">设定权限范围</a></td-->
    <td align="center"  width="14%" bgcolor="#D4E4EB" class="zhengwen"><a href="javascript:del('delete_group.jsp?group_id=<%=group.getId()%>')">删除</a></td>
  </tr>
<%
}
}

%>

<form>
</table>
<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="add_group.jsp">添加新的权限组</a> &nbsp;&nbsp; <a href="right_main.jsp">返回</a></div>
</body>
</html>
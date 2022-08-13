<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*,com.whaty.platform.entity.user.Manager" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>权限管理</title>
</head>
<body>
<script language=javascript>			
		function del(url)
		{
		if(confirm("你确实要删除吗?"))
		{
			window.location=url;
		}
		
		}
</script>


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


String group_id=fixnull(request.getParameter("group_id"));
String group_name=fixnull(request.getParameter("group_name"));

%>
<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">权限组:<%=group_name%></td>
	
</tr>
<%
List managerList=new ArrayList();

 managerList=rightManage.getRightGroupMember(group_id);

if(managerList!=null&&managerList.size()>0){

    for(int i=0;i<managerList.size();i++){
    
    Manager manager=(Manager)managerList.get(i);

%>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%"><%=manager.getName()%></td>
	<td bgcolor="#D4E4EB" class="zhengwen"><a href="javascript:del('delete_group_member.jsp?id=<%=manager.getId()%>&group_id=<%=group_id%>&group_name=<%=group_name%>')">取消</a></td>
</tr>
<%}
}else{
%>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">该权限组暂时没有设定管理员!</td>
	
</tr>
<%}%>

</table>
<br>

<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="right_group.jsp">返回</a></div>
</body>
</html>
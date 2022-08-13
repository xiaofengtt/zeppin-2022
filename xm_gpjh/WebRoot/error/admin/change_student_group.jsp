<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.net.*"%>
<//%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<//%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*,com.whaty.platform.entity.user.Manager" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeManager"/>
<jsp:directive.page import="com.whaty.platform.sso.bean.PePriRole"/>
<jsp:directive.page import="com.whaty.platform.User"/>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeStudent"/>
<%@ include file="./pub/priv.jsp"%>
<html>
<head>
<link href="<%=request.getContextPath() %>/admin/css.css" rel="stylesheet" type="text/css">
<title>权限管理</title>
</head>
<body>
<script language=javascript>

function unselectall(index)
{
	temp = "document.inputgroupright.modelgroup" + index;
	modelgroup = eval(temp);
	temp = "document.inputgroupright.checkgroup" + index;
	checkgroup = eval(temp);

    if(modelgroup.checked)
    {
		modelgroup.checked = modelgroup.checked & 0;
    }
    else
    {
    	var checkall = 1;
		var i = 0;
	
	    while (i < checkgroup.length)
	    {
			if(!checkgroup[i].checked)
			{
				checkall = 0;
				break;
			}
			i++;
	    }
	    
	    if(checkall != 0)
	    	modelgroup.checked = modelgroup.checked | 1;
    }
}
function selectall(index)
{
	temp = "document.inputgroupright.modelgroup" + index;
	modelgroup = eval(temp);
	temp = "document.inputgroupright.checkgroup" + index;
	checkgroup = eval(temp);

	if(checkgroup == null)
	{
		return;
	}

	if(1 < checkgroup.length)
	{
		var i = 0;
	    while (i < checkgroup.length)
	    {
			checkgroup[i].checked = modelgroup.checked;
			i++;
	    }
    }
    else
    	checkgroup.checked = modelgroup.checked;
}
function disright(aa)
{
	
}

</script>

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

PeStudent manager = (PeStudent)session.getAttribute("manager");





%>

<form name="inputgroupright" method="post" action="/sso/admin/teacherStudentManagerOper_updateAdminGroup.action">

<input type="hidden" name="managerId" value="<%=manager.getId()%>">
<input type="hidden" name="loginId" value="<%=manager.getSsoUser().getLoginId()%>">
<input type="hidden" name="type" value="stu">


<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">为管理员：<%=manager.getName()%> 设置权限组 </td>
	
</tr>

<%
List groupRight= (List)session.getAttribute("list");
if(groupRight!=null){
     for(int i=0;i<groupRight.size();i++){
     
      PePriRole group=(PePriRole) groupRight.get(i); 
    
   


%>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%"><%=group.getName()%></td>
	
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="radio" name="admin_id" value="<%=group.getId()%>"   <%if(manager.getSsoUser().getPePriRole()!= null && group.getId().equals(manager.getSsoUser().getPePriRole().getId())) {%>checked <%}%>></td>
</tr>

<%
}
}
%>
</table>

<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="#" onclick="javascript:document.inputgroupright.submit();">确定</a> &nbsp;&nbsp;<a href="javascript:history.back();">返回</a></div>

<form>
</body>
</html>
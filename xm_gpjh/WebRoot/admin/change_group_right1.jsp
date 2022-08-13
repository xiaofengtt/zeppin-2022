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
	//office_id=aa;
	//window.location="change_group_right.jsp?office_id="+office_id+"&groupname=<%//=groupname%>&group_id=<%//=group_id%>";
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
PlatformFactory platformfactory = PlatformFactory.getInstance();
BasicRightManage rightManage = platformfactory.creatBasicRightManage();


String group_id=fixnull(request.getParameter("group_id"));
String group_name=fixnull(request.getParameter("group_name"));

%>

<form name="inputgroupright" method="post" action="change_group_right_exe.jsp">

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">设置权限组: <%=group_name%> </td>
	
</tr>

<%
List officeList=rightManage.getOffices();
if(officeList!=null){
  for(int i=0;i<officeList.size();i++){
      Office office= (Office) officeList.get(i);
   


%>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%"><%=office.getName()%></td>
	
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="radio" name="roffice" value="<%=office.getId()%>"  onclick="disright('<%=office.getId()%>')" <%//if(office_id.equals(officeid)) {%>checked <%//}%>></td>
</tr>

<%
}
}
%>
</table>
<br>

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan="3" align="center">设置在该部门的权限 </td>
	
</tr>


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%//=manager.getName()%></td>
	<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><input type="radio" name="roffice" value="<%//=office_id%>"  onclick="disright('<%//=office_id%>')" <%//if(office_id.equals(officeid)) {%>checked <%//}%>></td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="radio" name="roffice" value="<%//=office_id%>"  onclick="disright('<%//=office_id%>')" <%//if(office_id.equals(officeid)) {%>checked <%//}%>></td>
</tr>


</table>
<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="right_group.jsp">返回</a></div>

<form>
</body>
</html>
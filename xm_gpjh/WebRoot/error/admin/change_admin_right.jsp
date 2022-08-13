<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.net.*"%>
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

function unselectAll(index)
{
	var right = document.getElementsByName("right_id_"+index);
	for(i=0;i<right.length;i++)
	{
		right[i].checked = true;
	}
}
function selectAll(index)
{
	var right = document.getElementsByName("right_id_"+index);
	for(i=0;i<right.length;i++)
	{
		right[i].checked = false;
	}
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


String id=URLDecoder.decode(fixnull(request.getParameter("id")),"UTF-8");
String name=URLDecoder.decode(fixnull(request.getParameter("name")),"UTF-8");
String group_id=URLDecoder.decode(fixnull(request.getParameter("group_id")),"UTF-8");



%>

<form name="inputgroupright" method="post" action="change_admin_rightexe.jsp">

<input type="hidden" name="group_id" value="<%=group_id%>">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="name" value="<%=name%>">


<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" colspan="3" align="center">为管理员：<%=name%> 设置权限 </td>
	
</tr>

<%
List modelgroup=rightManage.getModelGroups(id);
if(modelgroup!=null){
     for(int i=0;i<modelgroup.size();i++){
      RightModel model=(RightModel) modelgroup.get(i); 
%>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="25%"><%=model.getName()%></td>
	<td bgcolor="#D4E4EB" class="zhengwen" width="50%">
		<table>
	<%
		String model_id = model.getId();
		List rights=rightManage.getModelRights2(id,model_id);
		for(Iterator it = rights.iterator();it.hasNext();)
		{
			ModuleRight right = (ModuleRight)it.next();
		%>
			<tr>
				<td bgcolor="#D4E4EB" class="zhengwen">
					<input type="checkbox" name="right_id_<%=model_id%>" value="<%=right.getId()%>" <%=right.getStatus().equals("1")?"checked":""%>>
					<%=right.getName()%>
				</td>
			</tr>
		<%
		}
	%>
		</table>
	</td>
	<td bgcolor="#D4E4EB" class="zhengwen" width="25%">
		<input type="checkbox" name="model_id" value="<%=model_id%>" onclick="if(!this.checked) selectAll('<%=model_id%>');else unselectAll('<%=model_id%>');">
		<input type="hidden" name="model_ids" value="<%=model_id%>">
	</td>
</tr>

<%
}
}
%>
</table>

<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="#" onclick="javascript:document.inputgroupright.submit();">确定</a> &nbsp;&nbsp;<a href="right_admin.jsp">返回</a></div>

<form>
</body>
</html>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
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
String group_name=fixnull(request.getParameter("change_name"));

int count=0;

count=rightManage.updateRightGroup(group_id,group_name);

if (count < 1)
	{
	%>
	<script language="javascript">
	alert("修改失败！");
	window.history.back();
	</script>
	<%
	}
	else
	{
	%>
	<script language="javascript">
	alert("修改成功!");
	window.navigate("change_group_name.jsp?group_id=<%=group_id%>&group_name=<%=group_name%>");
</script>

<%}%>
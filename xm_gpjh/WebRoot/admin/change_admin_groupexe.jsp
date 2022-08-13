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
String group_id=fixnull(request.getParameter("group_id"));

String admin_id=fixnull(request.getParameter("admin_id"));

String id=fixnull(request.getParameter("id"));

String name=fixnull(request.getParameter("name"));

//out.println(admin_id);
	int count=0;
	
 //int cc=admin_id.indexOf("*");
 //String group_id=admin_id.substring(0,cc);
 //String group_name=admin_id.substring(cc+1);
 
 //out.println(office_id);
// out.println(office_name);	
 count=rightManage.updateGroup(id,admin_id);

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
	//window.navigate("change_admin_group.jsp?group_id=<%=group_id%>&name=<%=name%>&id=<%=id%>");
	window.navigate("right_admin.jsp?group_id=<%=group_id%>&name=<%=name%>&id=<%=id%>");
</script>

<%}%>
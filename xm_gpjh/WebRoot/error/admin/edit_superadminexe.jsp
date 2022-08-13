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

String admin_id=fixnull(request.getParameter("admin_id"));
String admin_name=fixnull(request.getParameter("admin_name"));
String password=fixnull(request.getParameter("password"));
String mobile=fixnull(request.getParameter("mobile"));
String status=fixnull(request.getParameter("status"));

String type="SUPERADMIN";//总站管理员

int count=0;

//count=rightManage.addAdmin(admin_id,admin_name,password,status,type);
count=rightManage.updateAdmin(request);


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
	window.navigate("right_admin.jsp");
</script>

<%}%>
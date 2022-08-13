<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page
	import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*"%>
<%@ page import="com.whaty.platform.config.PlatformConfig,com.whaty.platform.info.*"%>
<%@ include file="./pub/priv.jsp"%>
<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals("null"))
			str = "";
		return str;
	}

%>

<%
PlatformFactory platformfactory = PlatformFactory.getInstance();
BasicRightManage rightManage = platformfactory.creatBasicRightManage();
InfoManage infoManage = platformfactory.creatInfoManage();
String admin_id=fixnull(request.getParameter("admin_id"));
String site_id=fixnull(request.getParameter("site_id"));

//分站管理员

int count=0;

count=rightManage.addSiteAdmin(request);

if (count < 1)
	{
	%>
<script language="javascript">
	alert("添加失败！");
	window.history.back();
	</script>
<%
	}
	else
	{
	infoManage.putInfomanagerPriv(admin_id, site_id);
	rightManage.putSiteAdminRight(admin_id);
	%>
<script language="javascript">
	alert("添加成功!");
	window.navigate("right_site_admin.jsp");
</script>

<%}%>
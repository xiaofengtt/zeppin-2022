<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page
	import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*"%>
<%@ page import="com.whaty.platform.config.PlatformConfig"%>
<%@ page import="com.whaty.platform.info.*"%>
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
	InfoManage infoManage = platformfactory.creatInfoManage();

//--------创建分站新闻类型
 	int count0 = infoManage.createNewsTypeForSiteNews();
 //	out.print(request.getParameter("site_id") + "<br>");
// 	out.print(count0);if(true) return;
//--------

	String id = fixnull(request.getParameter("id"));

	int count = 0;

	String admin_id = fixnull(request.getParameter("admin_id"));
	String site_id = fixnull(request.getParameter("site_id"));
	count = infoManage.putInfomanagerPriv(admin_id, site_id);

	if (count < 1) {
%>
<script language="javascript">
	alert("赋权失败！");
	window.history.back();
	</script>
<%
} else {
%>
<script language="javascript">
	alert("赋权成功!");
	window.navigate("right_site_admin.jsp");
</script>

<%
}
%>

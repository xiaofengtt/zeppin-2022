<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="../css/admincss.css" rel="stylesheet" type="text/css">
</head>
<%
	String id = request.getParameter("id");
	String dir_id = request.getParameter("dir_id");
	
	ResourceFactory fac = ResourceFactory.getInstance();
	BasicResourceManage manage = fac.creatBasicResourceManage();
	
	int i = manage.deleteResourceDir(id);
	if(i >0) {
%>
<script>
	alert("删除成功");
	window.location.href="content_frame.jsp?id=<%=dir_id%>";
</script>
<%
	} else {
%>
<script>
	alert("删除失败");
	window.history.back();
</script>
<%
	}
%>
<body leftmargin="0" topmargin="0" class="scllbar">
</body>
</html>

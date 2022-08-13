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
	String dir_id = request.getParameter("dir_id");
	String dir_name = request.getParameter("dir_name");
	String dir_note = request.getParameter("dir_note");
	String dir_status = request.getParameter("dir_status");
	String dir_isinherit = request.getParameter("dir_isinherit");
	
	ResourceFactory fac = ResourceFactory.getInstance();
	BasicResourceManage manage = fac.creatBasicResourceManage();
	
	int i = manage.addResourceDir(dir_name, dir_id, dir_note, dir_status, dir_isinherit);
	if(i >0) {
%>
<script>
	alert("添加成功");
	opener.history.go(0);
	window.close();
</script>
<%
	} else {
%>
<script>
	alert("添加失败");
	window.history.back();
</script>
<%
	}
%>
<body leftmargin="0" topmargin="0" class="scllbar">
</body>
</html>

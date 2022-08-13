<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标题文档</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">

</head>
<body leftmargin="0" topmargin="0" bgcolor="#F9F9F9">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F9F9F9">
  <tr>
    <td><iframe src="menu/user/preview_menu.jsp?id=<%=id%>" frameborder="0" width="100%" height="100%" scrolling="auto" allowtransparency="true"></iframe></td>
  </tr>
</table>
</body>
</html>
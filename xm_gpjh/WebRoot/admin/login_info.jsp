<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>

<%
	PlatformConfig platformConfig = (PlatformConfig)application.getAttribute("platformConfig");
	String admin_pwd = request.getParameter("admin_pwd");
	if(admin_pwd.equals(platformConfig.getManagepwd())) {
		session.setAttribute("admin", "1");
		response.sendRedirect("admin_index.jsp");
	} else {
%>
<script type="text/javascript">
<!--
	alert("密码错误，请重新输入!");
	window.history.back();
//-->
</script>
<%
	}
%>

<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>


<%
	String prefix =  getServletContext().getRealPath("/");
	String configdir=prefix+"WEB-INF"+File.separator+"config"+File.separator;
	//out.print(configdir);
	PlatformConfig platformConfig = new PlatformConfig(configdir);
	platformConfig.getConfig();
	
	String oldpwd = request.getParameter("oldpwd");
	String newpwd = request.getParameter("newpwd");
	if(platformConfig.getManagepwd().equals(oldpwd)) {
		platformConfig.setManagepwd(newpwd);
		platformConfig.setConfig();
%>
<script type="text/javascript">
<!--
	alert("密码修改成功");
	location.href = "manager_password_change.jsp";
//-->
</script>
<%
	} else {	
%>
<script type="text/javascript">
<!--
	alert("原密码不正确!");
	window.history.back();
//-->
</script>
<%
	}
%>
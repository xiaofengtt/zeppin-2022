
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TabMenu</title>
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--内容区-->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="6" bgcolor="278abe" style="background-color:#278abe;"></td>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<div id="0111" class="tab_menu2" onClick="top.openPage('/entity/function/sms/smssystempoint_list.jsp',this.id)" title="系统短信点管理">短信点管理</div>
				<div id="0110" class="tab_menu2" onClick="top.openPage('/entity/function/sms/new_sms.jsp',this.id)" title="发送短信">发送短信</div>
				<div id="0112" class="tab_menu2" onClick="top.openPage('/entity/function/sms/sms_check.jsp',this.id)" title="审核短信">审核短信</div>
				<div id="0113" class="tab_menu2" onClick="top.openPage('/entity/function/sms/sms_list.jsp',this.id)" title="短信列表">短信列表</div>
		 	<% UserSession userSession = (UserSession)session.getAttribute("user_session");
		 	   
		 	 if (userSession.getUserLoginType().equals("3")) { %>
				<div id="0115" class="tab_menu2" onClick="top.openPage('/entity/function/sms/new_sms.jsp',this.id)" title="发送OA短信">发送OA短信</div>
				<% } %>
				<div id="0114" class="tab_menu2" onClick="top.openPage('/entity/function/sms/sms_time.jsp',this.id)" title="短信统计">短信统计</div>
				<script>top.openPage('/entity/function/sms/sms_list.jsp','0113');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>


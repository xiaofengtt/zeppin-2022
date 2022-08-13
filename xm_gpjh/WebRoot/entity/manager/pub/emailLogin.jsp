<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/entity/manager/pub/UserSession.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<title>无标题文档</title>
</head>
<!-- JavaScript functions -->
<body>
<!--内容区-->
<div id="main_content">
    <div class="content_title">我的邮箱</div>
    <div class="cntent_k">
   	  <div class="k_cc">
		<form name="emailForm" action="http://219.238.253.7/cgi-bin/login" method="POST" target="_blank">
			<input type="hidden" name="UserID" value=<%=userSession.getSsoUser().getLoginId()%> />
			<input type="hidden" name="Domain_Name" value="cupde.cn">
			<input type="hidden" name="PassWord" value="<%=userSession.getSsoUser().getPassword()%>">
		</form>
	  	<p><a href="#" onclick="emailForm.submit();">进入我的邮箱</a></p>
	  	<p>&nbsp;</p>
   	  </div>
    </div>
</div>
<div class="clear"></div>

<!-- JavaScript onload -->

</body>
</html>

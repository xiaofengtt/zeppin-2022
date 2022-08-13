<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新的结业申请</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}

a:link { text-decoration: none;color: #000000}
a:active { text-decoration:blink}
a:hover { text-decoration:underline;color: red} 
a:visited { text-decoration: none;color: #000000}

-->
</style></head>

<body bgcolor="#E0E0E0">
<s:if test="ForbidApply">
<script type="text/javascript">
alert('<s:property value="msg" escape="false"/>');
window.close();
</script>
</s:if>
<table width="300px" border="0" cellpadding="0" cellspacing="0" align="center" class="twocencetop" style="word-break:break-all;margin-top:50px;">
	<tr>
		<td align="center" style="color:red;font-size:16px;height:30px;">
			申请结业
		</td>
	</tr>
	<tr style="line-height: 30px;" align="center">
		<form action="/entity/study/peEndCourseApplyAction_saveNewApply.action" method="post">
		<td>
			申请原因：<input name="bean.applyInfo" type="text"/><br>
			申请备注：<input name="bean.applyNote" type="text"/><br>
			<input type="submit" value="提交" style="margin-left:30px;"/>
			<input type="reset" value="重新填写" style="margin-left:60px;"/>
		</td>
		</form>
	</tr>
</table>
</body>
</html>
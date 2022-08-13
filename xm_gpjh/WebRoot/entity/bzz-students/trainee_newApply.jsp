<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>学员学习申请</title>
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
alert("<s:property value='msg'/>");
window.close();
</script>
</s:if>
<table width="300px" border="0" cellpadding="0" cellspacing="0" align="center" class="twocencetop" style="word-break:break-all;margin-top:40px;">
	<tr>
		<td align="center" style="color:red;font-size:16px;font-weight:bold;height:30px;">
			<s:property value="applyTypeStr"/>
		</td>
	</tr>
	<tr>
		<td height="20px"></td>
	</tr>
	<tr style="line-height: 30px;" align="center">
		<form action="/entity/study/peApply_saveNewApply.action" method="post">
			<input name="applyType" type="hidden" value="<s:property value='applyType'/>">
		<td>
			申请原因：<input name="bean.applyInfo" type="text" maxlength='400'/><br>
			申请备注：<input name="bean.applyNote" type="text"maxlength='400'/><br>
			<input type="submit" value="提 交" style="margin-left:30px;" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"/>
			<input type="reset" value="重 置" style="margin-left:60px;" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"/>
		</td>
		</form>
	</tr>
</table>
</body>
</html>
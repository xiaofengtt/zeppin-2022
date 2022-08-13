<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>开除学生</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
	</head>
	<body>
		<form name="print" method="post" action="/entity/studentStatus/peStudentStatus_turntoExpelChange.action">
			<div id="main_content">
			   <div class="content_title">开除学生</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">请输入要开除学生的学号</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td width="40%">
			   				<div align="right" class="postFormBox" ><span class="name"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="bean.peStudent.regNo" value="<s:property value='bean.peStudent.regNo'/>" size="21"/>
			   				<font color="red"><s:property value="msg"/></font></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" name="Submit" value="提交" /></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>
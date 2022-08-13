<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学期开课</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
	</head>
	<body>
		<form name="print" id= "print" method="get" action="/entity/teaching/prTchOpencourse_semesterOpenCourse.action">
			<div id="main_content">
			   <div class="content_title">学期开课</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">

			   				<div align="left" class="postFormBox">启动所有课程。如有关闭课程，请删除。</div>
			   				<input type="submit" value="确认"/>

		</form>
	</body>
</html>
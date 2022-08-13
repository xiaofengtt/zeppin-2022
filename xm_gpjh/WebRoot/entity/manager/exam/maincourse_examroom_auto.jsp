<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>选择条件</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		

	</head>
	<body>
		<form name="final" method="get" action="/entity/exam/peMainCourseExamRoom_autoMainExamRoom.action">
			<div id="main_content">
			   <div class="content_title">自动分配考场</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td>
			   				<div align="center" class="postFormBox">自动分配考场</div>
			   			</td>
			   		</tr>			   					   		
			   		<tr>
			   			<td width="18%">
			   				<div align="left" >自动分配考场将会覆盖之前已分配的结果，例如：考场号，座位号等，确认分配吗？<input type="submit" value="提交" ></div>
		   			  </td>
			   			
			   		</tr>
				   	
			   		   				
	   				
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>
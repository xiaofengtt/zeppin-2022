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
		<form name="final" method="get" action="/entity/exam/scoreManualInput.action" onsubmit="javascript:return pageGuarding();">
			<div id="main_content">
			   <div class="content_title">为学生批量预约考试</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">为学生批量预约考试</div>
			   			</td>
			   		</tr>			   					   		
			   		<tr>
			   			<td width="18%">
			   				<div align="left" class="postFormBox"><span class="name">年级：<br /></span></div>
		   			  </td>
			   			<td width="82%">
			   				<div align="left" class="postFormBox"><select></select></div>
		   			  </td>
			   		</tr>
				   	<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">专业：<br /></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><select></select></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">学习中心：<br /></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><select></select></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">层次：<br /></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><select></select></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">课程：<br /></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><select ></select></div>
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
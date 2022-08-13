<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		<script>
		</script>
	</head>
	<body>
		<form name="feereturn" method="post" action="">
			<div id="main_content">
			   <div class="content_title">退费操作</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">		   		
			   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>退费类型</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   				<select name="graduationStatus">
			   					<option value="">请选择退费类型...</option>
			   					<option value="毕业">毕业</option>
        						<option value="退学">退学</option>
			   				</select>
			   				</div>
			   			</td>
			   		</tr>

			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>原因</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><textarea name="body" cols=15 rows=2"></textarea> 
			   				</div>
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
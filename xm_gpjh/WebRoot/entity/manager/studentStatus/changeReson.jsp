<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  		<title>学籍变动原因</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
    


  </head>
  
  <body>
			<div id="main_content">
			   <div class="content_title">学籍变动原因</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td >
			   				<div align="center" class="postFormBox"><s:property value='bean.peStudent.trueName'/> 学籍变动原因</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td >
			   				<div align="center" class="postFormBox"><s:property value='bean.note' escape="false"/></div>
			   			</td>
			   		</tr>
			   	</table>
			   	</div>
			   	</div>
			   	</div>				   		
  </body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>自动分配考场</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
<script>
	 function sub()
  {
   info.innerHTML='正在自动分配考场，请等待...';
   return true;
  }
</script>		

	</head>
	<body>
		<form name="final" method="get" action="/entity/exam/examInfoSearch_autoExamRoom.action" onsubmit ="return sub();">
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
			   				<div align="left" id="info">自动分配考场将会覆盖之前已分配的结果，例如：考场号，座位号等，确认分配吗？<input type="submit" value="提交" id="submit1" ></div>
		   			  </td>
			   			
			   		</tr>
				   	
			   		   				
	   				
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>
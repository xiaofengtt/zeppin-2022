<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		<script language="javascript">
		function showcourses() {
			//document.getElementById("courses").innerHTML="123";
		}
		
		</script>
	</head>
	<body>
		<form name="print" id= "print" method="get" action="/entity/recruit/recruitManageExam.action">
			<div id="main_content">
			   <div class="content_title">设置录取条件</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
							<div align="center" class="postFormBox">请设置专业单科最低分及总分</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>学习中心</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" id="showstudeycenter">
			   				<select id="site" onchange="">
			   					<option>---</option>
			   					<option>学习中心1</option>
			   					<option>学习中心2</option>
			   					<option>学习中心3</option>
			   				</select>
			   				</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>层次</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   				<select id="type" onchange="">
			   				<option>---</option>
			   				<option>高起专</option>
			   				<option>专升本</option>
			   				</select>
							</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>专业</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   				<select id="major" onchange="showcourses()">
							<option>---</option>
			   				<option>工商管理</option>
			   				<option>计算机科学与技术</option>
			   				</select>
			   				</div>
			   			</td>
			   		</tr>
			   		<div id="courses">
			   		<tr>
			   			<td>
			   			<div align="left" class="postFormBox"><span class="name"><label>数学</label> </span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="highMath"/> </div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>语文</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="collegeChinese"/> </div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>英语</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="eduphych"/> </div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>总分</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="total"/> </div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" value="提交"/></div>
			   			</td>
			   		</tr>
			   		</div>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>
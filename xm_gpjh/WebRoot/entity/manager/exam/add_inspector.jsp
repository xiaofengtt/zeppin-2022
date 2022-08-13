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
		<script Language="JavaScript">
			function pageGuarding() {
				if(document.final.courseName.value == "") {
					alert("请先选择学期再选择课程");
					document.final.courseName.focus();
					return false;
				}
				return true;
			}
		</script>		

	</head>
	<body>
		<form name="final" method="get" action="" onsubmit="javascript:return pageGuarding();">
			<div id="main_content">
			   <div class="content_title">添加巡考人员</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">添加巡考人员个人信息</div>
			   			</td>
			   		</tr>			   					   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">姓名</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="semester" size="24"/></div>
			   			</td>
			   		</tr>
				   	<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">上岗证号码</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">身份证</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">出生日期</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">性别</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">原籍</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">籍贯</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">工作单位</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">职业</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">相片</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="file" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">广州手机</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">外出手机</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">办公电话</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name">家庭电话</span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="courseName" size="24"/></div>
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
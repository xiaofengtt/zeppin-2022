<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学生照片信息查看比较</title>
		<% String path = request.getContextPath();%>
		<link href="<%=path%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>

	</head>
	<body>
		<div id="main_content" >
			   <div class="content_title">学生入学照片和毕业照片校对</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   			   <table width="80%" class="postFormBox" >
<s:iterator value="studentlist">

			   		<tr class="postFormBox" width="100%" >
			   			<td align="right"   nowrap="nowrap"><span class="name"><label>学号:</label></span>
			   			</td>
			   			<td align="left" nowrap="nowrap"><s:property value="regNo"/></td>
			   			<td align="right"   nowrap="nowrap"><span class="name"><label>姓名:</label></span>
			   			</td>
			   			<td align="left" nowrap="nowrap"><s:property value="trueName"/></td>
			   			<td align="right" nowrap="nowrap"><span class="name"><label>学习中心:</label></span>
			   			</td>
			   			<td align="left" nowrap="nowrap"><s:property value="peSite.name"/>
			   			</td>
			   			<td align="right"   nowrap="nowrap"><span class="name"><label>层次:</label></span>
			   			</td>
			   			<td align="left" nowrap="nowrap"><s:property value="peEdutype.name"/>
			   			</td>
			   			<td align="right"   nowrap="nowrap"><span class="name"><label>专业:</label></span>
			   			</td>
			   			<td align="left" nowrap="nowrap"><s:property value="peMajor.name"/></td>
			   			<td align="right"   nowrap="nowrap"><span class="name"><label>年级:</label></span>
			   			</td>
			   			<td align="left" nowrap="nowrap"><s:property value="peGrade.name"/></td>			   			
			   		</tr>
			   		<tr class="postFormBox">			   		
			   			<td align="right" nowrap="nowrap">入学照片:
			   			<td colspan="5" height="100px">
			   				<img src="<s:property value='peRecStudent.photoLink'/>" width="66px" height="96px"/>
			   			</td>
			   			<td align="right" nowrap="nowrap">毕业照片:
			   			<td colspan="5">
			   				<img src="<s:property value='prStudentInfo.photoLink'/>" width="66px" height="96px"/>
			   			</td>
			   		</tr>

</s:iterator> 	
			   </table>	
			   <div align="center"><input type="button" name="close" value="关闭" onclick="javascript:window.close();;"/></div>
			   </div>
		   </div>
		   </div>
	</body>
</html>
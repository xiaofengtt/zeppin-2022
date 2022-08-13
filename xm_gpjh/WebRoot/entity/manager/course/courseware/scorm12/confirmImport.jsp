<%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="com.whaty.platform.config.*"%>
<%
	
	String course_id=request.getParameter("course_id");
	

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>后台管理</title>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../../../js/frame.js"></script>
</head>
<style type="text/css">
<!--
.w1 {
	font-family: "宋体";
	font-size: 12px;
	line-height: 24px;
	color: #333333;
	text-decoration: none;
}
.w2 {
	font-family: "宋体";
	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #265A8E;
	text-decoration: none;
}
-->
</style>
<body leftmargin="0" rightmargin="0" style="background-color:transparent;" class="scllbar">
	<!-- start:图标区域 -->
	<!-- end:图标区域 -->
	<form name='user-form' action='../../coursewareImportExe.jsp' method='post' class='nomargin' onsubmit="">
	<input type="hidden" name="course_id" value="<%=course_id%>">
	<input type="hidden" name="type" value="<%=CoursewareType.SCORM12 %>">
	<!-- start:内容区域1 -->
	<div unselectable="on" class="border">   
		<table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
		<tr bgcolor="#FFFFFF">
			<td align='center' bgColor=#E6EFF9 class="w2" colspan="1">导入SCORM1.2版课件</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' bgColor=#ffffff class="w1"><span>课件顺利入导成功！请点击确认按钮完成操作！</span></td>
		</tr>
		<tr bgcolor="#FFFFFF">
		<td align="center" class="t12_14_bgE3EAE9">
			<!-- <input name="ok" value="确 认" type="submit" class="dialogbutton" >  -->
			<input name="ok" value="关闭" type="button" class="dialogbutton" onclick="javascript:window.close()">
		</td>
		</tr>
		</table>
	</div>
	
</form>

	<!-- 内容区域结束 -->
</body>
</html>


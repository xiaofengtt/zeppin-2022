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
<title>��̨����</title>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../../../js/frame.js"></script>
</head>
<style type="text/css">
<!--
.w1 {
	font-family: "����";
	font-size: 12px;
	line-height: 24px;
	color: #333333;
	text-decoration: none;
}
.w2 {
	font-family: "����";
	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #265A8E;
	text-decoration: none;
}
-->
</style>
<body leftmargin="0" rightmargin="0" style="background-color:transparent;" class="scllbar">
	<!-- start:ͼ������ -->
	<!-- end:ͼ������ -->
	<form name='user-form' action='../../coursewareImportExe.jsp' method='post' class='nomargin' onsubmit="">
	<input type="hidden" name="course_id" value="<%=course_id%>">
	<input type="hidden" name="type" value="<%=CoursewareType.SCORM12 %>">
	<!-- start:��������1 -->
	<div unselectable="on" class="border">   
		<table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
		<tr bgcolor="#FFFFFF">
			<td align='center' bgColor=#E6EFF9 class="w2" colspan="1">����SCORM1.2��μ�</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' bgColor=#ffffff class="w1"><span>�μ�˳���뵼�ɹ�������ȷ�ϰ�ť��ɲ�����</span></td>
		</tr>
		<tr bgcolor="#FFFFFF">
		<td align="center" class="t12_14_bgE3EAE9">
			<!-- <input name="ok" value="ȷ ��" type="submit" class="dialogbutton" >  -->
			<input name="ok" value="�ر�" type="button" class="dialogbutton" onclick="javascript:window.close()">
		</td>
		</tr>
		</table>
	</div>
	
</form>

	<!-- ����������� -->
</body>
</html>


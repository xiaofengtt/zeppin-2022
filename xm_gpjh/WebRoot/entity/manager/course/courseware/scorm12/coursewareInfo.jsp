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
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.training.basic.courseware.*"%>
<%

	String courseware_id = request.getParameter("courseware_id");
	TrainingFactory factory=TrainingFactory.getInstance();
	TrainingManage trainingManage=factory.creatTrainingManage();
	Scorm12TrainingCourseware  scormCourseware=trainingManage.getScorm12Courseware(courseware_id);
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>导入scorm课件</title>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../../../js/frame.js"></script>
<script language="Javascript" >
	function delCourseware(id)
	{
		if(!confirm("是否确认删除课件!"))
			return;
		window.location.href = "../../coursewareDelete.jsp?course_Id="+id;
	}
</script>
</head>
<%!
	String fixnull(String str) {
		if(str == null || str.equals("") || str.equals("null"))
			return ("");
		else
			return str;
	}
%>
<body leftmargin="0" rightmargin="0" style="background-color:transparent;" class="scllbar">
<!-- start:图标区域 -->
	<!-- end:图标区域 -->

	<!-- start:内容区域1 -->
	<div unselectable="on" class="border">   
		
		<table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
		<tr bgcolor="#FFFFFF">
			<td align='center' class="t12_14_bgE3EAE9" colspan="2" ><span><%=fixnull(scormCourseware.getCourse().getCourseTitle()) %>&nbsp;SCORM课件信息</span></td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件ID：</span></td>
        <td class="xmlTd"><%=scormCourseware.getCourse().getCourseId() %></td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件名称：</span></td>
        <td class="xmlTd"><%=fixnull(scormCourseware.getCourse().getCourseTitle()) %></td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件描述：</span></td>
        <td class="xmlTd"><%=fixnull(scormCourseware.getCourse().getDescription()) %></td>
		</tr>
		
		
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>版本：</span></td>
        <td class="xmlTd"><%=fixnull(scormCourseware.getCourse().getVersion()) %></td>
		</tr>	
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件类型：</span></td>
        <td class="xmlTd">SCORM1.2</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>查看课件：</span></td>
        <td class="xmlTd"> 
        <a href="../../../../student/course/courseware/scorm12/LMSMain.jsp?navigate=<%=scormCourseware.getNavigate() %>&course_id=<%=scormCourseware.getId() %>&user_id=1" target=_blank>【查看】</a></td>
			
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>删除课件：</span></td>
        <td class="xmlTd"> 
        <a href="javascript:delCourseware('<%=scormCourseware.getId() %>')">【删除】</a></td>
		</tr>	
		
		</table>
	</div>
</body>
</html>


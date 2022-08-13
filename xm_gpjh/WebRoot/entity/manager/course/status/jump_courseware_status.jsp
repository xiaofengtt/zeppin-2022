<%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import = "com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.training.basic.*"%>
<%
	String courseId=request.getParameter("courseId");
	String studentId = request.getParameter("studentId");
	
	TrainingFactory factory=TrainingFactory.getInstance();
	TrainingManage stuManage=factory.creatTrainingManage();
	TrainingCourseware courseware=stuManage.getCourseware(courseId);
	if(courseware != null)
	{
	String coursewareType=courseware.getCoursewareType();
	if(coursewareType.equals(TrainingCoursewareType.AICC))
	{
		response.sendRedirect(request.getContextPath()+"/training/manager/course/status/aicc_status.jsp?coursewareId="+courseware.getId()+"&studentId="+studentId);
	}
	else if(coursewareType.equals(TrainingCoursewareType.WHATYBASIC))
	{
		response.sendRedirect(request.getContextPath()+"/training/manager/course/status/myCourseStatus.jsp?coursewareId="+courseware.getId()+"&studentId="+studentId);
	}
	else if(coursewareType.equals(TrainingCoursewareType.TIME))
	{
		response.sendRedirect(request.getContextPath()+"/training/manager/course/status/time_status.jsp?coursewareId="+courseware.getId()+"&studentId="+studentId+"&courseId="+courseId);
	}
	else if(coursewareType.equals(TrainingCoursewareType.SCORM12))
	{
		response.sendRedirect(request.getContextPath()+"/training/manager/course/status/scorm12_status.jsp?coursewareId="+courseware.getId()+"&studentId="+studentId);
	}
	}
	else
	{
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>课件不存在</title>
</head>

<body>
<table width="100%" height="100%" border="0">
<tr><td width="100%" height="100%" valign="middle" align="center">
<table width="200" border="0">
  <tr>
    <td width="100%" align="center">该课程还没有课件!</td>
  </tr>
  <tr>
    <td width="100%" align="center"><a href="javascript:history.back(-1)" >返回</a></td>
  </tr>
</table>
</td></tr>
</table>
</body>
</html>

<%
	}
%>
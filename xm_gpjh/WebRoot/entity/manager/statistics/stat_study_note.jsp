
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
<%@page import = "com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@ include file="./pub/priv.jsp"%>
<%
	String courseId=request.getParameter("c_course_id");
	String user_id=request.getParameter("id");
	String sso_id=request.getParameter("sso_id");
	UserSession usersession=(UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	TrainingStudentPriv includePriv=new OracleTrainingStudentPriv();
	includePriv.setStudentId(sso_id);
	TrainingFactory factory=TrainingFactory.getInstance();
	TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage(includePriv);
	TrainingCourseware courseware=stuManage.getCourseware(courseId);
	if(courseware != null)
	{
	String coursewareType=courseware.getCoursewareType();
	if(coursewareType.equals(TrainingCoursewareType.AICC))
	{
		response.sendRedirect("./myCourseStatus.jsp?coursewareId="+courseware.getId());
	}
	else if(coursewareType.equals(TrainingCoursewareType.WHATYBASIC))
	{
		response.sendRedirect("./myCourseStatus.jsp?coursewareId="+courseware.getId());
	}
	
	else if(coursewareType.equals(TrainingCoursewareType.SCORM12))
	{
		response.sendRedirect("./myCourseStatus.jsp?coursewareId="+courseware.getId()+"&courseId="+courseId+"&user_id="+sso_id);
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
    <td width="100%" align="center">【<span><a href="#" onClick="javascript:window.close()" style="color:#FE7E01; text-decoration:none;">关闭</a></span>】</td>
  </tr>
</table>
</td></tr>
</table>
</body>
</html>

<%
	}
%>
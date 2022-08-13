
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
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import = "com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
	
	String courseId=request.getParameter("courseId");
	
	UserSession usersession=(UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	 if(usersession == null){
	 	%>
<script type="text/javascript">
	alert("请您登录后再查看详细学习记录！");
	//window.location="<%=basePath1%>";
	window.close();
</script>
<%
	return;
	 }
	 
	TrainingStudentPriv includePriv=new OracleTrainingStudentPriv();
	includePriv.setStudentId(usersession.getId());
	TrainingFactory factory=TrainingFactory.getInstance();
	TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage(includePriv);
	TrainingCourseware courseware=stuManage.getCourseware(courseId);
	
	String t_sql="";
	dbpool db1 = new dbpool();
	t_sql="select c.id from pe_bzz_tch_course t,pe_bzz_tch_courseware c,scorm_course_info sc where t.id = c.fk_course_id  and t.id='"
					+ courseId + "' and sc.id=c.code ";
					
	if(courseware != null && db1.countselect(t_sql)>0)
	{
	String coursewareType=courseware.getCoursewareType();
	if(coursewareType.equals(TrainingCoursewareType.AICC))
	{
		response.sendRedirect("./courseware/aicc/myCourseStatus.jsp?coursewareId="+courseware.getId());
	}
	else if(coursewareType.equals(TrainingCoursewareType.WHATYBASIC))
	{
		response.sendRedirect("./courseware/whatybasic/myCourseStatus.jsp?coursewareId="+courseware.getId());
	}
	
	else if(coursewareType.equals(TrainingCoursewareType.SCORM12))
	{
		response.sendRedirect("./courseware/scorm12/myCourseStatus.jsp?coursewareId="+courseware.getId()+"&courseId="+courseId);
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
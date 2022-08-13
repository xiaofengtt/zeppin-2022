
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/><%		/*---------------------------------------------
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
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.training.basic.*,
				  com.whaty.platform.standard.scorm.operation.*,com.whaty.platform.database.oracle.standard.standard.scorm.operation.*"%>
<%@page import = "com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
	String coursewareId=request.getParameter("coursewareId");   //课件id;
	//String coursewareId=request.getParameter("coursewareId"); //课件code;
	String opencourseId=request.getParameter("opencourseId"); //开课Id
	 UserSession usersession=(UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	 if(usersession == null){
	 	%>
<script type="text/javascript">
	alert("请您登录后再学习课件！");
	//window.location="<%=basePath1%>";
	window.close();
</script>
<%
	return;
	 }
	
	Date now  = new Date();
	request.getSession().setAttribute("now", now);
	
	 TrainingStudentPriv includePriv=new OracleTrainingStudentPriv();
	includePriv.setStudentId(usersession.getId());
	session.setAttribute("traininguser_priv",includePriv);
	TrainingFactory factory=TrainingFactory.getInstance();
	TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage(includePriv);
	TrainingCourseware courseware=stuManage.getCourseware(coursewareId);//stuManage.getScorm12Courseware(courseId);
	//System.out.print("courseware:"+courseware);
	String t_sql="";
	dbpool db1 = new dbpool();
	t_sql="select cw.id from pe_tch_courseware cw,SCORM_COURSE_INFO s where cw.code = s.course_code and cw.id ='"
					+ coursewareId + "' ";
	
	if(courseware != null && db1.countselect(t_sql)>0)
	{
		session.setAttribute("COURSEID",courseware.getId()); //scorm课件id
		session.setAttribute("openId",coursewareId);  //开课id 在本平台修改为课件的id
		String coursewareType=courseware.getCoursewareType();
		 if(coursewareType.equals(TrainingCoursewareType.SCORM12))
		{
			String coursewareURL="";
			String coursewareURL1 = "";
			//System.out.println("courseware.getNavigate():"+courseware.getNavigate());
			coursewareURL="./courseware/scorm12/LMSMain.jsp?navigate="+courseware.getNavigate()+"&course_id="+courseware.getId()+"&user_id="+usersession.getId();
			coursewareURL1="./courseware/scorm12/LMSMain.jsp?navigate="+courseware.getNavigate()+"&course_id="+courseware.getId()+"&user_id="+usersession.getId()+"&start=begin";
			//out.print("coursewareURL="+coursewareURL);
			
			ScormStudentPriv userPriv = new OracleScormStudentPriv();
			userPriv.setStudentId(usersession.getId());
			ScormStudentManage man = ScormFactory.getInstance().creatScormStudentManage(userPriv);
		 
			man.addAttemptNum(courseware.getId()); 
			dbpool db = new dbpool();
			//平台导航，且有学习记录的
			String sql = " select tcs.id from TRAINING_COURSE_STUDENT tcs,SCORM_COURSE_INFO sc where tcs.course_id = sc.id and sc.id = '"+courseware.getId()+"' and sc.navigate<>'courseware_nav' and tcs.student_id = '"+usersession.getId()+"' and tcs.percent > 0 ";
			//课程导航，且有学习记录的
			String sql1 = "select tcs.id from TRAINING_COURSE_STUDENT tcs,SCORM_COURSE_INFO sc where tcs.course_id = sc.id and sc.id = '"+courseware.getId()+"' and sc.navigate = 'courseware_nav' and tcs.student_id = '"+usersession.getId()+"' and tcs.percent > 0 ";
			if(db.countselect(sql)>0)
			{
%>
<script type="text/javascript">
	if(confirm("是否接着上次的学习进度继续学习，点击“确定”将继续上次进度，点击“取消”将重头开始学习？"))
	{
		window.location="<%=coursewareURL%>";
	}
	else
	{
		window.location="<%=coursewareURL1%>";
	}
</script>
<%
			}
			else if(db.countselect(sql1)>0)
			{
				response.sendRedirect(coursewareURL1);
			}
			else
			{
				response.sendRedirect(coursewareURL);
			}
			//response.sendRedirect(coursewareURL);
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
<table width="100%" height="100%" border="1">
<tr  height="100%"><td width="100%" valign="middle" align="center">
<table width="200" border="0">
  <tr>
    <td width="100%" align="center">该课程还没有课件!</td>
  </tr>
  <tr>
    <td width="100%" align="center"><a href="javascript:window.close()" >关闭</a></td>
  </tr>
</table>
</td></tr>
</table>
</body>
</html>	
<% } %>


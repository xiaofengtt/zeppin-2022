<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.courseware.config.*,
				  com.whaty.platform.entity.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.standard.sso.OracleSsoUser"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.standard.sso.OracleSsoUserPriv"/>
<jsp:directive.page import="com.whaty.platform.sso.SsoUserPriv"/>
<%
	/* 教师进入交流园地
		传入参数：
			open_course_id , 开课id 
			courseware_id , 课件id
	*/


	
	String courseware_id = request.getParameter("courseware_id");
	if(courseware_id == null) courseware_id = "";
	String teachclass_id = request.getParameter("teachclass_id");
	String open_course_id = request.getParameter("open_course_id");	
	
	
		
	Object o2 = session.getAttribute("teacher_eduplatform_priv");
	TeacherPriv session_teacherPriv;
	if(o2 != null)
		session_teacherPriv = (TeacherPriv)o2;
	else
		session_teacherPriv = (TeacherPriv)(session.getAttribute("eduplatform_priv"));
	

	PlatformFactory platformFactory = PlatformFactory.getInstance();
	TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
	InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(), teachclass_id);
	session.setAttribute("interactionUserPriv",interactionUserPriv);
	OpenCourse openCourse = teacherOperationManage.getOpenCourse(open_course_id);
	session.setAttribute("openCourse",openCourse);
	session.setAttribute("userType", "teacher");
	session.setAttribute("courseware_priv",teacherOperationManage.getCoursewareManagerPriv());
	response.sendRedirect("../function/teacher/index.jsp?teachclass_id=" + teachclass_id + "&courseware_id=" + courseware_id);
%>


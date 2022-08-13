<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import = "java.util.*,com.whaty.platform.standard.time.operation.*"%>
<%@ include file="../../../pub/priv.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String time = request.getParameter("learn_time");
System.out.println(time);
String course_id = request.getParameter("course_id");
String student_id = request.getParameter("student_id");
if(time == null || time.equals(""))
	return;
if(course_id == null || course_id.equals(""))
	return;
if(student_id == null || student_id.equals(""))
	return;	
 
TimeCourseStudentManager man = TimeCourseFactory.getInstance().createTimeCourseStudentManager();
man.addStudentLearnTimeHldb(student_id,course_id,time);
	 	
%>
 

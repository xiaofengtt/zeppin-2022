<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.courseware.config.CoursewareConfig" %>
<%@ include file="./pub/priv.jsp"%>

<%
	String prefix =  getServletContext().getRealPath("/");
	String configdir=prefix+"WEB-INF"+File.separator+"config"+File.separator;
	CoursewareConfig coursewareConfig = new CoursewareConfig(configdir);
	
	coursewareConfig.setCoursewareConfigAbsPath(request.getParameter("coursewareConfigAbsPath"));

	coursewareConfig.setCoursewareTemplateAbsPath(request.getParameter("coursewareTemplateAbsPath"));

	coursewareConfig.setCoursewareTemplateRefPath(request.getParameter("coursewareTemplateRefPath"));

	coursewareConfig.setCoursewareTemplateImgAbsPath(request.getParameter("coursewareTemplateImgAbsPath"));

	coursewareConfig.setCoursewareTemplateImgRefPath(request.getParameter("coursewareTemplateImgRefPath"));

	coursewareConfig.setCoursewareAbsPath(request.getParameter("coursewareAbsPath"));

	coursewareConfig.setCoursewareRefPath(request.getParameter("coursewareRefPath"));

	coursewareConfig.setCoursewareURI(request.getParameter("coursewareURI"));
	
	coursewareConfig.setCryptType(request.getParameter("cryptType"));
	
	
	coursewareConfig.setConfig();
	
	response.sendRedirect("coursewareparam_set.jsp");
%>
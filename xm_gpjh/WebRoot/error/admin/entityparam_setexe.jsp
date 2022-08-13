<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.entity.config.EntityConfig" %>
<%@ include file="./pub/priv.jsp"%>

<%
	String prefix =  getServletContext().getRealPath("/");
	String configdir=prefix+"WEB-INF"+File.separator+"config"+File.separator;
	EntityConfig entityConfig = new EntityConfig(configdir);
	
	entityConfig.setEntityConfigAbsPath(request.getParameter("entityConfigAbsPath"));
	
	entityConfig.setEntityWebIncomingAbsPath(request.getParameter("entityWebIncomingAbsPath"));
	
	entityConfig.setEntityWebIncomingUriPath(request.getParameter("entityWebIncomingUriPath"));
	
	
	entityConfig.setEntityWebFtpPort(request.getParameter("entityWebFtpPort"));
	
	entityConfig.setEntitySystemName(request.getParameter("entitySystemName"));
	
	entityConfig.setEntityCopyRight(request.getParameter("entityCopyRight"));
	
	entityConfig.setEntityLink(request.getParameter("entityLink"));	
	
	entityConfig.setEntityCollege(request.getParameter("entityCollege"));
	
	entityConfig.setConfig();
	
	response.sendRedirect("entityparam_set.jsp");
%>
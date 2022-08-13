<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>
<%
	String prefix =  getServletContext().getRealPath("/");
	String configdir=prefix+"WEB-INF"+File.separator+"config"+File.separator;
	PlatformConfig platformConfig = new PlatformConfig(configdir);
	
	platformConfig.setPlatformConfigAbsPath(request.getParameter("platformConfigAbsPath"));
	
	platformConfig.setPlatformWebAppAbsPath(request.getParameter("platformWebAppAbsPath"));
	
	platformConfig.setPlatformWebAppUriPath(request.getParameter("platformWebAppUriPath"));
	
	platformConfig.setPlatformConfigRefPath(request.getParameter("platformConfigRefPath"));
	
	platformConfig.setPlatformWebINFRefPath(request.getParameter("platformWebINFRefPath"));
	
	platformConfig.setPlatformIP(request.getParameter("platformIP"));
	
	platformConfig.setPlatformPort(request.getParameter("platformPort"));
	
	platformConfig.setPlatformDomainName(request.getParameter("platformDomainName"));
	
	platformConfig.setConfig();
	
	response.sendRedirect("platformparam_set.jsp");
%>
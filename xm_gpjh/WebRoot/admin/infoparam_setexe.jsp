<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File" %>
<%@ page import="com.whaty.platform.info.config.InfoConfig" %>
<%@ include file="./pub/priv.jsp"%>

<%
	String prefix =  getServletContext().getRealPath("/");
	String configdir=prefix+"WEB-INF"+File.separator+"config"+File.separator;
	InfoConfig infoConfig = new InfoConfig(configdir);
	
	infoConfig.setInfoConfigAbsPath(request.getParameter("infoConfigAbsPath"));
	
	infoConfig.setInfoWebIncomingAbsPath(request.getParameter("infoWebIncomingAbsPath"));
	
	infoConfig.setInfoWebIncomingUriPath(request.getParameter("infoWebIncomingUriPath"));
	
	infoConfig.setInfoWebFtpPort(request.getParameter("infoWebFtpPort"));
	
	infoConfig.setInfoSystemName(request.getParameter("infoSystemName"));
	
	infoConfig.setInfoCopyRight(request.getParameter("infoCopyRight"));
	
	infoConfig.setInfoLink(request.getParameter("infoLink"));
	
	infoConfig.setInfoCollege(request.getParameter("infoCollege"));
	
	infoConfig.setConfig();
	
	response.sendRedirect("infoparam_set.jsp");
%>
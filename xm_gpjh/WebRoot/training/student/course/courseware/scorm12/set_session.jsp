<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String value = request.getParameter("scoID");
	if(value == null || "".equals(value) || "null".equals(value)){
		value = "";
	}
	
	session.setAttribute("SCOID",value);
	%>
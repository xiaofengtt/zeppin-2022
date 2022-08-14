<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String menu_id=request.getParameter("menu_id");
	String menu_info=request.getParameter("menu_info");
	session.setAttribute("menu_id",menu_id);
	session.setAttribute("menu_info",menu_info);
%>
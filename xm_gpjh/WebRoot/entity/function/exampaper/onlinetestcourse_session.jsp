<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String testCourseId = request.getParameter("testCourseId");
	String isHiddenAnswer = request.getParameter("isHiddenAnswer");
	String isAutoCheck = request.getParameter("isAutoCheck");
	String pageInt = request.getParameter("pageInt");
	
	session.setAttribute("testCourseId",testCourseId);
	session.setAttribute("isHiddenAnswer",isHiddenAnswer);
	session.setAttribute("isAutoCheck",isAutoCheck);
%>
<script>
	location.href = "testpaper_list.jsp?pageInt=<%=pageInt%>";
</script>
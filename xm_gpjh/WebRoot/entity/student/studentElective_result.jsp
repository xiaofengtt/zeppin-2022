<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

<s:property value="msg" escape="false" />
<a href="/entity/teaching/opencourseElective_OpenCourseList.action">返回</a>
</body>
</html>
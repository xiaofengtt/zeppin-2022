<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.whaty.platform.interaction.*" %>
 <%@ include file="./pub/priv.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<link href="css/style.css" rel="stylesheet" type="text/css">
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<title>生殖健康咨询师培训网</title>
</head>
<%
	String courseware_id = request.getParameter("courseware_id");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		
 %>
<FRAMESET border=0 frameSpacing=0 rows=65,* frameBorder=0>
  <frame name="top" scrolling="no" noresize src="top.jsp?courseware_id=<%=courseware_id%>">
  <frame name="main" scrolling="auto"  src="main.jsp?courseware_id=<%=courseware_id%>">
  <noframes>
  <body>

  <p>此网页使用了框架，但您的浏览器不支持框架。</p>

  </body>
  </noframes>
</frameset>

</html>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.interaction.*"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String pageInt = request.getParameter("pageInt");
	String title = request.getParameter("title1");
	title = java.net.URLEncoder.encode(title,"UTF-8");
	String id= request.getParameter("id");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	int count =	interactionManage.updateTeachClass(request);
	if(count>0)
	{
 %>
 <script>
 	alert("修改成功！");
 	window.location="teachernote_list.jsp?pageInt=<%=pageInt%>&title=<%=title%>";
 </script>
 <%
	 }
	 else
	 {
  %>
  <script>
 	alert("修改失败！");
 	history.back();
 </script>
  <%
  	}
   %>
</body>
</html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.database.oracle.standard.sso.*"%>
<%@ page import="com.whaty.platform.courseware.*,
				 com.whaty.platform.entity.*,
				 com.whaty.platform.Exception.*"%>
<%@ page import="com.whaty.platform.sso.*" %>
<%@ include file="pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%!
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "不详";
			return str;
	
	}
%>

<%
	//String id=request.getParameter("id");
	String name=request.getParameter("name"); 
	String author=request.getParameter("author");
	String pubisher=request.getParameter("pubisher");
	String active=request.getParameter("active");
	String note=request.getParameter("note"); 
	
	if(note==null||note.equals(""))
	{
		note="略";
	} 
%>
<html>
  <head>
    <title>添加课件信息</title>
  </head>
  <body>
<%
	int suc = 0;
	int suc2 = 0;
try{

	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	CoursewareManage coursewareManage=interactionManage.createCoursewareManage();
	suc = coursewareManage.addCoursewareAndToTeachClass(name,active,"","",author,pubisher,note,teachclass_id);
	if(suc > 0)
	{	
			
		%>
	
		<script language="javascript">
			alert("<%=name+"课件加入成功"%>");
			window.navigate ("courseware_list.jsp");
		</script>


	<%
	}
	else
	{
%>
		<script language="javascript">
		alert("<%=name+"课件加入不成功"%>");
		window.history.back();
		</script>
<%	
	}
}catch(PlatformException e)
{
	out.print(e);
}
%>
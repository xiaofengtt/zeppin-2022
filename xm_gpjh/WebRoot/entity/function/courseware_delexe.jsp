<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.database.oracle.standard.sso.*"%>
<%@ page import="com.whaty.platform.courseware.*,
				 com.whaty.platform.entity.*,
				 com.whaty.platform.Exception.*"%>
<%@ page import="com.whaty.platform.sso.*,com.whaty.platform.interaction.*" %>
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
	String courseware_id=request.getParameter("courseware_id");
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
	suc = interactionManage.deleteCoursewares(courseware_id);
	if(suc > 0)
	{	
		
		%>
	
		<script language="javascript">
			alert("<%="课件删除成功"%>");
			window.navigate ("courseware_list.jsp");
		</script>


	<%
	}
	else
	{
%>
		<script language="javascript">
		alert("<%="课件删除不成功"%>");
		window.history.back();
		</script>
<%	
	}
}catch(PlatformException e)
{
	out.print(e);
}
%>
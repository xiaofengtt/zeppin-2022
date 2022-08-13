<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>

<%@ include file="../pub/priv.jsp"%>
<%!
	String fixNull(String s)
	{
		if(s == null)
			return "";
		else
			return s;
	}
%>
<%
	String title = request.getParameter("title");
	String body = request.getParameter("body");
	if(teacher == null  || !"teacher".equals(userType))
	{
%>
<script>
	alert("请以教师身份登陆");
	window.history.back();
</script>	
<%
	}	
		
	String publisherId = "";
	String publisherName = "";
	String publisherType = "";	
	String courseId1 = "";	
	int ret ;
	
	publisherId = fixNull(teacher.getId());
	publisherName = fixNull(teacher.getName());
	publisherType = fixNull(teacher.getLoginType());	
	if(publisherType == null || publisherType.trim().equals(""))
		publisherType = "teacher";
	courseId1 = teachclass_id; 
		    
	try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	ret=interactionManage.addAnnounce(title, body,publisherId, publisherName, publisherType,courseId1);
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%
	if(ret>0){
%>
	<script>
		alert("添加成功");
		window.navigate("announce_list.jsp");
	</script>	
<%
	}else{
%>
	<script>
		alert("添加失败");
		window.history.back();
	</script>		
<%
	}
%>
</body>
</html>

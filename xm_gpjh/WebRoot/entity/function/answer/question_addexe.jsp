<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>

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
<%!
	String fixnull1(String s)
	{
		if(s == null)
			return "";
		else
			return s;
	}
%>
<%
	if(student == null  || !"student".equals(userType))
	{
%>
<script>
	alert("请以学生身份登陆");
	window.history.back();
</script>	
<%
	}	
	
	String title = request.getParameter("title");
	String body = request.getParameter("body");
	String key = request.getParameter("key");
	
	String submituserId = fixNull(student.getId());
	String submituserName = fixNull(student.getName());
	String submituserType = fixNull(student.getLoginType());	
	if(submituserType == null || submituserType.trim().equals(""))
		submituserType = "student";
	String courseId1 = teachclass_id;
		    
	String reStatusStr = "false";
	
	int ret ;
	try
{
   
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
		ret = interactionManage.addQuestion(title, body, key, submituserId, submituserName, submituserType,
			courseId1, reStatusStr);
	
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret > 0) {
%>
		<script language="javascript">
			alert("成功添加！");
			window.navigate("index.jsp");
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("添加不成功！");
			window.history.back(-1);
		</script>
<%
	}
	
%>

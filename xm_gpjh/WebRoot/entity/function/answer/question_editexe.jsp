<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="java.awt.print.Printable" %>

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
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	String title = request.getParameter("title");
	String title1 = request.getParameter("title1");
	String name = request.getParameter("name");
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
	
	ret = interactionManage.updateQuestion(id, title, body, key, submituserId, submituserName, submituserType,
			courseId1, reStatusStr);
			
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret == 1) {
%>
		<script language="javascript">
			alert("修改成功！");
			window.navigate("question_detail.jsp?id=<%=id%>&pageInt=<%=pageInt%>&title=<%=title1%>&name=<%=name%>");
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("修改不成功！");
			window.history.back(-1);
		</script>
<%
	}
%>

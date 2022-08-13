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
	String id = request.getParameter("answer_id");	
	String questionId = request.getParameter("question_id");		
	String pageInt = request.getParameter("pageInt");
	String body = request.getParameter("body");
	String title = request.getParameter("title");
	String name = request.getParameter("name");
	//out.println(id+"********"+questionId+"*******"+"body");
	String reuserId = fixNull(teacher.getId());
	String reuserName = fixNull(teacher.getName());
	String reuserType = fixNull(teacher.getLoginType());
	if(reuserType == null || reuserType.trim().equals(""))
		reuserType = userType;	

	int ret ;
		    
	try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	ret = interactionManage.updateAnswer(id, questionId, body, reuserId, reuserName, reuserType);
			
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret > 0) {
%>
		<script language="javascript">
			alert("修改成功！");
			window.navigate("question_detail.jsp?id=<%=questionId%>&pageInt=<%=pageInt%>&title=<%=title%>&name=<%=name%>");
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

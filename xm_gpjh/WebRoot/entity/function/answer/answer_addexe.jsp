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
	if(teacher == null  || !"teacher".equals(userType))
	{
%>
<script>
	alert("请以老师身份登陆");
	window.history.back();
</script>	
<%
	}	
	//String title = request.getParameter("title");
	String body = request.getParameter("body");
	String questionId = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	String title = request.getParameter("title");
	String name = request.getParameter("name");

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
	
	ret = interactionManage.addAnswer(questionId, body, reuserId, reuserName, reuserType);
			
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret == 1) {
%>
		<script language="javascript">
			alert("成功添加！");
			window.navigate("question_detail.jsp?id=<%=questionId%>&pageInt=<%=pageInt%>&title=<%=title%>&name=<%=name%>");
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

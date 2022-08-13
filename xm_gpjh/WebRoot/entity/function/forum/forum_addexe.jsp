<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>

<%@ include file="../pub/priv.jsp"%>

<%
	String title = request.getParameter("title");
	String forumlist_id = request.getParameter("forumlist_id");	
	String related_id = "";

	String body = request.getParameter("body");
	String courseId1 = teachclass_id;
	String submitUserId = user.getId();
	String userName = user.getName();
	int ret ;
		    
	try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	ret=interactionManage.addForum(title, body,	submitUserId, forumlist_id,  courseId1,userName, related_id);
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
	if(ret==1){
%>
	<script>
		alert("添加成功");
		window.navigate("forum_list.jsp?forumlist_id=<%=forumlist_id%>");
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

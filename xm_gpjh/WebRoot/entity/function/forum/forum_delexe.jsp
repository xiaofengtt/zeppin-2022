<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.announce.*"%>
<%@ include file="../pub/priv.jsp"%>

<%
String id = request.getParameter("id");
String realted_id = request.getParameter("realted_id");
String forumlist_id = request.getParameter("forumlist_id");

int count = 0;
String link = "";
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	if(realted_id == null)	
	{
		count = interactionManage.deleteTopicForum(id);
		link = "forum_list.jsp?forumlist_id="+forumlist_id;
	}
	else
	{
		count = interactionManage.deleteReplyForum(id);
		link = "forum_detail.jsp?realted_id="+realted_id+"&forumlist_id="+forumlist_id;
	}	
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}

if(count == 1) {
%>
		<script language="javascript">
			alert("成功删除！");
			self.opener.location.reload();			
			window.navigate("<%=link%>");
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("删除不成功！");
			window.history.back(-1);
		</script>
<%
	}
%>
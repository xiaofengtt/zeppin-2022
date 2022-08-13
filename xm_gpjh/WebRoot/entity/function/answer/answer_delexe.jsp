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
String id = request.getParameter("answer_id");
String question_id = request.getParameter("question_id");
String pageInt = request.getParameter("pageInt");
String title = request.getParameter("title");
String name = request.getParameter("name");

int count = 0;
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	//权限设置未知。。。待修改
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	count = interactionManage.deleteAnswer(id);
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
			window.navigate("question_detail.jsp?id=<%=question_id%>&pageInt=<%=pageInt%>&title=<%=title%>&name=<%=name%>");
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
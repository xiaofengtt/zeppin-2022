<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	
	session.setAttribute("paperId",id);
	session.setAttribute("StandardAnswer",new HashMap());
	session.setAttribute("UserAnswer",new HashMap());
	session.setAttribute("Score",new HashMap());
	session.setAttribute("Title",new HashMap());
	session.setAttribute("Type",new HashMap());
	
	String tsId = request.getParameter("tsId");
	if(tsId!=null && !tsId.equals("") && !tsId.equalsIgnoreCase("null"))
	{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		HomeworkPaperHistory history = testManage.getHomeworkPaperHistory(tsId);
		HashMap map = history.getTestResultMap();
		List idList = (List)map.get("idList");
		HashMap userAnswer = (HashMap)map.get("userAnswer");
		HashMap standardAnswer = (HashMap)map.get("standardAnswer");
		HashMap Title = (HashMap)map.get("title");
		HashMap Type = (HashMap)map.get("type");
		HashMap standardScore = (HashMap)map.get("standardScore");
		HashMap userScore = (HashMap)map.get("userScore");
	
		session.setAttribute("StandardAnswer",standardAnswer);
		session.setAttribute("UserAnswer",userAnswer);
		session.setAttribute("Score",standardScore);

		session.setAttribute("UserScore",userScore);
		session.setAttribute("Title",Title);
		session.setAttribute("Type",Type);
		session.setAttribute("tsId",tsId);
	}
%>
<script>
	location.href = "homework_frame.jsp?id=<%=id%>&pageInt=<%=pageInt%>";
</script>
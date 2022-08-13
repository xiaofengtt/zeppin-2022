<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	
	try {
		String id = request.getParameter("id");
		String totalScore = request.getParameter("totalScore");
		HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
		HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");
		HashMap Title = (HashMap)session.getAttribute("Title");
		HashMap Type = (HashMap)session.getAttribute("Type");
		HashMap standardScore = (HashMap)session.getAttribute("Score");
		HashMap userScore = (HashMap)session.getAttribute("UserScore");
		HashMap map = new HashMap();
		map.put("idList",session.getAttribute("idList"));
		session.removeAttribute("idList");
		String tsId = (String)session.getAttribute("tsId");
		session.removeAttribute("tsId");
		map.put("userAnswer",userAnswer);
		map.put("standardAnswer",standardAnswer);
		map.put("title",Title);
		map.put("type",Type);
		map.put("standardScore",standardScore);
		map.put("userScore",userScore);
		map.put("totalScore",totalScore);
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		String user_id = "("+user.getId()+")"+user.getName();
		int count = 0;
		String link = "";
//		System.out.println("____"+tsId);
		if(tsId==null || tsId.equals("") || tsId.equalsIgnoreCase("null"))
		{
			count = testManage.addHomeworkPaperHistory(user_id,id,map);
			link = "homeworkpaper_list.jsp";
		}
		else
		{
			count = testManage.updateHomeworkPaperHistory(tsId,user_id,id,map,"0");
			link = "homeworkhistory_list.jsp";
		}
	if(count>0) {
%>
<script type="text/javascript">
	alert("答卷结果保存成功!");
	window.opener.location.reload();
	window.close();
	//location.href = "<%=link %>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("答卷结果保存失败!");
	window.history.back(-2);
</script>
<%		
	}	
	} catch (Exception e) {
		//out.print(e.toString());
		
	}
%>
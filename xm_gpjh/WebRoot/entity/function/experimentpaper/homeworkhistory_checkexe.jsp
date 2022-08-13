<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%!
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
	return str;
}
%>	
<%
	try {
		String tsId = request.getParameter("tsId");
		String paperId = request.getParameter("paperId");
		String pageInt = request.getParameter("pageInt");
		String totalScore = request.getParameter("total_score");
		String totalNote = request.getParameter("total_note");
		HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
		HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");
		HashMap Title = (HashMap)session.getAttribute("Title");
		HashMap Type = (HashMap)session.getAttribute("Type");
		HashMap standardScore = (HashMap)session.getAttribute("StandardScore");
		HashMap userScore = new HashMap();
		HashMap Note = new HashMap();
		Set set = Title.keySet();
		String id = "";
		String type = "";
		String uScore = "";
		String note = "";
		String totalnum = "";
		for(Iterator it=set.iterator();it.hasNext();)
		{
			id = (String) it.next();
			type = (String)Type.get(id);
			if(!type.equalsIgnoreCase(TestQuestionType.YUEDU))
			{
				uScore = fixnull(request.getParameter(id+"_score"));
				note = fixnull(request.getParameter(id+"_note"));
				userScore.put(id,uScore);
				Note.put(id,note);
			}
			else
			{
				List scoreList = new ArrayList();
				List noteList = new ArrayList();
				totalnum = request.getParameter(id+"_totalnum");
				uScore = fixnull(request.getParameter(id+"_score"));
				scoreList.add(uScore);
				note = fixnull(request.getParameter(id+"_note"));
				noteList.add(note);
				for(int i=1;i<Integer.parseInt(totalnum);i++)
				{
					uScore = fixnull(request.getParameter(id+"_"+i+"_score"));
					scoreList.add(uScore);
					note = fixnull(request.getParameter(id+"_"+i+"_note"));
					noteList.add(note);
				}
				userScore.put(id,scoreList);
				Note.put(id,noteList);
			}
		}
		
		HashMap map = new HashMap();
		map.put("idList",session.getAttribute("idList"));
		map.put("userAnswer",userAnswer);
		map.put("standardAnswer",standardAnswer);
		map.put("title",Title);
		map.put("type",Type);
		map.put("standardScore",standardScore);
		map.put("userScore",userScore);
		map.put("note",Note);
		map.put("totalScore",totalScore);
		map.put("totalNote",totalNote);
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		BasicScoreManage basicScoreManage = interactionManage.createBasicScoreManage();
		BasicActivityManage basicActivityManage = interactionManage.createBasicActivityManage();
		
		ExperimentPaperHistory history = testManage.getExperimentPaperHistory(tsId);
		String historyCreateUserId = history.getUserId().substring(history.getUserId().indexOf("(")+1,history.getUserId().indexOf(")"));
		String eleId = "";
		
		Elective ele = basicActivityManage.getElective(historyCreateUserId,teachclass_id);
		eleId = ele.getId();
		
		String user_id = user.getId();
		int count = testManage.updateExperimentPaperHistory(tsId,user_id,paperId,map);
		//int scoreCount = basicScoreManage.updateScore(null,totalScore,null,null,null,null,eleId);
	if(count>0){// && scoreCount>0) {
%>
<script type="text/javascript">
	alert("批改实验保存成功!");
	location.href = "homeworkhistory_list.jsp?pageInt=<%=pageInt%>";
</script>
<%
		} else{ %>
<script type="text/javascript">
	alert("批改实验保存失败!");
	window.history.back(-1);
</script>
<%		
	}	
	} catch (Exception e) {
		out.print(e.toString());	}
%>
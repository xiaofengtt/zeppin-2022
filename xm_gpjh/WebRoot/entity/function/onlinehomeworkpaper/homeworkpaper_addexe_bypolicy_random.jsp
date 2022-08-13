<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String score = "";
	String field = "";
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();

	String paperId = request.getParameter("paperId");
	String userId = user.getId();

	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	if(paperQuestions!=null)
	{
	Set set = paperQuestions.keySet();
	List questionList = null;	
	int count = 1;
	int totalScore = 0;
	double questionScore = 0;
	try{
		testManage.deleteStuPaperQuestions(paperId,userId);
	} catch(Exception e){}
	
	for(Iterator it = set.iterator();it.hasNext();) {
		field = (String)it.next();
		questionList = (List)paperQuestions.get(field);
		for(int i=0;i<questionList.size();i++) {
			PaperQuestion question = (PaperQuestion)questionList.get(i);
			//totalScore += Integer.parseInt(question.getReferenceScore());
			totalScore += Double.parseDouble(question.getReferenceScore());
		}
	}
	
  	for(Iterator it = set.iterator();it.hasNext();)
  	{
  		field = (String)it.next();
		questionList = (List)paperQuestions.get(field);
		
		for(int i=0;i<questionList.size();i++)
	    {
			PaperQuestion question = (PaperQuestion)questionList.get(i);
//			score = request.getParameter(question.getId()+"score");
			questionScore = Double.parseDouble(question.getReferenceScore());
			questionScore = (questionScore / totalScore) * 100;
			score = questionScore+"";
			question.setTestPaperId(paperId);
			question.setUserId(userId);

			question.setScore(score);
			question.setIndex(Integer.toString(count++));
			testManage.addPaperQuestion(question);
			questionList.set(i,question);
		}
	}
	}
%>
<script>
	location.href="homeworkpaper_pre.jsp?id=<%=paperId%>";
</script>

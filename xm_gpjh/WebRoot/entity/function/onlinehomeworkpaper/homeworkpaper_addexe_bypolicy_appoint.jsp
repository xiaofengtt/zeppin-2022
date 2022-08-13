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
	double totalScore = 0;
	String field = "";
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();

	String paperId = request.getParameter("paperId");
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	if(paperQuestions!=null)
	{
	Set set = paperQuestions.keySet();
	List questionList = null;	
	int count = 1;
	
	for(Iterator it = set.iterator();it.hasNext();){
		field = (String)it.next();
		questionList = (List)paperQuestions.get(field);
		for(int i=0;i<questionList.size();i++) {
			PaperQuestion question = (PaperQuestion)questionList.get(i);
			totalScore += Double.parseDouble(request.getParameter(question.getId()+"score"));
		}
	}
	
  	for(Iterator it = set.iterator();it.hasNext();)
  	{
  		field = (String)it.next();
		questionList = (List)paperQuestions.get(field);
		for(int i=0;i<questionList.size();i++)
	    {
			PaperQuestion question = (PaperQuestion)questionList.get(i);
			// modify 将分值以原始分数显示  begin by lianghongxia 2009-07-13
			//score = (Double.parseDouble(request.getParameter(question.getId()+"score"))/totalScore)*100 + "";
			score = request.getParameter(question.getId()+"score");
			// modify 将分值以原始分数显示  end by lianghongxia 2009-07-13
			System.out.println("score"+score);
			question.setTestPaperId(paperId);
			question.setScore(score);
			question.setIndex(Integer.toString(count++));
			testManage.addPaperQuestion(question);
			questionList.set(i,question);
		}
	}
	}
%>
<script>
	location.href="homeworkpaper_list.jsp";
</script>

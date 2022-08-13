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
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	
	try{
		session.removeAttribute("paperQuestions");
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		ExperimentPaper paper = testManage.getExperimentPaper(id);
		List questionList = paper.getPaperQuestion();
		HashMap paperQuestions = new HashMap();
		List singleList = new ArrayList();
		List multiList = new ArrayList();
		List judgeList = new ArrayList();
		List blankList = new ArrayList();
		List answerList = new ArrayList();
		List comprehensionList = new ArrayList();
		for(int i=0; i<questionList.size(); i++) {
			PaperQuestion pq = (PaperQuestion)questionList.get(i);
			pq.setStatus("old");
			String pq_type = pq.getType();
			if(pq_type.equalsIgnoreCase(TestQuestionType.DANXUAN))
				singleList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.DUOXUAN))
				multiList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.PANDUAN))
				judgeList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.TIANKONG))
				blankList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.WENDA))
				answerList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.YUEDU))
				comprehensionList.add(pq);
		}
		paperQuestions.put(TestQuestionType.DANXUAN,singleList);
		paperQuestions.put(TestQuestionType.DUOXUAN,multiList);
		paperQuestions.put(TestQuestionType.PANDUAN,judgeList);
		paperQuestions.put(TestQuestionType.TIANKONG,blankList);
		paperQuestions.put(TestQuestionType.WENDA,answerList);
		paperQuestions.put(TestQuestionType.YUEDU,comprehensionList);
		
		session.setAttribute("paperQuestions",paperQuestions);
	} catch(Exception e) { 
		out.print(e.getMessage());
	}
%>
<script>
	window.navigate("homeworkpaper_edit1_bypolicy.jsp?paperId=<%=id%>&pageInt=<%=pageInt%>");
</script>
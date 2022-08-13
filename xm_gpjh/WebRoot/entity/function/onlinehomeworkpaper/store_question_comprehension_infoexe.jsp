<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	try {
	String id = request.getParameter("id");
	String id1 = request.getParameter("id1");
	int id2 = Integer.parseInt(id1);
	String questionCoreXml = request.getParameter("questionCoreXml");
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
		String scorestr = "";
		float score = 0f;
		for(int i=1; i<coreList.size(); i++) {
        	List subList = (List)coreList.get(i);
        	scorestr = request.getParameter(i+"score");
        	score+= Float.parseFloat(scorestr);
        	subList.set(3,scorestr);
        	coreList.set(i,subList);
        }
        String xml = XMLParserUtil.getComprehensionXML(coreList);
		HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
        List questionList = (List)paperQuestions.get(TestQuestionType.YUEDU);
        PaperQuestion question = (PaperQuestion)questionList.get(id2);
        question.setQuestionCore(xml);
        question.setReferenceScore(Float.toString(score));
        PaperQuestion question1 = (PaperQuestion)questionList.set(id2,question);
		if(question1!=null) {
%>
<script type="text/javascript">
	alert("设定分数成功");
	self.opener.location.reload();
	window.close();
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("设定分数失败");
	window.history.back();
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
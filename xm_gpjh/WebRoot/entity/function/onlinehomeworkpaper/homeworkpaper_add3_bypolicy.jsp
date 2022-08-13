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
	String field = request.getParameter("field");
	String paperId = request.getParameter("paperId");
	String[] ids = request.getParameterValues("ids");
	if(ids==null || ids.length==0)
	{
	%>
	<script>
		alert("请选择试题！");
		window.history.back(-1);
	</script>
	<%
		return;
	}
	List idList = Arrays.asList(ids);
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	if(paperQuestions==null)
	{
		paperQuestions = new HashMap();
		paperQuestions.put(TestQuestionType.DANXUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.DUOXUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.PANDUAN,new ArrayList());
//		paperQuestions.put(TestQuestionType.TIANKONG,new ArrayList());
		paperQuestions.put(TestQuestionType.WENDA,new ArrayList());
//		paperQuestions.put(TestQuestionType.YUEDU,new ArrayList());
	}
	List questionList1 = (List)paperQuestions.get(field);
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	List questionList2 = testManage.getQuestionsByPaperPolicy(null,field,idList);
	List questionList = new ArrayList(questionList1);
	questionList.addAll(questionList2);
	paperQuestions.put(field,questionList);
	session.setAttribute("paperQuestions",paperQuestions);
%>
<script>
	location.href="homeworkpaper_add1_bypolicy.jsp?paperId=<%=paperId%>";
</script>

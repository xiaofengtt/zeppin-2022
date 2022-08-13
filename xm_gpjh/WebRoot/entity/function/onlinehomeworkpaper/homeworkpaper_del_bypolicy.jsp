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
	String i = request.getParameter("i");
	String field = request.getParameter("field");
	String paperId = request.getParameter("paperId");
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	List questionList = (List)paperQuestions.get(field);
	PaperQuestion question = (PaperQuestion)questionList.get(Integer.parseInt(i));
	questionList.remove(question);
	paperQuestions.put(field,questionList);
	session.setAttribute("paperQuestions",paperQuestions);
%>
<script>
	location.href="homeworkpaper_add1_bypolicy.jsp?paperId=<%=paperId%>";
</script>

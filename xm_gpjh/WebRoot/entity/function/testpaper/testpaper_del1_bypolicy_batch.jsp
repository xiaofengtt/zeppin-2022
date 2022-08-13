<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%!
	int[] toNumbers(String[] strs) throws Exception{
		int[] nums = new int[strs.length];
		for(int i = 0 ; i < strs.length ; i++) {
			nums[i] = Integer.parseInt(strs[i]);
		}
		return nums;
	}
 %>
<%
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	Set set = paperQuestions.keySet();
	String paperId = request.getParameter("paperId");
	for(Iterator it = set.iterator();it.hasNext();) {
		String field = (String)it.next();
		String[] list = request.getParameterValues(field + "Child");
		if(list == null || list.length == 0)
			continue;
		List questionList = (List)paperQuestions.get(field);
		int[] questions = toNumbers(list);
		Arrays.sort(questions);
		for(int i = 1 ; i <= questions.length ; i++) {
			questionList.remove(questions[questions.length - i]);
		}
		//paperQuestions.put(field,questionList);
	}


	session.setAttribute("paperQuestions",paperQuestions);
%>
<script>
	location.href="testpaper_edit1_bypolicy.jsp?paperId=<%=paperId%>";
</script>

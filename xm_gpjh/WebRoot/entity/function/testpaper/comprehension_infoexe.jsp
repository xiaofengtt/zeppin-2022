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
		HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
		List user_answer = new ArrayList();
		//if(userAnswer!=null)
		//{
		//	user_answer = (List)userAnswer.get(id);
		//}
		//if(user_answer==null || user_answer.size()==0)
		//{
		//	user_answer = new ArrayList();
			user_answer.add("");
		//}
		String totalNum = request.getParameter("totalNum");
		for(int i=1;i<=Integer.parseInt(totalNum);i++)
		{
			String answer = "";
			String answers[] = request.getParameterValues("answer_"+i);
			if(answers!=null)
			{
				for(int t=0;t<answers.length;t++)
				{
					answer+="|"+answers[t];
				}
			}
			if(answer.length()>0)
				answer = answer.substring(1);
			user_answer.add(answer);
		}
		userAnswer.put(id,user_answer);
		session.setAttribute("UserAnswer",userAnswer);
		
		String qNum = request.getParameter("qNum");
		String dire = request.getParameter("direction");
		List qList = (ArrayList)session.getAttribute("qList");
		String link = "";
			if(dire!=null && dire.equals("next"))
			{
				if(Integer.parseInt(qNum)<qList.size()-1)
				link = (String)qList.get(Integer.parseInt(qNum)+1);
				else
				link = (String)qList.get(Integer.parseInt(qNum));
			}
			if(dire!=null && dire.equals("last"))
			{
				if(Integer.parseInt(qNum)>0)
				link = (String)qList.get(Integer.parseInt(qNum)-1);
				else
				link = (String)qList.get(Integer.parseInt(qNum));
			}
			if(dire!=null && dire.equals("final"))
			{
				String paperId = (String)session.getAttribute("paperId");
				%>
<script type="text/javascript">
	//alert("?????????????????????");
	parent.location.href = "testpaper_result.jsp?id=<%=paperId%>";
</script>			
				<%
				return;
			}
%>
<script type="text/javascript">
	//alert("?????????????????????");
	location.href = "<%=link%>";
</script>
<%		
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
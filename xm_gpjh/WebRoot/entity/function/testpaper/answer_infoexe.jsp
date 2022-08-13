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
		String qNum = request.getParameter("qNum");
		String dire = request.getParameter("direction");
		List qList = (ArrayList)session.getAttribute("qList");
		String user_answer = request.getParameter("body");
		HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
		if(userAnswer!=null)
		{
			userAnswer.put(id,user_answer);
		}
		session.setAttribute("UserAnswer",userAnswer);
		
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
	//alert("答案保存完毕！");
	parent.location.href = "testpaper_result.jsp?id=<%=paperId%>";
</script>			
				<%
				return;
			}
%>
<script type="text/javascript">
	//alert("答案保存完毕！");
	location.href = "<%=link%>";
</script>
<%		
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
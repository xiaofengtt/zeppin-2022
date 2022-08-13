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
	String fixnull(String str)
	{
		if(str==null || str.equalsIgnoreCase("null"))
			return "";	
		else
			return str;
	}
%>
<%
	try {
		List singleList = (ArrayList)session.getAttribute("singleList");
		List multiList = (ArrayList)session.getAttribute("multiList");
		List judgeList = (ArrayList)session.getAttribute("judgeList");
		List blankList = (ArrayList)session.getAttribute("blankList");
		List answerList = (ArrayList)session.getAttribute("answerList");
		List comprehensionList = (ArrayList)session.getAttribute("comprehensionList");
		HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
		if(userAnswer==null)
		{
			userAnswer = new HashMap();
		}
		if(singleList!=null && singleList.size()>0)
		{
			for(int t=0;t<singleList.size();t++)
			{
				String id = ((PaperQuestion)singleList.get(t)).getId();
				userAnswer.put(id,fixnull(request.getParameter("single_"+id)));
			}
		}
		if(multiList!=null && multiList.size()>0)
		{
			for(int t=0;t<multiList.size();t++)
			{
				String id = ((PaperQuestion)multiList.get(t)).getId();
				String[] user_answers = request.getParameterValues("multi_"+id);
				String user_answer = "";
				if(user_answers!=null)
				{
					for(int i=0;i<user_answers.length;i++)
					{
						user_answer+="|"+fixnull(user_answers[i]);
					}
					if(user_answer.length()>0)
					user_answer = user_answer.substring(1);
				}	
				userAnswer.put(id,fixnull(user_answer));
			}
		}
		if(judgeList!=null && judgeList.size()>0)
		{
			for(int t=0;t<judgeList.size();t++)
			{
				String id = ((PaperQuestion)judgeList.get(t)).getId();
				userAnswer.put(id,fixnull(request.getParameter("judge_"+id)));
			}
		}
		if(blankList!=null && blankList.size()>0)
		{
			for(int t=0;t<blankList.size();t++)
			{
				String id = ((PaperQuestion)blankList.get(t)).getId();
				userAnswer.put(id,fixnull(request.getParameter("blank_"+id)));
			}
		}
		if(answerList!=null && answerList.size()>0)
		{
			for(int t=0;t<answerList.size();t++)
			{
				String id = ((PaperQuestion)answerList.get(t)).getId();
				userAnswer.put(id,fixnull(request.getParameter("answer_"+id)));
			}
		}
		if(comprehensionList!=null && comprehensionList.size()>0)
		{
			for(int t=0;t<comprehensionList.size();t++)
			{
				String id = ((PaperQuestion)comprehensionList.get(t)).getId();
				List user_answer = new ArrayList();
				user_answer.add("");
				String totalNum = request.getParameter("comp_"+id+"_totalNum");
				for(int i=1;i<=Integer.parseInt(totalNum);i++)
				{
					String answer = "";
					String answers[] = request.getParameterValues("comp_"+id+"_"+i);
					if(answers!=null)
					{
						for(int x=0;x<answers.length;x++)
						{
							answer+="|"+fixnull(answers[x]);
						}
					}
					if(answer.length()>0)
						answer = answer.substring(1);
					user_answer.add(answer);
				}
				userAnswer.put(id,user_answer);
			}
		}
		String paperId = (String)session.getAttribute("paperId");
		String link = "homeworkpaper_result.jsp?id="+paperId;
%>
<script type="text/javascript">
	//alert("答案保存完毕！");
	parent.location.href = "<%=link%>";
</script>
<%		
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
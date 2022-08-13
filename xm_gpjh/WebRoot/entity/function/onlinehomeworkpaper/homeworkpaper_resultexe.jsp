<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@page import="java.text.NumberFormat"%>
<%@ include file="../pub/priv.jsp"%>
<%
	
	try {
		String id = request.getParameter("id");
		String tsId = request.getParameter("tsId");
		
		
		String totalScore = request.getParameter("totalScore");
		String right = request.getParameter("right");
		String errors = request.getParameter("errors");
		int zong = Integer.parseInt(right)+Integer.parseInt(errors);
		Double err = Double.valueOf(errors);
		Double rig = Double.valueOf(right);

		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMaximumFractionDigits(2);
		Double double1 = rig/zong;
		String percent =nf.format(double1);
		
		HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
		HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");
		HashMap Title = (HashMap)session.getAttribute("Title");
		HashMap Type = (HashMap)session.getAttribute("Type");
		HashMap standardScore = (HashMap)session.getAttribute("Score");
		HashMap userScore = (HashMap)session.getAttribute("UserScore");
		HashMap map = new HashMap();
		map.put("idList",session.getAttribute("idList"));
		session.removeAttribute("idList");
		map.put("userAnswer",userAnswer);
		map.put("standardAnswer",standardAnswer);
		map.put("title",Title);
		map.put("type",Type);
		map.put("standardScore",standardScore);
		map.put("userScore",userScore);
		map.put("totalScore",totalScore);
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		String user_id = "("+user.getId()+")"+user.getName();
		int count = 0;
		String link = "";
		String ischeck = (String)session.getAttribute("ischeck");
		if(tsId==null || tsId.equals("") || tsId.equalsIgnoreCase("null"))
		{	
			count = testManage.addHomeworkPaperHistory(user_id,id,map,ischeck);
			link = "homeworkpaper_list.jsp";
		}
		else
		{
			count = testManage.updateHomeworkPaperHistory(tsId,user_id,id,map,ischeck);
			link = "homeworkhistory_info.jsp";
		}
	if(count>0) {
%>
<script type="text/javascript">
	window.opener.location.reload();
	alert("答卷结果保存成功!");
	var te1 = <%=right%>;
	var te2 = <%=errors%>;
	var ischeck=<%=ischeck%>
	if(ischeck==0)
	{
		if((te1>0)&&(te2>0)){
			alert("    本次作业一共<%=zong%>题!\n     您做对了:"+<%=right%>+"题! \n       您做错了:"+<%=errors%>+"题!    \n  正确率:<%=percent%>");
		}
		if(confirm("查看参考答案?")){
			window.location='homeworkhistory_info.jsp?paperId=<%=id%>&user_id=<%=user_id%>&ischeck=<%=ischeck%>';
		}else{
			window.close();
		}
	}
	else
	{
		window.close();
	}
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("答卷结果保存失败!");
	window.history.go(-3);
</script>
<%		
	}	
	} catch (Exception e) {
		//out.print(e.toString());
		
	}
%>
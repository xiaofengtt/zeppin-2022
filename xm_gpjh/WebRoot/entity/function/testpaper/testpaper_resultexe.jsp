<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<jsp:directive.page import="com.whaty.platform.Exception.PlatformException"/>
<%@ include file="../pub/priv.jsp"%>
<%
	
	try {
		String id = request.getParameter("id");
		String totalScore = request.getParameter("totalScore");
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
		if(((String)session.getAttribute("isAutoCheck")).equals("1"))
		{
			map.put("userScore",userScore);
			map.put("totalScore",totalScore);
		}
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		BasicScoreManage basicScoreManage = interactionManage.createBasicScoreManage();
		BasicActivityManage basicActivityManage = interactionManage.createBasicActivityManage();
		TestManage testManage = interactionManage.creatTestManage();
		
		ArrayList scoreList = new ArrayList();
		scoreList.add(totalScore);
		ArrayList idList = new ArrayList();
		idList.add(user.getId());
		
		String user_id = "("+user.getId()+")"+user.getName();
		int count = 0;
		try{
		//try{
	//	String openCourseId = (String)session.getAttribute("openCourseId");
		
		//basicScoreManage.updateScoreBatch(null,null,null,null,null,scoreList,null, idList, null,null,openCourseId);
		//}
		//catch(PlatformException e)
		//{
		//}
		}catch(Exception e)
		{
			count=-1;
		}
		if(count!=-1)
			count=testManage.addTestPaperHistory(user_id,id,map);
	if(count>0) {
%>
<script type="text/javascript">
	alert("答卷结果保存成功!");
	window.opener.location.reload();
	window.close();
	//location.href = "testpaper_list.jsp";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("答卷结果保存失败!");
	window.close();
	//window.history.back(-2);
</script>
<%		
	}	
	} catch (Exception e) {
		out.print(e.toString());
		e.printStackTrace();
	}
%>
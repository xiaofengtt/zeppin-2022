<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	try {
		String testCourseId = (String) session.getValue("testCourseId");  
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String paperId = request.getParameter("paperId");
		String title = request.getParameter("title");
		String pageInt = request.getParameter("pageInt");
		int count = testManage.deleteTestPaperByOnlineTestCourse(paperId,testCourseId);
		int i = testManage.deleteTestPaper(paperId);
		if(i>0) {
%>
<script type="text/javascript">
	alert("删除成功");
	location.href = "testpaper_list.jsp?pageInt=<%=pageInt%>&title=<%=title%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("删除失败");
	window.history.back();
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
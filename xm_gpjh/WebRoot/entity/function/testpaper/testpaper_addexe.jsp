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
	String testCourseId = (String) session.getValue("testCourseId");  
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String title = request.getParameter("title");
		String time = request.getParameter("time");
		String note = request.getParameter("note");
		String status = request.getParameter("status");
		String types = request.getParameter("types");
		String creatUser = user.getName();
		
		
		int paperId = testManage.addTestPaper(title,creatUser,status,note,time,teachclass_id);
		int count = testManage.addTestPaperByOnlineTestCourse(Integer.toString(paperId),testCourseId);
		
		if(paperId>0 && types.equals("1")) {
%>
<script type="text/javascript">
	alert("添加成功");
	location.href = "testpaperpolicy_list.jsp?paperId=<%=paperId%>";
</script>
<%
		}
		else if(paperId>0 && types.equals("0")) {
%>
<script type="text/javascript">
	alert("添加成功");
	location.href = "testpaper_add_bypolicy.jsp?id=0&paperId=<%=paperId%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("添加失败");
	window.history.back(-1);
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
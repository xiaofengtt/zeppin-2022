<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/priv.jsp"%>
<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String testCourseId = request.getParameter("testCourseId");
		String title = request.getParameter("title");
		String pageInt = request.getParameter("pageInt");
		String status = request.getParameter("status");
		String note = request.getParameter("note");
		int i = testManage.changeOnlineExamCourseStatus(testCourseId,status);
		if(i>0) {
%>
<script type="text/javascript">
	alert("设置为<%=note%>成功!");
	location.href = "onlinetestcourse_list.jsp?pageInt=<%=pageInt%>&title=<%=title%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("设置为<%=note%>失败!");
	window.history.back(-1);
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
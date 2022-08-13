<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/priv.jsp"%>
<%
			String status = (String)session.getAttribute("mock_login");
			if(status != null && status.equals("1")){ 
		%>
			<script>alert('模拟学生登陆只能查看不能删除');window.history.back();</script>
		<%
			}
		%>
<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String testCourseId = request.getParameter("testCourseId");
		String title = request.getParameter("title");
		String pageInt = request.getParameter("pageInt");
		int i = testManage.deleteOnlineExamCourse(testCourseId);
		if(i>0) {
%>
<script type="text/javascript">
	alert("删除成功");
	location.href = "onlinetestcourse_list.jsp?pageInt=<%=pageInt%>&title=<%=title%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("删除失败");
	window.history.back(-1);
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
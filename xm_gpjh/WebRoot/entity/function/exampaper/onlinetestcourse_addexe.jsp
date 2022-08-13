<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/priv.jsp"%>

<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String title = request.getParameter("title");
		String note = request.getParameter("note");
		String start_date = request.getParameter("start_date");
		String start_hour = request.getParameter("start_hour");
		String start_minute = request.getParameter("start_minute");
		String start_second = request.getParameter("start_second");
		String startDate = start_date+" "+start_hour+":"+start_minute+":"+start_second;
		String end_date = request.getParameter("end_date");
		String end_hour = request.getParameter("end_hour");
		String end_minute = request.getParameter("end_minute");
		String end_second = request.getParameter("end_minute");
		String endDate = end_date+" "+end_hour+":"+end_minute+":"+end_second;
		String status = request.getParameter("status");
		String isAutoCheck = request.getParameter("isAutoCheck");
		String isHiddenAnswer = request.getParameter("isHiddenAnswer");
		String user_id = user.getName();
		
		int count = testManage.addOnlineExamCourse(title,note,startDate,endDate,status,isAutoCheck,isHiddenAnswer,null,teachclass_id,user_id);
		
		if(count>0) {
%>
<script type="text/javascript">
	alert("添加成功");
	location.href = "onlinetestcourse_list.jsp";
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
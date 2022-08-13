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
		String note = request.getParameter("note");
		String status = request.getParameter("status");
		String types = request.getParameter("types");//1：随机，0：固定。
		String creatUser = user.getName();
		
		int paperId = 0;
		
		if(types.equals("1")) {
		//随机题目作业 
			session.setAttribute("paper_title",title);
			session.setAttribute("paper_status",status);
			session.setAttribute("paper_note",note);
			session.setAttribute("paper_startDate",startDate);
			session.setAttribute("paper_endDate",endDate);
//			paperId = testManage.addHomeworkPaper(title,creatUser,status,note,null,startDate,endDate,"1",teachclass_id);
		} else {
		//指定题目作业
			paperId = testManage.addHomeworkPaper(title,creatUser,status,note,null,startDate,endDate,"0",teachclass_id);
		}
		
		if(types.equals("1")) {
		
%>
<script type="text/javascript">
//	alert("添加成功");
//	location.href = "homeworkpaperpolicy_list.jsp?paperId=<%=paperId%>&type1=<%=types%>";
	location.href = "/entity/function/onlinehomeworkpaper/paperpolicy_add1.jsp?";
</script>
<%
		} else if(paperId>0 && types.equals("0")) {
%>
<script type="text/javascript">
	alert("添加成功");
	location.href = "/entity/function/onlinehomeworkpaper/homeworkpaper_add_bypolicy_appoint.jsp?id=0&paperId=<%=paperId%>&type1=<%=types%>";
</script>
<%
		}else {
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
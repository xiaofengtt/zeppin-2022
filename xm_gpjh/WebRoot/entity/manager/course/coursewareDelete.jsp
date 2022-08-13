<%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import = "com.whaty.platform.training.*"%>
<%

	String coursewareId=request.getParameter("course_Id");
	String type=request.getParameter("type");
	try{
		TrainingFactory factory=TrainingFactory.getInstance();
		TrainingManage trainingManage=factory.creatTrainingManage();
		trainingManage.removeTrainingCourseware(coursewareId);
		
		%>
		<script type="text/javascript">
<!--
	alert('删除成功!');
	window.close();
//-->
</script>
		<%
		
	}catch(Exception e){
		%>
		<script type="text/javascript">
<!--
	alert('<%=e.getMessage()%>');
	window.history.back();
//-->
</script>
		<%
	}
	
%>

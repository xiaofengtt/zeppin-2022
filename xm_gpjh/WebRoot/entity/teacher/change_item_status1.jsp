<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.whaty.platform.entity.CourseItemManage"%>
<%@ page import="com.whaty.platform.database.oracle.standard.entity.OracleCourseItemManage"%>

<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	
	}
%>
<%
	CourseItemManage manage = new OracleCourseItemManage();
	
	String smzuoye = "0";
	String zuoye = "0";
	String xingshi = request.getParameter("xingshi");
	if (xingshi != null && xingshi.equals("0")) {
		smzuoye = "1";
	} else {
		zuoye = "1";
	}

	String courseIds = request.getSession().getAttribute("courseIds").toString();
	String[] ids = courseIds.split(",");
	int count = 0;
	for (int i = 0; i < ids.length; i++) {
		
		manage.updateCourseItemStatus("smzuoye",smzuoye,ids[i]);
		manage.updateCourseItemStatus("zuoye",zuoye,ids[i]);
	}
	
	count = 1;
%>


<%
	if(count>0){
%>
	<script>
		alert("修改成功!");
		window.navigate("/entity/teaching/courseHomework.action");
	</script>		
<%
	}
	else{
%>
<script>
		alert("修改不成功!");
		window.close();
	</script>


<%}%>
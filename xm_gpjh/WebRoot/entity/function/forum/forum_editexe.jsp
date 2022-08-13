<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="java.awt.print.Printable" %>

<%@ include file="../pub/priv.jsp"%>

<%
	String id = request.getParameter("id");
	String realted_id = request.getParameter("realted_id");
	String title = request.getParameter("title");
	String body = request.getParameter("body");

	//String submituserId = student.getId();
	//String submituserName = student.getName();
	//String submituserType = student.getLoginType();
	//String courseId = course.getId();
	//String reStatusStr = "false";
	
	int ret ;
		    
	try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
		
	ret = interactionManage.updateForum(id, title, body);
			
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret == 1) {
%>
		<script language="javascript">
			alert("修改成功！");
			self.opener.location.reload();
			window.navigate("forum_detail.jsp?realted_id=<%=realted_id%>");
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("修改不成功！");
			window.history.back(-1);
		</script>
<%
	}
%>

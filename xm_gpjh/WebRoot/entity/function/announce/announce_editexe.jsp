<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>

<%@ include file="../pub/priv.jsp"%>
<%!
	String fixNull(String s)
	{
		if(s == null)
			return "";
		else
			return s;
	}
%>
<%
	String pageInt = request.getParameter("pageInt");
	String title = request.getParameter("title");
	String title1 = request.getParameter("title1");
	String publisher_name = request.getParameter("publisher_name");
	String body = request.getParameter("body");
	String id = request.getParameter("id");
	String publisherId = "";
	String publisherName = "";
	String publisherType = "";	
	String courseId1 = "";


	int ret ;
	
	//courseInfo = (com.whaty.dls.OpenCourseInfo)session.getValue("courseInfo");
	//String open_course_id = courseInfo.getOpenCourseId();
	
	//InteractionUser userInfo = new InteractionUser();
	publisherId = fixNull(teacher.getId());
	publisherName = fixNull(teacher.getName());
	publisherType = fixNull(teacher.getLoginType());	
	if(publisherType == null || publisherType.trim().equals(""))
		publisherType = "teacher";
	courseId1 = teachclass_id; 
	
		    
	try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	ret=interactionManage.updateAnnounce(id,title, body,publisherId,publisherName, publisherType,courseId1);
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>

<html>
<body>
<%
	if(ret > 0){
%>
	<script language="javascript">
		alert("修改成功");
		window.navigate("announce_detail.jsp?id=<%=id%>&pageInt=<%=pageInt%>&title=<%=title1%>&publisher_name=<%=publisher_name%>");
	</script>
<%
		return;
	}else{
%>
	<script language="javascript">
		window.history.back();
	</script>
<%
	}
%>
</body>
</html>

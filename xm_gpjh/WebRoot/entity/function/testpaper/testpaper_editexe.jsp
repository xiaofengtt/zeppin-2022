<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String time = request.getParameter("time");
		String note = request.getParameter("note");
		String status = request.getParameter("status");
		String pageInt = request.getParameter("pageInt");
		
		String creatUser = user.getName();
		
		int i = testManage.updateTestPaper(id,title,creatUser,status,note,time,"test",teachclass_id);
		
		if(i>0) {
%>
<script type="text/javascript">
	alert("试卷基本信息修改成功");
	location.href = "testpaper_list.jsp";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("试卷基本信息修改失败");
	window.history.back(-1);
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
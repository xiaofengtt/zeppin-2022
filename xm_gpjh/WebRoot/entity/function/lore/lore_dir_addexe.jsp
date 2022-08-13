<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>

<%
	String fatherDir = request.getParameter("fatherDir");
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String name = request.getParameter("name");
		String note = request.getParameter("note");
		String creatDate = request.getParameter("creatDate");
		String openCourseId = openCourse.getId();
		String courseId1 = openCourse.getBzzCourse().getId();
		int i = testManage.addLoreDir(name, note, fatherDir, creatDate,courseId);
		
		if(i>0) {
%>
<script type="text/javascript">
	alert("添加成功");
	location.href = "lore_dir_list.jsp?loreDirId=<%=fatherDir%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("添加失败");
	window.history.back();
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
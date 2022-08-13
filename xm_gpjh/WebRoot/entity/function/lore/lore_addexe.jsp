<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>

<%
	String loreDirId = request.getParameter("loreDirId");
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance(); 
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();  
		
		String name = request.getParameter("name");
		String creatDate = request.getParameter("creatDate");
		String content = request.getParameter("content");
		String active = request.getParameter("active");

		int i = testManage.addLore(name,creatDate,content,loreDirId,"1111",active); 
		
		if(i>0) {
%>
<script type="text/javascript">
	alert("添加成功");
	location.href = "lore_dir_list.jsp?loreDirId=<%=loreDirId%>";
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
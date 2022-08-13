<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>

<%
	String loreDirId = request.getParameter("loreDirId");
	String id = request.getParameter("id");
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		int i = 0;
		try{
			i = testManage.deleteLore(id);
		}catch (Exception e){
%>		
<script type="text/javascript">
	alert("<%=e.getMessage().trim()%>");
	location.href = "lore_dir_list.jsp?loreDirId=<%=loreDirId%>";
</script>
<%	
		}
		
		if(i>0) {
%>
<script type="text/javascript">
	alert("删除成功");
	location.href = "lore_dir_list.jsp?loreDirId=<%=loreDirId%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("删除失败");
	window.history.back();
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
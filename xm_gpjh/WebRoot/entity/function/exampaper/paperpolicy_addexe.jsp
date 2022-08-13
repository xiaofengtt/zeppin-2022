<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/priv.jsp"%>
<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		int id = testManage.addPaperPolicy(request,session);
		
		if(id > 0) {
%>
<script type="text/javascript">
	alert("添加成功!")
	location.href = "testpaperpolicy_list.jsp?type=list";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("添加失败!");
	window.history.back();
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
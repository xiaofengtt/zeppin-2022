<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.whaty.platform.interaction.*" %>
<%@ page import="com.whaty.platform.interaction.*" %>
<%@ page import="com.whaty.platform.entity.basic.*" %>

<%@ include file="../pub/priv.jsp"%>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	String id=request.getParameter("id");
	int ret=0;
	try{
		ret=interactionManage.deleteForumList(id);
	}catch(Exception e){
		out.println(e.getMessage());
		return;
	}
	if(ret==1){
%>
<script language="javascript">
	alert("恭喜您，删除成功");
	window.navigate("forumlist_list.jsp");
</script>
<%
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>生殖健康咨询师培训网</title>
  </head>
  
  <body>

  </body>
</html>

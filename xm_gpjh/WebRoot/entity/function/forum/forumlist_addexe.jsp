<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.interaction.*" %>
<%@ page import="com.whaty.platform.interaction.*" %>
<%@ page import="com.whaty.platform.entity.basic.*" %>

<%@ include file="../pub/priv.jsp"%>
<%
try{

	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
String name=request.getParameter("name");
String content=request.getParameter("content");
String course_id=teachclass_id;
int ret=0;
try{
ret=interactionManage.addForumList(name,content,course_id);
}catch(Exception e){
	out.print(e.getMessage());
	return;
}
if(ret==1){
%>
<script language="javascript">
	alert("恭喜您，添加成功！");
	window.navigate("forumlist_list.jsp");
</script>
<%
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title></title>
  </head>
  
  <body>

  </body>
</html>
<%
}catch(Exception e)
{
	out.print(e.getStackTrace());
}	
%>
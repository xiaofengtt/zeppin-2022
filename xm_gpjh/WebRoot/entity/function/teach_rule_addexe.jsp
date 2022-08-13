<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ include file="./pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		int count  = interactionManage.updateTeachRule(teachclass_id,request.getParameter("body"));
		
		if(count>0)
		{
 %>
 <script>
 	alert("修改成功");
 	window.location = "teach_rule.jsp";
 </script>
 <%
 		}
 		else
 		{
  %>
  <script>
 	alert("修改失败");
 	histroy.back();
 </script>
  <%
  		}
   %>

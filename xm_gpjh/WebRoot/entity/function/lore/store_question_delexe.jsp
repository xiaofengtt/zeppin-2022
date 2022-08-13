<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../pub/commonfuction.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库题目删除</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String id = fixnull(request.getParameter("id"));
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	
	String pageInt = request.getParameter("pageInt");
	
	String titleVal= fixnull(request.getParameter("titleVal"));//java.net.URLDecoder.decode(fixnull(request.getParameter("titleVal")),"UTF-8");
	//System.out.println("titleVal:"+titleVal);
	
	String[] ids = request.getParameterValues("idaaa");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
	if(!"".equals(id)){
	try {

		int i = testManage.deleteStoreQuestion(id);
		if(i > 0) {
%>
<script type="text/javascript">
	alert("删除成功");
	location.href = "lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>&title=<%=java.net.URLEncoder.encode(titleVal,"UTF-8")%>&pageInt=<%=pageInt%>";
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
	}
	if(ids!=null){
	  int j=0;
	   for(int i = 0;i<ids.length;i++){
	     j=j+testManage.deleteStoreQuestion(ids[i]);
	   }
	   		if(j> 0) {
%>
<script type="text/javascript">
	alert("成功删除<%=j%>条记录");
	location.href = "lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>&title=<%=java.net.URLEncoder.encode(titleVal,"UTF-8")%>&pageInt=<%=pageInt%>";
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
	}
	
%>

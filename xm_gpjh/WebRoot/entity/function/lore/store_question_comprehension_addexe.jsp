<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库--单选题添加</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String loreId = request.getParameter("loreId");//loreId
	String loreDirId = request.getParameter("loreDirId");
	
	
	StoreQuestion sq = (StoreQuestion)session.getAttribute("storeQuestion");
	String xml = sq.getQuestionCore();
	xml = xml + "</question>";
	sq.setQuestionCore(xml);
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
			
		
	 java.util.Date sDate = new java.util.Date();
	SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	 String creatdate=sDateFormat.format(sDate);//sq.getCreatDate()

		
		int i = testManage.addStoreQuestion(sq.getTitle(), user.getName(), creatdate, sq.getDiff(), sq.getKeyWord(), xml,
			sq.getLore(), sq.getCognizeType(), sq.getPurpose(), sq.getReferenceScore(), sq.getReferenceTime(), sq.getStudentNote(), sq.getTeacherNote(), sq.getType());
		if(i > 0) {
		session.removeAttribute("storeQuestion");
%>
<script type="text/javascript">
	alert("添加成功");
	parent.opener.location.href = "lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>";
	top.window.close();
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
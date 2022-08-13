<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.util.Cookie.*,com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
	session.setMaxInactiveInterval(40*60);
	session.setAttribute("paperId",id);
	session.setAttribute("StandardAnswer",new HashMap());
	session.setAttribute("UserAnswer",new HashMap());
	session.setAttribute("Score",new HashMap());
	session.setAttribute("Title",new HashMap());
	WhatyCookieManage cookieManage = new WhatyCookieManage();
	cookieManage.addCookie(response,"TestTime",null);
	
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	String user_id = "("+user.getId()+")"+user.getName();
	int isTest = testManage.getTestPaperHistorysNum(user_id,id,null,null,teachclass_id);
	isTest=0;  //判断小于60分可以重做
	if(isTest>0)
	{
%>
<script>
		alert("你已经参加过此次自测！");
		window.close();
</script>
<%
	}
	else
	{
%>
<script>
	location.href = "testpaper_frame.jsp?id=<%=id%>";
</script>
<%
	}
%>
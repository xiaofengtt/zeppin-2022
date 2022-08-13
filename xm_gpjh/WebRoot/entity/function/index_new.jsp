<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.whaty.util.editor.*,com.whaty.platform.interaction.*" %>
 <%@ include file="./pub/priv.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<style type="text/css">
<!--
body {
	background-color: #e5eaee;
}
-->
</style></head>
<%	
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	WhatyEditorConfig editorConfig=interactionManage.getWhatyEditorConfig(session);
	session.setAttribute("whatyEditorConfig",editorConfig);
 %>
<frameset rows="35,*,35" frameborder="no" border="0" framespacing="0">
<frame src="top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="8,184,*,8" frameborder="no" border="0" framespacing="0">
			<frame src="left0.html" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
			<frame src="left1.jsp" name="leftFrame1" scrolling="No" noresize="noresize" id="leftFrame1" title="leftFrame1" />
			<frameset rows="75,*" frameborder="no" border="0" framespacing="0">
				<frame src="top2.jsp" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
				<frame src="./kcjj/coursenote_list.jsp" name="mainFrame" scrolling="auto" noresize="noresize"  title="mainFrame" />
			</frameset>
			<frame src="right0.html" name="rightFrame" scrolling="No" noresize="noresize" id="rightFrame" title="rightFrame" />
  </frameset>
  <frame src="bot.html" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes><body>
</body>
</noframes></html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<link href="css/style.css" rel="stylesheet" type="text/css">
<title>生殖健康咨询师培训课堂</title>
</head>
<html>
<frameset rows="60,*" cols="*" frameborder=0 border="0" framespacing="0">
  <frame src="/entity/studyZone/courseResources_previewTestpaperTop.action?paper_id=<s:property value='#parameters.paper_id[0]'/>" name="topFrame" scrolling="NO" frameborder="0" marginwidth="0" marginheight="0" noresize >
  <frameset rows="*" cols="150,0,*" framespacing="0" frameborder="0" border="0" marginwidth="0" marginheight="0" name="main_set" id="main_set">
    <frame src="preview_testpaper_left.jsp?paper_id=<s:property value='#parameters.paper_id[0]'/>" scrolling="auto" name="rightmarin" frameborder="0" marginwidth="0" marginheight="0"  noresize>
    <frame src="" scrolling="no" name="right">
	<frame src="testpaper_main.jsp" scrolling="auto" name="submain" frameborder="0" marginwidth="0" marginheight="0"  noresize>
  </frameset>
  <noframes>
  <body>
  <p>此网页使用了框架，但您的浏览器不支持框架。</p>
  </body>
  </noframes>
</frameset>

</html>
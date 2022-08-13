<%@ page contentType="text/html;charset=UTF-8" %> 
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java" %>
<html>
  <head>
    <title>My JSP 'sendMessage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form action="/message/msginfo_add.action">
    msgUserId:<input name="msgUserId"></input><br>
    msgFromUserId:<input name="msgFromUserId"></input><br>
    msgFromUserName:<input name="msgFromUserName"></input><br>
    msgCanRepaly:<select name="msgCanReply">
    	<option value="1">是</option>
    	<option value="0">否</option>
    </select><br>
    msgContent:<textarea rows="" cols="" name="msgContent"></textarea>
    <input type=submit value="提交">
    </form>
  </body>
</html>

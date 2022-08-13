<%@ page contentType="text/html;charset=UTF-8" %> 
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java" %>
<html>
  <head>
    <title>My JSP 'example.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src=message_ajax.js></script>

  </head>
  
 <body onload='makeRequest("/message/msginfo_status.action")'>
    在线消息系统演示  
   
   <div id="msgview" name="msgview"></div>
   
   <a href="msginfo_add.action">add</a>
  </body>
</html>

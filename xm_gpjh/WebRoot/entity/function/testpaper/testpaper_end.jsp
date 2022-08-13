<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>测试结束</title>

  </head>
  
  <body>
    <script type="text/javascript">
    	<s:if test="msg != null and msg != ''">
    		alert("<s:property value='msg'/>");
    	</s:if>
    	top.window.close();
    </script>
  </body>
</html>

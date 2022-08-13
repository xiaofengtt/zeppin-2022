<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script type="text/javascript">
    alert('<s:property value="msg"/>');
     window.close();
   // history.go(-1);
    </script>    
  </head>  
  <body>
   <br>
  </body>
</html>

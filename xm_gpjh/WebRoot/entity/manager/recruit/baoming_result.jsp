<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*;" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>报名结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
<!--
.STYLE2 {
	font-size: 18px;
	font-family: "新宋体";
}
-->
    </style>
</head>
  
  <body>
  <table width="500" border="0" align="center" height="200">
  <tr>
    <td width="30%">&nbsp;</td>
    <td width="40%">&nbsp;</td>
    <td width="30%">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="center"><span class="STYLE2"><s:property value="operateresult"/> </span>
    </td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

  </body>
</html>

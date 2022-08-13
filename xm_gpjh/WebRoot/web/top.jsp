<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
<link href="/web/css/css.css" rel="stylesheet" type="text/css" />    

  </head>

<body onselectstart="return flase">
<table width="1002" border="0" cellspacing="0" cellpadding="0" style="margin-top:4px;">
  <tr>
    <td width="150" align="center"><img src="/web/images/index_03.jpg" width="93" height="49" /></td>
    <td width="850"  align="right">
	<table width="100%" height="30" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" style="margin-top:14px;" >
      <tr>
        <td  width="143" background="/web/images/index_06.jpg" class="topmenu" ><a href="/entity/first/firstInfoNews_toIndex.action" target="_parent">返回首页</a></td>
        <td width="109"  background="/web/images/index_08.jpg" class="topmenu"><a href="#">学院简介</a></td>
        <td width="121" background="/web/images/index_10.jpg" class="topmenu"><a href="/web/help/faq.jsp" target="_parent">招生与入学</a></td>
        <td width="109" background="/web/images/index_12.jpg" class="topmenu"><a href="#">学院教育</a></td>
        <td width="109" background="/web/images/index_14.jpg" class="topmenu"><a href="#">学习园地</a></td>
        <td width="109" background="/web/images/index_16.jpg" class="topmenu"><a href="#">学员天地</a></td>
        <td width="124" background="/web/images/index_18.jpg" class="topmenu"><a href="/web/help/map.jsp" target="_parent">网站导航</a></td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="1002" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="align"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="1002" height="157">
      <param name="movie" value="/web/images/00.swf" />
      <param name="quality" value="high" />
      <embed src="/web/images/00.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="1002" height="157"></embed>
    </object></td>
  </tr>
</table>
</body>
</html>

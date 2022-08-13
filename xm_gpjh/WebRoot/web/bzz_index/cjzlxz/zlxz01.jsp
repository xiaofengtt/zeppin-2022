<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<link href="/web/bzz_index/style/xytd.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body bgcolor="#FFFFFF">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="cjwt_zi1"><img src="images/cjwt_09.gif" width="4" height="6" hspace="6" align="absmiddle"><a href="/web/bzz_index/files/xydjb.xls" target="_blank" style="text-decoration:none;">学员报名信息登记表</a></td>
  </tr>
   <tr>
    <td class="cjwt_zi1"><img src="images/cjwt_09.gif" width="4" height="6" hspace="6" align="absmiddle"><a href="/web/bzz_index/files/bksqb.doc" target="_blank" style="text-decoration:none;">补考申请表</a></td>
  </tr>
   <tr>
    <td class="cjwt_zi1"><img src="images/cjwt_09.gif" width="4" height="6" hspace="6" align="absmiddle"><a href="/web/bzz_index/files/zpmb.doc" target="_blank" style="text-decoration:none;">照片模板</a></td>
  </tr>
   <tr>
    <td class="cjwt_zi1"><img src="images/cjwt_09.gif" width="4" height="6" hspace="6" align="absmiddle"><a href="/web/bzz_index/files/hksqb.doc" target="_blank" style="text-decoration:none;">缓考申请表</a></td>
  </tr>
</table>
</body>
</html>


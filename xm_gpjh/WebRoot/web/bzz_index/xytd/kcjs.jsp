<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
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
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<%
	String title="";
	String note="";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql="";
	sql="select title,note from pe_info_news a where a.fk_news_type_id='_kcjs' and a.flag_news_status='402880a91e4f1248011e4f1c0ab40004' and a.flag_isactive='402880a91e4f1248011e4f1a2b360002' ";
	rs=db.executeQuery(sql);
	if(rs!=null && rs.next())
	{
		title=fixnull(rs.getString("title"));
		note=fixnull(rs.getString("note"));
	}
	db.close(rs);
	if(title.equals(""))
		title="课程介绍";
 %>
<body bgcolor="#FFFFFF">
<table width="699" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="34" background="/web/bzz_index/xytd/images/title.jpg"><table width="100%" height="34" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" class="tit"><%=title %></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td background="/web/bzz_index/xytd/images/bg.jpg"><table width="99%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="con"><%=note %></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="/web/bzz_index/xytd/images/bottom.jpg" width="699" height="15"></td>
  </tr>
</table>
</body>
</html>

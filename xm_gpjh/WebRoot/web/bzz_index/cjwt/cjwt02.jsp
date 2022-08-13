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
<%
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql="select * from pe_info_news a where a.fk_news_type_id='_xycjwt' order by a.report_date";
		rs=db.executeQuery(sql);
		while(rs!=null&&rs.next())
	{
		String title=fixnull(rs.getString("title"));
		String id=fixnull(rs.getString("id"));
 %>
  <tr>
    <td class="cjwt_zi1"><img src="images/cjwt_09.gif" width="4" height="6" hspace="6" align="absmiddle"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<%=id %>" target="_blank" style="text-decoration:none;"><%=title %></a></td>
  </tr>
 <%
 }
 db.close(rs);
  %>
</table>
</body>
</html>


<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<?xml version="1.0" encoding="gb2312"?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/style/xytd.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/css.css" rel="stylesheet" type="text/css" />
<link href="/web/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #FF9000;
	font-weight: bold;
}
.link a{
color:#3399cc;}
-->
</style>
</head>

<body>
	<table width="918" align="center" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td><img src="/web/news/images/news_02.jpg" border="0"></td>
	  </tr>
	  <tr>
		<td background="/web/news/images/news_05.jpg">
			<table width="842" align="center" border="0" cellspacing="0" cellpadding="0">
			 <tr>
    			<td height="56"><iframe id="top" name="top" frameborder="0" width="100%" height="56" scrolling="no" src="/web/bzz_index/top1.jsp"></iframe></td>
  				</tr>
			  <!-- <tr>
				<td height="130" background="/web/news/images/news_11.jpg" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td height="46" align="right"><a href="/entity/first/firstInfoNews_toIndex.action" ><img src="/web/news/images/news_08.jpg" border="0" style="margin-right:10px;"></a></td>
					  </tr>
					  <tr>
						<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							<td width="30%">&nbsp;</td>
							<td width="40%" class="news_title"><s:property value="peInfoNews.title"/></td>
							<td width="30%">&nbsp;</td>
						  </tr>
						</table>
						</td>
					  </tr>  
					  <tr>
						<td align="center"><div class="news_date">发布时间：<s:date name="peInfoNews.reportDate" format="yyyy-MM-dd HH:mm:ss" /> 
						　<span onClick="window.print()" class="print">【打 印】</span>　【点击次数：<s:property value="peInfoNews.readCount"/>】</div></td>
					  </tr> 
					</table>
				</td>
			  </tr> 
			   <tr>
				<td height="46" align="right"><a href="/entity/first/firstInfoNews_toIndex.action" ><img src="/web/news/images/news_08.jpg" border="0" style="margin-right:10px;"></a></td>
			 </tr>-->
			  <tr>
				 <td height="27" background="/web/bzz_index/images/index/index_r5_c007.jpg"> 
                  <div align="left">
                    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="6%">&nbsp;</td>
                        <td width="94%"><font color="#000066" size="3"><strong>首页公告</strong></font></td>
                      </tr>
                    </table>
                  </div></td>
			 </tr>
			  <tr>
				<td class="text_news">
				<div style="font-size:24px" align=center><b><s:property value="bulletin.title"/></b></div><br>
				<div class="news_date" align=center>发布时间：<s:date name="bulletin.publishDate" format="yyyy-MM-dd" />
						　<span onClick="window.print()" class="print">【打 印】</span>　</div>
						<p><s:property value="bulletin.note" escape="false"/></p></td>
			  </tr>
			</table>
		</td>
	  </tr>
	  <tr>
		<td height="78">
			<table width="918" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="10" height="78"><img src="/web/news/images/news_15.jpg" border="0"></td>
				<td background="/web/news/images/news_17.jpg" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td align="center" class="close"><div style="cursor:pointer;" onClick="window.opener=null;window.close()">【关 闭】</div></td>
					  </tr>
					  <tr>
						<td align="center" style="padding-top:4px;"><span class="down">版权所有：生殖健康咨询师培训网 京ICP备05046845号
      <BR>
      投诉：010-62786820 
      传真：010-62789770 技术客服热线电话： 
010-62796475</span></td>
					  </tr>
					</table>
				</td>
				<td width="10" height="78"><img src="/web/news/images/news_20.jpg" border="0"></td>
			  </tr>
			</table>
		</td>
	  </tr>
	</table>
</body>
</html>

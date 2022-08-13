<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<?xml version="1.0" encoding="gb2312"?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<div style="word-break:break-all;">
	<table width="918" align="center" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td><img src="/web/news/images/news_02.jpg" border="0"></td>
	  </tr>
	  <tr>
		<td background="/web/news/images/news_05.jpg">
			<table width="842" align="center" border="0" cellspacing="0" cellpadding="0">
			 <tr>
    			<!--  <td height="56"><iframe id="top" name="top" frameborder="0" width="100%" height="56" scrolling="no" src="/web/bzz_index/top1.jsp"></iframe></td>-->
  				</tr>
			  <tr>
				<td class="text_news">
					<div   align=center><font size="4" style="font-family: 华文新魏"><b>学员申请详细信息</b></font></div><br>
				</td>
			  </tr>
			  <tr>
			  	<td class="text_news" align="left">
			  		申请类型：<s:property value="systemApply.enumConstByApplyType.name"/>
			  	</td>
			  </tr>
			  <tr>
			  	<td class="text_news" align="left">
			  		申请日期：<s:date name="systemApply.applyDate" format="yyyy-MM-dd" />
			  	</td>
			  </tr>
			  <tr>
			  	<td class="text_news" align="left">
			  		申请原因：<s:property value="systemApply.applyInfo"/>
			  	</td>
			  </tr>
			  <tr>
			  	<td class="text_news" align="left">
			  		申请备注：<s:property value="systemApply.applyNote"/>
			  	</td>
			  </tr>
			  <tr>
			  	<td class="text_news" align="left">
			  		审核状态：<s:property value="systemApply.enumConstByFlagApplyStatus.name"/>
			  	</td>
			  </tr>
			  <tr>
			  	<td class="text_news" align="left">
			  		审核日期：<s:date name="systemApply.checkDate" format="yyyy-MM-dd" />
			  	</td>
			  </tr>
			  <tr>
			  	<td class="text_news" align="left">
			  		审核备注：<s:property value="systemApply.checkNote"/>
			  	</td>
			  </tr>
			</table>
		</td>
	  </tr>
	  <tr>
		<td height="78">
			<table width="1002" border="0" cellspacing="0" cellpadding="0">
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
      </span></td>
					  </tr>
					</table>
				</td>
				<td width="10" height="78"><img src="/web/news/images/news_20.jpg" border="0"></td>
			  </tr>
			</table>
		</td>
	  </tr>
	</table>
</div>
</body>
</html>

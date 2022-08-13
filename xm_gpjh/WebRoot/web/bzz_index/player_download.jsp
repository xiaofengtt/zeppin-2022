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
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body onLoad="MM_preloadImages('/web/bzz_index/images/btn_011.jpg','/web/bzz_index/images/btn_021.jpg','/web/bzz_index/images/btn_031.jpg','/web/bzz_index/images/btn_041.jpg','/web/bzz_index/images/btn_051.jpg','/web/bzz_index/images/btn_061.jpg','/web/bzz_index/images/btn_071.jpg','/web/bzz_index/images/btn_081.jpg','/web/bzz_index/images/btn_091.jpg')">
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
				<td height="46" align="left"><a href="/entity/first/firstInfoNews_toIndex.action" ><img src="/web/news/images/news_08.jpg" border="0" style="margin-right:10px;"></a></td>
			 </tr>-->
			  <tr>
				 <td height="27" background="/web/bzz_index/images/index/index_r5_c007.jpg"> 
                  <div align="left">
                    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="6%">&nbsp;</td>
                        <td width="94%"><font color="#000066" size="3"><strong>常用软件下载</strong></font></td>
                      </tr>
                    </table>
                  </div></td>
			 </tr>
			  <tr>
				<td class="text_news" align=left>
				<b><font size=2>名称：Windows Media Player9.0（简体中文版）</font></b><font color="#FF6633"></font><br><br>
            <a href="/web/bzz_index/files/mp9setup.exe">点击下载（用于 Win 98 SE、Me 和 2000）</a><br><br>
            介绍：安装后即可观看视频多媒体课件
            </td>
			  </tr>
			  
			   <tr>
				<td class="text_news" align=left>
				<b><font size=2>名称：Media Player10.0（简体中文版）</font></b><font color="#FF6633"></font><br><br>
            <a href="/web/bzz_index/files/MP10Setup_CN.exe">点击下载（适用于Win XP）</a><br><br>
            介绍：安装后即可观看视频多媒体课件</td>
			  </tr>
			  
			   <tr>
				<td class="text_news" align=left>
				<b><font size=2>名称：Flash插件</font></b><font color="#FF6633"></font><br><br>
            <a href="/web/bzz_index/files/flash10.exe">点击下载（适用于Win98/ME/2000/XP/vista）</a><br><br>
            介绍：安装后即可观看视频多媒体课件</td>
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
010-58731118转254</span></td>
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

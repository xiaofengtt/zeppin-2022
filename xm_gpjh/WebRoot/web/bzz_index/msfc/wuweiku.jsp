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
<link href="/web/bzz_index/style/msfc.css" rel="stylesheet" type="text/css">
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
			 <tr>
				 <td height="27" background="/web/bzz_index/images/index/index_r5_c007.jpg"> 
                  <div align="left">
                    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="6%">&nbsp;</td>
                        <td width="94%"><font color="#000066" size="2"><strong>
                        名师风采
			</strong></font></td>
                      </tr>
                    </table>
                  </div></td>
			 </tr>
			  <tr>
				<td background="/web/news/images/news_11.jpg" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					 <!--  <tr>
						<td height="46" >
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="30" height="30"><img src="/web/bzz_index/msfc/images/msfc_11.jpg" border="0"></td>
			<td width="100" class="ms_title1">
			<s:if test="peInfoNews.peInfoNewsType.id=='_yxjs'">名师风采</s:if>
			<s:else>优秀班组长</s:else> </td>
    <td align="right"> <a href="/entity/first/firstInfoNews_toIndex.action" ><img src="/web/news/images/msfc_pic_03.jpg" border="0" usemap="#Map" style="margin-right:10px;"></a></td>
  </tr>  
</table>
                       </td>
					  </tr>  -->
					   <tr>
					  <td><br></td>
					  </tr>
					  <tr>
						<td>
						<table width="100%" style="height:21px; background:#e3e3e5;"  border="0" cellspacing="0" cellpadding="0">
						  <tr>
							<td align="center">
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                               <td width="80" id="author">吴维库</td>
                               <td id="zw">清华大学经济管理学院企业战略与政策系教授，国内知名领导力专家。</td>
                              </tr>
                            </table>
</td>
						 </tr>
						</table>
						</td>
					  </tr>
					</table>
				</td>
			  </tr>
			  <tr>
				<td class="text_news"><br>
				<div id="pic"><img src="/web/bzz_index/msfc/images/wuweiku1.jpg" width="192" height="287"></div>
              <h5>1、简介</h5>   
   <p>  吴维库，清华大学经济管理学院企业战略与政策系教授，国内知名领导力专家。</p>
<h5>2、教学成果与理论实践  </h5>
   <p>主要研究领域：企业战略、竞争战略、企业领导学、价值观与领导、情商与领导。讲授课程：战略管理、领导学。
</p>
                </td>
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

<map name="Map"><area shape="rect" coords="86,4,140,17" href="/entity/first/firstInfoNews_toIndex.action">
<s:if test="peInfoNews.peInfoNewsType.id=='_yxjs'">
<area shape="rect" coords="9,3,68,18" href="/web/bzz_index/msfc/msfc.jsp">
</area></s:if>
<s:else><area shape="rect" coords="9,3,68,18" href="/web/bzz_index/yxbzz/yxbzz.jsp"></s:else>
</map></body>
</html>

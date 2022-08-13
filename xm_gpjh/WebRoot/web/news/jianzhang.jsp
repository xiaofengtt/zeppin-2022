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
    
    <title>生殖健康咨询师培训网</title>
    <link href="/web/css/css.css" rel="stylesheet" type="text/css" />
  </head>
  

<body >
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="newsbg">
  <tr>
    <td>
	<table width="703" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
  <tr>
    <td  width="703" height="210" valign="top"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="703" height="163">
      <param name="movie" value="/web/news/images/02.swf" />
      <param name="quality" value="high" />
      <embed src="/web/news/images/02.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="703" height="163"></embed>
    </object>
      <table width="703" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td  valign="top" width="703" height="11"><img src="/web/news/images/1_05.jpg" width="703" height="11" /></td>
        </tr>
        <tr>
          <td valign="top" width="670" height="100%"  style="background:url(/web/news/images/1_07.jpg); background-repeat:repeat-y;" >
		  <table width="670" border="0" cellspacing="0" cellpadding="0" style="margin:2px 15px;">
		  <tr>
			<td class="positions">当前位置：<a href="/entity/first/firstInfoNews_toIndex.action"><font class="positions">首页</font></a>>>   
			招生简章</td>
		  </tr>
		  <tr>
			<td align="center">
			<table width="670" border="0" cellpadding="0" cellspacing="0" background="/web/news/images/1_12.jpg">
              <tr>
                <td width="4%" align="left"><img src="/web/news/images/1_1.jpg" width="18" height="30" /></td>
                <td width="96%" class="newsbtitle" align="left"><s:property value="jianzhang.name"/></td>
              </tr>
            </table></td>
		  </tr>
		  <tr>
			<td align="right" class="secondtitle"><s:date name="jianzhang.creatDate" format="yyyy-MM-dd" />  【<a href="#"><span class="orange">打印</span></a>】</td>
		  </tr>
		  <tr>
			<td style="padding-top:5px;">
        	<FONT class="newsp"><s:property value="jianzhang.jianzhang" escape="false"/></FONT>
			<p align="center"><span style="color:#777777; font-size:12px; font-family:'宋体'; line-height:27px;">
			【<span><a href="#" onClick="javascript:window.close()" style="color:#FE7E01; text-decoration:none;">关闭</a></span>】
			</span></p></td>
		  </tr>
		</table>

		  </td>
        </tr>
        <tr>
          <td  valign="top" width="703" height="14"><img src="/web/news/images/1_08.jpg" width="703" height="14" /></td>
        </tr>
      </table></td>
  </tr>
  
  <tr>
    <td width="703"  height="68" valign="top" style="background-image:url(/web/news/images/1_11.jpg); background-repeat:repeat-x;" align="center" class="banquan">版权所有：生殖健康咨询师培训网 京ICP备05046845号<br />
投诉：010-62786820 传真：010-62789770 <br/>
技术客服热线电话： 010-58731118转254
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>

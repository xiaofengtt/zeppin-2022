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
<style type="text/css">
<!--
.STYLE1 {font-size: 12px}
-->
</style>
  </head>
  

<body >
<table width="1002" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
  <tr>
    <td  width="1002" height="210" valign="top"><iframe id="top" name="top" frameborder="0" scrolling="no" src="/web/top.jsp" width="1002" height="210"></iframe></td>
  </tr>
  <tr>
    <td width="1002"  height="100%" valign="top" align="center">
	<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:10px 0px 3px 0px;">
      <tr>
        <td><table width="980" border="0" height="15" cellpadding="0" cellspacing="0" background="/web/news/images/04.jpg">
          <tr>
            <td width="104" valign="top">学生邮箱</td>
            <td width="798" align="right">
			&nbsp;<table width="280" border="0" cellspacing="0" cellpadding="0" style=" margin-right:5px; margin-bottom:3px;">
              <tr>
                <td width="80" class="orangefont" align="right"></td>
                <td width="140">&nbsp;</td>
                <td width="60" align="left" style="padding-left:2px;" ></td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td> <TABLE cellSpacing=0 cellPadding=0 align="center" width=750 border=0>
        <TBODY>
        <TR>
          <TD width=47>&nbsp;</TD>
          <TD vAlign=top width=374><IMG height=154 src="/web/mail/images/1-02.jpg" 
            width=374 useMap=#Map border=0></TD>
          <TD width=299>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" align=center 
border=0>
              <TBODY>
              <TR>
                <TD align="left" vAlign=bottom><B><BR>
                  <span class="STYLE1">进入邮箱前请先仔细阅读此说明：                  </span></B><span class="STYLE1"><BR>
                  一、Web页面登录，即通过浏览器(Internet<BR>　　
                  Explorer)查看信件(<A 
                  href="/web/mail/web.jsp" target=_blank><FONT 
                  color=#0000ff><U><FONT 
                  color=#ff0000>点击进入查看详细说明</FONT></U></FONT></A>)<BR>
                  二、通过Outlook或Outlook 
                  Express来收取信件<BR>
                  (<A href="/web/mail/oe.jsp" 
                  target=_blank><U><FONT 
                  color=#ff0000>点击进入查看详细说明</FONT></U></A>)<BR>
                  <BR>
                  <B>注意事项：</B> 
                  </span>
                  <P class="STYLE1">　1.以web方式查看邮件，在使用后必须将所有浏<BR>　　览器窗口关闭；<BR>　2.以web方式查看邮件，查看信件如果有乱码现<BR>　　象时：请双击信件后，点击按"web页查看"；<BR>　3.操作系统的版本：<BR>　　Microsoft 
                  Windows98第二版4.10.2222A、<BR>　　Microsoft Windows Me 
                  4.90.3000、<BR>　　Microsoft Windows 2000 版本5.0、<BR>　　Microsoft 
                  Windows XP 版本5.1</P>
                  <span class="STYLE1"><FONT 
                  color=#ff0000>邮箱服务器地址：<BR>
                  SMTP：mail.gdou.com，POP3：mail.gdou.com</FONT>                  </span></TD>
              </TR></TBODY></TABLE></TD>
          
          <TD vAlign=bottom width=28 rowSpan=2>&nbsp;</TD>
        </TR>
        <TR>
          <TD>&nbsp;</TD>
          <TD>&nbsp;</TD>
          <TD colSpan=3>&nbsp;</TD>
        </TR></TBODY></TABLE></TD>
    
      </TR></TABLE>

</td>
  </tr>
  <tr>
    <td width="1002"  height="68" valign="top">
	<iframe id="footer" name="footer" frameborder="0" scrolling="no" src="/web/footer.jsp" width="1002"  height="68" ></iframe>	</td>
  </tr>
</table>

<map name="Map" id="Map">
<AREA shape=RECT target=_blank alt=进入GDOU邮箱 coords=187,59,294,86 
  href="http://mail.gdou.com/exchange" />
</map>
</body>
</html>

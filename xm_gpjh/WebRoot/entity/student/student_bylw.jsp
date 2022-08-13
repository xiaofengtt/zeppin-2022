<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
response.setHeader("expires", "0");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎光临华南师范大学现代远程教育平台</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {font-size: 14pt}
-->
</style>
</head>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">毕业论文资料</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
               
			   <tr>
			   	<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
			   </tr>
			   <tr> 
                <td valign="top" class="postFormBox">&nbsp;</td>
                <td height="28" valign="middle" class="postFormBox"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					 <td width="5%" class="postFormBox" >&nbsp;</td>
                    <td align="center" class="postFormBox STYLE1">资料主题</td>
                    <td class="postFormBox STYLE1" width="20%"  align="center">发布时间</td>
				</tr>
				<s:iterator value="infoPage.getItems()">
					<tr>
                    <td width="5%" class="postFormBox" >&nbsp;</td> 
                    <td class="postFormBox">
                      <a href='/info/firstPage_getAnnounceDetial.action?id=<s:property value="getId()"/>&property=student' class="time" style="padding-left:19px" target="_blank">
                      <s:property value="getTitle()"/></a>
                    </td>
                    <td class="postFormBox" width="20%" valign="top" class="time" style="padding-left:19px" align="center" >
                    	<s:property value="getDisplayDate()"/>
                    </td>
                  </tr>
				</s:iterator>
                </table></td>
                <td valign="top" class="postFormBox">&nbsp;</td>
              </tr>
              <tr align="center" valign="bottom" class="postFormBox">
                <td colspan="3" class="postFormBox">
                	<%@ include file="/entity/student/student_bylw_dividepage.jsp" %>
       			</td>
              </tr>             
                       
        </table>

	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
 
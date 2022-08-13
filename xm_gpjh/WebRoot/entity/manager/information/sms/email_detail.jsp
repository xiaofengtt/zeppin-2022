<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>详细内容</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">

  </head>
  
  <body style="font-family:Arial;">
<div id="main_content">
    <div class="content_title">详细内容</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  		<table align="center" border="0" width="700">
				<tr><td height="25" align="center" class="topTitleText"><s:property value="bean.title"/> </td></tr>
				<tr><td class="tab_dc_Mid"  align="right">发送时间：<s:date name="bean.sendTime" format="yyyy-MM-dd"/> &nbsp; </td></tr>
				<tr><td  class="oddrowbg2" height="500"><p><s:property value="bean.content" escape="false"/></p></td></tr>
				<s:if test="bean.attachments != null">
					<s:set name="attachments" value="bean.attachments.split(';')"/>
					<tr><td height="20"> 附件: <br/>
						<s:iterator value="#attachments" id = "attachment">
							<a style="text-decoration:underline;padding-left:20px" href="<s:property value='#attachment'  escape='false'/>"><s:property value="#attachment.substring(#attachment.lastIndexOf('/')+1)" escape='false'/></a><br/>
						</s:iterator>
					</td></tr>
				</s:if>
			</table>
	  </div>
	<div align="center"><img src="/entity/student/images/close.gif" title="关闭" style="cursor: hand" onclick="javascript:window.close();" ></div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>

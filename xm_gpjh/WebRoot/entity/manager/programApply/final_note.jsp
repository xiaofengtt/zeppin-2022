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
  
  <body>
<div id="main_content">
    <div class="content_title">查看终审意见</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  		<table align="center" border="0" width="700">
				
				<tr><td  class="oddrowbg2" height="500"><p>
				<s:if test="peProApply.enumConstByFkCheckFinal.code=='1000'">该项目尚未终审！</s:if>
				<s:else>
				<s:property value="peProApply.noteFinal" escape="false"/>
</s:else>
</p></td></tr>
			</table>
	  </div>
	<div align="center"><img src="/entity/student/images/close.gif" title="关闭" style="cursor: hand" onclick="javascript:window.close();" ></div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>

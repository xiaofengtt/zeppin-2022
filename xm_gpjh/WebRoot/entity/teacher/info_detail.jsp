<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>通知公告</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
  </head>
  
<body>
<div id="main_content">
    <div class="content_title">您当前的位置：教师<s:property value="peTeacher.trueName"/>的工作室 > 通知公告 > 通知公告内容</div>
	
    <div class="cntent_k">
   	  <div class="k_cc">
	  		<table align="center" border="0" width="700">
				<tr><td height="25" align="center" class="topTitleText"><s:property value="bean.title"/></td></tr><br>
				<tr><td class="tab_dc_Mid"  align="right">发布时间：<s:date name="bean.publishDate" format="yyyy-MM-dd" /> &nbsp; 发布人：<s:property value="bean.peManager.trueName"/></td></tr>
				<tr><td   class="oddrowbg2" height="200"><p><s:property value="bean.note" escape="false"/>
</p></td></tr>
				<tr><td   align="center"><a href="javascript:window.history.back();">[返回]</a></td></tr>
			</table>
	  </div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>

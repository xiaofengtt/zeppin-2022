<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎光临华南师范大学现代远程教育平台</title></title></title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">

</head>
<body>
<div id="main_content">
    <div class="content_title">通告信息内容</div>
	
    <div class="cntent_k">
   	  <div class="k_cc">
	  		<table align="center" border="0" width="700">
				<tr><td height="25" align="center" class="topTitleText"><s:property value="infonews.title" /></td></tr><br>
				<tr><td class="tab_dc_Mid"  align="right">发布时间：<s:property value="infonews.reportDate"/> &nbsp; 发布人：<s:property value="infonews.reporter"/></td></tr>
				<tr><td   class="oddrowbg2" height="200"><s:property value="infonews.body" escape="false" /></td></tr>
				<tr><td   align="center"><a href="javascript:window.history.back();">[返回]</a></td></tr>
			</table>
	  </div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>

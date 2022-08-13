<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="<%=request.getContextPath() %>/admin/css.css" rel="stylesheet" type="text/css">
<title>权限管理</title>

</head>
<script language="javascript">
   
   function pageGuarding()
   {  
   	  var str = document.change.upload.value;
   	  if(str == null || str==""){
   	  	alert('请选择导入文件!');
   	  	document.change.upload.focus();
   	  	return false;
   	  }
   	  var per = str.substring(str.length-3,str.length);
   	  if(per!='xls'){
   	  	alert('您选择的不是标准excel文档!');
   	  	document.change.upload.focus();
   	  	return false;
   	  }
      document.change.submit();
   }

</script>
<body>
<s:actionmessage theme="bbs0"/>
<form action="/sso/admin/siteManagerOper_updateMobileBatch.action" method=post name="change" ENCTYPE="multipart/form-data" >

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%" colspan="2" align="center">批量上载移动电话号码</td>
	
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">下载模板:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"> <span class="link" help="下载Excel格式的模版表格。">&nbsp;<a href='<%=request.getContextPath() %>/admin/MobileSample.xls' target=_blank>Excel报表</a></span></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">上载：</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><s:file id="upload" name="upload" theme="simple"/> &nbsp;</td>
</tr>
</table>
<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="#" onclick="javascript:pageGuarding();">确定</a> &nbsp;&nbsp; <a href="/sso/admin/siteManagerOper_showManagerList.action">返回</a></div>

</form>
</body>
</html>
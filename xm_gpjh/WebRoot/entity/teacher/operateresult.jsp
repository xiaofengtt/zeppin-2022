<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作结果</title>
<link href="/entity/teacher/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="main_content">
	<div class="content_title">您当前的位置是：教师<s:property value="peTeacher.getTrueName()"/>的工作室 &gt;<span class="table_bg1">操作结果</span></div>
    <div class="student_cntent_k">
    	<div class="k_cc">
    
		<table width="100%" border="0">
  <tr>
    <td class="table_bg1"><div align="center"><s:property value="operateresult"/>&nbsp;</div></td>
  </tr>
  <tr>
  	<td>
		<input type="button" value="返回" onClick="javaScript:window.location.href='<s:property value="backAction"/>'"/>
  	</td>
  </tr>
  
</table>

	
    </div>
  </div>
</div>
</body>
</html>

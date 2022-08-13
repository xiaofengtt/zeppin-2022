<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<title>监考人员自动安排</title>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<!-- JavaScript functions -->
<body>
<form action="/entity/recruit/examInspector_autoInspector.action">
<!--内容区-->
<div id="main_content">
    <div class="content_title">监考人员自动安排</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  	监考人员自动安排将会覆盖之前已分配的结果，是否继续？
		<br />
		<center><input type="submit" value="确认" /></center>
		
		
	  </div>
    </div>
</div>
<div class="clear"></div>
<div>
  <div> </div>
</div>
<!-- JavaScript onload -->
</form>
</body>
</html>

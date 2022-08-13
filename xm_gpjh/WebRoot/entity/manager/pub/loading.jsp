<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<title>无标题文档</title>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<!-- JavaScript functions -->
<body>
<!--内容区-->
<div id="main_content">
    <div class="content_title">提示信息</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  	<p>程序正在加载，请稍候...</p>
	  	<p>&nbsp;</p>
	  	<p class="STYLE1">注：此页面可应用于加载需要较长时间的页面。页面样式待美化</p>
   	  </div>
    </div>
</div>
<div class="clear"></div>

<!-- JavaScript onload -->

</body>
</html>

<script>
	window.location="<%out.print(request.getParameter("url"));%>";
</script>

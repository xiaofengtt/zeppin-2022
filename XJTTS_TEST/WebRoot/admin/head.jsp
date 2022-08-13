<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>新疆教师培训信息化管理系统</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="../css/bootstrap.css">
<!--[if lt IE 9]>
  <script src="../js/html5shiv.js"></script>
  <script src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../css/app.css">	
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../css/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script> 
 <script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?0133cc6005cf2c1313355bace07f7a4d";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
 
</head>
<body>
<!--[if lt IE 8]>
	<div class="browserhappy"><div class="inn">新疆教师培训信息化管理系统，是北京知鹏汇仁科技有限公司(<a href="http://www.zeppin.cn">zeppin.cn</a>)基于最新web技术为新疆开发的一套系统，您的浏览器无法完全支持我们的页面，建议<a href="http://browsehappy.com/">升级您的浏览器</a>，以获得良好体验的最新版本!</div></div>
	<div class="browserhappyoverlay"></div>
<![endif]-->
<!--Header-part-->
<div class="navbar" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="logo" title="新疆维吾尔自治区 教师培训信息化管理系统" ><img src="../img/logo.png" height="41"></a>
    </div>
	<p class="logout"><span>欢迎<s:property value="organization.name"/><s:property value="userType" /> &nbsp;&nbsp;<strong><s:property value="username" /></strong>&nbsp;&nbsp;</span> <s:property value="currentTime"  /> &nbsp;|&nbsp;  <a href="../login_modifyPassword.action" data-fancybox-type="iframe" class="text text-modifyPasswd">修改密码</a> &nbsp;|&nbsp;  <a href="../login_logout.action">退出</a> </p>	
	
  </div>
</div>
<!--close-Header-part--> 

<div id="container" class="container-fluid">

	<div class="row">
	<script type="text/javascript">
$(function() {//添加按钮
		$(".text-modifyPasswd,.ifrmbox").colorbox({
			iframe : true,
			width : "400px",
			height : "350px",
			opacity : '0.5',
			escKey : true
		});
	})
</script>
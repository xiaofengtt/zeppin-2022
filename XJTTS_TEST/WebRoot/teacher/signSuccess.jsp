<%@page import="org.apache.struts2.components.Include"%>
<%@page import="cn.zeppin.action.sso.UserSession"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>个人主页</title>

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
<script type="text/javascript">
	var timeout=10;
	function show(){
		var showbox;
		showbox = $(".showbox");
		showbox.html(timeout);
		timeout--;
		if(timeout == 0){
			window.opener = null;
			window.open('','_self'); 
			window.close();
		}else{
			setTimeout("show()", 1000);
		}
	}
		
	</script>
</head>
<body onload="show();">

	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="left.jsp"></jsp:include>
	<div align="center">
		<h1>报名成功</h1>
	</div>
	<br /><br /><br />
	<div align="center">
		当前页面将在<span class="showbox"></span>秒后关闭
	</div>
	
	<jsp:include page="foot.jsp"></jsp:include>
	
</body>
</html>
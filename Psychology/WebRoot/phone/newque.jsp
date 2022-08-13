<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>心理测评</title>
<jsp:include page="head.jsp"></jsp:include>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/m/chartist.min.css">
<script src="../js/m/chartist.min.js"></script>
</head>
<body>

	<!-- Make sure all your bars are the first things in your <body> -->
	<header class="bar bar-nav bar-nav-gs">
		<a class="pull-left" href="../phone/myMyIndex"><span class="icon icon-left-nav"></span></a>
		<h1 class="title">
			<img class="logo" src="../img/m/logo-text.png" alt="果实网">
		</h1>
	</header>
	
	<div class="content">
		
     	
     	
     	<div class="chart-padded">
     		<div class="medal-hd" style="color:#32cea5;margin:10px 0;">新增提问：</div>
     		<form action="../phone/myAddQuestion">
			  <input name="text" type="text" placeholder="我有问题">
			  
			  <button class="btn btn-positive btn-block">提交</button>
			</form>
     	</div>
     	
     	
     	
      
    </div>
	
</body>
</html>
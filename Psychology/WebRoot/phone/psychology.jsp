<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="cn.zeppin.entity.User" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>心理测评</title>
<jsp:include page="head.jsp"></jsp:include>
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="../css/m/chartist.min.css">
<script src="../js/m/chartist.min.js"></script>
</head>
<body>
	<!-- Make sure all your bars are the first things in your <body> -->
	<header class="bar bar-nav bar-nav-gs">
		<a class="pull-left" style="z-index:20;position:relative" href="../phone/myMyIndex"><span class="icon icon-person"></span> 
			<% out.print(((User)session.getAttribute("userphone")).getName());  %>
		</a>
		<h1 class="title">
			<img class="logo" src="../img/m/logo-text.png" alt="果实网">
		</h1>
	</header>
	
	<div class="content">
     <div class="step">心理素质测评</div>
	 
	 <ul class="table-view">
	   <li class="table-view-cell media">
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=159&type=31"  data-transition="slide-in">
		   <span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
	       <div class="media-body">
	         	学生人际关系测评
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media">
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=344&type=32" data-transition="slide-in">
	     
	    	<span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
			 
	       <div class="media-body">
	         	学生心理健康程度测评
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media">
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=348&type=33" data-transition="slide-in">
	     	<span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
	      
	       <div class="media-body">
	         	学生逆反心理测评
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media"> 
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=344&type=34" data-transition="slide-in">
	     <span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
	      
	       <div class="media-body">
	         	学生挫折承受能力测评
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media"> 
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=348&type=35" data-transition="slide-in">
	     <span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
	      
	       <div class="media-body">
	         	学生压力承受能力测评
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media"> 
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=344&type=36" data-transition="slide-in">
	     <span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
	      
	       <div class="media-body">
	         	学生情绪稳定力
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media"> 
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=348&type=37" data-transition="slide-in">
	     <span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
	      
	       <div class="media-body">
	         	学生抑郁能力测评
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media"> 
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=344&type=38" data-transition="slide-in">
	     <span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
	      
	       <div class="media-body">
	         	学生心理适应性测评
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media"> 
	     <a class="navigate-right" href="../phone/phPaperPsychology?paper.id=348&type=39" data-transition="slide-in">
	     <span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
	      
	       <div class="media-body">
	         	学生焦虑程度测评
	       </div>
	     </a>
	   </li>
	  
	 </ul>
	  
	  <footer>
	  	<div class="footer-copyright">&copy; 2014 中国教育电视台  京ICP证101020号</div>
	  </footer>
      
    </div>
	
</body>
</html>
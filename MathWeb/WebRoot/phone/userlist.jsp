<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>安全意识测评</title>
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
     		<div class="medal-hd" style="color:#32cea5;margin:10px 0;">好有列表
     			
     		</div>
     		<ul class="table-view">
     		
     			<s:iterator value="userFriends" id="uf"> 
     			
     				<li class="table-view-cell media">
                        <a href="#">
                         <div class="pull-left">
                               <div class="icon icon-person" style="font-size:40px;color:#32cea5;margin-right:10px;"></div> 
                         </div>
                         
                          <div class="media-body">
                          	<s:property value="userByFriend.name"/>
                          </div>
                         
                        </a>
                     </li>
     			
     			</s:iterator>
     		
     			
			</ul>
     	</div>
          
          
          
    </div>
    
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>英语测评</title>
<jsp:include page="head.jsp"></jsp:include>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/m/chartist.min.css">
<script src="../js/m/chartist.min.js"></script>
</head>
<body>

	<!-- Make sure all your bars are the first things in your <body> -->
	<header class="bar bar-nav bar-nav-gs">
		<a class="pull-left" href="../phone/chart.jsp"><span class="icon icon-left-nav"></span></a>
		<a class="pull-right" href="../phone/myGetFriends"><span class="icon icon-list"></span></a>
		<h1 class="title">
			<img class="logo" src="../img/m/logo-text.png" alt="果实网">
		</h1>
	</header>
	
	<div class="content">
		<div class="text-center">
			<div class="icon icon-person" style="font-size:60px;color:#32cea5;margin:15px 0 10px"></div> 
	     	<p style="color:#32cea5;"><s:property value='currentUser.name' />(<s:property value='ranktitle' />)</p>
     	</div>
     	
     	<div class="chart-padded">
     		<div class="medal-hd" style="color:#32cea5;margin:10px 0;">我的勋章
     			
     		</div>
     		<ul class="table-view">
     			
     			<s:iterator value="medalExs" id="medal" >
     				<li class="table-view-cell" style="padding-right:15px">
			          <div class='<s:property value="#medal.type" />'>
							<span><s:property value="#medal.name" /></span>
						</div>
			        </li>
     			</s:iterator>
		        
		      </ul>
     	</div>
     	
     	<div class="chart-padded">
     		<div class="medal-hd" style="color:#32cea5;margin:10px 0;">我的提问
     			<a href="../phone/newque.jsp" class="btn btn-positive pull-right" style="margin-right:15px;">提问</a>
     		</div>
     		<ul class="table-view">
     			<s:iterator value="listQuestion" id="que" status="index">
     				<li class="table-view-cell">
     					<a href="../phone/myAnsque?qid=<s:property value='id' />"><s:property value="#index.index+1" />.<s:property value="content" /> <span style="right:65px" class="badge"><s:property value="count" /></span>
     						<button class="btn btn-answer">回答</button>
     					</a>
     				</li>
     			</s:iterator>
			</ul>
     	</div>
     	
      
    </div>
	
</body>
</html>
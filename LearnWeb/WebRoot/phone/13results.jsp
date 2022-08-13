<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>分析结果</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body>

	<!-- Make sure all your bars are the first things in your <body> -->
	<header class="bar bar-nav bar-nav-gs">

		<a class="btn btn-link btn-nav purple pull-left"
			data-transition="slide-out" href="../phone/learnAbility.jsp"> <span
			class="icon icon-left-nav"></span> 返回首页
		</a>

		<h1 class="title purple">测评分析</h1>
	</header>

	<!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
	<div class="content">
		<div class="padded">
			<s:if test="%{currentUserTest.isMedal()==1}">
				<div class="learn-medal">
					<span><s:property value='currentUserTest.medalName' /> </span>
				</div>
			</s:if>
			<s:else>
				<div class="bad">
					<img src="../img/m/sad.png" alt><br> <br>
					<s:property value='currentUserTest.medalString' />
				</div>
			</s:else>
		</div>

		<footer>
			<div class="footer-copyright">&copy; 2014 中国教育电视台 京ICP证101020号</div>
		</footer>

	</div>



</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<!-- <script type="text/javascript" src="https://cdn.webfont.youziku.com/wwwroot/js/wf/youziku.api.min.js"></script>
		<link href='https://cdn.webfont.youziku.com/webfonts/nomal/27029/47558/58ecabb2f629d80dbc71b3c2.css' rel='stylesheet' type='text/css' /> -->
	</head>
	<body>
		<div class="contain-left">
			<!-- 用户信息 -->
			<div class="userInfo">
				<a class="btn-user-edit" href="currentOperatorDetail.jsp?uuid=${sessionScope.currentOperator.uuid}">
					<img class="img-circle" src="img/userBig.png">
				</a>
				<p id="adminName">${sessionScope.currentOperator.realname}</p>
			</div>
			<ul id="menu">

			</ul>
			<div class="realTime cssa01a7f8496995 text-center"><span class="data"></span><span class="hours"></span></div>
		</div>

		<script type="text/javascript" src="js/contentLeft.js" ></script>
	</body>
</html>

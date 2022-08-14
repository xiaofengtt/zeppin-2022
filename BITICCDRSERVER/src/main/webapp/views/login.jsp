<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<meta charset="utf-8"/>
		<title>CDR</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
		<meta content="" name="description"/>
		<meta content="" name="author"/>
		<meta name="MobileOptimized" content="320">
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link href="../views/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<link href="../views/css/style.css" rel="stylesheet" type="text/css"/>
		<link href="../views/css/login.css" rel="stylesheet" type="text/css"/>
	</head>
	<body class="login">
		<div class="content">			
			<!-- BEGIN LOGIN FORM -->
			<form:form id="loginsubmit" class="login-form" action="../rest/backadmin/operator/login" method="post">
				<input type="hidden" id="time" name="time" value="" />
				<div class="alert alert-danger display-hide">
					<button class="close" data-close="alert"></button>
					<span>
						 输入您的用户名和密码
					</span>
				</div>
				<div class="form-group">
					<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
					<label class="control-label visible-ie8 visible-ie9">用户名</label>
					<div class="input-icon">
						<i class="fa user"></i>
						<input name="loginname" id="loginname" size="25" value="" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">密码</label>
					<div class="input-icon">
						<i class="fa lock"></i>
						<input name="password" id="password" size="25" value="" class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" />
					</div>
				</div>
				<div >
					<button id="loginBut" type="submit" class="btn">登录</button>
				</div>
			</form:form>
		</div>

		<script src="../views/js/jquery-2.2.4.min.js" type="text/javascript"></script>
		<script src="../views/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="../views/js/jquery.cokie.min.js" type="text/javascript"></script>
		<script src="../views/js/app.js" type="text/javascript"></script>

		<script>
			jQuery(document).ready(function() {
				//自动跳转
				<%if(session.getAttribute("username") != null){%>
					window.location.href="../bjitic/default.jsp"
				<%}%>
			
			  App.init();
			  $("body").height($(window).height());
			});
			//捕获回车键
			  $('html').bind('keydown',function(e){
			      if(e.keyCode==13){
			       $('#loginsubmit').submit();
			      }
			  });
			$('#loginsubmit').submit(function() {
				var form = $(this)
				var time = new Date().getTime();
				$('#time').val(time);
				$.post(form.attr("action"),form.serialize(), function(data) {
					if (data.status == "SUCCESS") {
						window.location.href="../bjitic/default.jsp"
					} else {
						alert(data.message);
					}
				})
				return false;
			});
		</script>
	</body>
</html>

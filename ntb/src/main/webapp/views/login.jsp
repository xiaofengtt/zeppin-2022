<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<meta charset="utf-8"/>
		<title>牛投邦管理平台</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
		<meta content="" name="description"/>
		<meta content="" name="author"/>
		<meta name="MobileOptimized" content="320">
		
		<link href="../views/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<link href="../views/css/style.css" rel="stylesheet" type="text/css"/>
		<link href="../views/css/login.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="http://cdn.webfont.youziku.com/wwwroot/js/wf/youziku.api.min.js"></script>
	</head>
	<body class="login">
		<div class="content">
			<div class="img-circle"></div>
			<img class="logoLogin" src="../backadmin/img/logoLogin.png" alt="logo" />
			<!-- BEGIN LOGIN FORM -->
			<form:form id="loginsubmit" class="login-form" action="../rest/backadmin/operator/login" method="post">
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
			
				<div class="chknumber form-group" style="text-align:left;">
					<input class="form-control placeholder-no-fix" style="width:49%;display:inline-block;padding:16px 0 12px 11px" name="kaptcha" type="text" maxlength="5" placeholder="验证码"> 
					<img style="vertical-align:middle;cursor:pointer;height:50px;border-radius:5px !important;display:inline-block;" src="../rest/backadmin/operator/kaptchaImage" id="kaptchaImage">  
				</div>
				<div >
					<button id="loginBut" type="submit" class="btn">登录</button>
				</div>
			</form:form>
			<div class="copyright">
			 	<p>Copyright&copy;2016&nbsp;华夏财富（北京）科技有限公司&nbsp;版权所有</p>
				<p>备案许可证号：京ICP备15016518号</p>
			</div>
		</div>
		
		<script src="../views/js/jquery-2.2.4.min.js" type="text/javascript"></script>
		<script src="../views/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="../views/js/jquery.cokie.min.js" type="text/javascript"></script>
		<script src="../views/js/app.js" type="text/javascript"></script>
		
		<script>
			jQuery(document).ready(function() {     
			  App.init();
			  $("body").height($(window).height());
			});
			$('#kaptchaImage').click(function () {
		 		$(this).hide().attr('src', '../rest/backadmin/operator/kaptchaImage').fadeIn();
			});
			//捕获回车键
			  $('html').bind('keydown',function(e){
			      if(e.keyCode==13){
			       $('#loginsubmit').submit();
			      }
			  });
			$('#loginsubmit').submit(function() {
				var form = $(this)
				$.post(form.attr("action"),form.serialize(), function(data) {
					if (data.status == "SUCCESS") {
						if(data.message == 'unopen'){
							window.location.href="../backadmin/operatorOpen.jsp?uuid="+data.data.uuid;
						}else{
							window.location.href="../backadmin/index.jsp"
						}
					} else {
						$("#kaptchaImage").click();
						$("input[name='kaptcha']").val('');
						alert(data.message);
					}
				})
				return false;
			});
			$youziku.load("body", "c6da775181ca42f7a7c023bb366de971", "PingFangSC_R");
			$youziku.load("h1", "da21d882d8c44aba953de645cc83cdd8", "PingFangSC_L");//苹方纤黑
			$youziku.draw(); 
		</script>
	</body>
</html>
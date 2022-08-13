
<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<title>新疆维吾尔自治区教师培训信息化管理系统</title>
<meta name="keywords" content="新疆,教师培训信息化管理系统" />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/bootstrap.min.css">
<!--link rel="stylesheet" href="css/animate.css"-->
<link rel="stylesheet" href="css/iframe.css">
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery.colorbox.js"></script>
<script src="js/base64.js"></script>
<link href="css/colorbox.css" rel="stylesheet" type="text/css">
<style>
#login_error {
	background: #fff;
	border-left: 4px solid #dd3d36;
	-webkit-box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .1);
	box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .1);
	margin-bottom: 15px;
	padding: 10px 5px;
}

.login-cnt {
	width: 810px;
	height: 340px;
	position: absolute;
	top: 50%;
	margin-top: -170px;
	left: 50%;
	margin-left: -405px;
}

/*body.login {
	background: url(img/login.jpg) no-repeat 50% 15%;
}*/
.logo {
	margin-top: 110px;
	width: 418px;
	height: 103px;
}

.row-fluid .side {
	width: 420px;
}

.row-fluid .main {
	width: 370px;
}

.login-form {
	background: #eaecf0;
	border: 1px solid #999;
	border: 1px solid rgba(0, 0, 0, 0.3);
	*border: 1px solid #999;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	outline: none;
	-webkit-box-shadow: 0 0 7px rgba(0, 0, 0, 0.5);
	-moz-box-shadow: 0 0 7px rgba(0, 0, 0, 0.5);
	box-shadow: 0 0 7px rgba(0, 0, 0, 0.5);
	padding: 10px 20px;
}

.login-form h2 {
	color: #575757;
	font-size: 18px;
	font-weight: 400;
	margin: 0 0 10px 0;
}

.input-append .add-on,.input-prepend .add-on {
	background: #dadee5;
	padding: 10px 5px;
}

.login-form .input-large {
	width: 283px;
	padding: 10px 6px;
}

.btn-large {
	padding: 9px 19px;
}

.fullscreen_post_bg {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background: #4aceff;
}

.fullscreen_post_bg:before {
	content: '';
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background: url('img/login.jpg');
	background-size: 100% 100%;
}

.fullscreen_post_bg:after {
	content: '';
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0
}

.fullscreen_post_bg img {
	/*display:none*/
	
}

#footer a {
	color: #dedede;
}
</style>
<script type="text/javascript">
$(function() {
	$(".ifrbox").colorbox({
		iframe : true,
		width : "460px",
		height: "450px",
		opacity : '0.5',
		overlayClose : false,
		escKey : true
	});
})
</script>
</head>
<body class="login">
	<div id="fullscreen_post_bg" class="fullscreen_post_bg">
		<img id="fullscreen_post_image" src="img/login.jpg"
			alt="新疆 教育培训信息化管理系统">
	</div>
	<div class="login-cnt">
		<div class="row-fluid">
			<div class="span7 side">
				<h1 class="logo">
					<img src="img/logo2.png">
				</h1>
			</div>
			<div class="span5 main">

				<div <s:if test='%{status=="fail"}'>style="display:block"</s:if>
					<s:else>style="display:none"</s:else> id="login_error">
					<strong>错误</strong>：
					<s:property value="message" />
					<!--a href="#" title="找回密码">忘记密码了</a-->
					<br>
				</div>
				<div class="login-form">
					<h2>评审专家登录</h2>
					<form action="login_login.action" method="post"
						style="margin-bottom:0">
						<!--  <form action="admin/teacherManage_initPage.action" method="post" style="margin-bottom:0"> -->
						<fieldset>

							<div class="control-group">
								<div class="input-prepend">
									<span class="add-on"><img src="img/u.png" alt=""
										style="width:19px;"></span> <input class="input-large"
										type="text" name="username" placeholder="手机号/邮箱/身份证号">
								</div>

							</div>
							<div class="control-group">

								<div class="input-prepend">
									<span class="add-on"><img src="img/p.png" alt=""
										style="width:19px;"></span> <input class="input-large"
										name="passwd" type="password" placeholder="密码">

								</div>

							</div>

							<div class="control-group">
								<div class="controls">
<%-- 									<select name="roleid" class="form-control"> --%>
<!-- <!-- 										<option value="1">管理员</option> -->
<!-- <!-- 										<option value="2">承训单位管理员</option> -->
<!-- 										<option value="4">评审专家</option> -->
<!-- <!-- 										<option value="5">超级管理员</option> -->
<%-- 									</select> --%>
									<input type="hidden" name="roleid" value="4" placeholder="">
								</div>
							</div>

							<div class="control-group">

								<div class="controls">
									<input class="input-medium" name="authCode" type="text"
										placeholder="验证码"> <img
										onclick="this.src='login_authImg.action'"
										style="vertical-align:top;cursor:pointer;margin-top:2px;margin-left:5px;"
										src="login_authImg.action">
								</div>
							</div>

							<button id="submit" type="submit" onclick="check();"
								class="loginbtn btn btn-block btn-large btn-primary">立即登录</button>
							<div class="control-group" style="margin-top:10px;">
								<div class="controls">
									<!--label class="checkbox"
										style="display:inline-block;float:left;margin-right:10px;">
										<input type="checkbox" id="inlineCheckbox1" value="option1">保存密码
									</label-->
									<a
										style="display:inline-block;float:right;margin-left:10px;padding-top:5px;"
										class="forgetpwd ifrbox" data-fancybox-type="iframe" href="admin/modifyPasswordCheck.html">忘记密码?</a>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>

		</div>

		<div class="login-footer"
			style="text-align:center;margin-top:150px;color:#dedede">
			<div id="footer">
				主办：新疆维吾尔自治区教育厅 &nbsp;&nbsp; 承办：新疆师范大学 新疆维吾尔自治区教育发展中心 &nbsp;&nbsp;|&nbsp;&nbsp;
				<br>	<a target="_blank" href="https://beian.miit.gov.cn">新ICP备10003677号-3</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				Power by <a target="_blank" href="http://www.zeppin.cn">www.zeppin.cn</a>

			</div>
		</div>
	</div>

	<!--end-main-container-part-->

<script>
var base = new Base64();

function check(){
	var pwdstr = $('input[name="passwd"]').val();
	$('input[name="passwd"]').val(base.encode(pwdstr.replace(/(^\s*)|(\s*$)/g, "")));
}

	</script>
</body>
</html>


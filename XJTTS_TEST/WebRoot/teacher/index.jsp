<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新疆教师信息化云平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../css/bootstrap.min.css">
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
<script src="../js/iframe.js"></script>
<!--link rel="stylesheet" href="css/animate.css"-->
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
	width: 1000px;
	height: 340px;
	position: absolute;
	top: 50%;
	margin-top: -300px;
	left: 50%;
	margin-left: -500px;
	
}



.logo {
	margin-top: 110px;
	width: 418px;
	height: 103px;
}

.row-fluid .side {
	width: 550px;
	background:url(../img/tree2.png) no-repeat 50% 100%;
	padding-bottom:400px;
}
.row-fluid .side h1{ color:#1d88b1;font-size:26px;font-weight:400;font-family:'Microsoft Yahei';text-align:center;}

.row-fluid .main {
	width: 370px;
}

.login-form {
	margin-top:100px;
	background: #fdfdfd;
	border: 1px solid #eee;
	border: 1px solid rgba(0, 0, 0, 0.2);
	*border: 1px solid #eee;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	outline: none;
	-webkit-box-shadow: 0 0 7px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 0 0 7px rgba(0, 0, 0, 0.2);
	box-shadow: 0 0 7px rgba(0, 0, 0, 0.2);
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
	padding:10px 5px;
}

.login-form .input-large {
	width: 283px;
	padding: 10px 0px;
}

.login-form .input-block-level {
font-family:"Times New Roman";
	padding:5px;
	font-size:20px;
	line-height:24px;
}
.btn-large {padding:9px 19px;}

.fullscreen_post_bg {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background:#fff url(../img/login-que.gif) repeat-x;;
}




.fullscreen_post_bg img {
	/*display:none*/
}

.fullscreen_post_bg:after {
	content: '';
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background: url(../img/cloud.png) repeat 50% 15%  !important;
}

.loginbtn {
    color: #fff;
    text-shadow: 0 -1px 0 rgba(0,0,0,0.25);
    background-color: #92c219;
    *background-color: #6b9405;
    background-image: -moz-linear-gradient(top, #92c219, #6b9405);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#92c219), to(#6b9405));
    background-image: -webkit-linear-gradient(top, #92c219, #6b9405);
    background-image: -o-linear-gradient(top, #92c219, #6b9405);
    background-image: linear-gradient(to bottom, #92c219, #6b9405);
    background-repeat: repeat-x;
    border-color: #04c #04c #002a80;
    border-color: rgba(0,0,0,0.1) rgba(0,0,0,0.1) rgba(0,0,0,0.25);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='##92c219', endColorstr='#6b9405', GradientType=0);
    filter: progid:DXImageTransform.Microsoft.gradient(enabled=false)
}

.loginbtn:hover, .loginbtn:focus, .loginbtn:active, .loginbtn.active, .loginbtn.disabled, .loginbtn[disabled] {
    color: #fff;
    background-color: #6b9405;
   
}

</style>
</head>
<body class="login">
	<div id="fullscreen_post_bg" class="fullscreen_post_bg">
		
	</div>
	<div class="login-cnt">
		<div class="row-fluid">
			<div class="span7 side">
				<h1>新疆教师培训信息化———教师服务平台</h1>
			</div>
			
			<div class="span5 main">

				<div class="login-form">
					<h2>教师系统登录</h2>
					<div <s:if test='%{status=="fail"}'>style="display:block"</s:if>
						<s:else>style="display:none"</s:else> id="login_error">
						<strong>错误</strong>：
						<s:property value="message" />
						<br>
					</div>
					<div <s:if test='%{status=="regist"}'>style="display:block"</s:if>
						<s:else>style="display:none"</s:else> id="login_error">
						<strong>提示</strong>：
						<s:property value="message" />
						<br>
					</div>
					<div <s:if test='%{status=="error"}'>style="display:block"</s:if>
						<s:else>style="display:none"</s:else> id="login_error">
						<strong>提示</strong>：信息审核失败,原因为<br>
						<p align="center"><s:property value="message" /></p>
						<br>
							<div class="controls" style="float: left;"><a class="ifrmbox" style="margin-left: 200px" href="../teacher/tinfo_editInit.action?editId=<s:property value="teacherId" />">点击修改</a></div>
						<br>
					</div>
					<form action="../teacher/tlg_login.action" method="post" >
						<fieldset>

							<div class="control-group">
								<div class="input-prepend">
									<span class="add-on"><img src="../img/u.png" alt=""
										style="width:19px;"></span> <input class="input-large"
										type="text" name="username" placeholder="手机号/邮箱/身份证号">
								</div>

							</div>
							<div class="control-group">

								<div class="input-prepend">
									<span class="add-on"><img src="../img/p.png" alt=""
										style="width:19px;"></span> <input class="input-large"
										name="passwd" type="password" placeholder="密码">
								</div>

							</div>

							<button id="submit" type="submit"
								class="loginbtn btn btn-block btn-large btn-primary">立即登录</button>
							<div class="control-group" style="margin-top:10px;">
								<div class="controls"><a href="../teacher/trg_registerInit.action?opt=add">注册</a>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>

		</div>
		<div id="pagination" style="float:right;" class="pull-right"></div>
		<div class="login-footer" style="text-align:center;margin-top:100px;color:#1d88b1">
			<style>
				.login-footer a {color:#1d88b1;}
			</style>
			<div id="footer">
				Copyright &copy; 2014 新疆维吾尔自治区教育厅. <a target="_blank" href="http://www.xjedu.gov.cn/">联系我们</a> &nbsp;&nbsp;|&nbsp;&nbsp;  Power by <a target="_blank" href="http://www.zeppin.cn">www.zeppin.cn</a>

			</div>
			
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$(".ifrmbox").colorbox({
				iframe : true,
				width : "860px",
				height : "680px",
				maxWidth : '1600px',
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		})
	</script>
</body>
</html>


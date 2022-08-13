<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新疆教师培训项目问卷评价系统</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../css/bootstrap.min.css">
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
	background: url(../img/cloud.png) repeat 50% 15%;
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
				<h1>新疆教师培训项目问卷评价系统</h1>
				
			</div>
			<div class="span5 main">
				
				<div <s:if test='%{status=="fail"}'>style="display:block"</s:if>
					<s:else>style="display:none"</s:else> id="login_error">
					<strong>错误</strong>：
					<s:property value="message" />
					<br>
				</div>
				
				<div class="login-form" style="padding-top:30px;padding-bottom:30px; ">
					<br>
					<br>
					<form action="login_loginPaper.action" method="post"
						style="margin-bottom:0" >
						<fieldset>
							
							
							<div class="control-group">
								
								<label style="color:#449bd5;font-size:20px;text-align:center">请输入学员培训登陆码：</label>
								<br><br><br>
								<div class="controls">
									<input style="text-align:center" class="input-block-level" 
										name="uuid"  placeholder="请输入学员培训登陆码">

								</div>

							</div>
							
							<br><br>

							<button type="submit"
								class="loginbtn btn btn-block btn-large btn-primary">进入问卷</button>
							<br><br>
						</fieldset>
					</form>
				</div>
			</div>

		</div>

		<div class="login-footer"
			style="text-align:center;margin-top:100px;color:#1d88b1">
			<style>
				.login-footer a {color:#1d88b1;}
				<style>
			<div id="footer">
				Copyright &copy; 2014 新疆维吾尔自治区教育厅. <a target="_blank" href="http://www.xjedu.gov.cn/">联系我们</a> &nbsp;&nbsp;|&nbsp;&nbsp;  Power by <a target="_blank" href="http://www.zeppin.cn">www.zeppin.cn</a>

			</div>
			
		</div>
	</div>

</body>
</html>


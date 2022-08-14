<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="企财宝,人事福利,北京知鹏汇仁科技有限公司,saas服务云平台,福利,财务福利,投资理财,企财宝（北京）区块链科技有限公司,智能福利" />
<meta name="description" content="企财宝是一个专业服务于企业人事福利、费用管理与成本控制的Saas服务云平台，由北京知鹏汇仁科技有限公司运营。企财宝平台核心团队由多位企业人力资源领域、财税审计领域、互联网领域、金融服务领域的资深专家组成，公司总部坐落在北京中关村核心园区，是一家国家认定的高新技术企业、双软企业，注册实缴资本1000万元。" />
<title>登录-企财宝</title>
<link rel="shortcut icon" href="../qcb/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="../qcb/css/style.css" />
<link rel="stylesheet" href="../qcb/css/login.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<!--头部-->
		<div id="header">
			<div class="heaer-inner">
				<div class="nav-left">
					<a href="https://www.qicaibao.cc/" class="nav-logo"></a>
					<p>欢迎登录</p>
				</div>
				<div class="nav-right">
					<span>还没有账户？去<a href="../qcb/register.jsp">企业注册</a></span>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<!--banner-->
		<div id="banner">
			<div class="banner-inner">
				<!--登录内容-->
				<div class="content">
					<h1>企业登录</h1>
					<form:form id="loginsubmit" action="../rest/qcb/admin/login" method="post">
					<ul>
						<li><div class="inputBox"><label>手机号</label><input type="text" class="telNum" placeholder="请输入手机号"/><p class="clear"></p></div>
						<span class="tips">请输入正确的手机号</span></li>
						<li><div class="inputBox"><label>密码</label><input type="password" class="password" placeholder="请输入密码"/><p class="clear"></p></div>
						<span class="tips">请输入密码</span></li>
						<li><div class="inputBox"><label>验证码</label><input type="text" class="codeinput" placeholder="请输入验证码"/>
						<img class="code" src="../rest/qcb/admin/kaptchaImage" alt="验证码" /><p class="clear"></p></div>
						<span class="tips">请输入验证码</span></li>
						<li><button type="button" class="loginBtn clickBtn">登录</button></li>
						<li>
							<div class="remember">
								<div class="piaochecked on_check">
                						<input name="need_inv" type="checkbox" class="radioclass input" id="checkbox">
              					</div>
              					<label for="checkbox" style="cursor: pointer;">记住账号</label></div>
							<a class="forgetPassword">忘记密码</a>
							<div class="clear"></div>
						</li>
					</ul>
					</form:form>
				</div>
			</div>
		</div>
		<!--footer-->
		<p class="footer">客服电话：010-62226659&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
		<a href="http://www.beian.miit.gov.cn" target="_blank">京ICP备18057956号-1</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;Copyright©2018 企财宝（北京）区块链科技有限公司 </p>
		<!--绑定微信-->
		<div class="iframe bindWeChatIframe">
			<div class="iframeInner">
				<a class="closeIframe"></a>
				<p>请微信扫码登录</p>
			</div>			
		</div>
		<a id="wechatAgainBtn" href="../qcb/safetyCenterIframe.jsp?type=login" target="_blank"></a>
	<script type="text/javascript" src="../qcb/js/jquery-1.11.1.js" ></script>
	<script type="text/javascript" src="../qcb/layer-v3.0.1/layer/layer.js"></script>
	<script type="text/javascript" src="../qcb/js/base64.js" ></script>
	<script type="text/javascript" src="../qcb/js/cookie.js" ></script>
	<script type="text/javascript" src="../qcb/js/login.js?v=<%=Math.random()%>" ></script>

</body>
</html>

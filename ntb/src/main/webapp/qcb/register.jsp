<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="企财宝,人事福利,北京知鹏汇仁科技有限公司,saas服务云平台,福利,财务福利,投资理财,企财宝（北京）区块链科技有限公司,智能福利" />
<meta name="description" content="企财宝是一个专业服务于企业人事福利、费用管理与成本控制的Saas服务云平台，由北京知鹏汇仁科技有限公司运营。企财宝平台核心团队由多位企业人力资源领域、财税审计领域、互联网领域、金融服务领域的资深专家组成，公司总部坐落在北京中关村核心园区，是一家国家认定的高新技术企业、双软企业，注册实缴资本1000万元。" />
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
	<title>注册-企财宝</title>
	<link rel="stylesheet" href="css/style.css" />
	<link rel="stylesheet" href="css/register.css" />
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
					<p>欢迎注册</p>
				</div>
				<div class="nav-right">
					<span>已有账户？<a href="login.jsp">请登录</a></span>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<!--内容区域-->
		<div id="content">
			<div class="content-inner">
				<!--进度条-->
				<div class="progressDiv">
					<div class="progressDiv-inner">
						<div class="firstStep step light">
							<span>1</span>
							<p>填写手机号码</p>
							<div class="clear"></div>
						</div>
						<div class="bar"></div>
						<div class="secStep step">
							<span>2</span>
							<p>填写企业信息</p>
							<div class="clear"></div>
						</div>
						<div class="bar"></div>
						<div class="thirdStep step">
							<span>3</span>
							<p>注册完成</p>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>					
				</div>
				 <!-- 注册form开始 -->
				<form:form id="registersubmit" class="register-form" action="../rest/qcb/admin/register" method="post"> 
				<div class="progressContent">
					<!--第一步-->
					
					<div class="firstContent contentDiv">
						<!-- 获取短信验证码form开始 -->
						
						<div class="form-group">
							<label class="label">手机号</label>
							<div class="form-item"><input type="text" class="mobile" name="mobile" placeholder="请输入手机号" /></div>
							<div class="tipsBox"><p class="tips">请输入正确手机号</p></div>
							<div class="clear"></div>
						</div>
						<div class="form-group">
							<label class="label">图形验证码</label>
							<div class="form-item">
								<input type="text" class="kaptcha" name="kaptcha" placeholder="请输入验证码"/>
								<img class="code" src="../rest/qcb/admin/kaptchaImage" alt="验证码" />
								<div class="clear"></div>
							</div>
							<div class="tipsBox"><p class="tips">请输入验证码</p></div>
							<div class="clear"></div>
						</div>
						<input type="hidden" name="codeType" value="register"/>
						
						<!-- 获取短信验证码form结束 -->
						<div class="form-group">
							<label class="label">短信验证码</label>
							<div class="form-item">
								<input type="text" class="smsCodeinput" placeholder="请输入短信验证码"/>
								<a class="smsCode">获取验证码</a>
								<div class="clear"></div>
							</div>
							<div class="tipsBox"><p class="tips">请输入短信验证码</p></div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
						<button class="firstConfirm continueBtn">继续填写企业信息</button>
						<div class="clear"></div>
						<!--<a class="goBack">返回</a>-->
					</div>
					
					<!-- 校验验证码正确性form结束 -->
					<!--第二步-->
					<div class="secContent contentDiv">
						<div class="form-group">
							<label class="label">企业名称</label>
							<div class="form-item"><input type="text" class="companyName" placeholder="请输入企业名称" /></div>
							<div class="tipsBox"><p class="tips">请输入企业名称</p></div>
							<div class="clear"></div>
						</div>
						<div class="form-group">
							<label class="label">所在地区</label>
							<div class="form-item">								
								<select class="province">
									<option value="0">请选择</option>									
								</select>
								<select class="city">
									<option value="0">请选择</option>
								</select>
								<select class="county" name="area">
									<option value="0">请选择</option>
								</select>
							</div>
							<div class="tipsBox"><p class="tips">请选择所在地区</p></div>
							<div class="clear"></div>
						</div>
						<div class="form-group">
							<label class="label">管理密码</label>
							<div class="form-item"><input type="password" class="companyPassword" placeholder="请输入你的登录密码" /></div>
							<div class="tipsBox tipsBoxs"><p class="tips">8-20个字符，包含数字、字母和特殊字符</p></div>
							<div class="clear"></div>
						</div>
						<div class="form-group">
							<label class="label">联系人姓名</label>
							<div class="form-item"><input type="text" class="contactName" placeholder="请输入联系人姓名" /></div>
							<div class="tipsBox"><p class="tips">请输入联系人姓名</p></div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
						<button class="secConfirm light completeBtn">完成注册</button>
						<div class="clear"></div>
						<div class="agreeRegist">
							<div class="piaochecked">
            						<input name="need_inv" type="checkbox" class="radioclass input" id="checkbox">
          					</div>
          					<label for="checkbox" style="cursor: pointer;width: auto;">同意<a href="#">《企财宝平台使用协议》</a></label>
						</div>
					</div>
					<!--第三步-->
					<div class="thirdContent contentDiv">
						<p>恭喜你完成注册</p>
						<a><span>3</span>秒后自动前往</a>
					</div>
				</div>
				</form:form>
				<!-- 获取短信验证码form -->
				<form:form id="getCodesubmit" action="../rest/qcb/sms/sendCode" method="post">
				</form:form>
				<!-- 校验短信验证码正确form -->
				<form:form id="checkCodesubmit" action="../rest/qcb/admin/check" method="post"> 
				</form:form>
			</div>
		</div>
				
		<!--footer-->
		<p class="footer">客服电话：010-62226659&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="http://www.beian.miit.gov.cn" target="_blank">京ICP备18057956号-1</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;Copyright©2018 企财宝（北京）区块链科技有限公司 </p>
	<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
	<script type="text/javascript" src="../qcb/layer-v3.0.1/layer/layer.js"></script>
	<script type="text/javascript" src="js/base64.js" ></script>
	<script type="text/javascript" src="js/register.js?v=<%=Math.random()%>" ></script>
</body>
</html>
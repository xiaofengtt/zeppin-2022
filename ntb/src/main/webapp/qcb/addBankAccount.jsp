<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="css/select2.min.css" />
<link rel="stylesheet" href="./css/base.css" />
<link rel="stylesheet" href="css/addBankAccount.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="600eaeb1-ff26-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<%-- <input type="hidden" value="${sessionScope}" /> --%>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<form:form id="addCompanyBanksubmit" action="../rest/qcb/companyBankcard/add" method="post">
				<div class="box loadingOver">
					<h1 class="box-title">账户信息-添加银行账户</h1>
					<div class="groupBox">
						<div class="group" style="padding:0 0 15px 0;">
							<label>账户类型：</label>
							<div class="group-right">
								企业对公账户
							</div>
							<div class="clear"></div>
						</div>
						<div class="group" style="padding:0 0 15px 0;">
							<label>账户名称：</label>
							<div class="group-right">
								${sessionScope.currentQcbOperator.qcbCompanyName}
							</div>
							<div class="clear"></div>
						</div>
						<div class="group">
							<label class="line-height">银行账号：</label>
							<div class="group-right">
								<input type="text" class="bankAccount" placeholder="请输入银行账号" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');" />
							</div>
							<p class="tips color-red">请填写正确的银行卡号</p>
							<div class="clear"></div>
						</div>
						<div class="group">
							<label class="line-height">开户银行：</label>
							<div class="group-right">
								<select class="bankList">

								</select>
							</div>
							<p class="tips color-red">请选择开户银行</p>
							<div class="clear"></div>
						</div>
						<div class="group">
							<label class="line-height">开户地区：</label>
							<div class="group-right">
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
							<p class="tips color-red">请选择开户地区</p>
							<div class="clear"></div>
						</div>
						<div class="group">
							<label class="line-height">所在支行：</label>
							<div class="group-right">
								<select class="bankBranchList">
									<option>请选择</option>
								</select>
							</div>
							<p class="tips color-red">请选择所在支行</p>
							<div class="clear"></div>
						</div>
						<div class="group">
							<label class="line-height">预留手机号：</label>
							<div class="group-right">
								<input type="text" class="bingingCardPhone" placeholder="请输入预留手机号"  />
							</div>
							<p class="tips color-red">请填写正确的预留手机号</p>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<div class="box authentication loadingOver" style="padding-top: 40px;">
						<h1 class="box-title">身份验证</h1>
						<div class="group">
							<label>绑定手机：</label>
							<div class="group-right font-bold">
								${sessionScope.currentQcbOperator.mobile}
							</div>
							<div class="clear"></div>
						</div>

						<div class="group sendCode">
							<label>验证码：</label>
							<div class="group-right">
								<div class="form-item">
									<input type="text" class="smsCodeinput" placeholder="请输入验证码" />
									<a class="smsCode">获取验证码</a>
									<div class="clear"></div>
								</div>
							</div>
							<p class="tips color-red">请填写正确的验证码</p>
							<div class="clear"></div>
						</div>

						<div class="btnGroup">
							<a class="btn sureBtn">保存</a>
						</div>
					</div>
				</form:form>
				<!-- 发送验证码 -->
				<form:form id="getCodesubmit" action="../rest/qcb/sms/sendCodeToCheck" method="post">
				</form:form>
				
				<!-- loading -->
				<div class="loadingDiv"></div>
				
			</div>
	</main>
	<jsp:include page="footer.jsp"/>
	<script type="text/javascript" src="js/select2.min.js" ></script>
	<script type="text/javascript" src="js/addBankAccount.js?v=<%=Math.random()%>" ></script>
</body>
</html>

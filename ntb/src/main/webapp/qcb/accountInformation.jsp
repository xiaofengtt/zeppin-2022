<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css" />
<link rel="stylesheet" href="./css/paging.css" />
<link rel="stylesheet" href="./css/accountInformation.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->

<script type="text/template" id="bankCardTpl">
	<div class="bank-msg">
		<img src="..{{:bankIconUrl}}" alt="" />
		<h5>{{:bankName}}{{:bindingCardTypeCN}}</h5>
		<p>
			<span class="name">{{:bindingCardCardholder}}</span>
			<span class="num">尾号
				{{:bindingBankCard.slice(bindingBankCard.length -4 , bindingBankCard.length)}}
			</span>
		</p>
		<div class="bg">
			<!-- <a href="./editBankAccount.jsp?uuid={{:uuid}}">
				<img src="./img/edit.png" alt="" />
			</a> -->
			<a class="delete" data-uuid="{{:uuid}}">
				<img src="./img/delete.png" alt=""/>
			</a>
		</div>
	</div>
</script>
<script type="text/template" id="listTpl">

</script>
<style>
	input.smsCode{
		width:160px !important;
		margin-right:17px;
		float:left;
	}

	.getCode{
		width: 132px;
		height: 42px;
		border: 1px solid #ffb031;
		display: block;
		float: left;
		border-radius: 2px;
		line-height: 42px;
		text-align: center;
		font-size: 14px;
		cursor: pointer;
		outline:none;
	}
	#mobile{
		line-height:42px;
	}
</style>
</head>
<body>
	<input id="pageId" type="hidden" value="600eaeb1-ff26-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
				<div class="main-right-top box loadingOver">
					<h3 class="box-title">账户信息</h3>
					<div class="btn-box">
						<a href="recharge.jsp" class="btn recharge">充值</a>
						<a href="companyWithdrawals.jsp" class="btn">提现</a>
					</div>
					<div class="account-msg">
						<div class="account-msg-item">
							<h5>账户余额（元）</h5>
							<p id="accountBalance"></p>
						</div>
						<div class="account-msg-item">
							<h5>可用余额（元）</h5>
							<p id="accountPay"></p>
						</div>
						<div class="account-msg-item">
							<h5>当前投资（元）</h5>
							<p id="totalInvest"></p>
						</div>
						<div class="account-msg-item">
							<h5>累计赚取（元）</h5>
							<p id="totalReturn"></p>
						</div>
					</div>
				</div>

				<div class="main-right-mid box loadingOver">
					<h3 class="box-title">银行账户</h3>
					<div class="main-right-mid-content">



					</div>
				</div>

				<div class="main-right-bottom box" style="display:none;">
					<h3 class="box-title">企业理财</h3>
					<div class="btn-box">
						<a href="" class="btn">投资</a>
					</div>
					<table cellpadding="0" cellspacing="0">
						<tr class="first-tr">
							<th width="10%" class="sort" style="background-position-x:50px;">到期日</th>
							<th width="76%">&nbsp;银行理财产品</th>
							<th width="15%" class="sort">投资金额</th>
							<th width="12%" class="sort">预期收益</th>
							<th width="20%">操作</th>
						</tr>
						<tr>
							<td>156天</td>
							<td>
								<p class="product-name">
									【浙商银行】永乐3号3天型人民币理财产品(BB4313)
								</p>
								<p class="product-msg">
									<span class="color-orange" style="border-color:#FF9E10;">5.51%</span>
									<span class="color-orange">365天</span>
									<span>N312415</span>
									<span>非保本浮动收益</span>
									<span style="border:none;">计息日：2017-08-01至2018-08-01</span>
								</p>
								<p class="product-date">

								</p>
							</td>
							<td>
								350,000
							</td>
							<td>
								4,500
							</td>
							<td>
								<a href="">查看</a>
								<a href="">投资</a>
								<a href="">赎回</a>
							</td>
						</tr>
						<tr>
							<td>156天</td>
							<td>
								<p class="product-name">
									【浙商银行】永乐3号3天型人民币理财产品(BB4313)
								</p>
								<p class="product-msg">
									<span class="color-orange" style="border-color:#FF9E10;">5.51%</span>
									<span class="color-orange">365天</span>
									<span>N312415</span>
									<span>非保本浮动收益</span>
									<span style="border:none;">计息日：2017-08-01至2018-08-01</span>
								</p>
								<p class="product-date">

								</p>
							</td>
							<td>
								350,000
							</td>
							<td>
								4,500
							</td>
							<td>
								<a href="">查看</a>
								<a href="">投资</a>
								<a href="">赎回</a>
							</td>
						</tr>
						<tr>
							<td>156天</td>
							<td>
								<p class="product-name">
									【浙商银行】永乐3号3天型人民币理财产品(BB4313)
								</p>
								<p class="product-msg">
									<span class="color-orange" style="border-color:#FF9E10;">5.51%</span>
									<span class="color-orange">365天</span>
									<span>N312415</span>
									<span>非保本浮动收益</span>
									<span style="border:none;">计息日：2017-08-01至2018-08-01</span>
								</p>
								<p class="product-date">

								</p>
							</td>
							<td>
								350,000
							</td>
							<td>
								4,500
							</td>
							<td>
								<a href="">查看</a>
								<a href="">投资</a>
								<a href="">赎回</a>
							</td>
						</tr>
						<tr>
							<td>156天</td>
							<td>
								<p class="product-name">
									【浙商银行】永乐3号3天型人民币理财产品(BB4313)
								</p>
								<p class="product-msg">
									<span class="color-orange" style="border-color:#FF9E10;">5.51%</span>
									<span class="color-orange">365天</span>
									<span>N312415</span>
									<span>非保本浮动收益</span>
									<span style="border:none;">计息日：2017-08-01至2018-08-01</span>
								</p>
								<p class="product-date">

								</p>
							</td>
							<td>
								350,000
							</td>
							<td>
								4,500
							</td>
							<td>
								<a href="">查看</a>
								<a href="">投资</a>
								<a href="">赎回</a>
							</td>
						</tr>

					</table>

					<div id="pageTool"></div>
				</div>
				
				<!-- loading -->
				<div class="loadingDiv"></div>
				
			</div>
	</main>
	<div class="iframe bindPhoneIframe">
		<div class="iframeInner" style="height:380px;">
			<h1>删除银行账户</h1>
			<a class="closeIframe"></a>
			<form:form id="deleteBankCard" action="../rest/qcb/companyBankcard/delete" method="get">
				<input type="hidden" name="" id="uuid" value="">
				<div class="groupBox">
					<div class="group">
						<label>手机号：</label>
						<%-- <input type="text" class="oldphone" /> --%>
						<span id="mobile">${sessionScope.currentQcbOperator.mobile}</span>
					</div>
					<div class="group">
						<label>验证码：</label>
						<input type="text" class="smsCode" name="code" />
						<a class="getCode color-orange">获取验证码</a>
						<div class="clear"></div>
						<p class="tip color-red">验证码不能为空</p>
					</div>
				</div>
			</form:form>
			<a class="sureBtn deleteBtn">确定</a>
			<form:form id="getCodesubmit" action="../rest/qcb/sms/sendCodeToCheck" method="post">
			</form:form>
		</div>
	</div>
	<jsp:include page="footer.jsp"/>
	<script src="./js/query.js"></script>
	<script src="./js/paging.js"></script>
	<script src="./js/jsrender.min.js"></script>
	<script src="./js/accountInformation.js?v=<%=Math.random()%>"></script>
</body>
</html>

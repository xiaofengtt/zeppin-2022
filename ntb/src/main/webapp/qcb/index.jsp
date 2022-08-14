<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="./css/base.css" />
		<link rel="stylesheet" href="./css/index.css" />
		<!--[if lte IE 8]>
			<script src="js/html5shiv.js"></script>
			<script src="js/respond.js"></script>
		<![endif]-->
	</head>
	<body>
		<input id="pageId" type="hidden" value="ffffffff-ffff-ffff-ffff-ffffffffffff"/>
		<jsp:include page="header.jsp"/>
		<input id="scode" type="hidden" />
		<main>
			<jsp:include page="contentLeft.jsp"/>
			<div class="main-right">
				<!--公告-->
				<div class="box noticeBox" style="display:none;">
					<p class="color-orange notice">公告：企财宝Ver2.1版本已经上线，新增投资理财功能。</p>
					<a class="closeNotice"></a>
				</div>
				<!--公司概况、账户信息-->
				<div class="companyProfileBox loadingOver">
					<div class="companyProfile box">
						<h1 class="box-title">公司概况</h1>
						<a class="edit edit-infomation" href="companyOperate.jsp">修改信息</a>
						<div class="clear"></div>
						<div class="companyInfomation">
							<div class="group">
								<label>公司名称：</label>
								<p class="groupValue companyName"><span class="color-green"></span></p>
								<div class="clear"></div>
							</div>
							<div class="group">
								<label>公司人数：</label>
								<p class="groupValue companyNumber"></span></p>
								<div class="clear"></div>
							</div>
							<div class="group">
								<label>系统管理员：</label>
								<p class="groupValue companyAdmin"></p>
								<div class="clear"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="accountInformationBox loadingOver">
					<div class="accountInformation box">
						<h1 class="box-title">账户信息</h1>
						<!-- <h2 class="box-subtitle">如何降低企业成本？</h2> -->
						<a class="edit edit-account" href="financeList.jsp">账务明细</a>
						<div class="clear"></div>
						<div class="companyInfomation">
							<div class="group">
								<label>账户余额：</label>
								<p class="groupValue accountBalance"></span></p>
								<div class="btnBox">
									<a class="recharge" href="recharge.jsp">充值</a>
									<a class="withdrawals" href="companyWithdrawals.jsp">提现</a>
								</div>
								<div class="clear"></div>
							</div>
							<div class="group">
								<label>投资总额：</label>
								<p class="groupValue totalInvestment"></span></p>
								<div class="clear"></div>
							</div>
							<div class="group">
								<label>赚取利息：</label>
								<p class="groupValue earnInterest color-orange"></p>
								<div class="clear"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<!--支出曲线-->
				<div class="box lineBox loadingOver">
					<h1 class="box-title" style="margin-left:0;">近一年费用支出曲线</h1>
					<div id="containerline" style="width: 99%;min-height: 433px;"></div>
				</div>
				<!-- loading -->
				<div class="loadingDiv"></div>
			</div>
		</main>
		<jsp:include page="footer.jsp"/>
		<script src="./js/highcharts.js"></script>
		<script type="text/javascript" src="./js/index.js?v=<%=Math.random()%>" ></script>

	</body>
</html>

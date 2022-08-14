<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="cn.zeppin.product.ntb.qcb.vo.AdminVO"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css" />
<link rel="stylesheet" href="./css/paging.css" />
<link rel="stylesheet" href="./css/recharge.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="c7b0a832-ff26-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<div class="main-right-body box loadingOver">
				<div class="main-right-body-header">
					<h3 class="box-title" style="padding-top:2px;">企业充值</h3>
					<a  href="./financeList.jsp?type=income" class="with-draw-list">查看充值记录</a>
				</div>
				<div class="main-right-body-tip">
					<p>说明：以下企业专属充值账户是贵公司充值的专属账户，充值成功后，资金可即时到账，24小时内可提现</p>
					<a href="" style="display:none;">需要发票</a>
				</div>
				<div class="main-right-body-content">
					<h3 class="box-title">专属充值账户</h3>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td class="table-title">账户名称</td>
							<td class="companyAccountName"></td>
						</tr>
						<tr>
							<td class="table-title">银行账号</td>
							<td class="companyAccountNum"></td>
						</tr>
						<tr>
							<td class="table-title">开户银行</td>
							<td class="branchBankName"></td>
						</tr>
						<tr>
							<td class="table-title">开户行地址</td>
							<td class="branchBankAddress"></td>
						</tr>
					</table>
					<p class="main-right-body-content-ps color-gray">
						注：转账汇款方式的充值必须以公司账户发起转账才能充值到账，否则需要电话确认为贵公司的充值。如遇到问题，请咨询客服。
					</p>
				</div>
			</div>
			<!-- 引导认证 -->
			<div class="guideDBox box">
				<div class="guideInner">
					<p>1分钟完成企业认证，享受更多特权与服务。</p>
					<a href="companyOperate.jsp" class="btn">去认证</a>
				</div>
			</div>
			
			<!-- loading -->
			<div class="loadingDiv"></div>
			
		</div>
	</main>
	<jsp:include page="footer.jsp"/>
	<script type="text/javascript" src="js/recharge.js?v=<%=Math.random()%>" ></script>
	<script>
		<%
			String status = "";
			if(session.getAttribute("currentQcbOperator") != null){
				AdminVO admin = (AdminVO)session.getAttribute("currentQcbOperator");
				if(admin.getQcbCompanyStatus() != null){
					status = admin.getQcbCompanyStatus();
				}
			}
		%>
		var status="<%=status%>";
		if(status!="normal"){
			$(".main-right-body").hide();
			$(".guideDBox").css({"height":$(window).height()-194+"px"}).show();
			$(".loadingDiv").hide();
		}else{
			/* $(".main-right-body").show(); */
			$(".guideDBox").hide();
			getInfo();
		}
		
	</script>
</body>
</html>

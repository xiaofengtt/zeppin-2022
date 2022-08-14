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
	<link rel="stylesheet" href="./css/page.css" />
	<link rel="stylesheet" href="./css/table.css" />
	<link rel="stylesheet" href="./css/wagesList.css" />

	<!--[if lte IE 8]>
		<script src="js/html5shiv.js"></script>
		<script src="js/respond.js"></script>
	<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="6529ef10-ff27-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<div class="main-right-body box loadingOver" style="display:none;">
				<h3>发放记录</h3>

				<table class="table" cellspacing="0" cellpadding="0">
					
				</table>
				<div id="pageTool">
					
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
	<!--弹出框-->
	<div class="bg">
		<div class="modal iframeInner">
			<div class="modal-header">
				<h3 class="box-title iframe-title">发放福利</h3>
				<a id="modal-close"></a>
			</div>
			<h4>为保障账户资金安全，请获取短信验证码进行支付</h4>
			<div class="groupBox" style="border:none;">
				<div class="group">
					<label>手机号：</label>
					<span style="display:block;text-align:left;line-height:42px;">${sessionScope.currentQcbOperator.mobile}</span>
					<input type="hidden" class="oldphone" value="${sessionScope.currentQcbOperator.mobile}"
					readonly="readonly" />
					<div class="clear"></div>
				</div>
				<div class="group">
					<label>验证码：</label>
					<input type="text" class="smsCode" name="code" />
					<a class="getCode color-orange">获取验证码</a>
					<div class="clear"></div>
					<p class="tip color-red" style="padding-left:130px;">验证码不能为空</p>
				</div>
				<a class="sureBtn distributionBtn" style="margin-left:125px;margin-top:15px;">确认</a>
			</div>
			<!-- 发送验证码 -->
			<form:form id="sendCodeToCheck" action="../rest/qcb/sms/sendCodeToCheck" method="post">
			</form:form>
			<!-- 确认薪资发放 -->
			<form:form id="sureSend" action="../rest/qcb/companyPayroll/submit" method="post">
			</form:form>
		</div>
	</div>
	<div class="iframe submitGrant">
		<div class="iframeInner">
			<a class="closeIframe"></a>
			<p>工资发放中。。。</p>
		</div>			
	</div>
	<a id="wechatBtn" href="" target="_blank" rel="noopener noreferrer"></a>

	<jsp:include page="footer.jsp"/>
	<script src="./js/query.js"></script>
	<script src="./js/paging.js"></script>
	<script src="./js/wagesList.js?v=<%=Math.random()%>"></script>
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
			$(".box").hide();
			$(".guideDBox").css({"height":$(window).height()-194+"px"}).show();
			$(".loadingDiv").hide();
		}else{
			/* $(".box").show(); */
			$(".guideDBox").hide();
		    getlist();
		}
	</script>
</body>
</html>
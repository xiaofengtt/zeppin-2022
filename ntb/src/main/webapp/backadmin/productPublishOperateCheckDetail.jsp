<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/fundEdit.css" />
		<link rel="stylesheet" href="css/uploadfile.css">
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00400042" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="productPublishOperateCheckList.jsp">理财产品发布审核管理</a><span>></span><a class="current" id="titlename"></a></div>
					<form:form id="formsubmit" role="form" action="#" method="post">
						<div class="title-contain">
							<div class="contentDiv firstContent">
								<div class="content-item">
									<div class="content-item-info">
										<div class="logo">
											<img id="custodiansLogo"/>
										</div>
										<div class="form-group noBorder">
											<label class="label_large">管理银行：</label>
											<div class="content-items label_large"><span id="custodians"></span><span class="editSpan" id="custodian"></span>
											</div>
											<div class="clear"></div>
										</div>
										<div class="form-group noBorder">
											<label class="label_large">产品名称：</label>
											<div class="content-items label_large"><span id="names"></span><span class="editSpan" id="name"></span>
											</div>
											<div class="clear"></div>
										</div>
										<div class="form-group noBorder">
											<label class="label_large">产品链接：</label>
											<div class="content-items label_large"><span id="urls"></span><span class="editSpan" id="url"></span>
											</div>
											<div class="clear"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="main-contain">
							<div class="contentDiv">
								<p class="title">基本信息</p>
							<div class="content-item">
								<!-- 信息 -->
								<div class="content-item-info">
									<div class="form-group col-md-12">
										<label>产品系列：</label>
										<div class="content-items"><span id="seriess"></span><span class="editSpan" id="series"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>产品简称：</label>
										<div class="content-items"><span id="shortnames"></span><span class="editSpan" id="shortname"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>产品编号：</label>
										<div class="content-items"><span id="scodes"></span><span class="editSpan" id="ascode"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>产品规模：</label>
										<div class="content-items"><span id="totalAmounts"></span>亿元<span class="editSpan" id="totalAmount"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>产品类型：</label>
										<div class="content-items"><span id="types"></span><span class="editSpan" id="type"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>目标年化收益率：</label>
										<div class="content-items"><span id="targetAnnualizedReturnRates"></span>%<span class="editSpan" id="targetAnnualizedReturnRate"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>理财币种：</label>
										<div class="content-items"><span id="currencyTypes"></span><span class="editSpan" id="currencyType"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>最低年化收益率：</label>
										<div class="content-items"><span id="minAnnualizedReturnRates"></span>%<span class="editSpan" id="minAnnualizedReturnRate"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>风险等级：</label>
										<div class="content-items"><span id="riskLevels"></span><span class="editSpan" id="riskLevel"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>收益支付方式：</label>
										<div class="content-items"><span id="paymentTypes"></span><span class="editSpan" id="paymentType"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>面向对象：</label>
										<div class="content-items"><span id="targets"></span><span class="editSpan" id="target"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12 noBorder">
										<label>发行地区：</label>
										<div class="content-items"><span id="areas"></span><span class="editSpan" id="area"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
						<div class="contentDiv">
							<p class="title">认购信息</p>
							<div class="content-item">
								<!-- 信息 -->
								<div class="content-item-info">
									<div class="form-group col-md-12">
										<label>灵活期限：</label>
										<div class="content-items"><span id="flagFlexibles"></span><span class="editSpan" id="flagFlexible"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>申购状态：</label>
										<div class="content-items"><span id="flagPurchases"></span><span class="editSpan" id="flagPurchase"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>赎回状态：</label>
										<div class="content-items"><span id="flagRedemptions"></span><span class="editSpan" id="flagRedemption"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>认购时间：</label>
										<div class="content-items">
											<span id="collectStarttimes"></span> 至
											<span id="collectEndtimes"></span>
											<br>
											<span class="editSpan" id="collectStarttime" style="color:red;margin-left:0" ></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>登记日：</label>
										<div class="content-items"><span id="recordDates"></span><span class="editSpan" id="recordDate"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>起息/结束日：</label>
										<div class="content-items"><span id="valueDates"></span>
										 至
										<span id="maturityDates"></span>
										 产品期限：<span id="terms"></span>天
										 <br>
										<span style="color:red;margin-left:0" id="valueDate"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>最小投资递增：</label>
										<div class="content-items"><span id="minInvestAmountAdds"></span>元<span class="editSpan" id="minInvestAmountAdd"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12">
										<label>最小投资金额：</label>
										<div class="content-items"><span id="minInvestAmounts"></span>元<span class="editSpan" id="minInvestAmount"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12 noBorder">
										<label>最大投资金额：</label>
										<div class="content-items"><span id="maxInvestAmounts"></span>元<span class="editSpan" id="maxInvestAmount"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
						<div class="contentDiv">
							<p class="title">手续费率</p>
							<div class="content-item">
								<!-- 信息 -->
								<div class="content-item-info">
									<div class="form-group col-md-6">
										<label>认购费率：</label>
										<div class="content-items"><span id="subscribeFees"></span>%<span class="editSpan" id="subscribeFee"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-6">
										<label>申购费率：</label>
										<div class="content-items"><span id="purchaseFees"></span>%<span class="editSpan" id="purchaseFee"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-6">
										<label>赎回费率：</label>
										<div class="content-items"><span id="redemingFees"></span>%<span class="editSpan" id="redemingFee"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-6">
										<label>托管费率：</label>
										<div class="content-items"><span id="custodyFees"></span>%<span class="editSpan" id="custodyFee"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-6 noBorder">
										<label>销售费率：</label>
										<div class="content-items"><span id="networkFees"></span>%<span class="editSpan" id="networkFee"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-6 noBorder">
										<label>管理费率：</label>
										<div class="content-items"><span id="managementFees"></span>%<span class="editSpan" id="managementFee"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
						<div class="contentDiv">
							<p class="title">详细信息</p>
							<div class="content-item">
								<!-- 信息 -->
								<div class="content-item-info" style="margin-top:15px;">
									<div class="form-group col-md-12 noBorder">
										<label>投资范围：</label>
										<div class="content-items text_large">
											<span id="investScopes"></span><br>
										</div>
										<span class="red" id="investScope"></span>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12 noBorder">
										<label>产品收益说明：</label>
										<div class="content-items text_large">
											<span id="revenueFeatures"></span><br>
										</div>
										<span class="red" id="revenueFeature"></span>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12 noBorder">
										<label>产品更多描述：</label>
										<div class="content-items text_large">
											<span id="remarks"></span><br>
										</div>
										<span class="red" id="remark"></span>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12 noBorder">
										<label>产品说明书：</label>
										<div class="content-items text_large">
											<a id="documentsLink" href="javascript:void(0);"><span id="documents"></span></a><br>
											<a id="documentLink" href="javascript:void(0);"><span class="red" id="document"></span></a>
										</div>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
						<div class="contentDiv">
							<p class="title">更多信息</p>
							<div class="content-item">
								<!-- 信息 -->
								<div class="content-item-info">
									<div class="form-group col-md-12 noBorder">
										<label>保本保息状态：</label>
										<div class="content-items"><span id="guaranteeStatuss"></span><span class="editSpan" id="guaranteeStatus"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
									<div class="form-group col-md-12 noBorder">
										<label>计划募集金额：</label>
										<div class="content-items"><span id="collectAmounts"></span>万元<span class="editSpan" id="collectAmount"></span>
										</div>
										<div class="clear"></div>
									</div>
								</div>
								<div class="clear"></div>
							</div>

						</div>
					</div>
					<div class="foot-contain">
							<div class="contentDiv">
								<div class="operateBar">操作类型：<span id="operateType"></span></div>
								<div class="content-item">
									<div class="content-item-info">
										<div class="form-group col-md-12 noBorder">
											<label>提交人：</label>
											<div class="content-items"><span id="submitName"></span>
											</div>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
										<div class="form-group col-md-12 noBorder">
											<label>提交时间：</label>
											<div class="content-items"><span id="submitTime"></span>
											</div>
											<div class="clear"></div>
										</div>
										<div class="form-group col-md-12 noBorder" id="reasonItem">
											<label>审核原因：</label>
											<div class="content-items"><span id="reason"></span>
											</div>
											<div class="clear"></div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="operateCheck" style="font-size:16px;">
									审核原因：<input type="text" id="checkReason"/>
									<div>
										<button class="btn btn-primary" type="button" onclick="checked(this)">通过</button>
										<button class="btn btn-primary" type="button" onclick="unpassed(this)">不通过</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</div>
			<div class="clear"></div>


		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/productPublishOperateCheckDetail.js" ></script>
		<script type="text/javascript" src="js/flagSubmit.js"></script>

	</body>
</html>

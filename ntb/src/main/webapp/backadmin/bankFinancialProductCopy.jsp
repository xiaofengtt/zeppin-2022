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
		<link rel="stylesheet" href="css/datepicker3.css" >
		<link rel="stylesheet" href="css/uploadfile.css">
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00300031" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="bankFinancialProductList.jsp">理财产品信息管理</a><span>></span><a class="current" id="titlename"></a></div>
				<form:form id="formsubmit" role="form" action="#" method="post">
				<div class="title-contain">
						<input class="uuid" type="hidden" name="uuid"/>
						<div class="contentDiv firstContent">
						<div class="content-item">
							<div class="content-item-info">
								<div class="logo">
									<img id="custodiansLogo"/>
								</div>
								<div class="form-group noBorder">
									<label class="label_large">管理银行：</label>
									<div class="content-items"><span id="custodians" class="label_large"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label class="label_large">产品名称：</label>
									<div class="content-items"><span id="names" class="label_large"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label class="label_large">产品链接：</label>
									<div class="content-items"><span id="urls" class="label_large"></span>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<div class="content-item-edit">
								<div class="logo">
									<img id="custodianLogo"/>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 管理银行：</label>
									<div class="content-items">
										<select class="form-control long" id="custodian" name="custodian" onchange="changeCustodian()">
											<option value="">请选择</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 产品名称：</label>
									<div class="content-items">
										<input class="form-control long" id="name" name="name"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>产品链接：</label>
									<div class="content-items">
										<input class="form-control long" id="url" name="url"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
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
									<div class="content-items"><span id="seriess"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>产品简称：</label>
									<div class="content-items"><span id="shortnames"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>产品编号：</label>
									<div class="content-items"><span id="scodes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>产品规模：</label>
									<div class="content-items"><span id="totalAmounts"></span>亿元
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>产品类型：</label>
									<div class="content-items"><span id="types"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>目标年化收益率：</label>
									<div class="content-items"><span id="targetAnnualizedReturnRates"></span>%
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>理财币种：</label>
									<div class="content-items"><span id="currencyTypes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>最低年化收益率：</label>
									<div class="content-items"><span id="minAnnualizedReturnRates"></span>%
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>风险等级：</label>
									<div class="content-items"><span id="riskLevels"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>收益支付方式：</label>
									<div class="content-items"><span id="paymentTypes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>面向对象：</label>
									<div class="content-items"><span id="targets"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>发行地区：</label>
									<div class="content-items"><span id="areas"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-6 noBorder">
									<label class="label_left"><b class="red">*</b> 产品系列：</label>
									<div class="content-items">
										<input class="form-control" id="series" name="series"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label class="label_left">产品简称：</label>
									<div class="content-items">
										<input class="form-control" id="shortname" name="shortname"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label class="label_left"><b class="red">*</b> 产品编号：</label>
									<div class="content-items">
										<input class="form-control" id="scodess" name="scode"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label class="label_left">产品规模：</label>
									<div class="content-items">
										<input class="form-control" id="totalAmount" name="totalAmount"/><i>亿元</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label class="label_left"><b class="red">*</b> 产品类型：</label>
									<div class="content-items">
										<select class="form-control" name="type" id="type">
											<option value="income">固定收益</option>
											<option value="floatingIncome">保本浮动收益</option>
											<option value="unfloatingIncome">非保本浮动收益</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label class="label_left"><b class="red">*</b> 目标年化收益率：</label>
									<div class="content-items">
										<input class="form-control" id="targetAnnualizedReturnRate" name="targetAnnualizedReturnRate"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 理财币种：</label>
									<div class="content-items">
										<select class="form-control" id="currencyType" name="currencyType">
											<option value="rmb">人民币</option>
											<option value="dollar">外币</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left">最低年化收益率：</label>
									<div class="content-items">
										<input class="form-control" id="minAnnualizedReturnRate" name="minAnnualizedReturnRate"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 风险等级：</label>
									<div class="content-items">
										<select class="form-control" id="riskLevel" name="riskLevel">
											<option value="R1">R1（谨慎型）</option>
											<option value="R2">R2（稳健型）</option>
											<option value="R3">R3（平衡型）</option>
											<option value="R4">R4（进取型）</option>
											<option value="R5">R5（激进型）</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 收益支付方式：</label>
									<div class="content-items">
										<select class="form-control" id="paymentType" name="paymentType">
											<option value="last">到期全部支付</option>
											<option value="month">按月支付</option>
											<option value="day">按日支付</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label class="label_left"><b class="red">*</b> 面向对象：</label>
									<div class="content-items">
										<select class="form-control" name="target" id="target">
											<option value="individual">个人</option>
											<option value="enterprise">企业</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 发行地区：</label>
									<div class="content-items">
										<select class="form-control" id="area" name="area">
											<option value="">请选择</option>
										</select>
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
									<div class="content-items"><span id="flagFlexibles"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>申购状态：</label>
									<div class="content-items"><span id="flagPurchases"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>赎回状态：</label>
									<div class="content-items"><span id="flagRedemptions"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>认购时间：</label>
									<div class="content-items">
										<span id="collectStarttimes"></span> 至 <span id="collectEndtimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>登记日：</label>
									<div class="content-items"><span id="recordDates"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>起息/结束日：</label>
									<div class="content-items"><span id="valueDates"></span>
									 至
									<span id="maturityDates"></span>
									 产品期限：<span id="terms"></span>天
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>最小投资递增：</label>
									<div class="content-items">
										<span id="minInvestAmountAdds"></span>元
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>最小投资金额：</label>
									<div class="content-items">
										<span id="minInvestAmounts"></span>元
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>最大投资金额：</label>
									<div class="content-items">
										<span id="maxInvestAmounts"></span>元
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 灵活期限：</label>
									<div class="content-items">
										<select class="form-control" name="flagFlexible" id="flagFlexible">
											<option value="false">否</option>
											<option value="true">是</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 申购状态：</label>
									<div class="content-items">
										<select class="form-control" name="flagPurchase" id="flagPurchase">
											<option value="false">不可在投资期间申购</option>
											<option value="true">可在投资期间申购</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 赎回状态：</label>
									<div class="content-items">
										<select class="form-control" id="flagRedemption" name="flagRedemption">
											<option value="false">不可提前赎回</option>
											<option value="true">可提前赎回</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 认购时间：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="collectStarttime" name="collectStarttime" data-provide="datepicker"/>
										 至
										<input class="form-control datepicker" id="collectEndtime" name="collectEndtime" data-provide="datepicker"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 登记日：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="recordDate" name="recordDate" data-provide="datepicker"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 起息/结束日：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="valueDate" name="valueDate" data-provide="datepicker"/>
										 至
										<input class="form-control datepicker" id="maturityDate" name="maturityDate" data-provide="datepicker"/>
										 产品期限：<input readonly="readonly" class="form-control short" id="term" name="term"/><i>天</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 最小投资金额：</label>
									<div class="content-items">
										<input class="form-control" id="minInvestAmount" name="minInvestAmount"/><i>元</i>
									</div>
									<span id="minInvestAmount_big" style="color:green;"></span>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 最小投资递增：</label>
									<div class="content-items">
										<input class="form-control" id="minInvestAmountAdd" name="minInvestAmountAdd"/><i>元</i>
									</div>
									<span id="minInvestAmountAdd_big" style="color:green;"></span>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>最大投资金额：</label>
									<div class="content-items">
										<input class="form-control" id="maxInvestAmount" name="maxInvestAmount"/><i>元</i>
									</div>
									<span id="maxInvestAmount_big" style="color:green;"></span>
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

								<div class="form-group col-md-6">
									<label>认购费率：</label>
									<div class="content-items"><span id="subscribeFees"></span>%
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>申购费率：</label>
									<div class="content-items"><span id="purchaseFees"></span>%
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>赎回费率：</label>
									<div class="content-items"><span id="redemingFees"></span>%
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>托管费率：</label>
									<div class="content-items"><span id="custodyFees"></span>%
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label>销售费率：</label>
									<div class="content-items"><span id="networkFees"></span>%
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label>管理费率：</label>
									<div class="content-items"><span id="managementFees"></span>%
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 认购费率：</label>
									<div class="content-items">
										<input class="form-control" id="subscribeFee" name="subscribeFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 申购费率：</label>
									<div class="content-items">
										<input class="form-control" id="purchaseFee" name="purchaseFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 赎回费率：</label>
									<div class="content-items">
										<input class="form-control" id="redemingFee" name="redemingFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 托管费率：</label>
									<div class="content-items">
										<input class="form-control" id="custodyFee" name="custodyFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 销售费率：</label>
									<div class="content-items">
										<input class="form-control" id="networkFee" name="networkFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label class="label_left"><b class="red">*</b> 管理费率：</label>
									<div class="content-items">
										<input class="form-control" id="managementFee" name="managementFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="contentDiv">
						<p class="title">其他信息</p>
						<div class="content-item">
							<!-- 信息 -->
							<div class="content-item-info" style="margin-top:15px;">
								<div class="form-group col-md-12 noBorder">
									<label>投资范围：</label>
									<div class="content-items text_large">
										<span id="investScopes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>产品收益说明：</label>
									<div class="content-items text_large">
										<span id="revenueFeatures"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>产品更多描述：</label>
									<div class="content-items text_large">
										<span id="remarks"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>产品说明书：</label>
									<div class="content-items text_large">
										<a id="documentsLink" href="javascript:void(0);"><span id="documents"></span></a>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-12">
									<label class="label_left"><b class="red">*</b> 投资范围：</label>
									<div id="investScope"></div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label class="label_left"><b class="red">*</b> 产品收益说明：</label>
									<div id="revenueFeature"></div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label class="label_left">产品更多描述：</label>
									<div id="remark"></div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label class="label_left">产品说明书：</label>
									<div class="col-md-9" style="text-align:left;line-height:38px;">
										<a id="imageShow" style="border:0;max-width:100%;max-height:150px;margin-top:15px;display: none;"></a>
										<div id="resourceId">

										</div>
									</div>
									<div id="resourceAdd"><input type="hidden" name="document" id="document" value=""></div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="text-center">
						<a class="btn sureBtn edit">修改</a>
					</div>
					<div class="btnGroup text-center save">
						<button class="btn sureBtn submitBtn" type="submit">提交</button>
						<button class="btn cancleBtn" type="button" onclick="window.close();">取消</button>
					</div>
				</div>
				</form:form>
			</div>
			<div class="clear"></div>
		</div>

		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jquery.uploadfile.min.js"></script>
		<script type="text/javascript" src="js/tinymce/dropzone/lib/dropzone.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce.min.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce_zhcn.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce_tool.js"></script>
		<script type="text/javascript" src="./js/bankFinancialProductCopy.js" ></script>
		<script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/changeMoneyToChinese.js"></script>

	</body>
</html>

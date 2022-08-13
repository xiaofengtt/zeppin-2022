<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" href="css/fundEdit.css" />
		<link rel="stylesheet" href="css/datepicker3.css" >
		<link rel="stylesheet" href="css/uploadfile.css">
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00300032" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="bankFinancialProductPublishList.jsp">理财产品发布管理</a><span>></span><a class="current" id="titlename"></a></div>
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
									<label>资金托管方：</label>
									<div class="content-items">
										<span id="custodians"></span>
										<input class="form-control hidden" id="custodian" name="custodian"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label>产品名称：</label>
									<div class="content-items">
										<span id="names"></span>
										<input class="form-control hidden" id="name" name="name"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label>产品链接：</label>
									<div class="content-items">
										<span id="urls"></span>
										<input class="form-control hidden" id="url" name="url"/>
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
								<div class="form-group col-md-6">
									<label>产品系列：</label>
									<div class="content-items">
										<span id="seriess"></span>
										<input class="form-control hidden" id="series" name="series"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>产品简称：</label>
									<div class="content-items">
										<span id="shortnames"></span>
										<input class="form-control hidden" id="shortname" name="shortname"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>产品编号：</label>
									<div class="content-items">
										<span id="scodes"></span>
										<input class="form-control hidden" id="scodess" name="scode"/>
									</div>
									<div class="clear"></div>
								</div>	
								<div class="form-group col-md-6">
									<label>产品规模：</label>
									<div class="content-items">
										<span id="totalAmounts"></span>亿元
										<input class="form-control hidden" id="totalAmount" name="totalAmount"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>产品类型：</label>
									<div class="content-items">
										<span id="types"></span>
										<input class="form-control hidden" id="type" name="type"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>目标年化收益率：</label>
									<div class="content-items">
										<span id="targetAnnualizedReturnRates"></span>%
										<input class="form-control hidden" id="targetAnnualizedReturnRate" name="targetAnnualizedReturnRate"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>理财币种：</label>
									<div class="content-items">
										<span id="currencyTypes"></span>
										<input class="form-control hidden" id="currencyType" name="currencyType"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>最低年化收益率：</label>
									<div class="content-items">
										<span id="minAnnualizedReturnRates"></span>%
										<input class="form-control hidden" id="minAnnualizedReturnRate" name="minAnnualizedReturnRate"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>风险等级：</label>
									<div class="content-items">
										<span id="riskLevels"></span>
										<input class="form-control hidden" id="riskLevel" name="riskLevel"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>收益支付方式：</label>
									<div class="content-items">
										<span id="paymentTypes"></span>
										<input class="form-control hidden" id="paymentType" name="paymentType"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label>面向对象：</label>
									<div class="content-items">
										<span id="targets"></span>
										<input class="form-control hidden" id="target" name="target"/>
									</div>
									<div class="clear"></div>
								</div>	
								<div class="form-group col-md-6 noBorder">
									<label>发行地区：</label>
									<div class="content-items">
										<span id="areas"></span>
										<input class="form-control hidden" id="area" name="area"/>
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
								<div class="form-group col-md-12 noBorder">
									<label>灵活期限：</label>
									<div class="content-items">
										<span id="flagFlexibles"></span>
										<input class="form-control hidden" id="flagFlexible" name="flagFlexible"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 info-list noBorder">
									<label>申购状态：</label>
									<div class="content-items"><span id="flagPurchases"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 info-list noBorder">
									<label>赎回状态：</label>
									<div class="content-items"><span id="flagRedemptions"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 info-list noBorder">
									<label>认购时间：</label>
									<div class="content-items">
										<span id="collectStarttimes"></span> 至 <span id="collectEndtimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 info-list noBorder">
									<label>登记日：</label>
									<div class="content-items"><span id="recordDates"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 edit-list noBorder">
									<label><b class="red">*</b> 申购状态：</label>
									<div class="content-items">
										<select class="form-control" name="flagPurchase" id="flagPurchase">
											<option value="false">不可在投资期间申购</option>
											<option value="true">可在投资期间申购</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 edit-list noBorder">
									<label><b class="red">*</b> 赎回状态：</label>
									<div class="content-items">
										<select class="form-control" id="flagRedemption" name="flagRedemption">
											<option value="false">不可提前赎回</option>
											<option value="true">可提前赎回</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 edit-list noBorder">
									<label><b class="red">*</b> 认购时间：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="collectStarttime" name="collectStarttime" data-provide="datepicker"/>
										 至 
										<input class="form-control datepicker" id="collectEndtime" name="collectEndtime" data-provide="datepicker"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 edit-list noBorder">
									<label><b class="red">*</b> 登记日：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="recordDate" name="recordDate" data-provide="datepicker"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>起息/结束日：</label>
									<div class="content-items">
										<span>
										<span id="valueDates"></span>
										 至 
										<span id="maturityDates"></span>
										 产品期限：
										 <span id="terms"></span>
										 天
										 </span>
										 <input class="form-control hidden" id="maturityDate" name="maturityDate"/>
										 <input class="form-control hidden" id="valueDate" name="valueDate"/>
										 <input class="form-control hidden" id="term" name="term"/>
									</div>
									<div class="clear"></div>
								</div>
								
								<div class="form-group col-md-12 info-list noBorder">
									<label>最小投资递增：</label>
									<div class="content-items"><span id="minInvestAmountAdds"></span>元
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 info-list noBorder">
									<label>最小投资金额：</label>
									<div class="content-items"><span id="minInvestAmounts"></span>元
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 info-list noBorder">
									<label>最大投资金额：</label>
									<div class="content-items"><span id="maxInvestAmounts"></span>元
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 edit-list noBorder">
									<label><b class="red">*</b> 最小投资金额：</label>
									<div class="content-items">
										<input class="form-control" id="minInvestAmount" name="minInvestAmount"/><i>元</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 edit-list noBorder">
									<label><b class="red">*</b> 最小投资递增：</label>
									<div class="content-items">
										<input class="form-control" id="minInvestAmountAdd" name="minInvestAmountAdd"/><i>元</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 edit-list noBorder">
									<label>最大投资金额：</label>
									<div class="content-items">
										<input class="form-control" id="maxInvestAmount" name="maxInvestAmount"/><i>元</i>
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
							<div class="content-item-info info-list">
								
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
							<div class="content-item-edit edit-list">
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 认购费率：</label>
									<div class="content-items">
										<input class="form-control" id="subscribeFee" name="subscribeFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 申购费率：</label>
									<div class="content-items">
										<input class="form-control" id="purchaseFee" name="purchaseFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 赎回费率：</label>
									<div class="content-items">
										<input class="form-control" id="redemingFee" name="redemingFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 托管费率：</label>
									<div class="content-items">
										<input class="form-control" id="custodyFee" name="custodyFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 销售费率：</label>
									<div class="content-items">
										<input class="form-control" id="networkFee" name="networkFee"/><i>%</i>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 管理费率：</label>
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
							<div class="content-item-info info-list" style="margin-top:15px;">
								<div class="form-group col-md-12 noBorder">
									<label>投资范围：</label>
									<div class="content-items">
										<span id="investScopes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>产品收益说明：</label>
									<div class="content-items">
										<span id="revenueFeatures"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>产品更多描述：</label>
									<div class="content-items">
										<span id="remarks"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>产品说明书：</label>
									<div class="content-items">
										<a id="documentsLink" href="#"><span id="documents"></span></a>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit edit-list">
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 投资范围：</label>
									<div id="investScope"></div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 产品收益说明：</label>
									<div id="revenueFeature"></div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>产品更多描述：</label>
									<div id="remark"></div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>产品说明书：</label>
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
					<div class="contentDiv">
						<p class="title">更多信息</p>
						<div class="content-item">
							<!-- 信息 -->
							<div class="content-item-info info-list">
								<div class="form-group col-md-12 noBorder">
									<label>保本保息状态：</label>
									<div class="content-items"><span id="guaranteeStatuss"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit edit-list">
								<div class="form-group col-md-12">
									<label>保本保息状态：</label>
									<div class="content-items">
										<select class="form-control" id="guaranteeStatus" name="guaranteeStatus">
											<option value="1">不保本</option>
											<option value="2">保本</option>
											<option value="3">保本保息</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<div class="text-center">
						<a class="btn sureBtn edit">修改</a>
					</div>
					<div class="btnGroup text-center save">
						<button class="btn sureBtn submitBtn" type="submit">提交</button>
						<button class="btn cancleBtn" type="button" onclick="window.close();">取消</button>
					</div>
					<div class="clear"></div>
				</div>
				</form:form>
			</div>
			<div class="clear"></div>
		</div>
		
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>	
		<script type="text/javascript" src="js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jquery.uploadfile.min.js"></script>
		<script type="text/javascript" src="js/tinymce/dropzone/lib/dropzone.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce.min.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce_zhcn.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce_tool.js"></script>
		<script type="text/javascript" src="js/productPublishEdit.js" ></script>
	</body>
</html>

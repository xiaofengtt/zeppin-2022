<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" href="css/fundEdit.css" />
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00000002" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="fundList.jsp">基金信息管理</a><span>></span><a class="current" id="titlename"></a></div>
				<div class="title-contain">
					<form:form id="formsubmit1" role="form" action="#" method="post">
						<input class="uuid" type="hidden" name="uuid"/>
						<input type="hidden" name="step" value="1" />
						<div class="contentDiv firstContent">
						<a class="edit allEdit">修改</a>
						<button class="save allSave" type="submit">保存</button>
						<div class="content-item">
							<div class="content-item-info">
								<div class="form-group noBorder">
									<div class="content-items"><span id="names"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label>简称</label>
									<div class="content-items"><span id="shortnames"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label>编号</label>
									<div class="content-items"><span id="scodes"></span>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<div class="content-item-edit">
								<div class="form-group col-md-6 noBorder">
									<label>产品名称：</label>
									<div class="content-items">
										<input class="form-control" id="name" name="name"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label>产品简称：</label>
									<div class="content-items">
										<input class="form-control" id="shortname" name="shortname"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label>产品编号：</label>
									<div class="content-items">
										<input class="form-control" id="scodess" name="scode"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
						</div>
					</form:form>
				</div>
				<div class="main-contain">
					<div class="contentDiv">
						<form:form id="formsubmit1" role="form" action="#" method="post">
						<p class="title">基金概况</p>
						<a class="edit">修改</a>
						<button class="save savefirst" type="submit">保存</button>
						<div class="content-item">
							<!-- 信息 -->
							<div class="content-item-info">	
								<div class="form-group col-md-6">
									<label>基金类型：</label>
									<div class="content-items"><span id="types"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>成立日期：</label>
									<div class="content-items"><span id="setuptimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>管理方：</label>
									<div class="content-items"><span id="gps"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>资金托管人：</label>
									<div class="content-items"><span id="custodians"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>分级描述：</label>
									<div class="content-items"><span id="structuredRemarks"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>投资风格：</label>
									<div class="content-items"><span id="styles"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>信用等级：</label>
									<div class="content-items"><span id="creditLevels"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>业绩等级：</label>
									<div class="content-items"><span id="performanceLevels"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>募集起始日：</label>
									<div class="content-items"><span  id="collectStarttimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>募集截止日：</label>
									<div class="content-items"><span id="collectEndtimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>日常申购起始日：</label>
									<div class="content-items"><span id="purchaseStarttimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>信息录入人：</label>
									<div class="content-items"><span></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>分级状态：</label>
									<div class="content-items"><span id="flagStructureds"></span>									
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>分级类别：</label>
									<div class="content-items"><span id="structuredTypes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>风险等级：</label>
									<div class="content-items"><span id="riskLevels"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>总募集规模：</label>
									<div class="content-items"><span id="planingScales"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label>日常申购截止日：</label>
									<div class="content-items"><span  id="purchaseEndtimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
								<!-- <div class="form-group col-md-6 noBorder">
									<label>录入时间：</label>
									<div class="content-items"><span></span>
									</div>
									<div class="clear"></div>
								</div> -->
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-6">
									<label>基金类型：</label>
									<div class="content-items">
										<select class="form-control" name="type" id="type">
											<option value="currency">货币型</option>
											<option value="bond">债券型</option>
											<option value="stock">股票型</option>
											<option value="mix">混合型</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>成立日期：</label>
									<div class="content-items">
										<input class="form-control  datepicker" id="setuptime" name="setuptime" data-provide="datepicker" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>管理方：</label>
									<div class="content-items">
										<input class="form-control" name="gp" id="gp" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>资金托管人：</label>
									<div class="content-items">
										<input class="form-control" id="custodian" name="custodian" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>分级描述：</label>
									<div class="content-items">
										<input class="form-control" id="structuredRemark" name="structuredRemark"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>投资风格：</label>
									<div class="content-items">
										<select class="form-control" name="style" id="style">
											<option value="profit">收益型</option>
											<option value="balance">平衡型</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								
								<div class="form-group col-md-6">
									<label>信用等级：</label>
									<div class="content-items">
										<select class="form-control" id="creditLevel" name="creditLevel">
											<option value="high">高</option>
											<option value="middle">中</option>
											<option value="low">低</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>业绩等级：</label>
									<div class="content-items">
										<select class="form-control" id="performanceLevel" name="performanceLevel">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>募集起始日：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="collectStarttime" name="collectStarttime" data-provide="datepicker" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>募集截止日：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="collectEndtime" name="collectEndtime" data-provide="datepicker" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>日常申购起始日：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="purchaseStarttime" name="purchaseStarttime" data-provide="datepicker" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>信息录入人：</label>
									<div class="content-items">
										<input class="form-control" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>分级状态：</label>
									<div class="content-items">
										<select class="form-control" name="flagStructured" id="flagStructured">
											<option value="false">普通型</option>
											<option value="true">分级型</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>分级类别：</label>
									<div class="content-items">
										<select class="form-control" id="structuredType" name="structuredType">
											<option value="priority">优先型</option>
											<option value="lag">劣后型</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>风险等级：</label>
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
									<label>总募集规模：</label>
									<div class="content-items">
										<input class="form-control" id="planingScale" name="" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>日常申购截止日：</label>
									<div class="content-items">
										<input class="form-control datepicker" id="purchaseEndtime" name="purchaseEndtime" data-provide="datepicker" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<!-- <div class="form-group col-md-6">
									<label>录入时间：</label>
									<div class="content-items">
										<input class="form-control datepicker" data-provide="datepicker" value="" />
									</div>
									<div class="clear"></div>
								</div> -->
							</div>
							<div class="clear"></div>
						</div>
						</form:form>
					</div>
					<div class="contentDiv">
						<p class="title">详细信息</p>
						<a class="edit">修改</a>
						<a class="save">保存</a>
						<div class="content-item">
							<!-- 信息 -->
							<div class="content-item-info" style="margin-top:15px;">
								<div class="form-group col-md-12 noBorder">
									<label class="label">投资目标</label>
									<div class="content-items"><span id="goals"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label class="label">投资理念</label>
									<div class="content-items"><span id="investIdeas"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label class="label">投资范围</label>
									<div class="content-items"><span id="investScopes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label class="label">投资策略</label>
									<div class="content-items"><span id="investStaregys"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label class="label">投资标准</label>
									<div class="content-items"><span id="investStandards"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label class="label">风险收益特征</label>
									<div class="content-items"><span id="revenueFeatures"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label class="label">风险管理工具及指标</label>
									<div class="content-items"><span id="riskManagements"></span>
									</div>
									<div class="clear"></div>
								</div>
								
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-12 noBorder">
									<label>投资目标：</label>
									<div class="content-items">
										<input class="form-control" id="goal" name="goal" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>投资理念：</label>
									<div class="content-items">
										<input class="form-control" id="investIdea" name="investIdea" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>投资范围：</label>
									<div class="content-items">
										<textarea rows="5" id="investScope" name="investScope" class="form-control"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>投资策略：</label>
									<div class="content-items">
										<textarea rows="5" id="investStaregy" name="investStaregy" class="form-control"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>投资标准：</label>
									<div class="content-items">
										<textarea rows="5" id="investStandard" name="investStandard" class="form-control"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>风险收益特征：</label>
									<div class="content-items">
										<input class="form-control" id="revenueFeature" name="revenueFeature" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12 noBorder">
									<label>风险管理工具及指标：</label>
									<div class="content-items">
										<input class="form-control" id="riskManagement" name="riskManagement" value="" />
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<div class="contentDiv">
						<p class="title">募集信息</p>
						<a class="edit edits">修改</a>
						<a class="save saves">保存</a>
						<div class="content-item">
							<!-- 信息 -->
							<div class="content-item-info">
								<div class="form-group col-md-4 noBorder">
									<label>总募集规模：</label>
									<div class="content-items"><span></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-4 noBorder">
									<label>募集起止时间：</label>
									<div class="content-items"><span></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-4 noBorder">
									<label>日常申购截止时间：</label>
									<div class="content-items"><span></span>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-4">
									<label>总募集规模：</label>
									<div class="content-items">
										<input class="form-control" value="" />
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-4">
									<label>募集起止时间：</label>
									<div class="content-items" style="width:100%;">
										<div class="inputGroup">
											<input class="form-control datepicker" data-provide="datepicker"/><span>一</span><input class="form-control datepicker" data-provide="datepicker"/>
										</div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-4">
									<label>日常申购截止时间：</label>
									<div class="content-items" style="width:100%;">
										<div class="inputGroup">
											<input class="form-control datepicker" data-provide="datepicker"/><span>一</span><input class="form-control datepicker" data-provide="datepicker"/>
										</div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<div class="contentDiv">
						<p class="title">募集信息</p>
						<a class="edit raiseEdit">修改</a>
						<a class="save raiseSave">保存</a>
						<div class="content-item">
							<div class="col-md-12">
								<h4>认购费率</h4>
								<table class="table table-hover text-center buyRate">
									<tr class="thead"><th width="40%">适用金额</th><th width="30%">费率</th><th width="30%">优惠费率</th></tr>
									<tr><td>0≤金额＜50万元</td><td>0.60%</td><td>1.0%</td></tr>
									<tr class="odd"><td>50万元≤金额＜200万元</td><td>0.50%</td><td>0.5%</td></tr>
									<tr><td>200万元≤金额＜500万元</td><td>0.20%</td><td>1.0%</td></tr>
								</table>
								<table class="table table-hover text-center buyRateEdit">
									<tr class="thead"><th width="40%">适用金额</th><th width="30%">费率</th><th width="20%">优惠费率</th><th width="10%"></th></tr>
									<tr><td><input value="0" />万元≤金额＜<input value="50" />万元</td><td><input value="0.60" />%</td>
									<td><input value="1.0" />%</td><td><a class="rateDel" onclick="rateDel(this)">删除</a>
									<a class="rateAdd" onclick="rateAdd(this)">添加</a></td></tr>
									<tr><td><input value="50" />万元≤金额＜<input value="200" />万元</td><td><input value="0.50" />%</td>
									<td><input value="0.5" />%</td><td><a class="rateDel" onclick="rateDel(this)">删除</a>
									<a class="rateAdd" onclick="rateAdd(this)">添加</a></td></tr>
									<tr><td><input value="200" />万元≤金额＜<input value="500" />万元</td><td><input value="0.20" />%</td>
									<td><input value="1.0" />%</td><td><a class="rateDel" onclick="rateDel(this)">删除</a>
									<a class="rateAdd" onclick="rateAdd(this)">添加</a></td></tr>
								</table>
								<h4>申购费率</h4>
								<table class="table table-hover text-center applyRate">
									<tr class="thead"><th width="40%">适用金额</th><th width="30%">费率</th><th width="30%">优惠费率</th></tr>
									<tr><td>0≤金额＜50万元</td><td>0.60%</td><td>1.0%</td></tr>
									<tr class="odd"><td>50万元≤金额＜200万元</td><td>0.50%</td><td>0.5%</td></tr>
									<tr><td>200万元≤金额＜500万元</td><td>0.20%</td><td>1.0%</td></tr>
								</table>
								<table class="table table-hover text-center applyRateEdit">
									<tr class="thead"><th width="40%">适用金额</th><th width="30%">费率</th><th width="20%">优惠费率</th><th width="10%"></th></tr>
									<tr><td><input value="0" />万元≤金额＜<input value="50" />万元</td><td><input value="0.60" />%</td>
									<td><input value="1.0" />%</td><td><a class="rateDel" onclick="rateDel(this)">删除</a>
									<a class="rateAdd" onclick="rateAdd(this)">添加</a></td></tr>
									<tr><td><input value="50" />万元≤金额＜<input value="200" />万元</td><td><input value="0.50" />%</td>
									<td><input value="0.5" />%</td><td><a class="rateDel" onclick="rateDel(this)">删除</a>
									<a class="rateAdd" onclick="rateAdd(this)">添加</a></td></tr>
									<tr><td><input value="200" />万元≤金额＜<input value="500" />万元</td><td><input value="0.20" />%</td>
									<td><input value="1.0" />%</td><td><a class="rateDel" onclick="rateDel(this)">删除</a>
									<a class="rateAdd" onclick="rateAdd(this)">添加</a></td></tr>
								</table>
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<!-- <div class="btnGroup text-center">
						<a class="btn sureBtn">保存</a>
						<a class="btn cancleBtn" onclick="window.location.href=history.go(-1);">返回</a>
					</div> -->
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>		
		<script type="text/javascript" src="js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/fundEdit.js" ></script>
		
	</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" type="text/css" href="css/paging.css">
		<link rel="stylesheet" href="css/fundList.css" />
		<script id="queboxTpl" type="text/template">
			<tr>
				<td><span>{{:bankFinancialProductPublishName}}</span></td>
				<td><span>{{:amount}}</span></td>
				<td><span>{{:valueDate}}</span></td>
				<td><span>{{:maturityDate}}</span></td>
				<td><span>{{:targetAnnualizedReturnRate}}%</span></td>
				<td><span>{{:createtimeCN}}</span></td>
				<td class="operation">
					{{if status=='unchecked'}}
						<a class="operaBranch" onclick="checkThis(this)" data-uuid="{{:uuid}}" data-status="checked">提交审核</a>
						<a class="operaBranch btn-edit" href="productInvestEdit.jsp?uuid={{:uuid}}" data-uuid="{{:uuid}}">修改</a>
						<a onclick="checkThis(this)" data-uuid="{{:uuid}}" data-status="deleted">删除</a>
					{{else status=='checked'}}
						{{if stage=='income'}}
							<a class="operaBranch btn-redeem" href="productInvestRedeem.jsp?uuid={{:uuid}}" data-uuid="{{:uuid}}">赎回</a>
						{{/if}}
						{{if stage=='finished'}}
							<div class="btn-group">
								<a onclick="openThis(this)">赎回详情</a>
								<div class="popover">
									<div class="popover-content">
										<p>赎回金额：{{:redeemAmount}}</p>
										<p>产品收益：{{:investIncome}}</p>
										<p>返还用户本金：{{:returnCapital}}</p>
										<p>返还用户利息：{{:returnInterest}}</p>
										<p>平台收益：{{:platfomIncome}}</p>
									</div>
								</div>
							</div>
						{{/if}}
					{{/if}}
				</td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00300033" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a class="current">理财产品投资管理</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16" style="min-width:1002px;">
					<div class="searchDiv">
						<div class="input-group">
							<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
							<label class="input-group-addon" onclick="searchBtn()"></label>
						</div>
					</div>
					<div class="condition">
						<div class="statusDiv shortStatusDiv filter">
							<label>审核状态：</label>
							<div>
								<a class="statusLight" id="all">全部<span id="allCount">(0)</span></a>
								<a id="checked">已审核<span id="checkedCount">(0)</span></a>
								<a id="unchecked">待审核<span id="uncheckedCount">(0)</span></a>
								<a id="deleted">已删除<span id="deletedCount">(0)</span></a>
							</div>							
						</div>
						<div class="statusDiv filter1">
							<label>产品状态：</label>
							<div>
								<a class="statusLight1" id="all">全部<span id="stageCount">(0)</span></a>
								<a id="unstart">待投资<span id="unstartCount">(0)</span></a>
								<a id="income">收益中<span id="incomeCount">(0)</span></a>
								<a id="finished">已完成<span id="finishedCount">(0)</span></a>
							</div>
						</div>
					</div>
					
					<table class="table text-center tableList table-striped">
						<thead>
							<tr>
								<th class="text-center" width="20%">理财产品名称</th>
								<th class="text-center" width="10%">购买金额</th>
								<th class="text-center" width="10%">起息日</th>
								<th class="text-center" width="10%">到期日</th>
								<th class="text-center" width="10%">目标年化收益</th>
								<th class="text-center" width="20%">购买时间</th>
								<th class="text-center" width="20%">操作</th>
							</tr>
						</thead>
						<tbody id="queboxCnt"></tbody>
					</table>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>

		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/productInvestList.js" ></script>
	</body>
</html>

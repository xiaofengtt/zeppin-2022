<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="css/paging.css">
		<link rel="stylesheet" href="css/fundList.css" />
		<script id="queboxTpl" type="text/template">
			<tr>
				<td><span>{{:typeCN}}</span></td>
				<td><span>{{:bankFinancialProductInvestName}}</span></td>
				<td><span>{{:creatorName}}</span></td>
				<td><span>{{:createtimeCN}}</span></td>
				<td><span>{{:statusCN}}</span></td>
				<td class="operation">
					{{if type == 'add'}}
					<div class="btn-group">
						<a onclick="openThis(event)">查看详情</a>
						<div class="popover log">
							<div class="popover-content">
								{{for newData}}
									<p>投资金额：{{:amount}}</p>
								{{/for}}
								<p>投资日期：{{:createtimeCN}}</p>
							</div>
						</div>
					</div>
					{{else type == 'delete' || type == 'check'}}
					<div class="btn-group">
						<a onclick="openThis(event)">查看详情</a>
						<div class="popover log">
							<div class="popover-content">
								{{for oldData}}
									<p>投资金额：{{:amount}}</p>
								{{/for}}
								<p>投资日期：{{:createtimeCN}}</p>
							</div>
						</div>
					</div>
					{{else type == 'edit'}}
					<div class="btn-group">
						<a onclick="openThis(event)">查看详情</a>
						<div class="popover log">
							<div class="popover-content">
								<p>投资金额：{{for oldData}}{{:amount}}{{/for}}<span class="editSpan">修改为：{{for newData}}{{:amount}}{{/for}}</span></p>
								<p>投资日期：{{:createtimeCN}}</p>
							</div>
						</div>
					</div>
					{{else type == 'redeem'}}
					<div class="btn-group">
						<a onclick="openThis(event)">查看详情</a>
						<div class="popover log">
							<div class="popover-content">
								{{for newData}}
									<p>投资金额：{{:amount}}</p>
								{{/for}}
								<p>投资日期：{{:createtimeCN}}</p>
								{{for newData}}
									<p>赎回金额：{{:redeemAmount}}</p>
									<p>产品收益：{{:investIncome}}</p>
									<p>返还用户本金：{{:returnCapital}}</p>
									<p>返还用户利息：{{:returnInterest}}</p>
									<p>平台收益：{{:platfomIncome}}</p>
								{{/for}}
							</div>
						</div>
					</div>
					{{/if}}
					{{if status =='unchecked'}}
						<div class="btn-group">
							<a class="operaBranch" onclick="openThis(event)">审核</a>
							<div class="popover">
								<div class="popover-content">
									<p><input class="form-control reason" type="text" name="reason"></p>
									<div>
										<button class="btn btn-primary" type="button" data-uuid="{{:uuid}}" data-status="checked" onclick="checkThis(this)">通过</button>
										<button class="btn btn-primary" type="button" data-uuid="{{:uuid}}" data-status="unpassed" onclick="checkThis(this)">不通过</button>
									</div>
								</div>
							</div>
						</div>
					{{else}}
						<div class="btn-group">
							<a onclick="openThis(event)">审核记录</a>
							<div class="popover log">
								<div class="popover-content">
									<p>审核人：{{:checkerName}}</p>
									<p>原因：{{:reason}}</p>
									<p>审核时间：{{:checktimeCN}}</p>
								</div>
							</div>
						</div>
					{{/if}}
				</td>
			</tr>
		</script>
		<style>
			.main-contain .tableList td{
				overflow:visible !important;
			}
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00500052" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a class="current">理财产品投资审核管理</a></div>
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
								<a id="checked">审核通过<span id="checkedCount">(0)</span></a>
								<a id="unchecked">待审核<span id="uncheckedCount">(0)</span></a>
								<a id="unpassed">审核不通过<span id="unpassedCount">(0)</span></a>
							</div>
						</div>
						<div class="statusDiv filter1">
							<label>审核类型：</label>
							<div>
								<a class="statusLight1" id="all">全部<span id="typeCount">(0)</span></a>
								<a id="add">添加<span id="addCount">(0)</span></a>
								<a id="edit">修改<span id="editCount">(0)</span></a>
								<a id="delete">删除<span id="deleteCount">(0)</span></a>
								<a id="check">审核<span id="checkCount">(0)</span></a>
								<a id="redeem">赎回<span id="redeemCount">(0)</span></a>
							</div>
						</div>
					</div>

					<table class="table text-center tableList table-striped">
						<thead>
							<tr>
								<th class="text-center" width="10%">审核操作</th>
								<th class="text-center" width="30%">产品名称</th>
								<th class="text-center" width="10%">发起人</th>
								<th class="text-center" width="15%">时间</th>
								<th class="text-center" width="10%">审核状态</th>
								<th class="text-center" width="25%">操作</th>
							</tr>
						</thead>
						<tbody id="queboxCnt"></tbody>
					</table>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/productInvestOperateList.js" ></script>
	</body>
</html>

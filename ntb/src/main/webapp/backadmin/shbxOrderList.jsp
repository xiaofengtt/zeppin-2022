<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/fundList.css" />
        <link rel="stylesheet" href="./css/base.css">
        <link rel="stylesheet" href="./css/msg_table.css">
        <link rel="stylesheet" href="./css/QCBfinanceAndTaxationOpen.css">
		<script id="queboxTpl" type="text/template">
			<tr>
				<td>{{:orderNumber}}</td>
				<td>{{:shbxInsuredName}}</td>
				<td>{{:cardinalNumberCN}}</td>
				<td>{{:insuredTypeCN}}</td>
				<td>{{:duration}}个月（{{:durationCN}}）</td>
				<td>{{:benefitsCN}}</td>
				<td>{{:serviceFeeCN}}</td>
				<td>{{:totalAmountCN}}</td>
				<td>{{:statusCN}}</td>
				<td>
					<a href="shbxOrderDetail.jsp?uuid={{:uuid}}">查看</a>
					{{if status != 'normal'}}                
						<a href="shbxOrderProcess.jsp?uuid={{:uuid}}" class="process">处理</a>
           			{{/if}}
				</td>
			</tr>
		</script>
		<style>
			.msg_table tr td p{
				margin:0;
			}
			.msg_table tr td{
				text-align: left;
				padding:10px;
			}
			.msg_table tr th{
				text-align: left;
				padding:10px;
			}
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="01000102" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">社保熊订单管理</a><span>></span><a class="current">社保熊订单管理</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16">

					<div class="condition">
						<div class="statusDiv shortStatusDiv filter">
							<label>审核状态：</label>
							<div>
								<a id="all" class="statusLight">全部<span id="allCount">(0)</span></a>
								<a id="unprocess">未处理<span id="unprocessCount">(0)</span></a>
								<a id="normal">已处理<span id="normalCount">(0)</span></a>
							</div>
						</div>

						<div class="searchDiv">
							<div class="input-group">
								<input id="search" class="form-control" type="text" value="" placeholder="搜索">
								<label class="input-group-addon"></label>
							</div>
						</div>
					</div>

					<table cellspacing="0" cellpadding="0" class="msg_table">
					    <tr class="first_tr">
                            <th width="8%">订单编号</th>
                            <th width="8%">参保人</th>
                            <th width="12%">参保基数(元)</th>
                            <th width="7%">参保类型</th>
                            <th width="19%">参保时长</th>
                            <th width="10%">代缴金额(元)</th>
                            <th width="8%">服务费(元)</th>
                            <th width="12%">支付总额(元)</th>
                            <th width="8%">订单状态</th>
                            <th width="8%">操作</th>
					    </tr>
                        <tbody id="queboxCnt">

                        </tbody>
					</table>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="./js/getHtmlDocName.js"></script>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script>
        <script type="text/javascript" src="./js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/shbxOrderList.js" ></script>

	</body>
</html>

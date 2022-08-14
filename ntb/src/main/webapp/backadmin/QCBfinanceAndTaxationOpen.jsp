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
				<td>{{:applytimeCN}}</td>
				<td>
					<p>{{:qcbCompanyMerchantId}}</p>
					<p>{{:qcbCompanyName}}</p>
				</td>
				<td>{{:qcbCompanyAreaStr}}</td>
				<td>
					<p>{{:contacts}}</p>
					<p>{{:contactsMobile}}</p>
				</td>
				<td>{{:statusCN}}</td>
				<td>
					<a href="javascript:void(0);" class="open" data-uuid="{{:uuid}}">开通</a>
				</td>
			</tr>
		</script>
		<style>
			.msg_table tr td p{
				margin:0;
			}
			.msg_table tr td{
				text-align: left;
				padding:10px 20px;
			}
			.msg_table tr th{
				text-align: left;
				padding:10px 20px;
			}
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00800082" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">财税服务开通申请</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16">

					<div class="condition">
						<div class="statusDiv shortStatusDiv filter">
							<label>审核状态：</label>
							<div>
								<a id="all" class="statusLight">全部<span id="allCount">(0)</span></a>
								<a id="unauth">未申请<span id="unauthCount">(0)</span></a>
								<a id="normal">已开通<span id="normalCount">(0)</span></a>
								<a id="uncheck">申请中<span id="uncheckCount">(0)</span></a>
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
                            <th width="15%">申请时间</th>
                            <th width="24%">企业名称</th>
                            <th width="14%">联系人</th>
                            <th width="10%">销售经理</th>
                            <th width="10%">状态</th>
                            <th width="15%">操作</th>
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
		<script type="text/javascript" src="./js/QCBfinanceAndTaxation.js" ></script>

	</body>
</html>

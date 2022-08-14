<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>投资银行理财产品</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" type="text/css" href="css/paging.css">
<!-- 		<link rel="stylesheet" href="css/fundList.css" /> -->
		<script id="queboxTpl" type="text/template">
			<div class="list-item">
				<div class="list-item-bd">
					<table class="text-center">
						<tr>
							<td rowspan="2">
								<i class="red">{{:targetAnnualizedReturnRate}}%</i>
								<p class="longP">目标年化收益率</p>
							</td>
							<td colspan="4" class="left">
								<a class="fundName" target="_blank" href="productMessage.jsp?uuid={{:uuid}}">
									<span>【{{:custodianName}}】{{:name}}({{:scode}})</span>
								</a>
							</td>
							<td rowspan="2" class="middle"><a target="_top" class="btn-add" onclick="publishAdd(this);" data-url="./investProductMessage.jsp?uuid={{:uuid}}"><i id="publish">选择</i></a></td>
						</tr>
						<tr>
							<td class="text-left"><p class="break">认购起始日<span>{{:collectStarttime}}</span></p></td>
							<td class="text-left"><p class="break">认购结束日<span>{{:collectEndtime}}</span></p></td>
							<td class="text-left"><p class="break">产品期限<span>{{:term}}天</span></p></td>
							<td class="text-left"><p class="break">起始认购<span>{{:minInvestAmount}}元</span></p></td>
						</tr>
					</table>
				</div>
			</div>
		</script>

		<script id="queboxTpls" type="text/template">
			<div class="list-item">
				<div class="list-item-bd">
					<table class="text-center">
						<tr>
							<td rowspan="2">
								<i class="red">活期</i>
								<!-- <p class="longP">目标年化收益率</p> -->
							</td>
							<td colspan="4" class="left">
								<a class="fundName" target="_blank" href="./fundEdit.jsp?uuid={{:uuid}}">
									<span>【{{:gpName}}】{{:name}}({{:scode}})</span>
								</a>
							</td>
							<td rowspan="2" class="middle"><a target="_top" class="btn-add" onclick="publishAdd(this);" data-url="./fundPublishInvest.jsp?uuid={{:uuid}}"><i id="publish">选择</i></a></td>
						</tr>
						<tr>
							<td class="text-left"><p class="break">认购起始日<span>{{:collectStarttimeCN}}</span></p></td>
							<td class="text-left"><p class="break">认购结束日<span>{{:collectEndtimeCN}}</span></p></td>
							<!-- <td class="text-left"><p class="break">产品期限<span>{{:term}}天</span></p></td>
							<td class="text-left"><p class="break">起始认购<span>{{:minInvestAmount}}元</span></p></td> -->
						</tr>
					</table>
				</div>
			</div>
		</script>
	</head>
	<body style="min-width:0;">
		<div class="layerOpen">
			<p class="titles">选择要投资的银行理财产品</p>
			<div class="contents">
				<div class="layerContent">
					<div class="searchDivs">
						<div class="input-group">
							<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
							<label class="input-group-addon" onclick="searchBtn()"></label>
						</div>
					</div>

					<div class="list-content" id="queboxCnt">

					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>

		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/query.js"></script>
		<script type="text/javascript" src="js/paging.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/highcharts.js" ></script>
		<script type="text/javascript" src="./js/selectProductList.js" ></script>
	</body>
</html>

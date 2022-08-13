<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" href="css/fundReport.css"/>
		<script id="queboxTpl" type="text/template">
			<tr>
				<td><span>{{:statistime}}</span></td>
				<td><span>{{:netValue}}</span></td>
				<td>
					<a class="editDaily" href="bankFinancialProductDailyEdit.jsp?uuid={{:uuid}}">修改</a>
					<a onclick="deleteThis(this)" data-uuid="{{:uuid}}">删除</a>
				</td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00300031" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="bankFinancialProductList.jsp">理财产品信息管理</a><span>></span><a class="current">每日净值</a></div>
				</div>
				<div class="clear"></div>
				<div class="main-contain">
					<div class="title-big">
						<span id="fundName"></span><span class="common-rating"><span class="rate-stars"></span></span>
						<select class="duration" id="duration">
							<option value="1">近一个月</option>
							<option value="3">近三个月</option>
							<option value="6">近六个月</option>
							<option value="12" selected>近一年</option>
						</select>
					</div>
					<div class="trend">
						<p class="chart-title"><span id="chart-title">近一年</span>净值走势(万元)</p>
						<div id="container" style="min-width:400px;height:400px"></div>
					</div>
					<form:form id="formsubmit" role="form" action="#" method="post">
					<div class="title">历史净值<button class="submitButton" type="submit">提交修改</button><a class="btn-add add addNew">+&ensp;录入净值</a></div>
					</form:form>
					<div class="clear"></div>
					<div class="list-content">
						<table class="table table-bordered table-hover text-center">
							<tr><th width="35%" class="text-center">日期</th><th width="35%" class="text-center">净值（元）</th><th width="30%" class="text-center">操作</th></tr>
							<tbody id="queboxCnt"></tbody>
						</table>
						<div id="pageTool"></div>
					</div>
					
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="js/highcharts.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsmap.js" ></script>
		<script type="text/javascript" src="js/bankFinancialProductDaily.js" ></script>	
	</body>
</html>

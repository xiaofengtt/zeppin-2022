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
		<link rel="stylesheet" type="text/css" href="css/paging.css">
			<style>
				.contain .contain-right .main-contain{
					padding-top:48px !important;
				}
			</style>
		<script id="queboxTpl" type="text/template">
			<div class="list-item">
				<div class="list-item-hd">
					<table>
						<tr height="37px">
							<td width="70%">
								<a class="fundName" href="./fundPublishEdit.jsp?uuid={{:uuid}}"><span>【{{:name}}】({{:scode}})</span></a>
							</td>
							<td width="30%" class="text-right">
								<a class="btn" href="./fundPublishDaily.jsp?uuid={{:uuid}}">查看净值</a>
                                <a class="btn" href="./fundPublishEdit.jsp?uuid={{:uuid}}">修改</a>
							</td>
						</tr>
					</table>
				</div>
				<div class="list-item-bd">
					<table class="text-center">
						<tr>
							<td width="14%">
								<div>
									<p>投资风格</p>
									<i>{{:styleCN}}</i>
									<b></b>
									<!-- <span class="data">2016-09-25</span> -->
								</div>
							</td>
							<td width="14%">
								<div>
									<p>募集总规模</p>
									<i>{{:planingScale}}<span>元</span></i>
									<b></b>
									<!-- <span>近一个月</span> -->
								</div>
							</td>
							<td width="14%">
								<div>
									<p>信用等级</p>
									<i>{{:creditLevelCN}}</i>
									<b></b>
									<!-- <span>近三个月</span> -->
								</div>
							</td>
							<td width="14%">
								<div>
									<p>分级</p>
									<i>{{:riskLevel}}</i>
									<b></b>
									<!-- <span></span> -->
								</div>
							</td>
							<td width="14%">
								<div>
									<p>活期理财类型</p>
									<i>
										{{:typeCN}}
									</i>
									<b></b>
									<!-- <span></span> -->
								</div>
							</td>
							<td width="14%">
								<div>
									<p>业务等级</p>
									<i>
										{{:performanceLevel}}
									</i>
									<b></b>
									<span></span>
								</div>
							</td>
							<td width="14%">
								<div>
									<p>分级状态</p>
									<i>
										{{:flagStructuredCN}}
									</i>
									<b></b>
									<span></span>
								</div>
							</td>


						</tr>
					</table>
				</div>
				<div class="list-item-ft">
					<table class="table">
					  	<tr class="table_tr">
							<td>持有记录：<a href="./QCBcollectMessage.jsp?uuid={{:uuid}}" class="productMessage">详细</a></td>
							<td>交易记录：<a href="./QCBcollectHistoryList.jsp?uuid={{:uuid}}" class="productMessage">详细</a></td>
							<td>{{:createtimeCN}} {{:creatorName}}创建</td>
					  	</tr>
					</table>
				</div>
			</div>


			<!-- dklasjhdisahdjkashdisahdiashdu -->


		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00900092" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a class="current">活期理财发布管理</a></div><div class="clear"></div></div>
				<div class="main-contain">
					<%-- <div class="statusDiv col-md-8">
						<a class="statusLight" id="checked">已审核<span id="checkedCount">（0）</span></a>
						<a id="unchecked">待审核<span id="uncheckedCount">（0）</span></a>
						<a id="unpassed">未通过<span id="unpassedCount">（0）</span></a>
					</div> --%>
					<div class="searchDiv">
						<div class="input-group">
							<input id="search" class="form-control" type="text" value="" placeholder="请输入搜索内容" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
							<label class="input-group-addon" onclick="searchBtn()"></label>
						</div>
					</div>
					<div class="clear"></div>
					<div class="list-content" id="queboxCnt">

					</div>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/highcharts.js" ></script>
		<script type="text/javascript" src="js/jquery.simplePagination.js"></script>
		<script type="text/javascript" src="js/query.js"></script>
		<script type="text/javascript" src="js/paging.js"></script>
		<script type="text/javascript" src="./js/fundPublishList.js" ></script>
	</body>
</html>

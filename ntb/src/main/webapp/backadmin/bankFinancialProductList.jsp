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
			<div class="list-item">
				<div class="list-item-hd">
					<table>
						<tr height="37px">
							{{if stage=='unstart'}}
							<td width="50px">
								<span class="stageTag back-white">未开始</span>
							</td>
							{{else stage=='collect'}}
							<td width="50px">
								<span class="stageTag back-blue">募集中</span>
							</td>
							{{else stage=='income'}}
							<td width="50px">
								<span class="stageTag back-green">收益中</span>
							</td>
							{{else stage=='finished'}}
							<td width="50px">
								<span class="stageTag back-grey">已过期</span>
							</td>
							{{/if}}
							<td width="70%">
								<a class="fundName" href="./bankFinancialProductEdit.jsp?uuid={{:uuid}}"><span>【{{:custodianName}}】{{:name}}({{:scode}})</span></a>
							</td>
							<td width="30%" class="text-right">
									<a class="btn" href="./bankFinancialProductCopy.jsp?uuid={{:uuid}}">添加</a>
								{{if status=='checked'}}
									<a class="btn" href="bankFinancialProductDaily.jsp?uuid={{:uuid}}">每日净值</a>
									{{if !isPublish}}
										<a class="btn" href="./productPublishAdd.jsp?uuid={{:uuid}}">发布</a>
									{{/if}}
								{{/if}}
							</td>
						</tr>
					</table>
				</div>
				<div class="list-item-bd">
					<table class="text-center">
						<tr>
							<td width="15%">
								<p class="longP">目标年化收益率</p>
								<i class="red">{{:targetAnnualizedReturnRate}}<span>%</span></i>
							</td>
							<td width="10%">
								<p class="longP">产品期限</p>
								<i>{{:term}}<span>天</span></i>
							</td>
							<td width="15%">
								<p class="longP">认购起始日</p>
								<i>{{:collectStarttime}}</i>
							</td>
							<td width="15%">
								<p class="longP">认购结束日</p>
								<i>{{:collectEndtime}}</i>
							</td>
							<td width="20%">
								<p class="longP">资金保障</p>
								<i>{{:guaranteeStatusCN}}</i>
							</td>
							<td width="15%">
								<p class="longP">面向对象</p>
								<i>{{:targetCN}}</i>
							</td>
							<td width="15%" class="noBorder">
								<p class="longP">当前净值</p>
								<i>{{:netWorth}}</i>
								<span>{{:netWorthTime}}</span>
							</td>

						</tr>
						<tr>

						</tr>
					</table>
				</div>
				<div class="list-item-ft">
					<table class="table">
						<tr class="table_tr">
							<td>最小投资金额：{{:minInvestAmountCN}}元</td>
							<td>最小投资递增金额：{{:minInvestAmountAddCN}}元</td>
							<td>风险等级：{{:riskLevelCN}}</td>
							<td>销售地区：{{:area}}</td>
							<td>产品到期日：{{:maturityDate}}</td>
							<td>{{:createtime}} {{:creator}}创建</td>
						</tr>
					</table>
				</div>
			</div>
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
					<div class="locationLeft"><a class="current">理财产品信息管理</a></div>
					<a class="btn-add add addNew" target="_blank" href="../backadmin/bankFinancialProductAdd.jsp" style="margin-top:0px;">+&ensp;理财产品</a>
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
						<div class="statusDiv filter filter-stage">
							<label>产品状态：</label>
							<div>
								<a class="statusLight" id="all">全部<span id="stageCount">(0)</span></a>
								<a id="unstart">未开始<span id="unstartCount">(0)</span></a>
								<a id="collect">募集中<span id="collectCount">(0)</span></a>
								<a id="income">收益中<span id="incomeCount">(0)</span></a>
								<a id="finished">已过期<span id="finishedCount">(0)</span></a>
							</div>
						</div>
						<div class="hide" id="moreFilter">
							<div class="statusDiv filter filter-income">
								<label>预期收益：</label>
								<div>
									<a class="statusLight" id="all">全部</a>
									<a id="1">3%以下</a>
									<a id="2">3%-4%</a>
									<a id="3">4%-5%</a>
									<a id="4">5%-6%</a>
									<a id="5">6%以上</a>
								</div>
							</div>
							<div class="statusDiv filter filter-term">
								<label>产品期限：</label>
								<div>
									<a class="statusLight" id="all">全部</a>
									<a id="1">60天以下</a>
									<a id="2">61-120天</a>
									<a id="3">121-180天</a>
									<a id="4">181-365天</a>
									<a id="5">365天以上</a>
								</div>
							</div>
							<div class="statusDiv filter filter-custodian">
								<label style="margin-top:10px">管理银行：</label>
                            <div id="custodians" style="width:80%;vertical-align:top;">
                                <a class="statusLight" id="all">全部</a>

                            </div>
							</div>
							<div class="statusDiv filter filter-type">
								<label>产品类型：</label>
								<div>
									<a class="statusLight" id="all">全部</a>
									<a id="income">固定收益</a>
									<a id="floatingIncome">保本浮动收益</a>
									<a id="unfloatingIncome">非保本浮动收益</a>
								</div>
							</div>
							<div class="statusDiv filter filter-riskLevel">
								<label>风险等级：</label>
								<div>
									<a class="statusLight" id="all">全部</a>
									<a id="R1">R1（谨慎型）</a>
									<a id="R2">R2（稳健型）</a>
									<a id="R3">R3（平衡型）</a>
									<a id="R4">R4（进取型）</a>
									<a id="R5">R5（激进型）</a>
								</div>
							</div>
							<div class="statusDiv filter filter-redeem">
								<label>赎回状态：</label>
								<div>
									<a class="statusLight" id="all">全部</a>
									<a id="1">可提前赎回</a>
									<a id="0">不可提前赎回</a>
								</div>
							</div>
						</div>
						<div class="text-center">
							<a onclick="filterControl(this)" id="filterController">展开</a>
						</div>
					</div>
					<div class="sort">
						<div class="order-option">排序:</div>
						<ul class="sorting-btns">
							<li class="light desc" id="sorting-default"><a data-name="createtime"><span>默认</span></a></li>
							<li id="sorting-collectStarttime"><a data-name="collect_starttime"><span>发售时间</span></a></li>
							<li id="sorting-targetAnnualizedReturnRate" class=""><a data-name="target_annualized_return_rate"><span>收益率</span></a></li>
							<li id="sorting-maturityDate" class=""><a data-name="maturity_date"><span>产品到期日</span></a></li>
							<li id="sorting-term" class=""><a data-name="term"><span>理财期限</span></a></li>

						</ul>
					</div>

					<div class="list-content" id="queboxCnt">

					</div>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="./js/getHtmlDocName.js"></script>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/highcharts.js" ></script>
		<script type="text/javascript" src="./js/bankFinancialProductList.js" ></script>
	</body>
</html>

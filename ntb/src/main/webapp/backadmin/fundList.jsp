<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" href="css/fundList.css" />
		<link rel="stylesheet" type="text/css" href="css/paging.css">
		<script id="queboxTpl" type="text/template">
			<div class="list-item">
				<div class="list-item-hd">
					<table>
						<tr>
							<td width="50%"><a class="fundName" href="fundEdit.jsp?uuid={{:uuid}}">{{:scode}}-{{:name}}</a><span class="common-rating"><span class="rate-stars"></span></span></td>
							<td width="50%" class="text-right">
								{{if status=='checked'}}
									<a class="btn" href="fundReport.jsp?uuid={{:uuid}}">每日净值</a>
									<a class="btn callOff" onclick="checkThis(this)" data-uuid="{{:uuid}}" data-status="unchecked">取消审核</a>
								{{else status=='unchecked'}}
									<a class="btn callOff" onclick="checkThis(this)" data-uuid="{{:uuid}}" data-status="checked">审核通过</a>
									<a class="btn callOff" onclick="checkThis(this)" data-uuid="{{:uuid}}" data-status="unpassed">审核不通过</a>
								{{else status=='unpassed'}}
									<a class="btn callOff" onclick="checkThis(this)" data-uuid="{{:uuid}}" data-status="unchecked">重新审核</a>
									<a class="btn callOff" onclick="checkThis(this)" data-uuid="{{:uuid}}" data-status="checked">审核通过</a>
									<a class="btn callOff" onclick="checkThis(this)" data-uuid="{{:uuid}}" data-status="deleted">删除</a>
								{{/if}}
							</td>
						</tr>
					</table>
				</div>
				<div class="list-item-bd">
					<table class="text-center">
						<tr>
							<td width="14%">
								<div>
									<p>当前净值</p>
									<i>1.3180</i>
									<b></b>
									<span class="data">2016-09-25</span>
								</div>
							</td>
							<td width="14%">
								<div>
									<p>涨跌幅</p>
									<i>0.56<span>%</span></i>
									<b></b>
									<span>近一个月</span>
								</div>
							</td>
							<td width="14%">
								<div>
									<p>涨跌幅</p>
									<i>2.48<span>%</span></i>
									<b></b>
									<span>近三个月</span>
								</div>
							</td>
							<td width="14%">
								<div>
									<p>分级</p>
									<i>{{:riskLevel}}</i>	
									<span></span>
								</div>
							</td>
							<td width="14%">
								<div>
									<p>基金类型</p>
									<i>
										{{if type=='currency'}}
											货币型
										{{else type=='bond'}}
											债券型
										{{else type=='stock'}}
											股票型
										{{else type=='mix'}}
											混合型
										{{/if}}
									</i>
									<span></span>
								</div>
							</td>
							<td width="30%" class="noBorder">
								<div>
									<div class="trend">
										<span class="col-md-8 text-left">近一年净值走势（百分比）</span>
										<i class="col-md-4 text-right">6.45<span>%</span></i>
										<div class="clear"></div>
									</div>
									<div>
										<div id="container" style="min-width:100%;height:100px"></div>
									</div>
									
								</div>
							</td>
							
						</tr>
					</table>
				</div>
			</div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00000002" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a class="current">基金信息管理</a></div><a class="btn-add add addNew" href="../backadmin/fundAdd.jsp" style="margin-top:0px;">+&ensp;添加基金</a><div class="clear"></div></div>
				<div class="main-contain">
					<div class="statusDiv col-md-8">
						<a class="statusLight" id="checked">已审核<span id="checkedCount">（0）</span></a>
						<a id="unchecked">待审核<span id="uncheckedCount">（0）</span></a>
						<a id="unpassed">未通过<span id="unpassedCount">（0）</span></a>
					</div>
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
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/highcharts.js" ></script>
		<script type="text/javascript" src="js/jquery.simplePagination.js"></script>		
		<script type="text/javascript" src="js/query.js"></script>		
		<script type="text/javascript" src="js/paging.js"></script>
		<script type="text/javascript" src="js/fundList.js" ></script>
	</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<script id="queboxTpl" type="text/template">
			<tr>
				<td class="nameTd"><span>{{:nickname}}</span></td>
				<td><span>{{:statusCN}}</span></td>
				<td><span>{{:totalAmount}}</span></td>
				<td><span>{{:totalReturn}}</span></td>
				<td><span>{{:accountBalance}}</span></td>
				<td><span>{{:createtimeCN}}</span></td>
				<td class="operation">
					<a class="editBtn btn-edit" href="investorDetail.jsp?uuid={{:uuid}}">详情</a>
				</td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100015" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">后台用户管理</a><span>></span><a class="current">投资者用户管理</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain">
					<div class="searchDiv">
						<div class="input-group">
							<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
							<label class="input-group-addon" onclick="searchBtn()"></label>
						</div>
					</div>
					<table class="table table-hover text-center table-striped tableList mt-70">
						<thead>
							<tr>
								<th class="nameTh" width="15%">昵称</th>
								<th class="text-center" width="10%">状态</th>
								<th class="text-center" width="15%">账户总资产（元）</th>
								<th class="text-center" width="20%">历史总收益（元）</th>
								<th class="text-center" width="15%">账户余额（元）</th>
								<th class="text-center" width="20%">创建时间</th>
								<th class="text-center" width="15%">操作</th>
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
		<script type="text/javascript" src="js/investorList.js" ></script>
		
	</body>
</html>

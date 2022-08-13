<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" href="css/branchBankList.css" />
		<script id="queboxTpl" type="text/template">
			<tr class="{{if #index % 2 == 0}}odd-data{{else}}even-data{{/if}}">
				<td class="nameTd"><span>{{:name}}</span></td>
				<td class="nameTd"><span>{{:bankName}}</span></td>
				<td><span>{{if status=='normal'}}正常{{/if}}{{if status=='disable'}}停用{{/if}}</span></td>
				<td class="operation">
					<a class="deleteBtn btn-remove" onclick="deleteThis(this)" data-url="../rest/backadmin/branchBank/delete?uuid={{:uuid}}">删除</a>
					<a class="editBtn btn-edit operaBranch" href="branchBankEdit.jsp?uuid={{:uuid}}">修改</a>
				</td>
			</tr>
			<tr class="{{if #index % 2 == 0}}odd-address{{else}}even-address{{/if}}">
				<td colspan="4"><span>{{:address}}</span></td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00000001" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a href="bankInfoList.jsp">银行信息管理</a><span>></span><a class="current">支行信息管理</a></div>
					<a class="btn-add add addNew" id="addButton" href="" style="margin-top:0px;">+&ensp;添加支行</a>
					<div class="clear"></div>
				</div>
				<div class="main-contain">
					<table class="table text-center tableList branch-bank-table">
						<thead>
							<tr>
								<th class="nameTh" width="30%">支行名称</th>
								<th class="nameTh" width="30%">所属银行</th>
								<th class="text-center" width="20%">状态</th>
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
		<script type="text/javascript" src="js/branchBankList.js" ></script>
	</body>
</html>

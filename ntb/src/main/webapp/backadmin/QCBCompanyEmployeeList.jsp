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
				<td>{{:name}}</td>
				<td>{{:mobile}}</td>
				<td>{{:statusCN}}</td>
				<td>{{:accountBalance}}</td>
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
		<input id="scode" type="hidden" value="00800081" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="./QCBcompanyList.jsp">企业用户管理</a><span>></span><a class="current">企业员工列表</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16">

					<div class="condition">
						<div class="statusDiv shortStatusDiv filter">
							<label>余额状态：</label>
							<div>
								<a id="" class="statusLight">全部</a>
								<a id="1">未提现</a>
							</div>
						</div>
					</div>

					<table cellspacing="0" cellpadding="0" class="msg_table">
					    <tr class="first_tr">
                            <th width="15%">员工姓名</th>
                            <th width="15%">手机号码</th>
                            <th width="15%">状态</th>
                            <th width="15%">剩余金额</th>
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
		<script type="text/javascript" src="./js/QCBCompanyEmployeeList.js" ></script>

	</body>
</html>

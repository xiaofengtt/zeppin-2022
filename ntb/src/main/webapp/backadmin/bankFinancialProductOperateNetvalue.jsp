<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" href="css/fundEdit.css" />
		<link rel="stylesheet" href="css/uploadfile.css">
		<script id="queboxTpl" type="text/template">
			<tr>
				<td>{{:statistimeCN}}</td>
				<td>
				{{if type=='edit'}}
					{{:oldValue}}<span class="editSpan">变更为：{{:bankFinancialProductDaily.netValue}}</span>
				{{else}}
					{{:bankFinancialProductDaily.netValue}}
				{{/if}}
				</td>
				<td>{{:typeCN}}</td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00400044" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="bankFinancialProductOperateList.jsp">理财产品信息操作管理</a><span>></span><a id="titlename"></a><span>></span><a class="current" id="titlename">净值</a></div>
				<div class="title-contain">
					<div class="content-item">
						<div class="content-item-info">
							<div class="form-group noBorder">
								<div class="content-items">
									<div class="content-items">
										【<span id="custodian"></span>】<span id="name"></span>（<span id="ascode"></span>）
									</div>
								</div>
								<div class="clear"></div>
							</div>
							<div class="list-content">
								<table class="table table-bordered table-hover text-center">
									<tr><th width="35%" class="text-center">日期</th><th width="35%" class="text-center">净值（元）</th><th width="30%" class="text-center">操作类型</th></tr>
									<tbody id="queboxCnt"></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/bankFinancialProductOperateNetvalue.js" ></script>
		
	</body>
</html>

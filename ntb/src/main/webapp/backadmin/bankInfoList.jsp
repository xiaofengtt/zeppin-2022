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
				<td class="nameTd"><span>{{:name}}</span></td>
				<td><img class="bankLogo" src="..{{:logoUrl}}" alt="{{:name}}"/></td>
				<td><span>{{if status=='normal'}}正常{{/if}}{{if status=='disable'}}停用{{/if}}</span></td>
				<td class="operation">	
					<a class="deleteBtn btn-remove" onclick="deleteThis(this)" data-url="../rest/backadmin/bank/delete?uuid={{:uuid}}">删除</a>				
					<a class="editBtn" href="bankInfoEdit.jsp?uuid={{:uuid}}">修改</a>
					<a class="operaBranch" href="branchBankList.jsp?uuid={{:uuid}}">管理支行</a>
				</td>
			</tr>
		</script>
		<style>
			.table-striped > tbody > tr{border: 1px solid #ddd;}
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00000001" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a class="current">银行信息管理</a></div><a class="btn-add add addNew" href="../backadmin/bankInfoAdd.jsp" style="margin-top:0px;">+&ensp;添加银行</a><div class="clear"></div></div>
				<div class="main-contain">					
					<table class="table table-hover text-center table-striped tableList PFSCR">
						<thead>
							<tr>
								<th class="nameTh" width="26.4368%">名称</th>
								<th class="text-center logoTh" width="15.031%">LOGO</th>
								<th class="text-center" width="25.5526%">状态</th>
								<th class="text-center" width="">操作</th>
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
		<script type="text/javascript" src="js/jquery.simplePagination.js"></script>
		<script type="text/javascript" src="js/query.js"></script>
		<script type="text/javascript" src="js/paging.js"></script>
		<script type="text/javascript" src="js/bankInfoList.js" ></script>
		
	</body>
</html>


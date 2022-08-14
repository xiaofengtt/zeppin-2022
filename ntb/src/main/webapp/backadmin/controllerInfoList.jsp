<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<script id="queboxTpl" type="text/template">
			<tr>
				<td class="nameTd"><span>{{:name}}</span></td>
				<td><span>{{:description}}</span></td>
				<td><span>{{:sort}}</span></td>
				<td class="operation">
					<a class="deleteBtn btn-remove" onclick="deleteThis(this)" data-url="../rest/backadmin/controllerMethod/controllerdelete?uuid={{:uuid}}">删除</a>
					<a class="operaBranch editBtn btn-edit" href="controllerInfoEdit.jsp?uuid={{:uuid}}">修改</a>
					<a class="operaBranch" href="methodInfoList.jsp?uuid={{:uuid}}">管理方法</a>
				</td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00200022" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a class="current">功能信息管理</a></div>
					<a class="btn-add add addNew" href="../backadmin/controllerInfoAdd.jsp" style="margin-top:0px;">+&ensp;添加功能</a>
					<div class="clear"></div>
				</div>
				<div class="main-contain">
					<table class="table table-hover text-center table-striped tableList">
						<thead>
							<tr>
								<th class="nameTh" width="25.26%">功能名称</th>
								<th class="text-center" width="25.3%">功能描述</th>
								<th class="text-center" width="15.3%">默认排序</th>
								<th class="text-center" width="24.21%">操作</th>
							</tr>
						</thead>
						<tbody id="queboxCnt"></tbody>
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
		<script type="text/javascript" src="./js/controllerInfoList.js"></script>
	</body>
</html>

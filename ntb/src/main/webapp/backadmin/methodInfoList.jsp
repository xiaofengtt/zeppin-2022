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
				<td><span>{{:controllerName}}</span></td>
				<td><span>{{:description}}</span></td>
				<td class="operation">
					<a class="deleteBtn btn-remove" onclick="deleteThis(this)" data-url="../rest/backadmin/controllerMethod/methoddelete?uuid={{:uuid}}">删除</a>
					<a class="operaBranch editBtn btn-edit" href="methodInfoEdit.jsp?uuid={{:uuid}}">修改</a>
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
				<div class="location">
					<div class="locationLeft">
						<a href="controllerInfoList.jsp">功能信息管理</a><span>></span><a class="current">方法信息管理</a>
					</div>
					<a class="btn-add add addNew" id="addButton" href="" style="margin-top:0px;">+&ensp;添加方法</a>
					<div class="clear"></div>
					</div>
				
				<div class="main-contain">
<!-- 					 -->
<!-- 					<table class="table table-hover text-center table-striped"> -->
					<table class="table table-hover text-center table-striped tableList">
						<thead>
							<tr>
								<th class="nameTh" width="25.28%">方法名称</th>
								<th class="text-center" width="20.28%">所属功能</th>
								<th class="text-center" width="30.28%">方法描述</th> 
								<th class="text-center" width="13.06%">操作</th>
							</tr>
						</thead>
						<tbody id="queboxCnt"></tbody>
					</table>
<!-- 					<div class=" pagination  compact-theme" id="pagnationPaper"></div> -->
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/methodInfoList.js" ></script>
	</body>
</html>

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
				<td class="nameTd" title="{{:name}}"><span>{{:name}}</span></td>
				<td title="{{:title}}"><span>{{:title}}</span></td>
				<td title="{{:level}}"><span>{{:level}}</span></td>
				<td title="{{:scode}}"><span>{{:scode}}</span></td>
				<td title="{{:sort}}"><span>{{:sort}}</span></td>
				<td class="nameTd" title="{{if level==1}}无{{/if}}{{if level!=1}}{{:url}}{{/if}}"><span>{{if level==1}}无{{/if}}{{if level!=1}}{{:url}}{{/if}}</span></td>
				<td class="operation">
					<a class="deleteBtn btn-remove" onclick="deleteThis(this)" data-url="../rest/backadmin/menu/menudelete?uuid={{:uuid}}">删除</a>
					<a class="operaBranch editBtn btn-edit" href="menuInfoEdit.jsp?uuid={{:uuid}}">修改</a>
					{{if level==1}}<a class="operaBranch" href="menuInfoList.jsp?pid={{:uuid}}">管理下级菜单</a>{{/if}}
				</td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00200021" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a class="current">页面菜单信息管理</a></div>
					<a class="btn-add add addNew" href="../backadmin/menuInfoAdd.jsp" style="margin-top:0px;">+&ensp;添加页面</a>
					<div class="clear"></div>
				</div>
				<div class="main-contain">
					<table class="table table-hover text-center table-striped tableList">
						<thead>
							<tr>
								<th class="nameTh">名称</th>
								<th class="text-center">菜单标题</th>
								<th class="text-center">菜单级别</th>
								<th class="text-center">菜单编码</th>
								<th class="text-center">默认排序</th>
								<th class="nameTh">菜单链接</th>
								<th class="text-center" width="350px">操作</th>
							</tr>
						</thead>
						<tbody id="queboxCnt"></tbody>
					</table>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/menuInfoList.js" ></script>
	</body>
</html>

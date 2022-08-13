<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" href="css/roleControllerList.css" />
		
		<script id="queboxTpl" type="text/template">
			<table class="menu-table" id="menu-table-{{:sort}}">
				<tr class="menu-table-head">
					<td class="menu-table-left" style="background: url({{:icon}}_0.png) 30px center no-repeat;">{{:title}}</td>
					<td>
						<a class="sortdown" onclick="pSortDown(this)" data-uuid={{:uuid}}>下移</a>
						<a class="sortup" onclick="pSortUp(this)" data-uuid={{:uuid}}>上移</a>
					</td>
				</tr>
				{{for children}}
				<tr class="menu-table-row">
					<td class="menu-table-left">{{:title}}</td>
					<td>
						<a class="sortdown" onclick="cSortDown(this)" data-uuid={{:uuid}}></a>
						<a class="sortup" onclick="cSortUp(this)" data-uuid={{:uuid}}></a>
					</td>
				</tr>
				{{/for}}
			</table>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00200023" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a class="current">角色页面管理</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain">
					<div class="role-block">
						<span>选择角色：</span>
						<div class="role-list"  id="roleSelect"></div>
						<a class="role-edit" id="editButton" href="#">修改角色页面权限</a>
					</div>
					<div class="clear"></div>
					<div class="" id="queboxCnt"></div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/roleMenuList.js" ></script>
	</body>
</html>


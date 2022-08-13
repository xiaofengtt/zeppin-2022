<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>添加页面菜单</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/uploadfile.css">
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00200021" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a id="menuInfo" href="">页面菜单信息管理</a><span>></span><a class="current">添加页面菜单</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<form:form id="formsubmit" role="form" action="#" method="post">
						<div class="contents">
							<div class="form-group form-group-double col-xs-6" id="parent">
								<label class="col-xs-12">所属菜单：</label>
								<div class="col-xs-12">
									<input type="hidden" name="pid" id="pid" class="form-control"/>
									<input type="text" disabled="disabled" id="pids" class="form-control disabled"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">菜单名称：</label>
								<div class="col-xs-12">
									<input type="text" name="name" id="name" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">菜单标题：</label>
								<div class="col-xs-12">
									<input type="text" name="title" id="title" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">菜单编码：</label>
								<div class="col-xs-12">
									<input type="text" name="scode" id="scode" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">菜单链接：</label>
								<div class="col-xs-12">
									<input type="text" name="url" id="url" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">菜单图标：</label>
								<div class="col-xs-12">
									<input type="text" name="icon" id="icon" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="btnGroup" style="margin-top:45px;">
								<button class="btn sureBtn" type="submit">确定</button>
								<button class="btn cancleBtn" type="button" onclick="window.location.href=history.go(-1);">取消</button>
							</div>
							<div class="clear"></div>
						</div>
					</form:form>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/jquery.uploadfile.min.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/menuInfoAdd.js" ></script>
	</body>
</html>
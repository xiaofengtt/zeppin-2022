<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>个人信息</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/iframe.css" />
		
		<script id="DataTpl" type="text/template">
			<div class="form-group">
				<label>用户名：</label>
				<div>
					<input type="text" name="name" id="name" class="form-control single-input" readonly="readonly" value="{{:name}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label>真实姓名：</label>
				<div>
					<input type="text" name="realname" id="realname" class="form-control single-input" readonly="readonly" value="{{:realname}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label>手机：</label>
				<div>
					<input type="text" name="mobile" id="mobile" class="form-control single-input" readonly="readonly" value="{{:mobile}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label>邮箱：</label>
				<div>
					<input type="text" name="email" id="email" class="form-control single-input" readonly="readonly" value="{{:email}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="btnGroup">
				<a class="btn sureBtn btn-edit" href="currentOperatorEdit.jsp?uuid={{:uuid}}">修改</a>
				<button class="btn cancleBtn" onclick="parent.$.colorbox.close()">取消</button>
			</div>
		</script>
	</head>
	<body>
		<div class="layerOpen">
			<p class="title text-center">个人信息</p>
			<div class="contents detail">
				<div id="DataCnt"></div>
				<div class="clear"></div>
			</div>
		</div>
		
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/currentOperatorDetail.js" ></script>
	</body>
</html>
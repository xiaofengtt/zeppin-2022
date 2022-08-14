<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/iframe.css" />

		<script id="DataTpl" type="text/template">
			<input type="hidden" name="uuid" value="{{:uuid}}"/>
			<div class="form-group">
				<label>用户名：</label>
				<div>
					<input type="text" name="name" id="name" class="form-control single-input" value="{{:name}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label>真实姓名：</label>
				<div>
					<input type="text" name="realname" id="realname" class="form-control single-input" value="{{:realname}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label>手机：</label>
				<div>
					<input type="text" name="mobile" id="mobile" class="form-control single-input" value="{{:mobile}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label>邮箱：</label>
				<div>
					<input type="text" name="email" id="email" class="form-control single-input" value="{{:email}}"/>
				</div>
				<div class="clear"></div>
			</div>
		</script>
	</head>
	<body>
		<div class="layerOpen">
			<form:form id="formsubmit" role="form" action="#" method="post">
				<p class="title text-center">修改个人信息</p>
				<div class="contents">
					<div id="DataCnt"></div>
					<div class="btnGroup">
						<button class="btn sureBtn" type="submit">确定</button>
						<button class="btn cancleBtn" onclick="parent.$.colorbox.close()">取消</button>
					</div>
					<div class="clear"></div>
				</div>
			</form:form>
		</div>

		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/currentOperatorEdit.js" ></script>
		<script type="text/javascript" src="js/flagSubmit.js"></script>
	</body>
</html>

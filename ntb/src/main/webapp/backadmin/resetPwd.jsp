<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>修改密码</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/iframe.css" />
	</head>
	<body>
		<div class="layerOpen">
			<form:form id="formsubmit" role="form" action="#" method="post">
				<input type="hidden" id="uuid" name="uuid" value="" />
				<p class="text-center title">修改密码</p>
				<div class="contents">
					<div class="form-group">
						<label>原密码：</label>
						<div>
							<input type="password" name="password" id="password" class="form-control single-input" placeholder="6-20个字符"/>
						</div>
						<div class="clear"></div>
					</div>
					<div class="form-group">
						<label>新密码：</label>
						<div>
							<input type="password" name="passwordNew" id="passwordNew" class="form-control single-input" placeholder="6-20个字符"/>
						</div>
						<div class="clear"></div>
					</div>
					<div class="form-group">
						<label>新密码确认：</label>
						<div>
							<input type="password" name="passwordNewCheck" id="passwordNewCheck" class="form-control single-input" placeholder="6-20个字符"/>
						</div>
						<div class="clear"></div>
					</div>
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
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/resetPwd.js" ></script>
	</body>
</html>

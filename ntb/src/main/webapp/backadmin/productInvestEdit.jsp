<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>修改理财产品投资</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/iframe.css" />
	</head>
	<body>
		<div class="layerOpen">
			<form:form id="formsubmit" role="form" action="#" method="post">
				<p class="text-center title">修改理财产品投资</p>
				<div class="contents">
					<div class="form-group">
						<label>投资金额：</label>
						<div>
							<input type="text" name="amount" id="amount" class="form-control single-input"/>
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
		<script type="text/javascript" src="js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/productInvestEdit.js" ></script>
	</body>
</html>
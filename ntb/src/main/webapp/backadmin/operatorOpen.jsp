<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/operatorOpen.css" />
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<input id="scode" type="hidden" value="0000" />
		<div class="contents">
			<div class="form-group">
				<form:form id="formsubmit" role="form" action="#" method="post">
					<input type="hidden" name="uuid" value="${sessionScope.currentOperator.uuid}"/>
					<div class="form-group">
						<label>新密码：</label>
						<div>
							<input type="password" name="password" id="password" class="form-control"/>
						</div>
						<div class="clear"></div>
					</div>
					<div class="form-group">
						<label>确认密码：</label>
						<div>
							<input type="password" name="confirmPassword" id="confirmPassword" class="form-control"/>
						</div>
						<div class="clear"></div>
					</div>
					<div class="btnGroup text-center">
						<button class="btn sureBtn" type="submit">确定</button>
					</div>
				</form:form>
			</div>
		</div>

		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/operatorOpen.js" ></script>
	</body>
</html>

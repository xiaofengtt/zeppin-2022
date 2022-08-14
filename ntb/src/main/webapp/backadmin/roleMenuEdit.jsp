<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/roleControllerEdit.css" />
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00200023" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="roleMenuList.jsp">角色页面管理</a><span>></span><a class="current">修改角色权限</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain">
					<form:form id="formsubmit" role="form" action="#" method="post">
					<div>
						<div class="contain-head">
							<span><span id="roleName"></span>角色页面设置</span>
							<button class="btn submit-button" type="submit">提交修改</button>
							<button class="btn all-select-button" type="button" onclick="selectAll() ">全选</button>
						</div>
						<input type="hidden" id="role" name="role"/>
						<div class="contain-body">
							<div class="contain-body-left" id="contain-body-left">
								<ul id="left-ul"></ul>
							</div>
							<div class="contain-body-right" id="contain-body-right"></div>
						</div>
					</div>
					</form:form>
				</div>
			</div>
			<div class="clear"></div>
		</div>

		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/roleMenuEdit.js" ></script>
		<script type="text/javascript" src="js/flagSubmit.js"></script>
	</body>
</html>

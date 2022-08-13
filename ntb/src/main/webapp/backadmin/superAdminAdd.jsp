<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>添加系统管理员</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100011" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a href="javascript:">后台用户管理</a><span>></span><a href="superAdminList.jsp">系统管理员管理</a><span>></span><a class="current">添加系统管理员</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<form:form id="formsubmit" role="form" action="#" method="post">
						<div class="contents">
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">用户名：</label>
								<div class="col-xs-12">
									<input type="text" name="name" id="name" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">真实姓名：</label>
								<div class="col-xs-12">
									<input type="text" name="realname" id="realname" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">手机：</label>
								<div class="col-xs-12">
									<input type="text" name="mobile" id="mobile" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">邮箱：</label>
								<div class="col-xs-12">
									<input type="text" name="email" id="email" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="btnGroup">
								<button class="btn sureBtn" type="submit">确定</button>
								<button class="btn cancleBtn" onclick="window.location.href=history.go(-1)">取消</button>
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
		<script type="text/javascript" src="js/superAdminAdd.js" ></script>
	</body>
</html>
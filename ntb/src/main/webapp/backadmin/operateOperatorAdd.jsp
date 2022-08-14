<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>添加运营用户</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100012" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a href="javascript:">后台用户管理</a><span>></span><a href="operateOperatorList.jsp">运营用户管理</a><span>></span><a class="current">添加运营用户</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<form:form id="formsubmit" role="form" action="#" method="post">
						<div class="contents">
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">角色：</label>
								<div class="col-xs-12">
									<select id="role" name="role">
										<option value="" >请选择</option>
										<option value="0127fcbe-f57f-11e6-ac06-cacda7da5000" >运营经理</option>
										<option value="49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6" >运营编辑</option>
									</select>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">用户名：</label>
								<div class="col-xs-12">
									<input type="text" name="name" id="name" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">真实姓名：</label>
								<div class="col-xs-12">
									<input type="text" name="realname" id="realname" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">手机：</label>
								<div class="col-xs-12">
									<input type="text" name="mobile" id="mobile" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
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
								<button class="btn cancleBtn" onclick="history.go(-1)">取消</button>
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
		<script type="text/javascript" src="js/paramCheck.js" ></script>
		<script type="text/javascript" src="js/operateOperatorAdd.js" ></script>
		<script type="text/javascript" src="js/flagSubmit.js"></script>
	</body>
</html>

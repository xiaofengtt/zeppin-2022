<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>添加支行</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00000001" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a href="bankInfoList.jsp">银行信息管理</a><span>></span><a id="branchBank" href="">支行信息管理</a><span>></span><a class="current">添加支行</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<form:form id="formsubmit" role="form" action="#" method="post">
						<div class="contents">
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">支行名称：</label>
								<div class="col-xs-12">
									<input type="text" name="name" id="name" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">状态：</label>
								<div class="col-xs-12">
									<select name="status">
										<option value="normal">正常</option>
										<option value="disable">停用</option>
									</select>		
								</div>
								<div class="clear"></div>	
							</div>
							<div class="clear"></div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">支行地址：</label>
								<div class="col-xs-12">
									<input type="text" name="address" id="address" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="btnGroup">
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
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/branchBankAdd.js" ></script>
	</body>
</html>
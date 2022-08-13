<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>个人信息</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/colorbox.css" />
		
		<script id="DataTpl" type="text/template">
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">昵称：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:nickname}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">身份证号：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:idcard}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">电子邮箱：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:email}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">账户总资产（元）：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:totalAmount}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">账户余额（元）：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:accountBalance}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">推荐人：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:referrerName}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">真实姓名：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:realname}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">手机号：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:mobile}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">状态：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:statusCN}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">历史总收益（元）：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:totalReturn}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">创建时间：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:createtimeCN}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="btnGroup">
					<button class="btn cancleBtn" type="button" onclick="window.location.href=history.go(-1)">返回</button>
				</div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100015" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">后台用户管理</a><span>></span><a href="investorList.jsp">投资者用户管理</a><span>></span><a class="current">投资者用户详情</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<div id="formsubmit">
						<div class="contents">
							<div id="DataCnt"></div>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/investorDetail.js" ></script>
	</body>
</html>
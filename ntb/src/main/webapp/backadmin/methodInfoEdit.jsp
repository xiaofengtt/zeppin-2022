<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>修改方法</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/colorbox.css" />
		
		<script id="DataTpl" type="text/template">
			<input type="hidden" name="uuid" value="{{:uuid}}"/>
			<div class="form-group col-xs-6" style="padding-left:0;max-width:540px;margin-bottom:24px;">
				<label class="col-xs-12">所属功能：</label>
				<div class="col-xs-12">
					<input type="hidden" name="cuuid" id="cuuid" class="form-control" value="{{:controller}}"/>
					<input type="text" disabled="disabled" id="controllerName" class="form-control disabled" value="{{:controllerName}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group col-xs-6">
				<label class="col-xs-12">方法名称：</label>
				<div class="col-xs-12">
					<input type="text" name="name" id="name" class="form-control" value="{{:name}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group col-xs-6">
				<label class="col-xs-12">方法描述：</label>
				<div class="col-xs-12">
					<input type="text" name="description" id="description" class="form-control" value="{{:description}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00200022" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
			<div class="location"><div class="locationLeft"><a href="controllerInfoList.jsp">功能信息管理</a><span>></span><a id="methodInfoList" href="">方法信息管理</a><span>></span><a class="current">修改方法</a></div>
					<div class="clear"></div></div>
				<div class="layerOpen">
					<form:form id="formsubmit" role="form" action="#" method="post">
						<div class="contents">
							<div id="DataCnt"></div>
							<div class="btnGroup" style="margin-top:45px;">
								<button class="btn sureBtn" type="submit">确定</button>
								<button class="btn cancleBtn" type="button" onclick="window.location.href=history.go(-1);"">取消</button>
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
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/methodInfoEdit.js" ></script>
	</body>
</html>
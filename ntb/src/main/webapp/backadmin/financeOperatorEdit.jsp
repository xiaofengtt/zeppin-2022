<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>修改财务用户</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		
		<script id="DataTpl" type="text/template">
			<input type="hidden" name="uuid" value="{{:uuid}}"/>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">角色：</label>
				<div class="col-xs-12">
					<select id="role" name="role">
						<option id="0e15ae93-f57f-11e6-ac06-cacda7da5000" value="0e15ae93-f57f-11e6-ac06-cacda7da5000" >财务经理</option>
						<option id="0922a25d-f57f-11e6-ac06-cacda7da5000" value="0922a25d-f57f-11e6-ac06-cacda7da5000" >财务编辑</option>
					</select>				
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">用户名：</label>
				<div class="col-xs-12">
					<input type="text" name="name" id="name" class="form-control" value="{{:name}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">真实姓名：</label>
				<div class="col-xs-12">
					<input type="text" name="realname" id="realname" class="form-control" value="{{:realname}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">手机：</label>
				<div class="col-xs-12">
					<input type="text" name="mobile" id="mobile" class="form-control" value="{{:mobile}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">邮箱：</label>
				<div class="col-xs-12">
					<input type="text" name="email" id="email" class="form-control" value="{{:email}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">状态：</label>
				<div class="col-xs-12">
					<select name="status">
						<option value="normal" {{if status=='normal'}}selected{{/if}}>正常</option>
						<option value="disable" {{if status=='disable'}}selected{{/if}}>停用</option>
						<option value="locked" {{if status=='locked'}}selected{{/if}}>锁定</option>
					</select>
					{{if status=='locked'}}{{:lockedtimeCN}}{{/if}}
				</div>
				<div class="clear"></div>	
			</div>
			<div class="clear"></div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100013" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a href="javascript:">后台用户管理</a><span>></span><a href="financeOperatorList.jsp">财务用户管理</a><span>></span><a class="current">修改财务用户</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<form:form id="formsubmit" role="form" action="#" method="post">
						<div class="contents">
							<div id="DataCnt"></div>
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
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/financeOperatorEdit.js" ></script>
	</body>
</html>
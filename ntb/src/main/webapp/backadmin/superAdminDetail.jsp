<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>系统管理员详情</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/colorbox.css" />

		<script id="DataTpl" type="text/template">
			<div class="form-group">
				<label class="col-md-4">用户名：</label>
				<div class="col-md-6">
					{{:name}}
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-md-4">真实姓名：</label>
				<div class="col-md-6">
					{{:realname}}
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-md-4">手机：</label>
				<div class="col-md-6">
					{{:mobile}}
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-md-4">邮箱：</label>
				<div class="col-md-6">
					{{:email}}
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-md-4">状态：</label>
				<div class="col-md-6">
					{{if status=='normal'}}正常{{/if}}
					{{if status=='disable'}}停用{{/if}}
					{{if status=='locked'}}锁定 {{:lockedtimeCN}}{{/if}}
					{{if status=='unopen'}}未开通{{/if}}
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-md-4">创建者：</label>
				<div class="col-md-6">
					{{:creatorName}}
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-md-4">创建时间：</label>
				<div class="col-md-6">
					{{:createtimeCN}}
				</div>
				<div class="clear"></div>
			</div>
			<div class="btnGroup text-center">
				<a class="btn sureBtn btn-edit" href="superAdminEdit.jsp?uuid={{:uuid}}">修改</a>
				<button class="btn sureBtn" data-uuid="{{:uuid}}" onclick="editPassword(this)">重置密码</button>
				<button class="btn cancleBtn" onclick="parent.$.colorbox.close()">取消</button>
			</div>
		</script>
	</head>
	<body>
		<div class="layerOpen">
			<p class="text-left title">系统管理员详情</p>
			<div class="contents">
				<div id="DataCnt"></div>
				<div class="clear"></div>
			</div>
		</div>

		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/superAdminDetail.js" ></script>
	</body>
</html>

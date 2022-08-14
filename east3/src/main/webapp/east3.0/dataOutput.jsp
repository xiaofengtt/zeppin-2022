<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>修改密码</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/iframe.css" />
		<style>
		.layui-layer-loading{background:#000 !important;background:rgba(0,0,0,0.5) !important;color:#fff;border-radius:0 !important;padding:20px !important;}
		</style>
	</head>
	<body>
		<div class="layerOpen">
			<input type="hidden" id="id" name="id" value="" />
			<p class="text-center title">导出数据</p>
			<div class="contents">
				<div class="form-group">
					<label class="control-label" title="">
						时间区间</label>
					<div class="">
						<input name="starttime" maxlength="10" class="form-control form-date" value="" style="width:40%;display:inline-block;" onkeyup="value=value.replace(/[^\d^\-]+/g,'')"/>
						~
						<input name="endtime" maxlength="10" class="form-control form-date" value="" style="width:40%;display:inline-block;"onkeyup="value=value.replace(/[^\d^\-]+/g,'')"/>
					</div>
				</div>
				<div class="btnGroup" style="text-align:center;">
					<a class="btn sureBtn">确定</a>
					<button class="btn cancleBtn" onclick="parent.$.colorbox.close()" style="float:none;margin-left:20px;">取消</button>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		
		<script type="text/javascript" src="js/jquery-2.1.4.min.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/dataOutput.js" ></script>
	</body>
</html>
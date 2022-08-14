<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/colorbox.css" />
		<link rel="stylesheet" href="css/uploadfile.css">

		<script id="DataTpl" type="text/template">
				<div class="form-group form-group-single col-xs-12">
					<label class="col-xs-12">姓名：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:name}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-single col-xs-12">
					<label class="col-xs-12">身份证号：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:idcard}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-single col-xs-12">
					<label class="col-xs-12">证件照片正面：</label>
					<div class="col-xs-12">
						<div class="uploadLogo" style="text-align:center;">
							<img id="iconColorShow" src="..{{:imgfaceurl}}" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
						</div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-single col-xs-12">
					<label class="col-xs-12">证件照片反面：</label>
					<div class="col-xs-12">
						<div class="uploadLogo" style="text-align:center;">
							<img id="iconColorShow" src="..{{:imgbackurl}}" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
						</div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-single col-xs-12">
					<label class="col-xs-12">审核原因：</label>
					<div class="col-xs-12">
						<input id="checkReason" type="text"{{if status != 'unchecked'}} disabled="disabled"{{/if}} class="form-control disabled" value="{{:reason}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
		</script>
		<style>
			body{
				min-width:0;
			}

		</style>
	</head>
	<body>
		<div class="layerOpen">
			<p class="titles">证件审核</p>
			<div class="contents">
				<div class="contents_mid">
					<div class="layerContent">
						<div class="list-content" id="queboxCnt">
						</div>
						<div id="pageTool"></div>
					</div>
					<div class="foot-contain">
						<div class="content-item">
							<div class="contentDiv">
								<div class="operateCheck">
									<div>
										<button class="btn btn-primary" type="button" onclick="checked(this)">通过</button>
										<button class="btn btn-primary" type="button" onclick="unpassed(this)">不通过</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>

		</div>

		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/investorIdcardImgCheckDetail.js" ></script>
	</body>
</html>

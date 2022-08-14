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
		<link rel="stylesheet" href="css/uploadfile.css">
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00000001" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><div class="locationLeft"><a href="bankInfoList.jsp">银行信息管理</a><span>></span><a class="current">添加银行</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<form:form id="formsubmit" role="form" action="#" method="post">

						<div class="contents">
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">银行名称：</label>
								<div class="col-xs-12">
									<input type="text" name="name" id="name" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">银行简称：</label>
								<div class="col-xs-12">
									<input type="text" name="shortName" id="shortName" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
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
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">银行编码：</label>
								<div class="col-xs-12">
									<input type="text" name="code" id="code" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">单笔限额：</label>
								<div class="col-xs-12">
									<input type="text" name="singleLimit" id="singleLimit" maxlength="10" class="form-control"/><i>元</i>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">每日限额：</label>
								<div class="col-xs-12">
									<input type="text" name="dailyLimit" id="dailyLimit" maxlength="10" class="form-control"/><i>元</i>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">显示色值：</label>
								<div class="col-xs-12">
									<input type="text" name="color" id="color" maxlength="10" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">是否绑定：</label>
								<div class="col-xs-12">
									<select name="flagBinding" id="flagBinding">
										<option value="false">否</option>
										<option value="true">是</option>
									</select>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">数字编码：</label>
								<div class="col-xs-12">
									<input type="text" name="codeNum" id="codeNum" maxlength="10" class="form-control"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group form-group-double col-xs-6">
								<label class="col-xs-12">是否为银行：</label>
								<div class="col-xs-12">
									<select name="flagBank" id="flagBank">
										<option value="true">是</option>
										<option value="false">否</option>
									</select>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-single col-xs-11 self">
								<label class="col-xs-12">上传LOGO：</label>
								<div class="col-xs-12">
									<div class="uploadLogo" style="text-align:center;">
										<img id="imageShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
										<div id="resourceId">

										</div>
									</div>
									<div id="resourceAdd"><input type="hidden" name="logo" id="logo" value=""></div>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-single col-xs-11 other">
								<label class="col-xs-12">上传ICON：</label>
								<div class="col-xs-12">
									<div class="uploadLogo" style="text-align:center;">
										<img id="iconShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
										<div id="iconId">

										</div>
									</div>
									<div id="iconAdd"><input type="hidden" name="icon" id="icon" value=""></div>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group form-group-single col-xs-11 third">
								<label class="col-xs-12">上传彩色ICON：</label>
								<div class="col-xs-12">
									<div class="uploadLogo" style="text-align:center;">
										<img id="iconColorShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
										<div id="iconColorId">

										</div>
									</div>
									<div id="iconColorAdd"><input type="hidden" name="iconColor" id="iconColor" value=""></div>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="btnGroup">
								<button class="btn sureBtn" type="submit">确定</button>
								<button class="btn cancleBtn" type="button" onclick="window.location.href = document.referrer;">取消</button>
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
		<script type="text/javascript" src="js/jquery.uploadfile.min.js"></script>
		<script type="text/javascript" src="js/bankInfoAdd.js" ></script>
		<script type="text/javascript" src="js/flagSubmit.js"></script>
	</body>
</html>

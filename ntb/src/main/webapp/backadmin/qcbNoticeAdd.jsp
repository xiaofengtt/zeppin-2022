<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/fundEdit.css" />
		<link rel="stylesheet" href="css/datepicker3.css" >
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00800086" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="qcbNoticeList.jsp">企财宝通知管理</a><span>></span><a class="current">添加通知</a></div>
				<form:form id="formsubmit" role="form" action="#" method="post">
				<div class="title-contain">
					<div class="content-item">
						<div class="content-item-edit" style="display: block;">
							<div class="form-group col-md-12">
								<label>标题：</label>
								<div class="content-items">
									<input class="form-control long" id="title" name="title"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group col-md-12">
								<label>通知时间：</label>
								<div class="content-items">
									<input class="form-control datepicker" id="starttime" name="starttime" data-provide="datepicker"/>
									 至
									<input class="form-control datepicker" id="endtime" name="endtime" data-provide="datepicker"/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="form-group col-md-12" style="display: block;">
								<label class="label_left">通知内容：</label>
								<div id="content"></div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="form-group col-md-12 ">
								<label class="label_left">状态：</label>
								<div class="content-items">
									<select name="status">
										<option value="normal">正常</option>
										<option value="disabled">停用</option>
									</select>
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
				
					<div class="btnGroup text-center">
						<button class="btn sureBtn" type="submit">提交</button>
						<button class="btn cancleBtn" type="button" onclick="window.close();">取消</button>
					</div>
					<div class="clear"></div>
				</div>
				</form:form>
			</div>
			<div class="clear"></div>
		</div>

		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/tinymce/dropzone/lib/dropzone.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce.min.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce_zhcn.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce_tool.js"></script>
		<script type="text/javascript" src="js/qcbNoticeAdd.js" ></script>
		<script type="text/javascript" src="js/flagSubmit.js"></script>

	</body>
</html>

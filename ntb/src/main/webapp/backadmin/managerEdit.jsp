<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>修改主理人</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/uploadfile.css">
		
		<script id="DataTpl" type="text/template">
			<input type="hidden" id="uuid" name="uuid" value="{{:uuid}}"/>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">姓名：</label>
				<div class="col-xs-12">
					<input class="form-control" id="name" name="name" value="{{:name}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">身份证号：</label>
				<div class="col-xs-12">
					<input class="form-control" id="idcard" name="idcard" value="{{:idcard}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">手机号：</label>
				<div class="col-xs-12">
					<input class="form-control" id="mobile" name="mobile" value="{{:mobile}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">邮箱：</label>
				<div class="col-xs-12">
					<input class="form-control" id="email" name="email" value="{{:email}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">用户类型：</label>
				<div class="col-xs-12">
					<input class="form-control" id="type" name="type" value="{{:type}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">状态：</label>
				<div class="col-xs-12">
					<select name="status">
						<option value="normal" {{if status=='normal'}}selected{{/if}}>正常</option>
						<option value="disable" {{if status=='disable'}}selected{{/if}}>停用</option>
					</select>				
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group form-group-single col-xs-11">
				<label class="col-xs-12">上传头像：</label>
				<div class="col-xs-12">
					<div class="uploadLogo" style="text-align:center;">
						<img id="imageShow" src="{{if photo != ''}}..{{:photoUrl}}{{else}}img/fundManagement.jpg{{/if}}" style="border:0;max-width:100%;height:150px;margin-top:15px;">
						<div id="resourceId">
				
						</div>
					</div>
					<div id="resourceAdd"><input type="hidden" name="photo" id="photo" value="{{:photo}}"></div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">毕业院校：</label>
				<div class="col-xs-12">
					<input class="form-control" id="graduation" name="graduation" value="{{:graduation}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">学历：</label>
				<div class="col-xs-12">
					<input class="form-control" id="education" name="education"  value="{{:education}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group form-group-double col-xs-6">
				<label class="col-xs-12">从业年限：</label>
				<div class="col-xs-12">
					<input class="form-control" id="workage" name="workage"  value="{{:workage}}"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="form-group form-group-single col-xs-11">
				<label class="col-xs-12">个人简历：</label>
				<div class="col-xs-12">
					<textarea id="resume" rows="8" name="resume">{{:resume}}</textarea>
				</div>
				<div class="clear"></div>
			</div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100014" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">后台用户管理</a><span>></span><a href="managerList.jsp">主理人信息管理</a><span>></span><a class="current">修改主理人</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<form:form id="formsubmit" role="form" action="#" method="post">
						<div class="contents">
							<div id="DataCnt"></div>
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
		<script type="text/javascript" charset="utf-8" src="js/utf8-jsp/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/utf8-jsp/ueditor.all.min.js"> </script>
		<script type="text/javascript" charset="utf-8" src="js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/jquery.uploadfile.min.js"></script>
		<script type="text/javascript" src="js/managerEdit.js" ></script>
	</body>
</html>
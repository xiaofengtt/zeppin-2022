<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link rel="stylesheet" href="../css/uploadfile.css">
<link href="../css/datepicker3.css" rel="stylesheet">
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
<script src="../js/upload/jquery.uploadfile.min.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>添加申请</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display: none; margin: 0 15px 15px;"></div>
			<form id="addServiceApply" class="form-horizontal" role="form"
				action="../admin/serviceApply_addServiceApplyInit.action"
				method="post">
				<input type="hidden" name="applyId"
					value="<s:property value="applyId" />">
				<div class="clearfix">
					<div class="span10">

						<div class="control-group">
							<label class="control-label" for="">申请内容(或理由)</label>
							<div class="controls">
								<s:property value="applyContent" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">回复内容</label>
							<div class="controls">
								<textarea type="text" id="" name="replyContent"
									style="width: 50%; height: 111px"></textarea>
							</div>
						</div>
						<div class="row actionbar">
							<div class="offset7">
								<button class="btn btn-primary" type="submit">确定</button>
								<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
									class="btn btn-default" type="button">取消</button>
							</div>

						</div>
					</div>
				</div>


			</form>
		</div>

	</div>
	<script>
		$(function() {
			$('#addServiceApply').submit(
					function() {
						var str = $(this).serialize();
						$
								.get(
										'../admin/serviceApply_addReplyServiceApply.action?'
												+ str, function(data) {
											var Result = data.Result;
											var message = data.Message;
											if (Result == "OK") {
												alert(message);
												window.top.location.reload();
											} else {
												$('.alert-danger')
														.html(message).show();
											}
										})
						return false;
					});

		})
	</script>
</body>
</html>
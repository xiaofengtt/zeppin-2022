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
<script src="../js/url.min.js"></script>
<style type="text/css">
.box{position:relative;}
p.number{position:absolute;bottom:0px;right:20px;text-align:right;margin-bottom:3px;}
textarea{width:100%;}

</style>
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
							<label class="control-label" for="">申请类型</label>
							<div class="controls">
								<select class="span3" name="applyType" id="applyType">
									<option value="0" <s:if test="applyType==0" >selected</s:if>>添加承训单位申请</option>
									<option value="1" <s:if test="applyType==1" >selected</s:if>>添加培训学科申请</option>
									<option value="2" <s:if test="applyType==2" >selected</s:if>>其他申请</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">申请内容(或理由)</label>
							<div class="controls box">
								<textarea type="text" id="applyContent" name="applyContent"
									maxlength="200" cols="100" rows="5"
									placeholder="请输入申请内容，字数在200字以内" onkeydown='countChar("applyContent","currents");' 
									onkeyup='countChar("applyContent","currents");'><s:property
										value="applyContent"  /></textarea>
										<p class="number">（<span id="currents">0</span>/<span>200</span>）</p>
							</div>
						</div>
						<div class="control-group textareaBox">
							<label class="control-label" for="">回复内容</label>
							<div class="controls box">
								<textarea type="text" id="replyContent" name="replyContent"
									maxlength="200" cols="100" rows="5"
									placeholder="请输入回复内容，字数在200字以内" onkeydown='countChar("replyContent","current");' 
									onkeyup='countChar("replyContent","current");'></textarea>
									<p class="number">（<span id="current">0</span>/<span>200</span>）</p>
							</div>
						</div>
						<div class="row actionbar">
							<div class="offset7">
								<button class="btn btn-primary btn-myself" type="submit">确定</button>
								<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
									class="btn btn-default btn-myself" type="button">取消</button>
							</div>

						</div>
					</div>
				</div>


			</form>
		</div>

	</div>
	<script>
		$("#currents").html(document.getElementById("applyContent").value.length);
		$("#current").html(document.getElementById("replyContent").value.length);
		var val = url('?val');
		$(function() {
			if (val != "") {
				$("select option").each(
						function(index, elect) {
							if ($(this).val() == val) {
								$("select").val($(this).val()).attr("disabled",
										"disabled");
							}
						})
				$("#applyContent").attr("readonly", "readonly");
				$(".textareaBox").css("display", "block");

			} else {
				$(".textareaBox").css("display", "none");
				$("#applyContent").removeAttr("readonly");
			}
			$("textarea").focus(function(){
				
			})

			$('#addServiceApply').submit(
					function() {
						var str = $(this).serialize();
						if (val != "") {
							$.get(
									'../admin/serviceApply_addReplyServiceApply.action?'
											+ str, function(data) {
										var Result = data.Result;
										var message = data.Message;
										if (Result == "OK") {
											alert(message);
											window.parent.location.href = "../admin/serviceApply_init.action?"; 
										} else {
											$('.alert-danger').html(message)
													.show();
										}
									})
							return false;
						} else {
							$.get(
									'../admin/serviceApply_addServiceApply.action?'
											+ str, function(data) {
										var Result = data.Result;
										var message = data.Message;
										if (Result == "OK") {
											alert(message);
											window.parent.location.href = "../admin/serviceApply_init.action?"; 
										} else {
											$('.alert-danger').html(message)
													.show();
										}
									})
							return false;
						}
					});

		})
		function countChar(textareaName,spanName)
		{
		document.getElementById(spanName).innerHTML =  document.getElementById(textareaName).value.length;
		document.getElementById(textareaName).value=document.getElementById(textareaName).value.substr(0,199); 
		
		} 
	</script>
</body>
</html>
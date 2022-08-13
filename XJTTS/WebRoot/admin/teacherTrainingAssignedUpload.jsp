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
		
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>

<script src="../js/upload/jquery.uploadfile.min.js"></script>
<style>
.align-label{padding-right:40px}
.form-group{padding-bottom:20px}
</style>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>上传先报后分项目报名记录</h3>
			<h3>首先导出（先报后分）学员培训记录，填写承训单位内容后在此处上传，<br/>为了防止上传失败,表格中请不要出现空行、列。</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" id="upload" method="post" name="upload" enctype="multipart/form-data" >
				<div class="clearfix">
					<div class="span5">					
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">导入培训记录</label>
							<div class="controls" >
								<div id="applydoc" class="">&nbsp;导入培训记录&nbsp;&nbsp;</div>
								
									<script>
										$("#applydoc").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"xls,xlsx",
											maxFileSize:1024*1024*5,
											fileName:"trainingRecords",
											maxFileCount : 1,
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传Excel文档类型的文件",
											showDone:false,
											showDelete : false,
											deletelStr : '删除',
											showAbort:false
										});
									</script>
							</div>
						</div>
					</div>
				</div>
				<div class="row actionbar">
						<div class="offset8">
							<div id="isNone" style="position:absolute;left:160px;display:none;"><span>正在处理,请稍等(不要关闭本页面)。。。</span><br />
								<div class="ajax-file-upload-progress" style="width:150px">
								<div id="schedule" class="ajax-file-upload-bar" style=""></div></div>
								<div id="percent" style="font-size: 10px;">&nbsp;&nbsp;&nbsp;(总共<b id="maxIndex">*</b>条记录,已完成<b id="percent-inx">0</b>%)</div>
							</div>
				</div>
				<div class="offset8 buttons">
						<button class="btn btn-primary btn-myself" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default btn-myself" type="button">取消</button>
				</div>

				</div>
			</form>
		</div>
		
	</div>
	<script>
		$(function() {
			$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.uul').length) {
					$('.uul').hide();
				}
			});
		})
		
		$(function() {
			

			var timer;		
			function getSchedule(){
				var str =$(this).serialize();
				str=encodeURI(str);
				$.post('../admin/ttAssigned_getPercent.action?' + str, function(data) {
					var Result = data.Result;
					var message = data.Message;
					var maxIndex = data.MaxIndex;
					var percent = data.Percent;
					if (Result == "OK") {
						$("#schedule").css("width",""+percent+"%");
						$("#maxIndex").html(maxIndex);
						$("#percent-inx").html(percent);
						
					} else {
						$('.alert-danger').html(message).show();
					}
					
					if(percent == 100){
						window.clearInterval(timer); 
					}
				})
			}
			
			$('#upload').submit(
					function() {
						
						$(".buttons").css("margin-top","60px");
						timer = window.setInterval(getSchedule,2000); 
						var str =$(this).serialize();
						str=encodeURI(str);
						
						$.post('../admin/ttAssigned_upload.action?' + str, function(data) {
							var Result = data.Result;
							var message = data.Message;
							if (Result == "OK") {
								$("#isNone span").html("处理完成,请等待页面跳转...");
								window.top.location.reload();
							} else {
								$('.alert-danger').html(message).show();
							}
						})
						$("#isNone").show(1000);
						return false;
					});

		})
	</script>
<body>
</html>

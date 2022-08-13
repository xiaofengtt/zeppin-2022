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
<link rel="stylesheet" href="../css/datepicker3.css">
		
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<script src="../js/upload/jquery.uploadfile.min.js"></script>

</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>上传学员成绩</h3>
			<h3>首先导出该课程学员成绩，根据示例要求修改学员结业状态，证书编号/不合格原因，成绩和获得学时后，在此处上传，<br/>为了防止上传失败请不要修改表格其他内容,表格中不要出现空行。</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" id="upload" action="../admin/trainingStudentOpt_inputScore.action"
				method="post" name="upload" enctype="multipart/form-data" >
				<div class="clearfix">
					<div class="span5">					
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传学员成绩</label>
							<div class="controls" >
								<div id="applydoc" class="">&nbsp;上传学员成绩&nbsp;&nbsp;</div>
								
									<script>
										$("#applydoc").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"xls,xlsx",
											maxFileSize:1024*1024*5,
											fileName:"applyReportBook",
											maxFileCount : 1,
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传Excel文档类型的文件",
											showDone:false,
											showDelete : true,
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
						<button class="btn btn-primary" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default" type="button">取消</button>
				</div>

				</div>
			</form>
		</div>
		
	</div>
	<script>
		$(function() {
			/*$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('clId') || o.parent('.clId').length || o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.listSub').length || !o.parents('.uul').length) {
					$('.listSub,.uul').hide();
				}
			});*/
			$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.uul').length) {
					$('.uul').hide();
				}
			});
			
			$('.datepicker').datepicker({
				language : "zh-CN",
				format : 'yyyy-mm-dd',
				startView: 2,
				startDate : '1d',
				autoclose : true
			});

			$('.switch').bootstrapSwitch();

			$('#mainTeachingSubject').select2('enable',false);
			
			
			
		})
		
		$(function() {
			

			var timer;		
			function getSchedule(){
				var str =$(this).serialize();
				str=encodeURI(str);
				$.post('../admin/trainingStudentOpt_getPercent.action?'+ str, function(data) {
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
// 						window.alert(message);
						window.clearInterval(timer); 
					}
				})
			}
			
			$('#upload').submit(
					function() {
						
						$(".buttons").css("margin-top","60px");
						timer = window.setInterval(getSchedule,5000); 
						var str =$(this).serialize();
						str=encodeURI(str);
						
						$.post('../admin/trainingStudentOpt_inputScore.action?'+ str, function(data) {
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

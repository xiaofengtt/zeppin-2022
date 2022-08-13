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
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<style>
.align-label{padding-right:40px}
.form-group{padding-bottom:20px}
p{padding:8px 15px;margin:0;font-size:14px;}
img{cursor:pointer;}
.ifrcnt{width:100%;}
.actionbar{text-align:right;}
.offset8{margin-left:auto;}
.ajax-upload-dragdrop{width:auto !important;}
</style>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>上传学员名单和成绩</h3>
			<p>首先导出学员培训记录导入模板，按要求填写相关培训学员信息及成绩信息，为了防止上传失败,表格中请不要出现空行、列。</p>
			<p>导入模板如下图所示：</p>
			<div style="padding:15px;" title="点击查看大图" onclick="lookImg(this)"><img alt="导入模板" src="../img/model.png">
			</div>
			<p style="color:#f00;">“*”号表示必填内容，其中，“项目名称”、“培训学科名称”与“承训单位”请严格按照系统中的名称填写<s:if test="organizationLevel == 4">（校本级项目的“承训单位”请填写各自学校名称）</s:if>，“培训状态”从“培训合格、培训不合格”中二选一</p>
			<p><a href="../admin/学员导入模板.xlsx">导出学员培训记录导入模板</a></p>
			
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" id="upload" method="post" name="upload" enctype="multipart/form-data" >
				<div class="clearfix">
					<div class="span5">					
						<div class="control-group" style="width:100%">
							<label class="control-label" for="" style="line-height:44px;">导入学员名单和成绩</label>
							<div class="controls" >
								<div id="applydoc" class="">&nbsp;导入学员名单和成绩&nbsp;&nbsp;</div>
								
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
						<button id="colorboxcancel" onclick="parent.layer.closeAll()"
							class="btn btn-default btn-myself" type="button">取消</button>
				</div>

				</div>
			</form>
		</div>
		
	</div>
	<script>
	function lookImg(obj){
		var src=$(obj).find("img").attr("src");
		var index=top.layer.open({
		  type: 1,
		  title: false,
		  shadeClose: true,
		  shade: 0.8,
		  scrollbar: false,
		  area: ['98%', 'auto'],
		  content: '<div><img src="'+src+'" alt="大图" style="width:100%;"/></div>' 
		}); 
		$(".layui-layer-setwin .layui-layer-close2").css("display", "block");
	   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
	}
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
				$.post('../admin/ttResult_getPercent.action?' + str, function(data) {
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
			var submitFlag = true;
			$('#upload').submit(function() {
				if(submitFlag){
					submitFlag = false;
					$(".buttons").css("margin-top","60px");
					timer = window.setInterval(getSchedule,2000); 
					var str =$(this).serialize();
					str=encodeURI(str);
					
					$.post('../admin/ttResult_upload.action?' + str, function(data) {
						var Result = data.Result;
						var message = data.Message;
						if (Result == "OK") {
							$("#isNone span").html("处理完成,请等待页面跳转...");
							window.top.location.reload();
						} else {
							$('.alert-danger').html(message).show();
							submitFlag = true;
						}
					})
					$("#isNone").show(1000);
				}
				return false;
			});

		})
	</script>
<body>
</html>

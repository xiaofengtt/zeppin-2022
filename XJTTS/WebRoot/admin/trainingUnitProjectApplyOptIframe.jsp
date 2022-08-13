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
			<h3>项目申报:<s:property	value="project.name" /></h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" id="ProjectApply" action="trainingUnitProjectApply_addProjectapply.action"
				method="post" name="ProjectApply" enctype="multipart/form-data" >
				<input type="hidden" name="pid" value="<s:property	value="projectApply.id" />">
				<input type="hidden" name="id" value="<s:property	value="project.id" />">
				<div class="clearfix">

					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">首席专家/班主任</label>
							<div class="controls">
								<input type="text" name="contacts"
									value="<s:property	value="ProjectApply.contacts" />" placeholder="联系人"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">首席专家/班主任<br>联系电话</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off" name="phone"
									value="<s:property	value="ProjectApply.phone" />" placeholder="联系电话"/>
							</div>
						</div>
						

						<div class="control-group">
							<label class="control-label" for="">培训人数</label>
							<div class="controls">
								<input <s:if test="project.enrollType == 1">readonly   class="readonly"</s:if> type="text" id="" autocomplete="off" name="approveNumber"
									value="<s:property	value="ProjectApply.approveNumber"  />"  placeholder="培训人数"/>
							</div>
						</div>
						

					
						<div class="control-group">
							<label class="control-label" for="">申报学科</label>
							<div class="controls">
								<s:if test='%{2==projectApply.status}'>
									<input type="text" class="readonly" readonly  value="<s:property value="projectApply.trainingSubject.name" />">
									<input type="hidden" name="mainTeachingSubject" value="<s:property value="projectApply.trainingSubject.id" />">
								</s:if>
								<s:else>
									<select  id="mainTeachingSubject" name="mainTeachingSubject"
										class="span3" >
										<option  value="-1">请选择</option>
										<s:iterator value="lstteacTeachingSubjectExs" id="e">
											<option value="<s:property value="#e.id" />"
												search="<s:property
													value="#e.searchString" />"
												<s:if test="%{#e.id==ProjectApply.trainingSubject.id}" >selected</s:if> /><s:property
													value="#e.name" /></option>
										</s:iterator>
									</select>
								</s:else>	
							</div>
						</div>
						<s:if test="%{project.enrollType == 2}">
						<div class="control-group">
							<label class="control-label" for="">报名截止时间</label>
							<div class="controls">
								<input type="text" readonly autocomplete="off" data-provide="datepicker"
									class="datepicker" id="" name="enrollEndTime" value="<s:date name="ProjectApply.enrollEndTime" format="yyyy-MM-dd"/>">
								
							</div>
						</div>
						</s:if>
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传申报书</label>
							<div class="controls" >
								<div id="applydoc" class="">&nbsp;上传申报书&nbsp;&nbsp;</div>
								
									<script>
									var pid = $('input[name="pid"]').val();
										$("#applydoc").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"doc,docx",
											maxFileSize:1024*1024*50,
											fileName:"applyReportBook",
											maxFileCount : 1,
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word文档类型的文件",
											showDone:false,
											showDelete : false,
											deletelStr : '删除',
											doneStr: "完成",
											showAbort:false
										});
									</script>
							</div>
						</div>
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传实施方案</label>
							<div class="controls" >
								<div id="applydoc5" class="">上传实施方案</div>
								
									<script>
									var pid = $('input[name="pid"]').val();
										$("#applydoc5").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"doc,docx",
											maxFileSize:1024*1024*50,
											fileName:"projectPlan",
											maxFileCount : 1,
											deletelStr : '删除',
											doneStr: "完成",
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word文档类型的文件",
											showAbort:false,
											showDelete : false,
											showDone:false
										});
									</script>
							</div>
						</div>
					<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传开班通知</label>
							<div class="controls" >
								<div id="applydoc1" class="">上传开班通知</div>
								
									<script>
									var pid = $('input[name="pid"]').val();
										$("#applydoc1").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"doc,docx",
											maxFileSize:1024*1024*50,
											fileName:"classStartReport",
											maxFileCount : 1,
											deletelStr : '删除',
											doneStr: "完成",
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word文档类型的文件",
											showAbort:false,
											showDelete : false,
											showDone:false
										});
									</script>
							</div>
						</div>
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传工作简报</label>
							<div class="controls" >
								<div id="applydoc2" class="">上传工作简报</div>
								
									<script>
									var pid = $('input[name="pid"]').val();
										$("#applydoc2").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"doc,docx",
											maxFileSize:1024*1024*50,
											fileName:"workReport",
											doneStr: "完成",
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word文档类型的文件",
											showDone:false,
											showDelete : false,
											showAbort:false,
											maxFileCount : 100,
											deletelStr : '删除'
										});
									</script>
							</div>
						</div>
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传绩效报告</label>
							<div class="controls" >
								<div id="applydoc3" class="">上传绩效报告</div>
								
									<script>
									var pid = $('input[name="pid"]').val();
										$("#applydoc3").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"doc,docx",
											maxFileSize:1024*1024*50,
											fileName:"proformanctReport",
											doneStr: "完成",
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word文档类型的文件",
											showDone:false,
											showDelete : false,
											showAbort:false,
											maxFileCount : 1,
											deletelStr : '删除'
										});
									</script>
							</div>
						</div>
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传课程表</label>
							<div class="controls" >
								<div id="applydoc4" class="">上传课程表</div>
								
									<script>
									var pid = $('input[name="pid"]').val();
										$("#applydoc4").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"*",
											maxFileSize:1024*1024*50,
											fileName:"curriculumReport",
											doneStr: "完成",
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word文档类型的文件",
											showDone:false,
											showDelete : false,
											showAbort:false,
											maxFileCount : 1,
											deletelStr : '删除'
										});
									</script>
							</div>
						</div>
						
					</div>
					<div class="span5">
<!-- 					<div class="control-group" id="trainingClasshour" <s:if test="project.traintype==2"> style="display:none"</s:if>> -->
<!-- 							<label class="control-label" for="">集中培训课时</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<div class="input-append"> -->
<!-- 									<input type="text" id="" name="trainingClasshour" -->
<%-- 										placeholder="" value="<s:property value="projectApply.trainingClasshour"/>"> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="control-group" id="trainingOnlineHour" <s:if test="project.traintype==1"> style="display:none"</s:if>> -->
<!-- 							<label class="control-label" for="">远程培训课时</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<div class="input-append"> -->
<!-- 									<input type="text" id="" name="trainingOnlineHour" -->
<%-- 										placeholder="" value="<s:property value="projectApply.trainingOnlineHour"/>"> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
							
						<div class="control-group">
							<label class="control-label" for="">培训地址</label>
							<div class="controls">
								<input type="text" id="" name="trainingAddress"
									value="<s:property	value="ProjectApply.trainingAddress" />" placeholder="培训地址">
							</div>
						</div>
						
						

						<div class="control-group">
							<label class="control-label" for="">计划开始时间</label>
							<div class="controls">
								<input type="text" readonly autocomplete="off" data-provide="datepicker"
									class="datepicker" id="" name="trainingStarttime" value="<s:date name="ProjectApply.trainingStarttime" format="yyyy-MM-dd"/>">
							</div>
							<input type="checkbox" id="indeterminate" alt="是否待定" style="margin-left: 120px">是否待定
						</div>
						<div class="control-group">
							<label class="control-label" for="">计划结束时间</label>
							<div class="controls">
								<input type="text" readonly autocomplete="off" data-provide="datepicker"
									class="datepicker" id="" name="trainingEndtime" value="<s:date name="ProjectApply.trainingEndtime" format="yyyy-MM-dd"/>">
								
							</div>
						</div>
								
					</div>
				</div>
				<style>
					.attachment p {padding:4px 7px 4px 18px;background:url(../img/attachment.gif) no-repeat 0 50%;margin-bottom:0;margin-left:20px;}
				</style>
				<div class="row">
					<div class="span6">
					<div class="control-group">
							<label class="control-label" for=""></label>
							<div class="controls attachment">
							<s:iterator value="hmFiles" id="e">
							<p><a href="..<s:property value="#e.value.filePath"/>"><s:property value=" #e.value.fileName"/>(文件大小：<s:property value=" #e.value.fileSize"/>字节)</a>
							</s:iterator>
							<s:iterator value="lsFiles" id="e">
							<p><a href="..<s:property value="#e.filePath"/>"><s:property value=" #e.fileName"/>(文件大小：<s:property value=" #e.fileSize"/>字节)</a>
							</s:iterator>
							</div>
						</div>
					</div>

				</div>


				<div class="row actionbar">
					<div class="offset8">
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
			
			$('#indeterminate').click(function(){
				var date = $("[name='trainingStarttime']").val();
				if($('#indeterminate').is(":checked")){
					$("[name='trainingStarttime']").val('');
					$("[name='trainingStarttime']").attr('disabled',true);
				}else {
					$("[name='trainingStarttime']").removeAttr('disabled');
					$("[name='trainingStarttime']").val(date);
				}
				
			});
			
			if($("[name='trainingStarttime']").val() == ''){
				$("[name='trainingStarttime']").attr('disabled',true);
				$('#indeterminate').attr('checked',true);
			}
			
		})
		
// 		function isNumeric(a) {
// 			var reg=/^(-|+)?d+(.d+)?$/
// 			return(reg.test(a));
// 		}
		
		$(function() {
			var submitFlag = true;
			$('#ProjectApply').submit(function() {
				if(submitFlag){
					submitFlag = false;
					$('.alert-danger').hide();
					var reg=/^[0-9]+$/;
					var check1 = $("input[name='trainingClasshour']").val();
					var check2 = $("input[name='trainingOnlineHour']").val();
					var result= reg.test(check1);
					if(check1 != "" && !isNaN(parseInt(check1)) && !result){
						$('.alert-danger').html("集中培训课时为纯数字").show();
						submitFlag = true;
						return false;
					}
					
					var result2= reg.test(check2);
					if(check2 != "" && !isNaN(parseInt(check2)) && !result2){
						$('.alert-danger').html("远程培训课时为纯数字").show();
						submitFlag = true;
						return false;
					}
					var str =$(this).serialize();
					//str=encodeURI(str);
					
					$.post('../admin/trainingUnitProjectApply_addProjectapply.action?'+ str, function(data) {
						var Result = data.Result;
						var message = data.Message;
						if (Result == "OK") {
							alert("申报成功，请等待审核结果！");
							window.top.location.reload();
						} else {
							$('.alert-danger').html(message).show();
							submitFlag = true;
						}
					})
				}
				return false;
			});

		})
		
		
	</script>
<body>
</html>

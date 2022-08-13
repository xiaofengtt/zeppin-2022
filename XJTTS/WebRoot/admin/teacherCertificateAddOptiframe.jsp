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
<script src="../js/url.min.js"></script>

</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>教师资格证信息<s:if test="teacherCertificate.id==null" >(未注册)</s:if></h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" id="ProjectApply" action=""
				method="post" name="ProjectApply" enctype="multipart/form-data" >
				<input type="hidden" name="id" value="<s:property	value="teacherCertificate.id" />">
				<div class="clearfix">

					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">教师资格证编号</label>
							<div class="controls">
								<input type="text" name="certificate"
									value="<s:property	value="teacherCertificate.certificate" />" placeholder="编号"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">教师资格证认证机构</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off" name="certificateBody"
									value="<s:property	value="teacherCertificate.certificationBody" />" placeholder="认证机构"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">教师资格证资格种类</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off" name="type"
									value="<s:property	value="teacherCertificate.type" />" placeholder="资格种类"/>
							</div>
						</div>
						
					</div>
					<div class="span5">

						<div class="control-group">
							<label class="control-label" for="">认证时间</label>
							<div class="controls">
								<input type="text" readonly autocomplete="off" data-provide="datepicker"
									class="datepicker" id="" name="gettime" value="<s:date name="teacherCertificate.gettime" format="yyyy-MM-dd"/>">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">状态</label>
							<div class="controls">
								<select name="status">
									<option value="1"
										<s:if test="teacherCertificate.status==1" >selected</s:if>>正常</option>
									<option value="0"
										<s:if test="teacherCertificate.status==0" >selected</s:if>>停用</option>
								</select>
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
				endDate : '1d',
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
			$('#ProjectApply').submit(
					function() {
						$('.alert-danger').hide();
						var teacherId = url('?teacherId');
						var reg=/^[0-9]+$/;
						var check1 = $("input[name='trainingClasshour']").val();
						var check2 = $("input[name='trainingOnlineHour']").val();
						var result= reg.test(check1);
						if(check1 != "" && !isNaN(parseInt(check1)) && !result){
							$('.alert-danger').html("集中培训课时为纯数字").show();
							return false;
						}
						
						var result2= reg.test(check2);
						if(check2 != "" && !isNaN(parseInt(check2)) && !result2){
							$('.alert-danger').html("远程培训课时为纯数字").show();
							return false;
						}
						var str =$(this).serialize();
						//str=encodeURI(str);
						
						$.post('../admin/teacherManage_addCertificate.action?'+ str+'&teacherId='+teacherId, function(data) {
							var Result = data.Result;
							var message = data.Message;
							if (Result == "OK") {
								alert("操作成功！");
								window.top.location.reload();
							} else {
								$('.alert-danger').html(message).show();
							}
						})
						return false;
					});

		})
		
		
	</script>
<body>
</html>

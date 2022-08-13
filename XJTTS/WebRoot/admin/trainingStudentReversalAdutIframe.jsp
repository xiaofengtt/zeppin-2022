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
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">
<link href="../css/datepicker3.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<script src="../js/url.min.js"></script>


</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>请确认信息</h3>
		</div>
		<div class="hd">
			<div class="alert alert-danger" align="center"
				style="display: none; margin: 0 15px 15px;"></div>
			<h4 align="center">
				<s:property value="teacherTrainingRecords.project.name" />
			</h4>
		</div>
		<div class="bd">
			<label class="control-label" style="text-align: center;color: #044d79;font-weight: bold;margin-bottom: 30px" for="">教师基本信息</label>
			<form class="form-horizontal" id="changeSubject" action="" method="post">
				<div class="clearfix">
					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text" readonly="readonly" class="readonly"
									value="<s:property	value="teacherEx.teacher.name" />" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off" readonly="readonly" class="readonly"
									value="<s:property	value="teacherEx.teacher.idcard" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所在地区</label>
							<s:set name="retArea" value="area" />
							<div class="controls">
								<input type="text" autocomplete="off" readonly="readonly"
									value="<s:property	value="teacherEx.areaString" />" placeholder="所属地区">
							</div>
						</div>

					</div>


					<div class="span5">

						<div class="control-group">
							<label class="control-label" for="">手机</label>
							<div class="controls">
								<input type="text" readonly="readonly" class="readonly"
									value="<s:property	value="teacherEx.teacher.mobile" />" placeholder="手机">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所属学校</label>
							<div class="controls">
								<input type="text" readonly="readonly" class="readonly"
									value="<s:property	value="teacherEx.organization.name" />" placeholder="所属学校">

							</div>
						</div>

					</div>
					
					
					
				</div>

				<div class="row actionbar">
				
				
					<div align="center" style="margin-left: 30px">
						<label style="text-align: center;color: #044d79;font-weight: bold;margin-bottom: 30px" for=""><h3>调整前</h3></label>
						<div class="clearfix" style="margin-bottom: 20px">
							<div class="span5">
								<div class="control-group">
									<label class="control-label" for="">培训学科:</label>
									<input type="text" readonly="readonly" class="readonly"
									value="<s:property	value="oldTrainingSubject.name" />" placeholder="调整前培训学科">
								</div>
							</div>
							<div class="span5">
								<div class="control-group">
									<label class="control-label" for="">班级:</label>
									<input type="text" readonly="readonly" class="readonly"
										value="<s:property	value="oldClasses" />班" placeholder="调整前班级">
								</div>
							</div>
						</div>
						
						<div class="row actionbar">
						<label style="text-align: center;color: #044d79;font-weight: bold;margin-bottom: 30px" for=""><h3>调整后</h3></label>
							<div class="clearfix" style="margin-bottom: 20px">
								<div class="span5">
									<div class="control-group">
										<label class="control-label" for="">培训学科:</label>
										<input type="text" readonly="readonly" class="readonly"
										value="<s:property	value="trainingSubject.name" />" placeholder="调整后培训学科">
									</div>
								</div>
								<div class="span5">
									<div class="control-group">
										<label class="control-label" for="">班级:</label>
										<input type="text" readonly="readonly" class="readonly"
										value="<s:property	value="classes" />班" placeholder="调整后班级">
									</div>
								</div>
							</div>
						</div>
						<a class="btn btn-default btn-myself" onclick="adunopass();" id="adunopass" href="javascript:void(0)" data-url="../admin/trainingStudentOpt_changeSubject.action?method=nopass">审核不通过</a>
						<a class="btn btn-default btn-primary btn-myself" onclick="adupass();" id="adupass" href="javascript:void(0)" data-url="../admin/trainingStudentOpt_changeSubject.action?method=pass">审核通过</a>    
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()" class="btn btn-default btn-myself" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>
	</div>
	<script>
	function adupass() {
		var obj = $('#adupass');
		var cUrl = obj.attr('data-url');
		var id = url('?id');
		cUrl += '&id='+id;
		$.getJSON(cUrl, function(ret) {
			if (ret.Result == 'OK') {
				alert('成功,' + ret.Message);
				window.parent.$('#ProjectTableContainer').jtable('load');
				parent.$.colorbox.close();
			} else {
				alert('失败,' + ret.Message);
			}
		})
		return false;
	}
	
	function adunopass() {
		var obj = $('#adunopass');
		var cUrl = obj.attr('data-url');
		var id = url('?id');
		cUrl += '&id='+id;
		$.getJSON(cUrl, function(ret) {
			if (ret.Result == 'OK') {
				alert('成功,' + ret.Message);
				window.parent.$('#ProjectTableContainer').jtable('load');
				parent.$.colorbox.close();
			} else {
				alert('失败,' + ret.Message);
			}
		})
		return false;
	}
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
				startView : 2,
				endDate : '1d',
				autoclose : true
			});

			$('.switch').bootstrapSwitch();

			$('#mainTeachingSubject,#mainTeachingGrades,#mainTeachingLanguage')
					.select2();
			$(
					'#unMainTeachingSubject,#unMainTeachingGrades,#unMainTeachingLanguage')
					.select2();

			$("#organization").select2({
				placeholder : "请输入学校名称或拼音首字母",
				minimumInputLength : 0,
				quietMillis : 1000,
				allowClear : true,
				ajax : {
					url : "../base/organization_searchSchool.action",
					dataType : 'json',
					data : function(term, page) {
						return {
							search : term, // search term
							page_limit : 10
						};
					},
					results : function(data, page) {
						return {
							results : data.Options
						};

					}
				},

				initSelection : function(element, callback) {
					element = $(element);
					var data = {
						id : element.val(),
						DisplayText : element.attr('data-name')
					};
					callback(data);
				},
				formatResult : movieFormatResult,
				formatSelection : movieFormatSelection,
				dropdownCssClass : "bigdrop",
				escapeMarkup : function(m) {
					return m;
				}
			})

			function movieFormatResult(Options) {
				var html = Options.DisplayText;
				return html;

			}
			function movieFormatSelection(Options) {
				return Options.DisplayText;
			}
		})

		$(function() {
			$('#changeSubject')
					.submit(
							function() {
								var ttrId = url("?id")
								$.post('../admin/trainingStudentOpt_changeSubject.action?id='+ ttrId,
												function(ret) {
													if (ret.Result == 'OK') {
														alert('成功,'
																+ ret.Message);
														top.location.href = '../admin/trainingStudentOpt_getChangeSubjectRecordsInit.action';
													} else {
														$('.alert-danger')
																.html(
																		ret.Message)
																.show();
													}
												})
								// 						var projectId = url("?projectId");
								// 						var subjectId = url("?subjectId");
								// 						var collegeId = url("?collegeId");
								// 						var gorganizationId = url("?gorganizationId");
								// 						var porganizationId = url("?porganizationId");
								// 						var teacherId = url("?teacherId");
								// 						var replacedTtrId = url("?replacedTtrId");
								// 						var str ="projectId="+projectId+"&subjectId="+subjectId+"&collegeId="+collegeId+"&gorganizationId="+gorganizationId+"&porganizationId="+porganizationId+"&teacherId="+teacherId+"&replacedTtrId="+replacedTtrId;
								// 						$.post('../admin/ttRecord_addReplaceTeacher.action?'+ str, function(ret) {
								// 							if (ret.Result == 'OK') {
								// 								alert('成功,' + ret.Message + ',确定将回到学员审核。。。');
								// 								top.location.href='../admin/ttRecord_initAduPage.action';
								// 							} else if (ret.Result == 'WARNING') {
								// 								if (confirm("人数已经达到上限，是否继续添加?")) {
								// 									var tcUrl = '../admin/ttRecord_addReplaceTeacher.action?'+ str + '&goon=1';
								// 									$.getJSON(tcUrl, function(ret) {
								// 										if (ret.Result == 'OK') {
								// 											alert('成功,' + ret.Message + ',确定将回到学员审核。。。');
								// 											top.location.href='../admin/ttRecord_initAduPage.action';
								// 										} else if (ret.Result == "ERROR" || ret.Result == 'FAIL') {
								// 											$('.alert-danger').html(ret.Message).show();
								// 										}else if (ret.Result == "REPLACE") {
								// 											alert('失败,' + ret.Message);
								// 											top.location.href='../admin/ttRecord_initAduPage.action';
								// 										}
								// 									});
								// 								}
								// 							}else if (ret.Result == "ERROR" || ret.Result == 'FAIL') {
								// 								$('.alert-danger').html(ret.Message).show();
								// 							}else if (ret.Result == "REPLACE") {
								// 								alert('失败,' + ret.Message);
								// 								top.location.href='../admin/ttRecord_initAduPage.action';
								// 							}
								// 						})
								return false;
							});

		})
	</script>
<body>
</html>

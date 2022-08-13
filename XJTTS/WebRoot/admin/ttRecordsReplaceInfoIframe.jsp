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
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>

</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>请确认替换学员信息</h3>
		</div>
		<div class="hd">
		<div class="alert alert-danger" align="center"
				style="display:none;margin:0 15px 15px;"></div> 
			<h4 align="center"><s:property value="ttRe.project.name"/> >> <s:property value="ttRe.trainingSubject.name"/></h4>
		</div>
		<div class="bd">
			
			<form class="form-horizontal" id="teacherManage" action=""
				method="post" name="teahcer">
				<input type="hidden" name="teacher.id" value="<s:property	value="tTeacher.id" />">
				<div class="clearfix">

					<div class="span5" style="border-right:1px solid #000">
					<label style="text-align: center; margin-bottom: 10px"><font color=red>（原报送学员）</font></label>
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text" readonly="readonly" value="<s:property	value="reTeacherEx.teacher.name" />" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">性别</label>
							<div class="controls">
								<input type="text" readonly="readonly" value="<s:property	value="reTeacherEx.sexString" />"/>
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id=""  readonly="readonly"
									value="<s:property	value="reTeacherEx.teacher.idcard" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所在地区</label>
							<div class="controls">
								<input type="text" id=""  readonly="readonly"
									value="<s:property	value="reTeacherEx.areaString" />" placeholder="">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">民族</label>
							<div class="controls">
								<input type="text" id=""  readonly="readonly"
									value="<s:property	value="reTeacherEx.teacher.ethnic.name" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">手机</label>
							<div class="controls">
								<input type="text" id="" name="mobile" readonly="readonly"
									value="<s:property	value="reTeacherEx.teacher.mobile" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所属学校</label>
							<div class="controls">
							<input type="text" id="" name="mobile" readonly="readonly"
									value="<s:property	value="reTeacherEx.organization.name" />" placeholder="">	

							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">主要教学学科</label>
							<div class="controls">
							<input type="text" id="" readonly="readonly"
									value="<s:property	value="reTeacherEx.mainTeachingCourse.subject.name" />" placeholder="">	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">主要教学学段</label>
							<div class="controls">
							<input type="text" id="" readonly="readonly"
									value="<s:property	value="reTeacherEx.mainTeachingClass.grade.name" />" placeholder="">	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">主要教学语言</label>
							<div class="controls">
							<input type="text" id="" readonly="readonly"
									value="<s:property	value="reTeacherEx.mainTeachingLanguage.language.name" />" placeholder="">	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">汉语言水平</label>
							<div class="controls">
							<input type="text" id="" readonly="readonly"
									value="<s:property	value="reTeacherEx.teacher.chineseLanguageLevel.name" />" placeholder="">	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">是否双语教学</label>
							<div class="controls">
								<input type="text" readonly="readonly" value="<s:property	value="reTeacherEx.isMultiLanguage" />"/>
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">编制</label>
							<div class="controls">
								<input type="text" readonly="readonly" value="<s:property	value="reTeacherEx.authorized" />"/>
								
							</div>
						</div>
						
					</div>
						<img alt="" src="../img/replace.png" width="40px" height="40px" style="text-align: center; padding-top: 240px; padding-left: 10px">
					<div class="span5" style="border-left:1px solid #000; float: right;margin-left: 0px" >
						<label style="text-align: center; margin-bottom: 10px"><font color=green>（替换学员）</font></label>
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text" readonly="readonly" value="<s:property	value="teacherEx.teacher.name" />" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">性别</label>
							<div class="controls">
								<input type="text" readonly="readonly" value="<s:property	value="teacherEx.sexString" />"/>
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id=""  readonly="readonly"
									value="<s:property	value="teacherEx.teacher.idcard" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所在地区</label>
							<div class="controls">
								<input type="text" id=""  readonly="readonly"
									value="<s:property	value="teacherEx.areaString" />" placeholder="">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">民族</label>
							<div class="controls">
								<input type="text" id=""  readonly="readonly"
									value="<s:property	value="teacherEx.teacher.ethnic.name" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">手机</label>
							<div class="controls">
								<input type="text" id="" name="mobile" readonly="readonly"
									value="<s:property	value="teacherEx.teacher.mobile" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所属学校</label>
							<div class="controls">
							<input type="text" id="" name="mobile" readonly="readonly"
									value="<s:property	value="teacherEx.organization.name" />" placeholder="">	

							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">主要教学学科</label>
							<div class="controls">
							<input type="text" id="" readonly="readonly"
									value="<s:property	value="teacherEx.mainTeachingCourse.subject.name" />" placeholder="">	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">主要教学学段</label>
							<div class="controls">
							<input type="text" id="" readonly="readonly"
									value="<s:property	value="teacherEx.mainTeachingClass.grade.name" />" placeholder="">	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">主要教学语言</label>
							<div class="controls">
							<input type="text" id="" readonly="readonly"
									value="<s:property	value="teacherEx.mainTeachingLanguage.language.name" />" placeholder="">	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">汉语言水平</label>
							<div class="controls">
							<input type="text" id="" readonly="readonly"
									value="<s:property	value="teacherEx.teacher.chineseLanguageLevel.name" />" placeholder="">	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">是否双语教学</label>
							<div class="controls">
								<input type="text" readonly="readonly" value="<s:property	value="teacherEx.isMultiLanguage" />"/>
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">编制</label>
							<div class="controls">
								<input type="text" readonly="readonly" value="<s:property	value="teacherEx.authorized" />"/>
								
							</div>
						</div>

					</div>
				</div>
				



				<div class="row actionbar">
					<div align="center" style="margin-left: 50px;padding-top: 20px">
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

			$('#mainTeachingSubject,#mainTeachingGrades,#mainTeachingLanguage').select2();
			$('#unMainTeachingSubject,#unMainTeachingGrades,#unMainTeachingLanguage').select2();
			
			$("#organization").select2({
			    placeholder: "请输入学校名称或拼音首字母",
			    minimumInputLength: 0,
				quietMillis : 1000,
				allowClear : true,
			    ajax: { 
			        url: "../base/organization_searchSchool.action",
			        dataType: 'json',
			        data: function (term, page) {
			            return {
			                search: term, // search term
			                page_limit: 10
			            };
			        },
			        results: function (data, page) {
			            return {results: data.Options};
						
			        }
			    },
				
				initSelection: function (element, callback) {
                    element = $(element);
                	  var data = {id: element.val(), DisplayText: element.attr('data-name')};
				    callback(data);
                },
			    formatResult: movieFormatResult, 
			   formatSelection: movieFormatSelection, 
				dropdownCssClass: "bigdrop",
			    escapeMarkup: function (m) { return m; } 
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
			$('#teacherManage').submit(
					function() {
						layer.prompt({
						  formType: 0,
						  value: '',
						  title: '请输入申请替换原因（30字内）',
						  maxlength: 30
						 
						}, function(value, index, elem){
						  layer.close(index);
						var projectId = url("?projectId");
						var subjectId = url("?subjectId");
						var collegeId = url("?collegeId");
						var gorganizationId = url("?gorganizationId");
						var porganizationId = url("?porganizationId");
						var teacherId = url("?teacherId");
						var replacedTtrId = url("?replacedTtrId");
// 						var str ="projectId="+projectId+"&subjectId="+subjectId+"&collegeId="+collegeId+"&gorganizationId="+gorganizationId+"&porganizationId="+porganizationId+"&teacherId="+teacherId+"&replacedTtrId="+replacedTtrId+"&reason="+value;
						$.post('../admin/ttRecord_replaceTeacher.action',{
							projectId:projectId,
							subjectId:subjectId,
							collegeId:collegeId,
							gorganizationId:gorganizationId,
							porganizationId:porganizationId,
							teacherId:teacherId,
							replacedTtrId:replacedTtrId,
							reason:value
						}, function(ret) {
							if (ret.Result == 'OK') {
								alert('成功,' + ret.Message + ',确定将回到学员审核。。。');
								top.location.href='../admin/ttRecord_initAduPage.action';
							} else if (ret.Result == 'WARNING') {
								if (confirm("人数已经达到上限，是否继续添加?")) {
									var tcUrl = '../admin/ttRecord_replaceTeacher.action';
									$.post(tcUrl,{
										projectId:projectId,
										subjectId:subjectId,
										collegeId:collegeId,
										gorganizationId:gorganizationId,
										porganizationId:porganizationId,
										teacherId:teacherId,
										replacedTtrId:replacedTtrId,
										reason:value,
										goon:1
									}, function(ret) {
										if (ret.Result == 'OK') {
											alert('成功,' + ret.Message + ',确定将回到学员审核。。。');
											top.location.href='../admin/ttRecord_initAduPage.action';
										} else if (ret.Result == "ERROR" || ret.Result == 'FAIL') {
											$('.alert-danger').html(ret.Message).show();
										}else if (ret.Result == "REPLACE") {
											alert('失败,' + ret.Message);
											top.location.href='../admin/ttRecord_initAduPage.action';
										}
									});
								}
							}else if (ret.Result == "ERROR" || ret.Result == 'FAIL') {
								$('.alert-danger').html(ret.Message).show();
							}else if (ret.Result == "REPLACE") {
								alert('失败,' + ret.Message);
								top.location.href='../admin/ttRecord_initAduPage.action';
							}
						})
						});
						return false;
					});

		})
		
		
	</script>
<body>
</html>

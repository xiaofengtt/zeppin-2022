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


</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>学员信息管理</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" id="teacherManage" action="teacherManage_addItem.action"
				method="post" name="teahcer">
				<input type="hidden" name="teacher.id" value="<s:property	value="teacher.id" />" />
				<div class="clearfix">

					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text" name="name"
									value="<s:property	value="teacher.name" />" class="readonly" readonly />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off" name="idCard"
									value="<s:property	value="teacher.idcard" />" placeholder="" class="readonly" readonly />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所在地区</label>
							<s:set name="retArea" value="area" />
							<div class="controls">
								<select onchange="areacityy(this,'city')" style="width:90px;"
									class="span1" name="provinceId">
									<option value="-1">请选择</option>
									<s:iterator value="lstProvince" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==province.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>

								</select> <select id="areacity" style="width:80px;"
									onchange="areacityy(this,'county')" class="span1" name="cityId">
									<option value="-1">请选择</option>
									<s:iterator value="lstCity" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==city.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>

								</select> <select id="areacounty" style="width:80px;" class="span1"
									name="countyId">
									<option value="-1">请选择</option>
									<s:iterator value="lstCountry" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==county.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>

								</select>
							</div>
						</div>


						<div class="control-group">
							<label class="control-label" for="">职务</label>
							<div class="controls">
								<select name="jobDuty">
									<option value="-1">请选择</option>
									<s:iterator value="lstJobDuties" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.jobDuty.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">职称</label>
							<div class="controls">
								<select name="jobTitle">
									<option value="-1">请选择</option>
									<s:iterator value="lstJobTitles" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.jobTitle.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">最高学历</label>
							<div class="controls">
								<select name="eductionBackground">
									<option value="-1">请选择</option>
									<s:iterator value="lstBackgrounds" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.eductionBackground.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">政治面貌</label>
							<div class="controls">
								<select name="politics">
									<option value="-1">请选择</option>
									<s:iterator value="lstPolitics" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.politics.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">主要教学学科</label>
							<div class="controls">
								<select name="mainTeachingSubject" id="mainTeachingSubject"
									class="span3">
									<option value="-1">请选择</option>
									<s:iterator value="lstteacTeachingSubjectExs" id="e">
										<option value="<s:property value="#e.id" />"
											search="<s:property
												value="#e.searchString" />"
											<s:if test="%{#e.id==teacherEx.mainTeachingCourse.subject.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">主要教学学段</label>
							<div class="controls">
								<select name="mainTeachingGrades" id="mainTeachingGrades"
									class="span3">
									<option value="-1">请选择</option>
									<s:iterator value="lstGrades" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacherEx.mainTeachingClass.grade.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">主要教学语言</label>
							<div class="controls">
								<select name="mainTeachingLanguage" id="mainTeachingLanguage"
									class="span3">
									<option value="-1">请选择</option>
									<s:iterator value="lstLanguages" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacherEx.mainTeachingLanguage.language.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">备注一</label>
							<div class="controls">
								<input type="text" name="remark1"
									value="<s:property
										value="teacher.remark1" />">

							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">备注二</label>
							<div class="controls">
								<input type="text" name="remark2"
									value="<s:property
										value="teacher.remark2" />">

							</div>
						</div>
						
					</div>
					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">民族</label>
							<div class="controls">
								<select name="ethnic">
									<option value="-1">请选择</option>
									<s:iterator value="lstEthnics" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.ethnic.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">手机</label>
							<div class="controls">
								<input type="text" id="" name="mobile"
									value="<s:property	value="teacher.mobile" />" placeholder="手机">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所属学校</label>
							<div class="controls">
							<input name="organization" data-val="true" data-val-number="<s:property value="organizationp.id"/>" data-name="<s:property value="organizationp.name"/>" id="organization" type="text" class="span3"  value='<s:property value="organizationp.id"/>'>	

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">汉语言水平</label>
							<div class="controls">
								<select name="chineseLanguageLevel">
									<option value="-1">请选择</option>
									<s:iterator value="lstChineseLanguageLevels" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.chineseLanguageLevel.id}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">初次任教时间</label>
							<div class="controls">
								<input type="text" readonly autocomplete="off" data-provide="datepicker"
									class="datepicker" id="" name="teachingAge" value="<s:date name="teacher.teachingAge" format="yyyy-MM-dd"/>">
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">邮箱</label>
							<div class="controls">
								<input type="text" id="" name="email"
									value="<s:property value="teacher.email" />" placeholder="邮箱">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">编制</label>
							<div class="controls">
								<select name="authorized">
									<option value="1"
										<s:if test="teacher.authorized==1" >selected</s:if>>在编</option>
									<option value="0"
										<s:if test="teacher.authorized==0" >selected</s:if>>非编</option>
								</select>
							</div>
						</div>
						
						
						<div class="control-group">
							<label class="control-label" for="">其他教学学科</label>
							<div class="controls">
								<select name="unMainTeachingSubject" id="unMainTeachingSubject"
									multiple class="span3">
									<s:iterator value="lstteacTeachingSubjectExs" id="e">
										<option value="<s:property value="#e.id" />"
											search="<s:property
												value="#e.searchString" />"
											<s:if test="%{#e.id==teacher.chineseLanguageLevel}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
									<s:iterator value="teacherEx.unMaintTeachingSubjects" id="ee">
									<option value="<s:property value="#ee.subject.id" />"selected><s:property
												value="#ee.subject.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						
						<div class="control-group">
							<label class="control-label" for="">其他教学学段</label>
							<div class="controls">
								<select name="unMainTeachingGrades" id="unMainTeachingGrades"
									multiple class="span3">
									<s:iterator value="lstGrades" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.chineseLanguageLevel}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
									<s:iterator value="teacherEx.unMainTeachingGrades" id="ee">
								<option value="<s:property value="#ee.grade.id" />"selected><s:property
												value="#ee.grade.name" /></option>
								</s:iterator>
								</select>
								
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">其他教学语言</label>
							<div class="controls">
								<select name="unMainTeachingLanguage"
									id="unMainTeachingLanguage" multiple class="span3">
									<s:iterator value="lstLanguages" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.chineseLanguageLevel}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
									<s:iterator value="teacherEx.unMainTeachingLanguages" id="ee">
								<option value="<s:property value="#ee.language.id" />"selected><s:property
												value="#ee.language.name" /></option>
								</s:iterator>
								</select>
								
							</div>
							
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">是否双语教学</label>
							<div class="controls">
								<input name="multiLanguage" type="checkbox" data-text-label=""
									data-on="primary" data-off="default" data-on-label="是"
									data-off-label="否"
									<s:if test="teacher.multiLanguage==1" >checked</s:if>
								value="1"	class="switch">
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
						var str =$(this).serialize();
						str=encodeURI(str);
						//alert(str);
						$.post('../admin/teacherInfo_addItem.action?'+ str, function(data) {
							var Result = data.Result;
							var message = data.Message;
							if (Result == "OK") {
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

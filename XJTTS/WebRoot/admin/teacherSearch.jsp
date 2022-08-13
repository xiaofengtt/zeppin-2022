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
<link rel="stylesheet" href="../css/app.css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
</head>
<body>
	<div class="ifrcnt container" style="width:100%;">
		<div class="hd">
			<h3>搜索学员</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" action="teacherManage_search.action"
				method="post" name="teahcer" id="teacherSearch">

				<div class="clearfix">

					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text" name="tname" placeholder=" " />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off" name="idCard"
									placeholder="身份证号">
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">毕业院校</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off" name="graduation"
									placeholder="毕业院校">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">职称</label>
							<div class="controls">
								<select name="jobTitle">
									<option value="-1">请选择</option>
									<s:iterator value="lstJobTitles" id="e">
										<option value="<s:property value="#e.id" />"><s:property
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
										<option value="<s:property value="#e.id" />"><s:property
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
										<option value="<s:property value="#e.id" />"><s:property
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
										<option value="<s:property value="#e.id" />"><s:property
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
										<option value="<s:property value="#e.id" />"><s:property
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
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">性别</label>
							<div class="controls">
								<select name="sex">
								<option value="-1">请选择</option>
									<option value="1">男</option>
									<option value="2">女</option>
								</select>
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
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">手机</label>
							<div class="controls">
								<input type="text" id="" name="mobile" placeholder="手机">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所属学校</label>
							<div class="controls">
							<input type="text" id="organization" class="span3" name="organization" value="" placeholder="">
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">职务</label>
							<div class="controls">
								<select name="jobDuty">
									<option value="-1">请选择</option>
									<s:iterator value="lstJobDuties" id="e">
										<option value="<s:property value="#e.id" />">
										<s:property value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">汉语言水平</label>
							<div class="controls">
								<select name="chineseLanguageLevel">
									<option value="-1">请选择</option>
									<s:iterator value="lstChineseLanguageLevels" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">教龄</label>
							<div class="controls">
								
								<div class="input-daterange input-group" id="datepicker">
								    <input type="text" class="input-sm form-control" value="" name="teachingAgeStart">
								    <span class="input-group-addon"> 至 </span>
								    <input type="text" class="input-sm form-control" value="" name="teachingAgeEnd">
								</div>
								
							</div>
						</div>

						
						<div class="control-group">
							<label class="control-label" for="">邮箱</label>
							<div class="controls">
								<input type="text" id="" name="email" placeholder="邮箱">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">编制</label>
							<div class="controls">
								<select name="authorized">
									<option value="-1">全部</option>
									<option value="1">在编</option>
									<option value="0">非编</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">状态</label>
							<div class="controls">
								<select name="status">
								<option value="-1">请选择</option>
									<option value="1">在职</option>
									<option value="2">离职</option>
									<option value="3">转出</option>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">是否双语教学</label>
							<div class="controls">
									<select name="multiLanguage">
								<option value="-1">请选择</option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
						</div>


					</div>
				</div>



				<div class="row actionbar">
					<div class="text-center">
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
			
			$("#organization").select2({
			    placeholder: "请输入学校名称或拼音首字母",
			    minimumInputLength: 0,
				allowClear : true,
				quietMillis : 1000,
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
			
			

		});
		
		$(function() {
			$('#teacherSearch').submit(
					function() {
						var str =$(this).serialize();
						str = decodeURIComponent(str,true);
						str = encodeURI(encodeURI(str));
						$.getJSON('../admin/teacherManage_search.action?'+ str, function(data) {
							var Result = data.Result;
							var message = data.Message;
							if (Result == "OK") {
								if (window.parent != null || window.parent != window.top) {
									if(url('?') != 'undefined') {
										var lo = window.top.location.href;
										window.top.location = lo.split('?')[0] +'?opt=1&search=';
									}else {
										window.top.location = window.top.location.href +'?opt=1&search=';
									}
									
								}
							} else {
								$('.alert-danger').html(message).show();
							}
						}).fail(function(){
							$('.alert-danger').html('系统错误请稍后重试').show();
						})
						return false;
					});

		})
	</script>
	<script src="../js/iframe.js"></script>
<body>
</html>
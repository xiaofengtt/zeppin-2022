<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName"></s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>

<div class="main">

	<div class="listwrap">
		<div class="list_bar">添加报送学员</div>
		<div class="list-content clearfix" style="padding-top:15px;">
		
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
				<div class="fliter-btn-wrap" style="display:none">
					<a href="../admin/ttRecord_initReortAsstPage.action" class="btn btn-primary">&larr;返回报送任务</a>
					<button class="btn btn-primary fliter-btn"  type="button">查找学员</button>
				</div>
			<form id="ttRecordsReport" class="form-horizontal" role="form">
				
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">身份证号</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="teacherIdCard">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">所属学校</label>
							<div class="col-sm-9">
								<input name="organization" id="organization" type="hidden"
									value='<s:property value="organizationp.id"/>'>
							</div>

						</div>

						<!-- <div class="form-group">
							<label class="col-sm-2 control-label" for="">性别</label>
							<div class="col-sm-9">
								<select name="sex" class="form-control">
									<option value="">请选择</option>
									<option value="1">男</option>
									<option value="2">女</option>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">民族</label>
							<div class="col-sm-9">
								<select name="ethnic" class="form-control">
									<option value="">请选择</option>
									<s:iterator value="lstEthnics" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher. ethnic}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">是否双语教学</label>
							<div class="col-sm-9">
								<input type="radio" name="isMu" value="" checked>&nbsp;&nbsp;全部&nbsp;&nbsp;
								<input type="radio" name="isMu" value="1">&nbsp;&nbsp;是 &nbsp;&nbsp;
								<input type="radio" name="isMu" value="0"> &nbsp;&nbsp;否 &nbsp;&nbsp;
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">职称</label>
							<div class="col-sm-9">
								<select name="jobTitle" class="form-control">
									<option value="">请选择</option>
									<s:iterator value="lstJobTitles" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher. jobTitle}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">汉语言水平</label>
							<div class="col-sm-9">
								<select name="chineseLanguageLevel" class="form-control">
									<option value="">请选择</option>
									<s:iterator value="lstChineseLanguageLevels" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.chineseLanguageLevel}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">主要教学学科</label>
							<div class="col-sm-9">
								<select name="mainGrade" id="mainGrade" class="form-control">
									<option value="">请选择</option>
									<s:iterator value="lstSubjects" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div> -->

					</div>

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">姓名</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="teacherName">
							</div>

						</div>
						<!-- <div class="form-group">
							<label class="col-sm-2 control-label" for="">年龄</label>
							<div class="col-sm-9">
								<input type="text" class="form-control col-sm-4" style="width:50px"  name="age1"> <span style="padding:6px 0;text-align:center"  class="col-xs-1">至</span> <input
									type="text"  class="form-control col-sm-4"  style="width:50px"  name="age2">
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">教龄</label>
							<div class="col-sm-9 form-inline">
								<input type="text" class="form-control col-sm-4" name="tage1" style="width:50px"> <span  class="col-xs-1"  style="padding:6px 0;text-align:center">至</span> <input
									class="form-control col-sm-4" type="text" name="tage2" style="width:50px">
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">政治面貌</label>
							<div class="col-sm-9">
								<select class="form-control" name="politics">
									<option value="">请选择</option>
									<s:iterator value="lstPolitics" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">行政职务</label>
							<div class="col-sm-9">
								<select class="form-control" name="jobDuty">
									<option value="">请选择</option>
									<s:iterator value="lstJobDuties" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">最高学历</label>
							<div class="col-sm-9">
								<select class="form-control" name="eductionBackground">
									<option value="">请选择</option>
									<s:iterator value="lstBackgrounds" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">主要教学语言</label>
							<div class="col-sm-9">
								<select class="form-control" name="mainTeachingLanguage" id="mainTeachingLanguage"
									class="span3">
									<option value="">请选择</option>
									<s:iterator value="lstLanguages" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">主要教学学段</label>
							<div class="col-sm-9">
								<select class="form-control" name="mainTeachingGrades" id="mainTeachingGrades"
									class="span3">
									<option value="">请选择</option>
									<s:iterator value="lstGrades" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div> -->

					</div>
				</div>

				<div class="clearfix" style="margin:0 40px 0 20px">
					<div id="list-content" class="list-content clearfix"></div>
				</div>

				<div class="row actionbar">
					<div style="text-align:center">
						<button class="btn btn-primary" id="findRecord" type="submit">查询</button>
					</div>
				</div>
			</form>
		</div>
		<div id="list-item-cnt" style="margin-top:10px;"></div>
		<div id="pagination" style="float:right;margin-right:10px" class="pull-right"></div>
		<script id="ttRecordTpl" type="text/x-jsrender">
			<div id="{{:id}}" class="list-item">
				<div class="list-item-hd">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="auto"><span class="text-primary">所属学校：</span>{{:school}}</td>
								<td width="200px" class="text-primary">姓名：<span>{{:name}}</span></td>
								
								<td width="160px"><span id="status_{{:id}}">
									{{if record_status}}
									<a onclick="addRecord(this)" data-id="{{:id}}" data-url="../admin/ttRecord_addTeacherReport.action?projectId={{:projectId}}&subjectId={{:subjectId}}&collegeId={{:tcid}}&gorganizationId={{:gorganizationId}}&&porganizationId={{:porganizationId}}&teacherId={{:teacherId}}" href="javascript:void(0)">添加该学员</a>
									{{else}}
									添加成功
									{{/if}}
									</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="list-item-bd clearfix">
		
					<div class="list-item-col list-5-05" style="width:100px;text-align:center">
						<img src="../img/u{{:sexIndex}}.png" width="80">
					</div>
		
					<div class="list-item-col list-5-05">
						<ul>
							<li><span class="text-primary">性别：</span>{{:sex}}</li>
							<li><span class="text-primary">年龄：</span> {{:age}}</li>
							<li><span class="text-primary">民族：</span> {{:ethnic}}</li>
							<li><span class="text-primary">政治面貌：</span> {{:politic}}</li>

						</ul>
					</div>
					<div class="list-item-col list-5-05" style="width:150px;">
						<ul>
							<li><span class="text-primary">教龄：</span> {{:tage}}</li>
							<li><span class="text-primary">职称：</span> {{:jobDuty}}</li>
							<li><span class="text-primary">行政职务：</span> {{:jobTitle}}</li>
							<li><span class="text-primary">是否双语教学：</span>{{:isMu}}</li>

						</ul>
					</div>
					<div class="list-item-col list-5-1">
						<ul>
							<li><span class="text-primary">主要教学学段：</span>{{:grade}} </li>
							<li><span class="text-primary">主要教学学科：</span>{{:subject}} </li>
							<li><span class="text-primary">主要教学语言：</span>{{:language}} </li>
							<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt">{{:area}}</div></li>

						</ul>
					</div>
					<div class="list-item-col list-5-8">
						<ul>
							<li><span class="text-primary">最高学历：</span> {{:edubackground}}</li>
							<li><span class="text-primary">汉语言水平：</span> {{:languageLevel}}</li>
							<li><span class="text-primary">身份证: </span>{{:idcard}}</li>
							<li>
								<span class="text-primary">手机：</span>{{:mobile}}
							</li>

						</ul>
					</div>
		

				</div>
			</div>
		</script>





	</div>
	</div>

</div>
	<script>
		$(function() {
			var recordReport = function(o, pp) {
				var pp = (typeof (pp) == 'undefined') ? 1 : pp;
				
				var projectId = url('?projectId');
				var subjectId = url('?subjectId');
				var collegeId = url('?collegeId');
				var gorganizationId = url('?gorganizationId');
				var porganizationId = url('?porganizationId');
				
				var str = $(o).serialize() + '&page=' + pp+'&projectId='+projectId+'&subjectId='+subjectId+'&collegeId='+collegeId+'&gorganizationId='+gorganizationId+'&porganizationId='+porganizationId;
				$('#list-item-cnt').html('<p style="font-size:20px;text-align:center;height:100px;line-height:100px;">数据加载中....</p>');
				$.getJSON('../admin/ttRecord_searchReportTeacher.action?' + str,
						function(data) {
							if (data.status == 'OK') {
								var template = $.templates('#ttRecordTpl');
								var html = template.render(data.records);
								$('#list-item-cnt').html(html);
								var options = {
									currentPage : data.page.currentPage,
									totalPages : data.page.totalPage,
									shouldShowPage:function(type, page, current){
						                switch(type)
						                {
						                    default:
						                        return true;
						                }
						            },
									onPageClicked : function(e, originalEvent,
											type, page) {
										var pp = page;
										recordReport('#ttRecordsReport', pp);
									}
								}
								$('#pagination').bootstrapPaginator(options);
								
								$(o).slideUp('slow',function(){
									$('.fliter-btn-wrap').show();
								})
								$('.alert-danger').hide();
							} else {
								$('.alert-danger').html(data.message).show();
								$('#list-item-cnt,#pagination').html('');
								
							}
				}).fail(function(){
					$('#list-item-cnt').html('系统错误请稍后重试');
				});
			}
			$('#ttRecordsReport').submit(function() {
				recordReport(this);
				return false;
			});
			
			$('.fliter-btn').click(function(e){
				e.preventDefault();
				$('.fliter-btn-wrap').hide();
				$('#ttRecordsReport').slideDown('slow');
				
			})

			var sort = (url('?sort')) ? url('?sort') : 'year-asc';
			var _ = sort.split('-');
			$('#sorting-' + _[0]).addClass('crt');
			$('#sorting-' + _[0]).find('span').addClass(_[1]);

			$('.sorting-btns a').click(function() {
				var name = $(this).attr('data-name');
				sortasc(name);
				return false;
			})

			//所属学校
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
							page : 10
						};
					},
					results : function(data, page) {
						return {
							results : data.Options
						};

					}
				},
				initSelection : function(element, callback) {
					var id = $(element).val();
					callback(data.Options);
				},
				formatResult : movieFormatResult,
				formatSelection : movieFormatSelection,
				dropdownCssClass : "bigdrop",
				escapeMarkup : function(m) {
					return m;
				}
			});

			function movieFormatResult(Options) {
				var html = Options.DisplayText;
				return html;

			}
			function movieFormatSelection(Options) {
				return Options.DisplayText;
			}

		})
	</script>
	<script src="../js/iframe.js"></script>
<body>
</html>